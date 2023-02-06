package com.example.pixabaytt.app.data.api

import com.example.pixabaytt.app.data.api.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query



interface CategoriesApi {

    @GET("api")
    suspend fun getPicturesByCategory(
        @Query("key") apiKey: String = API_KEY,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = LIMIT,
        @Query("category") categoryType: String,
        @Query("image_type") imageType: String
    ): ApiResponse

    @GET("api")
    suspend fun getPicturesById(
        @Query("key") apiKey: String = API_KEY,
        @Query("id") id: Int
    ): ApiResponse
}

private const val API_KEY = "33106230-b104905cd7ff74ed17e2229af"
private const val LIMIT= 5
