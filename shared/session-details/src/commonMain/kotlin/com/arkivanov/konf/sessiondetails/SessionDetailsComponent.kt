package com.arkivanov.konf.sessiondetails

import com.arkivanov.konf.database.KonfDatabaseQueries
import com.arkivanov.mvikotlin.core.store.StoreFactory

interface SessionDetailsComponent {

    fun onViewCreated(view: SessionDetailsView)

    fun onStart()

    fun onStop()

    fun onViewDestroyed()

    fun onDestroy()

    interface Dependencies {
        val sessionId: String
        val storeFactory: StoreFactory
        val databaseQueries: KonfDatabaseQueries
    }
}
