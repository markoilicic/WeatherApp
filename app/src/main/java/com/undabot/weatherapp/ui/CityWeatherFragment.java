package com.undabot.weatherapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.undabot.weatherapp.R;
import com.undabot.weatherapp.data.api.ApiServiceManager;
import com.undabot.weatherapp.data.api.OpenWeatherAPIService;
import com.undabot.weatherapp.data.model.OpenWeatherApi.CurrentWeatherResponse;
import com.undabot.weatherapp.data.model.OpenWeatherApi.ForecastWeatherResponse;
import com.undabot.weatherapp.data.model.OpenWeatherApi.ResponseEnvelope;
import com.undabot.weatherapp.ui.adapters.RecyclerWeatherAdapter;
import com.undabot.weatherapp.utils.ConnectionUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import timber.log.Timber;


public class CityWeatherFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

	private static final int FORECAST_DAYS_NUMBER = 7;

	@InjectView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;
	@InjectView(R.id.error_layout) TextView errorLayout;

	@InjectView(R.id.rv_forecast_weather) RecyclerView rvWeather;

	private String mCityName;
	private ResponseEnvelope mResponseEnvelope;

	private OpenWeatherAPIService mOpenWeatherService;

	public CityWeatherFragment(String cityName) {
		this.mCityName = cityName;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.city_weather_fragment, container, false);

		ButterKnife.inject(this, view);

		mOpenWeatherService = new ApiServiceManager().getOpenWeatherApiService();

		mSwipeRefreshLayout.setColorSchemeResources(R.color.primary, R.color.primary_dark, R.color.accent);
		mSwipeRefreshLayout.setOnRefreshListener(this);

		rvWeather.setHasFixedSize(true);
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
		rvWeather.setLayoutManager(layoutManager);

		refreshWeather();

		return view;
	}

	@Override
	public void onRefresh() {
		refreshWeather();
	}

	public void refreshWeather() {
		Timber.d("Refreshing weather data for " + mCityName);
		if (ConnectionUtils.isConnected(getActivity())) {
			setOnRefreshStartViews();
			requestWeatherData();
		} else {
			setOnRefreshErrorViews(getString(R.string.error_no_connection));
		}
	}

	private void setOnRefreshStartViews() {
		/*
		When swipeRefreshLayout.setRefreshing() is called programmatically, refreshing layout is not visible
		This approach fixes the problem
		issue: https://code.google.com/p/android/issues/detail?id=77712
		*/
		mSwipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				mSwipeRefreshLayout.setRefreshing(true);
			}
		});
		rvWeather.setAlpha(0.5f);
		errorLayout.setVisibility(View.GONE);
	}

	private void setOnRefreshSucessViews() {
		mSwipeRefreshLayout.setRefreshing(false);
		rvWeather.setAlpha(1f);
		rvWeather.setVisibility(View.VISIBLE);
		errorLayout.setVisibility(View.GONE);
	}

	private void setOnRefreshErrorViews(String errorMsg) {
		mSwipeRefreshLayout.setRefreshing(false);
		//Set error text
		errorLayout.setText(errorMsg);
		errorLayout.setVisibility(View.VISIBLE);
	}

	private void requestWeatherData() {

		Observable.zip(mOpenWeatherService.getCurrentWeather(mCityName),
				mOpenWeatherService.getForecastWeather(mCityName, FORECAST_DAYS_NUMBER),
				new Func2<CurrentWeatherResponse, ForecastWeatherResponse, ResponseEnvelope>() {
					@Override
					public ResponseEnvelope call(CurrentWeatherResponse currentWeatherResponse, ForecastWeatherResponse forecastWeatherResponse) {
						return new ResponseEnvelope(currentWeatherResponse, forecastWeatherResponse);
					}
				}).subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<ResponseEnvelope>() {
					@Override
					public void onCompleted() {
						setCityWeatherViews();
					}

					@Override
					public void onError(Throwable e) {
						Timber.e("RequestWeatherData onError: " + e.getMessage());
						setOnRefreshErrorViews(getString(R.string.error_message));
					}

					@Override
					public void onNext(ResponseEnvelope responseEnvelope) {
						mResponseEnvelope = responseEnvelope;
					}
				});

	}

	private void setCityWeatherViews() {
		//If responseCode != 200, there is some error message
		if (mResponseEnvelope.getResponseCode() != 200) {
			setOnRefreshErrorViews(mResponseEnvelope.getErrorMessage());
			Timber.e("OnRefresh error from server: " + mResponseEnvelope.getErrorMessage());
		} else {
			RecyclerWeatherAdapter recyclerWeatherAdapter = new RecyclerWeatherAdapter(
					mResponseEnvelope.getCurrentWeather(),
					mResponseEnvelope.getForecastWeatherList());
			rvWeather.setAdapter(recyclerWeatherAdapter);

			setOnRefreshSucessViews();
		}
	}

}
