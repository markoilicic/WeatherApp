package com.undabot.weatherapp.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.undabot.weatherapp.R;
import com.undabot.weatherapp.data.utils.SharedPrefsUtils;
import com.undabot.weatherapp.ui.adapters.CityListAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.android.observables.ViewObservable;
import rx.functions.Action1;

public class EditCityListActivity extends ActionBarActivity {

	@InjectView(R.id.app_bar) Toolbar mAppBar;
	@InjectView(R.id.lv_edit_activity_list) ListView lvCityList;
	@InjectView(R.id.fab_add_city) FloatingActionButton btnAddCity;

	private ArrayList<String> mCityList;
	private CityListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_city_list_activity);

		ButterKnife.inject(this);

		setSupportActionBar(mAppBar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mCityList = (ArrayList<String>) SharedPrefsUtils.getCityList();
		adapter = new CityListAdapter(getApplicationContext(), mCityList);
		lvCityList.setAdapter(adapter);

		//Listen for add new city button click
		ViewObservable.clicks(btnAddCity, false).subscribe(new Action1<FloatingActionButton>() {
			@Override
			public void call(FloatingActionButton floatingActionButton) {
				showAddNewCityDialog();
			}
		});

	}

	//TODO create custom alert dialog layout
	//Create and show dialog for adding new city
	private void showAddNewCityDialog() {
		final EditText inputEditText = new EditText(this);

		//Create alert dialog for adding new city
		final AlertDialog addCityDialog = new AlertDialog.Builder(this)
				.setTitle(R.string.edit_city_list_activity_dialog_title)
				.setView(inputEditText)
				.setPositiveButton(R.string.text_ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String inputText;
						inputText = inputEditText.getText().toString();
						mCityList.add(inputText);
						SharedPrefsUtils.setCityList(mCityList);
						lvCityList.setAdapter(adapter);
					}
				}).setNegativeButton(R.string.text_cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						//Dismiss
					}
				}).show();

		//Disable positive button at start
		if (inputEditText.getText().toString().equals("")) {
			addCityDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
		}
		inputEditText.setPadding(30, 20, 30, 20);
		inputEditText.setHint(R.string.edit_city_list_activity_dialog_hint);

		//Create text input observable
		Observable inputObservable = ViewObservable.input(inputEditText, false);

		//Subscribe for input change
		inputObservable.subscribe(new Action1() {
			String inputText;

			@Override
			public void call(Object o) {
				inputText = inputEditText.getText().toString();
				//Check if inputed city is already in list and disable OK button
				if (mCityList.contains(inputText) || inputText.equals("")) {
					addCityDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
					Toast.makeText(getApplicationContext(), inputText + " already in list", Toast.LENGTH_SHORT).show();
				} else {
					addCityDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
				}
			}
		});

	}

}


