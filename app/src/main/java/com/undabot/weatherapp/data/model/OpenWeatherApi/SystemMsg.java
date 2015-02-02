package com.undabot.weatherapp.data.model.OpenWeatherApi;

import com.google.gson.annotations.SerializedName;

public class SystemMsg {

	@SerializedName("country") private String country;
	@SerializedName("sunrise") private long sunrise;
	@SerializedName("sunset") private long sunset;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public long getSunrise() {
		return sunrise;
	}

	public void setSunrise(long sunrise) {
		this.sunrise = sunrise;
	}

	public long getSunset() {
		return sunset;
	}

	public void setSunset(long sunset) {
		this.sunset = sunset;
	}
}
