package com.undabot.weatherapp.modules;

import android.app.Application;
import android.content.Context;

import com.undabot.weatherapp.modules.qualifiers.ApplicationContext;

import dagger.Module;
import dagger.Provides;

@Module(
		library = true,
		complete = false
)
public final class ContextModule {

	@Provides @ApplicationContext
	public Context provideApplicationContext(Application application) {
		return application;
	}

}
