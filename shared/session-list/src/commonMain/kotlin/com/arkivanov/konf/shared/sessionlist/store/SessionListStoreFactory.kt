package com.arkivanov.konf.shared.sessionlist.store

import com.arkivanov.konf.shared.sessionlist.model.EventInfo
import com.arkivanov.konf.shared.sessionlist.model.SessionInfo
import com.arkivanov.konf.shared.sessionlist.store.SessionListStore.State
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor
import com.badoo.reaktive.annotations.EventsOnIoScheduler
import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.observable.combineLatest
import com.badoo.reaktive.observable.observeOn
import com.badoo.reaktive.scheduler.mainScheduler

internal class SessionListStoreFactory(
    private val factory: StoreFactory,
    private val database: Database
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

    private companion object {
        private const val MILLIS_IN_DAY = 86400000L
    }

    private inner class ExecutorImpl : ReaktiveExecutor<Nothing, Unit, State, State.Data, Nothing>() {
        override fun executeAction(action: Unit, getState: () -> State) {
            val database = database

            combineLatest(database.getEvent(), database.getSessions()) { event, sessions ->
                State.Data(
                    event = event,
                    items = mapItems(event = event, sessions = sessions)
                )
            }
                .observeOn(mainScheduler)
                .subscribeScoped(isThreadLocal = true, onNext = ::dispatch)
        }

        private fun mapItems(event: EventInfo, sessions: List<SessionInfo>): List<State.Item> {
            val eventStartDate: Long? = event.startDate?.truncateToDay()
            val eventEndDate: Long? = event.endDate?.truncateToDay()

            return if ((eventStartDate == null) || (eventEndDate == null) || (eventEndDate <= eventStartDate)) {
                sessions.map(State.Item::Session)
            } else {
                val groups = sessions.groupBy { dayNumber(eventDate = eventStartDate, sessionDate = it.startDate?.truncateToDay()) }
                val items = ArrayList<State.Item>()
                groups.forEach { (dayNumber, sessionsOfDay) ->
                    items.add(State.Item.DaySeparator(number = dayNumber))
                    val groupByStartDate = sessionsOfDay.groupBy(SessionInfo::startDate)
                    if (groupByStartDate.size > 1) {
                        groupByStartDate.values.forEach { sessionsOfStartDate ->
                            items.addAll(sessionsOfStartDate.map(State.Item::Session))
                            items.add(State.Item.SessionSeparator)
                        }
                    } else {
                        items.addAll(sessionsOfDay.map(State.Item::Session))
                    }
                }
                items
            }
        }

        private fun dayNumber(eventDate: Long, sessionDate: Long?): Int =
            (((sessionDate ?: eventDate).truncateToDay() - eventDate.truncateToDay()) / MILLIS_IN_DAY).toInt() + 1

        private fun Long.truncateToDay(): Long = (this / MILLIS_IN_DAY) * MILLIS_IN_DAY
    }

    private object ReducerImpl : Reducer<State, State.Data> {
        override fun State.reduce(result: State.Data): State =
            copy(isLoading = false, data = result)
    }

    interface Database {
        @EventsOnIoScheduler
        fun getEvent(): Observable<EventInfo>

        @EventsOnIoScheduler
        fun getSessions(): Observable<List<SessionInfo>>
    }
}
