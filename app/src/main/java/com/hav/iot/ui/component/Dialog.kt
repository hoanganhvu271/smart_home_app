package com.hav.iot.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hav.iot.R
import com.hav.iot.ui.theme.FourthColor
import com.hav.iot.ui.theme.MainBG
import com.hav.iot.ui.theme.OnColor
import com.hav.iot.ui.theme.ThirdColor
import com.hav.iot.viewmodel.DataSensorViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun FilterDialog( sort : List<Boolean>, filter : List<Boolean>,onDismiss: () -> Unit, dataSensorViewModel: DataSensorViewModel,
                  order : String) {
    val sortOptions = remember(sort) {
        mutableStateOf(
            sort
        )
    }

    val filterOptions = remember(filter) {
        mutableStateOf(
            filter
        )
    }
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = rememberModalBottomSheetState(),
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = MainBG,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 50.dp, start = 10.dp, end = 10.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    Text(
                        "Sort", modifier = Modifier.padding(8.dp), style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.notosans))

                        )
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    OrderButton(order, dataSensorViewModel.setOrder())
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ItemBorder(name = "Temperature",
                        onclick = {
                            run {
                                sortOptions.value = listOf(false, false, false, false)
                                sortOptions.value = sortOptions.value.toMutableList().also {
                                    it[0] = true
                                }
                            }
                        }, choosen = sortOptions.value[0]
                    )
                    ItemBorder(name = "Humidity",
                        onclick = {
                            run {
                                sortOptions.value = listOf(false, false, false, false)
                                sortOptions.value = sortOptions.value.toMutableList().also {
                                    it[1] = true
                                }
                            }
                        }, choosen = sortOptions.value[1]
                    )
                    ItemBorder(name = "Light",
                        onclick = {
                            run {
                                sortOptions.value = listOf(false, false, false, false)
                                sortOptions.value = sortOptions.value.toMutableList().also {
                                    it[2] = true
                                }
                            }
                        }, choosen = sortOptions.value[2]
                    )
                    ItemBorder(name = "Time",
                        onclick = {
                            run {
                                sortOptions.value = listOf(false, false, false, false)
                                sortOptions.value = sortOptions.value.toMutableList().also {
                                    it[3] = true
                                }
                            }
                        }, choosen = sortOptions.value[3]
                    )
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
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ItemBorder(name = "Temperature", {
                        run {
                            filterOptions.value = listOf(false, false, false, false)
                            filterOptions.value = filterOptions.value.toMutableList().also {
                                it[0] = true
                            }
                        }
                    }, filterOptions.value[0])
                    ItemBorder(name = "Humidity", {
                        run {
                            filterOptions.value = listOf(false, false, false, false)
                            filterOptions.value = filterOptions.value.toMutableList().also {
                                it[1] = true
                            }
                        }
                    }, filterOptions.value[1])
                    ItemBorder(name = "Light", {
                        run {
                            filterOptions.value = listOf(false, false, false, false)
                            filterOptions.value = filterOptions.value.toMutableList().also {
                                it[2] = true
                            }
                        }
                    }, filterOptions.value[2])
                    ItemBorder(name = "Time",
                        onclick = {
                            run {
                                filterOptions.value = listOf(false, false, false, false)
                                filterOptions.value = filterOptions.value.toMutableList().also {
                                    it[3] = true
                                }
                            }
                        }, choosen = filterOptions.value[3]
                    )
                }

            }

            Divider(thickness = 1.dp, color = FourthColor, modifier = Modifier.padding(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MainButton(
                    text = "Cancel",
                    onClick = {
                        onDismiss()
                    },
                    color = MainBG
                )
                MainButton(text = "Confirm", onClick = {
                    for (i in sortOptions.value.indices) {
                        if (sortOptions.value[i]) {
                            for(j in filterOptions.value.indices){
                                if(filterOptions.value[j]){
                                    dataSensorViewModel.changeOptions(i, j)
                                    onDismiss()
                                    break
                                }
                            }
                            break
                        }
                    }

                }, color = OnColor)
            }
            Spacer(modifier = Modifier.height(20.dp))

        }
    }
}

@Composable
fun OrderButton(order : String, onclick: () -> Unit) {
    val status = remember(order) {
        mutableStateOf(
            order
        )
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, ThirdColor, RoundedCornerShape(10.dp))
            .background(MainBG)
            .clickable { onclick() }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(status.value, fontSize = 12.sp)
    }
}

@Composable
fun ItemBorder(name: String, onclick: () -> Unit, choosen: Boolean) {
    val color = remember(choosen) {
        mutableStateOf(
            choosen
        )
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, ThirdColor, RoundedCornerShape(10.dp))
            .background(if (color.value) OnColor else MainBG)
            .clickable { onclick() }
            .padding(8.dp),
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
            .padding(vertical = 14.dp, horizontal = 7.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                onclick()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(26.dp),
            painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = null
        )
    }
}