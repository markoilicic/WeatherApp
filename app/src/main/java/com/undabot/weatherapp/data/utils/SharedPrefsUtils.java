package com.undabot.weatherapp.data.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.undabot.weatherapp.App;
import com.undabot.weatherapp.data.api.ApiConstants;
import com.undabot.weatherapp.data.prefs.StringPreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SharedPrefsUtils {

	public static final String KEY_CITY_LIST = "city_list";
	public static final String KEY_SELECTED_CITY = "drawer_selected_city";

	private static StringPreference mCityList = new StringPreference(getSharedPreferences(), KEY_CITY_LIST, "[]");
	private static Gson mGson = new Gson();

	public static SharedPreferences getSharedPreferences() {
		Context context = App.getAppContext();
		return context.getSharedPreferences(App.getAppContext().getPackageName(), Context.MODE_PRIVATE);
	}

	/**
	 * Returns list of saved cities from shared prefs
	 */
	public static ArrayList<String> getCityList() {
		String jsonArray = mCityList.get();
		return mGson.fromJson(jsonArray, new TypeToken<ArrayList<String>>() {
		}.getType());
	}

	/**
	 * Sets list of cities to shared prefs
	 */
	public static void setCityList(ArrayList<String> cityList) {
		String jsonArray = mGson.toJson(cityList);
		mCityList.set(jsonArray);
	}


	//TODO replace this values with real values from settings
	public static Map<String, String> getWeatherOptions() {
		Map<String, String> options = new HashMap<>();

		options.put(ApiConstants.PARAM_UNIT, ApiConstants.VALUE_UNIT_METRIC);
		options.put(ApiConstants.PARAM_LANGUAGE, ApiConstants.VALUE_LANGUAGE_ENGLISH);

		return options;
	}

	public static Map<String, String> getForecastWeatherOptions() {
		Map<String, String> options = new HashMap<>();

		options.putAll(getWeatherOptions());
		options.put(ApiConstants.PARAM_FORECAST_DAY_COUNT, ApiConstants.VALUE_FORECAST_DAY_COUNT);

		return options;
	}
}
