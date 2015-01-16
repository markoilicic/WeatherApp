package com.undabot.weatherapp.ui;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.undabot.weatherapp.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CityWeatherActivity extends ActionBarActivity {

    @InjectView(R.id.app_bar)
    Toolbar mAppBar;
    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private NavigationDrawerFragment mDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_weather_activity);

        ButterKnife.inject(this);

        setSupportActionBar(mAppBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set up drawer
        mDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        mDrawerFragment.setUp(R.id.navigation_drawer_fragment, mDrawerLayout, mAppBar);

    }

}
