package com.kypi.khoahm_alarm.services;

import static com.kypi.khoahm_alarm.ConstantsUtils.FILTER_ACTION_ALARM;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kypi.khoahm_alarm.ConstantsUtils;
import com.kypi.khoahm_alarm.MyApplication;
import com.kypi.khoahm_alarm.data.AlarmRepository;
import com.kypi.khoahm_alarm.helper.AlarmHelper;
import com.kypi.khoahm_alarm.helper.NotificationHelper;
import com.kypi.khoahm_alarm.helper.SoundHelper;
import com.kypi.khoahm_alarm.helper.VibrateHelper;

public class AlarmReceiver extends BroadcastReceiver {

    public static final int ALARM_NOTIFICATION_ID = 100;


    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmRepository alarmRepository = MyApplication.alarmRepository;



        String action = intent.getStringExtra(ConstantsUtils.ACTION);
        Log.d("KhoaHM", "onReceive action = " + action);

        switch (action) {
            case ConstantsUtils.ACTION_PLAY:
                // Play sound
                SoundHelper.start(context);
                // Vibrate
                VibrateHelper.makeVibrate(context);
                // Push Notification
                NotificationHelper.createNotification(context);
                break;
            case ConstantsUtils.ACTION_CANCEL:
                VibrateHelper.cancelVibrate(context);
                NotificationHelper.cancelNotification(context);
                SoundHelper.stop(context);
                alarmRepository.setTargetAlarm(0);
                break;
            case ConstantsUtils.ACTION_OK:
                VibrateHelper.cancelVibrate(context);
                SoundHelper.stop(context);

                int repeatType = alarmRepository.getRepeatType();
                long lastPickTime = alarmRepository.getLastTimePicked();
                long targetTime = System.currentTimeMillis() + lastPickTime;

                if (repeatType != 0) {
                    AlarmHelper.startAlarm(context, targetTime);
                    alarmRepository.setTargetAlarm(targetTime);
                    Intent alarmIntent = new Intent(FILTER_ACTION_ALARM);
                    alarmIntent.putExtra(ConstantsUtils.ACTION, ConstantsUtils.ACTION_REPEAT);
                    context.sendBroadcast(alarmIntent);
                } else {
                    Intent alarmIntent = new Intent(FILTER_ACTION_ALARM);
                    alarmIntent.putExtra(ConstantsUtils.ACTION, ConstantsUtils.ACTION_CANCEL);
                    context.sendBroadcast(alarmIntent);
                }

                NotificationHelper.cancelNotification(context);
                break;
        }
    }

}