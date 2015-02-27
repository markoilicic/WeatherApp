package com.undabot.weatherapp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.undabot.weatherapp.R;
import com.undabot.weatherapp.presenters.CityWeatherActivityPresenter;
import com.undabot.weatherapp.ui.adapters.DrawerCityListAdapter;
import com.undabot.weatherapp.ui.adapters.WeatherPagerAdapter;
import com.undabot.weatherapp.ui.views.CityWeatherActivityView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CityWeatherActivity extends BaseActivity implements
		CityWeatherActivityView,
		ViewPager.OnPageChangeListener,
		AdapterView.OnItemClickListener {

	@Inject CityWeatherActivityPresenter presenter;

	@InjectView(R.id.toolbar) Toolbar mToolbar;
	@InjectView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
	@InjectView(R.id.drawer_fragment) View mDrawerView;
	@InjectView(R.id.lv_drawer_city_list) ListView lvDrawerCityList;
	@InjectView(R.id.view_pager) ViewPager mPager;

	private ActionBarDrawerToggle mDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_weather_activity);

		ButterKnife.inject(this);

		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		presenter.init(this);
		presenter.onCreate();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.syncState();
	}

	@Override
	public void onBackPressed() {
		presenter.onBackButtonPressed();
	}

	@Override
	public void setupDrawerAndPager(ArrayList<String> cityList) {
		DrawerCityListAdapter drawerAdapter = new DrawerCityListAdapter(this, cityList);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();

		lvDrawerCityList.setAdapter(drawerAdapter);
		lvDrawerCityList.setOnItemClickListener(this);
		lvDrawerCityList.setEmptyView(findViewById(R.id.empty_city_list_layout));

		WeatherPagerAdapter pagerAdapter = new WeatherPagerAdapter(getSupportFragmentManager(), cityList);
		mPager.setAdapter(pagerAdapter);
		mPager.setOnPageChangeListener(this);

	}

	/**
	 * Method that handle drawer opening/closing
	 *
	 * @param shouldOpen boolean
	 */
	@Override
	public void shouldOpenDrawer(boolean shouldOpen) {
		if (shouldOpen && !mDrawerLayout.isDrawerOpen(mDrawerView)) {
			mDrawerLayout.openDrawer(mDrawerView);
		} else if (!shouldOpen && mDrawerLayout.isDrawerOpen(mDrawerView)) {
			mDrawerLayout.closeDrawer(mDrawerView);
		}
	}

	/**
	 * Set clicked item in drawer as selected and move pager to that position if necessary
	 *
	 * @param position of clicked item in drawer
	 */
	@Override
	public void setSelectedItem(int position) {
		lvDrawerCityList.setItemChecked(position, true);
		if (mPager.getCurrentItem() != position) {
			mPager.setCurrentItem(position);
		}
	}

	@Override
	public boolean isDrawerOpened() {
		return mDrawerLayout.isDrawerOpen(mDrawerView);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		//do nothing
	}

	@Override
	public void onPageSelected(int position) {
		presenter.onPageChanged(position);
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		//do nothing
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		presenter.onDrawerItemClicked(position);
	}


	@Override
	public Context getViewContext() {
		return this;
	}

	@Override
	public Activity getViewActivity() {
		return this;
	}

	@OnClick(R.id.btn_drawer_edit_city_list)
	public void onEditCityListClick() {
		presenter.onEditCityListClicked(this);
	}
}
