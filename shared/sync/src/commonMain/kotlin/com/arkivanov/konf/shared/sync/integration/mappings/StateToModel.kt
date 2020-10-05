package com.arkivanov.konf.shared.sync.integration.mappings

import com.arkivanov.konf.shared.sync.SyncViewModel
import com.arkivanov.konf.shared.sync.store.SyncStore.State

internal val stateToModel: State.() -> SyncViewModel =
    {
        SyncViewModel(isLoading = isLoading)
    }
