package com.arkivanov.konf.sessiondetails.store

import com.arkivanov.konf.database.KonfDatabaseQueries
import com.arkivanov.konf.database.SessionBundle
import com.arkivanov.konf.database.listenOne
import com.arkivanov.konf.sessiondetails.store.SessionDetailsStore.Intent
import com.arkivanov.konf.sessiondetails.store.SessionDetailsStore.Label
import com.arkivanov.konf.sessiondetails.store.SessionDetailsStore.State
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor
import com.badoo.reaktive.observable.map
import com.badoo.reaktive.observable.observeOn
import com.badoo.reaktive.scheduler.mainScheduler

internal class SessionDetailsStoreFactory(
    private val sessionId: String,
    private val factory: StoreFactory,
    private val databaseQueries: KonfDatabaseQueries
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
        data class Data(val session: SessionBundle?) : Result()
    }

    private inner class ExecutorImpl : ReaktiveExecutor<Intent, Unit, State, Result, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.SelectSpaker -> selectSpeaker(getState())
            }.let {}
        }

        private fun selectSpeaker(state: State) {
            state.session?.speakerId?.also {
                publish(Label.SpeakerSelected(id = it))
            }
        }

        override fun executeAction(action: Unit, getState: () -> State) {
            databaseQueries
                .sessionBundleById(id = sessionId)
                .listenOne()
                .map(Result::Data)
                .observeOn(mainScheduler)
                .subscribeScoped(isThreadLocal = true, onNext = ::dispatch)
        }
    }

    private object ReducerImpl : Reducer<State, Result> {
        override fun State.reduce(result: Result): State =
            when (result) {
                is Result.Data -> copy(isLoading = false, session = result.session)
            }
    }
}
