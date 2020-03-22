package com.arkivanov.konf.shared.common

class ProvidableCache<in K, out V : Any>(
    private val provider: (K) -> V
) {

    private val cache = HashMap<K, V>()

    operator fun get(key: K): V {
        var value: V? = cache[key]
        if (value == null) {
            value = provider(key)
            cache[key] = value
        }

        return value
    }
}
