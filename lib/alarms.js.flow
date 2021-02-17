// @flow
// $FlowFixMe
import { NativeModules } from "react-native";

const AlarmAndroid = NativeModules.AlarmAndroid;

class Alarms {
	static setElapsedRealtime(alarmName: string, triggerMillis: number, intervalMillis: number = 0): Promise<void> {
		return AlarmAndroid.setElapsedRealtime(alarmName, triggerMillis, intervalMillis);
	}

	static setElapsedRealtimeWakeup(alarmName: string, triggerMillis: number, intervalMillis: number = 0): Promise<void> {
		return AlarmAndroid.setElapsedRealtimeWakeup(alarmName, triggerMillis, intervalMillis);
	}

	static setRTC(alarmName: string, fireDate: Date, intervalMillis: number = 0): Promise<void> {
		const millisString = fireDate.getTime().toString();
		return AlarmAndroid.setRTC(alarmName, millisString, intervalMillis);
	}

	static setRTCWakeup(alarmName: string, fireDate: Date, intervalMillis: number = 0): Promise<void> {
		const millisString = fireDate.getTime().toString();
		return AlarmAndroid.setRTCWakeup(alarmName, millisString, intervalMillis);
	}

	static clearAlarm(alarmName: string): Promise<void> {
		return AlarmAndroid.clearAlarm(alarmName);
	}

	static alarmExists(alarmName: string): Promise<boolean> {
		return AlarmAndroid.alarmExists(alarmName);
	}

	static launchMainActivity(): Promise<void> {
		return AlarmAndroid.launchMainActivity();
	}

	static getPersistedAlarmName(): Promise<string | void> {
		return AlarmAndroid.getPersistedAlarmName();
	}

	static clearPersistedAlarmName(): Promise<void> {
		return AlarmAndroid.clearPersistedAlarmName();
	}

	static clearAlarmNotification(): Promise<void> {
		return AlarmAndroid.clearAlarmNotification();
	}
}

export default Alarms;

export const { RTC, RTC_WAKEUP, ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, INTERVAL_FIFTEEN_MINUTES,
	INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY, INTERVAL_HALF_DAY, ALARM_FIRED_EVENT,
	DEFAULT_EVENT, launchMainActivity } = AlarmAndroid;
