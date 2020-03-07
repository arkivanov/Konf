package com.arkivanov.konf.sessionlist.integration

import com.arkivanov.konf.sessionlist.SessionListComponent
import com.arkivanov.konf.sessionlist.SessionListComponent.Dependencies
import com.arkivanov.konf.sessionlist.SessionListView
import com.arkivanov.konf.sessionlist.store.SessionListStoreFactory
import com.arkivanov.mvikotlin.core.binder.Binder
import com.arkivanov.mvikotlin.extensions.reaktive.bind
import com.arkivanov.mvikotlin.extensions.reaktive.states
import com.badoo.reaktive.annotations.ExperimentalReaktiveApi
import com.badoo.reaktive.disposable.scope.DisposableScope
import com.badoo.reaktive.observable.map

@UseExperimental(ExperimentalReaktiveApi::class)
internal class SessionListComponentImpl(
    dependencies: Dependencies
) : SessionListComponent, DisposableScope by DisposableScope() {

    private val store =
        SessionListStoreFactory(
            factory = dependencies.storeFactory,
            databaseQueries = dependencies.databaseQueries
        ).create().scope()

    private var binder: Binder? = null

    override fun onViewCreated(view: SessionListView) {
        binder =
            bind {
                store.states.map { it.toViewModel() } bindTo view::render
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
