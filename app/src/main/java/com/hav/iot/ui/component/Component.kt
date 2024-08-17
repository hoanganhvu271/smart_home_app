package com.hav.iot.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hav.iot.R
import com.hav.iot.ui.theme.MainBG
import com.hav.iot.ui.theme.SecondColor
import com.hav.iot.ui.theme.ThirdColor

@Composable
fun TextHeader(text: String) {
    val font = FontFamily(Font(R.font.notosans))
    Box(
        modifier = Modifier
            .padding(5.dp)
            .background(color = SecondColor)
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(5.dp),
            style = TextStyle(
                fontSize = 26.sp,
                fontWeight = FontWeight(1000),
                fontFamily = font
            ))
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview1() {
    TextHeader(text = "Controller")
}