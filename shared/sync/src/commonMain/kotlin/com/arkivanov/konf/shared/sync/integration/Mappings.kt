package com.arkivanov.konf.shared.sync.integration

import com.arkivanov.konf.shared.sync.SyncView
import com.arkivanov.konf.shared.sync.store.SyncStore

internal fun SyncStore.State.toViewModel(): SyncView.Model =
    SyncView.Model(
        isLoading = isLoading
    )

internal fun SyncView.Event.toIntent(): SyncStore.Intent? =
    when (this) {
        is SyncView.Event.RefreshTriggered -> SyncStore.Intent.StartSync
    }
