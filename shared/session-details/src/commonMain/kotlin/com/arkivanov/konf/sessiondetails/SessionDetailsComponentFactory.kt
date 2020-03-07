package com.arkivanov.konf.sessiondetails

import com.arkivanov.konf.sessiondetails.SessionDetailsComponent.Dependencies
import com.arkivanov.konf.sessiondetails.integration.SessionDetailsComponentImpl

@Suppress("FunctionName")
fun SpeakerProfileComponent(dependencies: Dependencies): SessionDetailsComponent =
    SessionDetailsComponentImpl(dependencies)
