package com.example.pixabaytt.app.fragment.categories

import androidx.lifecycle.ViewModel
import com.example.pixabaytt.app.data.api.model.CategoryType

class CategoriesViewModel : ViewModel() {

    fun setDataToAdapter(): List<CategoryType> {
        val items = arrayListOf<CategoryType>()
        items.addAll(CategoryType.values())
        return items
    }
}