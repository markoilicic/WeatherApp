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
import com.undabot.weatherapp.data.model.OpenWeatherApi.ForecastDayWeather;
import com.undabot.weatherapp.data.model.OpenWeatherApi.Weather;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ForecastListAdapter extends RecyclerView.Adapter<ForecastListAdapter.ViewHolder> {
	private List<ForecastDayWeather> mForecastList;

	public ForecastListAdapter(List<ForecastDayWeather> forecastList) {
		this.mForecastList = forecastList;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_list_item, parent, false);
		ViewHolder holder = new ViewHolder(itemView);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		ForecastDayWeather dayWeather = mForecastList.get(position);
		Weather weather = dayWeather.getWeatherList().get(0);

		holder.dayName.setText(dayWeather.getDayNameShort());
		holder.weatherDescription.setText(weather.getDescription());
		holder.temp.setText(String.valueOf(dayWeather.getTemperature().getFormatedTemp()));
		holder.tempRange.setText(dayWeather.getTemperature().getFormatedTempRange());
		Picasso.with(App.getAppContext()).load(weather.getIconUrl()).into(holder.weatherIcon);

	}

	@Override
	public int getItemCount() {
		return mForecastList.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		@InjectView(R.id.tv_list_day_name) TextView dayName;
		@InjectView(R.id.tv_list_weather_description) TextView weatherDescription;
		@InjectView(R.id.tv_list_temp) TextView temp;
		@InjectView(R.id.tv_list_temp_range) TextView tempRange;
		@InjectView(R.id.iv_list_weather_icon) ImageView weatherIcon;

		public ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.inject(this, itemView);
		}
	}
}
