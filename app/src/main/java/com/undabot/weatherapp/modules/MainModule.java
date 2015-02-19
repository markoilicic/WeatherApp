package com.undabot.weatherapp.modules;

import android.app.Application;

import com.undabot.weatherapp.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
		includes = {
				ContextModule.class,
				DataModule.class
		},
		injects = {
				App.class,
				//CityWeatherActivity.class
		}
)
public final class MainModule {

	private final App app;

	public MainModule(App app) {
		this.app = app;
	}

	@Provides @Singleton
	public Application provideApplication() {
		return app;
	}

}
