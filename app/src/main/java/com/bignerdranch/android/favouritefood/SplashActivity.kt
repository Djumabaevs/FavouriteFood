package com.bignerdranch.android.favouritefood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bignerdranch.android.favouritefood.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashBinding: ActivitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

      //  splashBinding.tvAppName.text = "KFC"

    }
}