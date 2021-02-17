// $FlowFixMe
import { NativeModules } from "react-native";
const AlarmAndroid = NativeModules.AlarmAndroid;

class Alarms {
  static setElapsedRealtime(alarmName, triggerMillis, intervalMillis = 0) {
    return AlarmAndroid.setElapsedRealtime(alarmName, triggerMillis, intervalMillis);
  }

  static setElapsedRealtimeWakeup(alarmName, triggerMillis, intervalMillis = 0) {
    return AlarmAndroid.setElapsedRealtimeWakeup(alarmName, triggerMillis, intervalMillis);
  }

  static setRTC(alarmName, fireDate, intervalMillis = 0) {
    const millisString = fireDate.getTime().toString();
    return AlarmAndroid.setRTC(alarmName, millisString, intervalMillis);
  }

  static setRTCWakeup(alarmName, fireDate, intervalMillis = 0) {
    const millisString = fireDate.getTime().toString();
    return AlarmAndroid.setRTCWakeup(alarmName, millisString, intervalMillis);
  }

  static clearAlarm(alarmName) {
    return AlarmAndroid.clearAlarm(alarmName);
  }

  static alarmExists(alarmName) {
    return AlarmAndroid.alarmExists(alarmName);
  }

  static launchMainActivity() {
    return AlarmAndroid.launchMainActivity();
  }

  static getPersistedAlarmName() {
    return AlarmAndroid.getPersistedAlarmName();
  }

  static clearPersistedAlarmName() {
    return AlarmAndroid.clearPersistedAlarmName();
  }

  static clearAlarmNotification() {
    return AlarmAndroid.clearAlarmNotification();
  }

}

export default Alarms;
export const {
  RTC,
  RTC_WAKEUP,
  ELAPSED_REALTIME,
  ELAPSED_REALTIME_WAKEUP,
  INTERVAL_FIFTEEN_MINUTES,
  INTERVAL_HALF_HOUR,
  INTERVAL_HOUR,
  INTERVAL_DAY,
  INTERVAL_HALF_DAY,
  ALARM_FIRED_EVENT,
  DEFAULT_EVENT,
  launchMainActivity
} = AlarmAndroid;