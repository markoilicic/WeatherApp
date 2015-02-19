package com.undabot.weatherapp;

import android.app.Application;
import android.content.Context;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.List;

import dagger.ObjectGraph;
import timber.log.Timber;


public class App extends Application {

	private static Context mAppContext;
	private ObjectGraph objectGraph;

	public static Context getAppContext() {
		return mAppContext;
	}

	public static App from(Context context) {
		return (App) context.getApplicationContext();
	}

	public static App get() {
		return from(mAppContext);
	}

	@Override
	public void onCreate() {
		super.onCreate();

		mAppContext = getApplicationContext();

		//Init Joda Time
		JodaTimeAndroid.init(this);

		//Timber initialization
		if (BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
		} else {
			Timber.plant(new Timber.HollowTree());
		}

		createObjectGraphAndInject();

	}

	private void createObjectGraphAndInject() {
		objectGraph = ObjectGraph.create(Modules.list(this));
		objectGraph.inject(this);
	}

	public void inject(Object object) {
		objectGraph.inject(object);
	}

	public ObjectGraph plus(List<Object> modules) {
		if (modules == null) {
			throw new IllegalArgumentException("Can't plus a null module");
		}
		return objectGraph.plus(modules.toArray());
	}
}
