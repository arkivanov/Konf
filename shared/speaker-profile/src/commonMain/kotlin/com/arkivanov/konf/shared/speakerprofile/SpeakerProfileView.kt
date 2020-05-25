package com.arkivanov.konf.shared.speakerprofile

import com.arkivanov.konf.shared.speakerprofile.SpeakerProfileView.Event
import com.arkivanov.konf.shared.speakerprofile.SpeakerProfileView.Model
import com.arkivanov.mvikotlin.core.view.MviView

interface SpeakerProfileView : MviView<Model, Event> {

    data class Model(
        val isLoading: Boolean,
        val isError: Boolean,
        val name: String?,
        val imageUrl: String?,
        val job: String?,
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
