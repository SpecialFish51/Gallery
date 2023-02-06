package com.example.pixabaytt.app.fragment.categories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pixabaytt.app.data.api.model.CategoryType
import com.example.pixabaytt.app.utils.autoNotify
import com.example.pixabaytt.databinding.ItemCategoryBinding
import kotlin.properties.Delegates


class CategoriesAdapter(
    private val onCategoryClicked: (category: CategoryType) -> Unit
) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    var items: List<CategoryType> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o.categoryTitle == n.categoryTitle }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemCategoryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(view, onCategoryClicked)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        private val binding: ItemCategoryBinding,
        private val onCategoryClicked: (country: CategoryType) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(categoryType: CategoryType) {
            binding.categoryName.text = itemView.context.getString(categoryType.categoryTitle)

            itemView.setOnClickListener {
                onCategoryClicked.invoke(categoryType)
            }
        }

    }

}







