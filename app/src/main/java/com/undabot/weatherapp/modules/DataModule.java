package com.undabot.weatherapp.modules;

import dagger.Module;

@Module(
		complete = false,
		includes = {
				ApiModule.class,
				NetworkModule.class,
				PreferencesModule.class,
		}
)
public final class DataModule {
}
