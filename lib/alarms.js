// $FlowFixMe
import { NativeModules } from "react-native";
const AlarmAndroid = NativeModules.AlarmAndroid;

class Alarms {
  setElapsedRealtime(alarmName, triggerMillis, intervalMillis = 0) {
    return AlarmAndroid.setElapsedRealtime(alarmName, triggerMillis, intervalMillis);
  }

  setElapsedRealtimeWakeup(alarmName, triggerMillis, intervalMillis = 0) {
    return AlarmAndroid.setElapsedRealtimeWakeup(alarmName, triggerMillis, intervalMillis);
  }

  setRTC(alarmName, fireDate, intervalMillis = 0) {
    const millisString = fireDate.getTime().toString();
    return AlarmAndroid.setRTC(alarmName, millisString, intervalMillis);
  }

  setRTCWakeup(alarmName, fireDate, intervalMillis = 0) {
    const millisString = fireDate.getTime().toString();
    return AlarmAndroid.setRTCWakeup(alarmName, millisString, intervalMillis);
  }

  clearAlarm(alarmName) {
    return AlarmAndroid.clearAlarm(alarmName);
  }

  alarmExists(alarmName) {
    return AlarmAndroid.alarmExists(alarmName);
  }

  getPersistedAlarmName() {
    return AlarmAndroid.getPersistedAlarmName();
  }

  clearPersistedAlarmName() {
    return AlarmAndroid.clearPersistedAlarmName();
  }

  clearAlarmNotification() {
    return AlarmAndroid.clearAlarmNotification();
  }

}

export default new Alarms();
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