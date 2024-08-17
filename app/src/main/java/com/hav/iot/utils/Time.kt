package com.hav.iot.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun getCurrentTime(): String {
    val current = System.currentTimeMillis()
    SimpleDateFormat("HH:mm:ss").format(Date(current))
    return SimpleDateFormat("HH:mm:ss").format(Date(current))
}