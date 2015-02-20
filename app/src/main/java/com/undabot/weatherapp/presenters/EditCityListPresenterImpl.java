package com.undabot.weatherapp.presenters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.undabot.weatherapp.R;
import com.undabot.weatherapp.data.prefs.IntPreference;
import com.undabot.weatherapp.data.prefs.StringArrayPreference;
import com.undabot.weatherapp.data.utils.TextFormatUtils;
import com.undabot.weatherapp.ui.adapters.PlacesPredictionAdapter;
import com.undabot.weatherapp.ui.custom.DelayAutoCompleteTextView;
import com.undabot.weatherapp.ui.views.EditCityListView;

public class EditCityListPresenterImpl implements EditCityListPresenter {

	private EditCityListView view;

	private StringArrayPreference mCityListPreference;
	private IntPreference mSelectedItem;

	public EditCityListPresenterImpl(StringArrayPreference cityList, IntPreference selectedItem) {
		this.mCityListPreference = cityList;
		this.mSelectedItem = selectedItem;
	}

	@Override
	public void init(EditCityListView view) {
		this.view = view;
	}

	@Override
	public void onAddBtnPressed() {
		view.displayAddDialog(createAlertDialog());
	}

	private AlertDialog createAlertDialog() {
		//Get custom view for dialog
		View dialogView = view.getViewActivity().getLayoutInflater().inflate(R.layout.add_new_city_dialog, null);
		final DelayAutoCompleteTextView input = (DelayAutoCompleteTextView) dialogView.findViewById(R.id.et_input);
		final TextView tvWarningMsg = (TextView) dialogView.findViewById(R.id.tv_warning_message);

		//Add adapter for places prediction
		input.setAdapter(new PlacesPredictionAdapter(view.getViewActivity()));

		//Create alert dialog
		final AlertDialog dialog = new AlertDialog.Builder(view.getViewContext())
				.setTitle(R.string.add_city_dialog_title)
				.setView(dialogView)
				.setPositiveButton(R.string.text_ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						onAddCityToList(TextFormatUtils.capitalizeFirstLetterInEachWord(input.getText().toString()));
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
				if (mCityListPreference.getList().contains(inputText)) {
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

		return dialog;
	}

	@Override
	public void onAddCityToList(String cityName) {
		mCityListPreference.addItem(cityName);
		view.notifyDataSetChange();
	}

	@Override
	public void onItemDeletePressed(int position) {
		mCityListPreference.removeItem(position);
		//Handle selected position change
		if (mSelectedItem.get() >= position && position > 0) {
			mSelectedItem.set(mSelectedItem.get() - 1);
		}
		view.notifyDataSetChange();
	}

	@Override
	public void onBackButtonPressed(Activity activity) {
		NavUtils.navigateUpFromSameTask(activity);
	}


}
