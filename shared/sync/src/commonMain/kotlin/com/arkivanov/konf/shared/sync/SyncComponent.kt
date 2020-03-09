package com.arkivanov.konf.shared.sync

import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.mvikotlin.core.store.StoreFactory

interface SyncComponent {

    fun onViewCreated(view: SyncView)

    fun onStart()

    fun onStop()

    fun onViewDestroyed()

    fun onDestroy()

    interface Dependencies {
        val storeFactory: StoreFactory
        val database: KonfDatabase
    }
}
