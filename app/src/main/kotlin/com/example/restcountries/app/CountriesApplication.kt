package com.example.restcountries.app

import android.app.Application
import com.example.restcountries.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CountriesApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@CountriesApplication)
            modules(listOf(appModule))
        }
    }
}