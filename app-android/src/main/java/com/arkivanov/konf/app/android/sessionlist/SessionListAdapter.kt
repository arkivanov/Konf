package com.arkivanov.konf.app.android.sessionlist

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arkivanov.konf.app.android.R
import com.arkivanov.konf.app.android.utils.BindableViewHolder
import com.arkivanov.konf.app.android.utils.dispatchUpdates
import com.arkivanov.konf.app.android.utils.formatDate
import com.arkivanov.konf.app.android.utils.formatTime
import com.arkivanov.konf.app.android.utils.layoutInflater
import com.arkivanov.konf.app.android.utils.requireNotNull
import com.arkivanov.konf.app.android.utils.requireViewWithId
import com.arkivanov.konf.shared.sessionlist.SessionListView.Model.Item

class SessionListAdapter(
    private val onSessionClickListener: (id: String) -> Unit
) : RecyclerView.Adapter<BindableViewHolder<*>>() {

    private var items: List<Item> = emptyList()

    fun setItems(items: List<Item>) {
        val oldItems = this.items
        this.items = items

        dispatchUpdates(oldItems = oldItems, newItems = items) { a, b ->
            when (a) {
                is Item.DaySeparator -> (b is Item.DaySeparator) && (a.number == b.number)
                is Item.SessionSeparator -> b is Item.SessionSeparator
                is Item.Session -> (b is Item.Session) && (a.id == b.id)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int =
        when (items[position]) {
            is Item.DaySeparator -> ViewType.DAY_SEPARATOR
            is Item.SessionSeparator -> ViewType.SESSION_SEPARATOR
            is Item.Session -> ViewType.SESSION
        }.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder<*> {
        val inflater = parent.context.layoutInflater

        return when (ViewType.VALUES[viewType]) {
            ViewType.DAY_SEPARATOR -> DaySeparatorViewHolder(view = inflater.inflate(R.layout.day_separator, parent, false))
            ViewType.SESSION_SEPARATOR -> SessionSeparatorViewHolder(view = inflater.inflate(R.layout.session_separator, parent, false))

            ViewType.SESSION ->
                SessionViewHolder(
                    view = inflater.inflate(R.layout.session_list_item, parent, false),
                    onSessionClickListener = onSessionClickListener
                )
        }
    }

    override fun onBindViewHolder(holder: BindableViewHolder<*>, position: Int) {
        @Suppress("UNCHECKED_CAST")
        (holder as BindableViewHolder<Item>).bind(items[position])
    }

    private enum class ViewType {
        DAY_SEPARATOR, SESSION_SEPARATOR, SESSION;

        companion object {
            val VALUES: List<ViewType> = values().toList()
        }
    }

    private class DaySeparatorViewHolder(view: View) : BindableViewHolder<Item.DaySeparator>(view) {
        private val textView = view.requireViewWithId<TextView>(R.id.text_day)

        override fun bind(item: Item.DaySeparator) {
            super.bind(item)

            textView.text = textView.resources.getString(R.string.day_number_fmt, item.number)
        }
    }

    private class SessionSeparatorViewHolder(view: View) : BindableViewHolder<Item.SessionSeparator>(view)

    private class SessionViewHolder(
        view: View,
        onSessionClickListener: (id: String) -> Unit
    ) : BindableViewHolder<Item.Session>(view) {
        private val context = view.context
        private val titleTextView = view.requireViewWithId<TextView>(R.id.text_title)
        private val infoTextView = view.requireViewWithId<TextView>(R.id.text_info)
        private val speakerInfoTextView = view.requireViewWithId<TextView>(R.id.text_speaker_info)

        init {
            view.setOnClickListener { onSessionClickListener(item.requireNotNull().id) }
        }

        @SuppressLint("SetTextI18n")
        override fun bind(item: Item.Session) {
            super.bind(item)

            titleTextView.text = item.title

            val startTimeText = item.startDate?.let { formatTime(context, item.eventTimeZone, it)}
            val endTimeText = item.endDate?.let { formatTime(context, item.eventTimeZone, it)}
            infoTextView.text = "$startTimeText-$endTimeText, ${item.roomName}"

            speakerInfoTextView.text = item.speakerInfo
        }
    }
}
