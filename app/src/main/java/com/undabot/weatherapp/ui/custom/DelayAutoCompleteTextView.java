package com.undabot.weatherapp.ui.custom;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

/**
 * Used for delayed request to server (750 millis by default).
 */

public class DelayAutoCompleteTextView extends AutoCompleteTextView {

	private static final int MESSAGE_TEXT_CHANGED = 100;
	private static final int DEFAULT_AUTOCOMPLETE_DELAY = 750;

	private int mAutoCompleteDelay = DEFAULT_AUTOCOMPLETE_DELAY;
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			DelayAutoCompleteTextView.super.performFiltering((CharSequence) msg.obj, msg.arg1);
		}
	};
	private ProgressBar mLoadingIndicator;

	public DelayAutoCompleteTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void performFiltering(CharSequence text, int keyCode) {
		if (mLoadingIndicator != null) {
			mLoadingIndicator.setVisibility(View.VISIBLE);
		}
		mHandler.removeMessages(MESSAGE_TEXT_CHANGED);
		mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_TEXT_CHANGED, text), mAutoCompleteDelay);
	}

	@Override
	public void onFilterComplete(int count) {
		if (mLoadingIndicator != null) {
			mLoadingIndicator.setVisibility(View.GONE);
		}
		super.onFilterComplete(count);
	}

	/**
	 * Set custom progressBar
	 *
	 * @param progressBar
	 */
	public void setLoadingIndicator(ProgressBar progressBar) {
		mLoadingIndicator = progressBar;
	}

	/**
	 * Set custom delay in millis, default is 750
	 *
	 * @param autoCompleteDelay
	 */
	public void setAutoCompleteDelay(int autoCompleteDelay) {
		mAutoCompleteDelay = autoCompleteDelay;
	}
}
