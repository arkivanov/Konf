package com.arkivanov.konf.sessiondetails

import com.arkivanov.konf.sessiondetails.SessionDetailsView.Model
import com.arkivanov.mvikotlin.core.view.MviView

interface SessionDetailsView : MviView<Model, Nothing> {

    data class Model(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val title: String? = null
    )
}
