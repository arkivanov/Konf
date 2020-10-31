package com.arkivanov.konf.shared.common.decompose

import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.extensions.android.RouterView
import com.arkivanov.decompose.extensions.android.ViewContext
import com.arkivanov.decompose.lifecycle.Lifecycle
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.observe
import com.arkivanov.mvikotlin.core.utils.DiffBuilder
import com.arkivanov.mvikotlin.core.utils.diff
import com.arkivanov.mvikotlin.core.view.ViewRenderer

fun <T : Any> ViewRenderer<T>.render(value: Value<T>, lifecycle: Lifecycle) {
    value.observe(lifecycle = lifecycle, observer = ::render)
}

inline fun <Model : Any> diff(value: Value<Model>, lifecycle: Lifecycle, block: DiffBuilder<Model>.() -> Unit) {
    diff(block)
        .render(value, lifecycle)
}

inline fun <Model : Any> ViewContext.diff(value: Value<Model>, block: DiffBuilder<Model>.() -> Unit) {
    diff(value, lifecycle, block)
}

internal inline fun ViewGroup.forEachChild(block: (View) -> Unit) {
    for (i in 0 until childCount) {
        block(getChildAt(i))
    }
}

fun <C : Parcelable, T : Any> View.children(
    routerState: Value<RouterState<C, T>>,
    @IdRes routerViewId: Int,
    lifecycle: Lifecycle,
    replaceChildView: ViewContext.(parent: ViewGroup, child: T, configuration: C) -> Unit
) {
    findViewById<RouterView>(routerViewId)
        .children(routerState, lifecycle, replaceChildView)
}
