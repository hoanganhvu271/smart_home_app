package com.hav.iot.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hav.iot.data.api.ApiRepository
import com.hav.iot.data.model.DataResponse
import com.hav.iot.data.model.DataSensorTable
import com.hav.iot.data.model.ResponseBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataSensorViewModel : ViewModel() {
    val apiRepository: ApiRepository = ApiRepository()

    private val _dataSensorList = MutableLiveData<List<DataSensorTable>>()
    val dataSensorList: LiveData<List<DataSensorTable>> = _dataSensorList

    private val _page = MutableLiveData<Int>()
    val page: LiveData<Int> = _page

    private val _pageSize = MutableLiveData<Int>()
    val pageSize: LiveData<Int> = _pageSize

    private val _sortBy = MutableLiveData<String>()
    val sortBy: LiveData<String> = _sortBy

    private val _order = MutableLiveData<String>()
    val order: LiveData<String> = _order

    private val _filterBy = MutableLiveData<String>()
    val filterBy: LiveData<String> = _filterBy

    private val _filter = MutableLiveData<String>()
    val filter: LiveData<String> = _filter

    //loading for next page
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {
        viewModelScope.launch {
            _loading.value = false
            _page.value = 1
            _pageSize.value = 10
            _sortBy.value = "timestamp"
            _order.value = "DESC"
            _filter.value = ""
            _filterBy.value = ""
            getDataSensorFromApi()

//            Log.d("vu", _dataSensorList.value!!.size.toString())
        }
    }

    private fun addElementToDataList(data : List<DataSensorTable>){
        val currentList = _dataSensorList.value ?: mutableListOf()
        val updatedList = currentList.toMutableList()
        updatedList.addAll(data)

        _dataSensorList.value = updatedList
        Log.d("vu", (_dataSensorList.value as MutableList<DataSensorTable>).size.toString())
    }

    fun loadMoreItems() {
        _page.value = _page.value!! + 1
        _loading.value = true

        apiRepository.apiService.getDataSensor(
            _page.value!!,
            _pageSize.value!!,
            _sortBy.value!!,
            _order.value!!,
            _filter.value!!,
            _filterBy.value!!
        )
            .enqueue(object : Callback<DataResponse<DataSensorTable>> {
                override fun onResponse(
                    p0: Call<DataResponse<DataSensorTable>>,
                    p1: Response<DataResponse<DataSensorTable>>
                ) {
                    Log.d("vu", "get: 500")
                    if (p1.isSuccessful) {
                        Log.d("vu", "get: 200" + p1.body()?.data?.size.toString())
                        p1.body()?.data?.let { addElementToDataList(it) }
                        _loading.value = false
                    }
                }

                override fun onFailure(p0: Call<DataResponse<DataSensorTable>>, p1: Throwable) {
                    Log.d("vu", "error get api")
                    _loading.value = false
                }

            })


    }

    private fun getDataSensorFromApi()  {
        apiRepository.apiService.getDataSensor(
            _page.value!!,
            _pageSize.value!!,
            _sortBy.value!!,
            _order.value!!,
            _filter.value!!,
            _filterBy.value!!
        )
            .enqueue(object : Callback<DataResponse<DataSensorTable>> {
                override fun onResponse(
                    p0: Call<DataResponse<DataSensorTable>>,
                    p1: Response<DataResponse<DataSensorTable>>
                ) {
                    Log.d("vu", "get: 500")
                    if (p1.isSuccessful) {
                        Log.d("vu", "get: 200" + p1.body()?.data?.size.toString())
                        _dataSensorList.value =  p1.body()?.data
                       _loading.value = false
                    }
                }

                override fun onFailure(p0: Call<DataResponse<DataSensorTable>>, p1: Throwable) {
                    Log.d("vu", "error get api")
                    _dataSensorList.value = emptyList()
                    _loading.value = false
                }

            })
    }
}