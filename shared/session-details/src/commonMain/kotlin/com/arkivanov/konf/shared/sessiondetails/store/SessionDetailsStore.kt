package com.arkivanov.konf.shared.sessiondetails.store

import com.arkivanov.konf.database.EventEntity
import com.arkivanov.konf.database.SessionBundle
import com.arkivanov.konf.shared.sessiondetails.store.SessionDetailsStore.Intent
import com.arkivanov.konf.shared.sessiondetails.store.SessionDetailsStore.Label
import com.arkivanov.konf.shared.sessiondetails.store.SessionDetailsStore.State
import com.arkivanov.mvikotlin.core.store.Store
import com.badoo.reaktive.disposable.Disposable

internal interface SessionDetailsStore : Store<Intent, State, Label>, Disposable {

    sealed class Intent {
        object SelectSpeaker : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val event: EventEntity? = null,
        val session: SessionBundle? = null
    )

    sealed class Label {
        data class SpeakerSelected(val id: String) : Label()
    }
}
