package com.hav.iot.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hav.iot.R
import com.hav.iot.data.model.DataSensorTable
import com.hav.iot.ui.component.MainButton
import com.hav.iot.ui.component.SearchField
import com.hav.iot.ui.component.TextHeader
import com.hav.iot.ui.theme.FourthColor
import com.hav.iot.ui.theme.MainBG
import com.hav.iot.ui.theme.OnColor
import com.hav.iot.ui.theme.SecondColor
import com.hav.iot.ui.theme.ThirdColor
import com.hav.iot.utils.TimeConvert
import com.hav.iot.viewmodel.DataSensorViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(dataSensorViewModel: DataSensorViewModel) {


    val dataSensorItems = dataSensorViewModel.dataSensorFlow.collectAsLazyPagingItems()

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    val coroutineScope = rememberCoroutineScope()

    //query
    val queryText by dataSensorViewModel.filter.collectAsState("")

    fun onRefresh() {
        coroutineScope.launch {
            swipeRefreshState.isRefreshing = true
            dataSensorViewModel.reload()
            swipeRefreshState.isRefreshing = false
        }
    }

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        FilterDialog(
            onDismiss = { showDialog = false }
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondColor)
            .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 55.dp)
    ) {
        Column {
            TextHeader("Data sensor")
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchField(searchQuery = queryText, onQueryChanged = dataSensorViewModel::onQueryTextChanged, Modifier.weight(10f))

                Spacer(modifier = Modifier.width(8.dp))

                FilterButton(onclick = { showDialog = true }, modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(20.dp))
            HistoryTable(dataSensorItems, swipeRefreshState, onRefresh = { onRefresh() })
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun FilterDialog(onDismiss: () -> Unit) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = rememberModalBottomSheetState(),
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = MainBG,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 30.dp, start = 10.dp, end = 10.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    "Sort", modifier = Modifier.padding(8.dp), style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.notosans))

                    )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    ItemBorder(name = "Temperature", {})
                    Spacer(modifier = Modifier.width(50.dp))
                    ItemBorder(name = "Humidity", {})
                    Spacer(modifier = Modifier.width(50.dp))
                    ItemBorder(name = "Light", {})
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    ItemBorder(name = "Time", {})
                }
            }

//            Divider(thickness = 1.dp, color = ThirdColor)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    "Filter", modifier = Modifier.padding(8.dp), style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.notosans))

                    )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    ItemBorder(name = "Temperature", {})
                    Spacer(modifier = Modifier.width(50.dp))
                    ItemBorder(name = "Humidity", {})
                    Spacer(modifier = Modifier.width(50.dp))
                    ItemBorder(name = "Light", {})
                }
            }

            Divider(thickness = 1.dp, color = FourthColor, modifier = Modifier.padding(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MainButton(text = "Cancel", onClick = onDismiss, color = MainBG)
                MainButton(text = "Confirm", onClick = onDismiss, color = OnColor)
            }
            Spacer(modifier = Modifier.height(20.dp))

        }
    }
}

@Composable
fun ItemBorder(name: String, onclick: () -> Unit) {
    var color by remember { mutableStateOf(MainBG) }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, ThirdColor, RoundedCornerShape(10.dp))
            .background(color)
            .padding(8.dp)
            .clickable {
                onclick()
                if (color == MainBG) {
                    color = OnColor
                } else {
                    color = MainBG
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(name)
    }
}

@Composable
fun FilterButton(onclick: () -> Unit, modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MainBG)
            .padding(7.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                onclick()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(22.dp),
            painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = null
        )
    }
}


@Composable
fun HistoryTable(dataSensorItems: LazyPagingItems<DataSensorTable>, swipeRefreshState : SwipeRefreshState, onRefresh: () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(MainBG, OnColor),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            )
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
        ) {
            CellOfHistoryTable(
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.notosans))
                ),
                col = listOf("ID", "Temp (°C)", "Hum (%)", "Light", "Time")
            )

            Spacer(modifier = Modifier.height(5.dp))
            Divider(color = SecondColor, thickness = 1.dp)
            if (dataSensorItems.loadState.refresh is LoadState.Loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = { onRefresh() }
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(dataSensorItems.itemCount) { index ->
                            val action = dataSensorItems[index]
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (action != null) {
                                    CellOfHistoryTable(
                                        textStyle = TextStyle(
                                            fontSize = 14.sp,
                                            fontFamily = FontFamily(Font(R.font.notosans))
                                        ),
                                        col = listOf(
                                            action.id.toString(),
                                            action.temperature.toString(),
                                            action.humidity.toString(),
                                            action.light.toString(),
                                            TimeConvert.dateToStringFormat(action.timestamp)
                                        )
                                    )
                                }
                            }
                            Divider(color = SecondColor, thickness = 1.dp)
                        }
                        // Handle load state for footer loading indicator
                        dataSensorItems.apply {
                            when {
                                loadState.append is LoadState.Loading -> {
                                    item {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator()
                                        }
                                    }
                                }

                                loadState.append is LoadState.Error -> {
                                    item {
                                        Text(
                                            text = "Error loading more data",
                                            color = Color.Red,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

            }

        }
    }
}

@Composable
fun CellOfHistoryTable(textStyle: TextStyle, col: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in col.indices) {
            val w = if (i == 0) 0.5f else if (i == 4) 1.2f else 1f
            Box(
                modifier = Modifier
                    .weight(w)
                    .padding(3.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = col[i],
                    style = textStyle
                )
            }
        }
    }
}


//private val historyList = listOf(
//    HistoryData(1, "25", "50", "500", "2024/09/02 12:00:00"), // On
//    HistoryData(2, "26", "51", "1000", "2024/09/02 12:01:00"), // Off
//    HistoryData(3, "27", "52", "1000", "2024/09/02 12:02:00"), // Off
//    HistoryData(4, "28", "53", "1000", "2024/09/02 12:03:00"), // Off
//    HistoryData(5, "29", "54", "500", "2024/09/02 12:04:00"), // On
//    HistoryData(6, "30", "55", "1000", "2024/09/02 12:05:00"), // Off
//    HistoryData(7, "31", "56", "500", "2024/09/02 12:06:00"), // On
//    HistoryData(8, "32", "57", "500", "2024/09/02 12:07:00"), // On
//    HistoryData(9, "33", "58", "1000", "2024/09/02 12:08:00"), // Off
//    HistoryData(10, "34", "59", "500", "2024/09/02 12:09:00"), // On
//    HistoryData(11, "35", "60", "500", "2024/09/02 12:10:00"), // On
//    HistoryData(12, "36", "61", "500", "2024/09/02 12:11:00"), // On
//    HistoryData(13, "37", "62", "1000", "2024/09/02 12:12:00"), // Off
//    HistoryData(14, "38", "63", "500", "2024/09/02 12:13:00"), // On
//    HistoryData(15, "39", "64", "500", "2024/09/02 12:14:00"), // On
//    HistoryData(16, "40", "65", "1000", "2024/09/02 12:15:00"), // Off
//    HistoryData(17, "41", "66", "500", "2024/09/02 12:16:00"), // On
//    HistoryData(18, "42", "67", "1000", "2024/09/02 12:17:00"), // Off
//    HistoryData(19, "43", "68", "500", "2024/09/02 12:18:00"), // On
//    HistoryData(20, "44", "69", "500", "2024/09/02 12:19:00"), // On
//    HistoryData(21, "45", "70", "1000", "2024/09/02 12:20:00"), // Off
//    HistoryData(22, "46", "71", "500", "2024/09/02 12:21:00"), // On
//    HistoryData(23, "47", "72", "1000", "2024/09/02 12:22:00"), // Off
//    HistoryData(24, "48", "73", "500", "2024/09/02 12:23:00"), // On
//)


//
//data class HistoryData(
//    val id: Int,
//    val temperature: String,
//    val humidity: String,
//    val light: String,
//    val time: String
//)