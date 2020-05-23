package com.arkivanov.konf.shared.sessiondetails.integration.mappers

import com.arkivanov.konf.shared.sessiondetails.SessionDetailsView.Event
import com.arkivanov.konf.shared.sessiondetails.store.SessionDetailsStore.Intent

internal val eventToIntent: Event.() -> Intent? =
    {
        when (this) {
            is Event.CloseClicked -> null
            is Event.SpeakerClicked -> Intent.SelectSpeaker
        }
    }
