package com.arkivanov.konf.shared.root

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import androidx.transition.Visibility
import androidx.transition.doOnEnd
import com.arkivanov.konf.shared.common.decompose.children
import com.arkivanov.konf.shared.common.ui.ViewContext
import com.arkivanov.konf.shared.common.ui.addTo
import com.arkivanov.konf.shared.common.ui.inflate
import com.arkivanov.konf.shared.root.RootComponent.Model
import com.arkivanov.konf.shared.sessiondetails.SessionDetailsView
import com.arkivanov.konf.shared.sessionlist.SessionListView

@Suppress("FunctionName") // Factory function
fun ViewContext.RootView(model: Model): View {
    val root = inflate(R.layout.root_container)

    root.children(model.routerState, R.id.router_view, lifecycle) { parent, child, _ ->
        val oldChildView: View? = parent.getChildAt(0)
        if (oldChildView != null) {
            setupDelayedTransition(parent, child, oldChildView)
        }

        when (child) {
            is RootChild.List -> SessionListView(child.model)
            is RootChild.Details -> SessionDetailsView(child.model)
        }.addTo(parent)
    }

    return root
}

private fun setupDelayedTransition(parent: ViewGroup, newChild: RootChild, oldChildView: View) {
    val slideMode =
        when (newChild) {
            is RootChild.List -> Visibility.MODE_OUT
            is RootChild.Details -> Visibility.MODE_IN
        }

    TransitionManager.beginDelayedTransition(
        parent,
        TransitionSet()
            .addTransition(Fade())
            .addTransition(
                Slide(Gravity.END)
                    .apply { mode = slideMode }
            )
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(150L)
            .apply { doOnEnd { parent.removeView(oldChildView) } }
    )

    oldChildView.visibility = View.GONE
}
