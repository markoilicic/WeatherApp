package com.undabot.weatherapp.data.model.OpenWeatherApi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ForecastWeatherResponse {

	@SerializedName("cod") private int responseCode;
	@SerializedName("city") private City city;
	@SerializedName("cnt") private int daysCount;
	@SerializedName("list") private List<ForecastDayWeather> forecastDayWeather;

	public ForecastWeatherResponse() {
		this.responseCode = -1;
		this.city = new City();
		this.daysCount = -1;
		this.forecastDayWeather = new ArrayList<ForecastDayWeather>();
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public int getDaysCount() {
		return daysCount;
	}

	public void setDaysCount(int daysCount) {
		this.daysCount = daysCount;
	}

	public List<ForecastDayWeather> getForecastDayWeather() {
		return forecastDayWeather;
	}

	public void setForecastDayWeather(List<ForecastDayWeather> forecastDayWeather) {
		this.forecastDayWeather = forecastDayWeather;
	}
}
