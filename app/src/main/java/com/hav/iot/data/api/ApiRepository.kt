package com.hav.iot.data.api

import com.hav.iot.data.model.ActionTable
import com.hav.iot.data.model.ResponseBody
import com.hav.iot.data.model.DataResponse
import com.hav.iot.data.model.DataSensorTable
import retrofit2.Response

class ApiRepository<DataSensorTable> {
    val apiService: ApiService = RetrofitInstance.retrofit!!.create(ApiService::class.java)

    suspend fun turnOnLed(deviceId: Int): Response<ResponseBody> {
        return apiService.getOnLed(deviceId)
    }

    suspend fun turnOffLed(deviceId: Int): Response<ResponseBody> {
        return apiService.getOffLed(deviceId)
    }

    suspend fun getDataSensor(
        page: Int, pageSize: Int,
        sortBy: String, order: String,
        filter: String, filterBy: String
    ): Response<DataResponse<com.hav.iot.data.model.DataSensorTable>> {
        return apiService.getDataSensor(page, pageSize, sortBy, order, filter, filterBy)
    }

    suspend fun getDeviceAction(
        page: Int, pageSize: Int,
        sortBy: String, order: String,
        filter: String, filterBy: String
    ): Response<DataResponse<ActionTable>> {
        return apiService.getDeviceAction(page, pageSize, sortBy, order, filter, filterBy)
    }
}
