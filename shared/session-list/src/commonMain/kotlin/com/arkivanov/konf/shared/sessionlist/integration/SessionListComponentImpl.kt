package com.arkivanov.konf.shared.sessionlist.integration

import com.arkivanov.konf.shared.sessionlist.SessionListComponent
import com.arkivanov.konf.shared.sessionlist.SessionListComponent.Dependencies
import com.arkivanov.konf.shared.sessionlist.SessionListView
import com.arkivanov.konf.shared.sessionlist.store.SessionListStoreFactory
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle
import com.arkivanov.mvikotlin.core.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.extensions.reaktive.bind
import com.arkivanov.mvikotlin.extensions.reaktive.events
import com.arkivanov.mvikotlin.extensions.reaktive.states
import com.badoo.reaktive.annotations.ExperimentalReaktiveApi
import com.badoo.reaktive.observable.map
import com.badoo.reaktive.observable.mapNotNull

@UseExperimental(ExperimentalReaktiveApi::class)
internal class SessionListComponentImpl(
    private val dependencies: Dependencies
) : SessionListComponent {

    private val store =
        SessionListStoreFactory(
            factory = dependencies.storeFactory,
            eventQueries = dependencies.database.eventQueries,
            sessionBundleQueries = dependencies.database.sessionBundleQueries
        )
            .create()

    init {
        dependencies.lifecycle.doOnDestroy(store::dispose)
    }

    override fun onViewCreated(view: SessionListView, output: (SessionListComponent.Output) -> Unit, viewLifecycle: Lifecycle) {
        bind(viewLifecycle, BinderLifecycleMode.CREATE_DESTROY) {
            view.events.mapNotNull(SessionListView.Event::toOutput) bindTo output
        }

        bind(viewLifecycle, BinderLifecycleMode.START_STOP) {
            store.states.map { it.toViewModel(timeFormatProvider = dependencies.timeFormatProvider) } bindTo view
        }
    }
}
