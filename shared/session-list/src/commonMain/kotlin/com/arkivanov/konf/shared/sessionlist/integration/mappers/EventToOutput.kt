package com.arkivanov.konf.shared.sessionlist.integration.mappers

import com.arkivanov.konf.shared.sessionlist.SessionListComponent.Output
import com.arkivanov.konf.shared.sessionlist.SessionListView.Event

internal val eventToOutput: Event.() -> Output =
    {
        when (this) {
            is Event.CloseClicked -> Output.Finished
            is Event.SessionClicked -> Output.SessionSelected(id = id)
        }
    }
