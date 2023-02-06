package com.example.pixabaytt.app.data.api.model

import com.google.gson.annotations.SerializedName

data class ImagesInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("pageURL")
    val pageURL: String,
    @SerializedName("previewURL")
    val previewURL: String,
    @SerializedName("largeImageURL")
    val largeImageURL: String,
    @SerializedName("fullHDURL")
    val fullHDURL: String,
    @SerializedName("imageURL")
    val imageURL: String,
    @SerializedName("views")
    val views: Int,
    @SerializedName("likes")
    val likes: Int
)


