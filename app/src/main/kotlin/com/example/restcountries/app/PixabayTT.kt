package com.example.pixabaytt.app

import android.app.Application
import com.example.pixabaytt.app.fragment.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PixabayTT: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@PixabayTT)
            modules(listOf(appModule))
        }
    }
}