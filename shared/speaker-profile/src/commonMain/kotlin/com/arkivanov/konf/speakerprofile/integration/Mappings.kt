package com.arkivanov.konf.speakerprofile.integration

import com.arkivanov.konf.speakerprofile.SpeakerProfileView
import com.arkivanov.konf.speakerprofile.store.SpeakerProfileStore

internal fun SpeakerProfileStore.State.toViewModel(): SpeakerProfileView.Model =
    SpeakerProfileView.Model(
        isLoading = isLoading,
        isError = !isLoading && (speaker == null),
        name = speaker?.name
    )
