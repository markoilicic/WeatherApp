package com.undabot.weatherapp.data.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CurrentWeatherResponse {

    @SerializedName("coord") private Coordinates coordinates;
    @SerializedName("sys") private  SystemMsg sysMsg;
    @SerializedName("weather") private List<Weather> weatherList;
    @SerializedName("main") private MainConditions mainConditions;
    @SerializedName("wind") private Wind wind;
    @SerializedName("dt") private long timeStamp;
    @SerializedName("id") private long cityId;
    @SerializedName("name") private String cityName;
    @SerializedName("cod") private int responseCode;

    public CurrentWeatherResponse() {
        this.coordinates = new Coordinates();
        this.sysMsg = new SystemMsg();
        this.weatherList = new ArrayList<Weather>();
        this.mainConditions = new MainConditions();
        this.wind = new Wind();
        this.timeStamp = (long) -1;
        this.cityId = (long) -1;
        this.cityName = "";
        this.responseCode = -1;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public SystemMsg getSysMsg() {
        return sysMsg;
    }

    public void setSysMsg(SystemMsg sysMsg) {
        this.sysMsg = sysMsg;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public MainConditions getMainConditions() {
        return mainConditions;
    }

    public void setMainConditions(MainConditions mainConditions) {
        this.mainConditions = mainConditions;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}
