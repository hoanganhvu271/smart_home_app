package com.hav.iot.data.api

import com.hav.iot.data.model.ActionTable
import com.hav.iot.data.model.DataResponse
import com.hav.iot.data.model.DataSensorTable
import com.hav.iot.data.model.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/turn-on")
    fun getOnLed(@Query("id") deviceId : Int): Call<ResponseBody>

    @GET("api/turn-off")
    fun getOffLed(@Query("id") deviceId : Int): Call<ResponseBody>

    @GET("api/get-data-sensor")
    fun getDataSensor(
        @Query("page") page : Int, @Query("pageSize") pageSize : Int,
        @Query("sortBy") sortBy : String, @Query("order") order : String,
        @Query("filter") filter : String, @Query("filterBy") filterBy: String
    ): Call<DataResponse<DataSensorTable>>

    @GET("api/get-device-action")
    fun getDeviceAction(
        @Query("page") page : Int, @Query("pageSize") pageSize : Int,
        @Query("sortBy") sortBy : String, @Query("order") order : String,
        @Query("filter") filter : String, @Query("filterBy") filterBy: String
    ): Call<DataResponse<ActionTable>>

}