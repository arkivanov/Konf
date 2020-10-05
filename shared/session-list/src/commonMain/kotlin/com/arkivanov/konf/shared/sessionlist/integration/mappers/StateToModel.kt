package com.arkivanov.konf.shared.sessionlist.integration.mappers

import com.arkivanov.konf.shared.common.resources.Resources
import com.arkivanov.konf.shared.common.timeformat.TimeFormat
import com.arkivanov.konf.shared.sessionlist.SessionListViewModel
import com.arkivanov.konf.shared.sessionlist.model.SessionInfo
import com.arkivanov.konf.shared.sessionlist.store.SessionListStore.State

internal fun stateToModel(
    timeFormatProvider: TimeFormat.Provider,
    resources: Resources
): State.() -> SessionListViewModel =
    {
        SessionListViewModel(
            isLoading = isLoading,
            items = data?.toModelItems(timeFormatProvider, resources) ?: emptyList()
        )
    }

private fun State.Data.toModelItems(timeFormatProvider: TimeFormat.Provider, resources: Resources): List<SessionListViewModel.Item> {
    val timeFormat = timeFormatProvider.get(timeZone = event.timeZone ?: "GMT")

    return items.map { it.toModelItem(timeFormat, resources) }
}

private fun State.Item.toModelItem(timeFormat: TimeFormat, resources: Resources): SessionListViewModel.Item =
    when (this) {
        is State.Item.DaySeparator -> SessionListViewModel.Item.DaySeparator(text = resources.daySeparatorText(number))
        is State.Item.SessionSeparator -> SessionListViewModel.Item.SessionSeparator
        is State.Item.Session -> session.toModelSession(timeFormat)
    }

private fun SessionInfo.toModelSession(timeFormat: TimeFormat): SessionListViewModel.Item.Session =
    SessionListViewModel.Item.Session(
        id = id,
        title = title,
        info = "${startDate?.let(timeFormat::format)}-${endDate?.let(timeFormat::format)}, $roomName",
        speakerInfo = "$speakerName @ $companyName"
    )
