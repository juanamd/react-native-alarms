package com.ioddly.alarms;

import android.app.NotificationManager;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NotificationHelper {
	public static final String NOTIFICATION_CHANNEL_ID = "react_native_alarms_channel_id";
	public static final String NOTIFICATION_CHANNEL_NAME = "Alarms";

	static void notifyWithFullScreenIntent(final Context context, final Intent fullScreenIntent, final String title) {
		PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, 0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
			.setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
			.setContentTitle(title)
			.setContentText(getCurrentTimeText())
			.setAutoCancel(true)
			.setPriority(NotificationCompat.PRIORITY_MAX)
			.setCategory(NotificationCompat.CATEGORY_ALARM)
			.setFullScreenIntent(fullScreenPendingIntent, true);

		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		createNotificationChannelIfNeeded(manager);
		manager.notify(NOTIFICATION_CHANNEL_ID, 0, notificationBuilder.build());
	}

	static private String getCurrentTimeText() {
		Date currentLocalTime = Calendar.getInstance().getTime();
		SimpleDateFormat date = new SimpleDateFormat("E, hh:mm a");
		return date.format(currentLocalTime);
	}

	static private void createNotificationChannelIfNeeded(NotificationManager manager) {
		if (manager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) == null) {
			NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
			channel.setSound(null, null);
			channel.enableVibration(false);
			manager.createNotificationChannel(channel);
		}
	}

	static void clearNotification(final Context context) {
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.cancel(NOTIFICATION_CHANNEL_ID, 0);
	}
}
