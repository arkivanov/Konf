package com.arkivanov.konf.database

import com.badoo.reaktive.annotations.EventsOnAnyScheduler
import com.badoo.reaktive.annotations.EventsOnIoScheduler
import com.badoo.reaktive.disposable.Disposable
import com.badoo.reaktive.maybe.Maybe
import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.observable.flatMapSingle
import com.badoo.reaktive.observable.observable
import com.badoo.reaktive.scheduler.ioScheduler
import com.badoo.reaktive.single.Single
import com.badoo.reaktive.single.notNull
import com.badoo.reaktive.single.singleFromFunction
import com.badoo.reaktive.single.subscribeOn
import com.squareup.sqldelight.Query

@EventsOnIoScheduler
fun <T : Any> Query<T>.oneOrNull(): Single<T?> =
    singleFromFunction(::executeAsOneOrNull)
        .subscribeOn(ioScheduler)

@EventsOnIoScheduler
fun <T : Any> Query<T>.oneOrEmpty(): Maybe<T> =
    singleFromFunction(::executeAsOneOrNull)
        .subscribeOn(ioScheduler)
        .notNull()

@EventsOnIoScheduler
fun <T : Any> Query<T>.list(): Single<List<T>> =
    singleFromFunction(::executeAsList)
        .subscribeOn(ioScheduler)

@EventsOnIoScheduler
fun <T : Any> Query<T>.listenOne(): Observable<T?> =
    listenForChanges()
        .flatMapSingle { oneOrNull() }

@EventsOnIoScheduler
fun <T : Any> Query<T>.listenList(): Observable<List<T>> =
    listenForChanges()
        .flatMapSingle { list() }

@EventsOnAnyScheduler
private fun Query<*>.listenForChanges(): Observable<Unit> =
    observable { emitter ->
        val listener = queryListener { emitter.onNext(Unit) }
        addListener(listener)
        emitter.onNext(Unit)
        emitter.setDisposable(Disposable { removeListener(listener) })
    }

private inline fun queryListener(crossinline listener: () -> Unit): Query.Listener =
    object : Query.Listener {
        override fun queryResultsChanged() {
            listener()
        }
    }
