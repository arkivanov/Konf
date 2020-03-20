package com.arkivanov.konf.shared.sessionlist

import com.arkivanov.konf.shared.sessionlist.SessionListView.Event
import com.arkivanov.konf.shared.sessionlist.SessionListView.Model
import com.arkivanov.mvikotlin.core.view.MviView

interface SessionListView : MviView<Model, Event> {

    data class Model(
        val isLoading: Boolean,
        val items: List<Item>
    ) {
        sealed class Item {
            data class DaySeparator(val number: Int) : Item()
            object SessionSeparator : Item()

            data class Session(
                val id: String,
                val title: String?,
                val speakerInfo: String?,
                val startDate: Long?,
                val endDate: Long?,
                val roomName: String?,
                val eventTimeZone: String
            ) : Item()
        }
    }

    sealed class Event {
        object CloseClicked : Event()
        data class SessionClicked(val id: String) : Event()
    }
}
