package com.undabot.weatherapp.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.undabot.weatherapp.App;
import com.undabot.weatherapp.R;
import com.undabot.weatherapp.data.model.OpenWeatherApi.CurrentWeatherResponse;
import com.undabot.weatherapp.data.model.OpenWeatherApi.ForecastDayWeather;
import com.undabot.weatherapp.data.model.OpenWeatherApi.Weather;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RecyclerWeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private static final int TYPE_HEADER = 0;
	private static final int TYPE_ITEM = 1;

	private CurrentWeatherResponse mCurrentWeather;
	private List<ForecastDayWeather> mForecastList;

	public RecyclerWeatherAdapter(CurrentWeatherResponse currentWeather, List<ForecastDayWeather> forecastList) {
		this.mCurrentWeather = currentWeather;
		this.mForecastList = forecastList;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (viewType == TYPE_HEADER) {
			//Header viewHolder
			View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_weather_header, parent, false);
			return new HeaderViewHolder(itemView);

		} else if (viewType == TYPE_ITEM) {
			//Item viewHolder
			View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_list_item, parent, false);
			return new ItemViewHolder(itemView);
		}

		throw new RuntimeException("There is no type that matches the type " + viewType);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		//Set header view
		if (holder instanceof HeaderViewHolder) {
			HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
			//Set text
			headerHolder.tvCityName.setText(mCurrentWeather.getCityName());
			headerHolder.tvTemperature.setText(mCurrentWeather.getMainConditions().getFormatedTemp());
			headerHolder.tvTempUnit.setText("°C");
			headerHolder.tvWeatherDescription.setText(mCurrentWeather.getWeatherList().get(0).getDescription());
			headerHolder.tvTempRange.setText(mCurrentWeather.getMainConditions().getFormatedTempRange());
			headerHolder.tvWindSpeed.setText(mCurrentWeather.getWind().getSpeed() + "m/s");
			//Set images
			Picasso.with(App.getAppContext()).load(mCurrentWeather.getWeatherList().get(0).getIconUrl()).into(headerHolder.ivWeatherIcon);
			headerHolder.ivWindDirection.setRotation(mCurrentWeather.getWind().getDegrees());

			//Set item view
		} else if (holder instanceof ItemViewHolder) {
			ItemViewHolder itemHolder = (ItemViewHolder) holder;
			ForecastDayWeather dayWeather = getItem(position);
			Weather weather = dayWeather.getWeatherList().get(0);
			//Set text
			itemHolder.dayName.setText(dayWeather.getDayNameShort());
			itemHolder.weatherDescription.setText(weather.getDescription());
			itemHolder.temp.setText(String.valueOf(dayWeather.getTemperature().getFormatedTemp()));
			itemHolder.tempRange.setText(dayWeather.getTemperature().getFormatedTempRange());
			//Set images
			Picasso.with(App.getAppContext()).load(weather.getIconUrl()).into(itemHolder.weatherIcon);
		}
	}

	@Override
	public int getItemCount() {
		return mForecastList.size() + 1; // Need to +1 count because of added header
	}

	@Override
	public int getItemViewType(int position) {
		return isPositionHeader(position) ? TYPE_HEADER : TYPE_ITEM;
	}

	private boolean isPositionHeader(int position) {
		return position == TYPE_HEADER;
	}

	private ForecastDayWeather getItem(int position) {
		return mForecastList.get(position - 1); //Need to -1 position because of added header
	}

	public static class ItemViewHolder extends RecyclerView.ViewHolder {

		@InjectView(R.id.tv_list_day_name) TextView dayName;
		@InjectView(R.id.tv_list_weather_description) TextView weatherDescription;
		@InjectView(R.id.tv_list_temp) TextView temp;
		@InjectView(R.id.tv_list_temp_range) TextView tempRange;
		@InjectView(R.id.iv_list_weather_icon) ImageView weatherIcon;

		public ItemViewHolder(View itemView) {
			super(itemView);
			ButterKnife.inject(this, itemView);
		}
	}

	public static class HeaderViewHolder extends RecyclerView.ViewHolder {

		@InjectView(R.id.tv_city_name) TextView tvCityName;
		@InjectView(R.id.tv_weather_description) TextView tvWeatherDescription;
		@InjectView(R.id.tv_temperature) TextView tvTemperature;
		@InjectView(R.id.tv_temp_unit) TextView tvTempUnit;
		@InjectView(R.id.tv_temp_range_value) TextView tvTempRange;
		@InjectView(R.id.tv_wind_speed) TextView tvWindSpeed;
		@InjectView(R.id.iv_weather_icon) ImageView ivWeatherIcon;
		@InjectView(R.id.iv_wind_direction) ImageView ivWindDirection;

		public HeaderViewHolder(View itemView) {
			super(itemView);
			ButterKnife.inject(this, itemView);
		}
	}
}
