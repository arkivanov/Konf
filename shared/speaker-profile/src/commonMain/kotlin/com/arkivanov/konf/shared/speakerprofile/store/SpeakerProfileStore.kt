package com.arkivanov.konf.shared.speakerprofile.store

import com.arkivanov.konf.shared.speakerprofile.model.SpeakerInfo
import com.arkivanov.konf.shared.speakerprofile.store.SpeakerProfileStore.State
import com.arkivanov.mvikotlin.core.store.Store
import com.badoo.reaktive.disposable.Disposable

internal interface SpeakerProfileStore : Store<Nothing, State, Nothing>, Disposable {

    data class State(
        val isLoading: Boolean = false,
        val speaker: SpeakerInfo? = null
    )
}
