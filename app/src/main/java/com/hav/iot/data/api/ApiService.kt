package com.hav.iot.data.api

import com.hav.iot.data.model.ActionTable
import com.hav.iot.data.model.DataResponse
import com.hav.iot.data.model.DataSensorTable
import com.hav.iot.data.model.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/turn-on")
    suspend fun getOnLed(@Query("id") deviceId : Int): Response<ResponseBody>

    @GET("api/turn-off")
    suspend fun getOffLed(@Query("id") deviceId : Int): Response<ResponseBody>

    @GET("api/get-data-sensor")
    suspend fun getDataSensor(
        @Query("page") page : Int,
        @Query("pageSize") pageSize : Int,
        @Query("sortBy") sortBy : String,
        @Query("order") order : String,
        @Query("filter") filter : String,
        @Query("filterBy") filterBy: String
    ): Response<DataResponse<DataSensorTable>>

    @GET("api/get-device-action")
    suspend fun getDeviceAction(
        @Query("page") page : Int,
        @Query("pageSize") pageSize : Int,
        @Query("sortBy") sortBy : String,
        @Query("order") order : String,
        @Query("filter") filter : String,
        @Query("filterBy") filterBy: String
    ): Response<DataResponse<ActionTable>>
}
