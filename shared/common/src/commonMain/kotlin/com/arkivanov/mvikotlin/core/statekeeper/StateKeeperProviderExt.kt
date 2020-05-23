package com.arkivanov.mvikotlin.core.statekeeper

import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle
import com.arkivanov.mvikotlin.core.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.store.Store

/**
 * Same as [retainInstance] but dedicated to retain [Store]s. Automatically disposes the [Store] at the end of [Lifecycle].
 */
fun <T : Store<*, *, *>> StateKeeperProvider<Any>?.retainStore(lifecycle: Lifecycle, key: String, factory: (Lifecycle) -> T): T =
    this
        ?.retainInstance(lifecycle = lifecycle, key = key) {
            val store = factory(it)
            it.doOnDestroy(store::dispose)
            store
        }
        ?: factory(lifecycle)

inline fun <reified T : Store<*, *, *>> StateKeeperProvider<Any>?.retainStore(lifecycle: Lifecycle, noinline factory: (Lifecycle) -> T): T =
    this
        ?.retainInstance(lifecycle = lifecycle) {
            val store = factory(it)
            it.doOnDestroy(store::dispose)
            store
        }
        ?: factory(lifecycle)
