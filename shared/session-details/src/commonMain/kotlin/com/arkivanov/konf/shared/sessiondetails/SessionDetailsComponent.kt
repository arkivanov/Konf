package com.arkivanov.konf.shared.sessiondetails

import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent.Output
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
        val database: KonfDatabase
    }

    sealed class Output {
        object Finished : Output()
        data class SpeakerSelected(val id: String) : Output()
    }
}
