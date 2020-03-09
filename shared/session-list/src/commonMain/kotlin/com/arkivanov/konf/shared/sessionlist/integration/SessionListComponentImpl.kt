package com.arkivanov.konf.shared.sessionlist.integration

import com.arkivanov.konf.shared.sessionlist.SessionListComponent
import com.arkivanov.konf.shared.sessionlist.SessionListComponent.Dependencies
import com.arkivanov.konf.shared.sessionlist.SessionListComponent.Output
import com.arkivanov.konf.shared.sessionlist.SessionListView
import com.arkivanov.konf.shared.sessionlist.store.SessionListStore
import com.arkivanov.konf.shared.sessionlist.store.SessionListStoreFactory
import com.arkivanov.mvikotlin.core.binder.Binder
import com.arkivanov.mvikotlin.extensions.reaktive.bind
import com.arkivanov.mvikotlin.extensions.reaktive.events
import com.arkivanov.mvikotlin.extensions.reaktive.labels
import com.arkivanov.mvikotlin.extensions.reaktive.states
import com.badoo.reaktive.annotations.ExperimentalReaktiveApi
import com.badoo.reaktive.disposable.scope.DisposableScope
import com.badoo.reaktive.observable.ObservableObserver
import com.badoo.reaktive.observable.map
import com.badoo.reaktive.observable.mapNotNull
import com.badoo.reaktive.subject.publish.PublishSubject

@UseExperimental(ExperimentalReaktiveApi::class)
internal class SessionListComponentImpl(
    dependencies: Dependencies
) : SessionListComponent, DisposableScope by DisposableScope() {

    private val output = PublishSubject<Output>()

    private val store =
        SessionListStoreFactory(
            factory = dependencies.storeFactory,
            databaseQueries = dependencies.databaseQueries
        ).create().scope()

    private var binder: Binder? = null

    init {
        store.labels.mapNotNull(SessionListStore.Label::toOutput).subscribeScoped(onNext = output::onNext)
    }

    override fun subscribe(observer: ObservableObserver<Output>) {
        output.subscribe(observer)
    }

    override fun onViewCreated(view: SessionListView) {
        binder =
            bind {
                store.states.map(SessionListStore.State::toViewModel) bindTo view
                view.events.mapNotNull(SessionListView.Event::toIntent) bindTo store
                view.events.mapNotNull(SessionListView.Event::toOutput) bindTo output
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
