declare module "react-native-alarms" {
	export const ALARM_FIRED_EVENT: string;
	export const DEFAULT_EVENT: string;
	export default class {
		static setElapsedRealtime(alarmName: string, triggerMillis: number, intervalMillis?: number): Promise<void>;
		static setElapsedRealtimeWakeup(alarmName: string, triggerMillis: number, intervalMillis?: number): Promise<void>;
		static setRTC(alarmName: string, fireDate: Date, intervalMillis?: number): Promise<void>;
		static setRTCWakeup(alarmName: string, fireDate: Date, intervalMillis?: number): Promise<void>;
		static clearAlarm(alarmName: string): Promise<void>;
		static clearAlarms(alarmNames: string[]): Promise<void>;
		static alarmExists(alarmName: string): Promise<boolean>;
		static launchMainActivity(): Promise<void>;
		static getPersistedAlarmName(): Promise<string | undefined>;
		static clearPersistedAlarmName(): Promise<void>;
		static clearAlarmNotification(): Promise<void>;
	}
}
