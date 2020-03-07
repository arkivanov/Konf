package com.arkivanov.konf.speakerdetails

import com.arkivanov.konf.speakerdetails.SpeakerProfileComponent.Dependencies
import com.arkivanov.konf.speakerdetails.integration.SpeakerProfileComponentImpl

@Suppress("FunctionName")
fun SpeakerProfileComponent(dependencies: Dependencies): SpeakerProfileComponent =
    SpeakerProfileComponentImpl(dependencies)
