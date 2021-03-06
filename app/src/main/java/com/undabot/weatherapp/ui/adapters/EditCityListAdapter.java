package com.undabot.weatherapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.undabot.weatherapp.R;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class EditCityListAdapter extends ArrayAdapter<String> {

	final int INVALID_ID = -1;

	private Context mContext;
	private ArrayList<String> mCityList;
	private HashMap<String, Integer> mIdMap = new HashMap<>();

	private OnDeleteItemListener onDeleteClickListener;

	public EditCityListAdapter(Context context, ArrayList<String> cityList) {
		super(context, R.layout.edit_city_list_item, cityList);
		this.mContext = context;
		this.mCityList = cityList;

		refreshIds();
	}

	/*	NOTE:
		Add 'android:descendantFocusability="blocksDescendants"' to every view in list item layout when using DynamicListView,
		because Motion.ACTION_DOWN won't be triggered and drag'n'drop won't work...
	 */
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
				if (onDeleteClickListener != null) {
					onDeleteClickListener.onDeleteItemClicked(position);
				}
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

	/**
	 * Call this method whenever you add/remove items in the list to refresh items ids
	 */
	public void refreshIds() {
		mIdMap.clear();
		for (int i = 0; i < mCityList.size(); ++i) {
			mIdMap.put(mCityList.get(i), i);
		}
	}

	/**
	 * Set on delete item listener
	 *
	 * @param onDeleteClickListener {@link OnDeleteItemListener}
	 */
	public void setOnDeleteItemListener(OnDeleteItemListener onDeleteClickListener) {
		this.onDeleteClickListener = onDeleteClickListener;
	}

	/**
	 * Used for notify that item in list is deleted
	 */
	public interface OnDeleteItemListener {

		/**
		 * Returns deleted position
		 *
		 * @param position deleted item position
		 */
		public void onDeleteItemClicked(int position);
	}

	static class ViewHolder {
		@InjectView(R.id.tv_city_name_list_item) TextView cityName;
		@InjectView(R.id.btn_delete) ImageButton btnDelete;

		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
		}
	}

}
