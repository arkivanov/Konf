package com.arkivanov.konf.speakerdetails.integration

import com.arkivanov.konf.speakerdetails.SpeakerProfileView
import com.arkivanov.konf.speakerdetails.store.SpeakerProfileStore

internal fun SpeakerProfileStore.State.toViewModel(): SpeakerProfileView.Model =
    SpeakerProfileView.Model(
        isLoading = isLoading,
        isError = !isLoading && (speaker == null),
        name = speaker?.name
    )
