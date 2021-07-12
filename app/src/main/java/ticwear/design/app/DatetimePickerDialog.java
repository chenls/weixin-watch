/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.os.Bundle
 *  android.text.format.DateFormat
 *  android.util.Log
 *  android.util.TypedValue
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.ImageButton
 */
package ticwear.design.app;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import com.mobvoi.ticwear.view.SidePanelEventDispatcher;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import ticwear.design.DesignConfig;
import ticwear.design.R;
import ticwear.design.app.AlertDialog;
import ticwear.design.app.DatePickerViewHolder;
import ticwear.design.app.TimePickerViewHolder;
import ticwear.design.widget.CirclePageIndicator;
import ticwear.design.widget.DatePicker;
import ticwear.design.widget.MultiPickerContainer;
import ticwear.design.widget.NumberPicker;
import ticwear.design.widget.TicklableFrameLayout;
import ticwear.design.widget.TimePicker;

public class DatetimePickerDialog
extends AlertDialog
implements DialogInterface.OnClickListener,
DatePicker.OnDateChangedListener,
TimePicker.OnTimeChangedListener,
ViewPager.OnPageChangeListener,
View.OnClickListener,
MultiPickerContainer.MultiPickerClient,
SidePanelEventDispatcher {
    static final String LOG_TAG = "DTPDialog";
    public static final int PAGE_FLAG_DATE = 1;
    public static final int PAGE_FLAG_TIME = 2;
    private Calendar mCurrentCalendar;
    private DatePickerViewHolder mDatePickerViewHolder;
    private NumberPicker mFocusedPicker;
    private boolean mIsSidePanelTouching = false;
    private OnCalendarSetListener mOnCalendarSetListener;
    private boolean mOnLastPage = false;
    private CirclePageIndicator mPageIndicator;
    private PickerPagerAdapter mPagerAdapter;
    private TimePickerViewHolder mTimePickerViewHolder;
    private ViewPager mViewPager;

    /*
     * Enabled aggressive block sorting
     */
    public DatetimePickerDialog(Context object, @StyleRes int n2, int n3, Calendar object2) {
        super((Context)object, DatetimePickerDialog.resolveDialogTheme((Context)object, n2));
        object = this.getContext();
        this.mCurrentCalendar = object2;
        this.mCurrentCalendar.clear(13);
        n2 = (n3 & 1) == 1 ? 1 : 0;
        boolean bl2 = (n3 & 2) == 2;
        int n4 = ((Calendar)object2).get(1);
        int n5 = ((Calendar)object2).get(2);
        int n6 = ((Calendar)object2).get(5);
        int n7 = ((Calendar)object2).get(11);
        int n8 = ((Calendar)object2).get(12);
        ValidationCallback validationCallback = new ValidationCallback(){

            @Override
            public void onValidationChanged(boolean bl2) {
                Button button = DatetimePickerDialog.this.getButton(-1);
                if (button != null) {
                    button.setEnabled(bl2);
                }
            }
        };
        object2 = (TicklableFrameLayout)LayoutInflater.from((Context)this.getContext()).inflate(R.layout.dialog_datetime_picker, null);
        ((TicklableFrameLayout)object2).setSidePanelEventDispatcher(this);
        this.mViewPager = (ViewPager)object2.findViewById(R.id.tic_datetimeContainer);
        ArrayList<View> arrayList = new ArrayList<View>(Integer.bitCount(n3));
        if (n2 != 0) {
            this.mDatePickerViewHolder = new DatePickerViewHolder((Context)object);
            DatePicker datePicker = this.mDatePickerViewHolder.init(this.mViewPager, n4, n5, n6, this, validationCallback);
            datePicker.setMultiPickerClient(this);
            datePicker.setTag(R.id.title_template, R.string.date_picker_dialog_title);
            arrayList.add((View)datePicker);
        }
        if (bl2) {
            this.mTimePickerViewHolder = new TimePickerViewHolder((Context)object);
            object = this.mTimePickerViewHolder.init(this.mViewPager, n7, n8, DateFormat.is24HourFormat((Context)object), this, validationCallback);
            ((TimePicker)object).setMultiPickerClient(this);
            object.setTag(R.id.title_template, (Object)R.string.time_picker_dialog_title);
            arrayList.add((View)object);
        }
        this.mPagerAdapter = new PickerPagerAdapter(arrayList);
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mPageIndicator = (CirclePageIndicator)object2.findViewById(R.id.tic_datetimeIndicator);
        this.mPageIndicator.setViewPager(this.mViewPager);
        this.mPageIndicator.setOnPageChangeListener(this);
        if (this.mPagerAdapter.getCount() < 2) {
            this.mPageIndicator.setVisibility(8);
        }
        this.setView((View)object2);
        this.setButton(-1, this.getContext().getDrawable(R.drawable.tic_ic_btn_next), (DialogInterface.OnClickListener)this);
        this.setButtonPanelLayoutHint(1);
        this.setTitle(this.mPagerAdapter.getPageTitle(0));
    }

    public DatetimePickerDialog(Context context, int n2, Calendar calendar) {
        this(context, 0, n2, calendar);
    }

    private void onConfirm() {
        if (this.mOnCalendarSetListener != null) {
            this.mOnCalendarSetListener.onCalendarSet(this, this.mCurrentCalendar);
        }
    }

    @StyleRes
    static int resolveDialogTheme(Context context, @StyleRes int n2) {
        int n3 = n2;
        if (n2 == 0) {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(16843948, typedValue, true);
            n3 = typedValue.resourceId;
        }
        return n3;
    }

    private void updateButton() {
        ImageButton imageButton;
        block3: {
            block2: {
                imageButton = this.getIconButton(-1);
                if (imageButton == null) break block2;
                if (!this.mOnLastPage) break block3;
                imageButton.setImageResource(R.drawable.tic_ic_btn_ok);
                imageButton.setOnClickListener((View.OnClickListener)this);
            }
            return;
        }
        imageButton.setImageResource(R.drawable.tic_ic_btn_next);
        imageButton.setOnClickListener((View.OnClickListener)this);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean dispatchTouchSidePanelEvent(MotionEvent motionEvent, @NonNull SidePanelEventDispatcher.SuperCallback superCallback) {
        if (motionEvent.getAction() == 0) {
            this.mIsSidePanelTouching = true;
            this.minimizeButtons();
            return superCallback.superDispatchTouchSidePanelEvent(motionEvent);
        }
        if (motionEvent.getAction() != 3) {
            if (motionEvent.getAction() != 1) return superCallback.superDispatchTouchSidePanelEvent(motionEvent);
        }
        this.mIsSidePanelTouching = false;
        this.showButtonsDelayed();
        return superCallback.superDispatchTouchSidePanelEvent(motionEvent);
    }

    public DatePicker getDatePicker() {
        return this.mDatePickerViewHolder.getDatePicker();
    }

    public TimePicker getTimePicker() {
        return this.mTimePickerViewHolder.getTimePicker();
    }

    public void onClick(DialogInterface dialogInterface, int n2) {
        switch (n2) {
            default: {
                return;
            }
            case -1: {
                this.onConfirm();
                return;
            }
            case -2: 
        }
        this.cancel();
    }

    public void onClick(View view) {
        if (this.mOnLastPage) {
            this.onConfirm();
            this.dismiss();
            return;
        }
        this.mViewPager.setCurrentItem(this.mViewPager.getCurrentItem() + 1);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.onPageSelected(0);
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int n2, int n3, int n4) {
        this.mCurrentCalendar.set(n2, n3, n4);
    }

    @Override
    public void onPageScrollStateChanged(int n2) {
        block3: {
            block2: {
                if (this.mIsSidePanelTouching) break block2;
                if (n2 != 0) break block3;
                this.showButtons();
            }
            return;
        }
        this.minimizeButtons();
    }

    @Override
    public void onPageScrolled(int n2, float f2, int n3) {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onPageSelected(int n2) {
        boolean bl2 = n2 == this.mPagerAdapter.getCount() - 1;
        this.mOnLastPage = bl2;
        this.updateButton();
        this.setTitle(this.mPagerAdapter.getPageTitle(n2));
    }

    @Override
    public void onPickerPostFocus(@NonNull NumberPicker numberPicker) {
        if (DesignConfig.DEBUG_PICKERS) {
            Log.d((String)LOG_TAG, (String)("focused on " + numberPicker));
        }
        if (this.mFocusedPicker != null) {
            this.mFocusedPicker.setOnScrollListener(null);
        }
        this.mFocusedPicker = numberPicker;
        this.mFocusedPicker.setOnScrollListener(new NumberPicker.OnScrollListener(){

            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int n2) {
                block3: {
                    block2: {
                        if (DatetimePickerDialog.this.mIsSidePanelTouching) break block2;
                        if (n2 != 0) break block3;
                        DatetimePickerDialog.this.showButtonsDelayed();
                    }
                    return;
                }
                DatetimePickerDialog.this.minimizeButtons();
            }
        });
    }

    @Override
    public boolean onPickerPreFocus(NumberPicker numberPicker, boolean bl2) {
        if (DesignConfig.DEBUG_PICKERS) {
            Log.d((String)LOG_TAG, (String)("pre focus from last? " + bl2 + ", for " + numberPicker));
        }
        if (bl2) {
            this.onClick((View)this.getIconButton(-1));
            return true;
        }
        return false;
    }

    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        this.mDatePickerViewHolder.onRestoreInstanceState(bundle);
    }

    public Bundle onSaveInstanceState() {
        Bundle bundle = super.onSaveInstanceState();
        this.mDatePickerViewHolder.onSaveInstanceState(bundle);
        return bundle;
    }

    @Override
    public void onTimeChanged(TimePicker timePicker, int n2, int n3) {
        this.mCurrentCalendar.set(11, n2);
        this.mCurrentCalendar.set(12, n3);
    }

    public void setOnCalendarSetListener(OnCalendarSetListener onCalendarSetListener) {
        this.mOnCalendarSetListener = onCalendarSetListener;
    }

    public void updateDate(int n2, int n3, int n4) {
        this.mDatePickerViewHolder.updateDate(n2, n3, n4);
    }

    public void updateTime(int n2, int n3) {
        this.mTimePickerViewHolder.updateTime(n2, n3);
    }

    public static class Builder {
        private Calendar defaultCalendar;
        private OnCalendarSetListener listener;
        private final Context mContext;
        private int pageFlag;
        @StyleRes
        private int theme;

        public Builder(Context context) {
            this.mContext = context;
            this.pageFlag = 3;
        }

        public DatetimePickerDialog create() {
            DatetimePickerDialog datetimePickerDialog = new DatetimePickerDialog(this.mContext, this.theme, this.pageFlag, this.defaultCalendar);
            datetimePickerDialog.setOnCalendarSetListener(this.listener);
            return datetimePickerDialog;
        }

        public Builder defaultValue(Calendar calendar) {
            this.defaultCalendar = calendar;
            return this;
        }

        public Builder disableDatePicker() {
            this.pageFlag &= 0xFFFFFFFE;
            return this;
        }

        public Builder disableTimePicker() {
            this.pageFlag &= 0xFFFFFFFD;
            return this;
        }

        public Builder enableDatePicker() {
            this.pageFlag |= 1;
            return this;
        }

        public Builder enableTimePicker() {
            this.pageFlag |= 2;
            return this;
        }

        public Builder listener(OnCalendarSetListener onCalendarSetListener) {
            this.listener = onCalendarSetListener;
            return this;
        }

        public DatetimePickerDialog show() {
            DatetimePickerDialog datetimePickerDialog = this.create();
            datetimePickerDialog.show();
            return datetimePickerDialog;
        }

        public Builder theme(@StyleRes int n2) {
            this.theme = n2;
            return this;
        }
    }

    public static interface OnCalendarSetListener {
        public void onCalendarSet(DatetimePickerDialog var1, Calendar var2);
    }

    private class PickerPagerAdapter
    extends PagerAdapter {
        private List<View> mPickerPages;

        public PickerPagerAdapter(List<View> list) {
            this.mPickerPages = list;
        }

        @Override
        public void destroyItem(ViewGroup viewGroup, int n2, Object object) {
            viewGroup.removeView((View)object);
        }

        @Override
        public int getCount() {
            return this.mPickerPages.size();
        }

        public View getItemPage(int n2) {
            return this.mPickerPages.get(n2);
        }

        @Override
        public CharSequence getPageTitle(int n2) {
            Object object = this.mPickerPages.get(n2).getTag(R.id.title_template);
            if (object instanceof Integer) {
                return DatetimePickerDialog.this.getContext().getString(((Integer)object).intValue());
            }
            return null;
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int n2) {
            View view = this.mPickerPages.get(n2);
            viewGroup.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private static interface ValidationCallback
    extends DatePicker.ValidationCallback,
    TimePicker.ValidationCallback {
    }
}

