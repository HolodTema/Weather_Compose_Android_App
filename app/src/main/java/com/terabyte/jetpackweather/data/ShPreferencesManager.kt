package com.terabyte.jetpackweather.data

import android.content.Context
import android.content.SharedPreferences
import com.terabyte.jetpackweather.CITY_DEFAULT
import com.terabyte.jetpackweather.SHARED_PREFERENCES_NAME

object ShPreferencesManager {
    private const val KEY_CITY = "city"
    lateinit var shPreferences: SharedPreferences

    fun getCity(context: Context): String {
        if(!this::shPreferences.isInitialized) shPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return shPreferences.getString(KEY_CITY, CITY_DEFAULT) ?: CITY_DEFAULT
    }

    fun setCity(context: Context, city: String) {
        if(!this::shPreferences.isInitialized) shPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        shPreferences.edit()
            .putString(KEY_CITY, city)
            .apply()
    }

}