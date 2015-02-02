package com.undabot.weatherapp.data.api;

import com.undabot.weatherapp.data.model.OpenWeatherApi.CurrentWeatherResponse;
import com.undabot.weatherapp.data.model.OpenWeatherApi.ForecastWeatherResponse;

import java.util.Map;

import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import rx.Observable;

public interface OpenWeatherAPIService {

	@GET("/weather")
	public Observable<CurrentWeatherResponse> getCurrentWeather(@QueryMap Map<String, String> options, @Query("q") String cityName);

	@GET("/forecast/daily")
	public Observable<ForecastWeatherResponse> getForecastWeather(@QueryMap Map<String, String> options, @Query("q") String cityName);

}
