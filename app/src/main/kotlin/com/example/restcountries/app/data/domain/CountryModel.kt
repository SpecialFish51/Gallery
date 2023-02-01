package com.example.restcountries.app.data.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CountryModel(
    val name: String,
    val capital: String,
    val timeZones: List<String>,
    val flags: FlagModel,
    val currencies: List<CurrencyModel>,
    val position: Int
): Parcelable

@Parcelize
data class CurrencyModel(
    val name: String,
    val code: String,
    val symbol: String
) : Parcelable

@Parcelize
data class FlagModel(
    val svg: String,
    val png: String
) : Parcelable