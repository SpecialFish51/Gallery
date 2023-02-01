package com.example.restcountries.app.fragment.countries

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restcountries.app.data.domain.CountryModel
import com.example.restcountries.app.data.repo.CountriesRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CountriesViewModel(private val countriesRepo: CountriesRepo) : ViewModel() {

    val countries = MutableStateFlow<List<CountryModel>>(emptyList())
    val isEmpty = MutableLiveData<String>()
    val error = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    val notFound = MutableLiveData<String>()


    fun getCountries() {
        viewModelScope.launch {
            countriesRepo.getAllCountries().onSuccess { items ->
                countries.emit(items)
                loading.value = false
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

