package com.arkivanov.konf.shared.sync.datasource

import com.badoo.reaktive.maybe.Maybe
import com.badoo.reaktive.maybe.maybeFromFunction
import com.badoo.reaktive.maybe.subscribeOn
import com.badoo.reaktive.scheduler.ioScheduler
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.json

internal actual class SyncDataSourceImpl : SyncDataSource<JsonObject> {

    override fun load(): Maybe<JsonObject> =
        maybeFromFunction {
            Thread.sleep(1000L)

            json {} // Dummy JSON
        }
            .subscribeOn(ioScheduler)
}
