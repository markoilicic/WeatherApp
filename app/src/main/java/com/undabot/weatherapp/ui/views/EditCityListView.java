package com.undabot.weatherapp.ui.views;

import android.app.AlertDialog;

public interface EditCityListView extends BaseView {

	public void displayAddDialog(AlertDialog dialog);

	public void notifyDataSetChange();
}
