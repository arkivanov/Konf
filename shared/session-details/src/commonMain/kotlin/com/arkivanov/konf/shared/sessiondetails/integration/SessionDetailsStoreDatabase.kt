package com.arkivanov.konf.shared.sessiondetails.integration

import com.arkivanov.konf.database.EventEntity
import com.arkivanov.konf.database.EventQueries
import com.arkivanov.konf.database.SessionBundle
import com.arkivanov.konf.database.SessionBundleQueries
import com.arkivanov.konf.database.listenOne
import com.arkivanov.konf.shared.sessiondetails.model.SessionInfo
import com.arkivanov.konf.shared.sessiondetails.store.SessionDetailsStoreFactory
import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.observable.combineLatest
import com.badoo.reaktive.observable.notNull

internal class SessionDetailsStoreDatabase(
    private val sessionId: String,
    private val eventQueries: EventQueries,
    private val sessionBundleQueries: SessionBundleQueries
) : SessionDetailsStoreFactory.Database {

    override fun getSession(): Observable<SessionInfo?> =
        combineLatest(
            eventQueries.get().listenOne().notNull(),
            sessionBundleQueries.getById(id = sessionId).listenOne()
        ) { event, session: SessionBundle? ->
            if (session == null) null else mapSession(event, session)
        }

    private fun mapSession(event: EventEntity, session: SessionBundle): SessionInfo =
        SessionInfo(
            title = session.sessionTitle,
            description = session.sessionDescription,
            imageUrl = session.sessionImageUrl,
            level = session.sessionLevel,
            startDate = session.sessionStartDate,
            endDate = session.sessionEndDate,
            roomName = session.roomName,
            speakerName = session.speakerName,
            speakerAvatarUrl = session.speakerAvatarUrl,
            speakerJob = session.speakerJob,
            speakerCompanyName = session.speakerCompanyName,
            speakerBiography = session.speakerBiography,
            speakerTwitterAccount = session.speakerTwitterAccount,
            speakerGitHubAccount = session.speakerGithubAccount,
            speakerFacebookAccount = session.speakerFacebookAccount,
            speakerLinkedInAccount = session.speakerLinkedInAccount,
            speakerMediumAccount = session.speakerMediumAccount,
            eventTimeZone = event.timeZone
        )
}
