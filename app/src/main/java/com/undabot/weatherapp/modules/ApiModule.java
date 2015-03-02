package com.undabot.weatherapp.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

@Module(
		includes = {
				OpenWeatherApiModule.class,
				GooglePlacesApiModule.class
		},
		complete = false
)
public class ApiModule {

	@Provides @Singleton
	public RestAdapter.LogLevel provideLogLevel() {
		return RestAdapter.LogLevel.FULL;
	}
}
