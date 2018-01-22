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
    /**
     * Fires alarm after ReactContext has been obtained
     * @param reactContext
     * @param alarmName
     */
    public static void emitJSAlarmEvent(ReactContext reactContext, final String alarmName) {
        if(reactContext.hasActiveCatalystInstance()) {
            Log.i("RNAlarms", "Firing alarm '" + alarmName + "'");
            WritableMap map = Arguments.createMap();
            map.putString("alarmName", alarmName);
            reactContext.getJSModule(AlarmEmitter.class).emit("alarmFired", map);
        } else {
            Log.i("RNAlarms", "no active catalyst instance; not firing alarm '" + alarmName + "'");
        }
    }

	@Override
	public void onReceive(final Context context, Intent intent) {
		if(isAlarmIntent(intent)) AlarmHelper.launchMainActivity(context);
		prepareJSListeners(context, intent);
    }

	private boolean isAlarmIntent(Intent intent) {
		return !intent.getAction().contains("android.intent.action");
	}

	private void prepareJSListeners(final Context context, Intent intent) {
		final String alarmName = intent.hasExtra("name") ? intent.getStringExtra("name") : "@boot";
		ReactApplication reactApp = ((ReactApplication) context.getApplicationContext());
		ReactInstanceManager reactManager = reactApp.getReactNativeHost().getReactInstanceManager();
		ReactContext reactContext = reactManager.getCurrentReactContext();
		if(reactContext != null) {
			Log.i("RNAlarms", "Attempting to fire alarm '" + alarmName + "'");
			emitJSAlarmEvent(reactContext, alarmName);
		} else {
			addReactNativeInitializedListener(reactManager, alarmName);
			createReactContextIfNecessary(reactManager);
		}
	}

	private void addReactNativeInitializedListener(ReactInstanceManager reactManager, final String alarmName) {
		Log.i("RNAlarms", "Application is closed; attempting to launch and fire alarm '" + alarmName + "'");
		reactManager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
			public void onReactContextInitialized(ReactContext reactContext) {
				Log.i("RNAlarms", "Attempting to fire alarm '" + alarmName + "'");
				emitJSAlarmEvent(reactContext, alarmName);
			}
		});
	}

	private void createReactContextIfNecessary(ReactInstanceManager reactManager) {
		if(!reactManager.hasStartedCreatingInitialContext()) reactManager.createReactContextInBackground();
	}

}
