package com.arkivanov.konf.shared.sessionlist.integration.mappers

import com.arkivanov.konf.shared.common.timeformat.TimeFormat
import com.arkivanov.konf.shared.sessionlist.SessionListView.Model
import com.arkivanov.konf.shared.sessionlist.model.SessionInfo
import com.arkivanov.konf.shared.sessionlist.store.SessionListStore.State

internal fun stateToModel(timeFormatProvider: TimeFormat.Provider): State.() -> Model =
    {
        Model(
            isLoading = isLoading,
            items = data?.toModelItems(timeFormatProvider) ?: emptyList()
        )
    }

private fun State.Data.toModelItems(timeFormatProvider: TimeFormat.Provider): List<Model.Item> {
    val timeFormat = timeFormatProvider.get(timeZone = event.timeZone ?: "GMT")

    return items.map { it.toModelItem(timeFormat) }
}

private fun State.Item.toModelItem(timeFormat: TimeFormat): Model.Item =
    when (this) {
        is State.Item.DaySeparator -> Model.Item.DaySeparator(number = number)
        is State.Item.SessionSeparator -> Model.Item.SessionSeparator
        is State.Item.Session -> session.toModelSession(timeFormat)
    }

private fun SessionInfo.toModelSession(timeFormat: TimeFormat): Model.Item.Session =
    Model.Item.Session(
        id = id,
        title = title,
        info = "${startDate?.let(timeFormat::format)}-${endDate?.let(timeFormat::format)}, $roomName",
        speakerInfo = "$speakerName @ $companyName"
    )
