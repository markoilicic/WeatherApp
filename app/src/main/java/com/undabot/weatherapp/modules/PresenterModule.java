package com.undabot.weatherapp.modules;

import com.undabot.weatherapp.ui.CityWeatherFragment;
import com.undabot.weatherapp.ui.EditCityListActivity;

import dagger.Module;

@Module(
		addsTo = MainModule.class,
		injects = {
				CityWeatherFragment.class,
				EditCityListActivity.class
		}
)
public final class PresenterModule {

}
