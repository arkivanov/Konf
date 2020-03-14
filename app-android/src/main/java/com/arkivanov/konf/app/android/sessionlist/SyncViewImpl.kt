package com.arkivanov.konf.app.android.sessionlist

import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arkivanov.konf.app.android.R
import com.arkivanov.konf.app.android.utils.DiffMviView
import com.arkivanov.konf.app.android.utils.requireViewWithId
import com.arkivanov.konf.shared.sync.SyncView
import com.arkivanov.konf.shared.sync.SyncView.Event
import com.arkivanov.konf.shared.sync.SyncView.Model
import com.arkivanov.mvikotlin.core.utils.DiffBuilder

class SyncViewImpl(root: View) : DiffMviView<Model, Event>(), SyncView {

    private val refreshLayout = root.requireViewWithId<SwipeRefreshLayout>(R.id.refresh_layout)

    init {
        refreshLayout.setOnRefreshListener { dispatch(Event.RefreshTriggered) }
    }

    override fun DiffBuilder<Model>.setupDiff() {
        diff(get = Model::isLoading, bind = refreshLayout::setRefreshing)
    }
}
