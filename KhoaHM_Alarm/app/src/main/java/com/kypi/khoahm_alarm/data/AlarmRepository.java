package com.kypi.khoahm_alarm.data;

import com.kypi.khoahm_alarm.ConstantsUtils;


// Todo : add DI later
public class AlarmRepository {

    final AppStorageProvider storageProvider;

    public AlarmRepository(AppStorageProvider storageProvider) {
        this.storageProvider = storageProvider;
    }


    /* mốc thời gian user đã chọn để báo thức*/
    public void setTargetAlarm(long targetAlarm){
        storageProvider.writeValue(ConstantsUtils.KEY_TIME_TARGET, targetAlarm);
    }

    public long getTargetAlarm() {
        return storageProvider.readLong(ConstantsUtils.KEY_TIME_TARGET);
    }

    /* Mốc thời gian user đã chọn lần cuối, mục đích cho việc hiển thị lại */
    public void setLastTimePicked(long targetAlarm){
        storageProvider.writeValue(ConstantsUtils.KEY_LAST_TIME_PICKED, targetAlarm);
    }

    public long getLastTimePicked(){
        return storageProvider.readLong(ConstantsUtils.KEY_LAST_TIME_PICKED);
    }

    /* Lấy ra loại repeat, sau này có thể có thêm */
    public void setRepeatType(int repeatType){
        storageProvider.writeValue(ConstantsUtils.KEY_REPEAT_TYPE, repeatType);
    }

    public int getRepeatType(){
        return storageProvider.readInt(ConstantsUtils.KEY_REPEAT_TYPE);
    }
}
