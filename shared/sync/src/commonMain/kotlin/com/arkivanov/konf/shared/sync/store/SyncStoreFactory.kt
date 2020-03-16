package com.arkivanov.konf.shared.sync.store

import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.konf.database.oneOrEmpty
import com.arkivanov.konf.shared.sync.datasource.SyncDataSource
import com.arkivanov.konf.shared.sync.store.SyncStore.Intent
import com.arkivanov.konf.shared.sync.store.SyncStore.State
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor
import com.badoo.reaktive.maybe.doOnBeforeSuccess
import com.badoo.reaktive.maybe.doOnBeforeTerminate
import com.badoo.reaktive.maybe.map
import com.badoo.reaktive.maybe.observeOn
import com.badoo.reaktive.maybe.threadLocal
import com.badoo.reaktive.scheduler.computationScheduler
import com.badoo.reaktive.scheduler.ioScheduler
import com.badoo.reaktive.scheduler.mainScheduler
import com.badoo.reaktive.utils.ensureNeverFrozen

internal class SyncStoreFactory<T>(
    private val factory: StoreFactory,
    private val dataSource: SyncDataSource<T>,
    private val mapper: (T) -> SyncData,
    private val database: KonfDatabase
) {

    fun create(): SyncStore =
        object : SyncStore, Store<Intent, State, Nothing> by factory.create(
            name = "SyncStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {
        }

    private sealed class Result {
        object SyncStarted : Result()
        object SyncFinished : Result()
    }

    private inner class ExecutorImpl : ReaktiveExecutor<Intent, Unit, State, Result, Nothing>() {
        init {
            ensureNeverFrozen()
        }

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.StartSync -> startSync(getState())
            }.let {}
        }

        override fun executeAction(action: Unit, getState: () -> State) {
            database
                .eventQueries
                .get()
                .oneOrEmpty()
                .observeOn(mainScheduler)
                .subscribeScoped(isThreadLocal = true, onComplete = ::sync)
        }

        private fun startSync(state: State) {
            if (!state.isLoading) {
                sync()
            }
        }

        private fun sync() {
            dispatch(Result.SyncStarted)

            val database = database

            dataSource
                .load()
                .observeOn(computationScheduler)
                .map(mapper)
                .observeOn(ioScheduler)
                .doOnBeforeSuccess { saveData(it, database) }
                .observeOn(mainScheduler)
                .threadLocal()
                .doOnBeforeTerminate { dispatch(Result.SyncFinished) }
                .subscribeScoped()
        }

        private fun saveData(data: SyncData, database: KonfDatabase) {
            database.transaction {
                database.eventQueries.apply {
                    clear()
                    put(data.event)
                }

                database.companyQueries.apply {
                    clear()
                    data.companies.forEach(::put)
                }

                database.speakerQueries.apply {
                    clear()
                    data.speakers.forEach(::put)
                }

                database.roomQueries.apply {
                    clear()
                    data.rooms.forEach(::put)
                }

                database.sessionQueries.apply {
                    clear()
                    data.sessions.forEach(::put)
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Result> {
        override fun State.reduce(result: Result): State =
            when (result) {
                is Result.SyncStarted -> copy(isLoading = true)
                is Result.SyncFinished -> copy(isLoading = false)
            }
    }
}
