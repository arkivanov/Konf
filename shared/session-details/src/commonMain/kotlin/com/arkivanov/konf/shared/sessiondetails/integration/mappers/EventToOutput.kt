package com.arkivanov.konf.shared.sessiondetails.integration.mappers

import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent.Output
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsView.Event

internal val eventToOutput: Event.() -> Output? =
    {
        when (this) {
            is Event.CloseClicked -> Output.Finished
        }
    }
