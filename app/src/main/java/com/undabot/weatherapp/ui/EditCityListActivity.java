package com.undabot.weatherapp.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.undabot.weatherapp.R;
import com.undabot.weatherapp.data.utils.SharedPrefsUtils;
import com.undabot.weatherapp.data.utils.TextFormatUtils;
import com.undabot.weatherapp.ui.adapters.CityListAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.observables.ViewObservable;
import rx.functions.Action1;

public class EditCityListActivity extends ActionBarActivity {

	@InjectView(R.id.toolbar) Toolbar toolbar;
	@InjectView(R.id.lv_edit_activity_list) ListView lvCityList;

	private ArrayList<String> mCityList;
	private CityListAdapter cityListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_city_list_activity);

		ButterKnife.inject(this);

		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mCityList = (ArrayList<String>) SharedPrefsUtils.getCityList();
		cityListAdapter = new CityListAdapter(getApplicationContext(), mCityList);
		lvCityList.setAdapter(cityListAdapter);

	}

	/**
	 * Creates and shows dialog for adding new city
	 */
	private void showAddNewCityDialog() {
		//Get custom view for dialog
		View dialogView = getLayoutInflater().inflate(R.layout.add_new_city_dialog, null);
		final EditText input = (EditText) dialogView.findViewById(R.id.et_input);
		final TextView tvWarningMsg = (TextView) dialogView.findViewById(R.id.tv_warning_message);

		//Create alert dialog
		final AlertDialog dialog = new AlertDialog.Builder(this)
				.setTitle(R.string.add_city_dialog_title)
				.setView(dialogView)
				.setPositiveButton(R.string.text_ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						mCityList.add(TextFormatUtils.capitalizeFirstLetterInEachWord(input.getText().toString()));
						SharedPrefsUtils.setCityList(mCityList);
						cityListAdapter.notifyDataSetChanged();
					}
				}).setNegativeButton(R.string.text_cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						//Dismiss
					}
				}).show();

		//Disable positive button at start
		dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

		//Create text input observable
		Observable inputObservable = ViewObservable.input(input, false);
		inputObservable.subscribe(new Action1() {
			String inputText;

			@Override
			public void call(Object o) {
				inputText = TextFormatUtils.capitalizeFirstLetterInEachWord(input.getText().toString());
				//Check if city is already in list or length<3 and disable OK button
				if (mCityList.contains(inputText)) {
					dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
					tvWarningMsg.setText(R.string.add_city_dialog_warning_in_list);
				} else if (inputText.length() < 3) {
					dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
				} else {
					dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
					tvWarningMsg.setText("");
				}
			}
		});
	}

	@OnClick(R.id.fab_add_city)
	public void onAddCityClick() {
		showAddNewCityDialog();
	}

}


