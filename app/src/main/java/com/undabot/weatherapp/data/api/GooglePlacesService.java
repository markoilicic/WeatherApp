package com.undabot.weatherapp.data.api;

import com.undabot.weatherapp.data.model.GoogleApi.PlacesAutoCompleteResponse;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface GooglePlacesService {

	@GET("/json")
	public PlacesAutoCompleteResponse getPlacesPrediction(@Query("input") String inputText);

	@GET("/json")
	public Observable<PlacesAutoCompleteResponse> getPlacesPredictionObservable(@Query("input") String inputText);

}
