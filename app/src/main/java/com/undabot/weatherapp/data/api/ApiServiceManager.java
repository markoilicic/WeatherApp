package com.undabot.weatherapp.data.api;

import retrofit.RestAdapter;

public class ApiServiceManager {

    private ApiService mApiService;

    public ApiServiceManager() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ApiConstants.BASE_URL)
                .setRequestInterceptor(new ApiHeader())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        mApiService = restAdapter.create(ApiService.class);
    }

    public ApiService getApiService() {
        return mApiService;
    }

    //TODO create okHttp client for caching response

}
