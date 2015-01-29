package com.undabot.weatherapp.data.api;

public class ApiConstants {

	//Base URLs
	public static final String OPEN_WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/";
	public static final String OPEN_WEATHER_IMG_BASE_URL = "http://openweathermap.org/img/w/";

	public static final String GOOGLE_PLACES_BASE_URL = "https://maps.googleapis.com/maps/api/place/autocomplete/";
	public static final String GOOGLE_PLACES_API_KEY = "AIzaSyB8zeKjp65Kd3dwrkLIRUJ4fu9KZPif3EU";

	//Base PATHs
	public static final String PATH_CURRENT_WEATHER = "/weather";
	public static final String PATH_FORECAST_WEATHER = "/forecast";

	//Request header
	public static final String PARAM_OPEN_WEATHER_HEADER_API_KEY = "x-api-key";
	public static final String OPEN_WEATHER_API_KEY = "5883aceef09b86a44ca55bde725a8342";

	//Query params
	public static final String PARAM_RESPONSE_MODE = "mode";
	public static final String VALUE_JSON = "json";

	public static final String PARAM_API_ID = "appid";
	public static final String PARAM_QUERY_SEARCH = "q";
	public static final String PARAM_UNIT = "units";
	public static final String VALUE_UNIT_METRIC = "metric";
	public static final String PARAM_LANGUAGE = "lang";
	public static final String VALUE_LANGUAGE_CROATIAN = "hr";
	public static final String VALUE_LANGUAGE_ENGLISH = "en";

	public static final String PARAM_FORECAST_DAY_COUNT = "cnt";
	public static final String VALUE_FORECAST_DAY_COUNT = "7";

}