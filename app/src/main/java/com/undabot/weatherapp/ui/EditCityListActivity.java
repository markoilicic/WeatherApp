package com.undabot.weatherapp.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.undabot.weatherapp.R;
import com.undabot.weatherapp.data.utils.SharedPrefsUtils;
import com.undabot.weatherapp.data.utils.TextFormatUtils;
import com.undabot.weatherapp.ui.adapters.CityListAdapter;
import com.undabot.weatherapp.ui.adapters.PlacesPredictionAdapter;
import com.undabot.weatherapp.ui.custom.DelayAutoCompleteTextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

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

		mCityList = SharedPrefsUtils.getCityList();
		cityListAdapter = new CityListAdapter(getApplicationContext(), mCityList);
		lvCityList.setAdapter(cityListAdapter);

	}

	/**
	 * Creates and shows dialog for adding new city
	 */
	private void showAddNewCityDialog() {
		//Get custom view for dialog
		View dialogView = getLayoutInflater().inflate(R.layout.add_new_city_dialog, null);
		final DelayAutoCompleteTextView input = (DelayAutoCompleteTextView) dialogView.findViewById(R.id.et_input);
		final TextView tvWarningMsg = (TextView) dialogView.findViewById(R.id.tv_warning_message);

		//Add adapter for places prediction
		input.setAdapter(new PlacesPredictionAdapter(this));

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
				}).setNegativeButton(R.string.text_cancel, null).show();

		//Disable positive button at start
		dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

		//Create onTextChangeListener
		input.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void afterTextChanged(Editable input) {
				String inputText = TextFormatUtils.capitalizeFirstLetterInEachWord(input.toString());
				//Check if city is already in list or length<3 and disable OK button
				if (mCityList.contains(inputText)) {
					dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
					tvWarningMsg.setText(R.string.add_city_dialog_warning_in_list);
				} else if (inputText.length() < 3) {
					dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
					tvWarningMsg.setText("");
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


