package com.example.pixabaytt.app.fragment.images

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabaytt.app.data.api.model.CategoryType
import com.example.pixabaytt.app.data.domain.ResponseModel
import com.example.pixabaytt.app.data.repo.CountriesRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ImagesViewModel(private val imagesRepo: CountriesRepo,
                      categoryType: String) : ViewModel() {

    val images = MutableStateFlow(ResponseModel(0, 0, emptyList()))
    val isEmpty = MutableLiveData<String>()
    val error = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    val notFound = MutableLiveData<String>()

    init {
        getImages(categoryType)
    }


    fun getImages(categoryName: String) {
        viewModelScope.launch {
            imagesRepo.getImagesByCategory(
                categoryType = CategoryType.valueOf(categoryName)
            ).onSuccess { items ->
                    images.value = items
                }.onFailure {
                    Log.d("api error",it.message?: "")
                    error.value = (it.message + it.cause?.message)
                    loading.value = false
                }
        }
    }
}

