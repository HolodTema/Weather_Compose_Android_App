package com.terabyte.jetpackweather.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Preview(showBackground = true)
@Composable
fun ListItem() {
    Card(
        border = BorderStroke(1.dp, Color.White),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Column(
                modifier = Modifier
                    .padding(start = 8.dp, top = 5.dp, bottom = 5.dp)
            ) {
                Text(
                    text = "12:00",
                    color = Color.White
                )
                Text(
                    text = "Sunny",
                    color = Color.White
                )
            }

            Text(
                text = "28°C",
                color = Color.White,
                style = TextStyle(fontSize = 22.sp)
            )

            AsyncImage(
                model = "https://cdn.weatherapi.com/weather/64x64/night/113.png",
                contentDescription = "weatherIconInListItem",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(55.dp)
                    .padding(end = 8.dp)
            )
        }
    }
}
