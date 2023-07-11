package com.cs26.iweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.cs26.iweather.IWeatherApplication
import com.cs26.iweather.logic.model.Place
import com.google.gson.Gson

object PlaceDao {

    private const val PLACE_LOCAL_KEY = "place"

    fun savePlace(place:Place) {
        sharedPreferences().edit {
            putString(PLACE_LOCAL_KEY, Gson().toJson(place))
        }
    }

    fun getSavedPlace():Place {
        val placeJson = sharedPreferences().getString(PLACE_LOCAL_KEY, "")
        return Gson().fromJson(placeJson,Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains(PLACE_LOCAL_KEY)

    private fun sharedPreferences() = IWeatherApplication.context.getSharedPreferences("iweather",Context.MODE_PRIVATE)

}