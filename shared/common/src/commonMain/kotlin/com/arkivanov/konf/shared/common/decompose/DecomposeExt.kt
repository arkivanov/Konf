package com.arkivanov.konf.shared.common.decompose

import com.arkivanov.decompose.instancekeeper.InstanceKeeper
import com.arkivanov.decompose.instancekeeper.getOrCreate
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.ValueObserver
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.rx.Disposable
import com.arkivanov.mvikotlin.rx.observer

fun <T : Store<*, *, *>> InstanceKeeper.getStore(key: Any, factory: () -> T): T =
    getOrCreate(key) { StoreHolder(factory()) }
        .store

inline fun <reified T : Store<*, *, *>> InstanceKeeper.getStore(noinline factory: () -> T): T = getStore(T::class, factory)

private class StoreHolder<T : Store<*, *, *>>(val store: T) : InstanceKeeper.Instance {
    override fun onDestroy() {
        store.dispose()
    }
}

fun <T : Any> Store<*, T, *>.asValue(): Value<T> =
    object : Value<T>() {
        override val value: T get() = state

        private val disposables = HashMap<ValueObserver<*>, Disposable>()

        override fun subscribe(observer: ValueObserver<T>) {
            disposables[observer] = states(observer { observer(it) })
        }

        override fun unsubscribe(observer: ValueObserver<T>) {
            disposables
                .remove(observer)
                ?.dispose()
        }
    }

fun <T : Any, R : Any> Store<*, T, *>.asValue(mapper: (T) -> R): Value<R> =
    asValue().map(mapper)
