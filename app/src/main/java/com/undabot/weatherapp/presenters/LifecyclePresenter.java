package com.undabot.weatherapp.presenters;

public interface LifecyclePresenter<T> extends BasePresenter<T> {

	public void onCreate();

	public void onStart();

	public void onResume();

	public void onStop();

	public void onPause();

	public void onDestroy();
}
