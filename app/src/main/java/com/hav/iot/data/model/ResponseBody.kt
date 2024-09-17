package com.hav.iot.data.model

import java.util.Date

data class ResponseBody (
    val message: String
)
data class DataResponse<T>(
    val message: String,
    val data : List<T>
)

data class DataSensorTable(
    val id : Int,
    val temperature : Int,
    val humidity :Int,
    val light: Int,
    val timestamp : Date
)

data class ActionTable(
    val id : Int,
    val deviceName: Int,
    val action : Int,
    val timestamp: Date
)





