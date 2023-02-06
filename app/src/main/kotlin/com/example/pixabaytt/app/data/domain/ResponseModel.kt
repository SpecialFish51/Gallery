package com.example.pixabaytt.app.data.domain

data class ResponseModel(
    val total: Int,
    val totalHits: Int,
    val images: List<ImagesInfoModel>
)

