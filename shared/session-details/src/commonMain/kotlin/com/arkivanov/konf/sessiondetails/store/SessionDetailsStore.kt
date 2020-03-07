package com.arkivanov.konf.sessiondetails.store

import com.arkivanov.konf.database.SessionBundle
import com.arkivanov.konf.sessiondetails.store.SessionDetailsStore.State
import com.arkivanov.mvikotlin.core.store.Store
import com.badoo.reaktive.disposable.Disposable

internal interface SessionDetailsStore : Store<Nothing, State, Nothing>, Disposable {

    data class State(
        val isLoading: Boolean = false,
        val session: SessionBundle? = null
    )
}
