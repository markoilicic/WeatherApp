package com.undabot.weatherapp.data.api;

import retrofit.RequestInterceptor;

public class ApiHeader implements RequestInterceptor {
    @Override
    public void intercept(RequestFacade request) {
        request.addQueryParam(ApiConstants.PARAM_RESPONSE_MODE, ApiConstants.VALUE_JSON);
        request.addHeader(ApiConstants.PARAM_HEADER_API_KEY, ApiConstants.API_KEY);
    }
}
