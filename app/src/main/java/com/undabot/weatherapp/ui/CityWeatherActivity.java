package com.undabot.weatherapp.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.undabot.weatherapp.R;
import com.undabot.weatherapp.data.utils.SharedPrefsUtils;
import com.undabot.weatherapp.ui.adapters.DrawerCityListAdapter;

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

	private ActionBarDrawerToggle mDrawerToggle;

	private boolean mIsUserLearnedDrawer;

	private ArrayList<String> mCityList;
	private DrawerCityListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_weather_activity);

		ButterKnife.inject(this);

		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		//Set up drawer
		mIsUserLearnedDrawer = Boolean.valueOf(SharedPrefsUtils.getFromPreferences(KEY_USER_LEARNED_DRAWER, "false"));
		mCityList = (ArrayList<String>) SharedPrefsUtils.getCityList();
		adapter = new DrawerCityListAdapter(this, mCityList);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				//If user started for first time, set isUserLearnedDrawer = true so drawer doesn't opens in next start
				if (!mIsUserLearnedDrawer) {
					mIsUserLearnedDrawer = true;
					SharedPrefsUtils.saveToPreferences(KEY_USER_LEARNED_DRAWER, "true");
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

		//Show drawer if user never seen it
		if (!mIsUserLearnedDrawer) {
			mDrawerLayout.openDrawer(mDrawerView);
		}
		setDrawerList();
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


	public void setDrawerList() {
		lvDrawerCityList.setAdapter(adapter);
		lvDrawerCityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				adapter.setSelectedItem(position);
				mDrawerLayout.closeDrawer(Gravity.LEFT);
			}
		});

	}

	@OnClick(R.id.btn_drawer_edit_city_list)
	public void onEditCityListClick() {
		startActivity(new Intent(this, EditCityListActivity.class));
	}
}
