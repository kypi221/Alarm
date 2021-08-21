package com.kypi.khoahm_alarm.helper;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class VibrateHelper {
    public static void makeVibrate(Context context){
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createWaveform(new long []{0, 1000, 500, 1000, 500}, 0));
        } else {
            v.vibrate(30000);
        }
    }


    public static void cancelVibrate(Context context) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (v.hasVibrator()) {
            v.cancel();
        }
    }
}
