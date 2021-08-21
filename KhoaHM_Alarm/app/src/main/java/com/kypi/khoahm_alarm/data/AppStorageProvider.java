package com.kypi.khoahm_alarm.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.kypi.khoahm_alarm.MyApplication;

public class AppStorageProvider {
    private static final String SHARE_PREFERENCES_NAME = "com.kypi.khoahm_alarm.StorageManager";
    private static AppStorageProvider instance;
    private final SharedPreferences sharedPreferences;

    public static AppStorageProvider getInstance() {
        if (instance == null) {
            return instance = new AppStorageProvider();
        } else return instance;
    }

    private AppStorageProvider() {
        sharedPreferences = MyApplication.getApplication()
                .getSharedPreferences(SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }


    public void writeValue(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void writeValue(String key, long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public void writeValue(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }


    public String readString(String key){
        return sharedPreferences.getString(key, "");
    }

    public int readInt(String key){
        return sharedPreferences.getInt(key, 0);
    }

    public long readLong(String key){
        return sharedPreferences.getLong(key, 0);
    }

}
