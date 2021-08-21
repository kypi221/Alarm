//package com.kypi.khoahm_alarm;
//
//import static com.kypi.khoahm_alarm.ConstantsUtils.FILTER_ACTION_ALARM;
//
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.media.MediaPlayer;
//import android.os.Build;
//import android.os.VibrationEffect;
//import android.os.Vibrator;
//import android.provider.Settings;
//import android.util.Log;
//
//import androidx.core.app.NotificationCompat;
//import androidx.core.app.NotificationManagerCompat;
//
//import com.kypi.khoahm_alarm.presentation.CountDownActivity;
//
//import java.io.IOException;
//
//public class Alarm extends BroadcastReceiver {
//
//    public static final int ALARM_NOTIFICATION_ID = 100;
//    private static final String CHANNEL_ID = "default_channel_id";
//    private static MediaPlayer soundInstance;
//
//    static MediaPlayer getSoundInstance(Context context) {
//        if (soundInstance == null) {
//            return soundInstance = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
//        } else return soundInstance;
//    }
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        String action = intent.getStringExtra(ConstantsUtils.ACTION);
//        switch (action) {
//            case ConstantsUtils.ACTION_PLAY:
//                getSoundInstance(context).start();
//                makeVibrate(context);
//                createNotification(context);
//
//                break;
//            case ConstantsUtils.ACTION_CANCEL:
//                System.out.println("#ACTION_CANCEL");
//                stopAlarm(context);
//                break;
//            case ConstantsUtils.ACTION_OK:
//                System.out.println("#ACTION_OK");
//                stopAlarm(context);
//                // clear notification
//                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.cancel(ALARM_NOTIFICATION_ID);
//                //
//                if (SharePreferencesManager.getInstance(context).getIsRepeat()) {
//                    AlarmHelper.startAlarm(context, SharePreferencesManager.getInstance(context).getTimeSet());
//                    SharePreferencesManager.getInstance(context).putTimeStart(System.currentTimeMillis());
//                    Intent alarmIntent = new Intent(FILTER_ACTION_ALARM);
//                    alarmIntent.putExtra(ConstantsUtils.ACTION, ConstantsUtils.ACTION_REPEAT);
//                    context.sendBroadcast(alarmIntent);
//                } else {
//                    Intent alarmIntent = new Intent(FILTER_ACTION_ALARM);
//                    alarmIntent.putExtra(ConstantsUtils.ACTION, ConstantsUtils.ACTION_CANCEL);
//                    context.sendBroadcast(alarmIntent);
//                }
//                break;
//        }
//        Log.d("KhoaHM", "onReceive");
//    }
//
//    private void makeVibrate(Context context) {
//        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            v.vibrate(VibrationEffect.createOneShot(30000, VibrationEffect.DEFAULT_AMPLITUDE));
//        } else {
//            v.vibrate(30000);
//        }
//    }
//
//    private void cancelVibrate(Context context) {
//        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//        if (v.hasVibrator()) {
//            v.cancel();
//        }
//    }
//
//    private void createNotification(Context context) {
//        Intent stopIntent = new Intent(context, Alarm.class);
//        stopIntent.putExtra(ConstantsUtils.ACTION, ConstantsUtils.ACTION_OK);
//        PendingIntent stopAlarmPendingIntent =
//                PendingIntent.getBroadcast(context, 0, stopIntent, 0);
//        //
//        Intent openAlarmIntent = new Intent(context, CountDownActivity.class);
//        PendingIntent openAlarmPendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, openAlarmIntent, 0);
//        //
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
//                .setContentTitle("Alarm sound")
//                .setContentText("Time up!")
//                .setPriority(NotificationCompat.PRIORITY_MAX)
//                .setContentIntent(openAlarmPendingIntent)
//                .addAction(0, "OK",
//                        stopAlarmPendingIntent)
//                .addAction(0, "OPEN",
//                        openAlarmPendingIntent)
//                .setDeleteIntent(stopAlarmPendingIntent);
//        // crete channel before push notify
//        createNotificationChannel(context);
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//        notificationManager.notify(ALARM_NOTIFICATION_ID, mBuilder.build());
//    }
//
//    private void createNotificationChannel(Context context) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "alarm_sound";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }
//
//    private void stopAlarm(Context context) {
//        System.out.println("#ACTION_STOP");
//        System.out.println("#1 stop");
//        getSoundInstance(context).stop();
//        System.out.println("#2 reset");
//        getSoundInstance(context).reset();
//        try {
//            System.out.println("#3 setDataSource");
//            getSoundInstance(context).setDataSource(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
//            System.out.println("#4 prepare");
//            getSoundInstance(context).prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // tắt vibrate
//        cancelVibrate(context);
//    }
//
//}
//    No newline at end of file
