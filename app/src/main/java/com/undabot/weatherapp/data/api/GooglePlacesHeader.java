package com.undabot.weatherapp.data.api;

import retrofit.RequestInterceptor;

public class GooglePlacesHeader implements RequestInterceptor {

	@Override
	public void intercept(RequestFacade request) {
		request.addQueryParam("key", ApiConstants.GOOGLE_PLACES_API_KEY);
		request.addQueryParam("types", "(cities)");
	}
}
