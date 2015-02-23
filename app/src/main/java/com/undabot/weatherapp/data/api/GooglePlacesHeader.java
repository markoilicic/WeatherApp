package com.undabot.weatherapp.data.api;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.RequestInterceptor;

@Singleton
public class GooglePlacesHeader implements RequestInterceptor {

	@Inject
	public GooglePlacesHeader() {
	}

	@Override
	public void intercept(RequestFacade request) {
		request.addQueryParam("key", ApiConstants.GOOGLE_PLACES_API_KEY);
		request.addQueryParam("types", "(cities)");
	}
}
