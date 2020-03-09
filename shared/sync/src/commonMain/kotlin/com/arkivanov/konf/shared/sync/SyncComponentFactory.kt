package com.arkivanov.konf.shared.sync

import com.arkivanov.konf.shared.sync.SyncComponent.Dependencies
import com.arkivanov.konf.shared.sync.integration.SyncComponentImpl

@Suppress("FunctionName")
fun SyncComponent(dependencies: Dependencies): SyncComponent =
    SyncComponentImpl(dependencies)
