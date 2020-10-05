package com.arkivanov.konf.shared.root

import com.arkivanov.konf.shared.root.RootComponent.Dependencies
import com.arkivanov.konf.shared.root.integration.RootComponentImpl

@Suppress("FunctionName") // Factory function
fun RootComponent(dependencies: Dependencies): RootComponent = RootComponentImpl(dependencies)
