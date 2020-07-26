package com.arkivanov.konf.shared.sessiondetails.integration

import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent.Dependencies
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsView
import com.arkivanov.konf.shared.sessiondetails.integration.mappers.eventToIntent
import com.arkivanov.konf.shared.sessiondetails.integration.mappers.eventToOutput
import com.arkivanov.konf.shared.sessiondetails.integration.mappers.stateToModel
import com.arkivanov.konf.shared.sessiondetails.store.SessionDetailsStoreFactory
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle
import com.arkivanov.mvikotlin.core.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.extensions.reaktive.bind
import com.arkivanov.mvikotlin.extensions.reaktive.events
import com.arkivanov.mvikotlin.extensions.reaktive.states
import com.badoo.reaktive.observable.map
import com.badoo.reaktive.observable.mapNotNull

internal class SessionDetailsComponentImpl(
    private val dependencies: Dependencies
) : SessionDetailsComponent {

    private val store =
        SessionDetailsStoreFactory(
            factory = dependencies.storeFactory,
            database = SessionDetailsStoreDatabase(
                sessionId = dependencies.sessionId,
                eventQueries = dependencies.database.eventQueries,
                sessionBundleQueries = dependencies.database.sessionBundleQueries
            )
        ).create()

    init {
        dependencies.lifecycle.doOnDestroy(store::dispose)
    }

    override fun onViewCreated(view: SessionDetailsView, viewLifecycle: Lifecycle) {
        bind(viewLifecycle, BinderLifecycleMode.CREATE_DESTROY) {
            view.events.mapNotNull(eventToIntent) bindTo store
        }

        bind(viewLifecycle, BinderLifecycleMode.START_STOP) {
            view.events.mapNotNull(eventToOutput) bindTo dependencies.detailsOutput
            store.states.map(stateToModel(dependencies.dateFormatProvider, dependencies.timeFormatProvider)) bindTo view
        }
    }
}
