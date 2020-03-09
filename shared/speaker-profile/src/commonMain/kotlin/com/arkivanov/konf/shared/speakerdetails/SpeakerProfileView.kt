package com.arkivanov.konf.shared.speakerdetails

import com.arkivanov.konf.shared.speakerdetails.SpeakerProfileView.Event
import com.arkivanov.konf.shared.speakerdetails.SpeakerProfileView.Model
import com.arkivanov.mvikotlin.core.view.MviView

interface SpeakerProfileView : MviView<Model, Event> {

    data class Model(
        val isLoading: Boolean,
        val isError: Boolean,
        val name: String?,
        val imageUrl: String?,
        val location: String?,
        val biography: String?,
        val twitterAccount: String?,
        val githubAccount: String?,
        val facebookAccount: String?,
        val linkedInAccount: String?,
        val mediumAccount: String?,
        val companyName: String?,
        val companyLogoUrl: String?
    )

    sealed class Event {
        object CloseClicked : Event()
    }
}
