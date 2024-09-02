package com.hav.iot.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.hav.iot.R
import com.hav.iot.ui.component.TextHeader
import com.hav.iot.ui.theme.MainBG
import com.hav.iot.ui.theme.OnColor
import com.hav.iot.ui.theme.SecondColor
import com.hav.iot.ui.theme.ThirdColor
import com.hav.iot.utils.getCurrentTime
import com.hav.iot.viewmodel.HomeViewmodel
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.hav.iot.ui.component.ComposeChart8
import com.hav.iot.ui.component.ComposeChart1
import com.hav.iot.ui.component.TextHeader2

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(viewmodel: HomeViewmodel) {

    val temperature by viewmodel.temperature.observeAsState(initial = "")
    val humidity by viewmodel.humidity.observeAsState(initial = "")
    val light by viewmodel.light.observeAsState(initial = "")

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val modelProducer = remember { CartesianChartModelProducer() }
    val modelProducerLine = remember { CartesianChartModelProducer() }
    LaunchedEffect(Unit) {
        modelProducer.runTransaction { columnSeries { series(y = listOf(2, 5, 3, 4, 2)) } }
        modelProducerLine.runTransaction { lineSeries { series(1, 8, 3, 7, 6) }}
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)

            ) {
            TextHeader(text = "PTIT Smart Room")
            Spacer(modifier = Modifier.size(20.dp))
            TimeShow()
            Spacer(modifier = Modifier.size(15.dp))
            Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
                StatusBar(temperature, humidity, light)
                Spacer(modifier = Modifier.size(8.dp))
                LazyColumn {
                    item {
                        ControllerContainer()
                        Spacer(modifier = Modifier.size(25.dp))
                        TextHeader2(text = "Data")
                        HorizontalPager(
                            count = 3,
                            state = pagerState,
                            modifier = Modifier.weight(1f)
                        ) { page ->
                            when (page) {
                                0 -> ChartCard("Humidity (%)", modelProducer = modelProducer, type = "column")
                                1 -> ChartCard("Temperature (°C)", modelProducer = modelProducerLine, type = "line")
                                2 -> ChartCard("Light (lux)", modelProducer = modelProducer, type = "column")
                            }
                        }
                        Spacer(modifier = Modifier.size(5.dp))
                        NextButton {
                            coroutineScope.launch {
                                nextPage(pagerState)
                            }

                        }
                    }
                }
            }

        }
    }
}

@Composable
fun ChartCard(name: String, modelProducer: CartesianChartModelProducer, type: String) {

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = 2.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = name,
                    fontSize = 18.sp,
                    color = ThirdColor,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = font)
                )
                Spacer(modifier = Modifier.size(10.dp))
                if(type == "column") {
                    ComposeChart8(modelProducer = modelProducer, modifier = Modifier.fillMaxWidth())
                } else {
                    ComposeChart1(modelProducer = modelProducer, modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }

}

@Composable
fun StatusBar( temperature: String, humidity: String, light: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(MainBG, OnColor),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = "Room status",
                    fontSize = 18.sp,
                    color = ThirdColor,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = font)
                )
                Spacer(modifier = Modifier.size(15.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatusItem(
                        R.drawable.ic_temperature,
                        "Temperature",
                        temperature,
                        modifier = Modifier.weight(1f)
                    )
                    StatusItem(
                        R.drawable.ic_humid,
                        "Humidity",
                        humidity,
                        modifier = Modifier.weight(1f)
                    )
                    StatusItem(
                        R.drawable.ic_light,
                        "Light",
                        light,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
//        }
    }
}

@Composable
fun StatusItem(icon: Int, name: String, value: String, modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = name,
            fontSize = 14.sp,
            color = ThirdColor,
            style = TextStyle(fontWeight = FontWeight.Normal, fontFamily = font)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = value,
            fontSize = 20.sp,
            color = ThirdColor,
            style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = font)
        )

    }
}

@Composable
fun NextButton(onclick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(painter =
        painterResource(id = R.drawable.ic_next),
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    onclick()
                }
                .clip(RoundedCornerShape(50.dp))
                .background(MainBG)
                .padding(8.dp)
        )
    }
}

@Composable
fun TimeShow() {

    val time = remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        while (true) {
            time.value = getCurrentTime()
            delay(1000L)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        shape = RoundedCornerShape(10.dp),
        elevation = 1.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 10.dp, 20.dp, 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(
                    text = "Time",
                    fontSize = 16.sp,
                    color = ThirdColor,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = font)
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = time.value,
                    fontSize = 16.sp,
                    color = ThirdColor,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontFamily = font)
                )
            }
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
private suspend fun nextPage(pagerState: PagerState) {
    val currentPage = pagerState.currentPage
    pagerState.animateScrollToPage((currentPage + 1) % 3)
}

private val font = androidx.compose.ui.text.font.FontFamily(
    androidx.compose.ui.text.font.Font(R.font.notosans)
)

@Composable
fun ControllerContainer() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondColor)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
        ) {
            Spacer(modifier = Modifier.height(3.dp))
//            TextHeader2(text = "Controller")
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                ControllerItem(icon = R.drawable.ic_light, name = "Smart Light", status = false)
                ControllerItem(icon = R.drawable.ic_ac, name = "Smart AC", status = true)
            }
            Spacer(modifier = Modifier.height(10.dp))
            LongControllerItem(icon = R.drawable.ic_fan, name = "Smart Fan", status = false)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}