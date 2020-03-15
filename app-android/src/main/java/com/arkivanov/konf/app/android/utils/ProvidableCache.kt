package com.arkivanov.konf.app.android.utils

import androidx.collection.ArrayMap

class ProvidableCache<K, V : Any>(
    private val provider: (K) -> V
) {

    private val cache = ArrayMap<K, V>()

    operator fun get(key: K): V {
        var value: V? = cache[key]
        if (value == null) {
            value = provider(key)
            cache[key] = value
        }

        return value
    }
}
