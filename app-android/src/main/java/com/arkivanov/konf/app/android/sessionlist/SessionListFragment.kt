package com.arkivanov.konf.app.android.sessionlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.arkivanov.konf.app.android.R
import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.konf.shared.sync.SyncComponent
import com.arkivanov.mvikotlin.core.store.StoreFactory

class SessionListFragment(
    private val dependencies: Dependencies
) : Fragment(R.layout.session_list) {

    private val syncComponent =
        SyncComponent(
            object : SyncComponent.Dependencies, Dependencies by dependencies {
            }
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        syncComponent.onViewCreated(SyncViewImpl(root = view))
    }

    override fun onStart() {
        super.onStart()

        syncComponent.onStart()
    }

    override fun onStop() {
        syncComponent.onStop()

        super.onStop()
    }

    override fun onDestroyView() {
        syncComponent.onViewDestroyed()

        super.onDestroyView()
    }

    override fun onDestroy() {
        syncComponent.onDestroy()

        super.onDestroy()
    }

    interface Dependencies {
        val storeFactory: StoreFactory
        val database: KonfDatabase
    }
}
