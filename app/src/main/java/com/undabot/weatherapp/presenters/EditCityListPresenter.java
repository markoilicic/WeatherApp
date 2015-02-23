package com.undabot.weatherapp.presenters;

import android.app.Activity;

import com.undabot.weatherapp.ui.views.EditCityListView;

import java.util.ArrayList;

public interface EditCityListPresenter extends LifecyclePresenter<EditCityListView> {

	public void onAddButtonPressed(Activity activity);

	public void onAddCityToList(String cityName);

	public void onItemDeletePressed(int position);

	public void onBackButtonPressed(Activity activity);

	public void onReorderFinished(ArrayList<String> list, int oldPosition, int newPosition);
}
