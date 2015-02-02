package com.undabot.weatherapp.data.model.OpenWeatherApi;

import com.google.gson.annotations.SerializedName;

public class Wind {

	@SerializedName("speed") private float speed;
	@SerializedName("deg") private float degrees;

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getDegrees() {
		return degrees;
	}

	public void setDegrees(float degrees) {
		this.degrees = degrees;
	}

}
