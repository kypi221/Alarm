package com.kypi.khoahm_alarm;

import android.app.Application;
import android.content.Context;

import com.kypi.khoahm_alarm.data.AlarmRepository;
import com.kypi.khoahm_alarm.data.AppStorageProvider;

public class MyApplication extends Application {
    private static MyApplication myApplication;

    public static AppStorageProvider appStorageProvider;
    public static AlarmRepository alarmRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        appStorageProvider = AppStorageProvider.getInstance();
        alarmRepository = new AlarmRepository(appStorageProvider);

    }

    public static MyApplication getApplication(){
        return myApplication;
    }
}
