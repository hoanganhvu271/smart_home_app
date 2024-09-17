package com.hav.iot.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.hav.iot.data.api.ApiRepository
import com.hav.iot.data.model.DataResponse
import com.hav.iot.data.model.DataSensorTable
import com.hav.iot.data.model.ResponseBody
import com.hav.iot.paging.DataSensorPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataSensorViewModel : ViewModel() {
    val apiRepository: ApiRepository<Any?> = ApiRepository()

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


    val dataSensorFlow = Pager(PagingConfig(initialLoadSize = 10, pageSize = 10,  prefetchDistance = 5, enablePlaceholders = false)) {
        DataSensorPagingSource(
            apiRepository,
            _sortBy.value ?: "timestamp",
            _order.value ?: "DESC",
            _filter.value ?: "",
            _filterBy.value ?: ""
        )
    }.flow.cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            _page.value = 1
            _pageSize.value = 10
            _sortBy.value = "timestamp"
            _order.value = "DESC"
            _filter.value = ""
            _filterBy.value = ""
        }
    }
}