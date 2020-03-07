package com.arkivanov.konf.sessiondetails.integration

import com.arkivanov.konf.database.SessionLevel
import com.arkivanov.konf.sessiondetails.SessionDetailsView
import com.arkivanov.konf.sessiondetails.store.SessionDetailsStore

internal fun SessionDetailsStore.State.toViewModel(): SessionDetailsView.Model =
    SessionDetailsView.Model(
        isLoading = isLoading,
        isError = !isLoading && (session == null),
        title = session?.sessionTitle,
        description = session?.sessionDescription,
        level = when (session?.sessionLevel) {
            SessionLevel.BEGINNER -> SessionDetailsView.Model.Level.BEGINNER
            SessionLevel.INTERMEDIATE -> SessionDetailsView.Model.Level.INTERMEDIATE
            SessionLevel.ADVANCED -> SessionDetailsView.Model.Level.ADVANCED
            SessionLevel.EXPERT -> SessionDetailsView.Model.Level.EXPERT
            null -> null
        },
        speakerName = session?.speakerName,
        speakerAvatarUrl = session?.speakerAvatarUrl,
        speakerCompanyName = session?.speakerCompanyName
    )
