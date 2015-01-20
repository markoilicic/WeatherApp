package com.undabot.weatherapp.data.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.undabot.weatherapp.App;
import com.undabot.weatherapp.data.api.ApiConstants;

import java.util.HashMap;
import java.util.Map;

public class SharedPrefsUtils {

    private static SharedPreferences getSharedPreferences() {
        Context context = App.getAppContext();
        return context.getSharedPreferences(App.getAppContext().getPackageName(), Context.MODE_PRIVATE);
    }

    public static void saveToPreferences(String preferenceName, String preferenceValue) {
        getSharedPreferences().edit().putString(preferenceName, preferenceValue).apply();
    }

    public static String getFromPreferences(String preferenceName, String defaultValue){
        return getSharedPreferences().getString(preferenceName, defaultValue);
    }


    //TODO replace this values with real values from settings
    public Map<String, String> getWeatherOptions () {
        Map<String, String> options = new HashMap<>();

        options.put(ApiConstants.PARAM_UNIT, ApiConstants.VALUE_UNIT_METRIC);
        options.put(ApiConstants.PARAM_LANGUAGE, ApiConstants.VALUE_LANGUAGE_CROATIAN);

        return options;
    }

    public Map<String, String> getForecastWeatherOptions() {
        Map<String, String> options = new HashMap<>();

        options.putAll(getWeatherOptions());
        options.put(ApiConstants.PARAM_FORECAST_DAY_COUNT, ApiConstants.VALUE_FORECAST_DAY_COUNT);

        return options;
    }
}
