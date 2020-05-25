package com.arkivanov.konf.shared.speakerprofile.integration.mappings

import com.arkivanov.konf.shared.speakerprofile.SpeakerProfileView.Model
import com.arkivanov.konf.shared.speakerprofile.store.SpeakerProfileStore.State

internal val stateToModel: State.() -> Model =
    {
        Model(
            isLoading = isLoading,
            isError = !isLoading && (speaker == null),
            name = speaker?.name,
            imageUrl = speaker?.imageUrl,
            job = speaker?.job,
            location = speaker?.location,
            biography = speaker?.biography,
            twitterAccount = speaker?.twitterAccount,
            githubAccount = speaker?.githubAccount,
            facebookAccount = speaker?.facebookAccount,
            linkedInAccount = speaker?.linkedInAccount,
            mediumAccount = speaker?.mediumAccount,
            companyName = speaker?.companyName,
            companyLogoUrl = speaker?.companyLogoUrl
        )
    }
