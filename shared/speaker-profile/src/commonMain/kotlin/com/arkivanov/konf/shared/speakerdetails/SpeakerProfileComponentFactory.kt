package com.arkivanov.konf.shared.speakerdetails

import com.arkivanov.konf.shared.speakerdetails.SpeakerProfileComponent.Dependencies
import com.arkivanov.konf.shared.speakerdetails.integration.SpeakerProfileComponentImpl

@Suppress("FunctionName")
fun SpeakerProfileComponent(dependencies: Dependencies): SpeakerProfileComponent =
    SpeakerProfileComponentImpl(dependencies)
