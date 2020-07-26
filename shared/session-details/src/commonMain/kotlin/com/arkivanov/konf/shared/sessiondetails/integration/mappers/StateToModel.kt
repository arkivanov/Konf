package com.arkivanov.konf.shared.sessiondetails.integration.mappers

import com.arkivanov.konf.database.SessionLevel
import com.arkivanov.konf.shared.common.dateformat.DateFormat
import com.arkivanov.konf.shared.common.timeformat.TimeFormat
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsView.Model
import com.arkivanov.konf.shared.sessiondetails.model.SessionInfo
import com.arkivanov.konf.shared.sessiondetails.store.SessionDetailsStore.State

internal fun stateToModel(dateFormatProvider: DateFormat.Provider, timeFormatProvider: TimeFormat.Provider): State.() -> Model =
    {
        Model(
            isLoading = isLoading,
            isError = !isLoading && (session == null),
            title = session?.title,
            description = session?.description,
            imageUrl = session?.imageUrl,
            level = when (session?.level) {
                SessionLevel.BEGINNER -> Model.Level.BEGINNER
                SessionLevel.INTERMEDIATE -> Model.Level.INTERMEDIATE
                SessionLevel.ADVANCED -> Model.Level.ADVANCED
                SessionLevel.EXPERT -> Model.Level.EXPERT
                null -> null
            },
            info = formatSessionInfo(session, dateFormatProvider, timeFormatProvider),
            speakerName = session?.speakerName,
            speakerAvatarUrl = session?.speakerAvatarUrl,
            speakerJobInfo = session?.formatSpeakerInfo(),
            speakerBiography = session?.speakerBiography,
            speakerTwitterAccount = session?.speakerTwitterAccount?.let { "https://twitter.com/$it" },
            speakerGitHubAccount = session?.speakerGitHubAccount?.let { "https://github.com/$it" },
            speakerFacebookAccount = session?.speakerFacebookAccount?.let { "https://facebook.com/$it" },
            speakerLinkedInAccount = session?.speakerLinkedInAccount?.let { "https://linkedin.com/in/$it" },
            speakerMediumAccount = session?.speakerMediumAccount?.let { "https://medium.com/@$it" }
        )
    }

private fun formatSessionInfo(
    session: SessionInfo?,
    dateFormatProvider: DateFormat.Provider,
    timeFormatProvider: TimeFormat.Provider
): String? {
    if (session == null) {
        return null
    }

    val dateFormat = dateFormatProvider.get(timeZone = session.eventTimeZone ?: "UTC")
    val timeFormat = timeFormatProvider.get(timeZone = session.eventTimeZone ?: "UTC")

    return "${session.startDate?.let(dateFormat::format)}, " +
            "${session.startDate?.let(timeFormat::format)}-${session.endDate?.let(timeFormat::format)}, " +
            "${session.roomName}"
}

private fun SessionInfo.formatSpeakerInfo(): String = "$speakerJob @ $speakerCompanyName"
