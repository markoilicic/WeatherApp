package com.undabot.weatherapp.presenters;

import com.undabot.weatherapp.ui.views.CityWeatherView;

public interface CityWeatherPresenter extends LifecyclePresenter<CityWeatherView> {

	public void onRefreshTriggered();

	public void onBackButtonPressed();
}
