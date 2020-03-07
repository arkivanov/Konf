package com.arkivanov.konf.sessionlist

import com.arkivanov.konf.sessionlist.SessionListView.Model
import com.arkivanov.mvikotlin.core.view.MviView

interface SessionListView : MviView<Model, Nothing> {

    data class Model(
        val isLoading: Boolean = false,
        val sessions: List<Session> = emptyList()
    ) {
        data class Session(
            val id: String,
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
}
