package com.undabot.weatherapp.presenters;

import android.app.Activity;

import com.undabot.weatherapp.ui.views.EditCityListView;

public interface EditCityListPresenter extends BasePresenter<EditCityListView> {

	public void onAddBtnPressed();

	public void onAddCityToList(String cityName);

	public void onItemDeletePressed(int position);

	public void onBackButtonPressed(Activity activity);

}
