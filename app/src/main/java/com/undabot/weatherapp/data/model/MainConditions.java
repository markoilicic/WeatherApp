package com.undabot.weatherapp.data.model;

import com.google.gson.annotations.SerializedName;

public class MainConditions {
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
}
