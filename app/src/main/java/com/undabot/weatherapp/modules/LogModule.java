package com.undabot.weatherapp.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

@Module(
		library = true,
		complete = false
)
public final class LogModule {

	@Provides @Singleton
	public RestAdapter.LogLevel provideLogLevel() {
		return RestAdapter.LogLevel.FULL;
	}
}
