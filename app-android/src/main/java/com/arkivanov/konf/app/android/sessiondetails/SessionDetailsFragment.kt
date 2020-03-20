package com.arkivanov.konf.app.android.sessiondetails

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.arkivanov.konf.app.android.R
import com.arkivanov.konf.app.android.utils.requireNotNull
import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent
import com.arkivanov.mvikotlin.core.store.StoreFactory

class SessionDetailsFragment(
    private val dependencies: Dependencies
) : Fragment(R.layout.session_details) {

    private val sessionDetailsComponent by lazy {
        SessionDetailsComponent(
            object : SessionDetailsComponent.Dependencies, Dependencies by dependencies {
                override val sessionId: String get() = arguments.requireNotNull().getString(ARG_SESSION_ID).requireNotNull()
                override val output: (SessionDetailsComponent.Output) -> Unit = ::onSessionDetailsComponentOutput
            }
        )
    }

    private fun onSessionDetailsComponentOutput(output: SessionDetailsComponent.Output) {
    }

    fun setArguments(sessionId: String): SessionDetailsFragment =
        apply {
            arguments = bundleOf(ARG_SESSION_ID to sessionId)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionDetailsComponent.onViewCreated(SessionDetailsViewImpl(root = view))
    }

    override fun onStart() {
        super.onStart()

        sessionDetailsComponent.onStart()
    }

    override fun onStop() {
        sessionDetailsComponent.onStop()

        super.onStop()
    }

    override fun onDestroyView() {
        sessionDetailsComponent.onViewDestroyed()

        super.onDestroyView()
    }

    override fun onDestroy() {
        sessionDetailsComponent.onDestroy()

        super.onDestroy()
    }

    private companion object {
        private const val ARG_SESSION_ID = "SESSION_ID"
    }

    interface Dependencies {
        val storeFactory: StoreFactory
        val database: KonfDatabase
    }
}
