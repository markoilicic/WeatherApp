package com.undabot.weatherapp.modules;

import com.undabot.weatherapp.data.api.ApiConstants;
import com.undabot.weatherapp.data.api.GooglePlacesAPIService;
import com.undabot.weatherapp.data.api.GooglePlacesHeader;
import com.undabot.weatherapp.modules.qualifiers.GooglePlaces;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

@Module(
		complete = false,
		library = true
)
public final class GooglePlacesApiModule {

	@Provides @Singleton
	public GooglePlacesAPIService provideGooglePlacesApiService(@GooglePlaces RestAdapter restAdapter) {
		return restAdapter.create(GooglePlacesAPIService.class);
	}

	@Provides @Singleton @GooglePlaces
	public RestAdapter provideRestAdapter(@GooglePlaces Endpoint endpoint,
										  @GooglePlaces RequestInterceptor header,
										  RestAdapter.LogLevel logLevel) {

		return new RestAdapter.Builder()
				.setEndpoint(endpoint)
				.setRequestInterceptor(header)
				.setLogLevel(logLevel)
				.build();
	}

	@Provides @Singleton @GooglePlaces
	public Endpoint provideEndpoint() {
		return Endpoints.newFixedEndpoint(ApiConstants.GOOGLE_PLACES_BASE_URL);
	}

	@Provides @Singleton @GooglePlaces
	public RequestInterceptor provideHeader(GooglePlacesHeader header) {
		return header;
	}
}
