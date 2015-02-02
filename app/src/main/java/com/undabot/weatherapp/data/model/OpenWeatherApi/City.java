package com.undabot.weatherapp.data.model.OpenWeatherApi;

import com.google.gson.annotations.SerializedName;

public class City {

	@SerializedName("id") private long id;
	@SerializedName("name") private String name;
	@SerializedName("coord") private Coordinates coordinates;
	@SerializedName("country") private String country;

	public City() {
		this.id = (long) -1;
		this.name = "";
		this.coordinates = new Coordinates();
		this.country = "";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
