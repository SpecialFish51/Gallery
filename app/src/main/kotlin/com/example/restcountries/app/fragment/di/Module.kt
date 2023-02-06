package com.example.pixabaytt.app.fragment.di

import com.example.pixabaytt.app.activity.SplashViewModel
import com.example.pixabaytt.app.data.api.CountriesApi
import com.example.pixabaytt.app.data.repo.CountriesRepo
import com.example.pixabaytt.app.data.repo.CountriesRepoImpl
import com.example.pixabaytt.app.fragment.countries.CountriesViewModel
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://restcountries.com/v2/"

val appModule = module {
    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
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

    single<CountriesApi> {
        get<Retrofit>().create(CountriesApi::class.java)
    }

    single<CountriesRepo> {
        CountriesRepoImpl(
            countriesApi = get(),
            gson = get()
        )
    }

    single {
        Gson()
    }

    viewModel {
        SplashViewModel()
    }

    viewModel {
        CountriesViewModel(countriesRepo = get())
    }
}