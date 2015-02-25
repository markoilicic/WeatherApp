package com.undabot.weatherapp.presenters;

import android.app.Activity;
import android.content.Intent;

import com.undabot.weatherapp.data.prefs.IntPreference;
import com.undabot.weatherapp.data.prefs.StringArrayPreference;
import com.undabot.weatherapp.ui.EditCityListActivity;
import com.undabot.weatherapp.ui.views.CityWeatherActivityView;

import java.util.ArrayList;

public class CityWeatherActivityPresenterImpl implements CityWeatherActivityPresenter {

	private CityWeatherActivityView view;

	private StringArrayPreference mCityListPref;
	private IntPreference mSelectedItem;
	private ArrayList<String> mCityList;

	public CityWeatherActivityPresenterImpl(StringArrayPreference cityList,
											IntPreference selectedItem) {
		this.mCityListPref = cityList;
		this.mSelectedItem = selectedItem;
	}

	@Override
	public void init(CityWeatherActivityView view) {
		this.view = view;
	}

	@Override
	public void onCreate() {
		mCityList = mCityListPref.getList();
		view.setupDrawerAndPager(mCityList);
		//show drawer if empty or select selected item
		if (mCityList.isEmpty()) {
			view.shouldOpenDrawer(true);
		} else {
			view.setSelectedItem(mSelectedItem.get());
		}
	}

	@Override
	public void onDrawerItemClicked(int position) {
		mSelectedItem.set(position);
		view.setSelectedItem(position);
		view.shouldOpenDrawer(false);
	}

	@Override
	public void onPageChanged(int position) {
		mSelectedItem.set(position);
		view.setSelectedItem(position);
	}

	@Override
	public void onEditCityListClicked(Activity activity) {
		activity.startActivity(new Intent(activity, EditCityListActivity.class));
	}

	@Override
	public void onBackButtonPressed() {
		if (view.isDrawerOpened()) {
			view.shouldOpenDrawer(false);
		} else {
			view.getViewActivity().finish();
		}
	}

	@Override
	public void onStart() {

	}

	@Override
	public void onResume() {

	}

	@Override
	public void onStop() {

	}

	@Override
	public void onPause() {

	}

	@Override
	public void onDestroy() {

	}

}
