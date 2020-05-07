package com.arkivanov.konf.shared.sync

import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle
import com.arkivanov.mvikotlin.core.store.StoreFactory

interface SyncComponent {

    fun onViewCreated(view: SyncView, viewLifecycle: Lifecycle)

    interface Dependencies {
        val storeFactory: StoreFactory
        val database: KonfDatabase
        val lifecycle: Lifecycle
    }
}
