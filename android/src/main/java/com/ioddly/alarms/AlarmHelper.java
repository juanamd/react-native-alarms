package com.ioddly.alarms;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class AlarmHelper {
	public static final String ALARM_FIRED_EVENT = "alarm_fired_event";
	public static final String DEFAULT_EVENT = "default_event";
	public static final String ALARM_NAME = "alarm_name";

	static void launchMainActivity(final Context context) {
		String packageName = context.getApplicationContext().getPackageName();
		Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
		launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		if (Build.VERSION.SDK_INT < 29) context.startActivity(launchIntent);
		else {
			boolean canStartActivity = android.provider.Settings.canDrawOverlays(context);
			Log.d("RNAlarms", "Can start activity: " + canStartActivity);
			if (canStartActivity) context.startActivity(launchIntent);
			else NotificationHelper.notifyWithFullScreenIntent(context, launchIntent, "Alarm");
		}
	}
}
