package com.arkivanov.konf.shared.sync

import com.arkivanov.konf.shared.sync.SyncView.Event
import com.arkivanov.konf.shared.sync.SyncView.Model
import com.arkivanov.mvikotlin.core.view.MviView

interface SyncView : MviView<Model, Event> {

    data class Model(
        val isLoading: Boolean
    )

    sealed class Event {
        object RefreshTriggered : Event()
    }
}
