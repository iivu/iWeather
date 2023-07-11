package com.cs26.iweather.logic.network

import com.cs26.iweather.IWeatherApplication
import com.cs26.iweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {

    @GET("/v2/place?token=${IWeatherApplication.CAIYUN_TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query:String): Call<PlaceResponse>

}