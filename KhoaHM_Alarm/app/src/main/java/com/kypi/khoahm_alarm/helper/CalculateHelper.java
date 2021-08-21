package com.kypi.khoahm_alarm.helper;

public class CalculateHelper {
    public static int hoursToMilliseconds(int hours) {
        return hours * 3600 * 1000;
    }

    public static int minutesToMilliseconds(int minutes) {
        return minutes * 60 * 1000;
    }

    public static int secondsToMilliseconds(int seconds) {
        return seconds * 1000;
    }

    public static int timeToMilliseconds(int hours, int minutes, int seconds) {
        return hoursToMilliseconds(hours) + minutesToMilliseconds(minutes) + secondsToMilliseconds(seconds);
    }

    public static int getCurrentHoursFromSeconds(int seconds) {
        if (seconds >= (60 * 60)) {
            return (seconds / (60 * 60)) % 60;
        } else return 0;
    }

    public static int getCurrentMinutesFromSeconds(int seconds) {
        if (seconds >= (60)) {
            return (seconds / 60) % 60;
        } else return 0;
    }

    public static int getCurrentSecondsFromSeconds(int seconds){
        return seconds % 60;
    }
}
