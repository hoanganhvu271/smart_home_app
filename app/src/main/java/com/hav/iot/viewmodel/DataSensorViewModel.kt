package com.hav.iot.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hav.iot.data.api.ApiRepository
import com.hav.iot.data.model.DataResponse
import com.hav.iot.data.model.DataSensorTable
import com.hav.iot.data.model.ResponseBody
import com.hav.iot.paging.DataSensorPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataSensorViewModel : ViewModel() {
    private val apiRepository: ApiRepository<Any?> = ApiRepository()

    //sort dialog options state
    private val _sortOptions = MutableLiveData<List<Boolean>>()
    val sortOptions: LiveData<List<Boolean>> = _sortOptions

    //filter
    private val _filterOptions = MutableLiveData<List<Boolean>>()
    val filterOptions: LiveData<List<Boolean>> = _filterOptions

    private val _dataSensorList = MutableLiveData<List<DataSensorTable>>()
    val dataSensorList: LiveData<List<DataSensorTable>> = _dataSensorList

    private val _page = MutableLiveData<Int>()
    val page: LiveData<Int> = _page

    private val _pageSize = MutableLiveData<Int>()
    val pageSize: LiveData<Int> = _pageSize

    private val _sortBy = MutableLiveData<String>()
    val sortBy: LiveData<String> = _sortBy

    // order
    private val _order = MutableLiveData<String>()
    val order: LiveData<String> = _order

    private val _filterBy = MutableLiveData<String>()
    val filterBy: LiveData<String> = _filterBy

    private val _filter = MutableStateFlow("")
    val filter: StateFlow<String> = _filter.asStateFlow()

    private var currentPagingSource: DataSensorPagingSource? = null

    // Initialize Pager
    private val pager = Pager(
        PagingConfig(
            initialLoadSize = 10,
            pageSize = 10,
            prefetchDistance = 5,
            enablePlaceholders = false
        )
    ) {
        DataSensorPagingSource(
            apiRepository,
            _sortBy.value ?: "timestamp",
            _order.value ?: "DESC",
            _filter.value,
            _filterBy.value ?: ""
        ).also { currentPagingSource = it }
    }.flow.cachedIn(viewModelScope)

    val dataSensorFlow: Flow<PagingData<DataSensorTable>> = pager

    fun reload() {
        currentPagingSource?.invalidate()
    }

    fun onQueryTextChanged(query: String) {
        viewModelScope.launch {
            _filter.value = query
            Log.d("vu", _filterBy.value.toString() + " " + _sortBy.value.toString())
            reload()
        }
    }

    fun changeOptions(idSort : Int, idFilter : Int){
        changeSortOption(idSort)
        changeFilterOption(idFilter)
        reload()

    }

    private fun changeFilterOption(id: Int){
        val list = _filterOptions.value?.toMutableList()!!
        for (i in list.indices){
            list[i] = false
        }
        list[id] = true
        _filterOptions.value = list
        when(id){
            0 -> {
                _filterBy.value = "temperature"
            }
            1 -> {
                _filterBy.value = "humidity"
            }
            2 -> {
                _filterBy.value = "light"
            }
            3 -> {
                _filterBy.value = "timestamp"
            }
        }
    }

    private fun changeSortOption(id : Int){
//        Log.d("vu", "hello")

            val list = _sortOptions.value?.toMutableList()!!
            for (i in list.indices){
                list[i] = false
            }
            list[id] = true
            _sortOptions.value = list
            when(id){
                0 -> {
                    _sortBy.value = "temperature"
                }
                1 -> {
                    _sortBy.value = "humidity"
                }
                2 -> {
                    _sortBy.value = "light"
                }
                3 -> {
                    _sortBy.value = "timestamp"
                }
            }
    }

    fun setOrder(): () -> Unit {
        return {
            if (_order.value == "DESC") {
                _order.value = "ASC"
            } else {
                _order.value = "DESC"
            }
            reload()
        }
    }

    init {
        viewModelScope.launch {
            _page.value = 1
            _pageSize.value = 10
            _sortBy.value = "timestamp"
            _order.value = "DESC"
            _filter.value = ""
            _filterBy.value = ""
            _sortOptions.value = listOf(false, false, false, true)
            _filterOptions.value = listOf(false, false, false, true)
        }
    }
}
