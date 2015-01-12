package com.undabot.weatherapp;

import android.app.Application;
import android.content.Context;

import timber.log.Timber;


public class App extends Application {

    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppContext = getApplicationContext();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new Timber.HollowTree());
        }
    }

    public static Context getAppContext() {
        return mAppContext;
    }
}
