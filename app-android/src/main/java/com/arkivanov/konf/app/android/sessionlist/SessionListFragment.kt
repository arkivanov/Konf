package com.arkivanov.konf.app.android.sessionlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.arkivanov.konf.app.android.R
import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.konf.shared.common.timeformat.TimeFormat
import com.arkivanov.konf.shared.sessionlist.SessionListComponent
import com.arkivanov.konf.shared.sync.SyncComponent
import com.arkivanov.mvikotlin.androidxlifecycleinterop.asMviLifecycle
import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle
import com.arkivanov.mvikotlin.core.statekeeper.StateKeeperProvider
import com.arkivanov.mvikotlin.core.store.StoreFactory

class SessionListFragment(
    private val dependencies: Dependencies
) : Fragment(R.layout.session_list) {

    private val mviLifecycle = lifecycle.asMviLifecycle()

    private val syncComponent =
        SyncComponent(
            object : SyncComponent.Dependencies, Dependencies by dependencies {
                override val lifecycle: Lifecycle = mviLifecycle
            }
        )

    private val listComponent =
        SessionListComponent(
            object : SessionListComponent.Dependencies, Dependencies by dependencies {
                override val lifecycle: Lifecycle = mviLifecycle
            }
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewLifecycle = viewLifecycleOwner.lifecycle.asMviLifecycle()
        syncComponent.onViewCreated(SyncViewImpl(root = view), viewLifecycle)
        listComponent.onViewCreated(SessionListViewImpl(root = view), viewLifecycle)
    }

    interface Dependencies {
        val storeFactory: StoreFactory
        val database: KonfDatabase
        val stateKeeperProvider: StateKeeperProvider<Any>?
        val timeFormatProvider: TimeFormat.Provider
        val listOutput: (SessionListComponent.Output) -> Unit
    }
}
