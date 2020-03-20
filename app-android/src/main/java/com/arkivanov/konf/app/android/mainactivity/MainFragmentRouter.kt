package com.arkivanov.konf.app.android.mainactivity

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.arkivanov.konf.app.android.utils.setDefaultFragmentAnimations
import com.arkivanov.konf.app.android.utils.transaction

class MainFragmentRouter(
    private val fragmentManager: FragmentManager,
    private val fragmentFactory: MainFragmentFactory,
    @IdRes private val contentId: Int
) {

    fun openSessionList() {
        fragmentManager.transaction {
            add(contentId, fragmentFactory.sessionListFragment())
        }
    }

    fun openSessionDetails(id: String) {
        fragmentManager.transaction {
            setDefaultFragmentAnimations()
            replace(contentId, fragmentFactory.sessionDetailsFragment().setArguments(sessionId = id))
            addToBackStack(null)
        }
    }
}
