package com.undabot.weatherapp.data.api;

import android.app.Application;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.undabot.weatherapp.App;

import java.io.File;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import timber.log.Timber;

public class ApiServiceManager {

    private static final int HTTP_DISC_CACHE_SIZE = 10 * 1024 * 1024; //10MB

    private ApiService mApiService;

    public ApiServiceManager() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ApiConstants.BASE_URL)
                .setClient(new OkClient(createOkHttpClient((Application) App.getAppContext())))
                .setRequestInterceptor(new ApiHeader())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        mApiService = restAdapter.create(ApiService.class);
    }

    public ApiService getApiService() {
        return mApiService;
    }

    /**
     * Create okHttpClient for caching responses
     */
    public static OkHttpClient createOkHttpClient(Application application) {
        OkHttpClient okHttpClient = new OkHttpClient();

        try {
            File discCache = new File(application.getCacheDir(), "http");
            Cache cache = new Cache(discCache, HTTP_DISC_CACHE_SIZE);
            okHttpClient.setCache(cache);
        } catch (Exception e) {
            Timber.e(e, "Unable to install disk cache");
        }

        return okHttpClient;
    }

}
