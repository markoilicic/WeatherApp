package com.undabot.weatherapp.data.api;

import com.undabot.weatherapp.data.model.OpenWeatherApi.CurrentWeatherResponse;
import com.undabot.weatherapp.data.model.OpenWeatherApi.ForecastWeatherResponse;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface OpenWeatherAPIService {

	@GET("/weather")
	public Observable<CurrentWeatherResponse> getCurrentWeather(@Query("q") String cityName);

	@GET("/forecast/daily")
	public Observable<ForecastWeatherResponse> getForecastWeather(@Query("q") String cityName, @Query("cnt") int daysCount);

}
