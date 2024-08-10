package com.terabyte.jetpackweather.ui.preview

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.terabyte.jetpackweather.data.WeatherModel

class CurrentDayProvider: PreviewParameterProvider<MutableState<WeatherModel>> {
    override val values: Sequence<MutableState<WeatherModel>>
        get() = sequenceOf(
            mutableStateOf(
                WeatherModel(
                    "London",
                    "2024-08-09 13:15",
                    "33",
                    "Patchy rain nearby",
                    "https://cdn.weatherapi.com/weather/64x64/night/116.png",
                    "14",
                    "21",
                    ""
                )
            )
        )
}


class WeatherModelProvider: PreviewParameterProvider<WeatherModel> {
    override val values: Sequence<WeatherModel>
        get() = sequenceOf(
            WeatherModel(
                "London",
                "2024-08-09 13:15",
                "33",
                "Patchy rain nearby",
                "https://cdn.weatherapi.com/weather/64x64/night/116.png",
                "14",
                "21",
                ""
            )
        )
}