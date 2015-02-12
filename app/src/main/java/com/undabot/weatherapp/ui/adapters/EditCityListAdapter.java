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
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import timber.log.Timber;

public class EditCityListAdapter extends ArrayAdapter<String> {

	final int INVALID_ID = -1;

	private Context mContext;
	private ArrayList<String> mCityList;
	private HashMap<String, Integer> mIdMap = new HashMap<>();

	public EditCityListAdapter(Context context, ArrayList<String> cityList) {
		super(context, R.layout.edit_city_list_item, cityList);
		this.mContext = context;
		this.mCityList = cityList;

		for (int i = 0; i < cityList.size(); ++i) {
			mIdMap.put(cityList.get(i), i);
		}
	}

	/*	NOTE:
		Add 'android:descendantFocusability="blocksDescendants"' to every view in list item layout when using DynamicListView,
		because Motion.ACTION_DOWN won't be triggered and drag'n'drop won't work...
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Timber.d("getView");
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

	@Override
	public long getItemId(int position) {
		if (position < 0 || position >= mIdMap.size()) {
			return INVALID_ID;
		}
		String item = getItem(position);
		return mIdMap.get(item);
	}

	@Override
	public boolean hasStableIds() {
		return android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		// When reordering is done, DynamicListView calls adapter.notifyDataSetChanged()
		// So, save the newly ordered list in shared prefs
		SharedPrefsUtils.setCityList(mCityList);
	}

	static class ViewHolder {
		@InjectView(R.id.tv_city_name_list_item) TextView cityName;
		@InjectView(R.id.btn_delete) ImageButton btnDelete;

		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
		}
	}

}
