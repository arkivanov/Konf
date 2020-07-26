package com.arkivanov.konf.app.android.sessiondetails

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.arkivanov.konf.app.android.R
import com.arkivanov.konf.app.android.utils.requireNotNull
import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.konf.shared.common.dateformat.DateFormat
import com.arkivanov.konf.shared.common.timeformat.TimeFormat
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent
import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.androidx.lifecycle.asMviLifecycle

class SessionDetailsFragment(
    private val dependencies: Dependencies
) : Fragment(R.layout.session_details) {

    private val sessionDetailsComponent by lazy {
        SessionDetailsComponent(
            object : SessionDetailsComponent.Dependencies, Dependencies by dependencies {
                override val lifecycle: Lifecycle = this@SessionDetailsFragment.lifecycle.asMviLifecycle()
                override val sessionId: String get() = arguments.requireNotNull().getString(ARG_SESSION_ID).requireNotNull()
            }
        )
    }

    fun setArguments(sessionId: String): SessionDetailsFragment =
        apply {
            arguments = bundleOf(ARG_SESSION_ID to sessionId)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewLifecycle = viewLifecycleOwner.lifecycle.asMviLifecycle()
        sessionDetailsComponent.onViewCreated(SessionDetailsViewImpl(root = view), viewLifecycle)
    }

    private companion object {
        private const val ARG_SESSION_ID = "SESSION_ID"
    }

    interface Dependencies {
        val storeFactory: StoreFactory
        val database: KonfDatabase
        val dateFormatProvider: DateFormat.Provider
        val timeFormatProvider: TimeFormat.Provider
        val detailsOutput: (SessionDetailsComponent.Output) -> Unit
    }
}
