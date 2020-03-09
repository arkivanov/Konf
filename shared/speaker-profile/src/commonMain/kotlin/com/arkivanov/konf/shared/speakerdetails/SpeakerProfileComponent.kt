package com.arkivanov.konf.shared.speakerdetails

import com.arkivanov.konf.database.KonfDatabaseQueries
import com.arkivanov.konf.shared.speakerdetails.SpeakerProfileComponent.Output
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.badoo.reaktive.observable.Observable

interface SpeakerProfileComponent : Observable<Output> {

    fun onViewCreated(view: SpeakerProfileView)

    fun onStart()

    fun onStop()

    fun onViewDestroyed()

    fun onDestroy()

    interface Dependencies {
        val speakerId: String
        val storeFactory: StoreFactory
        val databaseQueries: KonfDatabaseQueries
    }

    sealed class Output {
        object Finished : Output()
    }
}
