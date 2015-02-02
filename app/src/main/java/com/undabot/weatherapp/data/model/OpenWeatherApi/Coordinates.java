package com.undabot.weatherapp.data.model.OpenWeatherApi;

import com.google.gson.annotations.SerializedName;

public class Coordinates {

	@SerializedName("lon") private double longitude;
	@SerializedName("lat") private double latitude;

	public Coordinates() {
		this.longitude = (double) -1;
		this.latitude = (double) -1;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
}
