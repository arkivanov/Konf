package com.arkivanov.konf.shared.common.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(
    private val marginDp: Float
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val margin = view.resources.dpToPx(marginDp).toInt()
        outRect.set(margin, if (parent.getChildAdapterPosition(view) == 0) margin else 0, margin, margin)
    }
}
