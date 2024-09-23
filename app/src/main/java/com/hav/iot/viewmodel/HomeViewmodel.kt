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
import com.hav.iot.socket.SocketManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Integer.parseInt
import java.lang.Thread.sleep
import kotlin.math.floor

class HomeViewmodel : ViewModel() {
    private val apiRepository = ApiRepository<Any>()

    //color box
    private val _actionColorList = MutableLiveData<List<Int>>()
    val actionColorList: LiveData<List<Int>> = _actionColorList

    //action status
    private val _actionList = MutableLiveData<List<Int>>()
    val actionList: LiveData<List<Int>> = _actionList

    //status data
    private val _temperature = MutableLiveData<String>()
    val temperature: LiveData<String> = _temperature

    private val _humidity = MutableLiveData<String>()
    val humidity: LiveData<String> = _humidity

    private val _light = MutableLiveData<String>()
    val light: LiveData<String> = _light

    //chart data
    private val _tempChartData = MutableLiveData<List<Int>>()
    val tempChartData: LiveData<List<Int>> = _tempChartData

    private val _humidChartData = MutableLiveData<List<Int>>()
    val humidChartData: LiveData<List<Int>> = _humidChartData

    private val _lightChartData = MutableLiveData<List<Int>>()
    val lightChartData: LiveData<List<Int>> = _lightChartData


    fun changeButtonState(action: Int, id : Int){
        val list = _actionList.value?.toMutableList()!!
        list[id] = list[id] xor 1
        _actionList.value = list
        turnOnLed(action, id)
    }



    init {
        _actionList.value = listOf(0, 0, 0, 0)
        _actionColorList.value = listOf(0, 0, 0, 0)
        _tempChartData.value = listOf(0, 0, 0, 0, 0, 0)
        _humidChartData.value = listOf(0, 0, 0, 0, 0, 0)
        _lightChartData.value = listOf(0, 0, 0, 0, 0, 0)
    }

    fun updateMessage(message: String) {
        Log.d("vu", message)
        val data = Gson().fromJson(message, DataSensor::class.java)
        try {
            _temperature.value = data.temp.toString() + " °C"
            _humidity.value = data.humid.toString() + " %"
            _light.value = data.light.toString() 
            updateChart(data.temp, data.humid, data.light)

            updateLedStatus(data.led1, data.led2, data.led3, data.led4)

        } catch (e: Exception) {
            Log.d("vu", e.toString())
        }
    }

    private fun updateLedStatus(led1: Int, led2: Int, led3: Int, led4: Int) {
        val list = _actionList.value?.toMutableList()!!
        list[0] = led1
        list[1] = led2
        list[2] = led3
        list[3] = led4
        _actionList.value = list

        val colorList = _actionColorList.value?.toMutableList()!!
        colorList[0] = led1
        colorList[1] = led2
        colorList[2] = led3
        colorList[3] = led4
        _actionColorList.value = colorList
    }

    private fun updateChart(temp: Int, humid: Int, light: Int) {
        val tempData = _tempChartData.value?.toMutableList()!!
        val humidData = _humidChartData.value?.toMutableList()!!
        val lightData = _lightChartData.value?.toMutableList()!!

        tempData.removeAt(0)
        tempData.add(temp)
        _tempChartData.value = tempData

        humidData.removeAt(0)
        humidData.add(humid)
        _humidChartData.value = humidData

        lightData.removeAt(0)
        lightData.add(light)
        _lightChartData.value = lightData

//        Log.d("vu", tempData.toString())
//        Log.d("vu", humidData.toString())
//         Log.d("vu", lightData.toString())

    }

    fun turnOnLed(action: Int, deviceId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            if (action == 1) {
                val response = apiRepository.apiService.getOnLed(deviceId + 1)
                if (response.isSuccessful) {
                    // change colorList
                    withContext(Dispatchers.Main) {
                        val list = _actionColorList.value?.toMutableList()!!
                        list[deviceId] = 1
                        _actionColorList.value = list
                    }
                }

            } else {
                val response = apiRepository.apiService.getOffLed(deviceId + 1)

                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        val list = _actionColorList.value?.toMutableList()!!
                        list[deviceId] = 0
                        _actionColorList.value = list
                    }
                }


            }
        }

    }

}