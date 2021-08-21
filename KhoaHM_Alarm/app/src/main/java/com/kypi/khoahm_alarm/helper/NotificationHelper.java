package com.kypi.khoahm_alarm.helper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.kypi.khoahm_alarm.services.AlarmReceiver;
import com.kypi.khoahm_alarm.ConstantsUtils;
import com.kypi.khoahm_alarm.R;
import com.kypi.khoahm_alarm.presentation.CountDownActivity;

public class NotificationHelper {
    private static final String CHANNEL_ID = "kypi.khoahm_alarm.CHANNEL_ID";
    private static final int NOTIFICATION_ID = 2000;


    public static void createNotification(Context context) {
        /* STOP INTENT */
        // Tạo stop intent
        Intent stopIntent = new Intent(context, AlarmReceiver.class);
        // Set action
        stopIntent.putExtra(ConstantsUtils.ACTION, ConstantsUtils.ACTION_OK);
        // Tạo pending intent
        PendingIntent stopAlarmPendingIntent =
                PendingIntent.getBroadcast(context, 0, stopIntent, 0);

        /* OPEN INTENT*/
        // Tạo open intent
        Intent openAlarmIntent = new Intent(context, CountDownActivity.class);
        // Tạo pending intent
        PendingIntent openAlarmPendingIntent =
                PendingIntent.getActivity(context, 0, openAlarmIntent, 0);

        /* Setup Notification */
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Hurry, Contact me !")
                .setContentText("Time's up !!!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setNotificationSilent()
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Hi, I'm KhoaHM, \nIf you have any question, just contact me.\nIf you love this demo, send me an offer :D.\n(Lâu quá roài không code Android, chả nhớ gì :D)"))
                .setVibrate(null)
//                .setVibrate(new long[]{1000, 1000, 2000, 2000, 3000, 3000, 4000, 4000})
//                .setSound(Settings.System.DEFAULT_ALARM_ALERT_URI)
                /*
                 Dùng Vibrate và sound tự custom
                 Use custom vibrate and sound
                */
                .setContentIntent(openAlarmPendingIntent)
                .addAction(0, "OK",
                        stopAlarmPendingIntent)
                .addAction(0, "OPEN",
                        openAlarmPendingIntent)
                .setDeleteIntent(stopAlarmPendingIntent);


        // crete channel before push notify
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//        Notification notification = mBuilder.build();
        createNotificationChannel(notificationManager);

        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    public static void cancelNotification(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
    }

    private static void createNotificationChannel(NotificationManagerCompat notificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "alarm_sound";
             /*
             Dùng Vibrate và sound tự custom
             Use custom vibrate and sound
             */

//            AudioAttributes audioAttributes = new AudioAttributes.Builder()
//                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                    .setUsage(AudioAttributes.USAGE_ALARM)
//                    .build();


            CharSequence channelName = "Alarm Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setSound(null, null);
            notificationChannel.enableVibration(false);
//            notificationChannel.setSound(Settings.System.DEFAULT_ALARM_ALERT_URI, audioAttributes);
//            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

}
