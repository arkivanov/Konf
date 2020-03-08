package com.arkivanov.konf.sessionlist

import com.arkivanov.konf.database.KonfDatabaseQueries
import com.arkivanov.konf.sessionlist.SessionListComponent.Output
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.badoo.reaktive.observable.Observable

interface SessionListComponent : Observable<Output> {

    fun onViewCreated(view: SessionListView)

    fun onStart()

    fun onStop()

    fun onViewDestroyed()

    fun onDestroy()

    interface Dependencies {
        val storeFactory: StoreFactory
        val databaseQueries: KonfDatabaseQueries
    }

    sealed class Output {
        object Finished : Output()
        data class SessionSelected(val id: String) : Output()
    }
}
