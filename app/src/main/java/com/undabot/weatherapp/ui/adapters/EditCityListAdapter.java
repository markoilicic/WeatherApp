package com.undabot.weatherapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.undabot.weatherapp.R;
import com.undabot.weatherapp.data.prefs.IntPreference;
import com.undabot.weatherapp.data.utils.SharedPrefsUtils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class EditCityListAdapter extends ArrayAdapter<String> {

	private Context mContext;
	private ArrayList<String> mCityList;

	public EditCityListAdapter(Context context, ArrayList<String> cityList) {
		super(context, R.layout.edit_city_list_item, cityList);
		this.mContext = context;
		this.mCityList = cityList;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.edit_city_list_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.cityName.setText(mCityList.get(position));

		holder.btnDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCityList.remove(position);
				SharedPrefsUtils.setCityList(mCityList);
				// Subtract 1 from selectedPosition if position <= selectedPosition,
				// so drawer opens the same selected item or the one above if selected is erased
				IntPreference selectedPosition = new IntPreference(SharedPrefsUtils.getSharedPreferences(), SharedPrefsUtils.KEY_SELECTED_POSITION);
				if (selectedPosition.get() >= position && position > 0) {
					selectedPosition.set(selectedPosition.get() - 1);
				}
				notifyDataSetChanged();
			}
		});

		return convertView;
	}

	static class ViewHolder {
		@InjectView(R.id.tv_city_name_list_item) TextView cityName;
		@InjectView(R.id.btn_delete) ImageButton btnDelete;

		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
		}
	}

}
