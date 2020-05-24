package com.arkivanov.konf.shared.sync.integration.mappings

import com.arkivanov.konf.shared.sync.SyncView.Model
import com.arkivanov.konf.shared.sync.store.SyncStore.State

internal val stateToModel: State.() -> Model =
    {
        Model(isLoading = isLoading)
    }
