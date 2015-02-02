package com.undabot.weatherapp.data.model.GoogleApi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlacesAutoCompleteResponse {

	@SerializedName("status") String status;
	@SerializedName("predictions") ArrayList<Prediction> predictions;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<Prediction> getPredictions() {
		return predictions;
	}

	public void setPredictions(ArrayList<Prediction> predictions) {
		this.predictions = predictions;
	}
}
