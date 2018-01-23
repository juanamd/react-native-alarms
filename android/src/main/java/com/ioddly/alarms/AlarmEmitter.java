package com.ioddly.alarms;

import javax.annotation.Nullable;

import com.facebook.react.bridge.JavaScriptModule;

public interface AlarmEmitter extends JavaScriptModule {
	void emit(String eventName, @Nullable Object data);
}
