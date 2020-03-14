package com.arkivanov.konf.app.android.utils

import com.arkivanov.mvikotlin.core.utils.DiffBuilder
import com.arkivanov.mvikotlin.core.utils.diff
import com.arkivanov.mvikotlin.core.view.AbstractMviView
import com.arkivanov.mvikotlin.core.view.ViewRenderer

abstract class DiffMviView<Model : Any, Event : Any> : AbstractMviView<Model, Event>() {

    private var diff: ViewRenderer<Model>? = null

    protected abstract fun DiffBuilder<Model>.setupDiff()

    override fun render(model: Model) {
        val diff = diff ?: diff<Model> { setupDiff() }.also { diff = it }
        diff.render(model)
    }
}
