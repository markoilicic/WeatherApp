package com.undabot.weatherapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.undabot.weatherapp.R;
import com.undabot.weatherapp.data.api.ApiService;
import com.undabot.weatherapp.data.api.ApiServiceManager;
import com.undabot.weatherapp.data.model.CurrentWeatherResponse;
import com.undabot.weatherapp.data.model.ForecastWeatherResponse;
import com.undabot.weatherapp.data.utils.SharedPrefsUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import timber.log.Timber;

//TODO This is not finished, I did just some things for testing

public class CityWeatherFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

	@InjectView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
	@InjectView(R.id.city_weather_container) View cityWeatherContainer;
	@InjectView(R.id.city_header_container) View cityHeaderContainer;

	@InjectView(R.id.tv_city_name) TextView tvCityName;
	@InjectView(R.id.tv_weather_description) TextView tvWeatherDescription;
	@InjectView(R.id.tv_temperature) TextView tvTemperature;
	@InjectView(R.id.tv_temp_unit) TextView tvTempUnit;
	@InjectView(R.id.tv_temp_range_value) TextView tvTempRange;
	@InjectView(R.id.tv_temp_range_unit) TextView tvTempRangeUnit;
	@InjectView(R.id.tv_wind_speed) TextView tvWindSpeed;
	@InjectView(R.id.iv_weather_icon) ImageView ivWeatherIcon;

	@InjectView(R.id.lv_forecast_weather) ListView lvForecastWeather;

	private String mCity;
	private CurrentWeatherResponse mCurrentWeather;
	private ForecastWeatherResponse mForecastWeather;

	private ApiService mApiService;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.city_weather_fragment, container, false);

		ButterKnife.inject(this, view);

		mApiService = new ApiServiceManager().getApiService();
		mCity = SharedPrefsUtils.getSelectedCity();

		swipeRefreshLayout.setColorSchemeResources(R.color.primary, R.color.primary_dark, R.color.accent);
		swipeRefreshLayout.setOnRefreshListener(this);

		onRefresh();

		return view;
	}

	@Override
	public void onRefresh() {
		swipeRefreshLayout.setRefreshing(true);
		mCity = SharedPrefsUtils.getSelectedCity();
		requestWeatherData();
	}


	public void requestWeatherData() {

		Observable.zip(mApiService.getCurrentWeather(SharedPrefsUtils.getWeatherOptions(), mCity),
				mApiService.getForecastWeather(SharedPrefsUtils.getForecastWeatherOptions(), mCity),
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
						setCurrentWeather();
						swipeRefreshLayout.setRefreshing(false);
					}

					@Override
					public void onError(Throwable e) {
						Timber.e("Error on ");
						swipeRefreshLayout.setRefreshing(false);
					}

					@Override
					public void onNext(Object o) {

					}
				});

	}

	private void setCurrentWeather() {
		tvCityName.setText(mCurrentWeather.getCityName());
		tvTemperature.setText(String.valueOf(mCurrentWeather.getMainConditions().getTemperature()));
		tvTempUnit.setText("°C");
		tvWeatherDescription.setText(mCurrentWeather.getWeatherList().get(0).getDescription());
		Picasso.with(getActivity()).load(mCurrentWeather.getIconUrl()).into(ivWeatherIcon);
	}

}
