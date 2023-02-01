package com.example.restcountries.app.data.repo

import com.example.restcountries.app.base.BaseApiUseCase
import com.example.restcountries.app.data.api.CountriesApi
import com.example.restcountries.app.data.api.model.Country
import com.example.restcountries.app.data.api.model.Currency
import com.example.restcountries.app.data.api.model.Flag
import com.example.restcountries.app.data.domain.CountryModel
import com.example.restcountries.app.data.domain.CurrencyModel
import com.example.restcountries.app.data.domain.FlagModel
import com.google.gson.Gson

interface CountriesRepo {
    suspend fun getAllCountries(): Result<List<CountryModel>>
}


class CountriesRepoImpl(private val countriesApi: CountriesApi, gson: Gson) : CountriesRepo,
    BaseApiUseCase(gson) {
    override suspend fun getAllCountries(): Result<List<CountryModel>> = safeApiCall {
        countriesApi.getAllCountries().mapIndexed { index, countryModel ->
            countryModel.asDomainModel(index)
        }
    }
}

fun Country.asDomainModel(index: Int): CountryModel =
    CountryModel(
        name = name ?: "",
        capital = capital ?: "",
        timeZones = timeZones ?: emptyList(),
        flags = flags?.asFlagModel() ?: FlagModel("",""),
        currencies = currencies?.mapNotNull { currency -> currency?.asCurrencyModel() }
            ?: emptyList(),
        position = index


    )

fun Flag.asFlagModel(): FlagModel =
    FlagModel(
        svg = svg,
        png = png
    )

fun Currency.asCurrencyModel(): CurrencyModel = CurrencyModel(
    name = name ?: "",
    code = code ?: "",
    symbol = symbol ?: ""

)
