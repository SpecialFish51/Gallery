package com.example.pixabaytt.app.fragment.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabaytt.app.data.api.model.CategoryType
import com.example.pixabaytt.app.data.domain.ImagesModel
import com.example.pixabaytt.app.data.repo.CountriesRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CountriesViewModel(private val imagesRepo: CountriesRepo) : ViewModel() {

    val countries = MutableStateFlow<List<ImagesModel>>(emptyList())
    val isEmpty = MutableLiveData<String>()
    val error = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    val notFound = MutableLiveData<String>()


    fun getImages(category: CategoryType) {
        viewModelScope.launch {
            imagesRepo.getImagesByCategory(categoryType = category).onSuccess { items ->


            }.onFailure {
                error.value =
                    it.message ?: "Ошибка загрузки"
                loading.value = false
            }
        }
    }

    fun onItemDelete(position: Int) {
        viewModelScope.launch {
            val items = countries.value.filterIndexed { index, countryModel ->
                index != position
            }
            countries.emit(items)
        }
    }
}

