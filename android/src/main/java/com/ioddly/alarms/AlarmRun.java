package com.ioddly.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;

public class AlarmRun extends BroadcastReceiver {
	
	public static String BOOT_EVENT = "@boot";
	public static String ALARM_FIRED_EVENT = "alarmFired";

	private String alarmName;
	private ReactInstanceManager reactManager;
	private ReactContext reactContext;

	@Override
	public void onReceive(final Context context, Intent intent) {
		this.alarmName = intent.hasExtra("name") ? intent.getStringExtra("name") : BOOT_EVENT;
		this.reactManager = getReactManager(context);
		this.reactContext = reactManager.getCurrentReactContext();
		if(isAlarmIntent(intent)) AlarmHelper.launchMainActivity(context);
		prepareJSListeners();
	}

	public void onReactContextInitialized(ReactContext reactContext) {
		this.reactContext = reactContext;
		emitJSAlarmEvent();
	}

	private ReactInstanceManager getReactManager(final Context context) {
		ReactApplication reactApp = ((ReactApplication) context.getApplicationContext());
		return reactApp.getReactNativeHost().getReactInstanceManager();
	}

	private boolean isAlarmIntent(Intent intent) {
		return !intent.getAction().contains("android.intent.action");
	}

	private void prepareJSListeners() {
		if(this.reactContext != null) emitJSAlarmEvent();
		else {
			addReactNativeInitializedListener();
			createReactContextIfNecessary();
		}
	}

	private void emitJSAlarmEvent() {
		if(this.reactContext.hasActiveCatalystInstance()) {
			Log.i("RNAlarms", "Firing alarm '" + this.alarmName + "'");
			this.reactContext.getJSModule(AlarmEmitter.class).emit(ALARM_FIRED_EVENT, createEventMap());
		} else Log.i("RNAlarms", "no active catalyst instance; not firing alarm '" + this.alarmName + "'");
	}

	private WritableMap createEventMap() {
		WritableMap map = Arguments.createMap();
		map.putString("alarmName", this.alarmName);
		return map;
	}

	private void addReactNativeInitializedListener() {
		Log.i("RNAlarms", "Application is closed; attempting to launch and fire alarm '" + this.alarmName + "'");
		final AlarmRun self = this;
		this.reactManager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
			public void onReactContextInitialized(ReactContext reactContext) {
				self.onReactContextInitialized(reactContext);
			}
		});
	}

	private void createReactContextIfNecessary() {
		if(!this.reactManager.hasStartedCreatingInitialContext()) this.reactManager.createReactContextInBackground();
	}

}
