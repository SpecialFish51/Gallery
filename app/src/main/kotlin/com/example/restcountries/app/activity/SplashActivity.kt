package com.example.pixabaytt.app.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.restcountries.databinding.ActivitySplashBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

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

        viewModel.initSplashScreen()
    }

    private fun setSplashCount(progressCount: Int) = with(binding) {
        if (progressCount > 99) {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        } else {
            viewModel.initSplashScreen()
        }

        progressBar.progress = progressCount
    }
}