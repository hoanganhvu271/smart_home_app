package com.hav.iot.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class TimeConvert {
    companion object{
        @SuppressLint("SimpleDateFormat")
        fun dateToStringFormat(date : Date) : String{
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            return sdf.format(date)
        }
    }
}