package com.hav.iot.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.hav.iot.data.model.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.hav.iot.data.api.ApiRepository
import com.hav.iot.data.model.DataSensor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Thread.sleep
import kotlin.math.floor

class HomeViewmodel : ViewModel() {
    private val apiRepository = ApiRepository()

    //status data
    private val _temperature = MutableLiveData<String>()
    val temperature: LiveData<String> = _temperature

    private val _humidity = MutableLiveData<String>()
    val humidity: LiveData<String> = _humidity

    private val _light = MutableLiveData<String>()
    val light: LiveData<String> = _light

    //chart data
    private val _tempChartData = MutableLiveData<List<Int>>()
    val tempChartData : LiveData<List<Int>> = _tempChartData

    private val _humidChartData = MutableLiveData<List<Int>>()
    val humidChartData : LiveData<List<Int>> = _humidChartData

    private val _lightChartData = MutableLiveData<List<Int>>()
    val lightChartData : LiveData<List<Int>> = _tempChartData

    //get set
    fun updateTemperature(temperature: String) {
        _temperature.value = temperature
    }

    fun updateHumidity(humidity: String) {
        _humidity.value = humidity
    }

    fun updateLight(light: String) {
        _light.value = light
    }

    init{
        viewModelScope.launch(Dispatchers.IO) {
            val arr = mutableListOf(1, 2, 3, 4, 5)

           while(true){
               for (i in 0 until arr.size) {
                   arr[i] += (Math.random() * 10).toInt()
               }
//               Log.d("vu", arr.toString())
               withContext(Dispatchers.Main) {
                   _tempChartData.value = arr.toList()
                   _humidChartData.value = arr.toList()
                   _lightChartData.value = arr.toList()
               }
               sleep(1000)
           }
        }
    }

//    private fun updateChartData()

    fun updateMessage(message: String) {
//        Log.d( "socket","update")
//        val data : DataSensor = Gson().fromJson(message, DataSensor::class.java)
//        try
//        {
//            _temperature.value = data.temperature.toString() + " °C"
//            _humidity.value = data.humidity.toString() + " %"
//            _light.value = data.light.toString() + " lx"
//        }
//        catch (e: Exception){
//            Log.d("vu", e.toString())
//        }
    }

    fun turnOnLed(action: Int, deviceId: Int) {
        if(action == 1)
        {
            apiRepository.apiService.getOnLed(deviceId).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        // Handle the response here
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    // Handle the failure here
                }
            })
        }
        else
        {
            apiRepository.apiService.getOffLed(deviceId).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        // Handle the response here
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    // Handle the failure here
                }
            })
        }

    }

}