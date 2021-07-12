/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.TypedArray
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  android.text.format.DateFormat
 *  android.text.format.DateUtils
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.GestureDetector$SimpleOnGestureListener
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$OnFocusChangeListener
 *  android.view.ViewGroup
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.CalendarView
 *  android.widget.CalendarView$OnDateChangeListener
 *  android.widget.EditText
 *  android.widget.FrameLayout
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package ticwear.design.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mobvoi.ticwear.view.SidePanelEventDispatcher;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import ticwear.design.DesignConfig;
import ticwear.design.R;
import ticwear.design.widget.MultiPickerContainer;
import ticwear.design.widget.NumberPicker;

public class DatePicker
extends FrameLayout
implements MultiPickerContainer,
SidePanelEventDispatcher,
View.OnFocusChangeListener {
    private static final String LOG_TAG = "DatePicker";
    private static final int MODE_CALENDAR = 2;
    private static final int MODE_SPINNER = 1;
    private final DatePickerDelegate mDelegate;
    private final GestureDetector mGestureDetector;
    private MultiPickerContainer.MultiPickerClient mMultiPickerClient;
    private final OnGestureListener mOnGestureListener = new OnGestureListener();

    public DatePicker(Context context) {
        this(context, null);
    }

    public DatePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843612);
    }

    public DatePicker(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, 0);
    }

    public DatePicker(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.DatePicker, n2, n3);
        int n4 = typedArray.getInt(R.styleable.DatePicker_android_datePickerMode, 1);
        int n5 = typedArray.getInt(R.styleable.DatePicker_android_firstDayOfWeek, 0);
        typedArray.recycle();
        switch (n4) {
            default: {
                this.mDelegate = this.createSpinnerUIDelegate(context, attributeSet, n2, n3);
                if (n5 != 0) {
                    this.setFirstDayOfWeek(n5);
                }
                this.mGestureDetector = new GestureDetector(context, (GestureDetector.OnGestureListener)this.mOnGestureListener);
                return;
            }
            case 2: 
        }
        throw new RuntimeException("Calendar mode DataPicker not supported on wear.");
    }

    private DatePickerDelegate createSpinnerUIDelegate(Context context, AttributeSet attributeSet, int n2, int n3) {
        return new DatePickerSpinnerDelegate(this, context, attributeSet, n2, n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean nextFocus() {
        boolean bl2 = true;
        View view = this.mDelegate.getCurrentFocusedPicker();
        View view2 = this.mDelegate.getNextFocusPicker(view);
        if (DesignConfig.DEBUG_PICKERS) {
            Log.d((String)LOG_TAG, (String)("change focus from " + view + ", to " + view2));
        }
        if (view2 == null) {
            return false;
        }
        boolean bl3 = false;
        if (view instanceof NumberPicker) {
            bl3 = ((TextView)view.findViewById(R.id.numberpicker_input)).getImeOptions() == 6;
        }
        boolean bl4 = false;
        if (this.mMultiPickerClient != null) {
            bl4 = this.mMultiPickerClient.onPickerPreFocus((NumberPicker)view2, bl3);
        }
        if (!bl4) {
            view2.requestFocus();
        }
        if (!(view2 instanceof NumberPicker)) return false;
        if (((TextView)view2.findViewById(R.id.numberpicker_input)).getImeOptions() != 6) return false;
        return bl2;
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return this.mDelegate.dispatchPopulateAccessibilityEvent(accessibilityEvent);
    }

    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        this.dispatchThawSelfOnly(sparseArray);
    }

    @Override
    public boolean dispatchTouchSidePanelEvent(MotionEvent motionEvent, @NonNull SidePanelEventDispatcher.SuperCallback superCallback) {
        this.mGestureDetector.onTouchEvent(motionEvent);
        return superCallback.superDispatchTouchSidePanelEvent(motionEvent);
    }

    public CalendarView getCalendarView() {
        return this.mDelegate.getCalendarView();
    }

    public boolean getCalendarViewShown() {
        return this.mDelegate.getCalendarViewShown();
    }

    public int getDayOfMonth() {
        return this.mDelegate.getDayOfMonth();
    }

    public int getFirstDayOfWeek() {
        return this.mDelegate.getFirstDayOfWeek();
    }

    public long getMaxDate() {
        return this.mDelegate.getMaxDate().getTimeInMillis();
    }

    public long getMinDate() {
        return this.mDelegate.getMinDate().getTimeInMillis();
    }

    public int getMonth() {
        return this.mDelegate.getMonth();
    }

    public boolean getSpinnersShown() {
        return this.mDelegate.getSpinnersShown();
    }

    public int getYear() {
        return this.mDelegate.getYear();
    }

    public void init(int n2, int n3, int n4, OnDateChangedListener onDateChangedListener) {
        this.mDelegate.init(n2, n3, n4, onDateChangedListener);
    }

    public boolean isEnabled() {
        return this.mDelegate.isEnabled();
    }

    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mDelegate.onConfigurationChanged(configuration);
    }

    public void onFocusChange(View view, boolean bl2) {
        if (this.mMultiPickerClient != null && bl2) {
            this.mMultiPickerClient.onPickerPostFocus((NumberPicker)view);
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        this.mDelegate.onInitializeAccessibilityEvent(accessibilityEvent);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        this.mDelegate.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
    }

    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        this.mDelegate.onPopulateAccessibilityEvent(accessibilityEvent);
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        parcelable = (View.BaseSavedState)parcelable;
        super.onRestoreInstanceState(parcelable.getSuperState());
        this.mDelegate.onRestoreInstanceState(parcelable);
    }

    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        return this.mDelegate.onSaveInstanceState(parcelable);
    }

    public void setCalendarViewShown(boolean bl2) {
        this.mDelegate.setCalendarViewShown(bl2);
    }

    public void setEnabled(boolean bl2) {
        if (this.mDelegate.isEnabled() == bl2) {
            return;
        }
        super.setEnabled(bl2);
        this.mDelegate.setEnabled(bl2);
    }

    public void setFirstDayOfWeek(int n2) {
        if (n2 < 1 || n2 > 7) {
            throw new IllegalArgumentException("firstDayOfWeek must be between 1 and 7");
        }
        this.mDelegate.setFirstDayOfWeek(n2);
    }

    public void setMaxDate(long l2) {
        this.mDelegate.setMaxDate(l2);
    }

    public void setMinDate(long l2) {
        this.mDelegate.setMinDate(l2);
    }

    @Override
    public void setMultiPickerClient(MultiPickerContainer.MultiPickerClient multiPickerClient) {
        this.mMultiPickerClient = multiPickerClient;
    }

    public void setSpinnersShown(boolean bl2) {
        this.mDelegate.setSpinnersShown(bl2);
    }

    public void setValidationCallback(@Nullable ValidationCallback validationCallback) {
        this.mDelegate.setValidationCallback(validationCallback);
    }

    public void updateDate(int n2, int n3, int n4) {
        this.mDelegate.updateDate(n2, n3, n4);
    }

    static abstract class AbstractDatePickerDelegate
    implements DatePickerDelegate {
        protected Context mContext;
        protected Locale mCurrentLocale;
        protected DatePicker mDelegator;
        protected OnDateChangedListener mOnDateChangedListener;
        protected ValidationCallback mValidationCallback;

        public AbstractDatePickerDelegate(DatePicker datePicker, Context context) {
            this.mDelegator = datePicker;
            this.mContext = context;
            this.setCurrentLocale(Locale.getDefault());
        }

        @Override
        public View getCurrentFocusedPicker() {
            return null;
        }

        @Override
        public View getNextFocusPicker(View view) {
            return null;
        }

        protected void onValidationChanged(boolean bl2) {
            if (this.mValidationCallback != null) {
                this.mValidationCallback.onValidationChanged(bl2);
            }
        }

        protected void setCurrentLocale(Locale locale) {
            if (locale.equals(this.mCurrentLocale)) {
                return;
            }
            this.mCurrentLocale = locale;
        }

        @Override
        public void setValidationCallback(ValidationCallback validationCallback) {
            this.mValidationCallback = validationCallback;
        }
    }

    static interface DatePickerDelegate {
        public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent var1);

        public CalendarView getCalendarView();

        public boolean getCalendarViewShown();

        public View getCurrentFocusedPicker();

        public int getDayOfMonth();

        public int getFirstDayOfWeek();

        public Calendar getMaxDate();

        public Calendar getMinDate();

        public int getMonth();

        public View getNextFocusPicker(View var1);

        public boolean getSpinnersShown();

        public int getYear();

        public void init(int var1, int var2, int var3, OnDateChangedListener var4);

        public boolean isEnabled();

        public void onConfigurationChanged(Configuration var1);

        public void onInitializeAccessibilityEvent(AccessibilityEvent var1);

        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo var1);

        public void onPopulateAccessibilityEvent(AccessibilityEvent var1);

        public void onRestoreInstanceState(Parcelable var1);

        public Parcelable onSaveInstanceState(Parcelable var1);

        public void setCalendarViewShown(boolean var1);

        public void setEnabled(boolean var1);

        public void setFirstDayOfWeek(int var1);

        public void setMaxDate(long var1);

        public void setMinDate(long var1);

        public void setSpinnersShown(boolean var1);

        public void setValidationCallback(ValidationCallback var1);

        public void updateDate(int var1, int var2, int var3);
    }

    private static class DatePickerSpinnerDelegate
    extends AbstractDatePickerDelegate {
        private static final String DATE_FORMAT = "MM/dd/yyyy";
        private static final boolean DEFAULT_CALENDAR_VIEW_SHOWN = true;
        private static final boolean DEFAULT_ENABLED_STATE = true;
        private static final int DEFAULT_END_YEAR = 2100;
        private static final boolean DEFAULT_SPINNERS_SHOWN = true;
        private static final int DEFAULT_START_YEAR = 1900;
        private boolean mAllowNumericMonths;
        private final CalendarView mCalendarView;
        private Calendar mCurrentDate;
        private final java.text.DateFormat mDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        private final NumberPicker mDaySpinner;
        private final EditText mDaySpinnerInput;
        private boolean mIsEnabled = true;
        private Calendar mMaxDate;
        private Calendar mMinDate;
        private final NumberPicker mMonthSpinner;
        private final EditText mMonthSpinnerInput;
        private int mNumberOfMonths;
        private String[] mShortMonths;
        private final LinearLayout mSpinners;
        private Calendar mTempDate;
        private final NumberPicker mYearSpinner;
        private final EditText mYearSpinnerInput;

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        DatePickerSpinnerDelegate(DatePicker datePicker, Context object, AttributeSet object2, int n2, int n3) {
            super(datePicker, (Context)object);
            int n4;
            int n5;
            void var3_4;
            this.mDelegator = datePicker;
            this.mContext = object;
            this.setCurrentLocale(Locale.getDefault());
            TypedArray typedArray = object.obtainStyledAttributes((AttributeSet)var3_4, R.styleable.DatePicker, n5, n4);
            boolean bl2 = typedArray.getBoolean(R.styleable.DatePicker_android_spinnersShown, true);
            boolean bl3 = typedArray.getBoolean(R.styleable.DatePicker_android_calendarViewShown, true);
            n5 = typedArray.getInt(R.styleable.DatePicker_android_startYear, 1900);
            n4 = typedArray.getInt(R.styleable.DatePicker_android_endYear, 2100);
            String string2 = typedArray.getString(R.styleable.DatePicker_android_minDate);
            String string3 = typedArray.getString(R.styleable.DatePicker_android_maxDate);
            int n6 = typedArray.getResourceId(R.styleable.DatePicker_tic_legacyLayout, R.layout.date_picker_ticwear);
            this.mAllowNumericMonths = typedArray.getBoolean(R.styleable.DatePicker_tic_allowNumericMonths, true);
            typedArray.recycle();
            ((LayoutInflater)object.getSystemService("layout_inflater")).inflate(n6, (ViewGroup)this.mDelegator, true);
            NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener(){

                /*
                 * Enabled aggressive block sorting
                 */
                @Override
                public void onValueChange(NumberPicker numberPicker, int n2, int n3) {
                    block9: {
                        block11: {
                            block10: {
                                DatePickerSpinnerDelegate.this.updateInputState();
                                DatePickerSpinnerDelegate.this.mTempDate.setTimeInMillis(DatePickerSpinnerDelegate.this.mCurrentDate.getTimeInMillis());
                                if (numberPicker != DatePickerSpinnerDelegate.this.mDaySpinner) break block10;
                                int n4 = DatePickerSpinnerDelegate.this.mTempDate.getActualMaximum(5);
                                if (n2 == n4 && n3 == 1) {
                                    DatePickerSpinnerDelegate.this.mTempDate.add(5, 1);
                                    break block9;
                                } else if (n2 == 1 && n3 == n4) {
                                    DatePickerSpinnerDelegate.this.mTempDate.add(5, -1);
                                    break block9;
                                } else {
                                    DatePickerSpinnerDelegate.this.mTempDate.add(5, n3 - n2);
                                }
                                break block9;
                            }
                            if (numberPicker != DatePickerSpinnerDelegate.this.mMonthSpinner) break block11;
                            if (n2 == 11 && n3 == 0) {
                                DatePickerSpinnerDelegate.this.mTempDate.add(2, 1);
                                break block9;
                            } else if (n2 == 0 && n3 == 11) {
                                DatePickerSpinnerDelegate.this.mTempDate.add(2, -1);
                                break block9;
                            } else {
                                DatePickerSpinnerDelegate.this.mTempDate.add(2, n3 - n2);
                            }
                            break block9;
                        }
                        if (numberPicker != DatePickerSpinnerDelegate.this.mYearSpinner) {
                            throw new IllegalArgumentException();
                        }
                        DatePickerSpinnerDelegate.this.mTempDate.set(1, n3);
                    }
                    DatePickerSpinnerDelegate.this.setDate(DatePickerSpinnerDelegate.this.mTempDate.get(1), DatePickerSpinnerDelegate.this.mTempDate.get(2), DatePickerSpinnerDelegate.this.mTempDate.get(5));
                    DatePickerSpinnerDelegate.this.updateSpinners();
                    DatePickerSpinnerDelegate.this.updateCalendarView();
                    DatePickerSpinnerDelegate.this.notifyDateChanged();
                }
            };
            this.mSpinners = (LinearLayout)this.mDelegator.findViewById(R.id.tic_pickers);
            this.mCalendarView = (CalendarView)this.mDelegator.findViewById(R.id.tic_calendar_view);
            this.mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){

                public void onSelectedDayChange(CalendarView calendarView, int n2, int n3, int n4) {
                    DatePickerSpinnerDelegate.this.setDate(n2, n3, n4);
                    DatePickerSpinnerDelegate.this.updateSpinners();
                    DatePickerSpinnerDelegate.this.notifyDateChanged();
                }
            });
            this.mDaySpinner = (NumberPicker)this.mDelegator.findViewById(R.id.tic_day);
            this.mDaySpinner.setFormatter(NumberPicker.getTwoDigitFormatter());
            this.mDaySpinner.setOnLongPressUpdateInterval(100L);
            this.mDaySpinner.setOnValueChangedListener(onValueChangeListener);
            this.mDaySpinner.setOnFocusChangeListener(datePicker);
            this.mDaySpinnerInput = (EditText)this.mDaySpinner.findViewById(R.id.numberpicker_input);
            this.mMonthSpinner = (NumberPicker)this.mDelegator.findViewById(R.id.tic_month);
            this.mMonthSpinner.setMinValue(0);
            this.mMonthSpinner.setMaxValue(this.mNumberOfMonths - 1);
            this.mMonthSpinner.setDisplayedValues(this.mShortMonths);
            this.mMonthSpinner.setOnLongPressUpdateInterval(200L);
            this.mMonthSpinner.setOnValueChangedListener(onValueChangeListener);
            this.mMonthSpinner.setOnFocusChangeListener(datePicker);
            this.mMonthSpinnerInput = (EditText)this.mMonthSpinner.findViewById(R.id.numberpicker_input);
            this.mYearSpinner = (NumberPicker)this.mDelegator.findViewById(R.id.tic_year);
            this.mYearSpinner.setOnLongPressUpdateInterval(100L);
            this.mYearSpinner.setOnValueChangedListener(onValueChangeListener);
            this.mYearSpinner.setOnFocusChangeListener(datePicker);
            this.mYearSpinnerInput = (EditText)this.mYearSpinner.findViewById(R.id.numberpicker_input);
            if (!bl2 && !bl3) {
                this.setSpinnersShown(true);
            } else {
                this.setSpinnersShown(bl2);
                this.setCalendarViewShown(bl3);
            }
            this.mTempDate.clear();
            if (!TextUtils.isEmpty((CharSequence)string2)) {
                if (!this.parseDate(string2, this.mTempDate)) {
                    this.mTempDate.set(n5, 0, 1);
                }
            } else {
                this.mTempDate.set(n5, 0, 1);
            }
            this.setMinDate(this.mTempDate.getTimeInMillis());
            this.mTempDate.clear();
            if (!TextUtils.isEmpty((CharSequence)string3)) {
                if (!this.parseDate(string3, this.mTempDate)) {
                    this.mTempDate.set(n4, 11, 31);
                }
            } else {
                this.mTempDate.set(n4, 11, 31);
            }
            this.setMaxDate(this.mTempDate.getTimeInMillis());
            this.mCurrentDate.setTimeInMillis(System.currentTimeMillis());
            this.init(this.mCurrentDate.get(1), this.mCurrentDate.get(2), this.mCurrentDate.get(5), null);
            this.reorderSpinners();
            this.setContentDescriptions();
            if (this.mDelegator.getImportantForAccessibility() == 0) {
                this.mDelegator.setImportantForAccessibility(1);
            }
        }

        private Calendar getCalendarForLocale(Calendar calendar, Locale locale) {
            if (calendar == null) {
                return Calendar.getInstance(locale);
            }
            long l2 = calendar.getTimeInMillis();
            calendar = Calendar.getInstance(locale);
            calendar.setTimeInMillis(l2);
            return calendar;
        }

        private boolean isNewDate(int n2, int n3, int n4) {
            return this.mCurrentDate.get(1) != n2 || this.mCurrentDate.get(2) != n4 || this.mCurrentDate.get(5) != n3;
        }

        private void notifyDateChanged() {
            this.mDelegator.sendAccessibilityEvent(4);
            if (this.mOnDateChangedListener != null) {
                this.mOnDateChangedListener.onDateChanged(this.mDelegator, this.getYear(), this.getMonth(), this.getDayOfMonth());
            }
        }

        private boolean parseDate(String string2, Calendar calendar) {
            try {
                calendar.setTime(this.mDateFormat.parse(string2));
                return true;
            }
            catch (ParseException parseException) {
                Log.w((String)DatePicker.LOG_TAG, (String)("Date: " + string2 + " not in format: " + DATE_FORMAT));
                return false;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private void reorderSpinners() {
            this.mSpinners.removeAllViews();
            char[] cArray = DateFormat.getDateFormatOrder((Context)this.mContext);
            int n2 = cArray.length;
            int n3 = 0;
            while (n3 < n2) {
                switch (cArray[n3]) {
                    default: {
                        throw new IllegalArgumentException(Arrays.toString(cArray));
                    }
                    case 'd': {
                        this.mSpinners.addView((View)this.mDaySpinner);
                        this.setImeOptions(this.mDaySpinner, n2, n3);
                        break;
                    }
                    case 'M': {
                        this.mSpinners.addView((View)this.mMonthSpinner);
                        this.setImeOptions(this.mMonthSpinner, n2, n3);
                        break;
                    }
                    case 'y': {
                        this.mSpinners.addView((View)this.mYearSpinner);
                        this.setImeOptions(this.mYearSpinner, n2, n3);
                    }
                }
                ++n3;
            }
            return;
        }

        private void setContentDescriptions() {
        }

        /*
         * Enabled aggressive block sorting
         */
        private void setDate(int n2, int n3, int n4) {
            this.mCurrentDate.set(n2, n3, n4);
            if (this.mCurrentDate.before(this.mMinDate)) {
                this.mCurrentDate.setTimeInMillis(this.mMinDate.getTimeInMillis());
                return;
            } else {
                if (!this.mCurrentDate.after(this.mMaxDate)) return;
                this.mCurrentDate.setTimeInMillis(this.mMaxDate.getTimeInMillis());
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private void setImeOptions(NumberPicker numberPicker, int n2, int n3) {
            n2 = n3 < n2 - 1 ? 5 : 6;
            ((TextView)numberPicker.findViewById(R.id.numberpicker_input)).setImeOptions(n2);
        }

        private void trySetContentDescription(View view, int n2, int n3) {
            if ((view = view.findViewById(n2)) != null) {
                view.setContentDescription((CharSequence)this.mContext.getString(n3));
            }
        }

        private void updateCalendarView() {
            this.mCalendarView.setDate(this.mCurrentDate.getTimeInMillis(), false, false);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void updateInputState() {
            InputMethodManager inputMethodManager = (InputMethodManager)this.mContext.getSystemService("input_method");
            if (inputMethodManager == null) return;
            if (inputMethodManager.isActive((View)this.mYearSpinnerInput)) {
                this.mYearSpinnerInput.clearFocus();
                inputMethodManager.hideSoftInputFromWindow(this.mDelegator.getWindowToken(), 0);
                return;
            } else {
                if (inputMethodManager.isActive((View)this.mMonthSpinnerInput)) {
                    this.mMonthSpinnerInput.clearFocus();
                    inputMethodManager.hideSoftInputFromWindow(this.mDelegator.getWindowToken(), 0);
                    return;
                }
                if (!inputMethodManager.isActive((View)this.mDaySpinnerInput)) return;
                this.mDaySpinnerInput.clearFocus();
                inputMethodManager.hideSoftInputFromWindow(this.mDelegator.getWindowToken(), 0);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private void updateSpinners() {
            if (this.mCurrentDate.equals(this.mMinDate)) {
                this.mDaySpinner.setMinValue(this.mCurrentDate.get(5));
                this.mDaySpinner.setMaxValue(this.mCurrentDate.getActualMaximum(5));
                this.mDaySpinner.setWrapSelectorWheel(false);
                this.mMonthSpinner.setDisplayedValues(null);
                this.mMonthSpinner.setMinValue(this.mCurrentDate.get(2));
                this.mMonthSpinner.setMaxValue(this.mCurrentDate.getActualMaximum(2));
                this.mMonthSpinner.setWrapSelectorWheel(false);
            } else if (this.mCurrentDate.equals(this.mMaxDate)) {
                this.mDaySpinner.setMinValue(this.mCurrentDate.getActualMinimum(5));
                this.mDaySpinner.setMaxValue(this.mCurrentDate.get(5));
                this.mDaySpinner.setWrapSelectorWheel(false);
                this.mMonthSpinner.setDisplayedValues(null);
                this.mMonthSpinner.setMinValue(this.mCurrentDate.getActualMinimum(2));
                this.mMonthSpinner.setMaxValue(this.mCurrentDate.get(2));
                this.mMonthSpinner.setWrapSelectorWheel(false);
            } else {
                this.mDaySpinner.setMinValue(1);
                this.mDaySpinner.setMaxValue(this.mCurrentDate.getActualMaximum(5));
                this.mDaySpinner.setWrapSelectorWheel(true);
                this.mMonthSpinner.setDisplayedValues(null);
                this.mMonthSpinner.setMinValue(0);
                this.mMonthSpinner.setMaxValue(11);
                this.mMonthSpinner.setWrapSelectorWheel(true);
            }
            String[] stringArray = Arrays.copyOfRange(this.mShortMonths, this.mMonthSpinner.getMinValue(), this.mMonthSpinner.getMaxValue() + 1);
            this.mMonthSpinner.setDisplayedValues(stringArray);
            this.mYearSpinner.setMinValue(this.mMinDate.get(1));
            this.mYearSpinner.setMaxValue(this.mMaxDate.get(1));
            this.mYearSpinner.setWrapSelectorWheel(false);
            this.mYearSpinner.setValue(this.mCurrentDate.get(1));
            this.mMonthSpinner.setValue(this.mCurrentDate.get(2));
            this.mDaySpinner.setValue(this.mCurrentDate.get(5));
            if (this.usingNumericMonths()) {
                this.mMonthSpinnerInput.setRawInputType(2);
            }
        }

        private boolean usingNumericMonths() {
            boolean bl2;
            boolean bl3 = bl2 = false;
            if (this.mAllowNumericMonths) {
                bl3 = bl2;
                if (Character.isDigit(this.mShortMonths[0].charAt(0))) {
                    bl3 = true;
                }
            }
            return bl3;
        }

        @Override
        public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            this.onPopulateAccessibilityEvent(accessibilityEvent);
            return true;
        }

        @Override
        public CalendarView getCalendarView() {
            return this.mCalendarView;
        }

        @Override
        public boolean getCalendarViewShown() {
            return this.mCalendarView.getVisibility() == 0;
        }

        @Override
        public View getCurrentFocusedPicker() {
            if (this.mSpinners == null || this.mSpinners.getChildCount() == 0) {
                return null;
            }
            return this.mSpinners.getFocusedChild();
        }

        @Override
        public int getDayOfMonth() {
            return this.mCurrentDate.get(5);
        }

        @Override
        public int getFirstDayOfWeek() {
            return this.mCalendarView.getFirstDayOfWeek();
        }

        @Override
        public Calendar getMaxDate() {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(this.mCalendarView.getMaxDate());
            return calendar;
        }

        @Override
        public Calendar getMinDate() {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(this.mCalendarView.getMinDate());
            return calendar;
        }

        @Override
        public int getMonth() {
            return this.mCurrentDate.get(2);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public View getNextFocusPicker(View view) {
            View view2;
            block5: {
                block4: {
                    if (this.mSpinners == null || this.mSpinners.getChildCount() == 0) break block4;
                    view2 = view;
                    if (view == null) {
                        view2 = this.getCurrentFocusedPicker();
                    }
                    if (view2 != null) break block5;
                }
                return null;
            }
            return this.mSpinners.focusSearch(view2, 2);
        }

        @Override
        public boolean getSpinnersShown() {
            return this.mSpinners.isShown();
        }

        @Override
        public int getYear() {
            return this.mCurrentDate.get(1);
        }

        @Override
        public void init(int n2, int n3, int n4, OnDateChangedListener onDateChangedListener) {
            this.setDate(n2, n3, n4);
            this.updateSpinners();
            this.updateCalendarView();
            this.mOnDateChangedListener = onDateChangedListener;
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
            accessibilityEvent.setClassName((CharSequence)DatePicker.class.getName());
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            accessibilityNodeInfo.setClassName((CharSequence)DatePicker.class.getName());
        }

        @Override
        public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            String string2 = DateUtils.formatDateTime((Context)this.mContext, (long)this.mCurrentDate.getTimeInMillis(), (int)20);
            accessibilityEvent.getText().add(string2);
        }

        @Override
        public void onRestoreInstanceState(Parcelable object) {
            object = (SavedState)((Object)object);
            this.setDate(((SavedState)((Object)object)).mYear, ((SavedState)((Object)object)).mMonth, ((SavedState)((Object)object)).mDay);
            this.updateSpinners();
            this.updateCalendarView();
        }

        @Override
        public Parcelable onSaveInstanceState(Parcelable parcelable) {
            return new SavedState(parcelable, this.getYear(), this.getMonth(), this.getDayOfMonth());
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void setCalendarViewShown(boolean bl2) {
            CalendarView calendarView = this.mCalendarView;
            int n2 = bl2 ? 0 : 8;
            calendarView.setVisibility(n2);
        }

        @Override
        protected void setCurrentLocale(Locale locale) {
            super.setCurrentLocale(locale);
            this.mTempDate = this.getCalendarForLocale(this.mTempDate, locale);
            this.mMinDate = this.getCalendarForLocale(this.mMinDate, locale);
            this.mMaxDate = this.getCalendarForLocale(this.mMaxDate, locale);
            this.mCurrentDate = this.getCalendarForLocale(this.mCurrentDate, locale);
            this.mNumberOfMonths = this.mTempDate.getActualMaximum(2) + 1;
            this.mShortMonths = new DateFormatSymbols().getShortMonths();
            if (this.usingNumericMonths()) {
                this.mShortMonths = new String[this.mNumberOfMonths];
                for (int i2 = 0; i2 < this.mNumberOfMonths; ++i2) {
                    this.mShortMonths[i2] = String.format("%d", i2 + 1);
                }
            }
        }

        @Override
        public void setEnabled(boolean bl2) {
            this.mDaySpinner.setEnabled(bl2);
            this.mMonthSpinner.setEnabled(bl2);
            this.mYearSpinner.setEnabled(bl2);
            this.mCalendarView.setEnabled(bl2);
            this.mIsEnabled = bl2;
        }

        @Override
        public void setFirstDayOfWeek(int n2) {
            this.mCalendarView.setFirstDayOfWeek(n2);
        }

        @Override
        public void setMaxDate(long l2) {
            this.mTempDate.setTimeInMillis(l2);
            if (this.mTempDate.get(1) == this.mMaxDate.get(1) && this.mTempDate.get(6) != this.mMaxDate.get(6)) {
                return;
            }
            this.mMaxDate.setTimeInMillis(l2);
            this.mCalendarView.setMaxDate(l2);
            if (this.mCurrentDate.after(this.mMaxDate)) {
                this.mCurrentDate.setTimeInMillis(this.mMaxDate.getTimeInMillis());
                this.updateCalendarView();
            }
            this.updateSpinners();
        }

        @Override
        public void setMinDate(long l2) {
            this.mTempDate.setTimeInMillis(l2);
            if (this.mTempDate.get(1) == this.mMinDate.get(1) && this.mTempDate.get(6) != this.mMinDate.get(6)) {
                return;
            }
            this.mMinDate.setTimeInMillis(l2);
            this.mCalendarView.setMinDate(l2);
            if (this.mCurrentDate.before(this.mMinDate)) {
                this.mCurrentDate.setTimeInMillis(this.mMinDate.getTimeInMillis());
                this.updateCalendarView();
            }
            this.updateSpinners();
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void setSpinnersShown(boolean bl2) {
            LinearLayout linearLayout = this.mSpinners;
            int n2 = bl2 ? 0 : 8;
            linearLayout.setVisibility(n2);
        }

        @Override
        public void updateDate(int n2, int n3, int n4) {
            if (!this.isNewDate(n2, n3, n4)) {
                return;
            }
            this.setDate(n2, n3, n4);
            this.updateSpinners();
            this.updateCalendarView();
            this.notifyDateChanged();
        }
    }

    public static interface OnDateChangedListener {
        public void onDateChanged(DatePicker var1, int var2, int var3, int var4);
    }

    private class OnGestureListener
    extends GestureDetector.SimpleOnGestureListener {
        private OnGestureListener() {
        }

        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            DatePicker.this.nextFocus();
            return true;
        }
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
        private final int mDay;
        private final int mMonth;
        private final int mYear;

        private SavedState(Parcel parcel) {
            super(parcel);
            this.mYear = parcel.readInt();
            this.mMonth = parcel.readInt();
            this.mDay = parcel.readInt();
        }

        private SavedState(Parcelable parcelable, int n2, int n3, int n4) {
            super(parcelable);
            this.mYear = n2;
            this.mMonth = n3;
            this.mDay = n4;
        }

        public void writeToParcel(Parcel parcel, int n2) {
            super.writeToParcel(parcel, n2);
            parcel.writeInt(this.mYear);
            parcel.writeInt(this.mMonth);
            parcel.writeInt(this.mDay);
        }
    }

    public static interface ValidationCallback {
        public void onValidationChanged(boolean var1);
    }
}

