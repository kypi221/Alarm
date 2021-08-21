package com.kypi.khoahm_alarm.helper;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.Settings;

import java.io.IOException;


public class SoundHelper {

    private static MediaPlayer mediaPlayer;

    private static MediaPlayer createIfNeed(Context context) {
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
        return mediaPlayer;
    }




    public static void start(Context context) {
        MediaPlayer mediaPlayer = createIfNeed(context);
        mediaPlayer.start();
    }


    public static void stop(Context context) {
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
