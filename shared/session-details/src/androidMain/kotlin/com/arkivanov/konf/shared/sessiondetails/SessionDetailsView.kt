package com.arkivanov.konf.shared.sessiondetails

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.arkivanov.decompose.extensions.android.ViewContext
import com.arkivanov.decompose.extensions.android.layoutInflater
import com.arkivanov.konf.shared.common.decompose.diff
import com.arkivanov.konf.shared.common.ui.clipToCircle
import com.arkivanov.konf.shared.common.ui.loadImage
import com.arkivanov.konf.shared.common.ui.requireNotNull
import com.arkivanov.konf.shared.common.ui.requireViewWithId
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent.Events
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent.Model
import com.google.android.material.appbar.MaterialToolbar
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsViewModel as ViewModel

@Suppress("FunctionName") // Factory function
fun ViewContext.SessionDetailsView(model: Model): View {
    val root = layoutInflater.inflate(R.layout.session_details, parent, false)

    val toolbar = root.requireViewWithId<MaterialToolbar>(R.id.toolbar)
    val imageView = root.requireViewWithId<ImageView>(R.id.image)
    val titleTextView = root.requireViewWithId<TextView>(R.id.text_title)
    val descriptionTextView = root.requireViewWithId<TextView>(R.id.text_description)
    val speakerAvatarImageView = root.requireViewWithId<ImageView>(R.id.image_speaker_avatar)
    val speakerNameTextView = root.requireViewWithId<TextView>(R.id.text_speaker_name)
    val speakerJobTextView = root.requireViewWithId<TextView>(R.id.text_speaker_job)
    val speakerBiographyTextView = root.requireViewWithId<TextView>(R.id.text_speaker_biography)
    val twitterImageButton = root.requireViewWithId<ImageButton>(R.id.button_twitter)
    val gitHubImageButton = root.requireViewWithId<ImageButton>(R.id.button_github)
    val facebookImageButton = root.requireViewWithId<ImageButton>(R.id.button_facebook)
    val linkedInImageButton = root.requireViewWithId<ImageButton>(R.id.button_linkedin)
    val mediumImageButton = root.requireViewWithId<ImageButton>(R.id.button_medium)
    val infoTextView = root.requireViewWithId<TextView>(R.id.text_info)

    toolbar.setNavigationOnClickListener { model.onCloseClicked() }
    speakerAvatarImageView.clipToCircle()

    diff(model.data) {
        diff(get = ViewModel::title, set = toolbar::setTitle)
        diff(get = ViewModel::title, set = titleTextView::setText)
        diff(get = ViewModel::description, set = descriptionTextView::setText)
        diff(get = ViewModel::imageUrl, set = imageView::loadImage)
        diff(get = ViewModel::info, set = infoTextView::setText)
        diff(get = ViewModel::speakerName, set = speakerNameTextView::setText)
        diff(get = ViewModel::speakerJobInfo, set = speakerJobTextView::setText)
        diff(get = ViewModel::speakerBiography, set = speakerBiographyTextView::setText)

        diff(get = ViewModel::speakerAvatarUrl) {
            speakerAvatarImageView.loadImage(url = it, placeholderAttrId = R.attr.icAvatarPlaceholder)
        }

        diff(get = ViewModel::speakerTwitterAccount) { twitterImageButton.setUrl(it, model) }
        diff(get = ViewModel::speakerGitHubAccount) { gitHubImageButton.setUrl(it, model) }
        diff(get = ViewModel::speakerFacebookAccount) { facebookImageButton.setUrl(it, model) }
        diff(get = ViewModel::speakerLinkedInAccount) { linkedInImageButton.setUrl(it, model) }
        diff(get = ViewModel::speakerMediumAccount) { mediumImageButton.setUrl(it, model) }
    }

    return root
}

private fun ImageButton.setUrl(url: String?, model: Events) {
    isVisible = url != null
    setOnClickListener { model.onSocialAccountClicked(url.requireNotNull()) }
}
