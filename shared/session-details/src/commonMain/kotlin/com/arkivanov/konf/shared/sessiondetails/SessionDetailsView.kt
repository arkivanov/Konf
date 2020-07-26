package com.arkivanov.konf.shared.sessiondetails

import com.arkivanov.konf.shared.sessiondetails.SessionDetailsView.Event
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsView.Model
import com.arkivanov.mvikotlin.core.view.MviView

interface SessionDetailsView : MviView<Model, Event> {

    data class Model(
        val isLoading: Boolean,
        val isError: Boolean,
        val title: String?,
        val description: String?,
        val imageUrl: String?,
        val level: Level?,
        val info: String?,
        val speakerName: String?,
        val speakerAvatarUrl: String?,
        val speakerJobInfo: String?,
        val speakerBiography: String?,
        val speakerTwitterAccount: String?,
        val speakerGitHubAccount: String?,
        val speakerFacebookAccount: String?,
        val speakerLinkedInAccount: String?,
        val speakerMediumAccount: String?
    ) {
        enum class Level {
            BEGINNER, INTERMEDIATE, ADVANCED, EXPERT
        }
    }

    sealed class Event {
        object CloseClicked : Event()
    }
}
