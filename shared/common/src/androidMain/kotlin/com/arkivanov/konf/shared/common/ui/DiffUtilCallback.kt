package com.arkivanov.konf.shared.common.ui

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback<T>(
    private val oldItems: List<T>,
    private val newItems: List<T>,
    private val areItemsTheSame: (T, T) -> Boolean
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        this.areItemsTheSame(oldItems[oldItemPosition], newItems[newItemPosition])

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItems[oldItemPosition] == newItems[newItemPosition]
}
