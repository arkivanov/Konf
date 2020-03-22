package com.arkivanov.konf.shared.common.dateformat

interface DateFormat {

    fun format(millis: Long): String

    interface Provider {
        fun get(timeZone: String): DateFormat
    }
}
