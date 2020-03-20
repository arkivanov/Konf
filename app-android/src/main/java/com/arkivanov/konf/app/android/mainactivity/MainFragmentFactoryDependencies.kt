package com.arkivanov.konf.app.android.mainactivity

import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.statekeeper.StateKeeperProvider
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory

class MainFragmentFactoryDependencies(
    override val database: KonfDatabase,
    override val stateKeeperProvider: StateKeeperProvider<Any>,
    fragmentRouter: MainFragmentRouter
) : MainFragmentFactory.Dependencies {

    override val storeFactory: StoreFactory = DefaultStoreFactory

    override val onSessionSelected: (id: String) -> Unit = fragmentRouter::openSessionDetails
}
