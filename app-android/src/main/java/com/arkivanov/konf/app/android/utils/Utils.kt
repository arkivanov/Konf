package com.arkivanov.konf.app.android.utils

import android.view.View
import androidx.annotation.IdRes

fun <T : Any> T?.requireNotNull(): T = requireNotNull(this)

fun <T : View> View.requireViewWithId(@IdRes id: Int): T = findViewById<T>(id).requireNotNull()
