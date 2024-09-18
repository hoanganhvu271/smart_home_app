package com.hav.iot.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hav.iot.data.api.ApiRepository
import com.hav.iot.data.model.ActionTable
import com.hav.iot.data.model.DataSensorTable

class DeviceActionPagingSource (private val apiRepository: ApiRepository<Any?>,
                                private val sortBy: String,
                                private val order: String,
                                private val filter: String,
                                private val filterBy: String
) : PagingSource<Int, ActionTable>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ActionTable> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiRepository.apiService.getDeviceAction(
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
            } else {
                return LoadResult.Error(Exception("Failed to load data"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ActionTable>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}