package com.undabot.weatherapp.presenters;

import com.undabot.weatherapp.ui.views.CityWeatherFragmentView;

public interface CityWeatherFragmentPresenter extends LifecyclePresenter<CityWeatherFragmentView> {

	public void onRefreshTriggered(String cityName);

}
