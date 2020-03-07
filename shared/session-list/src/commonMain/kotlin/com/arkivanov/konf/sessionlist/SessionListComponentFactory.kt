package com.arkivanov.konf.sessionlist

import com.arkivanov.konf.sessionlist.SessionListComponent.Dependencies
import com.arkivanov.konf.sessionlist.integration.SessionListComponentImpl

@Suppress("FunctionName")
fun SpeakerProfileComponent(dependencies: Dependencies): SessionListComponent =
    SessionListComponentImpl(dependencies)
