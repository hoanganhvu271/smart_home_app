package com.hav.iot.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.text.selection.SelectionContainer
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
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
import com.hav.iot.ui.component.EmptyContent
import com.hav.iot.ui.component.FilterButton
import com.hav.iot.ui.component.FilterDialog
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

    val sortOptions by dataSensorViewModel.sortOptions.observeAsState(listOf(false, false, false, true))
    val filterOptions by dataSensorViewModel.filterOptions.observeAsState(listOf(false, false, false, true))


    //query
    val queryText by dataSensorViewModel.filter.collectAsState("")

    //order
    val orderState by dataSensorViewModel.order.observeAsState("DESC")


    //focus
    val focusManager = LocalFocusManager.current

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
            sort = sortOptions,
            filter = filterOptions,
            onDismiss = { showDialog = false },
            dataSensorViewModel,
            order = orderState
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
            .background(SecondColor)
            .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 45.dp)
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
                SearchField(
                    searchQuery = queryText,
                    onQueryChanged = dataSensorViewModel::onQueryTextChanged,
                    Modifier.weight(8f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                FilterButton(onclick = { showDialog = true }, modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(20.dp))
            HistoryTable(dataSensorItems, swipeRefreshState, onRefresh = { onRefresh() })
        }

    }
}

@Composable
fun HistoryTable(
    dataSensorItems: LazyPagingItems<DataSensorTable>,
    swipeRefreshState: SwipeRefreshState,
    onRefresh: () -> Unit
) {
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
                col = listOf("ID", "Temp (°C)", "Hum (%)", "Light", "Dust", "Time")
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
                        if(dataSensorItems.itemCount == 0){
                            item {
                                EmptyContent()
                            }
                        }
                        else{
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
                                                fontSize = 12.sp,
                                                fontFamily = FontFamily(Font(R.font.notosans))
                                            ),
                                            col = listOf(
                                                action.id.toString(),
                                                action.temperature.toString(),
                                                action.humidity.toString(),
                                                action.light.toString(),
                                                action.dust.toString(),
                                                TimeConvert.dateToStringFormat(action.timestamp)
                                            )
                                        )
                                    }
                                }
                                Divider(color = SecondColor, thickness = 1.dp)
                            }
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
                                            text = "End of data",
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
                if (i == 4) {
                    SelectionContainer {
                        Text(
                            text = col[i],
                            style = textStyle
                        )
                    }

                } else {
                    Text(
                        text = col[i],
                        style = textStyle
                    )
                }

            }
        }
    }
}
