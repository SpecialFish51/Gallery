package com.example.pixabaytt.app.di

import com.example.pixabaytt.app.activity.SplashViewModel
import com.example.pixabaytt.app.data.api.CategoriesApi
import com.example.pixabaytt.app.data.repo.ImagesRepo
import com.example.pixabaytt.app.data.repo.ImagesRepoImpl
import com.example.pixabaytt.app.fragment.categories.CategoriesViewModel
import com.example.pixabaytt.app.fragment.image.ImageViewModel
import com.example.pixabaytt.app.fragment.images.ImagesViewModel
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://pixabay.com/"

val appModule = module {
    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .pingInterval(1, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .addInterceptor(interceptor)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single<CategoriesApi> {
        get<Retrofit>()
            .create(CategoriesApi::class.java)
    }

    single {
        Gson()
    }

    single<ImagesRepo> {
        ImagesRepoImpl(get(), get())
    }

    viewModel {
        SplashViewModel()
    }

    viewModel {
        CategoriesViewModel()
    }

    viewModel { (categoryType:String) ->
        ImagesViewModel(get(), categoryType)
    }

    viewModel { (id:Int) ->
        ImageViewModel(get(), id)
    }
}

