package com.ioddly.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
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
	
	private static final String TAG = "RNAlarms";
	private static final long WAKELOCK_TIMEOUT = 15000;

	private String alarmName;
	private ReactInstanceManager reactManager;
	private ReactContext reactContext;
	private WakeLock wakelock;

	@Override
	public void onReceive(final Context context, final Intent intent) {
		if (this.isAlarmIntent(intent)) AlarmHelper.launchMainActivity(context);
		try {
			this.alarmName = intent.hasExtra("name") ? intent.getStringExtra("name") : null;
			this.reactManager = this.getReactManager(context);
			this.reactContext = this.reactManager.getCurrentReactContext();
			if (this.isReactContextReady()) this.emitJSModuleEvent();
			else {
				this.addReactNativeInitializedListener();
				this.createReactContextIfNecessary();
				if (this.isAlarmIntent(intent)) this.acquireWakeLock(context);
			}
		} catch (ClassCastException e) {
			Log.e(TAG, "Unable to cast: " + context.getApplicationContext().getClass().getName() + " to ReactApplication", e);
		}
	}

	private ReactInstanceManager getReactManager(final Context context) {
		ReactApplication reactApp = (ReactApplication) context.getApplicationContext();
		return reactApp.getReactNativeHost().getReactInstanceManager();
	}

	private boolean isAlarmIntent(final Intent intent) {
		String action = intent.getAction();
		if (action != null && intent.getData() != null && intent.hasExtra("name")) {
			return action.equals(intent.getStringExtra("name"));
		}
		return false;
	}

	private boolean isReactContextReady() {
		return (this.reactContext != null && this.reactContext.hasActiveCatalystInstance());
	}

	private void emitJSModuleEvent() {
		final String eventName = (this.alarmName != null) ? AlarmHelper.ALARM_FIRED_EVENT : AlarmHelper.DEFAULT_EVENT;
		Log.d(TAG, "Emiting event '" + eventName + "' for alarm '" + this.alarmName + "'");
		this.reactContext
			.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
			.emit(eventName, this.createEventMap());
		this.releaseWakeLock(5000);
	}

	private WritableMap createEventMap() {
		WritableMap map = Arguments.createMap();
		if (this.alarmName != null) map.putString("alarmName", this.alarmName);
		return map;
	}

	private void addReactNativeInitializedListener() {
		Log.d(TAG, "Application is closed; attempting to launch and fire alarm '" + this.alarmName + "'");
		final AlarmRun self = this;
		this.reactManager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
			public void onReactContextInitialized(ReactContext reactContext) {
				self.onReactContextInitialized(reactContext);
			}
		});
	}

	public void onReactContextInitialized(final ReactContext reactContext) {
		this.reactContext = reactContext;
		if (this.isReactContextReady()) this.emitJSModuleEvent();
		else {
			//Wait 3 seconds and try again
			try {
				Thread.sleep(3000);
				if (this.isReactContextReady()) this.emitJSModuleEvent();
			} catch (Exception exception) {
				Thread.currentThread().interrupt();
			}
		}
	}

	private void createReactContextIfNecessary() {
		if (!this.reactManager.hasStartedCreatingInitialContext()) this.reactManager.createReactContextInBackground();
	}

	private void acquireWakeLock(final Context context) {
		Log.d(TAG, "Acquiring wakelock...");
		try {
			PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
			this.wakelock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
			this.wakelock.acquire(WAKELOCK_TIMEOUT);
		} catch (Exception e) {
			Log.e(TAG, "acquireWakeLock(): Error acquiring wakeLock", e);
		}
	}

	private void releaseWakeLock(final int delayMillis) {
		if (this.wakelock != null) {
			Log.d(TAG, "Releasing wakelock in " + delayMillis + " milliseconds...");
			new Handler().postDelayed(
				new Runnable() {
					public void run() {
						try {
							if (wakelock.isHeld()) wakelock.release();
							wakelock = null;
							Log.d(TAG, "releaseWakeLock(): Wakelock released");
						} catch (Exception e) {
							Log.e(TAG, "releaseWakeLock(): Wakelock release error", e);
						}
					}
				}, 
				delayMillis
			);
		}
	}

}
