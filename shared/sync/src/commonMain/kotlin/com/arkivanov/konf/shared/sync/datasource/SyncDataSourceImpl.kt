package com.arkivanov.konf.shared.sync.datasource

import kotlinx.serialization.json.JsonObject

internal expect class SyncDataSourceImpl() : SyncDataSource<JsonObject>
