package com.arkivanov.konf.shared.sessionlist

import com.arkivanov.konf.shared.sessionlist.SessionListComponent.Dependencies
import com.arkivanov.konf.shared.sessionlist.integration.SessionListComponentImpl

@Suppress("FunctionName") // Factory function
fun SessionListComponent(dependencies: Dependencies): SessionListComponent =
    SessionListComponentImpl(dependencies)
