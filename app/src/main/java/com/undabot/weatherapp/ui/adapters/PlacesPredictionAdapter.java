package com.undabot.weatherapp.ui.adapters;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.undabot.weatherapp.data.api.GooglePlacesAPIService;
import com.undabot.weatherapp.data.model.GoogleApi.Prediction;

import java.util.ArrayList;
import java.util.List;

public class PlacesPredictionAdapter extends ArrayAdapter<String> {

	private List<String> predictions;

	private GooglePlacesAPIService mGooglePlacesService;

	public PlacesPredictionAdapter(Activity context, GooglePlacesAPIService googlePlacesAPIService) {
		super(context, android.R.layout.simple_dropdown_item_1line);
		this.predictions = new ArrayList<>();
		this.mGooglePlacesService = googlePlacesAPIService;
	}

	@Override
	public int getCount() {
		return predictions.size();
	}

	@Override
	public String getItem(int index) {
		return predictions.get(index);
	}

	@Override
	public Filter getFilter() {
		return new Filter() {

			@Override
			protected FilterResults performFiltering(CharSequence inputText) {
				FilterResults filterResults = new FilterResults();

				if (inputText != null) {
					// Retrieve the autocomplete results
					List<Prediction> new_predictions = mGooglePlacesService.getPlacesPrediction(inputText.toString()).getPredictions();
					predictions.clear();
					for (int i = 0; i < new_predictions.size(); i++) {
						predictions.add(new_predictions.get(i).getName());
					}

					// Assign the values and count to the FilterResults object
					filterResults.values = predictions;
					filterResults.count = predictions.size();
				}
				return filterResults;
			}

			@Override
			protected void publishResults(CharSequence inputText,
										  FilterResults results) {
				if (results != null && results.count > 0) {
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}
			}
		};
	}

}
