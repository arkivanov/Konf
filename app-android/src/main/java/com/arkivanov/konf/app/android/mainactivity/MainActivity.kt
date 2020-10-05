package com.arkivanov.konf.app.android.mainactivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.backpressed.toBackPressedDispatched
import com.arkivanov.decompose.instancekeeper.toInstanceKeeper
import com.arkivanov.decompose.lifecycle.asDecomposeLifecycle
import com.arkivanov.decompose.statekeeper.toStateKeeper
import com.arkivanov.konf.app.android.utils.getLocaleCompat
import com.arkivanov.konf.database.DatabaseDriverFactory
import com.arkivanov.konf.shared.common.dateformat.DateFormat
import com.arkivanov.konf.shared.common.dateformat.DateFormatProviderImpl
import com.arkivanov.konf.shared.common.resources.Resources
import com.arkivanov.konf.shared.common.timeformat.TimeFormat
import com.arkivanov.konf.shared.common.timeformat.TimeFormatProviderImpl
import com.arkivanov.konf.shared.common.ui.DefaultViewContext
import com.arkivanov.konf.shared.root.RootComponent
import com.arkivanov.konf.shared.root.RootView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lifecycle = lifecycle.asDecomposeLifecycle()

        val componentContext =
            DefaultComponentContext(
                lifecycle = lifecycle,
                stateKeeper = savedStateRegistry.toStateKeeper(),
                instanceKeeper = viewModelStore.toInstanceKeeper(),
                backPressedDispatcher = onBackPressedDispatcher.toBackPressedDispatched(lifecycle)
            )

        val root =
            RootComponent(
                object : RootComponent.Dependencies {
                    override val componentContext: ComponentContext = componentContext
                    override val databaseDriverFactory: DatabaseDriverFactory = DatabaseDriverFactory(this@MainActivity)
                    override val dateFormatProvider: DateFormat.Provider =
                        DateFormatProviderImpl(this@MainActivity.resources.configuration.getLocaleCompat())
                    override val timeFormatProvider: TimeFormat.Provider =
                        TimeFormatProviderImpl(this@MainActivity.resources.configuration.getLocaleCompat())
                    override val resources: Resources = ResourcesImpl(this@MainActivity)
                    override val rootOutput: (RootComponent.Output) -> Unit = ::onRootOutput
                }
            )

        DefaultViewContext(parent = findViewById(android.R.id.content), lifecycle = lifecycle).apply {
            parent.addView(RootView(root.model))
        }
    }

    private fun onRootOutput(output: RootComponent.Output): Unit =
        when (output) {
            is RootComponent.Output.SocialAccountSelected -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(output.url)))
        }
}
