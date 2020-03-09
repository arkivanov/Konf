package com.arkivanov.konf.shared.sync.datasource

import com.badoo.reaktive.annotations.EventsOnAnyScheduler
import com.badoo.reaktive.maybe.Maybe

/*
 * By default returns dummy data, customize with your own data
 */
internal interface SyncDataSource<T> {

    @EventsOnAnyScheduler
    fun load(): Maybe<T>
}
