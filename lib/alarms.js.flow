// @flow
// $FlowFixMe
import { NativeModules } from "react-native";

const AlarmAndroid = NativeModules.AlarmAndroid;

class Alarms {
	setElapsedRealtime(alarmName: string, triggerMillis: number, intervalMillis: number = 0) {
		AlarmAndroid.setElapsedRealtime(alarmName, triggerMillis, intervalMillis);
	}

	setElapsedRealtimeWakeup(alarmName: string, triggerMillis: number, intervalMillis: number = 0) {
		AlarmAndroid.setElapsedRealtimeWakeup(alarmName, triggerMillis, intervalMillis);
	}

	setRTC(alarmName: string, fireDate: Date, intervalMillis: number = 0) {
		const millisString = fireDate.getTime().toString();
		AlarmAndroid.setRTC(alarmName, millisString, intervalMillis);
	}

	setRTCWakeup(alarmName: string, fireDate: Date, intervalMillis: number = 0) {
		const millisString = fireDate.getTime().toString();
		AlarmAndroid.setRTCWakeup(alarmName, millisString, intervalMillis);
	}

	clearAlarm(alarmName: string) {
		AlarmAndroid.clearAlarm(alarmName);
	}

	async alarmExists(alarmName: string) {
		return await AlarmAndroid.alarmExists(alarmName);
	}
}

export default new Alarms();

export const { RTC, RTC_WAKEUP, ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, INTERVAL_FIFTEEN_MINUTES,
	INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY, INTERVAL_HALF_DAY, ALARM_FIRED_EVENT,
	DEFAULT_EVENT, launchMainActivity } = AlarmAndroid;
