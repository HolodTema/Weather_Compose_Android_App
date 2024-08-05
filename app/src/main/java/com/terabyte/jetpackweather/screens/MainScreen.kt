package com.terabyte.jetpackweather.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.terabyte.jetpackweather.R
import com.terabyte.jetpackweather.ui.theme.BlueLight


@Preview(showBackground = true)
@Composable
fun MainScreen() {
    Image(
        painter = painterResource(id = R.drawable.main_screen_background),
        contentDescription = "background",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                .fillMaxWidth()
                .background(color = BlueLight)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp, start = 8.dp),
                        text = "20 June 2022 13:00",
                        style = TextStyle(
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    )
                    AsyncImage(
                        model = "https://cdn.weatherapi.com/weather/64x64/night/113.png",
                        contentDescription = "weatherIcon",
                        modifier = Modifier
                            .size(35.dp)
                            .padding(end = 8.dp, top = 3.dp)
                    )
                }
            }
        }
    }

}