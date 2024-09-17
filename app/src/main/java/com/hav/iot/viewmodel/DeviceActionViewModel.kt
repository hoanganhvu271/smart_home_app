package com.hav.iot.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hav.iot.data.api.ApiRepository
import com.hav.iot.data.model.ActionTable
import com.hav.iot.data.model.DataResponse
import com.hav.iot.data.model.DataSensorTable
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class DeviceActionViewModel : ViewModel(){
    private val apiRepository : ApiRepository<Any?> = ApiRepository()

    private val _deviceActionList = MutableLiveData<List<ActionTable>>()
    val deviceActionList : LiveData<List<ActionTable>> = _deviceActionList

    private val _page = MutableLiveData<Int>()
    val page : LiveData<Int> = _page

    private val _pageSize = MutableLiveData<Int>()
    val pageSize  : LiveData<Int> = _pageSize

    private val _sortBy = MutableLiveData<String>()
    val sortBy : LiveData<String> = _sortBy

    private val _order = MutableLiveData<String>()
    val order : LiveData<String> = _order

    private val _filterBy = MutableLiveData<String>()
    val filterBy : LiveData<String> = _filterBy

    private val _filter = MutableLiveData<String>()
    val filter : LiveData<String> = _filter

    init{
        viewModelScope.launch {
            _page.value = 1
            _pageSize.value = 10
            _sortBy.value = "timestamp"
            _order.value = "DESC"
            _filter.value = ""
            _filterBy.value = ""

//            getDeviceActionFromApi()
        }
    }

//    private suspend fun getDeviceActionFromApi() {
//        apiRepository.apiService.getDeviceAction(_page.value!!, _pageSize.value!!, _sortBy.value!!, _order.value!!, _filter.value!!, _filterBy.value!!)
//            .enqueue(object : retrofit2.Callback<DataResponse<ActionTable>> {
//                override fun onResponse(
//                    p0: Call<DataResponse<ActionTable>>,
//                    p1: Response<DataResponse<ActionTable>>
//                ) {
//                    if(p1.isSuccessful){
//                        _deviceActionList.value = p1.body()?.data
//                    }
//                }
//
//                override fun onFailure(p0: Call<DataResponse<ActionTable>>, p1: Throwable) {
//                    Log.d("vu", "error get api")
//                    _deviceActionList.value = emptyList()
//                }
//
//            })
//    }
}