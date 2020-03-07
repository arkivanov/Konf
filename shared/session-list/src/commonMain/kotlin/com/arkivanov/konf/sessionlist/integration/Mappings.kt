package com.arkivanov.konf.sessionlist.integration

import com.arkivanov.konf.database.SessionAndSpeaker
import com.arkivanov.konf.sessionlist.SessionListView
import com.arkivanov.konf.sessionlist.store.SessionListStore

internal fun SessionListStore.State.toViewModel(): SessionListView.Model =
    SessionListView.Model(
        isLoading = isLoading,
        sessions = sessions.map { it.toSessionModel() }
    )

private fun SessionAndSpeaker.toSessionModel(): SessionListView.Model.Session =
    SessionListView.Model.Session(
        id = sessionId,
        title = sessionTitle,
        speaker = SessionListView.Model.Speaker(
            name = speakerName
        )
    )
