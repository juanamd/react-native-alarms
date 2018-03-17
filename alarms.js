import { NativeModules } from "react-native";

const AlarmAndroid = NativeModules.AlarmAndroid;

const assertString = (value) => {
	if(!(typeof value === "string" || value instanceof String)) throwTypeError("String");
};

const assertNumber = (value) => {
	if(typeof value !== "number") throwTypeError("Number");
};

const assertDate = (value) => {
	if(!(value instanceof Date)) throwTypeError("Date");
};

const throwTypeError = (typeString) => {
	throw new Error(`RNAlarms: Wrong argument type; Expected ${typeString}`);
};

class Alarms {
	setElapsedRealtime(alarmName, triggerMillis, intervalMillis = 0) {
		assertString(alarmName);
		assertNumber(triggerMillis);
		assertNumber(intervalMillis);

		AlarmAndroid.setElapsedRealtime(alarmName, triggerMillis, intervalMillis);
	}

	setElapsedRealtimeWakeup(alarmName, triggerMillis, intervalMillis = 0) {
		assertString(alarmName);
		assertNumber(triggerMillis);
		assertNumber(intervalMillis);

		AlarmAndroid.setElapsedRealtimeWakeup(alarmName, triggerMillis, intervalMillis);
	}

	setRTC(alarmName, fireDate, intervalMillis = 0) {
		assertString(alarmName);
		assertDate(fireDate);
		assertNumber(intervalMillis);

		const millisString = fireDate.getTime().toString();

		AlarmAndroid.setRTC(alarmName, millisString, intervalMillis);
	}

	setRTCWakeup(alarmName, fireDate, intervalMillis = 0) {
		assertString(alarmName);
		assertDate(fireDate);
		assertNumber(intervalMillis);

		const millisString = fireDate.getTime().toString();

		AlarmAndroid.setRTCWakeup(alarmName, millisString, intervalMillis);
	}

	clearAlarm(alarmName) {
		assertString(alarmName);
		AlarmAndroid.clearAlarm(alarmName);
	}

	alarmExists(alarmName) {
		assertString(alarmName);
		AlarmAndroid.alarmExists(alarmName);
	}
}

export default new Alarms();

export const { RTC, RTC_WAKEUP, ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, INTERVAL_FIFTEEN_MINUTES,
	INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY, INTERVAL_HALF_DAY, launchMainActivity } = AlarmAndroid;
