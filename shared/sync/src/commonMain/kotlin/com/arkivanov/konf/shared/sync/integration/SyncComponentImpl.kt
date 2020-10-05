package com.arkivanov.konf.shared.sync.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.lifecycle.doOnDestroy
import com.arkivanov.decompose.value.Value
import com.arkivanov.konf.shared.common.decompose.asValue
import com.arkivanov.konf.shared.sync.SyncComponent
import com.arkivanov.konf.shared.sync.SyncComponent.Dependencies
import com.arkivanov.konf.shared.sync.SyncComponent.Events
import com.arkivanov.konf.shared.sync.SyncComponent.Model
import com.arkivanov.konf.shared.sync.SyncViewModel
import com.arkivanov.konf.shared.sync.datasource.SyncDataSourceImpl
import com.arkivanov.konf.shared.sync.integration.mappings.jsonObjectToSyncData
import com.arkivanov.konf.shared.sync.integration.mappings.stateToModel
import com.arkivanov.konf.shared.sync.store.SyncStore
import com.arkivanov.konf.shared.sync.store.SyncStore.Intent
import com.arkivanov.konf.shared.sync.store.SyncStoreFactory

internal class SyncComponentImpl(
    dependencies: Dependencies
) : SyncComponent, ComponentContext by dependencies.componentContext, Events {

    private val store: SyncStore =
        SyncStoreFactory(
            factory = dependencies.storeFactory,
            dataSource = SyncDataSourceImpl(),
            mapper = jsonObjectToSyncData,
            database = dependencies.database
        ).create()

    init {
        lifecycle.doOnDestroy(store::dispose)
    }

    override val model: Model =
        object : Model, Events by this {
            override val data: Value<SyncViewModel> = store.asValue(stateToModel)
        }

    override fun onRefreshTriggered() {
        store.accept(Intent.StartSync)
    }
}
