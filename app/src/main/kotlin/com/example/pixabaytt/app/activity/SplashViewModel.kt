package com.example.restcountries.app.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    var progressCount: MutableLiveData<Int> = MutableLiveData(0)

    fun initSplashScreen() {
        viewModelScope.launch {
            delay(50)
            updateLiveData()
        }
    }

    fun updateLiveData() {
        val count = 1
        progressCount.value = progressCount.value?.plus(count)
    }
}