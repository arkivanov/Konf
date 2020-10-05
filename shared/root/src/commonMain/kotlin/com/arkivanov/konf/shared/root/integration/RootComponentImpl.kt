package com.arkivanov.konf.shared.root.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.child
import com.arkivanov.decompose.router
import com.arkivanov.decompose.statekeeper.Parcelable
import com.arkivanov.decompose.statekeeper.Parcelize
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.konf.database.SessionEntity
import com.arkivanov.konf.shared.root.RootChild
import com.arkivanov.konf.shared.root.RootComponent
import com.arkivanov.konf.shared.root.RootComponent.*
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent
import com.arkivanov.konf.shared.sessionlist.SessionListComponent
import com.arkivanov.konf.shared.sync.SyncComponent
import com.arkivanov.konf.shared.sync.SyncViewModel
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.squareup.sqldelight.EnumColumnAdapter
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent.Output as DetailsOutput
import com.arkivanov.konf.shared.sessionlist.SessionListComponent.Output as ListOutput

internal class RootComponentImpl(
    dependencies: Dependencies
) : RootComponent, ComponentContext by dependencies.componentContext, Dependencies by dependencies {

    private val storeFactory = DefaultStoreFactory

    private val database =
        KonfDatabase(
            databaseDriverFactory(),
            SessionEntity.Adapter(levelAdapter = EnumColumnAdapter())
        )

    private val sync =
        SyncComponent(
            object : SyncComponent.Dependencies {
                override val componentContext: ComponentContext = child(key = "SYNC")
                override val storeFactory: StoreFactory = this@RootComponentImpl.storeFactory
                override val database: KonfDatabase = this@RootComponentImpl.database
            }
        )

    private val router =
        router<Configuration, RootChild>(
            initialConfiguration = Configuration.List,
            handleBackButton = true,
            componentFactory = ::createChild
        )

    override val model: Model =
        object : Model {
            override val routerState: Value<RouterState<*, RootChild>> = router.state
        }

    private fun createChild(configuration: Configuration, componentContext: ComponentContext): RootChild =
        when (configuration) {
            is Configuration.List -> RootChild.List(sessionList(componentContext).model)
            is Configuration.Details -> RootChild.Details(sessionDetails(componentContext, sessionId = configuration.id).model)
        }

    private fun sessionList(componentContext: ComponentContext): SessionListComponent =
        SessionListComponent(
            object : SessionListComponent.Dependencies, Dependencies by this {
                override val componentContext: ComponentContext = componentContext
                override val storeFactory: StoreFactory = this@RootComponentImpl.storeFactory
                override val database: KonfDatabase = this@RootComponentImpl.database
                override val syncStatus: Value<Boolean> = sync.model.data.map(SyncViewModel::isLoading)
                override val listOutput: (ListOutput) -> Unit = ::onListOutput
            }
        )

    private fun sessionDetails(componentContext: ComponentContext, sessionId: String): SessionDetailsComponent =
        SessionDetailsComponent(
            object : SessionDetailsComponent.Dependencies, Dependencies by this {
                override val componentContext: ComponentContext = componentContext
                override val sessionId: String = sessionId
                override val storeFactory: StoreFactory = this@RootComponentImpl.storeFactory
                override val database: KonfDatabase = this@RootComponentImpl.database
                override val detailsOutput: (DetailsOutput) -> Unit = ::onDetailsOutput
            }
        )

    private fun onListOutput(output: ListOutput): Unit =
        when (output) {
            is ListOutput.Finished -> {
                backPressedDispatcher.onBackPressed()
                Unit
            }

            is ListOutput.SessionSelected -> router.push(Configuration.Details(id = output.id))
            is ListOutput.RefreshTriggered -> sync.model.onRefreshTriggered()
        }

    private fun onDetailsOutput(output: DetailsOutput): Unit =
        when (output) {
            is DetailsOutput.Finished -> router.pop()
            is DetailsOutput.SocialAccountSelected -> rootOutput(Output.SocialAccountSelected(url = output.url))
        }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object List : Configuration()

        @Parcelize
        class Details(val id: String) : Configuration()
    }
}
