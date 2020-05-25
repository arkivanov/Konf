package com.arkivanov.konf.shared.speakerprofile

import com.arkivanov.konf.shared.speakerprofile.SpeakerProfileComponent.Dependencies
import com.arkivanov.konf.shared.speakerprofile.integration.SpeakerProfileComponentImpl

@Suppress("FunctionName") // Factory function
fun SpeakerProfileComponent(dependencies: Dependencies): SpeakerProfileComponent =
    SpeakerProfileComponentImpl(dependencies)
