package com.arkivanov.konf.shared.sessiondetails.store

import com.arkivanov.konf.database.EventEntity
import com.arkivanov.konf.database.EventQueries
import com.arkivanov.konf.database.SessionBundle
import com.arkivanov.konf.database.SessionBundleQueries
import com.arkivanov.konf.database.listenOne
import com.arkivanov.konf.shared.sessiondetails.store.SessionDetailsStore.Intent
import com.arkivanov.konf.shared.sessiondetails.store.SessionDetailsStore.Label
import com.arkivanov.konf.shared.sessiondetails.store.SessionDetailsStore.State
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor
import com.badoo.reaktive.observable.combineLatest
import com.badoo.reaktive.observable.observeOn
import com.badoo.reaktive.scheduler.mainScheduler

internal class SessionDetailsStoreFactory(
    private val sessionId: String,
    private val factory: StoreFactory,
    private val eventQueries: EventQueries,
    private val sessionBundleQueries: SessionBundleQueries
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

    private sealed class Result {
        data class Data(val event: EventEntity?, val session: SessionBundle?) : Result()
    }

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
            combineLatest(
                eventQueries.get().listenOne(),
                sessionBundleQueries.getById(id = sessionId).listenOne(),
                Result::Data
            )
                .observeOn(mainScheduler)
                .subscribeScoped(isThreadLocal = true, onNext = ::dispatch)
        }
    }

    private object ReducerImpl : Reducer<State, Result> {
        override fun State.reduce(result: Result): State =
            when (result) {
                is Result.Data -> copy(isLoading = false, event = result.event, session = result.session)
            }
    }
}
