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

	private OpenWeatherService mOpenWeatherService;
	private GooglePlacesService mGooglePlacesService;

	public ApiServiceManager() {
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(ApiConstants.OPEN_WEATHER_BASE_URL)
				.setClient(new OkClient(createOkHttpClient((Application) App.getAppContext())))
				.setRequestInterceptor(new ApiHeaders(ApiHeaders.HEADER_TYPE_OPEN_WEATHER_SERVICE))
				.setLogLevel(RestAdapter.LogLevel.FULL)
				.build();

		mOpenWeatherService = restAdapter.create(OpenWeatherService.class);

		restAdapter = new RestAdapter.Builder()
				.setEndpoint(ApiConstants.GOOGLE_PLACES_BASE_URL)
				.setRequestInterceptor(new ApiHeaders(ApiHeaders.HEADER_TYPE_PLACES_SERVICE))
				.setLogLevel(RestAdapter.LogLevel.FULL)
				.build();

		mGooglePlacesService = restAdapter.create(GooglePlacesService.class);
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

	public OpenWeatherService getApiService() {
		return mOpenWeatherService;
	}

	public GooglePlacesService getGooglePlacesApiService() {
		return mGooglePlacesService;
	}

}
