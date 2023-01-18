package com.example.restcountries.app.data.repo

import com.example.restcountries.app.data.api.NetworkResult
import com.example.restcountries.app.data.api.model.CountryModel
import kotlinx.coroutines.flow.Flow

interface CountriesRepo {
    suspend fun getAllCountries(): Flow<NetworkResult<List<CountryModel>>>
}