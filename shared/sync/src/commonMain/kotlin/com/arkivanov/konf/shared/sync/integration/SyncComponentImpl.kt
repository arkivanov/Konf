package com.arkivanov.konf.shared.sync.integration

import com.arkivanov.konf.shared.sync.SyncComponent
import com.arkivanov.konf.shared.sync.SyncComponent.Dependencies
import com.arkivanov.konf.shared.sync.SyncView
import com.arkivanov.konf.shared.sync.datasource.SyncDataSourceImpl
import com.arkivanov.konf.shared.sync.store.SyncStore
import com.arkivanov.konf.shared.sync.store.SyncStoreFactory
import com.arkivanov.mvikotlin.core.binder.Binder
import com.arkivanov.mvikotlin.extensions.reaktive.bind
import com.arkivanov.mvikotlin.extensions.reaktive.events
import com.arkivanov.mvikotlin.extensions.reaktive.states
import com.badoo.reaktive.annotations.ExperimentalReaktiveApi
import com.badoo.reaktive.disposable.scope.DisposableScope
import com.badoo.reaktive.observable.map
import com.badoo.reaktive.observable.mapNotNull
import kotlinx.serialization.json.JsonObject

@UseExperimental(ExperimentalReaktiveApi::class)
internal class SyncComponentImpl(dependencies: Dependencies) : SyncComponent, DisposableScope by DisposableScope() {

    private val store: SyncStore =
        SyncStoreFactory(
            factory = dependencies.storeFactory,
            dataSource = SyncDataSourceImpl(),
            mapper = JsonObject::toSyncData,
            database = dependencies.database
        ).create().scope()

    private var binder: Binder? = null

    override fun onViewCreated(view: SyncView) {
        binder =
            bind {
                store.states.map(SyncStore.State::toViewModel) bindTo view
                view.events.mapNotNull(SyncView.Event::toIntent) bindTo store
            }
    }

    override fun onStart() {
        binder?.start()
    }

    override fun onStop() {
        binder?.stop()
    }

    override fun onViewDestroyed() {
        binder = null
    }

    override fun onDestroy() {
        dispose()
    }
}
