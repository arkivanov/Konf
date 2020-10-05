package com.arkivanov.konf.shared.sessiondetails

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.konf.shared.common.dateformat.DateFormat
import com.arkivanov.konf.shared.common.timeformat.TimeFormat
import com.arkivanov.mvikotlin.core.store.StoreFactory

interface SessionDetailsComponent {

    val model: Model

    interface Model : Events {
        val data: Value<SessionDetailsViewModel>
    }

    interface Events {
        fun onCloseClicked()
        fun onSocialAccountClicked(url: String)
    }

    interface Dependencies {
        val componentContext: ComponentContext
        val sessionId: String
        val storeFactory: StoreFactory
        val database: KonfDatabase
        val dateFormatProvider: DateFormat.Provider
        val timeFormatProvider: TimeFormat.Provider
        val detailsOutput: (Output) -> Unit
    }

    sealed class Output {
        object Finished : Output()
        data class SocialAccountSelected(val url: String) : Output()
    }
}
