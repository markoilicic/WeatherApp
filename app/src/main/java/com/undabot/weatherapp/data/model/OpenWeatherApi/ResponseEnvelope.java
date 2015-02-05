package com.undabot.weatherapp.data.model.OpenWeatherApi;

import com.undabot.weatherapp.App;
import com.undabot.weatherapp.R;

import java.util.List;

public class ResponseEnvelope {

	private int mCurrentWeatherResponseCode;
	private int mForecastWeatherResponseCode;
	private String mErrorMessage;
	private int mResponseCode;

	private CurrentWeatherResponse mCurrentWeatherResponse;
	private ForecastWeatherResponse mForecastWeatherResponse;

	public ResponseEnvelope(CurrentWeatherResponse currentWeatherResponse, ForecastWeatherResponse forecastWeatherResponse) {
		this.mCurrentWeatherResponse = currentWeatherResponse;
		this.mForecastWeatherResponse = forecastWeatherResponse;
		this.mCurrentWeatherResponseCode = mCurrentWeatherResponse.getResponseCode();
		this.mForecastWeatherResponseCode = mCurrentWeatherResponse.getResponseCode();
		handleErrors();
	}

	/**
	 * Server always returns HTTP response code '200', even if city is not found or there is some other error.
	 * For now, there is attribute 'cod' which returns '200' if response is OK.
	 * Other errors are not consistent and not documented.
	 * <p/>
	 * This method is used to handle some errors and to set appropriate message
	 */

	public void handleErrors() {
		//Check if any response 'cod' is not 200
		if (mCurrentWeatherResponseCode != 200 || mForecastWeatherResponseCode != 200) {
			if (mCurrentWeatherResponseCode == 404 || mForecastWeatherResponseCode != 404) {
				mErrorMessage = App.getAppContext().getString(R.string.error_city_not_found);
			} else {
				mErrorMessage = App.getAppContext().getString(R.string.error_server_unknown);
			}
			this.mResponseCode = 404;
		} else {
			this.mResponseCode = 200;
		}
	}

	public String getErrorMessage() {
		return mErrorMessage;
	}

	public void setErrorMessage(String mErrorMessage) {
		this.mErrorMessage = mErrorMessage;
	}

	public int getResponseCode() {
		return mResponseCode;
	}

	public void setResponseCode(int mResponseCode) {
		this.mResponseCode = mResponseCode;
	}

	public CurrentWeatherResponse getCurrentWeather() {
		return mCurrentWeatherResponse;
	}

	public void setCurrentWeather(CurrentWeatherResponse mCurrentWeatherResponse) {
		this.mCurrentWeatherResponse = mCurrentWeatherResponse;
	}

	public List<ForecastDayWeather> getForecastWeatherList() {
		return mForecastWeatherResponse.getForecastDayWeather();
	}

	public void setForecastWeatherResponse(ForecastWeatherResponse mForecastWeatherResponse) {
		this.mForecastWeatherResponse = mForecastWeatherResponse;
	}
}
