package com.undabot.weatherapp;

import com.undabot.weatherapp.modules.MainModule;

public final class Modules {

	private Modules() {
		//No instances
	}

	public static Object[] list(App app) {
		return new Object[]{
				new MainModule(app),
		};
	}
}
