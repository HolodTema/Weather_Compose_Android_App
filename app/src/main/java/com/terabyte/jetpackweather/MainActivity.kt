package com.terabyte.jetpackweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.terabyte.jetpackweather.data.WeatherModel
import com.terabyte.jetpackweather.json.JsonManager
import com.terabyte.jetpackweather.ui.DialogSearch
import com.terabyte.jetpackweather.screens.MainCard
import com.terabyte.jetpackweather.screens.TabLayout
import com.terabyte.jetpackweather.ui.theme.JetpackWeatherTheme
import com.terabyte.jetpackweather.volley.VolleyManager.getData


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackWeatherTheme {
                val daysList = remember {
                    mutableStateOf(listOf<WeatherModel>())
                }
                val hoursList = remember {
                    mutableStateOf(listOf<WeatherModel>())
                }
                val currentDay = remember {
                    mutableStateOf(WeatherModel.createEmptyWeatherModelForMainCard())
                }
                val dialogState = remember {
                    mutableStateOf(false)
                }

                drawMainActivity(currentDay, daysList, hoursList, dialogState)

                getData(CITY_DEFAULT, this, daysList, currentDay) { response ->
                    JsonManager.getWeatherByDays(response) { listWeatherByDays ->
                        daysList.value = listWeatherByDays
                        currentDay.value = listWeatherByDays[0]
                    }
                }
            }
        }
    }

    @Composable
    private fun drawMainActivity(currentDay: MutableState<WeatherModel>, daysList: MutableState<List<WeatherModel>>, hoursList: MutableState<List<WeatherModel>>, dialogState: MutableState<Boolean>) {
        Image(
            painter = painterResource(id = R.drawable.main_screen_background),
            contentDescription = "background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        Column {

            MainCard(
                currentDay = currentDay,
                onClickSync = {
                    getData(CITY_DEFAULT, this@MainActivity, daysList, currentDay) { response ->
                        JsonManager.getWeatherByDays(response) { listWeatherByDays ->
                            daysList.value = listWeatherByDays
                            currentDay.value = listWeatherByDays[0]
                        }
                    }
                },
                onClickSearch = {
                    dialogState.value = true
                }
            )
            TabLayout(daysList, hoursList, currentDay)
        }

        if(dialogState.value) {
            DialogSearch(dialogState) {
                getData(it, this@MainActivity, daysList, currentDay) {response ->
                    JsonManager.getWeatherByDays(response) { listWeatherByDays ->
                        daysList.value = listWeatherByDays
                        currentDay.value = listWeatherByDays[0]
                    }
                }
            }
        }
    }
}

