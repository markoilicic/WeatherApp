package com.undabot.weatherapp.data.api;

import com.undabot.weatherapp.data.model.CurrentWeatherResponse;
import com.undabot.weatherapp.data.model.ForecastWeatherResponse;

import java.util.Map;

import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import rx.Observable;

public interface ApiService {

    @GET(ApiConstants.PATH_CURRENT_WEATHER)
    public Observable<CurrentWeatherResponse> getCurrentWeather(@QueryMap Map<String, String> options, @Query(ApiConstants.PARAM_QUERY_SEARCH) String cityName);

    @GET(ApiConstants.PATH_FORECAST_WEATHER)
    public Observable<ForecastWeatherResponse> getForecastWeather(@QueryMap Map<String, String> options, @Query(ApiConstants.PARAM_QUERY_SEARCH) String cityName);


}
