package com.undabot.weatherapp.data.model.OpenWeatherApi;

import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;

public class MainConditions {
	DecimalFormat formatPattern;
	@SerializedName("temp") private float temperature;
	@SerializedName("humidity") private float humidity;
	@SerializedName("temp_min") private float tempMin;
	@SerializedName("temp_max") private float tempMax;
	@SerializedName("pressure") private float pressure;

	public MainConditions() {
		this.temperature = (float) -1;
		this.humidity = (float) -1;
		this.tempMin = (float) -1;
		this.tempMax = (float) -1;
		this.pressure = (float) -1;
		this.formatPattern = new DecimalFormat("#.#");
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public float getHumidity() {
		return humidity;
	}

	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}

	public float getTempMin() {
		return tempMin;
	}

	public void setTempMin(float tempMin) {
		this.tempMin = tempMin;
	}

	public float getTempMax() {
		return tempMax;
	}

	public void setTempMax(float tempMax) {
		this.tempMax = tempMax;
	}

	public float getPressure() {
		return pressure;
	}

	public void setPressure(float pressure) {
		this.pressure = pressure;
	}

	public String getFormatedTemp() {
		return formatPattern.format(temperature);
	}

	public String getFormatedTempRange() {
		return formatPattern.format(tempMin) + "°/" + formatPattern.format(tempMax) + "°";
	}
}
