package com.arkivanov.konf.app.android.mainactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arkivanov.konf.app.android.utils.app
import com.arkivanov.konf.app.android.utils.getLocaleCompat
import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.konf.shared.common.dateformat.DateFormat
import com.arkivanov.konf.shared.common.dateformat.DateFormatProviderImpl
import com.arkivanov.konf.shared.common.timeformat.TimeFormat
import com.arkivanov.konf.shared.common.timeformat.TimeFormatProviderImpl
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.statekeeper.SimpleStateKeeperContainer
import com.arkivanov.mvikotlin.core.utils.statekeeper.StateKeeperProvider
import com.arkivanov.mvikotlin.core.utils.statekeeper.saveAndGet
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory

class MainActivity : AppCompatActivity() {

    private val nonConfigurationStateKeeperContainer = SimpleStateKeeperContainer()
    private val fragmentFactory by lazy { MainFragmentFactory(MainFragmentFactoryDependencies()) }

    private val router by lazy {
        MainFragmentRouter(
            fragmentManager = supportFragmentManager,
            fragmentFactory = fragmentFactory,
            contentId = android.R.id.content
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = fragmentFactory

        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            router.openSessionList()
        }
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? =
        nonConfigurationStateKeeperContainer.saveAndGet(HashMap())

    private inner class MainFragmentFactoryDependencies : MainFragmentFactory.Dependencies {
        override val storeFactory: StoreFactory = DefaultStoreFactory
        override val database: KonfDatabase = app.database

        @Suppress("UNCHECKED_CAST")
        override val stateKeeperProvider: StateKeeperProvider<Any> =
            nonConfigurationStateKeeperContainer.getProvider(
                savedState = lastCustomNonConfigurationInstance as MutableMap<String, Any>?
            )

        override val dateFormatProvider: DateFormat.Provider = DateFormatProviderImpl(resources.configuration.getLocaleCompat())
        override val timeFormatProvider: TimeFormat.Provider = TimeFormatProviderImpl(resources.configuration.getLocaleCompat())
        override val onSessionSelected: (id: String) -> Unit = { router.openSessionDetails(it) }
    }
}
