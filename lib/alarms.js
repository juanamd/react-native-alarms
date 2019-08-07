// $FlowFixMe
import { NativeModules } from "react-native";
const AlarmAndroid = NativeModules.AlarmAndroid;

class Alarms {
  setElapsedRealtime(alarmName, triggerMillis, intervalMillis = 0) {
    AlarmAndroid.setElapsedRealtime(alarmName, triggerMillis, intervalMillis);
  }

  setElapsedRealtimeWakeup(alarmName, triggerMillis, intervalMillis = 0) {
    AlarmAndroid.setElapsedRealtimeWakeup(alarmName, triggerMillis, intervalMillis);
  }

  setRTC(alarmName, fireDate, intervalMillis = 0) {
    const millisString = fireDate.getTime().toString();
    AlarmAndroid.setRTC(alarmName, millisString, intervalMillis);
  }

  setRTCWakeup(alarmName, fireDate, intervalMillis = 0) {
    const millisString = fireDate.getTime().toString();
    AlarmAndroid.setRTCWakeup(alarmName, millisString, intervalMillis);
  }

  clearAlarm(alarmName) {
    AlarmAndroid.clearAlarm(alarmName);
  }

  async alarmExists(alarmName) {
    return await AlarmAndroid.alarmExists(alarmName);
  }

  async getPersistedAlarmName() {
    return await AlarmAndroid.getPersistedAlarmName();
  }

  async clearPersistedAlarmName() {
    await AlarmAndroid.clearPersistedAlarmName();
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