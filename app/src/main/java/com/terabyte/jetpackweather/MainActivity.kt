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
import com.terabyte.jetpackweather.data.ShPreferencesManager
import com.terabyte.jetpackweather.data.WeatherModel
import com.terabyte.jetpackweather.json.JsonManager
import com.terabyte.jetpackweather.screens.MainCard
import com.terabyte.jetpackweather.screens.TabLayout
import com.terabyte.jetpackweather.ui.DialogSearch
import com.terabyte.jetpackweather.ui.theme.JetpackWeatherTheme
import com.terabyte.jetpackweather.volley.VolleyManager.getData
import kotlin.random.Random
import kotlin.random.nextInt


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val image = chooseBackgroundImage()

        val city = ShPreferencesManager.getCity(this)

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
                val backgroundImage = remember {
                    mutableStateOf(0)
                }

                DrawMainActivity(currentDay, daysList, hoursList, dialogState, image)

                getData(city, this, daysList, currentDay) { response ->
                    JsonManager.getWeatherByDays(response) { listWeatherByDays ->
                        daysList.value = listWeatherByDays
                        currentDay.value = listWeatherByDays[0]
                    }
                }
            }
        }
    }

    private fun chooseBackgroundImage(): Int {
        val images = listOf(R.drawable.main_screen_background1, R.drawable.main_screen_background2, R.drawable.main_screen_background3)
        return images[Random.nextInt(images.indices)]
    }
    @Composable
    private fun DrawMainActivity(currentDay: MutableState<WeatherModel>, daysList: MutableState<List<WeatherModel>>, hoursList: MutableState<List<WeatherModel>>, dialogState: MutableState<Boolean>, backgroundImage: Int) {
        Image(
            painter = painterResource(id = backgroundImage),
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
            DialogSearch(dialogState) { city ->
                ShPreferencesManager.setCity(this, city)
                getData(city, this@MainActivity, daysList, currentDay) {response ->
                    JsonManager.getWeatherByDays(response) { listWeatherByDays ->
                        daysList.value = listWeatherByDays
                        currentDay.value = listWeatherByDays[0]
                    }
                }
            }
        }
    }
}

