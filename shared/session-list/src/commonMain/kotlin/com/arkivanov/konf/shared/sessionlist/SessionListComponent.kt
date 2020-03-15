package com.arkivanov.konf.shared.sessionlist

import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.mvikotlin.core.store.StoreFactory

interface SessionListComponent {

    fun onViewCreated(view: SessionListView)

    fun onStart()

    fun onStop()

    fun onViewDestroyed()

    fun onDestroy()

    interface Dependencies {
        val storeFactory: StoreFactory
        val database: KonfDatabase
        val output: (Output) -> Unit
    }

    sealed class Output {
        object Finished : Output()
        data class SessionSelected(val id: String) : Output()
    }
}
