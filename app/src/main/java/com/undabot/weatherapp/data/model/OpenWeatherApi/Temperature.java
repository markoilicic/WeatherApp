package com.undabot.weatherapp.data.model.OpenWeatherApi;

import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;

public class Temperature {
	DecimalFormat formatPattern;

	@SerializedName("day") private float day;
	@SerializedName("min") private float min;
	@SerializedName("max") private float max;
	@SerializedName("night") private float night;
	@SerializedName("eve") private float eve;
	@SerializedName("morn") private float morn;

	public Temperature() {
		this.formatPattern = new DecimalFormat("#.#");
	}


	public float getDay() {
		return day;
	}

	public void setDay(float day) {
		this.day = day;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public float getNight() {
		return night;
	}

	public void setNight(float night) {
		this.night = night;
	}

	public float getEve() {
		return eve;
	}

	public void setEve(float eve) {
		this.eve = eve;
	}

	public float getMorn() {
		return morn;
	}

	public void setMorn(float morn) {
		this.morn = morn;
	}

	public String getFormatedTemp() {
		return formatPattern.format(day) + "°";
	}

	public String getFormatedTempRange() {
		return formatPattern.format(min) + "°/" + formatPattern.format(max) + "°";
	}
}
