package com.arkivanov.konf.speakerprofile

import com.arkivanov.konf.speakerprofile.SpeakerProfileComponent.Dependencies
import com.arkivanov.konf.speakerprofile.integration.SpeakerProfileComponentImpl

@Suppress("FunctionName")
fun SpeakerProfileComponent(dependencies: Dependencies): SpeakerProfileComponent =
    SpeakerProfileComponentImpl(dependencies)
