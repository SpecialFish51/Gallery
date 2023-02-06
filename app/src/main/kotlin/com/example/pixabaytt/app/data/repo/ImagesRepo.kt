package com.example.pixabaytt.app.data.repo

import com.example.pixabaytt.app.base.BaseApiUseCase
import com.example.pixabaytt.app.data.api.CategoriesApi
import com.example.pixabaytt.app.data.api.model.*
import com.example.pixabaytt.app.data.domain.ImagesInfoModel
import com.example.pixabaytt.app.data.domain.ResponseModel
import com.google.gson.Gson

interface CountriesRepo {
    suspend fun getImagesByCategory(categoryType: CategoryType): Result<ResponseModel>
    suspend fun getImagesById(id: Int): Result<ResponseModel>
}

class CountriesRepoImpl(private val categoriesApi: CategoriesApi, gson: Gson) : CountriesRepo,
    BaseApiUseCase(gson) {
    override suspend fun getImagesByCategory(categoryType: CategoryType): Result<ResponseModel> =
        safeApiCall {
            categoriesApi.getPicturesByCategory(
                categoryType = categoryType.name.lowercase(),
                imageType = ImageType.PHOTO.name.lowercase()
            ).asDomainModel()
        }

    override suspend fun getImagesById(id: Int): Result<ResponseModel> = safeApiCall {
        categoriesApi.getPicturesById(id = id).asDomainModel()
    }
}

fun ApiResponse.asDomainModel(): ResponseModel =
    ResponseModel(
        total = total,
        totalHits = totalHits,
        images = images.map {
            it.asImagesModel()
        }
    )

fun Image.asImagesModel(): ImagesInfoModel =
    ImagesInfoModel(
        id = id,
        pageURL = pageURL,
        largeImageURL= largeImageURL,
        previewURL = previewURL,
        type = type,
        tags = tags
    )
