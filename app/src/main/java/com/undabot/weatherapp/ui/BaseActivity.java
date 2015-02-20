package com.undabot.weatherapp.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.undabot.weatherapp.App;
import com.undabot.weatherapp.modules.PresenterModule;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

public class BaseActivity extends ActionBarActivity {

	private ObjectGraph scopedObjectGraph;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		App app = App.from(this);

		scopedObjectGraph = app.plus(getModules());
		scopedObjectGraph.inject(this);
	}

	protected List<Object> getModules() {
		return Arrays.<Object>asList(new PresenterModule());
	}

	public void inject(Object object) {
		scopedObjectGraph.inject(object);
	}

	@Override protected void onDestroy() {
		scopedObjectGraph = null;
		super.onDestroy();
	}
}
