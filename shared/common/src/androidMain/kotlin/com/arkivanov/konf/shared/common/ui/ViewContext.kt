package com.arkivanov.konf.shared.common.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.arkivanov.decompose.lifecycle.Lifecycle

interface ViewContext {

    val parent: ViewGroup
    val lifecycle: Lifecycle
}

val ViewContext.context: Context get() = parent.context

val ViewContext.layoutInflater: LayoutInflater get() = parent.context.layoutInflater

fun ViewContext.inflate(@LayoutRes layoutId: Int): View = layoutInflater.inflate(layoutId, parent, false)
