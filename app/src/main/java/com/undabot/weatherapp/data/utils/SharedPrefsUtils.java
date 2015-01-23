package com.undabot.weatherapp.data.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.undabot.weatherapp.App;
import com.undabot.weatherapp.data.api.ApiConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SharedPrefsUtils {

	private static final String KEY_CITY_LIST = "city_list";
	private static final String KEY_SELECTED_CITY = "drawer_selected_city";

	private static Gson mGson = new Gson();

	private static SharedPreferences getSharedPreferences() {
		Context context = App.getAppContext();
		return context.getSharedPreferences(App.getAppContext().getPackageName(), Context.MODE_PRIVATE);
	}

	public static void saveToPreferences(String preferenceName, String preferenceValue) {
		getSharedPreferences().edit().putString(preferenceName, preferenceValue).apply();
	}

	public static String getFromPreferences(String preferenceName, String defaultValue) {
		return getSharedPreferences().getString(preferenceName, defaultValue);
	}

	/**
	 * Returns list of saved cities from shared prefs
	 */
	public static List<String> getCityList() {
		String jsonArray = getSharedPreferences().getString(KEY_CITY_LIST, "[]");
		return mGson.fromJson(jsonArray, new TypeToken<List<String>>() {
		}.getType());
	}

	/**
	 * Sets list of cities to shared prefs
	 */
	public static void setCityList(List<String> cityList) {
		String jsonArray = mGson.toJson(cityList);
		getSharedPreferences().edit().putString(KEY_CITY_LIST, jsonArray).apply();
	}

	/**
	 * Saves currently selected city in drawer
	 */
	public static String getSelectedCity() {
		return getSharedPreferences().getString(KEY_SELECTED_CITY, "");
	}

	/**
	 * Saves currently selected city in drawer
	 */
	public static void setSelectedCity(String cityName) {
		getSharedPreferences().edit().putString(KEY_SELECTED_CITY, cityName).apply();
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
