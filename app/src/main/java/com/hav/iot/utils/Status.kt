package com.hav.iot.utils

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.hav.iot.ui.theme.coldStatus
import com.hav.iot.ui.theme.hotStatus
import com.hav.iot.ui.theme.normalStatus

class Status {
    companion object{
        fun getStatus(temperature: String): Brush {
            if(temperature == "") return normalStatus
            val temp = temperature.toInt()
            return when {
                temp < 20 -> coldStatus
                temp in 20..28 -> normalStatus
                else -> hotStatus
            }
        }
    }
}