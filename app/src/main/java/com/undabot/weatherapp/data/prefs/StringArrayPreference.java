package com.undabot.weatherapp.data.prefs;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class StringArrayPreference {

	private final SharedPreferences preferences;
	private final String key;
	private final Gson gson;
	private final StringPreference mStringPreference;

	public StringArrayPreference(SharedPreferences preferences, String key) {
		this(preferences, key, "[]");
	}

	public StringArrayPreference(SharedPreferences preferences, String key, String defaultValue) {
		this.preferences = preferences;
		this.key = key;
		this.gson = new Gson();
		this.mStringPreference = new StringPreference(preferences, key, defaultValue);
	}

	public ArrayList<String> get() {
		return gson.fromJson(mStringPreference.get(),
				new TypeToken<ArrayList<String>>() {
				}.getType());
	}

	public boolean isSet() {
		return preferences.contains(key);
	}

	public void set(ArrayList<String> list) {
		mStringPreference.set(gson.toJson(list));
	}

	public void delete() {
		preferences.edit().remove(key).apply();
	}

}
