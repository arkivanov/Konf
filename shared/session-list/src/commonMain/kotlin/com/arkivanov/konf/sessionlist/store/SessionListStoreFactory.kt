package com.arkivanov.konf.sessionlist.store

import com.arkivanov.konf.database.KonfDatabaseQueries
import com.arkivanov.konf.database.SessionBundle
import com.arkivanov.konf.database.listenList
import com.arkivanov.konf.sessionlist.store.SessionListStore.State
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor
import com.badoo.reaktive.observable.map
import com.badoo.reaktive.observable.observeOn
import com.badoo.reaktive.scheduler.mainScheduler

internal class SessionListStoreFactory(
    private val factory: StoreFactory,
    private val databaseQueries: KonfDatabaseQueries
) {

    fun create(): SessionListStore =
        object : SessionListStore, Store<Nothing, State, Nothing> by factory.create(
            name = "SpeakerProfileStore",
            initialState = State(isLoading = true),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {
        }

    private sealed class Result {
        data class Data(val sessions: List<SessionBundle>) : Result()
    }

    private inner class ExecutorImpl : ReaktiveExecutor<Nothing, Unit, State, Result, Nothing>() {
        override fun executeAction(action: Unit, getState: () -> State) {
            databaseQueries
                .sessionBundle()
                .listenList()
                .map(Result::Data)
                .observeOn(mainScheduler)
                .subscribeScoped(isThreadLocal = true, onNext = ::dispatch)
        }
    }

    private object ReducerImpl : Reducer<State, Result> {
        override fun State.reduce(result: Result): State =
            when (result) {
                is Result.Data -> copy(isLoading = false, sessions = result.sessions)
            }
    }
}
