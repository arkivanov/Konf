package com.arkivanov.konf.shared.sessiondetails.integration

import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent.Dependencies
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsView
import com.arkivanov.konf.shared.sessiondetails.store.SessionDetailsStore
import com.arkivanov.konf.shared.sessiondetails.store.SessionDetailsStoreFactory
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle
import com.arkivanov.mvikotlin.core.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.extensions.reaktive.bind
import com.arkivanov.mvikotlin.extensions.reaktive.events
import com.arkivanov.mvikotlin.extensions.reaktive.labels
import com.arkivanov.mvikotlin.extensions.reaktive.states
import com.badoo.reaktive.observable.map
import com.badoo.reaktive.observable.mapNotNull

internal class SessionDetailsComponentImpl(
    private val dependencies: Dependencies
) : SessionDetailsComponent {

    private val store =
        SessionDetailsStoreFactory(
            sessionId = dependencies.sessionId,
            factory = dependencies.storeFactory,
            eventQueries = dependencies.database.eventQueries,
            sessionBundleQueries = dependencies.database.sessionBundleQueries
        ).create()

    init {
        dependencies.lifecycle.doOnDestroy(store::dispose)
    }

    override fun onViewCreated(view: SessionDetailsView, viewLifecycle: Lifecycle) {
        bind(viewLifecycle, BinderLifecycleMode.CREATE_DESTROY) {
            view.events.mapNotNull(SessionDetailsView.Event::toIntent) bindTo store
        }

        bind(viewLifecycle, BinderLifecycleMode.START_STOP) {
            store.labels.mapNotNull(SessionDetailsStore.Label::toOutput) bindTo dependencies.detailsOutput
            view.events.mapNotNull(SessionDetailsView.Event::toOutput) bindTo dependencies.detailsOutput
            store.states.map { it.toViewModel(dependencies.dateFormatProvider, dependencies.timeFormatProvider) } bindTo view
        }
    }
}
