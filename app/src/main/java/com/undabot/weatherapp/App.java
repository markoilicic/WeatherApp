package com.undabot.weatherapp;

import android.app.Application;
import android.content.Context;

import net.danlew.android.joda.JodaTimeAndroid;

import timber.log.Timber;


public class App extends Application {

	private static Context mAppContext;

	public static Context getAppContext() {
		return mAppContext;
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
	}
}
