package com.arkivanov.konf.shared.sessionlist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arkivanov.decompose.extensions.android.ViewContext
import com.arkivanov.decompose.extensions.android.layoutInflater
import com.arkivanov.konf.shared.common.decompose.diff
import com.arkivanov.konf.shared.common.ui.MarginItemDecoration
import com.arkivanov.konf.shared.common.ui.requireViewWithId
import com.arkivanov.konf.shared.sessionlist.SessionListComponent.Model
import com.arkivanov.konf.shared.sessionlist.SessionListViewModel as ViewModel

private const val ITEM_MARGIN_DP = 12F

@Suppress("FunctionName") // Factory function
fun ViewContext.SessionListView(model: Model): View {
    val root = layoutInflater.inflate(R.layout.session_list, parent, false)

    val recyclerView = root.requireViewWithId<RecyclerView>(R.id.recycler_view)
    val refreshLayout = root.requireViewWithId<SwipeRefreshLayout>(R.id.refresh_layout)
    val adapter = SessionListAdapter(onSessionClickListener = model::onSessionClicked)

    recyclerView.adapter = adapter
    recyclerView.addItemDecoration(MarginItemDecoration(marginDp = ITEM_MARGIN_DP))
    refreshLayout.setOnRefreshListener(model::onRefreshTriggered)

    diff(model.data) {
        diff(get = ViewModel::items, set = adapter::setItems)
    }

    diff(model.syncStatus) {
        diff(get = { it }) {
            set(refreshLayout, it)
        }
    }

    return root
}

private fun set(refreshLayout: SwipeRefreshLayout, v: Boolean) {
    refreshLayout.isRefreshing = v
}
