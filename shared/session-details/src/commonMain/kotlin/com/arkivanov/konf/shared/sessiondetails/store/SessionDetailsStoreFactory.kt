package com.arkivanov.konf.shared.sessiondetails.store

import com.arkivanov.konf.shared.sessiondetails.model.SessionInfo
import com.arkivanov.konf.shared.sessiondetails.store.SessionDetailsStore.Intent
import com.arkivanov.konf.shared.sessiondetails.store.SessionDetailsStore.Label
import com.arkivanov.konf.shared.sessiondetails.store.SessionDetailsStore.State
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor
import com.badoo.reaktive.annotations.EventsOnIoScheduler
import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.observable.map
import com.badoo.reaktive.observable.observeOn
import com.badoo.reaktive.scheduler.mainScheduler

internal class SessionDetailsStoreFactory(
    private val factory: StoreFactory,
    private val database: Database
) {

    fun create(): SessionDetailsStore =
        object : SessionDetailsStore, Store<Intent, State, Label> by factory.create(
            name = "SpeakerProfileStore",
            initialState = State(isLoading = true),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {
        }

    private class Result(val session: SessionInfo?)

    private inner class ExecutorImpl : ReaktiveExecutor<Intent, Unit, State, Result, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.SelectSpeaker -> selectSpeaker(getState())
            }.let {}
        }

        private fun selectSpeaker(state: State) {
            state.session?.speakerId?.also {
                publish(Label.SpeakerSelected(id = it))
            }
        }

        override fun executeAction(action: Unit, getState: () -> State) {
            database
                .getSession()
                .map(::Result)
                .observeOn(mainScheduler)
                .subscribeScoped(isThreadLocal = true, onNext = ::dispatch)
        }
    }

    private object ReducerImpl : Reducer<State, Result> {
        override fun State.reduce(result: Result): State =
            copy(isLoading = false, session = result.session)
    }

    interface Database {
        @EventsOnIoScheduler
        fun getSession(): Observable<SessionInfo?>
    }
}
