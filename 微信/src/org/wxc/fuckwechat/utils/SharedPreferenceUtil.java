package org.wxc.fuckwechat.utils;

import android.app.Activity;
import android.content.Context;

public class SharedPreferenceUtil {
	Context context;

	public SharedPreferenceUtil(Context context) {
		this.context = context;
	}

	public void write(String type, String key, boolean flag) {
		context.getSharedPreferences(type,
				context.getApplicationContext().MODE_WORLD_WRITEABLE).edit()
				.putBoolean(key, flag).commit();
	}

	public void write(String type, String key, String value) {
		context.getSharedPreferences(type,
				context.getApplicationContext().MODE_WORLD_WRITEABLE).edit()
				.putString(key, value).commit();
	}

	public void write(String type, String key, int number) {
		context.getSharedPreferences(type,
				context.getApplicationContext().MODE_WORLD_WRITEABLE).edit()
				.putInt(key, number).commit();
	}

	public boolean readBoolean(String type, String key) {
		return context.getSharedPreferences(type,
				context.getApplicationContext().MODE_WORLD_WRITEABLE)
				.getBoolean(key, false);

	}

	public String readString(String type, String key) {
		return context.getSharedPreferences(type,
				context.getApplicationContext().MODE_WORLD_WRITEABLE)
				.getString(key, "");

	}

	public int readInt(String type, String key) {
		return context.getSharedPreferences(type,
				context.getApplicationContext().MODE_WORLD_WRITEABLE).getInt(
				key, 0);
	}
}
