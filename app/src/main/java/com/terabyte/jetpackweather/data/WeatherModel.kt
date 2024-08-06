package com.terabyte.jetpackweather.data


//every WeatherModel object is for only 1 day or for only 1 hour
data class WeatherModel(
    val city: String,
    val time: String,
    val currentTemp: String,
    val conditionText: String,
    val conditionIconUrl: String,

    //if we use WeatherModel for a certain hour,
    // we make maxTemp and minTemp empty
    val maxTemp: String,
    val minTemp: String,

    //if we use WeatherModel for a certain hour,
    // we make hours val empty
    //if we use WeatherModel for a certain day,
    // then we will use hours val for every-hour forecast
    val hours: String
)
