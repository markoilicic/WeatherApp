package com.undabot.weatherapp.presenters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.undabot.weatherapp.R;
import com.undabot.weatherapp.data.api.GooglePlacesAPIService;
import com.undabot.weatherapp.data.prefs.IntPreference;
import com.undabot.weatherapp.data.prefs.StringArrayPreference;
import com.undabot.weatherapp.data.utils.TextFormatUtils;
import com.undabot.weatherapp.ui.adapters.PlacesPredictionAdapter;
import com.undabot.weatherapp.ui.custom.DelayAutoCompleteTextView;
import com.undabot.weatherapp.ui.views.EditCityListView;

import java.util.ArrayList;

public class EditCityListPresenterImpl implements EditCityListPresenter {

	private EditCityListView view;

	private StringArrayPreference mCityListPreference;
	private IntPreference mSelectedItem;

	private GooglePlacesAPIService mGooglePlacesAPIService;

	public EditCityListPresenterImpl(StringArrayPreference cityList,
									 IntPreference selectedItem,
									 GooglePlacesAPIService googlePlacesApiService) {
		this.mCityListPreference = cityList;
		this.mSelectedItem = selectedItem;
		this.mGooglePlacesAPIService = googlePlacesApiService;
	}

	@Override
	public void init(EditCityListView view) {
		this.view = view;
	}

	@Override
	public void onAddButtonPressed(Activity activity) {
		view.displayAddDialog(createAlertDialog(activity));
	}

	@Override
	public void onAddCityToList(String cityName) {
		mCityListPreference.addItem(cityName);
		view.notifyDataSetChange();
	}

	@Override
	public void onItemDeletePressed(int position) {
		mCityListPreference.removeItem(position);
		handleDrawerSelectedPositionOnDelete(position);
		view.notifyDataSetChange();
	}

	@Override
	public void onBackButtonPressed(Activity activity) {
		NavUtils.navigateUpFromSameTask(activity);
	}

	@Override public void OnReorderFinished(ArrayList<String> list, int oldPosition, int newPosition) {
		mCityListPreference.set(list);
		handleDrawerSelectedPositionChangeOnReorder(oldPosition, newPosition);
	}

	@Override
	public void onCreate() {

	}

	@Override
	public void onStart() {

	}

	@Override
	public void onResume() {

	}

	@Override
	public void onStop() {

	}

	@Override
	public void onPause() {

	}

	@Override
	public void onDestroy() {

	}

	/**
	 * Handles selected position change in drawer after list is reordered
	 *
	 * @param oldPosition item original position
	 * @param newPosition item new position
	 */
	private void handleDrawerSelectedPositionChangeOnReorder(int oldPosition, int newPosition) {
		if (oldPosition < mSelectedItem.get() && newPosition >= mSelectedItem.get()) {
			mSelectedItem.set(mSelectedItem.get() - 1);
		} else if (oldPosition > mSelectedItem.get() && newPosition <= mSelectedItem.get()) {
			mSelectedItem.set(mSelectedItem.get() + 1);
		} else if (oldPosition == mSelectedItem.get()) {
			mSelectedItem.set(newPosition);
		}
	}

	/**
	 * Handles drawer selected position change when some item in the list is deleted
	 *
	 * @param deletedPosition position of deleted item
	 */
	private void handleDrawerSelectedPositionOnDelete(int deletedPosition) {
		if (mSelectedItem.get() >= deletedPosition && deletedPosition > 0) {
			mSelectedItem.set(mSelectedItem.get() - 1);
		}
	}

	/**
	 * Creates dialog for adding new cities to list
	 *
	 * @return AlertDialog
	 */
	private AlertDialog createAlertDialog(Activity activity) {
		//Get custom view for dialog
		View dialogView = activity.getLayoutInflater().inflate(R.layout.add_new_city_dialog, null);
		final DelayAutoCompleteTextView input = (DelayAutoCompleteTextView) dialogView.findViewById(R.id.et_input);
		final TextView tvWarningMsg = (TextView) dialogView.findViewById(R.id.tv_warning_message);

		//Add adapter for places prediction
		input.setAdapter(new PlacesPredictionAdapter(activity, mGooglePlacesAPIService));

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
		final Button positiveBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
		positiveBtn.setEnabled(false);

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
					positiveBtn.setEnabled(false);
					tvWarningMsg.setText(R.string.add_city_dialog_warning_in_list);
				} else if (inputText.length() < 3) {
					positiveBtn.setEnabled(false);
					tvWarningMsg.setText("");
				} else {
					positiveBtn.setEnabled(true);
					tvWarningMsg.setText("");
				}
			}
		});

		return dialog;
	}
}
