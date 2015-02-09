package com.undabot.weatherapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.undabot.weatherapp.R;
import com.undabot.weatherapp.data.prefs.IntPreference;
import com.undabot.weatherapp.data.utils.SharedPrefsUtils;
import com.undabot.weatherapp.ui.adapters.DrawerCityListAdapter;
import com.undabot.weatherapp.ui.adapters.WeatherPagerAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CityWeatherActivity extends ActionBarActivity {

	@InjectView(R.id.toolbar) Toolbar mToolbar;
	@InjectView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
	@InjectView(R.id.drawer_fragment) View mDrawerView;
	@InjectView(R.id.lv_drawer_city_list) ListView lvDrawerCityList;
	@InjectView(R.id.view_pager) ViewPager mPager;

	private ActionBarDrawerToggle mDrawerToggle;

	private IntPreference mSelectedPosition;

	private ArrayList<String> mCityList;
	private DrawerCityListAdapter mDrawerAdapter;
	private PagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_weather_activity);

		ButterKnife.inject(this);

		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		//Get shared prefs
		SharedPreferences sharedPreferences = SharedPrefsUtils.getSharedPreferences();
		mSelectedPosition = new IntPreference(sharedPreferences, SharedPrefsUtils.KEY_SELECTED_POSITION, 0);
		mCityList = SharedPrefsUtils.getCityList();

		setupViewPager();
		setupDrawer();

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

	private void setupViewPager() {
		mPagerAdapter = new WeatherPagerAdapter(getSupportFragmentManager(), mCityList);
		mPager.setAdapter(mPagerAdapter);
		mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageSelected(int position) {
				mSelectedPosition.set(position);
				lvDrawerCityList.setItemChecked(position, true);
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});

	}

	private void setupDrawer() {
		mDrawerAdapter = new DrawerCityListAdapter(this, mCityList);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				super.onDrawerSlide(drawerView, slideOffset);
				//Change toolbar transparency on drawer slide
				if (slideOffset < 0.4) {
					mToolbar.setAlpha(1 - slideOffset);
				}
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		lvDrawerCityList.setAdapter(mDrawerAdapter);
		lvDrawerCityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mSelectedPosition.set(position);
				lvDrawerCityList.setItemChecked(position, true);
				mPager.setCurrentItem(position);
				mDrawerLayout.closeDrawer(Gravity.LEFT);
			}
		});
		lvDrawerCityList.setEmptyView(findViewById(R.id.empty_city_list_layout));

		//Show drawer if list is empty
		if (mCityList.isEmpty()) {
			mDrawerLayout.openDrawer(mDrawerView);
		} else {
			// Set drawer last selected item
			lvDrawerCityList.setItemChecked(mSelectedPosition.get(), true);
			// Set viewPager to last selected position
			mPager.setCurrentItem(mSelectedPosition.get());
		}

	}

	@OnClick(R.id.btn_drawer_edit_city_list)
	public void onEditCityListClick() {
		startActivity(new Intent(this, EditCityListActivity.class));
	}

}
