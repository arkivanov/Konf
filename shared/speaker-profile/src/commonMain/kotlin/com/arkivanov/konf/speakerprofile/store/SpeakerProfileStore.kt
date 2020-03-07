package com.arkivanov.konf.speakerprofile.store

import com.arkivanov.konf.database.SpeakerEntity
import com.arkivanov.konf.speakerprofile.store.SpeakerProfileStore.State
import com.arkivanov.mvikotlin.core.store.Store
import com.badoo.reaktive.disposable.Disposable

internal interface SpeakerProfileStore : Store<Nothing, State, Nothing>, Disposable {

    data class State(
        val isLoading: Boolean = false,
        val speaker: SpeakerEntity? = null
    )
}
