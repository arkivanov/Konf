package com.arkivanov.konf.shared.sessionlist.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.konf.shared.common.decompose.asValue
import com.arkivanov.konf.shared.common.decompose.getStore
import com.arkivanov.konf.shared.sessionlist.SessionListComponent
import com.arkivanov.konf.shared.sessionlist.SessionListComponent.Dependencies
import com.arkivanov.konf.shared.sessionlist.SessionListComponent.Events
import com.arkivanov.konf.shared.sessionlist.SessionListComponent.Model
import com.arkivanov.konf.shared.sessionlist.SessionListComponent.Output
import com.arkivanov.konf.shared.sessionlist.SessionListViewModel
import com.arkivanov.konf.shared.sessionlist.integration.mappers.stateToModel
import com.arkivanov.konf.shared.sessionlist.store.SessionListStoreFactory

internal class SessionListComponentImpl(
    dependencies: Dependencies
) : SessionListComponent, ComponentContext by dependencies.componentContext, Dependencies by dependencies, Events {

    private val store =
        instanceKeeper.getStore {
            SessionListStoreFactory(
                factory = storeFactory,
                database = SessionListStoreDatabase(database.eventQueries, database.sessionBundleQueries)
            ).create()
        }

    override val model: Model =
        object : Model, Events by this {
            override val data: Value<SessionListViewModel> = store.asValue(stateToModel(timeFormatProvider, resources))
            override val syncStatus: Value<Boolean> = this@SessionListComponentImpl.syncStatus
        }

    override fun onCloseClicked() {
        listOutput(Output.Finished)
    }

    override fun onSessionClicked(id: String) {
        listOutput(Output.SessionSelected(id = id))
    }

    override fun onRefreshTriggered() {
        listOutput(Output.RefreshTriggered)
    }
}
