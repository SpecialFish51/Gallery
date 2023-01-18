package com.example.restcountries.app.data.api

import com.example.restcountries.app.data.api.model.CountryModel
import retrofit2.Response
import retrofit2.http.GET

interface CountriesApi {
    @GET("all")
    suspend fun getAllCountries(): Response<List<CountryModel>>
}