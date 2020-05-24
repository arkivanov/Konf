package com.arkivanov.konf.shared.sync.integration.mappings

import com.arkivanov.konf.shared.sync.SyncView.Event
import com.arkivanov.konf.shared.sync.store.SyncStore.Intent

internal val eventToIntent: Event.() -> Intent? =
    {
        when (this) {
            is Event.RefreshTriggered -> Intent.StartSync
        }
    }
