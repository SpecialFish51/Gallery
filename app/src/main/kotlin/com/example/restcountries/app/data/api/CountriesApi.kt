package com.example.restcountries.app.data.api

import com.example.restcountries.app.data.api.model.Country
import retrofit2.http.GET

interface CountriesApi {
    @GET("all")
    suspend fun getAllCountries(): List<Country>
}