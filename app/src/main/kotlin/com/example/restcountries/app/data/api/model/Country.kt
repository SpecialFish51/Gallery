package com.example.restcountries.app.data.api.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name")
    val name: String?,
    @SerializedName("region")
    val region: String?,
    @SerializedName("capital")
    val capital: String?,
    @SerializedName("timezones")
    val timeZones: List<String>?,
    @SerializedName("flags")
    val flags: Flag?,
    @SerializedName("currencies")
    val currencies: List<Currency?>?,
    val position: Int
)

data class Currency(
    @SerializedName("name")
    val name: String?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("symbol")
    val symbol: String?
)

data class Flag(
    @SerializedName("svg")
    val svg: String,
    @SerializedName("png")
    val png: String
)

