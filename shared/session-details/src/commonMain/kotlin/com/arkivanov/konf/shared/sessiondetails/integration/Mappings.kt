package com.arkivanov.konf.shared.sessiondetails.integration

import com.arkivanov.konf.database.EventEntity
import com.arkivanov.konf.database.SessionBundle
import com.arkivanov.konf.database.SessionLevel
import com.arkivanov.konf.shared.common.dateformat.DateFormat
import com.arkivanov.konf.shared.common.timeformat.TimeFormat
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent.Output
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsView
import com.arkivanov.konf.shared.sessiondetails.store.SessionDetailsStore

internal fun SessionDetailsStore.State.toViewModel(
    dateFormatProvider: DateFormat.Provider,
    timeFormatProvider: TimeFormat.Provider
): SessionDetailsView.Model =
    SessionDetailsView.Model(
        isLoading = isLoading,
        isError = !isLoading && (session == null),
        title = session?.sessionTitle,
        description = session?.sessionDescription,
        imageUrl = session?.sessionImageUrl,
        level = when (session?.sessionLevel) {
            SessionLevel.BEGINNER -> SessionDetailsView.Model.Level.BEGINNER
            SessionLevel.INTERMEDIATE -> SessionDetailsView.Model.Level.INTERMEDIATE
            SessionLevel.ADVANCED -> SessionDetailsView.Model.Level.ADVANCED
            SessionLevel.EXPERT -> SessionDetailsView.Model.Level.EXPERT
            null -> null
        },
        info = formatSessionInfo(session, event, dateFormatProvider, timeFormatProvider),
        speakerName = session?.speakerName,
        speakerAvatarUrl = session?.speakerAvatarUrl,
        speakerJobInfo = session?.formatSpeakerInfo()
    )

private fun formatSessionInfo(
    session: SessionBundle?,
    event: EventEntity?,
    dateFormatProvider: DateFormat.Provider,
    timeFormatProvider: TimeFormat.Provider
): String? {
    if ((session == null) || (event == null)) {
        return null
    }

    val dateFormat = dateFormatProvider.get(timeZone = event.timeZone ?: "UTC")
    val timeFormat = timeFormatProvider.get(timeZone = event.timeZone ?: "UTC")

    return "${session.sessionStartDate?.let(dateFormat::format)}, " +
        "${session.sessionStartDate?.let(timeFormat::format)}-${session.sessionStartDate?.let(timeFormat::format)}, " +
        "${session.roomName}"
}

private fun SessionBundle.formatSpeakerInfo(): String = "$speakerJob @ $speakerCompanyName"

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
        is SessionDetailsView.Event.SpeakerClicked -> SessionDetailsStore.Intent.SelectSpeaker
    }
