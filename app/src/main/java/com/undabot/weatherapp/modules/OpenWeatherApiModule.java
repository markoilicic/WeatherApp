package com.undabot.weatherapp.modules;

import com.squareup.okhttp.OkHttpClient;
import com.undabot.weatherapp.data.api.ApiConstants;
import com.undabot.weatherapp.data.api.OpenWeatherAPIService;
import com.undabot.weatherapp.data.api.OpenWeatherHeader;
import com.undabot.weatherapp.modules.qualifiers.OpenWeather;
import com.undabot.weatherapp.ui.CityWeatherFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;

@Module(
		injects = {
				CityWeatherFragment.class
		},
		library = true,
		complete = false
)
public final class OpenWeatherApiModule {

	@Provides @Singleton
	public OpenWeatherAPIService provideOpenWeatherAPIService(@OpenWeather RestAdapter restAdapter) {
		return restAdapter.create(OpenWeatherAPIService.class);
	}

	@Provides @Singleton @OpenWeather
	public RestAdapter provideRestAdapter(@OpenWeather Client client,
										  @OpenWeather Endpoint endpoint,
										  @OpenWeather RequestInterceptor headers,
										  RestAdapter.LogLevel logLevel) {
		return new RestAdapter.Builder()
				.setClient(client)
				.setEndpoint(endpoint)
				.setRequestInterceptor(headers)
				.setLogLevel(logLevel)
				.build();
	}

	@Provides @Singleton @OpenWeather
	public Client provideClient(OkHttpClient client) {
		return new OkClient(client);
	}

	@Provides @Singleton @OpenWeather
	public Endpoint provideEndpoint() {
		return Endpoints.newFixedEndpoint(ApiConstants.OPEN_WEATHER_BASE_URL);
	}

	@Provides @Singleton @OpenWeather
	public RequestInterceptor provideRequestInterceptor(OpenWeatherHeader header) {
		return header;
	}

}
