package com.example.pixabaytt.app.fragment.images

import androidx.recyclerview.widget.RecyclerView
import com.example.pixabaytt.app.data.api.model.CategoryType
import com.example.pixabaytt.app.data.api.model.Image
import com.example.pixabaytt.app.data.api.model.ImagesInfo
import com.example.pixabaytt.app.utils.autoNotify
import com.example.pixabaytt.databinding.ItemCategoryBinding
import kotlin.properties.Delegates

class ImagesAdapter(
    private val onImageClicked: (image: Image) -> Unit
) : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    var items: List<ImagesInfo> by Delegates.observable(emptyList()) { prop, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o.??? == n.??? }
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