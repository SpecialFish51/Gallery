package com.example.pixabaytt.app.data.api.model

import com.google.gson.annotations.SerializedName

enum class ImageType {
    @SerializedName("photo")
    PHOTO,

    @SerializedName("all")
    ALL,

    @SerializedName("illustration")
    ILLUSTRATION,

    @SerializedName("vector")
    VECTOR
}