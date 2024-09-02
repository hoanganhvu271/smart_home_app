package com.hav.iot.data.api

import com.hav.iot.data.model.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api/turn-on")
    fun getOnLed(): Call<ResponseBody>

    @GET("api/turn-off")
    fun getOffLed(): Call<ResponseBody>
}