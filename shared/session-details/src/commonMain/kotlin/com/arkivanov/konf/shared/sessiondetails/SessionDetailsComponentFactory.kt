package com.arkivanov.konf.shared.sessiondetails

import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent.Dependencies
import com.arkivanov.konf.shared.sessiondetails.integration.SessionDetailsComponentImpl

@Suppress("FunctionName")
fun SpeakerProfileComponent(dependencies: Dependencies): SessionDetailsComponent =
    SessionDetailsComponentImpl(dependencies)
