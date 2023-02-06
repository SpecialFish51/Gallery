package com.example.pixabaytt.app.data.repo

import com.example.pixabaytt.app.base.BaseApiUseCase
import com.example.pixabaytt.app.data.api.CategoriesApi
import com.example.pixabaytt.app.data.api.model.ApiResponse
import com.example.pixabaytt.app.data.api.model.CategoryType
import com.example.pixabaytt.app.data.api.model.Image
import com.example.pixabaytt.app.data.api.model.ImageType
import com.example.pixabaytt.app.data.domain.ImagesInfoModel
import com.example.pixabaytt.app.data.domain.ResponseModel
import com.google.gson.Gson

interface ImagesRepo {
    suspend fun getImagesByCategory(categoryType: CategoryType, page: Int): Result<ResponseModel>
    suspend fun getImagesById(id: Int): Result<ResponseModel>
}

class ImagesRepoImpl(
    private val categoriesApi: CategoriesApi, gson: Gson,
) : ImagesRepo,
    BaseApiUseCase(gson) {
    override suspend fun getImagesByCategory(
        categoryType: CategoryType,
        page: Int
    ): Result<ResponseModel> =
        safeApiCall {
            categoriesApi.getPicturesByCategory(
                page = page,
                categoryType = categoryType.name.lowercase(),
                imageType = ImageType.ALL.name.lowercase(),

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
        largeImageURL = largeImageURL,
        webFormatURL= webFormatURL,
        previewURL = previewURL,
        type = type,
        tags = tags
    )
