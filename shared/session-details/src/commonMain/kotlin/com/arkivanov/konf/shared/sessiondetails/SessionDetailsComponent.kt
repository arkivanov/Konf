package com.arkivanov.konf.shared.sessiondetails

import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.konf.shared.common.dateformat.DateFormat
import com.arkivanov.konf.shared.common.timeformat.TimeFormat
import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle
import com.arkivanov.mvikotlin.core.store.StoreFactory

interface SessionDetailsComponent {

    fun onViewCreated(view: SessionDetailsView, output: (Output) -> Unit, viewLifecycle: Lifecycle)

    interface Dependencies {
        val sessionId: String
        val storeFactory: StoreFactory
        val database: KonfDatabase
        val lifecycle: Lifecycle
        val dateFormatProvider: DateFormat.Provider
        val timeFormatProvider: TimeFormat.Provider
    }

    sealed class Output {
        object Finished : Output()
        data class SpeakerSelected(val id: String) : Output()
    }
}
