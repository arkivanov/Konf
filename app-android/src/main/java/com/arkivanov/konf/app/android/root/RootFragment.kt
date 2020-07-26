package com.arkivanov.konf.app.android.root

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.arkivanov.konf.app.android.R
import com.arkivanov.konf.app.android.sessiondetails.SessionDetailsFragment
import com.arkivanov.konf.app.android.sessionlist.SessionListFragment
import com.arkivanov.konf.app.android.utils.setDefaultFragmentAnimations
import com.arkivanov.konf.app.android.utils.transaction
import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.konf.shared.common.dateformat.DateFormat
import com.arkivanov.konf.shared.common.timeformat.TimeFormat
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsComponent
import com.arkivanov.konf.shared.sessionlist.SessionListComponent
import com.arkivanov.mvikotlin.core.statekeeper.StateKeeperProvider
import com.arkivanov.mvikotlin.core.store.StoreFactory

class RootFragment(
    private val dependencies: Dependencies
) : Fragment(R.layout.content) {

    private val fragmentFactory = FragmentFactoryImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        childFragmentManager.fragmentFactory = fragmentFactory

        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            childFragmentManager.transaction {
                add(CONTENT_ID, fragmentFactory.sessionListFragment())
            }
        }
    }

    fun onBackPressed(): Boolean =
        if (childFragmentManager.backStackEntryCount > 0) {
            childFragmentManager.popBackStack()
            true
        } else {
            false
        }

    private fun listOutput(output: SessionListComponent.Output) {
        when (output) {
            is SessionListComponent.Output.Finished -> dependencies.rootOutput(Output.Finished)
            is SessionListComponent.Output.SessionSelected -> openDetails(output.id)
        }.let { }
    }

    private fun detailsOutput(output: SessionDetailsComponent.Output) {
        when (output) {
            is SessionDetailsComponent.Output.Finished -> childFragmentManager.popBackStack()
        }.let {}
    }

    private fun openDetails(sessionId: String) {
        childFragmentManager.transaction {
            setDefaultFragmentAnimations()
            replace(CONTENT_ID, fragmentFactory.sessionDetailsFragment().setArguments(sessionId = sessionId))
            addToBackStack(null)
        }
    }

    private companion object {
        @IdRes
        private const val CONTENT_ID = R.id.content
    }

    interface Dependencies {
        val storeFactory: StoreFactory
        val database: KonfDatabase
        val stateKeeperProvider: StateKeeperProvider<Any>
        val dateFormatProvider: DateFormat.Provider
        val timeFormatProvider: TimeFormat.Provider
        val rootOutput: (Output) -> Unit
    }

    sealed class Output {
        object Finished : Output()
    }

    private inner class FragmentFactoryImpl : FragmentFactory() {
        override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
            when (className) {
                SessionListFragment::class.java.name -> sessionListFragment()
                SessionDetailsFragment::class.java.name -> sessionDetailsFragment()
                else -> super.instantiate(classLoader, className)
            }

        fun sessionListFragment(): SessionListFragment =
            SessionListFragment(
                object : SessionListFragment.Dependencies, Dependencies by dependencies {
                    override val listOutput: (SessionListComponent.Output) -> Unit = ::listOutput
                }
            )

        fun sessionDetailsFragment(): SessionDetailsFragment =
            SessionDetailsFragment(
                object : SessionDetailsFragment.Dependencies, Dependencies by dependencies {
                    override val detailsOutput: (SessionDetailsComponent.Output) -> Unit = ::detailsOutput
                }
            )
    }
}
