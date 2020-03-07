package com.arkivanov.konf.sessiondetails.integration

import com.arkivanov.konf.sessiondetails.SessionDetailsComponent
import com.arkivanov.konf.sessiondetails.SessionDetailsComponent.Dependencies
import com.arkivanov.konf.sessiondetails.SessionDetailsView
import com.arkivanov.konf.sessiondetails.store.SessionDetailsStoreFactory
import com.arkivanov.mvikotlin.core.binder.Binder
import com.arkivanov.mvikotlin.extensions.reaktive.bind
import com.arkivanov.mvikotlin.extensions.reaktive.states
import com.badoo.reaktive.annotations.ExperimentalReaktiveApi
import com.badoo.reaktive.disposable.scope.DisposableScope
import com.badoo.reaktive.observable.map

@UseExperimental(ExperimentalReaktiveApi::class)
internal class SessionDetailsComponentImpl(
    dependencies: Dependencies
) : SessionDetailsComponent, DisposableScope by DisposableScope() {

    private val store =
        SessionDetailsStoreFactory(
            sessionId = dependencies.sessionId,
            factory = dependencies.storeFactory,
            databaseQueries = dependencies.databaseQueries
        ).create().scope()

    private var binder: Binder? = null

    override fun onViewCreated(view: SessionDetailsView) {
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
