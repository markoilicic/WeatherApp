package com.undabot.weatherapp.presenters;

import com.undabot.weatherapp.R;
import com.undabot.weatherapp.data.api.OpenWeatherAPIService;
import com.undabot.weatherapp.data.model.OpenWeatherApi.CurrentWeatherResponse;
import com.undabot.weatherapp.data.model.OpenWeatherApi.ForecastWeatherResponse;
import com.undabot.weatherapp.data.model.OpenWeatherApi.ResponseEnvelope;
import com.undabot.weatherapp.ui.views.CityWeatherFragmentView;
import com.undabot.weatherapp.utils.ConnectionUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class CityWeatherFragmentPresenterImpl implements CityWeatherFragmentPresenter {

	private static final int FORECAST_DAYS_NUMBER = 7;

	private CityWeatherFragmentView view;

	private OpenWeatherAPIService mOpenWeatherAPIService;
	private ResponseEnvelope mResponseEnvelope;

	public CityWeatherFragmentPresenterImpl(OpenWeatherAPIService openWeatherAPIService) {
		this.mOpenWeatherAPIService = openWeatherAPIService;
	}

	@Override
	public void init(CityWeatherFragmentView view) {
		this.view = view;
	}

	@Override
	public void onRefreshTriggered(String cityName) {
		if (ConnectionUtils.isConnected(view.getViewActivity())) {
			view.setRefreshingViews();
			fetchData(cityName);
		} else {
			view.displayOnRefreshErrorViews(view.getViewContext().getString(R.string.error_no_connection));
		}
	}

	private void fetchData(String cityName) {
		Observable.zip(mOpenWeatherAPIService.getCurrentWeather(cityName),
				mOpenWeatherAPIService.getForecastWeather(cityName, FORECAST_DAYS_NUMBER),
				new Func2<CurrentWeatherResponse, ForecastWeatherResponse, ResponseEnvelope>() {
					@Override
					public ResponseEnvelope call(CurrentWeatherResponse currentWeatherResponse,
												 ForecastWeatherResponse forecastWeatherResponse) {
						return new ResponseEnvelope(currentWeatherResponse, forecastWeatherResponse);
					}
				}).subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<ResponseEnvelope>() {
					@Override
					public void onCompleted() {
						setWeatherViews();
					}

					@Override
					public void onError(Throwable e) {
						Timber.e("Request Error: " + e.getMessage());
						view.displayOnRefreshErrorViews(view.getViewContext().getString(R.string.error_message));
					}

					@Override
					public void onNext(ResponseEnvelope responseEnvelope) {
						mResponseEnvelope = responseEnvelope;
					}
				});
	}

	private void setWeatherViews() {
		if (mResponseEnvelope.getResponseCode() != 200) {
			Timber.e("Error from server: " + mResponseEnvelope.getErrorMessage());
			view.displayOnRefreshErrorViews(mResponseEnvelope.getErrorMessage());
		} else {
			view.setCityWeatherData(mResponseEnvelope.getCurrentWeather(), mResponseEnvelope.getForecastWeatherList());
			view.displayOnRefreshSuccessViews();
		}
	}

	@Override
	public void onCreate() {

	}

	@Override
	public void onStart() {

	}

	@Override
	public void onResume() {

	}

	@Override
	public void onStop() {

	}

	@Override
	public void onPause() {

	}

	@Override
	public void onDestroy() {

	}

}
