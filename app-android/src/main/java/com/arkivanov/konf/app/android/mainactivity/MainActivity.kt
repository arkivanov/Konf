package com.arkivanov.konf.app.android.mainactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arkivanov.konf.app.android.utils.app
import com.arkivanov.mvikotlin.core.utils.statekeeper.SimpleStateKeeperContainer
import com.arkivanov.mvikotlin.core.utils.statekeeper.saveAndGet

class MainActivity : AppCompatActivity() {

    private val nonConfigurationStateKeeperContainer = SimpleStateKeeperContainer()

    override fun onCreate(savedInstanceState: Bundle?) {
        @Suppress("UNCHECKED_CAST", "DEPRECATION")
        val fragmentFactory =
            MainFragmentFactory(
                MainFragmentFactoryDependencies(
                    database = app.database,
                    stateKeeperProvider = nonConfigurationStateKeeperContainer.getProvider(
                        savedState = lastCustomNonConfigurationInstance as MutableMap<String, Any>?
                    )
                )
            )

        supportFragmentManager.fragmentFactory = fragmentFactory

        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(android.R.id.content, fragmentFactory.sessionListFragment())
                .commit()
        }
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? =
        nonConfigurationStateKeeperContainer.saveAndGet(HashMap())
}
