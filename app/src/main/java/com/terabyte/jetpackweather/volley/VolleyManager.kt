package com.terabyte.jetpackweather.volley

import android.content.Context
import androidx.compose.runtime.MutableState
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.terabyte.jetpackweather.URL_WEATHER_FORECAST
import com.terabyte.jetpackweather.WEATHER_API_KEY
import com.terabyte.jetpackweather.data.WeatherModel
import timber.log.Timber

object VolleyManager {

    fun getData(city: String, context: Context, daysList: MutableState<List<WeatherModel>>, currentDay: MutableState<WeatherModel>, onResponseListener: (String) -> Unit) {
        val url = URL_WEATHER_FORECAST +
                "?key=$WEATHER_API_KEY" +
                "&q=$city" +
                "&days=3" +
                "&aqi=no" +
                "&alerts=no"

        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                Timber.i("Volley response: $response")
                onResponseListener(response)
            },
            { error ->
                Timber.e("Volley error: $error")
            }
        )

        queue.add(stringRequest)
    }
}