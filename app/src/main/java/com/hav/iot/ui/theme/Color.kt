package com.hav.iot.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val MainBG = Color(0xFFFFFFFF)
val SecondColor = Color(0xFFF2F2F2)
val ThirdColor = Color(0xFF464646)
val FourthColor = Color(0xFF9C9999)

val OnColor = Color(0xFF61D4DF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Gradient color status
val hotColor = Color(0xFFE57373)
val coldColor = Color(0xFF277EC4)

val normalStatus = Brush.linearGradient(
    colors = listOf(MainBG, OnColor),
    start = Offset(0f, 0f),
    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
)

val hotStatus = Brush.linearGradient(
    colors = listOf(MainBG, hotColor),
    start = Offset(0f, 0f),
    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
)

val coldStatus = Brush.linearGradient(
    colors = listOf(MainBG, coldColor),
    start = Offset(0f, 0f),
    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
)