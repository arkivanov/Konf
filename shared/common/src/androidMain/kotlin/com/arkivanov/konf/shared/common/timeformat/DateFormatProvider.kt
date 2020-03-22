package com.arkivanov.konf.shared.common.timeformat

import com.arkivanov.konf.shared.common.ProvidableCache
import java.util.Locale

class TimeFormatProviderImpl(
    private val locale: Locale
) : TimeFormat.Provider {

    private val cache = ProvidableCache<String, TimeFormat> { TimeFormatImpl(locale) }

    override fun get(timeZone: String): TimeFormat = cache[timeZone]
}
