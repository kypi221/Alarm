package com.kypi.khoahm_alarm.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kypi.khoahm_alarm.services.AlarmReceiver;
import com.kypi.khoahm_alarm.ConstantsUtils;

public class AlarmHelper {

    private static final int AlarmRequestCode = 100;

    public static void startAlarm(Context context, long targetTime) {
        // Lấy ra AlarmManager
        AlarmManager alarmManager = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        // Tạo intent
        Intent intent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
        intent.putExtra(ConstantsUtils.ACTION, ConstantsUtils.ACTION_PLAY);

        // Tạo pending intent từ intent
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(
                        context.getApplicationContext(),
                        AlarmRequestCode, intent,
                        PendingIntent.FLAG_CANCEL_CURRENT);

        // Kích hoạt báo thức
        alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP, targetTime, pendingIntent);

    }

    public static void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager)
                context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        Intent updateServiceIntent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingUpdateIntent =
                PendingIntent.getBroadcast(
                        context.getApplicationContext(),
                        AlarmRequestCode, updateServiceIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT);
        // stop alarm sound
        Intent cancelAlarmSound = new Intent(context.getApplicationContext(), AlarmReceiver.class);
        cancelAlarmSound.putExtra(ConstantsUtils.ACTION, ConstantsUtils.ACTION_CANCEL);
        context.sendBroadcast(cancelAlarmSound);

        // Cancel alarms
        try {
            Log.e("KhoaHM", "try to cancel " );
            alarmManager.cancel(pendingUpdateIntent);
        } catch (Exception e) {
            Log.e("KhoaHM", "AlarmManager update was not canceled. " + e.toString());
        }
    }
}
