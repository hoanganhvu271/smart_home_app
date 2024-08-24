package com.hav.iot.ui.screen

import androidx.compose.foundation.background
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
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hav.iot.R
import com.hav.iot.ui.component.TextHeader
import com.hav.iot.ui.theme.MainBG
import com.hav.iot.ui.theme.OnColor
import com.hav.iot.ui.theme.SecondColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondColor)
            .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 55.dp)
    ){
        Column {
            TextHeader("History")
            Spacer(modifier = Modifier.height(20.dp))
            SearchBar(query = "", onQueryChange = {}, onSearch = {}, active = false, onActiveChange = {}, trailingIcon = {
                Icon(
                    painter =  painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }, modifier = Modifier.fillMaxWidth().height(40.dp).background(Color.Transparent), colors = SearchBarDefaults.colors(
                containerColor = MainBG,
            )
            ){
            }
            Spacer(modifier = Modifier.height(20.dp))
            HistoryTable()
        }

    }
}

@Composable
fun HistoryTable() {
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
            ),
        contentAlignment = Alignment.Center
    ){
        Column() {

               CellOfHistoryTable(
                   textStyle = TextStyle(
                       fontSize = 14.sp,
                       fontWeight = FontWeight.Bold,
                       fontFamily = FontFamily(Font(R.font.notosans))
                   ),
                   col = listOf("Temp (°C)", "Hum (%)", "Light","Time")
               )

            Spacer(modifier = Modifier.height(5.dp))
            Divider( color = SecondColor, thickness = 1.dp)
            LazyColumn(modifier = Modifier.fillMaxSize()) {

                items(historyList.size) { index ->
                    val action = historyList[index]
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CellOfHistoryTable(
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.notosans))
                            ),
                            col = listOf( action.temperature, action.humidity, action.light, action.time)
                        )
                    }
                    Divider( color = SecondColor, thickness = 1.dp)
                }
            }
        }
    }
}

@Composable
fun CellOfHistoryTable( textStyle: TextStyle, col: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in col) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(3.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = i,
                    style = textStyle
                )
            }
        }
    }
}

private val historyList = listOf(
    HistoryData(1, "25", "50", "On", "12:00"),
    HistoryData(2, "26", "51", "Off", "12:01"),
    HistoryData(3, "27", "52", "Off", "12:02"),
    HistoryData(4, "28", "53", "Off", "12:03"),
    HistoryData(5, "29", "54", "On", "12:04"),
    HistoryData(6, "30", "55", "Off", "12:05"),
    HistoryData(7, "31", "56", "On", "12:06"),
    HistoryData(8, "32", "57", "On", "12:07"),
    HistoryData(9, "33", "58", "Off", "12:08"),
    HistoryData(10, "34", "59", "On", "12:09"),
    HistoryData(11, "35", "60", "On", "12:10"),
    HistoryData(12, "36", "61", "On", "12:11"),
    HistoryData(13, "37", "62", "Off", "12:12"),
    HistoryData(14, "38", "63", "on", "12:13"),
    HistoryData(15, "39", "64", "On", "12:14"),
    HistoryData(16, "40", "65", "Off", "12:15"),
    HistoryData(17, "41", "66", "On", "12:16"),
    HistoryData(18, "42", "67", "Off", "12:17"),
    HistoryData(19, "43", "68", "On", "12:18"),
    HistoryData(20, "44", "69", "On", "12:19"),
    HistoryData(21, "45", "70", "Off", "12:20"),
    HistoryData(22, "46", "71", "On", "12:21"),
    HistoryData(23, "47", "72", "Off", "12:22"),
    HistoryData(24, "48", "73", "On", "12:23"),
)

data class HistoryData(val id: Int, val temperature: String, val humidity: String, val light: String, val time: String)














