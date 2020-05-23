package com.arkivanov.konf.app.android.mainactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.arkivanov.konf.app.android.root.RootFragment
import com.arkivanov.konf.app.android.utils.app
import com.arkivanov.konf.app.android.utils.getLocaleCompat
import com.arkivanov.konf.app.android.utils.transaction
import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.konf.shared.common.dateformat.DateFormat
import com.arkivanov.konf.shared.common.dateformat.DateFormatProviderImpl
import com.arkivanov.konf.shared.common.timeformat.TimeFormat
import com.arkivanov.konf.shared.common.timeformat.TimeFormatProviderImpl
import com.arkivanov.mvikotlin.core.statekeeper.SimpleStateKeeperContainer
import com.arkivanov.mvikotlin.core.statekeeper.StateKeeperProvider
import com.arkivanov.mvikotlin.core.statekeeper.saveAndGet
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory

class MainActivity : AppCompatActivity() {

    private val nonConfigurationStateKeeperContainer = SimpleStateKeeperContainer()

    override fun onCreate(savedInstanceState: Bundle?) {
        val fragmentFactory = FragmentFactoryImpl()
        supportFragmentManager.fragmentFactory = fragmentFactory

        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.transaction {
                add(android.R.id.content, fragmentFactory.rootFragment())
            }
        }
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? =
        nonConfigurationStateKeeperContainer.saveAndGet(HashMap())

    override fun onBackPressed() {
        supportFragmentManager
            .fragments
            .forEach {
                if ((it as? RootFragment)?.onBackPressed() == true) {
                    return
                }
            }

        super.onBackPressed()
    }

    private fun rootOutput(output: RootFragment.Output) {
        when (output) {
            is RootFragment.Output.Finished -> finish()
        }
    }

    private inner class FragmentFactoryImpl : FragmentFactory() {
        override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
            when (className) {
                RootFragment::class.java.name -> rootFragment()
                else -> super.instantiate(classLoader, className)
            }

        fun rootFragment(): RootFragment =
            RootFragment(
                object : RootFragment.Dependencies {
                    override val storeFactory: StoreFactory get() = DefaultStoreFactory
                    override val database: KonfDatabase get() = app.database

                    @Suppress("UNCHECKED_CAST")
                    override val stateKeeperProvider: StateKeeperProvider<Any> =
                        nonConfigurationStateKeeperContainer.getProvider(
                            savedState = lastCustomNonConfigurationInstance as MutableMap<String, Any>?
                        )

                    override val dateFormatProvider: DateFormat.Provider = DateFormatProviderImpl(resources.configuration.getLocaleCompat())
                    override val timeFormatProvider: TimeFormat.Provider = TimeFormatProviderImpl(resources.configuration.getLocaleCompat())
                    override val rootOutput: (RootFragment.Output) -> Unit = ::rootOutput
                }
            )
    }
}
