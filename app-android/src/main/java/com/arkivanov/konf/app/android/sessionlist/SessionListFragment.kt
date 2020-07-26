package com.arkivanov.konf.app.android.sessionlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.arkivanov.konf.app.android.R
import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.konf.shared.common.timeformat.TimeFormat
import com.arkivanov.konf.shared.sessionlist.SessionListComponent
import com.arkivanov.konf.shared.sync.SyncComponent
import com.arkivanov.mvikotlin.core.instancekeeper.InstanceKeeperProvider
import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.androidx.instancekeeper.getInstanceKeeperProvider
import com.arkivanov.mvikotlin.extensions.androidx.lifecycle.asMviLifecycle

class SessionListFragment(
    private val dependencies: Dependencies
) : Fragment(R.layout.session_list) {

    private val mviLifecycle = lifecycle.asMviLifecycle()
    private lateinit var syncComponent: SyncComponent
    private lateinit var listComponent: SessionListComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        syncComponent =
            SyncComponent(
                object : SyncComponent.Dependencies, Dependencies by dependencies {
                    override val lifecycle: Lifecycle = mviLifecycle
                }
            )

        listComponent =
            SessionListComponent(
                object : SessionListComponent.Dependencies, Dependencies by dependencies {
                    override val lifecycle: Lifecycle = mviLifecycle
                    override val instanceKeeperProvider: InstanceKeeperProvider = getInstanceKeeperProvider()
                }
            )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewLifecycle = viewLifecycleOwner.lifecycle.asMviLifecycle()
        syncComponent.onViewCreated(SyncViewImpl(root = view), viewLifecycle)
        listComponent.onViewCreated(SessionListViewImpl(root = view), viewLifecycle)
    }

    interface Dependencies {
        val storeFactory: StoreFactory
        val database: KonfDatabase
        val timeFormatProvider: TimeFormat.Provider
        val listOutput: (SessionListComponent.Output) -> Unit
    }
}
