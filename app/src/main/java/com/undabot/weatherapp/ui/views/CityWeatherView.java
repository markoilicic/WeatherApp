package com.undabot.weatherapp.ui.views;

import com.undabot.weatherapp.data.model.OpenWeatherApi.CurrentWeatherResponse;
import com.undabot.weatherapp.data.model.OpenWeatherApi.ForecastDayWeather;

import java.util.List;

public interface CityWeatherView extends BaseView {

	public void setRefreshingViews(boolean refreshing);

	public void displayOnRefreshSuccessViews();

	public void displayOnRefreshErrorViews(String errorMsg);

	public void displayCityWeatherData(CurrentWeatherResponse current, List<ForecastDayWeather> forecast);

}
