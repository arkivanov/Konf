package com.arkivanov.konf.shared.sessiondetails

import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent.Dependencies
import com.arkivanov.konf.shared.sessiondetails.integration.SessionDetailsComponentImpl

@Suppress("FunctionName") // Factory function
fun SessionDetailsComponent(dependencies: Dependencies): SessionDetailsComponent =
    SessionDetailsComponentImpl(dependencies)
