package com.cs26.iweather.logic.network

import com.cs26.iweather.IWeatherApplication
import com.cs26.iweather.logic.model.DailyResponse
import com.cs26.iweather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {

    @GET("/v2.5/${IWeatherApplication.CAIYUN_TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<RealtimeResponse>

    @GET("/v2.5/${IWeatherApplication.CAIYUN_TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<DailyResponse>

}