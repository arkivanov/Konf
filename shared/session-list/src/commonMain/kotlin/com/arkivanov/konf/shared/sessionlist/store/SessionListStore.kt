package com.arkivanov.konf.shared.sessionlist.store

import com.arkivanov.konf.database.EventEntity
import com.arkivanov.konf.database.SessionBundle
import com.arkivanov.konf.shared.sessionlist.store.SessionListStore.State
import com.arkivanov.mvikotlin.core.store.Store
import com.badoo.reaktive.disposable.Disposable

internal interface SessionListStore : Store<Nothing, State, Nothing>, Disposable {

    data class State(
        val isLoading: Boolean = false,
        val data: Data? = null
    ) {
        data class Data(
            val event: EventEntity?,
            val items: List<Item>
        )

        sealed class Item {
            data class DaySeparator(val number: Int) : Item()
            object SessionSeparator : Item()
            data class Session(val entity: SessionBundle) : Item()
        }
    }
}
