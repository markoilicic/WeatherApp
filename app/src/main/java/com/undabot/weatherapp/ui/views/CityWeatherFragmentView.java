package com.undabot.weatherapp.ui.views;

import com.undabot.weatherapp.data.model.OpenWeatherApi.CurrentWeatherResponse;
import com.undabot.weatherapp.data.model.OpenWeatherApi.ForecastDayWeather;

import java.util.List;

public interface CityWeatherFragmentView extends BaseView {

	public void setRefreshingViews();

	public void displayOnRefreshSuccessViews();

	public void displayOnRefreshErrorViews(String errorMsg);

	public void setCityWeatherData(CurrentWeatherResponse current, List<ForecastDayWeather> forecast);

}
