package com.cs26.iweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class IWeatherApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        const val CAIYUN_TOKEN = "I4xQgaVnxRF8xCvN"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}