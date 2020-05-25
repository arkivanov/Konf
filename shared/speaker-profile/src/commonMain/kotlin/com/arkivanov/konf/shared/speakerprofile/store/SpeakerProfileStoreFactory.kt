package com.arkivanov.konf.shared.speakerprofile.store

import com.arkivanov.konf.shared.speakerprofile.model.SpeakerInfo
import com.arkivanov.konf.shared.speakerprofile.store.SpeakerProfileStore.State
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

internal class SpeakerProfileStoreFactory(
    private val factory: StoreFactory,
    private val database: Database
) {

    fun create(): SpeakerProfileStore =
        object : SpeakerProfileStore, Store<Nothing, State, Nothing> by factory.create(
            name = "SpeakerProfileStore",
            initialState = State(isLoading = true),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {
        }

    private class Result(val speaker: SpeakerInfo?)

    private inner class ExecutorImpl : ReaktiveExecutor<Nothing, Unit, State, Result, Nothing>() {
        override fun executeAction(action: Unit, getState: () -> State) {
            database
                .getSpeaker()
                .map(::Result)
                .observeOn(mainScheduler)
                .subscribeScoped(isThreadLocal = true, onNext = ::dispatch)
        }
    }

    private object ReducerImpl : Reducer<State, Result> {
        override fun State.reduce(result: Result): State =
            copy(isLoading = false, speaker = result.speaker)
    }

    interface Database {
        @EventsOnIoScheduler
        fun getSpeaker(): Observable<SpeakerInfo?>
    }
}
