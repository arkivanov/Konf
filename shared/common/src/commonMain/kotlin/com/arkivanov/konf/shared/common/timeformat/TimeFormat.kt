package com.arkivanov.konf.shared.common.timeformat

interface TimeFormat {

    fun format(millis: Long): String

    interface Provider {
        fun get(timeZone: String): TimeFormat
    }
}
