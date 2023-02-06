package com.example.pixabaytt.app.utils

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun <T> RecyclerView.Adapter<*>.autoNotify(oldList: List<T>, newList: List<T>, compare: (T, T) -> Boolean) {
    val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return compare(oldList[oldItemPosition], newList[newItemPosition])
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size
    })

    diff.dispatchUpdatesTo(this)
}

fun RecyclerView.onEndOfListReached(function: () -> Unit) {
    addOnScrollListener(
        PaginatingScrollListener(
            layoutManager as LinearLayoutManager
        ) {
            function.invoke()
        }
    )
}

class PaginatingScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val onEndOfListReached: () -> Unit
) : RecyclerView.OnScrollListener() {

    private var isBottomAlreadyReached: Boolean = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        val isBottomReached =
            visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0
        if (isBottomReached && isBottomAlreadyReached.not()) {
            isBottomAlreadyReached = true
            onEndOfListReached()
        }
        if (isBottomReached.not()) {
            isBottomAlreadyReached = false
        }
    }
}