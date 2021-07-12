/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.ViewGroup
 */
package ticwear.design.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import ticwear.design.R;
import ticwear.design.widget.TimePicker;

class TimePickerViewHolder {
    private static final String HOUR = "hour";
    private static final String IS_24_HOUR = "is24hour";
    private static final String MINUTE = "minute";
    private final Context mContext;
    private int mInitialHourOfDay;
    private int mInitialMinute;
    private boolean mIs24HourView;
    private TimePicker mTimePicker;

    public TimePickerViewHolder(Context context) {
        this.mContext = context;
    }

    public TimePicker getTimePicker() {
        return this.mTimePicker;
    }

    public TimePicker init(ViewGroup viewGroup, int n2, int n3, boolean bl2, TimePicker.OnTimeChangedListener onTimeChangedListener, TimePicker.ValidationCallback validationCallback) {
        this.mInitialHourOfDay = n2;
        this.mInitialMinute = n3;
        this.mIs24HourView = bl2;
        this.mTimePicker = (TimePicker)LayoutInflater.from((Context)this.mContext).inflate(R.layout.dialog_time_picker, viewGroup, false);
        this.mTimePicker.setIs24HourView(this.mIs24HourView);
        this.mTimePicker.setCurrentHour(this.mInitialHourOfDay);
        this.mTimePicker.setCurrentMinute(this.mInitialMinute);
        this.mTimePicker.setOnTimeChangedListener(onTimeChangedListener);
        this.mTimePicker.setValidationCallback(validationCallback);
        return this.mTimePicker;
    }

    public void onRestoreInstanceState(Bundle bundle) {
        int n2 = bundle.getInt(HOUR);
        int n3 = bundle.getInt(MINUTE);
        this.mTimePicker.setIs24HourView(bundle.getBoolean(IS_24_HOUR));
        this.mTimePicker.setCurrentHour(n2);
        this.mTimePicker.setCurrentMinute(n3);
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt(HOUR, this.mTimePicker.getCurrentHour().intValue());
        bundle.putInt(MINUTE, this.mTimePicker.getCurrentMinute().intValue());
        bundle.putBoolean(IS_24_HOUR, this.mTimePicker.is24HourView());
    }

    public void updateTime(int n2, int n3) {
        this.mTimePicker.setCurrentHour(n2);
        this.mTimePicker.setCurrentMinute(n3);
    }
}

