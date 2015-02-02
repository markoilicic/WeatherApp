package com.undabot.weatherapp.data.model.GoogleApi;

import com.google.gson.annotations.SerializedName;

public class Prediction {

	@SerializedName("description") String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
