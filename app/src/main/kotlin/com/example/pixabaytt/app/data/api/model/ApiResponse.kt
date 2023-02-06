package com.example.pixabaytt.app.data.api.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalHits")
    val totalHits: Int,
    @SerializedName("hits")
    val images: List<Image>

)


data class Image(
    @SerializedName("id")
    var id: Int,
    @SerializedName("pageURL")
    val pageURL: String,
    @SerializedName("largeImageURL")
    val largeImageURL: String,
    @SerializedName("webformatURL")
    val webFormatURL: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("tags")
    val tags: String,
    @SerializedName("previewURL")
    val previewURL: String,
)
