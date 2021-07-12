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
import ticwear.design.widget.DatePicker;

class DatePickerViewHolder {
    static final String DAY = "day";
    static final String MONTH = "month";
    static final String YEAR = "year";
    private final Context mContext;
    private DatePicker mDatePicker;

    public DatePickerViewHolder(Context context) {
        this.mContext = context;
    }

    public DatePicker getDatePicker() {
        return this.mDatePicker;
    }

    public DatePicker init(ViewGroup viewGroup, int n2, int n3, int n4, DatePicker.OnDateChangedListener onDateChangedListener, DatePicker.ValidationCallback validationCallback) {
        this.mDatePicker = (DatePicker)LayoutInflater.from((Context)this.mContext).inflate(R.layout.dialog_date_picker, viewGroup, false);
        this.mDatePicker.init(n2, n3, n4, onDateChangedListener);
        this.mDatePicker.setValidationCallback(validationCallback);
        return this.mDatePicker;
    }

    public void onRestoreInstanceState(Bundle bundle) {
        int n2 = bundle.getInt(YEAR);
        int n3 = bundle.getInt(MONTH);
        int n4 = bundle.getInt(DAY);
        this.mDatePicker.init(n2, n3, n4, null);
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt(YEAR, this.mDatePicker.getYear());
        bundle.putInt(MONTH, this.mDatePicker.getMonth());
        bundle.putInt(DAY, this.mDatePicker.getDayOfMonth());
    }

    public void updateDate(int n2, int n3, int n4) {
        this.mDatePicker.updateDate(n2, n3, n4);
    }
}

