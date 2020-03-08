package com.arkivanov.konf.sessionlist.integration

import com.arkivanov.konf.database.SessionBundle
import com.arkivanov.konf.database.SessionLevel
import com.arkivanov.konf.sessionlist.SessionListView
import com.arkivanov.konf.sessionlist.store.SessionListStore

internal fun SessionListStore.State.toViewModel(): SessionListView.Model =
    SessionListView.Model(
        isLoading = isLoading,
        sessions = sessions.map { it.toSessionModel() }
    )

private fun SessionBundle.toSessionModel(): SessionListView.Model.Session =
    SessionListView.Model.Session(
        id = sessionId,
        title = sessionTitle,
        description = sessionDescription,
        level = when (sessionLevel) {
            SessionLevel.BEGINNER -> SessionListView.Model.Session.Level.BEGINNER
            SessionLevel.INTERMEDIATE -> SessionListView.Model.Session.Level.INTERMEDIATE
            SessionLevel.ADVANCED -> SessionListView.Model.Session.Level.ADVANCED
            SessionLevel.EXPERT -> SessionListView.Model.Session.Level.EXPERT
            null -> null
        },
        speakerName = speakerName,
        speakerAvatarUrl = speakerAvatarUrl,
        speakerCompanyName = speakerCompanyName
    )
