package com.example.pixabaytt.app.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeHelper(val onSwipe: (position: Int) -> Unit) : ItemTouchHelper(object : SimpleCallback(
    0,
    LEFT
) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = true

    override fun onSwiped(
        viewHolder: RecyclerView.ViewHolder,
        direction: Int
    ) {
        val position = viewHolder.bindingAdapterPosition
        viewHolder.bindingAdapter?.notifyItemChanged(position)
        onSwipe.invoke(position)
    }
})

