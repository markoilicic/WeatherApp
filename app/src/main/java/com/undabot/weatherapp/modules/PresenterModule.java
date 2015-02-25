package com.undabot.weatherapp.modules;

import com.undabot.weatherapp.data.api.GooglePlacesAPIService;
import com.undabot.weatherapp.data.prefs.IntPreference;
import com.undabot.weatherapp.data.prefs.StringArrayPreference;
import com.undabot.weatherapp.modules.qualifiers.CityList;
import com.undabot.weatherapp.modules.qualifiers.SelectedPosition;
import com.undabot.weatherapp.presenters.CityWeatherActivityPresenter;
import com.undabot.weatherapp.presenters.CityWeatherActivityPresenterImpl;
import com.undabot.weatherapp.presenters.EditCityListPresenter;
import com.undabot.weatherapp.presenters.EditCityListPresenterImpl;
import com.undabot.weatherapp.ui.CityWeatherActivity;
import com.undabot.weatherapp.ui.CityWeatherFragment;
import com.undabot.weatherapp.ui.EditCityListActivity;

import dagger.Module;
import dagger.Provides;

@Module(
		addsTo = MainModule.class,
		injects = {
				EditCityListActivity.class,
				CityWeatherActivity.class,
				CityWeatherFragment.class
		}

)
public final class PresenterModule {

	@Provides
	public EditCityListPresenter provideEditCityListPresenter(@CityList StringArrayPreference cityList,
															  @SelectedPosition IntPreference selectedItem,
															  GooglePlacesAPIService googlePlacesAPIService) {
		return new EditCityListPresenterImpl(cityList, selectedItem, googlePlacesAPIService);
	}

	@Provides
	public CityWeatherActivityPresenter provideCityWeatherActivityPresenter(@CityList StringArrayPreference cityList,
																			@SelectedPosition IntPreference selectedItem) {
		return new CityWeatherActivityPresenterImpl(cityList, selectedItem);
	}
}
