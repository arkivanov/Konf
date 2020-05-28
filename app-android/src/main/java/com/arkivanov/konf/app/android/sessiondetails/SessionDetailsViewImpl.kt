package com.arkivanov.konf.app.android.sessiondetails

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.arkivanov.konf.app.android.R
import com.arkivanov.konf.app.android.utils.clipToCircle
import com.arkivanov.konf.app.android.utils.loadImage
import com.arkivanov.konf.app.android.utils.requireViewWithId
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsView
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsView.Event
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsView.Model
import com.arkivanov.mvikotlin.core.utils.diff
import com.arkivanov.mvikotlin.core.view.BaseMviView
import com.arkivanov.mvikotlin.core.view.ViewRenderer
import com.google.android.material.appbar.MaterialToolbar

class SessionDetailsViewImpl(root: View) : BaseMviView<Model, Event>(), SessionDetailsView {

    private val toolbar = root.requireViewWithId<MaterialToolbar>(R.id.toolbar)
    private val imageView = root.requireViewWithId<ImageView>(R.id.image)
    private val titleTextView = root.requireViewWithId<TextView>(R.id.text_title)
    private val descriptionTextView = root.requireViewWithId<TextView>(R.id.text_description)
    private val speakerAvatarImageView = root.requireViewWithId<ImageView>(R.id.image_speaker_avatar)
    private val speakerNameTextView = root.requireViewWithId<TextView>(R.id.text_speaker_name)
    private val speakerJobTextView = root.requireViewWithId<TextView>(R.id.text_speaker_job)
    private val infoTextView = root.requireViewWithId<TextView>(R.id.text_info)

    init {
        toolbar.setNavigationOnClickListener { dispatch(Event.CloseClicked) }
        speakerAvatarImageView.clipToCircle()
        root.requireViewWithId<View>(R.id.layout_speaker).setOnClickListener { dispatch(Event.SpeakerClicked) }
    }

    override val renderer: ViewRenderer<Model>? =
        diff {
            diff(get = Model::title, set = toolbar::setTitle)
            diff(get = Model::title, set = titleTextView::setText)
            diff(get = Model::description, set = descriptionTextView::setText)
            diff(get = Model::imageUrl, set = imageView::loadImage)
            diff(get = Model::info, set = infoTextView::setText)
            diff(get = Model::speakerName, set = speakerNameTextView::setText)
            diff(get = Model::speakerJobInfo, set = speakerJobTextView::setText)

            diff(get = Model::speakerAvatarUrl) {
                speakerAvatarImageView.loadImage(url = it, placeholderAttrId = R.attr.icAvatarPlaceholder)
            }
        }
}
