package com.arkivanov.konf.shared.sessionlist

import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.konf.shared.common.timeformat.TimeFormat
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.statekeeper.StateKeeperProvider

interface SessionListComponent {

    fun onViewCreated(view: SessionListView)

    fun onStart()

    fun onStop()

    fun onViewDestroyed()

    fun onDestroy()

    interface Dependencies {
        val storeFactory: StoreFactory
        val database: KonfDatabase
        val stateKeeperProvider: StateKeeperProvider<Any>?
        val timeFormatProvider: TimeFormat.Provider
        val output: (Output) -> Unit
    }

    sealed class Output {
        object Finished : Output()
        data class SessionSelected(val id: String) : Output()
    }
}
