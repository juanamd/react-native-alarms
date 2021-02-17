declare module "react-native-alarms" {
	export const ALARM_FIRED_EVENT: string;
	export const DEFAULT_EVENT: string;
	export default class {
		setElapsedRealtime(alarmName: string, triggerMillis: number, intervalMillis?: number): Promise<void>;
		setElapsedRealtimeWakeup(alarmName: string, triggerMillis: number, intervalMillis?: number): Promise<void>;
		setRTC(alarmName: string, fireDate: Date, intervalMillis?: number): Promise<void>;
		setRTCWakeup(alarmName: string, fireDate: Date, intervalMillis?: number): Promise<void>;
		clearAlarm(alarmName: string): Promise<void>;
		alarmExists(alarmName: string): Promise<boolean>;
		getPersistedAlarmName(): Promise<string | undefined>;
		clearPersistedAlarmName(): Promise<void>;
		clearAlarmNotification(): Promise<void>;
	}
}
