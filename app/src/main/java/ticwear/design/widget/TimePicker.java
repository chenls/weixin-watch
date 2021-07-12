/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.TypedArray
 *  android.os.Parcelable
 *  android.util.AttributeSet
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.GestureDetector$SimpleOnGestureListener
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$OnFocusChangeListener
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.FrameLayout
 *  android.widget.TextView
 */
package ticwear.design.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.mobvoi.ticwear.view.SidePanelEventDispatcher;
import java.util.Locale;
import ticwear.design.R;
import ticwear.design.widget.MultiPickerContainer;
import ticwear.design.widget.NumberPicker;
import ticwear.design.widget.TimePickerSpinnerDelegate;

public class TimePicker
extends FrameLayout
implements MultiPickerContainer,
SidePanelEventDispatcher,
View.OnFocusChangeListener {
    private static final int MODE_SPINNER = 1;
    private final TimePickerDelegate mDelegate;
    private final GestureDetector mGestureDetector;
    private MultiPickerContainer.MultiPickerClient mMultiPickerClient;
    private final OnGestureListener mOnGestureListener = new OnGestureListener();

    public TimePicker(Context context) {
        this(context, null);
    }

    public TimePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843933);
    }

    public TimePicker(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, 0);
    }

    public TimePicker(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TimePicker, n2, n3);
        typedArray.getInt(R.styleable.TimePicker_android_timePickerMode, 1);
        typedArray.recycle();
        this.mDelegate = new TimePickerSpinnerDelegate(this, context, attributeSet, n2, n3);
        this.mGestureDetector = new GestureDetector(context, (GestureDetector.OnGestureListener)this.mOnGestureListener);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean nextFocus() {
        boolean bl2 = true;
        View view = this.mDelegate.getCurrentFocusedPicker();
        View view2 = this.mDelegate.getNextFocusPicker(view);
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

    @Override
    public boolean dispatchTouchSidePanelEvent(MotionEvent motionEvent, @NonNull SidePanelEventDispatcher.SuperCallback superCallback) {
        this.mGestureDetector.onTouchEvent(motionEvent);
        return superCallback.superDispatchTouchSidePanelEvent(motionEvent);
    }

    public int getBaseline() {
        return this.mDelegate.getBaseline();
    }

    public Integer getCurrentHour() {
        return this.mDelegate.getCurrentHour();
    }

    public Integer getCurrentMinute() {
        return this.mDelegate.getCurrentMinute();
    }

    public boolean is24HourView() {
        return this.mDelegate.is24HourView();
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

    public void setCurrentHour(Integer n2) {
        this.mDelegate.setCurrentHour(n2);
    }

    public void setCurrentMinute(Integer n2) {
        this.mDelegate.setCurrentMinute(n2);
    }

    public void setEnabled(boolean bl2) {
        super.setEnabled(bl2);
        this.mDelegate.setEnabled(bl2);
    }

    public void setIs24HourView(Boolean bl2) {
        this.mDelegate.setIs24HourView(bl2);
    }

    @Override
    public void setMultiPickerClient(MultiPickerContainer.MultiPickerClient multiPickerClient) {
        this.mMultiPickerClient = multiPickerClient;
    }

    public void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener) {
        this.mDelegate.setOnTimeChangedListener(onTimeChangedListener);
    }

    public void setValidationCallback(@Nullable ValidationCallback validationCallback) {
        this.mDelegate.setValidationCallback(validationCallback);
    }

    static abstract class AbstractTimePickerDelegate
    implements TimePickerDelegate {
        protected Context mContext;
        protected Locale mCurrentLocale;
        protected TimePicker mDelegator;
        protected OnTimeChangedListener mOnTimeChangedListener;
        protected ValidationCallback mValidationCallback;

        public AbstractTimePickerDelegate(TimePicker timePicker, Context context) {
            this.mDelegator = timePicker;
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

        public void setCurrentLocale(Locale locale) {
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

    private class OnGestureListener
    extends GestureDetector.SimpleOnGestureListener {
        private OnGestureListener() {
        }

        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            TimePicker.this.nextFocus();
            return true;
        }
    }

    public static interface OnTimeChangedListener {
        public void onTimeChanged(TimePicker var1, int var2, int var3);
    }

    static interface TimePickerDelegate {
        public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent var1);

        public int getBaseline();

        public View getCurrentFocusedPicker();

        public Integer getCurrentHour();

        public Integer getCurrentMinute();

        public View getNextFocusPicker(View var1);

        public boolean is24HourView();

        public boolean isEnabled();

        public void onConfigurationChanged(Configuration var1);

        public void onInitializeAccessibilityEvent(AccessibilityEvent var1);

        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo var1);

        public void onPopulateAccessibilityEvent(AccessibilityEvent var1);

        public void onRestoreInstanceState(Parcelable var1);

        public Parcelable onSaveInstanceState(Parcelable var1);

        public void setCurrentHour(Integer var1);

        public void setCurrentMinute(Integer var1);

        public void setEnabled(boolean var1);

        public void setIs24HourView(Boolean var1);

        public void setOnTimeChangedListener(OnTimeChangedListener var1);

        public void setValidationCallback(ValidationCallback var1);
    }

    public static interface ValidationCallback {
        public void onValidationChanged(boolean var1);
    }
}

