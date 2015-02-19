package com.undabot.weatherapp.modules;

import dagger.Module;

@Module(
		complete = false,
		includes = {
				OpenWeatherApiModule.class,
				GooglePlacesApiModule.class,
				NetworkModule.class,
				PreferencesModule.class,
				LogModule.class,
		}
)
public final class DataModule {
}
