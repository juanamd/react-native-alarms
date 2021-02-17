// @flow
// $FlowFixMe
import { NativeModules } from "react-native";

const AlarmAndroid = NativeModules.AlarmAndroid;

class Alarms {
	setElapsedRealtime(alarmName: string, triggerMillis: number, intervalMillis: number = 0): Promise<void> {
		return AlarmAndroid.setElapsedRealtime(alarmName, triggerMillis, intervalMillis);
	}

	setElapsedRealtimeWakeup(alarmName: string, triggerMillis: number, intervalMillis: number = 0): Promise<void> {
		return AlarmAndroid.setElapsedRealtimeWakeup(alarmName, triggerMillis, intervalMillis);
	}

	setRTC(alarmName: string, fireDate: Date, intervalMillis: number = 0): Promise<void> {
		const millisString = fireDate.getTime().toString();
		return AlarmAndroid.setRTC(alarmName, millisString, intervalMillis);
	}

	setRTCWakeup(alarmName: string, fireDate: Date, intervalMillis: number = 0): Promise<void> {
		const millisString = fireDate.getTime().toString();
		return AlarmAndroid.setRTCWakeup(alarmName, millisString, intervalMillis);
	}

	clearAlarm(alarmName: string): Promise<void> {
		return AlarmAndroid.clearAlarm(alarmName);
	}

	alarmExists(alarmName: string): Promise<boolean> {
		return AlarmAndroid.alarmExists(alarmName);
	}

	getPersistedAlarmName(): Promise<string | void> {
		return AlarmAndroid.getPersistedAlarmName();
	}

	clearPersistedAlarmName(): Promise<void> {
		return AlarmAndroid.clearPersistedAlarmName();
	}

	clearAlarmNotification(): Promise<void> {
		return AlarmAndroid.clearAlarmNotification();
	}
}

export default new Alarms();

export const { RTC, RTC_WAKEUP, ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, INTERVAL_FIFTEEN_MINUTES,
	INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY, INTERVAL_HALF_DAY, ALARM_FIRED_EVENT,
	DEFAULT_EVENT, launchMainActivity } = AlarmAndroid;
