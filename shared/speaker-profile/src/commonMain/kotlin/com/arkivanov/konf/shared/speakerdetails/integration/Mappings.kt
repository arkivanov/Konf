package com.arkivanov.konf.shared.speakerdetails.integration

import com.arkivanov.konf.shared.speakerdetails.SpeakerProfileComponent.Output
import com.arkivanov.konf.shared.speakerdetails.SpeakerProfileView
import com.arkivanov.konf.shared.speakerdetails.store.SpeakerProfileStore

internal fun SpeakerProfileStore.State.toViewModel(): SpeakerProfileView.Model =
    SpeakerProfileView.Model(
        isLoading = isLoading,
        isError = !isLoading && (speaker == null),
        name = speaker?.speakerName,
        imageUrl = speaker?.speakerImageUrl,
        location = speaker?.speakerLocation,
        biography = speaker?.speakerBiography,
        twitterAccount = speaker?.speakerTwitterAccount,
        githubAccount = speaker?.speakerGithubAccount,
        facebookAccount = speaker?.speakerFacebookAccount,
        linkedInAccount = speaker?.speakerLinkedInAccount,
        mediumAccount = speaker?.speakerMediumAccount,
        companyName = speaker?.companyName,
        companyLogoUrl = speaker?.companyLogoUrl
    )

internal fun SpeakerProfileView.Event.toOutput(): Output? =
    when (this) {
        is SpeakerProfileView.Event.CloseClicked -> Output.Finished
    }
