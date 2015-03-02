package com.undabot.weatherapp.modules;

import android.content.Context;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.undabot.weatherapp.modules.qualifiers.ApplicationContext;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

@Module(
		library = true,
		complete = false
)
public final class NetworkModule {

	private static final int HTTP_DISC_CACHE_SIZE = 10 * 1024 * 1024; //10MB

	private static OkHttpClient createOkHttpClient(Context context) {
		OkHttpClient okHttpClient = new OkHttpClient();

		try {
			File discCache = new File(context.getCacheDir(), "http");
			Cache cache = new Cache(discCache, HTTP_DISC_CACHE_SIZE);
			okHttpClient.setCache(cache);
		} catch (Exception e) {
			Timber.e(e, "Unable to install disk cache");
		}

		return okHttpClient;
	}

	@Provides @Singleton
	public OkHttpClient provideOkHttpClient(@ApplicationContext Context context) {
		return createOkHttpClient(context);
	}
}
