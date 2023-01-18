package com.example.restcountries.app.data.repo

import com.example.restcountries.app.data.api.BaseApiResponse
import com.example.restcountries.app.data.api.CountriesApi
import com.example.restcountries.app.data.api.NetworkResult
import com.example.restcountries.app.data.api.model.CountryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class CountriesRepoImpl(private val countriesApi: CountriesApi) : CountriesRepo, BaseApiResponse() {
    override suspend fun getAllCountries(): Flow<NetworkResult<List<CountryModel>>> {
        return flow { emit(safeApiCall {  countriesApi.getAllCountries() }) }
    }
}