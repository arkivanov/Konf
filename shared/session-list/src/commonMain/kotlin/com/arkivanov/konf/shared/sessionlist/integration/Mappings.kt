package com.arkivanov.konf.shared.sessionlist.integration

import com.arkivanov.konf.database.SessionBundle
import com.arkivanov.konf.shared.sessionlist.SessionListComponent.Output
import com.arkivanov.konf.shared.sessionlist.SessionListView.Event
import com.arkivanov.konf.shared.sessionlist.SessionListView.Model
import com.arkivanov.konf.shared.sessionlist.store.SessionListStore.State

internal fun State.toViewModel(): Model =
    Model(
        isLoading = isLoading,
        items = data?.toItemModels() ?: emptyList()
    )

private fun State.Data.toItemModels(): List<Model.Item> =
    items.map { it.toItemModel(eventTimeZone = event?.timeZone) }

private fun State.Item.toItemModel(eventTimeZone: String?): Model.Item =
    when (this) {
        is State.Item.DaySeparator -> Model.Item.DaySeparator(number = number)
        is State.Item.SessionSeparator -> Model.Item.SessionSeparator
        is State.Item.Session -> entity.toSessionModel(eventTimeZone = eventTimeZone)
    }

private fun SessionBundle.toSessionModel(eventTimeZone: String?): Model.Item.Session =
    Model.Item.Session(
        id = sessionId,
        title = sessionTitle,
        speakerInfo = "$speakerName @ $speakerCompanyName",
        startDate = sessionStartDate,
        endDate = sessionEndDate,
        roomName = roomName,
        eventTimeZone = eventTimeZone ?: "GMT"
    )

internal fun Event.toOutput(): Output? =
    when (this) {
        is Event.CloseClicked -> Output.Finished
        is Event.SessionClicked -> Output.SessionSelected(id = id)
    }
