package com.arkivanov.konf.shared.sessionlist.integration

import com.arkivanov.konf.shared.sessionlist.SessionListComponent
import com.arkivanov.konf.shared.sessionlist.SessionListComponent.Dependencies
import com.arkivanov.konf.shared.sessionlist.SessionListView
import com.arkivanov.konf.shared.sessionlist.store.SessionListStore
import com.arkivanov.konf.shared.sessionlist.store.SessionListStoreFactory
import com.arkivanov.mvikotlin.core.binder.Binder
import com.arkivanov.mvikotlin.extensions.reaktive.bind
import com.arkivanov.mvikotlin.extensions.reaktive.events
import com.arkivanov.mvikotlin.extensions.reaktive.states
import com.badoo.reaktive.annotations.ExperimentalReaktiveApi
import com.badoo.reaktive.disposable.scope.DisposableScope
import com.badoo.reaktive.observable.map
import com.badoo.reaktive.observable.mapNotNull

@UseExperimental(ExperimentalReaktiveApi::class)
internal class SessionListComponentImpl(
    private val dependencies: Dependencies
) : SessionListComponent, DisposableScope by DisposableScope() {

    private val store =
        SessionListStoreFactory(
            factory = dependencies.storeFactory,
            eventQueries = dependencies.database.eventQueries,
            sessionBundleQueries = dependencies.database.sessionBundleQueries
        ).create().scope()

    private var binder: Binder? = null

    override fun onViewCreated(view: SessionListView) {
        binder =
            bind {
                store.states.map(SessionListStore.State::toViewModel) bindTo view
                view.events.mapNotNull(SessionListView.Event::toOutput) bindTo dependencies.output
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
