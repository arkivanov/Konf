package com.arkivanov.konf.shared.speakerprofile.integration

import com.arkivanov.konf.database.SpeakerBundle
import com.arkivanov.konf.database.SpeakerBundleQueries
import com.arkivanov.konf.database.listenOne
import com.arkivanov.konf.shared.speakerprofile.model.SpeakerInfo
import com.arkivanov.konf.shared.speakerprofile.store.SpeakerProfileStoreFactory
import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.observable.map

internal class SpeakerProfileStoreDatabase(
    private val speakerId: String,
    private val speakerBundleQueries: SpeakerBundleQueries
) : SpeakerProfileStoreFactory.Database {

    override fun getSpeaker(): Observable<SpeakerInfo?> =
        speakerBundleQueries
            .getById(id = speakerId)
            .listenOne()
            .map { it?.toSpeakerInfo() }

    private fun SpeakerBundle.toSpeakerInfo(): SpeakerInfo =
        SpeakerInfo(
            name = speakerName,
            imageUrl = speakerImageUrl,
            job = speakerJob,
            location = speakerLocation,
            biography = speakerBiography,
            twitterAccount = speakerTwitterAccount,
            githubAccount = speakerGithubAccount,
            facebookAccount = speakerFacebookAccount,
            linkedInAccount = speakerLinkedInAccount,
            mediumAccount = speakerMediumAccount,
            companyName = companyName,
            companyLogoUrl = companyLogoUrl
        )
}
