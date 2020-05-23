package com.arkivanov.konf.shared.sessiondetails.integration.mappers

import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent.Output
import com.arkivanov.konf.shared.sessiondetails.store.SessionDetailsStore.Label

internal val labelToOutput: Label.() -> Output? =
    {
        when (this) {
            is Label.SpeakerSelected -> Output.SpeakerSelected(id = id)
        }
    }
