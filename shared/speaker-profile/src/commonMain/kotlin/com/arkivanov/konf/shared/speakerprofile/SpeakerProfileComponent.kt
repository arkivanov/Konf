package com.arkivanov.konf.shared.speakerprofile

import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle
import com.arkivanov.mvikotlin.core.store.StoreFactory

interface SpeakerProfileComponent {

    fun onViewCreated(view: SpeakerProfileView, viewLifecycle: Lifecycle)

    interface Dependencies {
        val speakerId: String
        val storeFactory: StoreFactory
        val database: KonfDatabase
        val lifecycle: Lifecycle
        val profileOutput: (Output) -> Unit
    }

    sealed class Output {
        object Finished : Output()
    }
}
