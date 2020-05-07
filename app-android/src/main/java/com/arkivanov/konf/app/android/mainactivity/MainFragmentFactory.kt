package com.arkivanov.konf.app.android.mainactivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.arkivanov.konf.app.android.sessiondetails.SessionDetailsFragment
import com.arkivanov.konf.app.android.sessionlist.SessionListFragment
import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.konf.shared.common.dateformat.DateFormat
import com.arkivanov.konf.shared.common.timeformat.TimeFormat
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.statekeeper.StateKeeperProvider

class MainFragmentFactory(
    private val dependencies: Dependencies
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        when (className) {
            SessionListFragment::class.java.name -> sessionListFragment()
            SessionDetailsFragment::class.java.name -> sessionDetailsFragment()
            else -> super.instantiate(classLoader, className)
        }

    fun sessionListFragment(): SessionListFragment =
        SessionListFragment(
            object : SessionListFragment.Dependencies, Dependencies by dependencies {
            }
        )

    fun sessionDetailsFragment(): SessionDetailsFragment =
        SessionDetailsFragment(
            object : SessionDetailsFragment.Dependencies, Dependencies by dependencies {
            }
        )

    interface Dependencies {
        val storeFactory: StoreFactory
        val database: KonfDatabase
        val stateKeeperProvider: StateKeeperProvider<Any>
        val dateFormatProvider: DateFormat.Provider
        val timeFormatProvider: TimeFormat.Provider
        val onSessionSelected: (id: String) -> Unit
    }
}
