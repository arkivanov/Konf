package com.arkivanov.konf.sessiondetails

import com.arkivanov.konf.sessiondetails.SessionDetailsView.Model
import com.arkivanov.mvikotlin.core.view.MviView

interface SessionDetailsView : MviView<Model, Nothing> {

    data class Model(
        val isLoading: Boolean,
        val isError: Boolean,
        val title: String?,
        val description: String?,
        val level: Level?,
        val speakerName: String?,
        val speakerAvatarUrl: String?,
        val speakerCompanyName: String?
    ) {
        enum class Level {
            BEGINNER, INTERMEDIATE, ADVANCED, EXPERT
        }
    }
}
