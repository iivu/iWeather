package com.cs26.iweather.ui.weather

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.cs26.iweather.R
import com.cs26.iweather.databinding.ActivityWeatherBinding
import com.cs26.iweather.logic.model.Weather
import com.cs26.iweather.logic.model.getSky
import java.text.SimpleDateFormat
import java.util.Locale

class WeatherActivity : AppCompatActivity() {

    private val TAG = "WeatherActivity"

    private lateinit var binding: ActivityWeatherBinding

    private val viewModel by lazy { ViewModelProvider(this)[WeatherViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
        setContentView(binding.root)

        if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }
        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }
        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }

        viewModel.weatherLiveData.observe(this) { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                showWeather(weather)
            } else {
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        }
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
    }

    private fun showWeather(weather: Weather) {
        binding.apply {
            val realTime = weather.realtime
            val daily = weather.daily
            // 填充now.xml
            now.placeName.text = viewModel.placeName
            now.currentTemp.text = "${realTime.temperature.toInt()} ℃"
            now.currentSky.text = getSky(realTime.skycon).info
            now.currentAQI.text = "空气指数 ${realTime.airQuality.aqi.chn.toInt()}"
            now.nowLayout.setBackgroundResource(getSky(realTime.skycon).bg)
            // 填充forecast.xml
            forecast.forecastLayout.removeAllViews()
            for(i in 0 until daily.skycon.size) {
                val skycon = daily.skycon[i]
                Log.d(TAG,skycon.toString())
                val temperature = daily.temperature[i]
                val view = LayoutInflater.from(this@WeatherActivity).inflate(R.layout.forecast_item,forecast.forecastLayout,false)
                val dateInfo:TextView = view.findViewById(R.id.dateInfo)
                val skyIcon: ImageView = view.findViewById(R.id.skyIcon)
                val skyInfo: TextView = view.findViewById(R.id.skyInfo)
                val temperatureInfo: TextView = view.findViewById(R.id.temperatureInfo)
                dateInfo.text = skycon.date
                val sky = getSky(skycon.value)
                skyIcon.setImageResource(sky.icon)
                skyInfo.text = sky.info
                temperatureInfo.text = "${temperature.min} ~ ${temperature.max} ℃"
                forecast.forecastLayout.addView(view)
            }
            // 填充life_index.xml
            val _lifeIndex = daily.lifeIndex
            lifeIndex.coldRiskText.text = _lifeIndex.coldRisk[0].desc
            lifeIndex.dressingText.text = _lifeIndex.dressing[0].desc
            lifeIndex.ultravioletText.text = _lifeIndex.ultraviolet[0].desc
            lifeIndex.carWashingText.text = _lifeIndex.carWashing[0].desc

            weatherLayout.visibility = View.VISIBLE
        }

    }
}