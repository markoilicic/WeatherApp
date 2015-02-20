package com.undabot.weatherapp.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.undabot.weatherapp.data.prefs.IntPreference;
import com.undabot.weatherapp.data.prefs.StringArrayPreference;
import com.undabot.weatherapp.modules.qualifiers.ApplicationContext;
import com.undabot.weatherapp.modules.qualifiers.CityList;
import com.undabot.weatherapp.modules.qualifiers.SelectedPosition;
import com.undabot.weatherapp.ui.CityWeatherActivity;
import com.undabot.weatherapp.ui.EditCityListActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
		injects = {
				CityWeatherActivity.class,
				EditCityListActivity.class,
		},
		library = true,
		complete = false
)
public final class PreferencesModule {

	private static final String KEY_SELECTED_POSITION = "drawer_selected_position";
	private static final String KEY_CITY_LIST = "city_list";

	@Provides @Singleton
	public SharedPreferences provideSharedPreferences(@ApplicationContext Context context) {
		return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
	}

	@Provides @Singleton @SelectedPosition
	public IntPreference provideSelectedPosition(SharedPreferences preferences) {
		return new IntPreference(preferences, KEY_SELECTED_POSITION);
	}

	@Provides @Singleton @CityList
	public StringArrayPreference provideCityList(SharedPreferences preferences) {
		return new StringArrayPreference(preferences, KEY_CITY_LIST);
	}

}
