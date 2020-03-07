package com.arkivanov.konf.speakerprofile

import com.arkivanov.konf.database.KonfDatabaseQueries
import com.arkivanov.mvikotlin.core.store.StoreFactory

interface SpeakerProfileComponent {

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
}
