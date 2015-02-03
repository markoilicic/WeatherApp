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
import com.undabot.weatherapp.data.prefs.BooleanPreference;
import com.undabot.weatherapp.data.prefs.StringPreference;
import com.undabot.weatherapp.data.utils.SharedPrefsUtils;
import com.undabot.weatherapp.ui.adapters.DrawerCityListAdapter;
import com.undabot.weatherapp.ui.adapters.WeatherPagerAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CityWeatherActivity extends ActionBarActivity {

	public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

	@InjectView(R.id.toolbar) Toolbar mToolbar;
	@InjectView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
	@InjectView(R.id.drawer_fragment) View mDrawerView;
	@InjectView(R.id.lv_drawer_city_list) ListView lvDrawerCityList;
	@InjectView(R.id.view_pager) ViewPager viewPager;

	private ActionBarDrawerToggle mDrawerToggle;

	private SharedPreferences sharedPreferences;
	private BooleanPreference mIsUserLearnedDrawer;
	private StringPreference mSelectedCity;

	private ArrayList<String> mCityList;
	private DrawerCityListAdapter adapter;
	private PagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_weather_activity);

		ButterKnife.inject(this);

		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		//Get shared prefs
		sharedPreferences = SharedPrefsUtils.getSharedPreferences();
		mIsUserLearnedDrawer = new BooleanPreference(sharedPreferences, KEY_USER_LEARNED_DRAWER, false);
		mSelectedCity = new StringPreference(sharedPreferences, SharedPrefsUtils.KEY_SELECTED_CITY);
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
		viewPager.setAdapter(mPagerAdapter);
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageSelected(int position) {
				mSelectedCity.set(mCityList.get(viewPager.getCurrentItem()));
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
	}

	private void setupDrawer() {
		adapter = new DrawerCityListAdapter(this, mCityList, mSelectedCity);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				//If user started for first time, set isUserLearnedDrawer = true so drawer doesn't opens in next start
				if (!mIsUserLearnedDrawer.get()) {
					mIsUserLearnedDrawer.set(true);
				}
				invalidateOptionsMenu();
			}

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
		setDrawerList();
		//Show drawer if user never seen it
		if (!mIsUserLearnedDrawer.get()) {
			mDrawerLayout.openDrawer(mDrawerView);
		}
	}

	public void setDrawerList() {
		lvDrawerCityList.setAdapter(adapter);
		lvDrawerCityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mSelectedCity.set(mCityList.get(position));
				lvDrawerCityList.setSelection(position);
				adapter.notifyDataSetChanged();
				viewPager.setCurrentItem(position);
				mDrawerLayout.closeDrawer(Gravity.LEFT);
			}
		});

	}

	@OnClick(R.id.btn_drawer_edit_city_list)
	public void onEditCityListClick() {
		startActivity(new Intent(this, EditCityListActivity.class));
	}

}
