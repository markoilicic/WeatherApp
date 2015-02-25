package com.undabot.weatherapp.ui.views;

import java.util.ArrayList;

public interface CityWeatherActivityView extends BaseView {

	public void setupDrawerAndPager(ArrayList<String> cityList);

	public void shouldOpenDrawer(boolean open);

	public void setSelectedItem(int position);

	public boolean isDrawerOpened();

}
