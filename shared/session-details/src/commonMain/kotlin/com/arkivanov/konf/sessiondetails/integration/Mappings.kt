package com.arkivanov.konf.sessiondetails.integration

import com.arkivanov.konf.database.SessionLevel
import com.arkivanov.konf.sessiondetails.SessionDetailsComponent.Output
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

internal fun SessionDetailsStore.Label.toOutput(): Output? =
    when (this) {
        is SessionDetailsStore.Label.SpeakerSelected -> Output.SpeakerSelected(id = id)
    }

internal fun SessionDetailsView.Event.toOutput(): Output? =
    when (this) {
        is SessionDetailsView.Event.CloseClicked -> Output.Finished
        is SessionDetailsView.Event.SpeakerClicked -> null
    }

internal fun SessionDetailsView.Event.toIntent(): SessionDetailsStore.Intent? =
    when (this) {
        is SessionDetailsView.Event.CloseClicked -> null
        is SessionDetailsView.Event.SpeakerClicked -> SessionDetailsStore.Intent.SelectSpaker
    }
