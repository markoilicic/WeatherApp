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
import com.undabot.weatherapp.data.utils.SharedPrefsUtils;
import com.undabot.weatherapp.ui.adapters.RecyclerWeatherAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import timber.log.Timber;


public class CityWeatherFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

	@InjectView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;
	@InjectView(R.id.error_layout) TextView errorLayout;

	@InjectView(R.id.rv_forecast_weather) RecyclerView rvWeather;

	private LinearLayoutManager mlayoutManager;
	private RecyclerWeatherAdapter mRecyclerWeatherAdapter;

	private String mCityName;
	private boolean isFirstTimeRequest = true;

	private CurrentWeatherResponse mCurrentWeather;
	private ForecastWeatherResponse mForecastWeather;

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
		mlayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
		rvWeather.setLayoutManager(mlayoutManager);

		if (isFirstTimeRequest) {
			isFirstTimeRequest = false;
			refreshWeather();
		}

		return view;
	}

	@Override
	public void onRefresh() {
		refreshWeather();
	}

	public void refreshWeather() {
		setOnRefreshStartViews();
		requestWeatherData();
		isFirstTimeRequest = false;
	}

	private void setOnRefreshStartViews() {
		mSwipeRefreshLayout.setRefreshing(true);
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

		Observable.zip(mOpenWeatherService.getCurrentWeather(SharedPrefsUtils.getWeatherOptions(), mCityName),
				mOpenWeatherService.getForecastWeather(SharedPrefsUtils.getForecastWeatherOptions(), mCityName),
				new Func2<CurrentWeatherResponse, ForecastWeatherResponse, Object>() {
					@Override
					public Object call(CurrentWeatherResponse currentWeatherResponse, ForecastWeatherResponse forecastWeatherResponse) {
						mCurrentWeather = currentWeatherResponse;
						mForecastWeather = forecastWeatherResponse;
						return null;
					}
				}).subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<Object>() {
					@Override
					public void onCompleted() {
						setCityWeatherViews();
					}

					@Override
					public void onError(Throwable e) {
						Timber.e("RequestWeatherData onError: " + e.getMessage());
						setOnRefreshErrorViews(e.getMessage());
					}

					@Override
					public void onNext(Object o) {

					}
				});

	}

	private void setCityWeatherViews() {
		//TODO manage errors in smarter way
		//If responseCode != 200, there is some error message returned from server (usually)
		if (mCurrentWeather.getResponseCode() != 200 || mForecastWeather.getResponseCode() != 200) {
			setOnRefreshErrorViews(mCurrentWeather.getErrorMsg());
			Timber.e("OnRefresh error from server: " + mCurrentWeather.getErrorMsg());
		} else {
			mRecyclerWeatherAdapter = new RecyclerWeatherAdapter(mCurrentWeather, mForecastWeather.getForecastDayWeather());
			rvWeather.setAdapter(mRecyclerWeatherAdapter);

			setOnRefreshSucessViews();
		}
	}

}
