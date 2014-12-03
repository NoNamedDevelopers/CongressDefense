package com.nonameddevelopers.congressdefense.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Logger;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.nonameddevelopers.congressdefense.CongressDefense;

public class AndroidLauncher extends AndroidApplication {

	GoogleAnalytics analytics;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		config.useWakelock = true;
		initialize(new CongressDefense(), config);
		analytics = GoogleAnalytics.getInstance(this);
		EasyTracker.getInstance(this).activityStart(this); // Add this method.

		// May return null if EasyTracker has not yet been initialized with a
		// property

		analytics.setLogger(new Logger() {

			@Override
			public void warn(String arg0) {
				System.out.println("ANALYTICS " + arg0);
			}

			@Override
			public void verbose(String arg0) {
				System.out.println("ANALYTICS " + arg0);

			}

			@Override
			public void setLogLevel(LogLevel arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void info(String arg0) {
				System.out.println("ANALYTICS " + arg0);

			}

			@Override
			public LogLevel getLogLevel() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void error(Exception arg0) {
				System.out.println("ANALYTICS " + arg0);

			}

			@Override
			public void error(String arg0) {
				// TODO Auto-generated method stub

			}
		});
		// ID.
		Tracker easyTracker = EasyTracker.getInstance(this);

		// This screen name value will remain set on the tracker and sent with
		// hits until it is set to a new value or to null.
		easyTracker.set(Fields.SCREEN_NAME, "Home Screen");

		easyTracker.send(MapBuilder.createAppView().build());

	}

}
