package com.undabot.weatherapp.modules;

import com.undabot.weatherapp.data.prefs.IntPreference;
import com.undabot.weatherapp.data.prefs.StringArrayPreference;
import com.undabot.weatherapp.modules.qualifiers.CityList;
import com.undabot.weatherapp.modules.qualifiers.SelectedPosition;
import com.undabot.weatherapp.presenters.EditCityListPresenter;
import com.undabot.weatherapp.presenters.EditCityListPresenterImpl;
import com.undabot.weatherapp.ui.BaseActivity;
import com.undabot.weatherapp.ui.EditCityListActivity;

import dagger.Module;
import dagger.Provides;

@Module(
		addsTo = MainModule.class,
		injects = {
				BaseActivity.class,
				EditCityListActivity.class
		},
		complete = false,
		library = true
)
public final class PresenterModule {

	@Provides
	public EditCityListPresenter provideEditCityListPresenter(@CityList StringArrayPreference cityList, @SelectedPosition IntPreference selectedItem) {
		return new EditCityListPresenterImpl(cityList, selectedItem);
	}

}
