package com.arkivanov.konf.sessionlist.store

import com.arkivanov.konf.database.SessionAndSpeaker
import com.arkivanov.konf.sessionlist.store.SessionListStore.State
import com.arkivanov.mvikotlin.core.store.Store
import com.badoo.reaktive.disposable.Disposable

internal interface SessionListStore : Store<Nothing, State, Nothing>, Disposable {

    data class State(
        val isLoading: Boolean = false,
        val sessions: List<SessionAndSpeaker> = emptyList()
    )
}
