package com.undabot.weatherapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ForecastDayWeather {

    @SerializedName("dt") private long timeStamp;
    @SerializedName("temp") private Temperature temperature;
    @SerializedName("pressure") private float pressure;
    @SerializedName("humidity") private float humidity;
    @SerializedName("weather") private List<Weather> weatherList;
    @SerializedName("speed") private float windSpeed;
    @SerializedName("deg") private float windDegrees;

    public ForecastDayWeather(){
        this.timeStamp = (long) -1;
        this.temperature = new Temperature();
        this.pressure = (float) -1;
        this.humidity = (float) -1;
        this.weatherList = new ArrayList<Weather>();
        this.windSpeed = (float) -1;
        this.windDegrees = (float) -1;
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
}
