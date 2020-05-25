package com.arkivanov.konf.shared.speakerprofile.integration.mappings

import com.arkivanov.konf.shared.speakerprofile.SpeakerProfileComponent.Output
import com.arkivanov.konf.shared.speakerprofile.SpeakerProfileView.Event

internal val eventToOutput: Event.() -> Output? =
    {
        when (this) {
            is Event.CloseClicked -> Output.Finished
        }
    }
