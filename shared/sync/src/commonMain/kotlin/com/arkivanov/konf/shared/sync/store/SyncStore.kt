package com.arkivanov.konf.shared.sync.store

import com.arkivanov.konf.shared.sync.store.SyncStore.Intent
import com.arkivanov.konf.shared.sync.store.SyncStore.State
import com.arkivanov.mvikotlin.core.store.Store
import com.badoo.reaktive.disposable.Disposable

internal interface SyncStore : Store<Intent, State, Nothing>, Disposable {

    sealed class Intent {
        object StartSync : Intent()
    }

    data class State(
        val isLoading: Boolean = false
    )
}
