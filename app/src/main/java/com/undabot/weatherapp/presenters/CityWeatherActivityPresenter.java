package com.undabot.weatherapp.presenters;

import android.app.Activity;

import com.undabot.weatherapp.ui.views.CityWeatherActivityView;

public interface CityWeatherActivityPresenter extends LifecyclePresenter<CityWeatherActivityView> {

	public void onDrawerItemClicked(int position);

	public void onPageChanged(int position);

	public void onEditCityListClicked(Activity activity);

	public void onBackButtonPressed();
}
