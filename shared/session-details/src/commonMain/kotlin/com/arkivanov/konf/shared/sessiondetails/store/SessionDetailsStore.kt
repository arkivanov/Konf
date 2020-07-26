package com.arkivanov.konf.shared.sessiondetails.store

import com.arkivanov.konf.shared.sessiondetails.model.SessionInfo
import com.arkivanov.konf.shared.sessiondetails.store.SessionDetailsStore.Intent
import com.arkivanov.konf.shared.sessiondetails.store.SessionDetailsStore.Label
import com.arkivanov.konf.shared.sessiondetails.store.SessionDetailsStore.State
import com.arkivanov.mvikotlin.core.store.Store
import com.badoo.reaktive.disposable.Disposable

internal interface SessionDetailsStore : Store<Intent, State, Label>, Disposable {

    sealed class Intent

    data class State(
        val isLoading: Boolean = false,
        val session: SessionInfo? = null
    )

    sealed class Label
}
