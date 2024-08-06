package com.terabyte.jetpackweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.terabyte.jetpackweather.screens.MainCard
import com.terabyte.jetpackweather.screens.TabLayout
import com.terabyte.jetpackweather.ui.theme.JetpackWeatherTheme


const val API_KEY = "1346c16f37d64d28bc2194344240507"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackWeatherTheme {
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

