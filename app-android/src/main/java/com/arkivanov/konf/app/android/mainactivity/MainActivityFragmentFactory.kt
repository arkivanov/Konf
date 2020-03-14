package com.arkivanov.konf.app.android.mainactivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.arkivanov.konf.app.android.sessionlist.SessionListFragment
import com.arkivanov.konf.database.KonfDatabase
import com.arkivanov.mvikotlin.core.store.StoreFactory

class MainActivityFragmentFactory(
    private val dependencies: Dependencies
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        when (className) {
            SessionListFragment::class::java.name -> sessionListFragment()
            else -> super.instantiate(classLoader, className)
        }

    fun sessionListFragment(): SessionListFragment =
        SessionListFragment(
            object : SessionListFragment.Dependencies, Dependencies by dependencies {
            }
        )

    interface Dependencies {
        val storeFactory: StoreFactory
        val database: KonfDatabase
    }
}
