package com.arkivanov.konf.shared.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.konf.database.DatabaseDriverFactory
import com.arkivanov.konf.shared.common.dateformat.DateFormat
import com.arkivanov.konf.shared.common.resources.Resources
import com.arkivanov.konf.shared.common.timeformat.TimeFormat

interface RootComponent {

    val model: Model

    interface Model {
        val routerState: Value<RouterState<*, RootChild>>
    }

    interface Dependencies {
        val componentContext: ComponentContext
        val databaseDriverFactory: DatabaseDriverFactory
        val dateFormatProvider: DateFormat.Provider
        val timeFormatProvider: TimeFormat.Provider
        val resources: Resources
        val rootOutput: (Output) -> Unit
    }

    sealed class Output {
        data class SocialAccountSelected(val url: String) : Output()
    }
}
