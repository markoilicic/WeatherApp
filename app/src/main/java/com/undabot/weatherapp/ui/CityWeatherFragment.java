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
import com.undabot.weatherapp.data.api.ApiServiceManager;
import com.undabot.weatherapp.data.api.OpenWeatherService;
import com.undabot.weatherapp.data.model.OpenWeatherApi.CurrentWeatherResponse;
import com.undabot.weatherapp.data.model.OpenWeatherApi.ForecastWeatherResponse;
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
	@InjectView(R.id.error_layout) View errorLayout;

	@InjectView(R.id.tv_city_name) TextView tvCityName;
	@InjectView(R.id.tv_weather_description) TextView tvWeatherDescription;
	@InjectView(R.id.tv_temperature) TextView tvTemperature;
	@InjectView(R.id.tv_temp_unit) TextView tvTempUnit;
	@InjectView(R.id.tv_temp_range_value) TextView tvTempRange;
	@InjectView(R.id.tv_wind_speed) TextView tvWindSpeed;
	@InjectView(R.id.tv_error_message) TextView tvErrorMsg;
	@InjectView(R.id.iv_weather_icon) ImageView ivWeatherIcon;
	@InjectView(R.id.iv_wind_direction) ImageView ivWindDirection;

	@InjectView(R.id.lv_forecast_weather) ListView lvForecastWeather;

	private String mCity;
	private CurrentWeatherResponse mCurrentWeather;
	private ForecastWeatherResponse mForecastWeather;

	private OpenWeatherService mOpenWeatherService;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.city_weather_fragment, container, false);

		ButterKnife.inject(this, view);

		mOpenWeatherService = new ApiServiceManager().getApiService();

		swipeRefreshLayout.setColorSchemeResources(R.color.primary, R.color.primary_dark, R.color.accent);
		swipeRefreshLayout.setOnRefreshListener(this);

		onRefresh();

		return view;
	}

	@Override
	public void onRefresh() {
		setOnRefreshStartViews();
		mCity = SharedPrefsUtils.getSelectedCity();
		requestWeatherData();
	}

	public void setOnRefreshStartViews() {
		swipeRefreshLayout.setRefreshing(true);
		cityWeatherContainer.setAlpha(0.5f);
		errorLayout.setVisibility(View.GONE);
	}

	public void setOnRefreshSucessViews() {
		swipeRefreshLayout.setRefreshing(false);
		cityWeatherContainer.setAlpha(1f);
		cityWeatherContainer.setVisibility(View.VISIBLE);
		errorLayout.setVisibility(View.GONE);
	}

	public void setOnRefreshErrorViews(String errorMsg) {
		swipeRefreshLayout.setRefreshing(false);
		cityWeatherContainer.setVisibility(View.GONE);
		//Set error text
		tvErrorMsg.setText(errorMsg);
		errorLayout.setVisibility(View.VISIBLE);
	}

	public void requestWeatherData() {

		Observable.zip(mOpenWeatherService.getCurrentWeather(SharedPrefsUtils.getWeatherOptions(), mCity),
				mOpenWeatherService.getForecastWeather(SharedPrefsUtils.getForecastWeatherOptions(), mCity),
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
						setCityWeather();
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

	private void setCityWeather() {
		//If responseCode != 200, there is some error message returned from server
		if (mCurrentWeather.getResponseCode() != 200) {
			setOnRefreshErrorViews(mCurrentWeather.getErrorMsg());
			Timber.d("OnRefresh error: " + mCurrentWeather.getErrorMsg());
		} else {
			//Current weather
			if (mCurrentWeather.getCityName().isEmpty()) {
				tvCityName.setText(mCurrentWeather.getSysMsg().getCountry());
			} else {
				tvCityName.setText(mCurrentWeather.getCityName());
			}
			tvTemperature.setText(mCurrentWeather.getMainConditions().getFormatedTemp());
			tvTempUnit.setText("°C");
			tvWeatherDescription.setText(mCurrentWeather.getWeatherList().get(0).getDescription());
			tvTempRange.setText(mCurrentWeather.getMainConditions().getFormatedTempRange());
			tvWindSpeed.setText(mCurrentWeather.getWind().getSpeed() + "m/s");

			Picasso.with(getActivity()).load(mCurrentWeather.getIconUrl()).into(ivWeatherIcon);
			ivWindDirection.setRotation(mCurrentWeather.getWind().getDegrees());

			setOnRefreshSucessViews();
		}
	}

}
