package com.undabot.weatherapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.undabot.weatherapp.R;
import com.undabot.weatherapp.data.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DrawerCityListAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private List<String> mCityList;
	private int mSelectedItem;

    public DrawerCityListAdapter(Context context, ArrayList<String> cityList) {
        super(context, R.layout.drawer_city_list_item, cityList);
        this.mContext = context;
        this.mCityList = SharedPrefsUtils.getCityList();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.drawer_city_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

		if(mSelectedItem == position){
			convertView.setBackgroundColor(mContext.getResources().getColor(R.color.drawer_selected_item));
		}

        holder.cityName.setText(mCityList.get(position));
        return convertView;
    }

	public void setSelectedItem(int position){
		mSelectedItem = position;
	}

	public int getmSelectedItem(){
		return mSelectedItem;
	}

    static class ViewHolder {
        @InjectView(R.id.tv_city_name_list_item)

        TextView cityName;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
