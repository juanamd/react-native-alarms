package com.ioddly.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class AlarmRun extends BroadcastReceiver {
	
	public static String BOOT_EVENT = "@boot";
	public static String ALARM_FIRED_EVENT = "alarmFired";

	private String alarmName;
	private ReactInstanceManager reactManager;
	private ReactContext reactContext;
	private WakeLock wakelock;

	@Override
	public void onReceive(final Context context, Intent intent) {
		this.alarmName = intent.hasExtra("name") ? intent.getStringExtra("name") : BOOT_EVENT;
		this.reactManager = this.getReactManager(context);
		this.reactContext = reactManager.getCurrentReactContext();

		if(this.isAlarmIntent(intent)) AlarmHelper.launchMainActivity(context);
		if(this.isReactContextReady()) this.emitJSAlarmEvent();
		else {
			this.addReactNativeInitializedListener();
			this.createReactContextIfNecessary();
			if (this.isAlarmIntent(intent)) this.acquireWakeLock(context);
		}
	}

	private ReactInstanceManager getReactManager(final Context context) {
		ReactApplication reactApp = (ReactApplication) context.getApplicationContext();
		return reactApp.getReactNativeHost().getReactInstanceManager();
	}

	private boolean isAlarmIntent(Intent intent) {
		return !intent.getAction().contains("android.intent.action");
	}

	private boolean isReactContextReady() {
		return (this.reactContext != null && this.reactContext.hasActiveCatalystInstance());
	}

	private void emitJSAlarmEvent() {
		Log.i("RNAlarms", "Firing alarm '" + this.alarmName + "'");
		this.reactContext
			.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
			.emit(ALARM_FIRED_EVENT, this.createEventMap());
		this.releaseWakeLock();
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

	public void onReactContextInitialized(ReactContext reactContext) {
		this.reactContext = reactContext;
		if(this.isReactContextReady()) this.emitJSAlarmEvent();
		else {
			//Wait 2 seconds and try again
			try {
				Thread.sleep(2000);
				if(this.isReactContextReady()) this.emitJSAlarmEvent();
			} catch(Exception exception) {
				Thread.currentThread().interrupt();
			}
		}
	}

	private void createReactContextIfNecessary() {
		if(!this.reactManager.hasStartedCreatingInitialContext()) this.reactManager.createReactContextInBackground();
	}

	private void acquireWakeLock(final Context context) {
		Log.i("RNAlarms", "Aquiring wakelock...");
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		this.wakelock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "TAG");
		this.wakelock.acquire();
	}

	private void releaseWakeLock() {
		if(this.wakelock != null && this.wakelock.isHeld()) {
			Log.i("RNAlarms", "Releasing wakelock");
			this.wakelock.release();
			this.wakelock = null;
		}
	}

}
