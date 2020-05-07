package com.arkivanov.konf.shared.sync.integration

import com.arkivanov.konf.shared.sync.SyncComponent
import com.arkivanov.konf.shared.sync.SyncComponent.Dependencies
import com.arkivanov.konf.shared.sync.SyncView
import com.arkivanov.konf.shared.sync.datasource.SyncDataSourceImpl
import com.arkivanov.konf.shared.sync.store.SyncStore
import com.arkivanov.konf.shared.sync.store.SyncStoreFactory
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle
import com.arkivanov.mvikotlin.core.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.extensions.reaktive.bind
import com.arkivanov.mvikotlin.extensions.reaktive.events
import com.arkivanov.mvikotlin.extensions.reaktive.states
import com.badoo.reaktive.annotations.ExperimentalReaktiveApi
import com.badoo.reaktive.observable.map
import com.badoo.reaktive.observable.mapNotNull
import kotlinx.serialization.json.JsonObject

@UseExperimental(ExperimentalReaktiveApi::class)
internal class SyncComponentImpl(dependencies: Dependencies) : SyncComponent {

    private val store: SyncStore =
        SyncStoreFactory(
            factory = dependencies.storeFactory,
            dataSource = SyncDataSourceImpl(),
            mapper = JsonObject::toSyncData,
            database = dependencies.database
        ).create()

    init {
        dependencies.lifecycle.doOnDestroy(store::dispose)
    }

    override fun onViewCreated(view: SyncView, viewLifecycle: Lifecycle) {
        bind(viewLifecycle, BinderLifecycleMode.CREATE_DESTROY) {
            view.events.mapNotNull(SyncView.Event::toIntent) bindTo store
        }

        bind(viewLifecycle, BinderLifecycleMode.START_STOP) {
            store.states.map(SyncStore.State::toViewModel) bindTo view
        }
    }
}
