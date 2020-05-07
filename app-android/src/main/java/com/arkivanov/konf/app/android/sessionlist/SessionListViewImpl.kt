package com.arkivanov.konf.app.android.sessionlist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arkivanov.konf.app.android.R
import com.arkivanov.konf.app.android.utils.MarginItemDecoration
import com.arkivanov.konf.app.android.utils.requireViewWithId
import com.arkivanov.konf.shared.sessionlist.SessionListView
import com.arkivanov.konf.shared.sessionlist.SessionListView.Event
import com.arkivanov.konf.shared.sessionlist.SessionListView.Model
import com.arkivanov.mvikotlin.core.utils.diff
import com.arkivanov.mvikotlin.core.view.BaseMviView
import com.arkivanov.mvikotlin.core.view.ViewRenderer

class SessionListViewImpl(root: View) : BaseMviView<Model, Event>(), SessionListView {

    private val adapter = SessionListAdapter(onSessionClickListener = { dispatch(Event.SessionClicked(id = it)) })

    init {
        val recyclerView = root.requireViewWithId<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(MarginItemDecoration(marginDp = ITEM_MARGIN_DP))
    }

    override val renderer: ViewRenderer<Model>? =
        diff {
            diff(get = Model::items, compare = { a, b -> a === b }, set = adapter::setItems)
        }

    private companion object {
        private const val ITEM_MARGIN_DP = 12F
    }
}
