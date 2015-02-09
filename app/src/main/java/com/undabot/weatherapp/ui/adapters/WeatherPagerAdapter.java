package com.undabot.weatherapp.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.undabot.weatherapp.ui.CityWeatherFragment;

import java.util.ArrayList;

public class WeatherPagerAdapter extends FragmentStatePagerAdapter {

	private ArrayList<String> mCityList;

	public WeatherPagerAdapter(FragmentManager fm, ArrayList<String> cityList) {
		super(fm);
		this.mCityList = cityList;
	}

	@Override
	public int getCount() {
		return mCityList.size();
	}

	@Override
	public Fragment getItem(int position) {
		return CityWeatherFragment.getInstance(mCityList.get(position));
	}

}
