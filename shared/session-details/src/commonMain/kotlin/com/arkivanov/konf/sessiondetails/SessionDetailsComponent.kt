package com.arkivanov.konf.sessiondetails

import com.arkivanov.konf.database.KonfDatabaseQueries
import com.arkivanov.konf.sessiondetails.SessionDetailsComponent.Output
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.badoo.reaktive.observable.Observable

interface SessionDetailsComponent : Observable<Output> {

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

    sealed class Output {
        object Finished : Output()
        data class SpeakerSelected(val id: String): Output()
    }
}
