package com.ioddly.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@ReactModule(name = "AlarmAndroid")
public class AlarmModule extends ReactContextBaseJavaModule {

	private static final String TAG = "RNAlarms";
	
	public AlarmModule(ReactApplicationContext reactContext) {
		super(reactContext);
		Log.d(TAG, "AlarmModule initialized");
	}

	@ReactMethod
	public void setElapsedRealtime(String alarmName, int triggerMillis, int intervalMillis, Promise promise) {
		try {
			long alarmMillis = SystemClock.elapsedRealtime() + triggerMillis;
			this.setAlarm(AlarmManager.ELAPSED_REALTIME, alarmName, alarmMillis, intervalMillis);
			promise.resolve(null);
			Log.d(TAG, "setElapsedRealtime. Name: " + alarmName + ", interval: " + intervalMillis + ", millis: " + alarmMillis);
		} catch (Exception e) {
			promise.reject(e);
		}
	}

	@ReactMethod
	public void setElapsedRealtimeWakeup(String alarmName, int triggerMillis, int intervalMillis, Promise promise) {
		try {
			long alarmMillis = SystemClock.elapsedRealtime() + triggerMillis;
			this.setAlarm(AlarmManager.ELAPSED_REALTIME_WAKEUP, alarmName, alarmMillis, intervalMillis);
			promise.resolve(null);
			Log.d(TAG, "setElapsedRealtimeWakeup. Name: " + alarmName + ", interval: " + intervalMillis + ", millis: " + alarmMillis);
		} catch (Exception e) {
			promise.reject(e);
		}
	}

	@ReactMethod
	public void setRTC(String alarmName, String alarmMillisString, int intervalMillis, Promise promise) {
		try {
			long alarmMillis = Long.parseLong(alarmMillisString, 10);
			this.setAlarm(AlarmManager.RTC, alarmName, alarmMillis, intervalMillis);
			promise.resolve(null);
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(alarmMillis);
			Log.d(TAG, "setRTC. Name: " + alarmName + ", interval: " + intervalMillis + " at: " + date);
		} catch (Exception e) {
			promise.reject(e);
		}
	}

	@ReactMethod
	public void setRTCWakeup(String alarmName, String alarmMillisString, int intervalMillis, Promise promise) {
		try {
			long alarmMillis = Long.parseLong(alarmMillisString, 10);
			this.setAlarm(AlarmManager.RTC_WAKEUP, alarmName, alarmMillis, intervalMillis);
			promise.resolve(null);
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(alarmMillis);
			Log.d(TAG, "setRTCWakeup. Name: " + alarmName + ", interval: " + intervalMillis + " at: " + date);
		} catch (Exception e) {
			promise.reject(e);
		}
	}

	private void setAlarm(int type, String alarmName, long alarmMillis, int intervalMillis) {
		PendingIntent pending = this.getAlarmPendingIntent(alarmName);
		if (intervalMillis != 0) this.setRepeatingAlarm(type, alarmMillis, intervalMillis, pending);
		else this.setNonRepeatingAlarm(type, alarmMillis, pending);
	}

	private PendingIntent getAlarmPendingIntent(String alarmName) {
		if (isExistingAlarm(alarmName)) {
			Log.d(TAG, "PendingIntent already exists for alarm '" + alarmName + "'; updating!");
			this.clearAlarm(alarmName);
			return createPending(alarmName, PendingIntent.FLAG_UPDATE_CURRENT);
		} else {
			return createPending(alarmName, 0);
		}
	}

	private void setRepeatingAlarm(int type, long alarmMillis, int intervalMillis, PendingIntent pending) {
		AlarmManager alarmManager = this.getAlarmManager();
		alarmManager.setInexactRepeating(type, alarmMillis, intervalMillis, pending);
	}

	private void setNonRepeatingAlarm(int type, long alarmMillis, PendingIntent pending) {
		AlarmManager alarmManager = this.getAlarmManager();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(alarmMillis, pending), pending);
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			alarmManager.setExact(type, alarmMillis, pending);
		} else {
			alarmManager.set(type, alarmMillis, pending);
		}
	}

	private AlarmManager getAlarmManager() {
		Context context = this.getReactApplicationContext();
		return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	}

	@ReactMethod
	public void alarmExists(String alarmName, Promise promise) {
		try {
			boolean exists = isExistingAlarm(alarmName);
			promise.resolve(exists);
		} catch (Exception e) {
			promise.reject(e);
		}
	}

	private boolean isExistingAlarm(String alarmName) {
		return this.createPending(alarmName, PendingIntent.FLAG_NO_CREATE) != null;
	}

	@ReactMethod
	public void clearAlarm(String alarmName, Promise promise) {
		try {
			Log.d(TAG, "Clearing alarm '" + alarmName + "'");
			PendingIntent pending = this.createPending(alarmName, PendingIntent.FLAG_NO_CREATE);
			if (pending != null) {
				pending.cancel();
				AlarmManager alarmManager = this.getAlarmManager();
				alarmManager.cancel(pending);
			} else {
				Log.d(TAG, "No PendingIntent found for alarm '" + alarmName + "'");
			}
			promise.resolve(null);
		} catch (Exception e) {
			promise.reject(e);
		}
	}

	private PendingIntent createPending(String alarmName, int flags) {
		Context context = this.getReactApplicationContext();
		Intent intent = new Intent(context, AlarmRun.class);
		intent.putExtra("name", alarmName);
		// This is so alarms may be cancelled
		intent.setAction(alarmName);
		intent.setData(Uri.parse("http://" + alarmName));
		return PendingIntent.getBroadcast(context, 0, intent, flags);
	}

	@ReactMethod
	public void launchMainActivity(Promise promise) {
		try {
			AlarmHelper.launchMainActivity(this.getReactApplicationContext());
			promise.resolve(null);
		} catch (Exception e) {
			promise.reject(e);
		}
	}

	@ReactMethod
	public void getPersistedAlarmName(Promise promise) {
		try {
			SharedPreferences prefs = getReactApplicationContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);
			String alarmName = prefs.getString(AlarmHelper.ALARM_NAME, null);
			promise.resolve(alarmName);
		} catch (Exception e) {
			promise.reject(e);
			Log.e(TAG, "Error while getting persisted alarm name", e);
		}
	}

	@ReactMethod
	public void clearPersistedAlarmName(Promise promise) {
		try {
			SharedPreferences prefs = getReactApplicationContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);
			Editor editor = prefs.edit();
			editor.remove(AlarmHelper.ALARM_NAME);
			editor.apply();
			promise.resolve(null);
		} catch (Exception e) {
			promise.reject(e);
			Log.e(TAG, "Error while getting persisted alarm name", e);
		}
	}

	@ReactMethod
	public void clearAlarmNotification(Promise promise) {
		try {
			NotificationHelper.clearNotification(getReactApplicationContext());
			promise.resolve(null);
		} catch (Exception e) {
			promise.reject(e);
			Log.e(TAG, "Error clearing notification", e);
		}
	}

	@Override
	public String getName() {
		return "AlarmAndroid";
	}

	@Override
	public Map<String, Object> getConstants() {
		final Map<String, Object> constants = new HashMap<>();

		constants.put("RTC", AlarmManager.RTC);
		constants.put("RTC_WAKEUP", AlarmManager.RTC_WAKEUP);
		constants.put("ELAPSED_REALTIME", AlarmManager.ELAPSED_REALTIME);
		constants.put("ELAPSED_REALTIME_WAKEUP", AlarmManager.ELAPSED_REALTIME_WAKEUP);

		constants.put("INTERVAL_FIFTEEN_MINUTES", AlarmManager.INTERVAL_FIFTEEN_MINUTES);
		constants.put("INTERVAL_HALF_HOUR", AlarmManager.INTERVAL_HALF_HOUR);
		constants.put("INTERVAL_HOUR", AlarmManager.INTERVAL_HOUR);
		constants.put("INTERVAL_DAY", AlarmManager.INTERVAL_DAY);
		constants.put("INTERVAL_HALF_DAY", AlarmManager.INTERVAL_HALF_DAY);

		constants.put("ALARM_FIRED_EVENT", AlarmHelper.ALARM_FIRED_EVENT);
		constants.put("DEFAULT_EVENT", AlarmHelper.DEFAULT_EVENT);

		return constants;
	}

}
