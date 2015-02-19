package com.undabot.weatherapp.data.api;

import javax.inject.Inject;

import retrofit.RequestInterceptor;

public class OpenWeatherHeader implements RequestInterceptor {

	@Inject
	public OpenWeatherHeader() {
	}

	@Override
	public void intercept(RequestFacade request) {
		request.addQueryParam("units", "metric");
		request.addQueryParam("lang", "en");
		request.addQueryParam("mode", "json");
		request.addHeader("api", ApiConstants.OPEN_WEATHER_API_KEY);
	}
}
