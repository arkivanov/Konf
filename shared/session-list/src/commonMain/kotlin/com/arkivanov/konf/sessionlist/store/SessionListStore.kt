package com.arkivanov.konf.sessionlist.store

import com.arkivanov.konf.database.SessionBundle
import com.arkivanov.konf.sessionlist.store.SessionListStore.Intent
import com.arkivanov.konf.sessionlist.store.SessionListStore.Label
import com.arkivanov.konf.sessionlist.store.SessionListStore.State
import com.arkivanov.mvikotlin.core.store.Store
import com.badoo.reaktive.disposable.Disposable

internal interface SessionListStore : Store<Intent, State, Label>, Disposable {

    sealed class Intent {
        data class SelectSession(val index: Int) : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val sessions: List<SessionBundle> = emptyList()
    )

    sealed class Label {
        data class SessionSelected(val id: String) : Label()
    }
}
