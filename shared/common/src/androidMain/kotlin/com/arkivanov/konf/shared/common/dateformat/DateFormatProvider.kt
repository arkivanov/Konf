package com.arkivanov.konf.shared.common.dateformat

import com.arkivanov.konf.shared.common.ProvidableCache
import java.util.Locale

class DateFormatProviderImpl(
    private val locale: Locale
) : DateFormat.Provider {

    private val cache = ProvidableCache<String, DateFormat> { DateFormatImpl(locale) }

    override fun get(timeZone: String): DateFormat = cache[timeZone]
}
