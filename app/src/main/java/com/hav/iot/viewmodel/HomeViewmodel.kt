package com.hav.iot.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewmodel : ViewModel() {
    private val _temperature = MutableLiveData<String>()
    val temperature: LiveData<String> = _temperature

    private val _humidity = MutableLiveData<String>()
    val humidity: LiveData<String> = _humidity

    private val _light = MutableLiveData<String>()
    val light: LiveData<String> = _light

    fun updateTemperature(temperature: String) {
        _temperature.value = temperature
    }

    fun updateHumidity(humidity: String) {
        _humidity.value = humidity
    }

    fun updateLight(light: String) {
        _light.value = light
    }

    fun updateMessage(message: String) {
        Log.d( "socket","update")
        try
        {
            _temperature.value = message + " °C"
            _humidity.value = (Integer.parseInt(message) + 10).toString() + " %"
            _light.value = (Integer.parseInt(message) * 100).toString() + " lx"
        }
        catch (_: Exception){
            
        }
    }

}