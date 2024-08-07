package com.terabyte.jetpackweather

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.terabyte.jetpackweather.data.WeatherModel
import com.terabyte.jetpackweather.screens.MainCard
import com.terabyte.jetpackweather.screens.TabLayout
import com.terabyte.jetpackweather.ui.theme.JetpackWeatherTheme
import org.json.JSONObject


const val API_KEY = "1346c16f37d64d28bc2194344240507"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackWeatherTheme {
                val daysList = remember {
                    mutableStateOf(listOf<WeatherModel>())
                }
                val currentDay = remember {
                    mutableStateOf(
                        WeatherModel
                        (
                            "Loading...",
                                "",
                            "0",
                            "",
                            "",
                            "0",
                            "0",
                            ""
                        )
                    )
                }

                getData("London", this, daysList, currentDay)
                Image(
                    painter = painterResource(id = R.drawable.main_screen_background),
                    contentDescription = "background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
                Column {

                    MainCard(currentDay)
                    TabLayout(daysList, currentDay)
                }
            }
        }
    }
}


//Business Logic part
private fun getData(city: String, context: Context, daysList: MutableState<List<WeatherModel>>, currentDay: MutableState<WeatherModel>) {
    //https://api.weatherapi.com/ is base url. Other is called path
    val url = "https://api.weatherapi.com/" +
            "v1/forecast.json" +
            "?key=$API_KEY" +
            "&q=$city" +
            "&days=3" +
            "&aqi=no" +
            "&alerts=no"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            Log.d(LOG_MY_DEBUG, "Volley response: $response")
            val listWeatherByDays = getWeatherByDays(response)
            daysList.value = listWeatherByDays

            currentDay.value = listWeatherByDays[0]
        },
        { error ->
            Log.d(LOG_MY_DEBUG, "Volley error in getData(): $error")
        }
    )
    queue.add(stringRequest)
}

private fun getWeatherByDays(response: String): List<WeatherModel> {
    if(response.isEmpty()) {
        return listOf()
    }

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
    return result
}

