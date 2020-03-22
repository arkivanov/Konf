package com.arkivanov.konf.shared.sessionlist.integration

import com.arkivanov.konf.database.SessionBundle
import com.arkivanov.konf.shared.common.timeformat.TimeFormat
import com.arkivanov.konf.shared.sessionlist.SessionListComponent.Output
import com.arkivanov.konf.shared.sessionlist.SessionListView.Event
import com.arkivanov.konf.shared.sessionlist.SessionListView.Model
import com.arkivanov.konf.shared.sessionlist.store.SessionListStore.State

internal fun State.toViewModel(timeFormatProvider: TimeFormat.Provider): Model =
    Model(
        isLoading = isLoading,
        items = data?.toItemModels(timeFormatProvider = timeFormatProvider) ?: emptyList()
    )

private fun State.Data.toItemModels(timeFormatProvider: TimeFormat.Provider): List<Model.Item> {
    val timeFormat = timeFormatProvider.get(timeZone = event?.timeZone ?: "GMT")

    return items.map { it.toItemModel(timeFormat = timeFormat) }
}

private fun State.Item.toItemModel(timeFormat: TimeFormat): Model.Item =
    when (this) {
        is State.Item.DaySeparator -> Model.Item.DaySeparator(number = number)
        is State.Item.SessionSeparator -> Model.Item.SessionSeparator
        is State.Item.Session -> entity.toSessionModel(timeFormat = timeFormat)
    }

private fun SessionBundle.toSessionModel(timeFormat: TimeFormat): Model.Item.Session =
    Model.Item.Session(
        id = sessionId,
        title = sessionTitle,
        info = "${sessionStartDate?.let(timeFormat::format)}-${sessionEndDate?.let(timeFormat::format)}, $roomName",
        speakerInfo = "$speakerName @ $speakerCompanyName"
    )

internal fun Event.toOutput(): Output? =
    when (this) {
        is Event.CloseClicked -> Output.Finished
        is Event.SessionClicked -> Output.SessionSelected(id = id)
    }
