package com.arkivanov.konf.app.android.utils

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import com.arkivanov.konf.app.android.App
import java.util.Locale

val Context.app: App get() = this.applicationContext as App

@Suppress("DEPRECATION")
fun Configuration.getLocaleCompat(): Locale =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) locales[0] else locale
