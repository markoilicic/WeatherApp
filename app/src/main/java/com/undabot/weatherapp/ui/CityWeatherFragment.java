package com.undabot.weatherapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.undabot.weatherapp.R;
import com.undabot.weatherapp.data.api.ApiServiceManager;
import com.undabot.weatherapp.data.api.OpenWeatherService;
import com.undabot.weatherapp.data.model.OpenWeatherApi.CurrentWeatherResponse;
import com.undabot.weatherapp.data.model.OpenWeatherApi.ForecastWeatherResponse;
import com.undabot.weatherapp.data.prefs.StringPreference;
import com.undabot.weatherapp.data.utils.SharedPrefsUtils;
import com.undabot.weatherapp.ui.adapters.ForecastListAdapter;

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

	@InjectView(R.id.rv_forecast_weather) RecyclerView rvForecastWeather;

	private LinearLayoutManager mlayoutManager;
	private ForecastListAdapter mForecastListAdapter;

	private StringPreference mCity;
	private CurrentWeatherResponse mCurrentWeather;
	private ForecastWeatherResponse mForecastWeather;

	private OpenWeatherService mOpenWeatherService;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mCity = new StringPreference(SharedPrefsUtils.getSharedPreferences(), SharedPrefsUtils.KEY_SELECTED_CITY);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.city_weather_fragment, container, false);

		ButterKnife.inject(this, view);

		mOpenWeatherService = new ApiServiceManager().getApiService();

		mSwipeRefreshLayout.setColorSchemeResources(R.color.primary, R.color.primary_dark, R.color.accent);
		mSwipeRefreshLayout.setOnRefreshListener(this);

		rvForecastWeather.setHasFixedSize(true);
		mlayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
		rvForecastWeather.setLayoutManager(mlayoutManager);
		rvForecastWeather.setItemAnimator(new DefaultItemAnimator());

		refreshWeather();

		return view;
	}

	@Override
	public void onRefresh() {
		refreshWeather();
	}

	public void refreshWeather() {
		setOnRefreshStartViews();
		mCity.get();
		requestWeatherData();
	}

	private void setOnRefreshStartViews() {
		mSwipeRefreshLayout.setRefreshing(true);
		cityWeatherContainer.setAlpha(0.5f);
		errorLayout.setVisibility(View.GONE);
	}

	private void setOnRefreshSucessViews() {
		mSwipeRefreshLayout.setRefreshing(false);
		cityWeatherContainer.setAlpha(1f);
		cityWeatherContainer.setVisibility(View.VISIBLE);
		errorLayout.setVisibility(View.GONE);
	}

	private void setOnRefreshErrorViews(String errorMsg) {
		mSwipeRefreshLayout.setRefreshing(false);
		cityWeatherContainer.setVisibility(View.GONE);
		//Set error text
		tvErrorMsg.setText(errorMsg);
		errorLayout.setVisibility(View.VISIBLE);
	}

	private void requestWeatherData() {

		Observable.zip(mOpenWeatherService.getCurrentWeather(SharedPrefsUtils.getWeatherOptions(), mCity.get()),
				mOpenWeatherService.getForecastWeather(SharedPrefsUtils.getForecastWeatherOptions(), mCity.get()),
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
			//------Current weather views-----
			tvCityName.setText(mCurrentWeather.getCityName());
			tvTemperature.setText(mCurrentWeather.getMainConditions().getFormatedTemp());
			tvTempUnit.setText("°C");
			tvWeatherDescription.setText(mCurrentWeather.getWeatherList().get(0).getDescription());
			tvTempRange.setText(mCurrentWeather.getMainConditions().getFormatedTempRange());
			tvWindSpeed.setText(mCurrentWeather.getWind().getSpeed() + "m/s");

			Picasso.with(getActivity()).load(mCurrentWeather.getWeatherList().get(0).getIconUrl()).into(ivWeatherIcon);
			ivWindDirection.setRotation(mCurrentWeather.getWind().getDegrees());


			//-----Forecast views-----
			mForecastListAdapter = new ForecastListAdapter(mForecastWeather.getForecastDayWeather());
			rvForecastWeather.setAdapter(mForecastListAdapter);

			/*
			Temporarily swipeRefreshLayout is not compatible with recyclerView,
			swipeRefreshLayout triggers when you try to scroll down recyclerView
			Issue is known and it will be fixed, but for now this little hack do the trick

			Issue link: https://code.google.com/p/android/issues/detail?id=78191
			*/
			rvForecastWeather.setOnScrollListener(new RecyclerView.OnScrollListener() {
				@Override
				public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
					super.onScrolled(recyclerView, dx, dy);
					//If first visible item in RecyclerView is not 0, disable swipeRefreshLayout
					mSwipeRefreshLayout.setEnabled(mlayoutManager.findFirstCompletelyVisibleItemPosition() == 0);
				}
			});

			setOnRefreshSucessViews();
		}
	}

}
