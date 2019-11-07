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

	async alarmExists(alarmName: string): Promise<boolean> {
		return await AlarmAndroid.alarmExists(alarmName);
	}

	async getPersistedAlarmName(): Promise<string | void> {
		return await AlarmAndroid.getPersistedAlarmName();
	}

	async clearPersistedAlarmName(): Promise<void> {
		await AlarmAndroid.clearPersistedAlarmName();
	}

	async clearAlarmNotification(): Promise<void> {
		await AlarmAndroid.clearAlarmNotification();
	}
}

export default new Alarms();

export const { RTC, RTC_WAKEUP, ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, INTERVAL_FIFTEEN_MINUTES,
	INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY, INTERVAL_HALF_DAY, ALARM_FIRED_EVENT,
	DEFAULT_EVENT, launchMainActivity } = AlarmAndroid;
