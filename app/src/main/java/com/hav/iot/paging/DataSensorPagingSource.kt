package com.hav.iot.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hav.iot.data.api.ApiRepository
import com.hav.iot.data.model.DataSensorTable

class DataSensorPagingSource(
    private val apiRepository: ApiRepository<Any?>,
    private val sortBy: String,
    private val order: String,
    private val filter: String,
    private val filterBy: String
) : PagingSource<Int, DataSensorTable>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataSensorTable> {
        Log.d("vu", filter)
        return try {
            val currentPage = params.key ?: 1
            val response = apiRepository.apiService.getDataSensor(
                page = currentPage,
                pageSize = params.loadSize ?: 10,
                sortBy = sortBy,
                order = order,
                filter = filter,
                filterBy = filterBy
            )
//            Log.d("vu", currentPage.toString() + " " + response.body()?.data?.size.toString() + " " + params.loadSize.toString())
            if (response.isSuccessful) {
                val data = response.body()?.data ?: emptyList()
                val nextKey = if (data.size < params.loadSize) null else currentPage + 1

                return LoadResult.Page(
                    data = data,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = nextKey
                )
            } else if(response.code() == 404){
                val data = emptyList<DataSensorTable>()
                val nextKey = null

                return LoadResult.Page(
                    data = data,
                    prevKey = null,
                    nextKey = nextKey
                )
            }
            else{
                return LoadResult.Error(Exception("Failed to load data"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DataSensorTable>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
