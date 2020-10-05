package com.arkivanov.konf.shared.sessionlist

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.konf.shared.common.resources.Resources
import com.arkivanov.konf.shared.common.timeformat.TimeFormat
import com.arkivanov.mvikotlin.core.store.StoreFactory

interface SessionListComponent {

    val model: Model

    interface Model : Events {
        val data: Value<SessionListViewModel>
        val syncStatus: Value<Boolean>
    }

    interface Events {
        fun onCloseClicked()
        fun onSessionClicked(id: String)
        fun onRefreshTriggered()
    }

    interface Dependencies {
        val componentContext: ComponentContext
        val storeFactory: StoreFactory
        val database: KonfDatabase
        val timeFormatProvider: TimeFormat.Provider
        val resources: Resources
        val syncStatus: Value<Boolean>
        val listOutput: (Output) -> Unit
    }

    sealed class Output {
        object Finished : Output()
        data class SessionSelected(val id: String) : Output()
        object RefreshTriggered : Output()
    }
}
