/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.format.DateFormat
 *  android.text.format.DateUtils
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.TextView
 */
package ticwear.design.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;
import ticwear.design.R;
import ticwear.design.widget.NumberPicker;
import ticwear.design.widget.TimePicker;

class TimePickerSpinnerDelegate
extends TimePicker.AbstractTimePickerDelegate {
    private static final boolean DEFAULT_ENABLED_STATE = true;
    private static final int HOURS_IN_HALF_DAY = 12;
    private final Button mAmPmButton;
    private final NumberPicker mAmPmSpinner;
    private final EditText mAmPmSpinnerInput;
    private final String[] mAmPmStrings;
    private final TextView mDivider;
    private char mHourFormat;
    private final NumberPicker mHourSpinner;
    private final EditText mHourSpinnerInput;
    private boolean mHourWithTwoDigit;
    private boolean mIs24HourView;
    private boolean mIsAm;
    private boolean mIsEnabled = true;
    private final NumberPicker mMinuteSpinner;
    private final EditText mMinuteSpinnerInput;
    private Calendar mTempCalendar;

    /*
     * Enabled aggressive block sorting
     */
    public TimePickerSpinnerDelegate(TimePicker timePicker, Context context, AttributeSet attributeSet, int n2, int n3) {
        super(timePicker, context);
        attributeSet = this.mContext.obtainStyledAttributes(attributeSet, R.styleable.TimePicker, n2, n3);
        n2 = attributeSet.getResourceId(R.styleable.TimePicker_tic_legacyLayout, R.layout.time_picker_ticwear);
        attributeSet.recycle();
        LayoutInflater.from((Context)this.mContext).inflate(n2, (ViewGroup)this.mDelegator, true);
        this.mHourSpinner = (NumberPicker)timePicker.findViewById(R.id.tic_hour);
        this.mHourSpinner.setOnFocusChangeListener(this.mDelegator);
        this.mHourSpinner.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void onValueChange(NumberPicker object, int n2, int n3) {
                TimePickerSpinnerDelegate.this.updateInputState();
                if (!TimePickerSpinnerDelegate.this.is24HourView() && (n2 == 11 && n3 == 12 || n2 == 12 && n3 == 11)) {
                    object = TimePickerSpinnerDelegate.this;
                    boolean bl2 = !TimePickerSpinnerDelegate.this.mIsAm;
                    TimePickerSpinnerDelegate.access$102((TimePickerSpinnerDelegate)object, bl2);
                    TimePickerSpinnerDelegate.this.updateAmPmControl();
                }
                TimePickerSpinnerDelegate.this.onTimeChanged();
            }
        });
        this.mHourSpinnerInput = (EditText)this.mHourSpinner.findViewById(R.id.numberpicker_input);
        this.mHourSpinnerInput.setImeOptions(5);
        this.mDivider = (TextView)this.mDelegator.findViewById(R.id.tic_divider);
        if (this.mDivider != null) {
            this.setDividerText();
        }
        this.mMinuteSpinner = (NumberPicker)this.mDelegator.findViewById(R.id.tic_minute);
        this.mMinuteSpinner.setMinValue(0);
        this.mMinuteSpinner.setMaxValue(59);
        this.mMinuteSpinner.setOnLongPressUpdateInterval(100L);
        this.mMinuteSpinner.setFormatter(NumberPicker.getTwoDigitFormatter());
        this.mMinuteSpinner.setOnFocusChangeListener(this.mDelegator);
        this.mMinuteSpinner.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void onValueChange(NumberPicker object, int n2, int n3) {
                boolean bl2 = true;
                boolean bl3 = true;
                TimePickerSpinnerDelegate.this.updateInputState();
                int n4 = TimePickerSpinnerDelegate.this.mMinuteSpinner.getMinValue();
                int n5 = TimePickerSpinnerDelegate.this.mMinuteSpinner.getMaxValue();
                if (n2 == n5 && n3 == n4) {
                    n2 = TimePickerSpinnerDelegate.this.mHourSpinner.getValue() + 1;
                    if (!TimePickerSpinnerDelegate.this.is24HourView() && n2 == 12) {
                        object = TimePickerSpinnerDelegate.this;
                        if (TimePickerSpinnerDelegate.this.mIsAm) {
                            bl3 = false;
                        }
                        TimePickerSpinnerDelegate.access$102((TimePickerSpinnerDelegate)object, bl3);
                        TimePickerSpinnerDelegate.this.updateAmPmControl();
                    }
                    TimePickerSpinnerDelegate.this.mHourSpinner.setValue(n2);
                } else if (n2 == n4 && n3 == n5) {
                    n2 = TimePickerSpinnerDelegate.this.mHourSpinner.getValue() - 1;
                    if (!TimePickerSpinnerDelegate.this.is24HourView() && n2 == 11) {
                        object = TimePickerSpinnerDelegate.this;
                        bl3 = !TimePickerSpinnerDelegate.this.mIsAm ? bl2 : false;
                        TimePickerSpinnerDelegate.access$102((TimePickerSpinnerDelegate)object, bl3);
                        TimePickerSpinnerDelegate.this.updateAmPmControl();
                    }
                    TimePickerSpinnerDelegate.this.mHourSpinner.setValue(n2);
                }
                TimePickerSpinnerDelegate.this.onTimeChanged();
            }
        });
        this.mMinuteSpinnerInput = (EditText)this.mMinuteSpinner.findViewById(R.id.numberpicker_input);
        this.mMinuteSpinnerInput.setImeOptions(5);
        this.mAmPmStrings = TimePickerSpinnerDelegate.getAmPmStrings(context);
        context = this.mDelegator.findViewById(R.id.tic_amPm);
        if (context instanceof Button) {
            this.mAmPmSpinner = null;
            this.mAmPmSpinnerInput = null;
            this.mAmPmButton = (Button)context;
            this.mAmPmButton.setOnClickListener(new View.OnClickListener(){

                /*
                 * Enabled aggressive block sorting
                 */
                public void onClick(View object) {
                    object.requestFocus();
                    TimePickerSpinnerDelegate timePickerSpinnerDelegate = TimePickerSpinnerDelegate.this;
                    boolean bl2 = !TimePickerSpinnerDelegate.this.mIsAm;
                    TimePickerSpinnerDelegate.access$102(timePickerSpinnerDelegate, bl2);
                    TimePickerSpinnerDelegate.this.updateAmPmControl();
                    TimePickerSpinnerDelegate.this.onTimeChanged();
                }
            });
        } else {
            this.mAmPmButton = null;
            this.mAmPmSpinner = (NumberPicker)context;
            this.mAmPmSpinner.setMinValue(0);
            this.mAmPmSpinner.setMaxValue(1);
            this.mAmPmSpinner.setDisplayedValues(this.mAmPmStrings);
            this.mAmPmSpinner.setOnFocusChangeListener(this.mDelegator);
            this.mAmPmSpinner.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){

                /*
                 * Enabled aggressive block sorting
                 */
                @Override
                public void onValueChange(NumberPicker object, int n2, int n3) {
                    TimePickerSpinnerDelegate.this.updateInputState();
                    object.requestFocus();
                    object = TimePickerSpinnerDelegate.this;
                    boolean bl2 = !TimePickerSpinnerDelegate.this.mIsAm;
                    TimePickerSpinnerDelegate.access$102((TimePickerSpinnerDelegate)object, bl2);
                    TimePickerSpinnerDelegate.this.updateAmPmControl();
                    TimePickerSpinnerDelegate.this.onTimeChanged();
                }
            });
            this.mAmPmSpinnerInput = (EditText)this.mAmPmSpinner.findViewById(R.id.numberpicker_input);
            attributeSet = this.mAmPmSpinnerInput;
            n2 = this.isAmPmAtStart() ? 5 : 6;
            attributeSet.setImeOptions(n2);
        }
        if (this.isAmPmAtStart()) {
            timePicker = (ViewGroup)timePicker.findViewById(R.id.tic_timePickerLayout);
            timePicker.removeView((View)context);
            timePicker.addView((View)context, 0);
            timePicker = (ViewGroup.MarginLayoutParams)context.getLayoutParams();
            n2 = timePicker.getMarginStart();
            n3 = timePicker.getMarginEnd();
            if (n2 != n3) {
                timePicker.setMarginStart(n3);
                timePicker.setMarginEnd(n2);
            }
        }
        this.getHourFormatData();
        this.updateHourControl();
        this.updateMinuteControl();
        this.updateAmPmControl();
        this.setCurrentHour(this.mTempCalendar.get(11));
        this.setCurrentMinute(this.mTempCalendar.get(12));
        if (!this.isEnabled()) {
            this.setEnabled(false);
        }
        if (this.mDelegator.getImportantForAccessibility() == 0) {
            this.mDelegator.setImportantForAccessibility(1);
        }
    }

    static /* synthetic */ boolean access$102(TimePickerSpinnerDelegate timePickerSpinnerDelegate, boolean bl2) {
        timePickerSpinnerDelegate.mIsAm = bl2;
        return bl2;
    }

    public static String[] getAmPmStrings(Context context) {
        return new DateFormatSymbols().getAmPmStrings();
    }

    private View getFocusedLeafChild(View view) {
        if (view instanceof NumberPicker) {
            if (view.hasFocus()) {
                return view;
            }
            return null;
        }
        if (view instanceof ViewGroup) {
            return this.getFocusedLeafChild(((ViewGroup)view).getFocusedChild());
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void getHourFormatData() {
        Locale locale = this.mCurrentLocale;
        String string2 = this.mIs24HourView ? "Hm" : "hm";
        string2 = DateFormat.getBestDateTimePattern((Locale)locale, (String)string2);
        int n2 = string2.length();
        this.mHourWithTwoDigit = false;
        int n3 = 0;
        while (true) {
            block6: {
                block5: {
                    if (n3 >= n2) break block5;
                    char c2 = string2.charAt(n3);
                    if (c2 != 'H' && c2 != 'h' && c2 != 'K' && c2 != 'k') break block6;
                    this.mHourFormat = c2;
                    if (n3 + 1 < n2 && c2 == string2.charAt(n3 + 1)) {
                        this.mHourWithTwoDigit = true;
                    }
                }
                return;
            }
            ++n3;
        }
    }

    private boolean isAmPmAtStart() {
        return DateFormat.getBestDateTimePattern((Locale)this.mCurrentLocale, (String)"hm").startsWith("a");
    }

    private void onTimeChanged() {
        this.mDelegator.sendAccessibilityEvent(4);
        if (this.mOnTimeChangedListener != null) {
            this.mOnTimeChangedListener.onTimeChanged(this.mDelegator, this.getCurrentHour(), this.getCurrentMinute());
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setCurrentHour(Integer n2, boolean bl2) {
        block11: {
            block10: {
                if (n2 == null || n2 == this.getCurrentHour()) break block10;
                Integer n3 = n2;
                if (!this.is24HourView()) {
                    if (n2 >= 12) {
                        this.mIsAm = false;
                        n3 = n2;
                        if (n2 > 12) {
                            n3 = n2 - 12;
                        }
                    } else {
                        this.mIsAm = true;
                        n3 = n2;
                        if (n2 == 0) {
                            n3 = 12;
                        }
                    }
                    this.updateAmPmControl();
                }
                this.mHourSpinner.setValue(n3);
                if (bl2) break block11;
            }
            return;
        }
        this.onTimeChanged();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setDividerText() {
        int n2;
        String string2 = this.mIs24HourView ? "Hm" : "hm";
        string2 = DateFormat.getBestDateTimePattern((Locale)this.mCurrentLocale, (String)string2);
        int n3 = n2 = string2.lastIndexOf(72);
        if (n2 == -1) {
            n3 = string2.lastIndexOf(104);
        }
        string2 = n3 == -1 ? ":" : ((n2 = string2.indexOf(109, n3 + 1)) == -1 ? Character.toString(string2.charAt(n3 + 1)) : string2.substring(n3 + 1, n2));
        this.mDivider.setText((CharSequence)string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateAmPmControl() {
        if (this.is24HourView()) {
            if (this.mAmPmSpinner != null) {
                this.mAmPmSpinner.setVisibility(8);
            } else {
                this.mAmPmButton.setVisibility(8);
            }
        } else {
            int n2 = this.mIsAm ? 0 : 1;
            if (this.mAmPmSpinner != null) {
                this.mAmPmSpinner.setValue(n2);
                this.mAmPmSpinner.setVisibility(0);
            } else {
                this.mAmPmButton.setText((CharSequence)this.mAmPmStrings[n2]);
                this.mAmPmButton.setVisibility(0);
            }
        }
        this.mDelegator.sendAccessibilityEvent(4);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateHourControl() {
        if (this.is24HourView()) {
            if (this.mHourFormat == 'k') {
                this.mHourSpinner.setMinValue(1);
                this.mHourSpinner.setMaxValue(24);
            } else {
                this.mHourSpinner.setMinValue(0);
                this.mHourSpinner.setMaxValue(23);
            }
        } else if (this.mHourFormat == 'K') {
            this.mHourSpinner.setMinValue(0);
            this.mHourSpinner.setMaxValue(11);
        } else {
            this.mHourSpinner.setMinValue(1);
            this.mHourSpinner.setMaxValue(12);
        }
        NumberPicker numberPicker = this.mHourSpinner;
        NumberPicker.Formatter formatter = this.mHourWithTwoDigit ? NumberPicker.getTwoDigitFormatter() : null;
        numberPicker.setFormatter(formatter);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateInputState() {
        InputMethodManager inputMethodManager = (InputMethodManager)this.mContext.getSystemService("input_method");
        if (inputMethodManager == null) return;
        if (inputMethodManager.isActive((View)this.mHourSpinnerInput)) {
            this.mHourSpinnerInput.clearFocus();
            inputMethodManager.hideSoftInputFromWindow(this.mDelegator.getWindowToken(), 0);
            return;
        } else {
            if (inputMethodManager.isActive((View)this.mMinuteSpinnerInput)) {
                this.mMinuteSpinnerInput.clearFocus();
                inputMethodManager.hideSoftInputFromWindow(this.mDelegator.getWindowToken(), 0);
                return;
            }
            if (!inputMethodManager.isActive((View)this.mAmPmSpinnerInput)) return;
            this.mAmPmSpinnerInput.clearFocus();
            inputMethodManager.hideSoftInputFromWindow(this.mDelegator.getWindowToken(), 0);
            return;
        }
    }

    private void updateMinuteControl() {
        if (this.is24HourView() || this.isAmPmAtStart()) {
            this.mMinuteSpinnerInput.setImeOptions(6);
            return;
        }
        this.mMinuteSpinnerInput.setImeOptions(5);
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        this.onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    @Override
    public int getBaseline() {
        return this.mHourSpinner.getBaseline();
    }

    @Override
    public View getCurrentFocusedPicker() {
        if (this.mDelegator == null || this.mDelegator.getChildCount() == 0) {
            return null;
        }
        return this.getFocusedLeafChild((View)this.mDelegator);
    }

    @Override
    public Integer getCurrentHour() {
        int n2 = this.mHourSpinner.getValue();
        if (this.is24HourView()) {
            return n2;
        }
        if (this.mIsAm) {
            return n2 % 12;
        }
        return n2 % 12 + 12;
    }

    @Override
    public Integer getCurrentMinute() {
        return this.mMinuteSpinner.getValue();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public View getNextFocusPicker(View view) {
        View view2;
        block5: {
            block4: {
                if (this.mDelegator == null || this.mDelegator.getChildCount() == 0) break block4;
                view2 = view;
                if (view == null) {
                    view2 = this.getCurrentFocusedPicker();
                }
                if (view2 != null) break block5;
            }
            return null;
        }
        return this.mDelegator.focusSearch(view2, 2);
    }

    @Override
    public boolean is24HourView() {
        return this.mIs24HourView;
    }

    @Override
    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        this.setCurrentLocale(configuration.locale);
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        accessibilityEvent.setClassName((CharSequence)TimePicker.class.getName());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        accessibilityNodeInfo.setClassName((CharSequence)TimePicker.class.getName());
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        int n2 = this.mIs24HourView ? 1 | 0x80 : 1 | 0x40;
        this.mTempCalendar.set(11, this.getCurrentHour());
        this.mTempCalendar.set(12, this.getCurrentMinute());
        String string2 = DateUtils.formatDateTime((Context)this.mContext, (long)this.mTempCalendar.getTimeInMillis(), (int)n2);
        accessibilityEvent.getText().add(string2);
    }

    @Override
    public void onRestoreInstanceState(Parcelable object) {
        object = (SavedState)((Object)object);
        this.setCurrentHour(((SavedState)((Object)object)).getHour());
        this.setCurrentMinute(((SavedState)((Object)object)).getMinute());
    }

    @Override
    public Parcelable onSaveInstanceState(Parcelable parcelable) {
        return new SavedState(parcelable, this.getCurrentHour(), this.getCurrentMinute());
    }

    @Override
    public void setCurrentHour(Integer n2) {
        this.setCurrentHour(n2, true);
    }

    @Override
    public void setCurrentLocale(Locale locale) {
        super.setCurrentLocale(locale);
        this.mTempCalendar = Calendar.getInstance(locale);
    }

    @Override
    public void setCurrentMinute(Integer n2) {
        if (n2 == this.getCurrentMinute()) {
            return;
        }
        this.mMinuteSpinner.setValue(n2);
        this.onTimeChanged();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setEnabled(boolean bl2) {
        this.mMinuteSpinner.setEnabled(bl2);
        if (this.mDivider != null) {
            this.mDivider.setEnabled(bl2);
        }
        this.mHourSpinner.setEnabled(bl2);
        if (this.mAmPmSpinner != null) {
            this.mAmPmSpinner.setEnabled(bl2);
        } else {
            this.mAmPmButton.setEnabled(bl2);
        }
        this.mIsEnabled = bl2;
    }

    @Override
    public void setIs24HourView(Boolean bl2) {
        if (this.mIs24HourView == bl2) {
            return;
        }
        int n2 = this.getCurrentHour();
        this.mIs24HourView = bl2;
        this.getHourFormatData();
        this.updateHourControl();
        this.setCurrentHour(n2, false);
        this.updateMinuteControl();
        this.updateAmPmControl();
    }

    @Override
    public void setOnTimeChangedListener(TimePicker.OnTimeChangedListener onTimeChangedListener) {
        this.mOnTimeChangedListener = onTimeChangedListener;
    }

    private static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n2) {
                return new SavedState[n2];
            }
        };
        private final int mHour;
        private final int mMinute;

        private SavedState(Parcel parcel) {
            super(parcel);
            this.mHour = parcel.readInt();
            this.mMinute = parcel.readInt();
        }

        private SavedState(Parcelable parcelable, int n2, int n3) {
            super(parcelable);
            this.mHour = n2;
            this.mMinute = n3;
        }

        public int getHour() {
            return this.mHour;
        }

        public int getMinute() {
            return this.mMinute;
        }

        public void writeToParcel(Parcel parcel, int n2) {
            super.writeToParcel(parcel, n2);
            parcel.writeInt(this.mHour);
            parcel.writeInt(this.mMinute);
        }
    }
}

