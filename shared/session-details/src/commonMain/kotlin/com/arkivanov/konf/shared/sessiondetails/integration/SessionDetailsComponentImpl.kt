package com.arkivanov.konf.shared.sessiondetails.integration

import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent.Dependencies
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent.Output
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsView
import com.arkivanov.konf.shared.sessiondetails.store.SessionDetailsStore
import com.arkivanov.konf.shared.sessiondetails.store.SessionDetailsStoreFactory
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
internal class SessionDetailsComponentImpl(
    dependencies: Dependencies
) : SessionDetailsComponent, DisposableScope by DisposableScope() {

    private val output = PublishSubject<Output>()

    private val store =
        SessionDetailsStoreFactory(
            sessionId = dependencies.sessionId,
            factory = dependencies.storeFactory,
            sessionBundleQueries = dependencies.database.sessionBundleQueries
        ).create().scope()

    private var binder: Binder? = null

    init {
        store.labels.mapNotNull(SessionDetailsStore.Label::toOutput).subscribeScoped(onNext = output::onNext)
    }

    override fun subscribe(observer: ObservableObserver<Output>) {
        output.subscribe(observer)
    }

    override fun onViewCreated(view: SessionDetailsView) {
        binder =
            bind {
                store.states.map(SessionDetailsStore.State::toViewModel) bindTo view
                view.events.mapNotNull(SessionDetailsView.Event::toIntent) bindTo store
                view.events.mapNotNull(SessionDetailsView.Event::toOutput) bindTo output
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
