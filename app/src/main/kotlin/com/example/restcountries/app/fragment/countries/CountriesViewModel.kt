package com.example.restcountries.app.fragment.countries

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restcountries.app.data.api.NetworkResult
import com.example.restcountries.app.data.api.model.CountryModel
import com.example.restcountries.app.data.repo.CountriesRepo
import kotlinx.coroutines.launch

class CountriesViewModel(private val countriesRepo: CountriesRepo) : ViewModel() {

    val countries = MutableLiveData<List<CountryModel>>()
    val isEmpty = MutableLiveData<String>()
    val error = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    val notFound = MutableLiveData<String>()

    fun getCountries() {
        viewModelScope.launch {
            countriesRepo.getAllCountries()
                .collect { networkResult ->
                    when (networkResult) {
                        is NetworkResult.Empty -> isEmpty.value =
                            networkResult.message ?: "Нет ответа"
                        is NetworkResult.Error -> {
                            error.value =
                                networkResult.message ?: "Ошибка загрузки"
                            loading.value = false
                        }
                        is NetworkResult.Loading -> loading.value = true
                        is NetworkResult.NotFound -> {
                            notFound.value =
                                networkResult.message ?: "Ошибка"
                        }
                        is NetworkResult.Success -> {
                            countries.value =
                                networkResult.data ?: emptyList()
                            loading.value = false
                        }
                    }
                }
        }
    }
}
