package com.hav.iot.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hav.iot.R
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

@Composable
fun TextHeader2(text: String) {
    val font = FontFamily(Font(R.font.notosans))
    Box(
        modifier = Modifier
            .padding(5.dp)
            .background(color = SecondColor)
            .fillMaxWidth()
    ) {
        Row{
            Text(
                text = text,
                modifier = Modifier.padding(5.dp),
                color = ThirdColor,
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight(1000),
                    fontFamily = font
                ))
            Spacer(modifier = Modifier.weight(1f))
            Icon(painter = painterResource(id = R.drawable.ic_chart), contentDescription = null)
        }
    }
}

@Composable
fun MainButton(text: String, onClick: () -> Unit, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color = color)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.notosans)),
                color = Color.Black,
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

