package com.terabyte.jetpackweather.json

import com.terabyte.jetpackweather.data.WeatherModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

object JsonManager {

    fun getWeatherByDays(response: String, listener: (List<WeatherModel>) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val deferredListWeatherByDays = async(Dispatchers.IO) {
                if(response.isEmpty()) return@async listOf<WeatherModel>()

                val result = arrayListOf<WeatherModel>()

                val mainObj = JSONObject(response)

                val city = mainObj.getJSONObject("location").getString("name")

                val days = mainObj.getJSONObject("forecast").getJSONArray("forecastday")
                for(i in 0 until days.length()) {
                    val item = days[i] as JSONObject
                    result.add(
                        WeatherModel(
                            city,
                            item.getString("date"),
                            "",
                            item.getJSONObject("day").getJSONObject("condition").getString("text"),
                            item.getJSONObject("day").getJSONObject("condition").getString("icon"),
                            item.getJSONObject("day").getString("maxtemp_c"),
                            item.getJSONObject("day").getString("mintemp_c"),
                            item.getJSONArray("hour").toString()
                        )
                    )
                }
                result[0] = result[0].copy(
                    time = mainObj.getJSONObject("current").getString("last_updated"),
                    currentTemp = mainObj.getJSONObject("current").getString("temp_c")
                )
                return@async result
            }

            listener(deferredListWeatherByDays.await())
        }
    }

    fun getWeatherByHours(hours: String, listener: (List<WeatherModel>) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val deferredListWeatherByHours = async(Dispatchers.IO) {
                if(hours.isEmpty()) return@async listOf<WeatherModel>()

                val hoursArray = JSONArray(hours)
                val result = arrayListOf<WeatherModel>()
                for(i in 0 until hoursArray.length()) {
                    val item = hoursArray[i] as JSONObject
                    result.add(
                        WeatherModel(
                            "",
                            item.getString("time"),
                            item.getString("temp_c").toFloat().toInt().toString(),
                            item.getJSONObject("condition").getString("text"),
                            item.getJSONObject("condition").getString("icon"),
                            "",
                            "",
                            ""
                        )
                    )
                }
                return@async result
            }

            listener(deferredListWeatherByHours.await())
        }
    }
}