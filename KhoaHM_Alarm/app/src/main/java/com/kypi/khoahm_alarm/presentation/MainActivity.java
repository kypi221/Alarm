package com.kypi.khoahm_alarm.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.kypi.khoahm_alarm.MyApplication;
import com.kypi.khoahm_alarm.data.AlarmRepository;
import com.kypi.khoahm_alarm.helper.AlarmHelper;
import com.kypi.khoahm_alarm.databinding.ActivityMainBinding;
import com.kypi.khoahm_alarm.helper.CalculateHelper;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    private int mHours, mMinutes, mSeconds;

    private AlarmRepository alarmRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Setup Repo
        alarmRepository = MyApplication.alarmRepository;

        // Setup View
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        setSupportActionBar(mBinding.toolbar);
        setupView();
        setupListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkCountDown();

    }

    private void setupListener() {
        mBinding.btActive.setOnClickListener(view -> onButtonActiveClick());

        // nếu không set time thì button start sẽ không được active
        mBinding.pickerSeconds.setOnValueChangedListener((numberPicker, i, i1) -> mBinding.btActive.setEnabled(isActiveButton()));

        mBinding.pickerMinutes.setOnValueChangedListener((numberPicker, i, i1) -> mBinding.btActive.setEnabled(isActiveButton()));

        mBinding.pickerHours.setOnValueChangedListener((numberPicker, i, i1) -> mBinding.btActive.setEnabled(isActiveButton()));
    }

    private boolean isActiveButton() {
        return mBinding.pickerSeconds.getValue() != 0
                || mBinding.pickerMinutes.getValue() != 0
                || mBinding.pickerHours.getValue() != 0;
    }

    private void setupView() {
        int repeatType = alarmRepository.getRepeatType();
        mBinding.btActive.setEnabled(false);
        mBinding.cbRepeat.setChecked(repeatType == 1);

        // I can set default value by last time picked here
        // But I dont have enough time. sorry !
        //
        mBinding.pickerHours.setMinValue(0);
        mBinding.pickerHours.setMaxValue(99);
        //
        mBinding.pickerMinutes.setMinValue(0);
        mBinding.pickerMinutes.setMaxValue(59);
        //
        mBinding.pickerSeconds.setMinValue(0);
        mBinding.pickerSeconds.setMaxValue(59);
    }

    private void onButtonActiveClick() {
        mHours = mBinding.pickerHours.getValue();
        mMinutes = mBinding.pickerMinutes.getValue();
        mSeconds = mBinding.pickerSeconds.getValue();
        Log.d("KhoaHM", "Hours = " + mHours);
        Log.d("KhoaHM", "Minutes = " + mMinutes);
        Log.d("KhoaHM", "Seconds = " + mSeconds);

        long milliseconds = CalculateHelper.timeToMilliseconds(mHours, mMinutes, mSeconds);
        long targetTime = System.currentTimeMillis() + milliseconds;

        // Start Alarm
        AlarmHelper.startAlarm(this, targetTime);

        // put data to repo
        alarmRepository.setTargetAlarm(targetTime);
        int repeatType = 0;
        if(mBinding.cbRepeat.isChecked()){
            repeatType = 1;
        }
        alarmRepository.setRepeatType(repeatType);
        alarmRepository.setLastTimePicked(milliseconds);

        // Start Activity
        startActivity(new Intent(this, CountDownActivity.class));
        finish();
    }

    // nếu time coutdown chưa set lại = 0 thì vẫn mở màn hình countdown
    private void checkCountDown() {
        long targetTime = alarmRepository.getTargetAlarm();
        long currentTime = System.currentTimeMillis();

        if(currentTime < targetTime){
            startActivity(new Intent(this, CountDownActivity.class));
            finish();
        }

    }
}