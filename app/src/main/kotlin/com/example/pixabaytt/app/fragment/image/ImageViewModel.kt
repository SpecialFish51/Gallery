package com.example.pixabaytt.app.fragment.image

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabaytt.app.data.domain.ImagesInfoModel
import com.example.pixabaytt.app.data.repo.ImagesRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ImageViewModel(private val imagesRepo: ImagesRepo,
                     id: Int) : ViewModel() {

    val image = MutableStateFlow<ImagesInfoModel?>(null)
    val isEmpty = MutableLiveData<String>()
    val error = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    val notFound = MutableLiveData<String>()

    init {
        getImagesById(id)
    }


    fun getImagesById(id: Int) {
        viewModelScope.launch {
            imagesRepo.getImagesById(
                id = id
            ).onSuccess { items ->
                    image.value = items.images.firstOrNull()
                }.onFailure {
                    Log.d("api error",it.message?: "")
                    error.value = (it.message + it.cause?.message)
                    loading.value = false
            }
        }
    }
}

