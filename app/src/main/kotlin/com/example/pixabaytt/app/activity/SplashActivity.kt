package com.example.pixabaytt.app.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pixabaytt.databinding.ActivitySplashBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel by viewModel<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.apply {
            progressCount.observe(this@SplashActivity) { progressCount ->
                setSplashCount(progressCount)
            }
        }

        viewModel.launchViewModelScope()
    }

    private fun setSplashCount(progressCount: Int) = with(binding) {
        if (progressCount == 100) {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        } else {
            viewModel.launchViewModelScope()
        }

        progressBar.progress = progressCount
    }
}