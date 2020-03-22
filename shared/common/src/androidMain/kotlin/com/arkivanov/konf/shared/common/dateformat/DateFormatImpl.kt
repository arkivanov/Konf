package com.arkivanov.konf.shared.common.dateformat

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal class DateFormatImpl(locale: Locale) : DateFormat {

    private val delegate = SimpleDateFormat("E dd", locale)

    override fun format(millis: Long): String = delegate.format(Date(millis))
}
