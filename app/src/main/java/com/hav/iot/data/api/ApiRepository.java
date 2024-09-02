package com.hav.iot.data.api;


import com.hav.iot.data.api.RetrofitInstance;
import com.hav.iot.data.api.ApiService;

public class ApiRepository {
    private ApiService apiService;

    public ApiRepository() {
        apiService = RetrofitInstance.getRetrofit().create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }
}