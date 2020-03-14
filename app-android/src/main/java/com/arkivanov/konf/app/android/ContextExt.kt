package com.arkivanov.konf.app.android

import android.content.Context

val Context.app: App get() = this.applicationContext as App
