package com.arkivanov.konf.shared.sync

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.mvikotlin.core.store.StoreFactory

interface SyncComponent {

    val model: Model

    interface Model : Events {
        val data: Value<SyncViewModel>
    }

    interface Events {
        fun onRefreshTriggered()
    }

    interface Dependencies {
        val componentContext: ComponentContext
        val storeFactory: StoreFactory
        val database: KonfDatabase
    }
}
