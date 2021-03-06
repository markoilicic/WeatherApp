package com.undabot.weatherapp.data.model.OpenWeatherApi;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ForecastDayWeather {

	@SerializedName("dt") private long timeStamp;
	@SerializedName("temp") private Temperature temperature;
	@SerializedName("pressure") private float pressure;
	@SerializedName("humidity") private float humidity;
	@SerializedName("weather") private List<Weather> weatherList;
	@SerializedName("speed") private float windSpeed;
	@SerializedName("deg") private float windDegrees;

	public ForecastDayWeather() {
		this.temperature = new Temperature();
		this.weatherList = new ArrayList<Weather>();
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Temperature getTemperature() {
		return temperature;
	}

	public void setTemperature(Temperature temperature) {
		this.temperature = temperature;
	}

	public float getPressure() {
		return pressure;
	}

	public void setPressure(float pressure) {
		this.pressure = pressure;
	}

	public float getHumidity() {
		return humidity;
	}

	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}

	public List<Weather> getWeatherList() {
		return weatherList;
	}

	public void setWeatherList(List<Weather> weatherList) {
		this.weatherList = weatherList;
	}

	public float getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(float windSpeed) {
		this.windSpeed = windSpeed;
	}

	public float getWindDegrees() {
		return windDegrees;
	}

	public void setWindDegrees(float windDegrees) {
		this.windDegrees = windDegrees;
	}

	public String getDayNameShort() {
		DateTime.Property dayName = new DateTime(timeStamp * 1000L).dayOfWeek(); //timestamp is in seconds, multiply it with 1000 to get milliseconds
		return dayName.getAsShortText(Locale.ENGLISH).toUpperCase();
	}
}
