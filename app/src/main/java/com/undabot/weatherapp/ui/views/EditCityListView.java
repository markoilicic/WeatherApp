package com.undabot.weatherapp.ui.views;

import android.app.AlertDialog;

import java.util.ArrayList;

public interface EditCityListView extends BaseView {

	public void displayAddDialog(AlertDialog dialog);

	public void setupListAndAdapters(ArrayList<String> cityList);

	public void notifyDataSetChange(ArrayList<String> cityList);

}
