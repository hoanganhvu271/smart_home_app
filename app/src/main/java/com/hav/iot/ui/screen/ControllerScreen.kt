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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hav.iot.R
import com.hav.iot.ui.component.TextHeader
import com.hav.iot.ui.theme.MainBG
import com.hav.iot.ui.theme.OnColor
import com.hav.iot.ui.theme.SecondColor
import com.hav.iot.ui.theme.ThirdColor

@Composable
fun ControllerScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondColor)
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
        ) {
            Spacer(modifier = Modifier.height(3.dp))
            TextHeader(text = "Controller")
            Spacer(modifier = Modifier.height(10.dp))
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
            StatusTable()
        }
    }
}

@Composable
fun ControllerItem(icon: Int, name: String, status: Boolean) {
    val checkedStatus = remember { mutableStateOf(status) }
    val customFont = FontFamily(Font(R.font.notosans))
    var color = if (checkedStatus.value) OnColor else MainBG

    Box(
        modifier = Modifier
            .width(170.dp)
            .height(120.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color)
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
                    checked = checkedStatus.value,
                    onCheckedChange = {
                        checkedStatus.value = it
                        color = if (it) OnColor else MainBG
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
                        fontSize = 18.sp
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
fun LongControllerItem(icon: Int, name: String, status: Boolean) {
    val checkedStatus = remember { mutableStateOf(status) }
    var color = if (checkedStatus.value) OnColor else MainBG
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color)
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
                            fontSize = 18.sp
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
                    checked = checkedStatus.value,
                    onCheckedChange = {
                        checkedStatus.value = it
                        color = if (it) OnColor else MainBG
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
fun StatusTable() {
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
                col1 = "Device",
                col2 = "Action",
                col3 = "Time"
            )
            Spacer(modifier = Modifier.height(5.dp))
            Divider( color = SecondColor, thickness = 1.dp)
            LazyColumn(modifier = Modifier.fillMaxSize()) {

                items(actionList.size) { index ->
                    val action = actionList[index]
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CellOfTable(
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.notosans))
                            ),
                            col1 = action.device,
                            col2 = action.action,
                            col3 = action.time
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CellOfTable(textStyle: TextStyle, col1: String, col2: String, col3: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
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
            var color = Color.Black
            if (col2 == "Turn On") {
                color = OnColor
            }
            Text(
                text = col2,
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
            Text(
                text = col3,
                style = textStyle
            )
        }
    }
}

data class Action(
    val device: String,
    val action: String,
    val time: String
)

val actionList = listOf(
    Action("Light", "Turn On", "12:00"),
    Action("AC", "Turn Off", "12:30"),
    Action("Fan", "Turn On", "13:00"),
    Action("Light", "Turn Off", "13:30"),
    Action("AC", "Turn On", "14:00"),
    Action("Fan", "Turn Off", "14:30"),
    Action("Light", "Turn On", "15:00"),
    Action("AC", "Turn Off", "15:30"),
    Action("Fan", "Turn On", "16:00"),
    Action("Light", "Turn Off", "16:30"),
    Action("AC", "Turn On", "17:00"),
    Action("Fan", "Turn Off", "17:30"),
    Action("Light", "Turn On", "18:00"),
    Action("AC", "Turn Off", "18:30"),
    Action("Fan", "Turn On", "19:00"),
    Action("Light", "Turn Off", "19:30"),
    Action("AC", "Turn On", "20:00"),
    Action("Fan", "Turn Off", "20:30"),
    Action("Light", "Turn On", "21:00"),
    Action("AC", "Turn Off", "21:30"),

    )