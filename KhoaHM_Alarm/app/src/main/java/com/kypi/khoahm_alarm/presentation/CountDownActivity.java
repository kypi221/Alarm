package com.kypi.khoahm_alarm.presentation;

import static com.kypi.khoahm_alarm.ConstantsUtils.FILTER_ACTION_ALARM;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.kypi.khoahm_alarm.ConstantsUtils;
import com.kypi.khoahm_alarm.MyApplication;
import com.kypi.khoahm_alarm.data.AlarmRepository;
import com.kypi.khoahm_alarm.databinding.ActivityCountDownBinding;
import com.kypi.khoahm_alarm.helper.AlarmHelper;
import com.kypi.khoahm_alarm.helper.CalculateHelper;
import com.kypi.khoahm_alarm.helper.NotificationHelper;

public class CountDownActivity extends AppCompatActivity {
    private ActivityCountDownBinding mBinding;

    private boolean mIsRepeat = false;

    private AlarmRepository alarmRepository;

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("broadcastReceiver");
            String action = intent.getStringExtra(ConstantsUtils.ACTION);
            System.out.println("#1 action: " + action);
            if (action.equals(ConstantsUtils.ACTION_REPEAT)) {
                System.out.println("#2 ACTION_REPEAT");
                calculateCountDownTime();
            } else if (action.equals(ConstantsUtils.ACTION_CANCEL)) {
                System.out.println("#3 ACTION_CANCEL");
                alarmRepository = MyApplication.alarmRepository;
                alarmRepository.setTargetAlarm(0);
                startActivity(new Intent(CountDownActivity.this, MainActivity.class));
                finish();
            }
        }
    };
    private CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alarmRepository = MyApplication.alarmRepository;

        // Setup view
        mBinding = ActivityCountDownBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        setupViews();

        setListeners();
        registerReceiver(broadcastReceiver, new IntentFilter(FILTER_ACTION_ALARM));

    }


    @Override
    protected void onResume() {
        super.onResume();
        calculateCountDownTime();
    }

    private void setupViews() {
        int repeatType = alarmRepository.getRepeatType();
        mIsRepeat = repeatType != 0;
        mBinding.cbIsRepeat.setChecked(mIsRepeat);
        mBinding.cbIsRepeat.setEnabled(false);
    }

    private void setListeners() {
        mBinding.btCancel.setOnClickListener(view -> {
            // clear notification
            NotificationHelper.cancelNotification(this);
            // stop sound and vibrate
            AlarmHelper.cancelAlarm(CountDownActivity.this);
            //
            alarmRepository.setTargetAlarm(0);
            startActivity(new Intent(CountDownActivity.this, MainActivity.class));
            finish();
        });

        mBinding.btOK.setOnClickListener(view -> {
            long lastPickedTime = alarmRepository.getLastTimePicked();
            long targetTime = System.currentTimeMillis() + lastPickedTime;

            // clear notification
            NotificationHelper.cancelNotification(this);

            // stop sound and vibrate
            AlarmHelper.cancelAlarm(this);
            // start alarm
            AlarmHelper.startAlarm(this, targetTime);
            // repeat count down
            alarmRepository.setTargetAlarm(targetTime);

            calculateCountDownTime();
            // hide button
            mBinding.btOK.setVisibility(View.GONE);
        });
    }

    private void calculateCountDownTime() {
        long targetTime = alarmRepository.getTargetAlarm();
        long timeLeft = targetTime - System.currentTimeMillis();
        if(timeLeft > 0){
            mBinding.btOK.setVisibility(View.GONE);
            startCountDownTime(timeLeft);
        }
        else if (mIsRepeat)
            mBinding.btOK.setVisibility(View.VISIBLE);

    }

    private void startCountDownTime(long milliseconds) {
        if(mCountDownTimer != null){
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }

        mCountDownTimer = new CountDownTimer(milliseconds, 1000) {
            public void onTick(long millisUntilFinished) {
                Log.d("KhoaHM", "onTick = " + millisUntilFinished);

                int totalSecond = Math.round((float) millisUntilFinished / 1000);
                Log.d("KhoaHM", "total seconds = " + totalSecond);

                // Second
                int currentSeconds = CalculateHelper.getCurrentSecondsFromSeconds(totalSecond);
                Log.d("KhoaHM", "currentSeconds = " + currentSeconds);
                mBinding.tvSeconds.setText(addZeroBeforeTime(currentSeconds));

                // Minute
                int currentMinutes = CalculateHelper.getCurrentMinutesFromSeconds(totalSecond);
                Log.d("KhoaHM", "currentMinutes = " + currentMinutes);
                mBinding.tvMinutes.setText(addZeroBeforeTime(currentMinutes));

                // Hour
                int currentHours = CalculateHelper.getCurrentHoursFromSeconds(totalSecond);
                Log.d("KhoaHM", "currentHours = " + currentHours);
                mBinding.tvHours.setText(addZeroBeforeTime(currentHours));
            }

            public void onFinish() {
                Log.d("KhoaHM", "onFinish");
                if (mIsRepeat) mBinding.btOK.setVisibility(View.VISIBLE);
                mBinding.tvSeconds.setText(addZeroBeforeTime(0));
            }
        }.start();
    }

    private String addZeroBeforeTime(int time) {
        String stringOfTime = String.valueOf(time);
        if (time == 0) {
            return "00";
        } else if (stringOfTime.length() == 1) {
            return "0" + stringOfTime;
        } else return stringOfTime;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}