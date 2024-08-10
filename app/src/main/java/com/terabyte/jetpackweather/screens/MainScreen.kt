package com.terabyte.jetpackweather.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.terabyte.jetpackweather.R
import com.terabyte.jetpackweather.data.WeatherModel
import com.terabyte.jetpackweather.json.JsonManager
import com.terabyte.jetpackweather.json.JsonManager.getWeatherByHours
import com.terabyte.jetpackweather.ui.MainList
import com.terabyte.jetpackweather.ui.preview.CurrentDayProvider
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

@Preview(showBackground = true, backgroundColor = 0x000000)
@Composable
fun MainCard(@PreviewParameter(CurrentDayProvider::class) currentDay: MutableState<WeatherModel>, onClickSync: () -> Unit = {}, onClickSearch: () -> Unit = {}) {
    Column{
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            border = BorderStroke(1.dp, Color.White),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(10.dp)


        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween

                ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp, start = 8.dp),
                        text = currentDay.value.time,
                        style = TextStyle(
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    )
                    AsyncImage(
                        model = "https:${currentDay.value.conditionIconUrl}",
                        contentDescription = "weatherIcon",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(55.dp)
                            .padding(end = 8.dp, top = 3.dp)
                    )
                }
                Text(
                    text = currentDay.value.city,
                    style = TextStyle(fontSize = 24.sp),
                    color = Color.White,
                    modifier = Modifier
                        .offset(y = -12.dp)
                )
                Text(
                    text = if(currentDay.value.currentTemp.isNotEmpty()) {
                        "${currentDay.value.currentTemp.toFloat().toInt()}°C"
                    } else {
                        "Min/Max: ${
                            currentDay.value.minTemp.toFloat().toInt()
                        }°C / ${currentDay.value.maxTemp.toFloat().toInt()}°C"
                    },
                    style = if(currentDay.value.currentTemp.isNotEmpty()) {
                        TextStyle(fontSize = 65.sp)
                    }
                    else {
                        TextStyle(fontSize = 30.sp, textAlign = TextAlign.Center)
                    },
                    color = Color.White
                )
                Text(
                    text = currentDay.value.conditionText,
                    style = TextStyle(fontSize = 16.sp),
                    color = Color.White
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    IconButton(
                        onClick = {
                            onClickSearch()
                        },
                        ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "iconButton",
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "Min/Max: ${
                            currentDay.value.minTemp.toFloat().toInt()
                        }°C / ${currentDay.value.maxTemp.toFloat().toInt()}°C",
                        style = TextStyle(fontSize = 16.sp),
                        color = Color.White
                    )
                    IconButton(
                        onClick = {
                            onClickSync()
                        },

                        ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_sync),
                            contentDescription = "iconSync",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(daysList: MutableState<List<WeatherModel>>, hoursList: MutableState<List<WeatherModel>>, @PreviewParameter(CurrentDayProvider::class) currentDay: MutableState<WeatherModel>) {
    val tabList = listOf("Hours", "Days")
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage

    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .padding(start = 10.dp, end = 10.dp)
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { pos ->
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .tabIndicatorOffset(pos[tabIndex])
                )
            },
            containerColor = Color.Transparent,
            contentColor = Color.White,
        ) {
            tabList.forEachIndexed { index, text ->
                Tab(
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = text
                        )
                    }
                )
            }
        }
        HorizontalPager(
            count = tabList.size,
            state = pagerState,
            modifier = Modifier
                .weight(1f)
        ) { index ->
            JsonManager.getWeatherByHours(currentDay.value.hours) {
                hoursList.value = it
            }
            val list = when(index) {
                0 -> hoursList.value
                1 -> daysList.value
                else -> daysList.value
            }
            MainList(list, currentDay)
        }
    }
}



