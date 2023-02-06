package com.example.pixabaytt.app.fragment.images

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabaytt.app.data.api.model.CategoryType
import com.example.pixabaytt.app.data.domain.ResponseModel
import com.example.pixabaytt.app.data.repo.ImagesRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ImagesViewModel(
    private val imagesRepo: ImagesRepo, private val categoryType: String
) : ViewModel() {

    val images = MutableStateFlow(ResponseModel(0, 0, emptyList()))
    val isEmpty = MutableLiveData<String>()
    val error = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    val notFound = MutableLiveData<String>()

    var value = MutableStateFlow(1)

    init {
        getImages(value.value++)
    }


    private fun getImages(page: Int) {
        viewModelScope.launch {
            loading.value = true
            imagesRepo.getImagesByCategory(
                page = page, categoryType = CategoryType.valueOf(categoryType)
            ).onSuccess { items ->
                loading.value = false
                images.value = ResponseModel(
                    items.total, items.totalHits, images.value.images + items.images
                )
            }.onFailure {
                Log.d("api error", it.message ?: "")
                error.value = (it.message + it.cause?.message)
                loading.value = false
            }
        }
    }

    fun getNewPage() {
        val value = value.value ++
        getImages(value)
    }
}

