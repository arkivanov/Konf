package com.arkivanov.konf.speakerdetails.store

import com.arkivanov.konf.database.SpeakerBundle
import com.arkivanov.konf.speakerdetails.store.SpeakerProfileStore.State
import com.arkivanov.mvikotlin.core.store.Store
import com.badoo.reaktive.disposable.Disposable

internal interface SpeakerProfileStore : Store<Nothing, State, Nothing>, Disposable {

    data class State(
        val isLoading: Boolean = false,
        val speaker: SpeakerBundle? = null
    )
}
