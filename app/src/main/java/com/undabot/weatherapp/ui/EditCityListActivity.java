package com.undabot.weatherapp.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.undabot.weatherapp.R;
import com.undabot.weatherapp.data.prefs.IntPreference;
import com.undabot.weatherapp.data.prefs.StringArrayPreference;
import com.undabot.weatherapp.modules.qualifiers.CityList;
import com.undabot.weatherapp.modules.qualifiers.SelectedPosition;
import com.undabot.weatherapp.presenters.EditCityListPresenter;
import com.undabot.weatherapp.presenters.EditCityListPresenterImpl;
import com.undabot.weatherapp.ui.adapters.EditCityListAdapter;
import com.undabot.weatherapp.ui.custom.DynamicListView;
import com.undabot.weatherapp.ui.util.ListOnDeleteItemClick;
import com.undabot.weatherapp.ui.util.ListOnReorderListener;
import com.undabot.weatherapp.ui.views.EditCityListView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class EditCityListActivity extends BaseActivity implements
		EditCityListView,
		ListOnDeleteItemClick,
		ListOnReorderListener {

	EditCityListPresenter presenter;

	@InjectView(R.id.toolbar) Toolbar toolbar;
	@InjectView(R.id.lv_edit_activity_list) DynamicListView lvCityList;

	@Inject @CityList StringArrayPreference cityListPreference;
	@Inject @SelectedPosition IntPreference selectedPosition;

	private ArrayList<String> mCityList;

	private EditCityListAdapter editCityListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_city_list_activity);

		ButterKnife.inject(this);

		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mCityList = cityListPreference.getList();

		editCityListAdapter = new EditCityListAdapter(getApplicationContext(), mCityList);
		editCityListAdapter.setOnReorderListener(this);
		editCityListAdapter.setOnDeleteClickListener(this);

		lvCityList.setItemList(mCityList);
		lvCityList.setSelectedItemBorderColor(getResources().getColor(R.color.primary));
		lvCityList.setEmptyView(findViewById(R.id.empty_city_list_layout));
		lvCityList.setAdapter(editCityListAdapter);

		presenter = new EditCityListPresenterImpl(cityListPreference, selectedPosition);
		presenter.init(this);

	}

	@Override
	public void onBackPressed() {
		presenter.onBackButtonPressed(this);
	}

	@Override
	public void displayAddDialog(AlertDialog dialog) {
		dialog.show();
	}

	@Override
	public void notifyDataSetChange() {
		mCityList.clear();
		mCityList.addAll(cityListPreference.getList());
		editCityListAdapter.notifyDataSetChanged();
		editCityListAdapter.refreshIds();
	}

	@Override
	public Context getViewContext() {
		return this;
	}

	@Override
	public Activity getViewActivity() {
		return this;
	}

	@Override
	public void onDeleteBtnClick(int position) {
		presenter.onItemDeletePressed(position);
	}

	@Override
	public void onReorder(ArrayList<String> list) {
		cityListPreference.set(list);
	}

	@OnClick(R.id.fab_add_city)
	public void onAddCityClick() {
		presenter.onAddBtnPressed();
	}
}


