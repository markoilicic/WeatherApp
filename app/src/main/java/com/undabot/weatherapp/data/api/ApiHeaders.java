package com.undabot.weatherapp.data.api;

import retrofit.RequestInterceptor;

public class ApiHeaders implements RequestInterceptor {

	public static final String HEADER_TYPE_OPEN_WEATHER_SERVICE = "api";
	public static final String HEADER_TYPE_PLACES_SERVICE = "places";

	private String mHeaderType;

	public ApiHeaders(String headerType) {
		this.mHeaderType = headerType;
	}

	@Override
	public void intercept(RequestFacade request) {

		switch (mHeaderType) {
			case HEADER_TYPE_OPEN_WEATHER_SERVICE:
				request.addQueryParam(ApiConstants.PARAM_RESPONSE_MODE, ApiConstants.VALUE_JSON);
				request.addHeader(ApiConstants.PARAM_OPEN_WEATHER_HEADER_API_KEY, ApiConstants.OPEN_WEATHER_API_KEY);
				break;

			case HEADER_TYPE_PLACES_SERVICE:
				request.addQueryParam("key", ApiConstants.GOOGLE_PLACES_API_KEY);
				request.addQueryParam("types", "(cities)");
				break;
		}
	}
}
