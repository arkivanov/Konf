package com.arkivanov.konf.shared.sessiondetails.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.konf.shared.common.decompose.asValue
import com.arkivanov.konf.shared.common.decompose.getStore
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent.Dependencies
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent.Events
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent.Model
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent.Output
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsViewModel
import com.arkivanov.konf.shared.sessiondetails.integration.mappers.stateToModel
import com.arkivanov.konf.shared.sessiondetails.store.SessionDetailsStoreFactory

internal class SessionDetailsComponentImpl(
    dependencies: Dependencies
) : SessionDetailsComponent, ComponentContext by dependencies.componentContext, Dependencies by dependencies, Events {

    private val store =
        instanceKeeper.getStore {
            SessionDetailsStoreFactory(
                factory = storeFactory,
                database = SessionDetailsStoreDatabase(
                    sessionId = sessionId,
                    eventQueries = database.eventQueries,
                    sessionBundleQueries = database.sessionBundleQueries
                )
            ).create()
        }

    override val model: Model =
        object : Model, Events by this {
            override val data: Value<SessionDetailsViewModel> =
                store.asValue(stateToModel(dependencies.dateFormatProvider, dependencies.timeFormatProvider))
        }

    override fun onCloseClicked() {
        detailsOutput(Output.Finished)
    }

    override fun onSocialAccountClicked(url: String) {
        detailsOutput(Output.SocialAccountSelected(url = url))
    }
}
