package com.terabyte.jetpackweather

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.terabyte.jetpackweather.screens.MainCard
import com.terabyte.jetpackweather.screens.TabLayout
import com.terabyte.jetpackweather.ui.theme.JetpackWeatherTheme


const val API_KEY = "1346c16f37d64d28bc2194344240507"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackWeatherTheme {
                getData("London", this)
                Image(
                    painter = painterResource(id = R.drawable.main_screen_background),
                    contentDescription = "background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
                Column {

                    MainCard()
                    TabLayout()
                }
            }
        }
    }
}


//Business Logic part
private fun getData(city: String, context: Context) {
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
        },
        { error ->
            Log.d(LOG_MY_DEBUG, "Volley error in getData(): $error")
        }
    )
    queue.add(stringRequest)
}

