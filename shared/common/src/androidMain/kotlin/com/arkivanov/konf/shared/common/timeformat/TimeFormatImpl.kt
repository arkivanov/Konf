package com.arkivanov.konf.shared.common.timeformat

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal class TimeFormatImpl(locale: Locale) : TimeFormat {

    private val delegate = SimpleDateFormat("h:mm", locale)

    override fun format(millis: Long): String = delegate.format(Date(millis))
}
