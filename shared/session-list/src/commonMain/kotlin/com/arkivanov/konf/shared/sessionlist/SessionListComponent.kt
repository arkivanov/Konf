package com.arkivanov.konf.shared.sessionlist

import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.konf.shared.common.timeformat.TimeFormat
import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle
import com.arkivanov.mvikotlin.core.store.StoreFactory

interface SessionListComponent {

    fun onViewCreated(view: SessionListView, viewLifecycle: Lifecycle)

    interface Dependencies {
        val storeFactory: StoreFactory
        val database: KonfDatabase
        val lifecycle: Lifecycle
        val timeFormatProvider: TimeFormat.Provider
        val output: (Output) -> Unit
    }

    sealed class Output {
        object Finished : Output()
        data class SessionSelected(val id: String) : Output()
    }
}
