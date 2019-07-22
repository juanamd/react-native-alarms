package com.ioddly.alarms;

import android.content.Context;
import android.content.Intent;

public class AlarmHelper {
	public static final String ALARM_FIRED_EVENT = "alarm_fired_event";
	public static final String DEFAULT_EVENT = "default_event";

	/** Find and launch the main activity of the application */
	static void launchMainActivity(final Context context) {
		String packageName = context.getApplicationContext().getPackageName();
		Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
		launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(launchIntent);
	}
}
