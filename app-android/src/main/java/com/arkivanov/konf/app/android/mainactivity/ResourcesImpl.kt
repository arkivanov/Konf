package com.arkivanov.konf.app.android.mainactivity

import android.content.Context
import com.arkivanov.konf.app.android.R
import com.arkivanov.konf.shared.common.resources.Resources

class ResourcesImpl(
    private val context: Context
) : Resources {

    override fun daySeparatorText(number: Int): String = context.getString(R.string.day_number_fmt, number)
}
