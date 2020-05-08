package com.arkivanov.konf.shared.sessionlist.integration

import com.arkivanov.konf.database.EventEntity
import com.arkivanov.konf.database.EventQueries
import com.arkivanov.konf.database.SessionBundle
import com.arkivanov.konf.database.SessionBundleQueries
import com.arkivanov.konf.database.listenList
import com.arkivanov.konf.database.listenOne
import com.arkivanov.konf.shared.sessionlist.model.EventInfo
import com.arkivanov.konf.shared.sessionlist.model.SessionInfo
import com.arkivanov.konf.shared.sessionlist.store.SessionListStoreFactory
import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.observable.map
import com.badoo.reaktive.observable.mapIterable
import com.badoo.reaktive.observable.notNull

internal class SessionListStoreDatabase(
    private val eventQueries: EventQueries,
    private val sessionBundleQueries: SessionBundleQueries
) : SessionListStoreFactory.Database {

    override fun getEvent(): Observable<EventInfo> =
        eventQueries
            .get()
            .listenOne()
            .notNull()
            .map { it.toEventInfo() }

    private fun EventEntity.toEventInfo(): EventInfo =
        EventInfo(
            startDate = startDate,
            endDate = endDate,
            timeZone = timeZone
        )

    override fun getSessions(): Observable<List<SessionInfo>> =
        sessionBundleQueries
            .getAll()
            .listenList()
            .mapIterable { it.toSessionInfo() }

    private fun SessionBundle.toSessionInfo(): SessionInfo =
        SessionInfo(
            id = sessionId,
            title = sessionTitle,
            startDate = sessionStartDate,
            endDate = sessionEndDate,
            roomName = roomName,
            speakerName = speakerName,
            companyName = speakerCompanyName
        )
}
