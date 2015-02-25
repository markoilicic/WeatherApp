package com.undabot.weatherapp.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.undabot.weatherapp.R;
import com.undabot.weatherapp.data.model.OpenWeatherApi.CurrentWeatherResponse;
import com.undabot.weatherapp.data.model.OpenWeatherApi.ForecastDayWeather;
import com.undabot.weatherapp.presenters.CityWeatherFragmentPresenter;
import com.undabot.weatherapp.ui.adapters.RecyclerWeatherAdapter;
import com.undabot.weatherapp.ui.views.CityWeatherFragmentView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class CityWeatherFragment extends BaseFragment implements
		CityWeatherFragmentView,
		SwipeRefreshLayout.OnRefreshListener {

	public static final String EXTRA_CITY_NAME = "city";

	@Inject CityWeatherFragmentPresenter presenter;

	@InjectView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;
	@InjectView(R.id.error_layout) TextView errorLayout;
	@InjectView(R.id.rv_forecast_weather) RecyclerView rvWeather;

	private String mCityName;

	public static CityWeatherFragment getInstance(String cityName) {
		CityWeatherFragment fragment = new CityWeatherFragment();

		Bundle args = new Bundle();
		args.putString(EXTRA_CITY_NAME, cityName);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onResume() {
		super.onResume();
		presenter.init(this);

		onRefresh();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCityName = getArguments().getString(EXTRA_CITY_NAME);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.city_weather_fragment, container, false);
		ButterKnife.inject(this, view);

		mSwipeRefreshLayout.setColorSchemeResources(R.color.primary, R.color.primary_dark, R.color.accent);
		mSwipeRefreshLayout.setOnRefreshListener(this);

		rvWeather.setHasFixedSize(true);
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
		rvWeather.setLayoutManager(layoutManager);

		return view;
	}

	@Override
	public void onRefresh() {
		presenter.onRefreshTriggered(mCityName);
	}

	@Override
	public void setRefreshingViews() {
		if (!mSwipeRefreshLayout.isRefreshing()) {
			mSwipeRefreshLayout.post(new Runnable() {
				@Override
				public void run() {
					mSwipeRefreshLayout.setRefreshing(true);
				}
			});
			errorLayout.setVisibility(View.GONE);
		}
	}

	@Override
	public void displayOnRefreshSuccessViews() {
		mSwipeRefreshLayout.setRefreshing(false);
		rvWeather.setVisibility(View.VISIBLE);
		errorLayout.setVisibility(View.GONE);
	}

	@Override
	public void displayOnRefreshErrorViews(String errorMsg) {
		mSwipeRefreshLayout.setRefreshing(false);
		//Set error text
		errorLayout.setText(errorMsg);
		errorLayout.setVisibility(View.VISIBLE);
	}

	@Override
	public void setCityWeatherData(CurrentWeatherResponse current, List<ForecastDayWeather> forecast) {
		RecyclerWeatherAdapter recyclerWeatherAdapter = new RecyclerWeatherAdapter(current, forecast);
		rvWeather.setAdapter(recyclerWeatherAdapter);
	}

	@Override
	public Context getViewContext() {
		return getActivity();
	}

	@Override
	public Activity getViewActivity() {
		return getActivity();
	}
}
