package com.arkivanov.konf.sessiondetails.integration

import com.arkivanov.konf.sessiondetails.SessionDetailsView
import com.arkivanov.konf.sessiondetails.store.SessionDetailsStore

internal fun SessionDetailsStore.State.toViewModel(): SessionDetailsView.Model =
    SessionDetailsView.Model(
        isLoading = isLoading,
        isError = !isLoading && (session == null),
        title = session?.title
    )
