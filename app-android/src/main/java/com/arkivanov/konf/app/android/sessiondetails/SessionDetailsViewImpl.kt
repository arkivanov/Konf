package com.arkivanov.konf.app.android.sessiondetails

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.arkivanov.konf.app.android.R
import com.arkivanov.konf.app.android.utils.clipToCircle
import com.arkivanov.konf.app.android.utils.loadImage
import com.arkivanov.konf.app.android.utils.requireNotNull
import com.arkivanov.konf.app.android.utils.requireViewWithId
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsView
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsView.Event
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsView.Model
import com.arkivanov.mvikotlin.core.utils.diff
import com.arkivanov.mvikotlin.core.view.BaseMviView
import com.arkivanov.mvikotlin.core.view.ViewRenderer
import com.google.android.material.appbar.MaterialToolbar


class SessionDetailsViewImpl(root: View) : BaseMviView<Model, Event>(), SessionDetailsView {

    private val context = root.context
    private val toolbar = root.requireViewWithId<MaterialToolbar>(R.id.toolbar)
    private val imageView = root.requireViewWithId<ImageView>(R.id.image)
    private val titleTextView = root.requireViewWithId<TextView>(R.id.text_title)
    private val descriptionTextView = root.requireViewWithId<TextView>(R.id.text_description)
    private val speakerAvatarImageView = root.requireViewWithId<ImageView>(R.id.image_speaker_avatar)
    private val speakerNameTextView = root.requireViewWithId<TextView>(R.id.text_speaker_name)
    private val speakerJobTextView = root.requireViewWithId<TextView>(R.id.text_speaker_job)
    private val speakerBiographyTextView = root.requireViewWithId<TextView>(R.id.text_speaker_biography)
    private val twitterImageButton = root.requireViewWithId<ImageButton>(R.id.button_twitter)
    private val gitHubImageButton = root.requireViewWithId<ImageButton>(R.id.button_github)
    private val facebookImageButton = root.requireViewWithId<ImageButton>(R.id.button_facebook)
    private val linkedInImageButton = root.requireViewWithId<ImageButton>(R.id.button_linkedin)
    private val mediumImageButton = root.requireViewWithId<ImageButton>(R.id.button_medium)
    private val infoTextView = root.requireViewWithId<TextView>(R.id.text_info)

    init {
        toolbar.setNavigationOnClickListener { dispatch(Event.CloseClicked) }
        speakerAvatarImageView.clipToCircle()
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
            diff(get = Model::speakerBiography, set = speakerBiographyTextView::setText)

            diff(get = Model::speakerAvatarUrl) {
                speakerAvatarImageView.loadImage(url = it, placeholderAttrId = R.attr.icAvatarPlaceholder)
            }

            diff(get = Model::speakerTwitterAccount) { twitterImageButton.setUrl(it) }
            diff(get = Model::speakerGitHubAccount) { gitHubImageButton.setUrl(it) }
            diff(get = Model::speakerFacebookAccount) { facebookImageButton.setUrl(it) }
            diff(get = Model::speakerLinkedInAccount) { linkedInImageButton.setUrl(it) }
            diff(get = Model::speakerMediumAccount) { mediumImageButton.setUrl(it) }
        }

    private fun ImageButton.setUrl(url: String?) {
        isVisible = url != null
        setOnClickListener { openLink(url.requireNotNull()) }
    }

    private fun openLink(url: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}
