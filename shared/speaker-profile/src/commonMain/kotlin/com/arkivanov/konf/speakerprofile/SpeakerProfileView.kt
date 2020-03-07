package com.arkivanov.konf.speakerprofile

import com.arkivanov.konf.speakerprofile.SpeakerProfileView.Model
import com.arkivanov.mvikotlin.core.view.MviView

interface SpeakerProfileView : MviView<Model, Nothing> {

    data class Model(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val name: String? = null
    )
}
