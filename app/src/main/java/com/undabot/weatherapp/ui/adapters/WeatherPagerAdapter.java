package com.undabot.weatherapp.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.undabot.weatherapp.ui.CityWeatherFragment;

import java.util.ArrayList;

public class WeatherPagerAdapter extends FragmentStatePagerAdapter {

	private ArrayList<CityWeatherFragment> mCityWeatherFragmentList;

	public WeatherPagerAdapter(FragmentManager fm, ArrayList<CityWeatherFragment> cityWeatherFragmentList) {
		super(fm);
		this.mCityWeatherFragmentList = cityWeatherFragmentList;
	}

	@Override
	public int getCount() {
		return mCityWeatherFragmentList.size();
	}

	@Override
	public Fragment getItem(int position) {
		return mCityWeatherFragmentList.get(position);
	}


}
