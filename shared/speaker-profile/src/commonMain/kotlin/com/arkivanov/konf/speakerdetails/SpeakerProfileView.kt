package com.arkivanov.konf.speakerdetails

import com.arkivanov.konf.speakerdetails.SpeakerProfileView.Model
import com.arkivanov.mvikotlin.core.view.MviView

interface SpeakerProfileView : MviView<Model, Nothing> {

    data class Model(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val name: String? = null
    )
}
