package com.undabot.weatherapp.data.model;

import com.google.gson.annotations.SerializedName;
import com.undabot.weatherapp.data.utils.TextFormatUtils;

public class Weather {

	@SerializedName("id") private long id;
	@SerializedName("main") private String mainCondition;
	@SerializedName("description") private String description;
	@SerializedName("icon") private String iconName;

	public Weather() {
		this.id = (long) -1;
		this.mainCondition = "";
		this.description = "";
		this.iconName = "";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMainCondition() {
		return mainCondition;
	}

	public void setMainCondition(String mainCondition) {
		this.mainCondition = mainCondition;
	}

	public String getDescription() {
		return TextFormatUtils.capitalizeFirstLetter(description);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}
}
