package com.arkivanov.konf.shared.common.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class BindableViewHolder<T : Any>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var item: T? = null
        private set

    open fun bind(item: T) {
        this.item = item
    }
}
