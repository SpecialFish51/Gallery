package com.example.pixabaytt.app.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    var progressCount: MutableLiveData<Int> = MutableLiveData(0)

    fun launchViewModelScope() {
        viewModelScope.launch {
            delay(30)
            updateLiveData()
        }
    }

    fun updateLiveData() {
        val count = 1
        progressCount.value = progressCount.value?.plus(count)
    }
}