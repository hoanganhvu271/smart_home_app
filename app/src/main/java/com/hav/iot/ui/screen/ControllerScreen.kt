package com.hav.iot.ui.screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hav.iot.R
import com.hav.iot.data.model.ActionTable
import com.hav.iot.ui.component.EmptyContent
import com.hav.iot.ui.component.SearchField
import com.hav.iot.ui.component.TextHeader
import com.hav.iot.ui.theme.MainBG
import com.hav.iot.ui.theme.OnColor
import com.hav.iot.ui.theme.SecondColor
import com.hav.iot.ui.theme.ThirdColor
import com.hav.iot.utils.TimeConvert
import com.hav.iot.viewmodel.DeviceActionViewModel
import com.hav.iot.viewmodel.HomeViewmodel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ControllerScreen(deviceActionViewModel: DeviceActionViewModel) {

    val actionList = deviceActionViewModel.deviceActionFlow.collectAsLazyPagingItems()

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    val coroutineScope = rememberCoroutineScope()

    //search
    val queryText by deviceActionViewModel.filter.collectAsState("")

    //focus
    val focusManager = LocalFocusManager.current

    fun onRefresh() {
        coroutineScope.launch {
            swipeRefreshState.isRefreshing = true
            deviceActionViewModel.reload()
            swipeRefreshState.isRefreshing = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit){
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
            .background(SecondColor)
            .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 45.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
        ) {
            Spacer(modifier = Modifier.height(3.dp))
            TextHeader(text = "Action")
            Spacer(modifier = Modifier.height(10.dp))
            SearchField(searchQuery = queryText, onQueryChanged = deviceActionViewModel::onQueryTextChanged, Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(20.dp))
            StatusTable(actionList = actionList, swipeRefreshState = swipeRefreshState) { onRefresh() }
        }
    }
}

@Composable
fun ControllerItem(icon: Int, name: String, realStatus : Int, actionStatus : Int, viewModel: HomeViewmodel, id : Int){
    val customFont = FontFamily(Font(R.font.notosans))

    val checkedStatus = remember { mutableIntStateOf(actionStatus) }

    val color = remember(realStatus) { mutableIntStateOf(realStatus) }

    Box(
        modifier = Modifier
            .width(160.dp)
            .height(120.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(if (color.intValue == 1) OnColor else MainBG)
            .padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(5.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                )
                Switch(
                    checked = checkedStatus.intValue == 1,
                    onCheckedChange = { isChecked ->
                        Log.d("vu", isChecked.toString() + id.toString())
                        if(isChecked){
                            viewModel.changeButtonState(1, id)
                        }
                        else{
                            viewModel.changeButtonState(0, id)
                        }
                        checkedStatus.value = if (isChecked) 1 else 0
                    },
                    modifier = Modifier.scale(1f),
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.White,
                        checkedTrackColor = Color.Black,
                        uncheckedTrackColor = ThirdColor
                    )
                )
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = name,
                    style = TextStyle(
                        fontFamily = customFont,
                        fontSize = 16.sp
                    ),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = ThirdColor
                )
            }
        }
    }
}


@Composable
fun LongControllerItem(icon: Int, name: String, realStatus : Int, actionStatus : Int, viewModel: HomeViewmodel, id: Int){
    val checkedStatus = remember { mutableIntStateOf(actionStatus) }

    val color = remember(realStatus) { mutableIntStateOf(realStatus) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(if (color.intValue == 1) OnColor else MainBG)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .weight(3f)
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.size(60.dp),
                )
                Spacer(modifier = Modifier.width(5.dp))
                Column(
                    modifier = Modifier
                        .padding(5.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    val customFont = FontFamily(Font(R.font.notosans))
                    Text(
                        text = name,
                        style = TextStyle(
                            fontFamily = customFont,
                            fontSize = 16.sp
                        ),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = ThirdColor
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "This is a controller item",
                        style = TextStyle(
                            fontFamily = customFont,
                            fontSize = 14.sp
                        ),
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Switch(
                    checked = checkedStatus.intValue == 1,
                    onCheckedChange = { isChecked ->
                        Log.d("vu", isChecked.toString() + id.toString())
                        if(isChecked){
                            viewModel.changeButtonState(1, id)
                        }
                        else{
                            viewModel.changeButtonState(0, id)
                        }
                        checkedStatus.intValue = if (isChecked) 1 else 0
                    },
                    modifier = Modifier.scale(1f),
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.White,
                        checkedTrackColor = Color.Black,
                        uncheckedTrackColor = ThirdColor
                    )
                )
            }
        }
    }
}

@Composable
fun StatusTable(actionList: LazyPagingItems<ActionTable>, swipeRefreshState: SwipeRefreshState, onRefresh: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp))
            .background(MainBG)
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column() {
            CellOfTable(
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.notosans))
                ),
                col1 = "ID",
                col2 = "Device",
                col3 = "Action",
                col4 = "Time"
            )
            Spacer(modifier = Modifier.height(5.dp))
            Divider( color = SecondColor, thickness = 1.dp)
                if (actionList.loadState.refresh is LoadState.Loading) {
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
                            if(actionList.itemCount == 0){
                                item {
                                   EmptyContent()
                                }
                            }
                            else{
                                items(actionList.itemCount) { index ->
                                    val action = actionList[index]
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(5.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        if (action != null) {
                                            CellOfTable(
                                                textStyle = TextStyle(
                                                    fontSize = 14.sp,
                                                    fontFamily = FontFamily(Font(R.font.notosans))
                                                ),
                                                col1 = action.id.toString(),
                                                col2 = action.device_id  ?: " ",
                                                col3 =  if (action.action == 1) "Turn on" else "Turn off",
                                                col4 = TimeConvert.dateToStringFormat(action.timestamp)
                                            )
                                        }
                                    }
                                }
                            }
                            // Handle load state for footer loading indicator
                            actionList.apply {
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
fun CellOfTable(textStyle: TextStyle, col1: String, col2: String, col3: String, col4 : String) {

    var color = Color.Black
    if (col3 == "Turn On") {
        color = OnColor
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(0.5f)
                .padding(3.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = col1,
                style = textStyle
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(3.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = col2,

                style = textStyle
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(3.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = col3,
                color = color,
                style = textStyle
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(3.dp),
            contentAlignment = Alignment.Center
        ) {
            SelectionContainer {
                Text(
                    text = col4,
                    style = textStyle,
                )
            }
        }

    }
}

//data class Action(
//    val id: Int,
//    val device: String,
//    val action: String,
//    val time: String
//)

//val actionList = listOf(
//    Action(1, "Light", "Turn On", "2024/09/02 12:00:00"),
//    Action(2, "AC", "Turn Off", "2024/09/02 12:30:00"),
//    Action(3, "Fan", "Turn On", "2024/09/02 13:00:00"),
//    Action(4, "Light", "Turn Off", "2024/09/02 13:30:00"),
//    Action(5, "AC", "Turn On", "2024/09/02 14:00:00"),
//    Action(6, "Fan", "Turn Off", "2024/09/02 14:30:00"),
//    Action(7, "Light", "Turn On", "2024/09/02 15:00:00"),
//    Action(8, "AC", "Turn Off", "2024/09/02 15:30:00"),
//    Action(9, "Fan", "Turn On", "2024/09/02 16:00:00"),
//    Action(10, "Light", "Turn Off", "2024/09/02 16:30:00"),
//    Action(11, "AC", "Turn On", "2024/09/02 17:00:00"),
//    Action(12, "Fan", "Turn Off", "2024/09/02 17:30:00"),
//    Action(13, "Light", "Turn On", "2024/09/02 18:00:00"),
//    Action(14, "AC", "Turn Off", "2024/09/02 18:30:00"),
//    Action(15, "Fan", "Turn On", "2024/09/02 19:00:00"),
//    Action(16, "Light", "Turn Off", "2024/09/02 19:30:00"),
//    Action(17, "AC", "Turn On", "2024/09/02 20:00:00"),
//    Action(18, "Fan", "Turn Off", "2024/09/02 20:30:00"),
//    Action(19, "Light", "Turn On", "2024/09/02 21:00:00"),
//    Action(20, "AC", "Turn Off", "2024/09/02 21:30:00"),
//)
