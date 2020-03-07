package com.arkivanov.konf.sessionlist

import com.arkivanov.konf.database.KonfDatabaseQueries
import com.arkivanov.mvikotlin.core.store.StoreFactory

interface SessionListComponent {

    fun onViewCreated(view: SessionListView)

    fun onStart()

    fun onStop()

    fun onViewDestroyed()

    fun onDestroy()

    interface Dependencies {
        val storeFactory: StoreFactory
        val databaseQueries: KonfDatabaseQueries
    }
}
