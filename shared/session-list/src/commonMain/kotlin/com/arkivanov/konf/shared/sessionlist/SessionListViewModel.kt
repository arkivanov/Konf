package com.arkivanov.konf.shared.sessionlist

data class SessionListViewModel(
    val isLoading: Boolean,
    val items: List<Item>
) {
    sealed class Item {
        data class DaySeparator(val text: String) : Item()
        object SessionSeparator : Item()

        data class Session(
            val id: String,
            val title: String?,
            val info: String,
            val speakerInfo: String
        ) : Item()
    }
}
