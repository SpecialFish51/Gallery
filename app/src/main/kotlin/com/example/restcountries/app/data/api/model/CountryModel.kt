package com.example.restcountries.app.data.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryModel(
    @SerializedName("name")
    val name: String?,
    @SerializedName("region")
    val region: String?,
    @SerializedName("capital")
    val capital: String?,
    @SerializedName("timezones")
    val timeZones: List<String?>?,
    @SerializedName("flags")
    val flags: FlagModel?,
    @SerializedName("currencies")
    val currencies: List<CurrencyModel?>?
) : Parcelable

@Parcelize
data class CurrencyModel(
    @SerializedName("name")
    val name: String?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("symbol")
    val symbol: String?
) : Parcelable

@Parcelize
data class FlagModel(
    @SerializedName("svg")
    val svg: String,
    @SerializedName("png")
    val png: String
) : Parcelable

