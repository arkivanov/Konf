package com.arkivanov.konf.app.android.sessiondetails

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.arkivanov.konf.app.android.R
import com.arkivanov.konf.app.android.utils.DiffMviView
import com.arkivanov.konf.app.android.utils.clipToCircle
import com.arkivanov.konf.app.android.utils.formatDate
import com.arkivanov.konf.app.android.utils.formatTime
import com.arkivanov.konf.app.android.utils.requireViewWithId
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsView
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsView.Event
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsView.Model
import com.arkivanov.mvikotlin.core.utils.DiffBuilder
import com.squareup.picasso.Picasso

class SessionDetailsViewImpl(root: View) : DiffMviView<Model, Event>(), SessionDetailsView {

    private val context = root.context
    private val imageView = root.requireViewWithId<ImageView>(R.id.image)
    private val titleTextView = root.requireViewWithId<TextView>(R.id.text_title)
    private val descriptionTextView = root.requireViewWithId<TextView>(R.id.text_description)
    private val speakerAvatarImageView = root.requireViewWithId<ImageView>(R.id.image_speaker_avatar)
    private val speakerNameTextView = root.requireViewWithId<TextView>(R.id.text_speaker_name)
    private val speakerJobTextView = root.requireViewWithId<TextView>(R.id.text_speaker_job)
    private val infoTextView = root.requireViewWithId<TextView>(R.id.text_info)

    init {
        speakerAvatarImageView.clipToCircle()
        root.requireViewWithId<View>(R.id.layout_speaker).setOnClickListener { dispatch(Event.SpeakerClicked) }
    }

    override fun DiffBuilder<Model>.setupDiff() {
        diff(get = Model::title, bind = titleTextView::setText)
        diff(get = Model::description, bind = descriptionTextView::setText)

        diff(get = Model::imageUrl) {
            Picasso.get().load(it).into(imageView)
        }

        diff(get = Model::speakerName, bind = speakerNameTextView::setText)
        diff(get = Model::speakerJobInfo, bind = speakerJobTextView::setText)

        diff(get = Model::speakerAvatarUrl) {
            Picasso.get().load(it).into(speakerAvatarImageView)
        }
    }

    override fun render(model: Model) {
        super.render(model)

        val dateText = model.startDate?.let { formatDate(context, model.eventTimeZone, it) }
        val startTimeText = model.startDate?.let { formatTime(context, model.eventTimeZone, it) }
        val endTimeText = model.endDate?.let { formatTime(context, model.eventTimeZone, it) }
        infoTextView.text = "$dateText, $startTimeText-$endTimeText, ${model.roomName}"
    }
}
