/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.os.Bundle
 *  android.text.Editable
 *  android.text.InputFilter
 *  android.text.Spanned
 *  android.text.TextUtils
 *  android.text.method.NumberKeyListener
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.util.SparseArray
 *  android.util.TypedValue
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.View$OnFocusChangeListener
 *  android.view.View$OnLongClickListener
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityManager
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.accessibility.AccessibilityNodeProvider
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.ImageButton
 *  android.widget.LinearLayout
 *  android.widget.Scroller
 *  android.widget.TextView
 */
package ticwear.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;
import com.mobvoi.ticwear.view.SidePanelEventDispatcher;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import ticwear.design.DesignConfig;
import ticwear.design.R;

public class NumberPicker
extends LinearLayout
implements SidePanelEventDispatcher {
    private static final int DEFAULT_LAYOUT_RESOURCE_ID = R.layout.number_picker_ticwear;
    private static final long DEFAULT_LONG_PRESS_UPDATE_INTERVAL = 300L;
    private static final char[] DIGIT_CHARACTERS;
    static final String LOG_TAG = "NumPicker";
    private static final int SELECTOR_ADJUSTMENT_DURATION_MILLIS = 800;
    private static final int SELECTOR_MAX_FLING_VELOCITY_ADJUSTMENT = 8;
    private static final int SELECTOR_MIDDLE_ITEM_INDEX = 1;
    private static final int SELECTOR_WHEEL_ITEM_COUNT = 3;
    private static final int SIZE_UNSPECIFIED = -1;
    private static final int SNAP_SCROLL_DURATION = 300;
    private static final float TOP_AND_BOTTOM_FADING_EDGE_STRENGTH = 0.9f;
    private static final int UNSCALED_DEFAULT_SELECTION_DIVIDERS_DISTANCE = 48;
    private static final int UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT = 2;
    private static final TwoDigitFormatter sTwoDigitFormatter;
    private AccessibilityNodeProviderImpl mAccessibilityNodeProvider;
    private final Scroller mAdjustScroller;
    private BeginSoftInputOnLongPressCommand mBeginSoftInputOnLongPressCommand;
    private int mBottomSelectionDividerBottom;
    private ChangeCurrentByOneFromLongPressCommand mChangeCurrentByOneFromLongPressCommand;
    private final boolean mComputeMaxWidth;
    private int mCurrentScrollOffset;
    private final ImageButton mDecrementButton;
    private boolean mDecrementVirtualButtonPressed;
    private String[] mDisplayedValues;
    private final Scroller mFlingScroller;
    private Formatter mFormatter;
    private final boolean mHasSelectorWheel;
    private boolean mHideWheelUntilFocused;
    private boolean mIgnoreMoveEvents;
    private final ImageButton mIncrementButton;
    private boolean mIncrementVirtualButtonPressed;
    private int mInitialScrollOffset;
    private final EditText mInputText;
    private long mLastDownEventTime;
    private float mLastDownEventY;
    private float mLastDownOrMoveEventY;
    private int mLastHandledDownDpadKeyCode = -1;
    private int mLastHoveredChildVirtualViewId;
    private long mLongPressUpdateInterval = 300L;
    private final int mMaxHeight;
    private int mMaxValue;
    private int mMaxWidth;
    private int mMaximumFlingVelocity;
    private final int mMinHeight;
    private int mMinValue;
    private final int mMinWidth;
    private int mMinimumFlingVelocity;
    private OnScrollListener mOnScrollListener;
    private OnValueChangeListener mOnValueChangeListener;
    private boolean mPerformClickOnTap;
    private final PressedStateHelper mPressedStateHelper;
    private int mPreviousScrollerY;
    private int mScrollState = 0;
    private final Drawable mSelectionDivider;
    private final int mSelectionDividerHeight;
    private final int mSelectionDividersDistance;
    private int mSelectorElementHeight;
    private final SparseArray<String> mSelectorIndexToStringCache = new SparseArray();
    private final int[] mSelectorIndices = new int[3];
    private int mSelectorTextGapHeight;
    private final Paint mSelectorWheelPaint;
    private SetSelectionCommand mSetSelectionCommand;
    private final int mSolidColor;
    private final int mTextSize;
    private int mTopSelectionDividerTop;
    private int mTouchSlop;
    private int mValue;
    private VelocityTracker mVelocityTracker;
    private final Drawable mVirtualButtonPressedDrawable;
    private boolean mWrapSelectorWheel;
    private boolean mWrapSelectorWheelPreferred = true;

    static {
        sTwoDigitFormatter = new TwoDigitFormatter();
        DIGIT_CHARACTERS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '\u0660', '\u0661', '\u0662', '\u0663', '\u0664', '\u0665', '\u0666', '\u0667', '\u0668', '\u0669', '\u06f0', '\u06f1', '\u06f2', '\u06f3', '\u06f4', '\u06f5', '\u06f6', '\u06f7', '\u06f8', '\u06f9', '\u0966', '\u0967', '\u0968', '\u0969', '\u096a', '\u096b', '\u096c', '\u096d', '\u096e', '\u096f', '\u09e6', '\u09e7', '\u09e8', '\u09e9', '\u09ea', '\u09eb', '\u09ec', '\u09ed', '\u09ee', '\u09ef', '\u0ce6', '\u0ce7', '\u0ce8', '\u0ce9', '\u0cea', '\u0ceb', '\u0cec', '\u0ced', '\u0cee', '\u0cef'};
    }

    public NumberPicker(Context context) {
        this(context, null);
    }

    public NumberPicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.tic_numberPickerStyle);
    }

    public NumberPicker(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    public NumberPicker(Context context, AttributeSet object, int n2, int n3) {
        super(context, object, n5, n4);
        int n4;
        int n5;
        this.mInitialScrollOffset = Integer.MIN_VALUE;
        TypedArray typedArray = context.obtainStyledAttributes(object, R.styleable.NumberPicker, n5, n4);
        n5 = typedArray.getResourceId(R.styleable.NumberPicker_tic_internalLayout, DEFAULT_LAYOUT_RESOURCE_ID);
        boolean bl2 = n5 != DEFAULT_LAYOUT_RESOURCE_ID;
        this.mHasSelectorWheel = bl2;
        this.mHideWheelUntilFocused = typedArray.getBoolean(R.styleable.NumberPicker_tic_hideWheelUntilFocused, false);
        this.mSolidColor = typedArray.getColor(R.styleable.NumberPicker_tic_solidColor, 0);
        Drawable drawable2 = typedArray.getDrawable(R.styleable.NumberPicker_tic_selectionDivider);
        if (drawable2 != null) {
            drawable2.setCallback((Drawable.Callback)this);
            if (drawable2.isStateful()) {
                drawable2.setState(this.getDrawableState());
            }
        }
        this.mSelectionDivider = drawable2;
        n4 = (int)TypedValue.applyDimension((int)1, (float)2.0f, (DisplayMetrics)this.getResources().getDisplayMetrics());
        this.mSelectionDividerHeight = typedArray.getDimensionPixelSize(R.styleable.NumberPicker_tic_selectionDividerHeight, n4);
        n4 = (int)TypedValue.applyDimension((int)1, (float)48.0f, (DisplayMetrics)this.getResources().getDisplayMetrics());
        this.mSelectionDividersDistance = typedArray.getDimensionPixelSize(R.styleable.NumberPicker_tic_selectionDividersDistance, n4);
        this.mMinHeight = typedArray.getDimensionPixelSize(R.styleable.NumberPicker_tic_internalMinHeight, -1);
        this.mMaxHeight = typedArray.getDimensionPixelSize(R.styleable.NumberPicker_tic_internalMaxHeight, -1);
        if (this.mMinHeight != -1 && this.mMaxHeight != -1 && this.mMinHeight > this.mMaxHeight) {
            throw new IllegalArgumentException("minHeight > maxHeight");
        }
        this.mMinWidth = typedArray.getDimensionPixelSize(R.styleable.NumberPicker_tic_internalMinWidth, -1);
        this.mMaxWidth = typedArray.getDimensionPixelSize(R.styleable.NumberPicker_tic_internalMaxWidth, -1);
        if (this.mMinWidth != -1 && this.mMaxWidth != -1 && this.mMinWidth > this.mMaxWidth) {
            throw new IllegalArgumentException("minWidth > maxWidth");
        }
        bl2 = this.mMaxWidth == -1;
        this.mComputeMaxWidth = bl2;
        this.mVirtualButtonPressedDrawable = typedArray.getDrawable(R.styleable.NumberPicker_tic_virtualButtonPressedDrawable);
        typedArray.recycle();
        this.mPressedStateHelper = new PressedStateHelper();
        bl2 = !this.mHasSelectorWheel;
        this.setWillNotDraw(bl2);
        ((LayoutInflater)this.getContext().getSystemService("layout_inflater")).inflate(n5, (ViewGroup)this, true);
        View.OnClickListener onClickListener = new View.OnClickListener(){

            public void onClick(View view) {
                NumberPicker.this.hideSoftInput();
                NumberPicker.this.mInputText.clearFocus();
                if (view.getId() == R.id.tic_increment) {
                    NumberPicker.this.changeValueByOne(true);
                    return;
                }
                NumberPicker.this.changeValueByOne(false);
            }
        };
        View.OnLongClickListener onLongClickListener = new View.OnLongClickListener(){

            public boolean onLongClick(View view) {
                NumberPicker.this.hideSoftInput();
                NumberPicker.this.mInputText.clearFocus();
                if (view.getId() == R.id.tic_increment) {
                    NumberPicker.this.postChangeCurrentByOneFromLongPress(true, 0L);
                    return true;
                }
                NumberPicker.this.postChangeCurrentByOneFromLongPress(false, 0L);
                return true;
            }
        };
        if (!this.mHasSelectorWheel) {
            this.mIncrementButton = (ImageButton)this.findViewById(R.id.tic_increment);
            this.mIncrementButton.setOnClickListener(onClickListener);
            this.mIncrementButton.setOnLongClickListener(onLongClickListener);
        } else {
            this.mIncrementButton = null;
        }
        if (!this.mHasSelectorWheel) {
            this.mDecrementButton = (ImageButton)this.findViewById(R.id.tic_decrement);
            this.mDecrementButton.setOnClickListener(onClickListener);
            this.mDecrementButton.setOnLongClickListener(onLongClickListener);
        } else {
            this.mDecrementButton = null;
        }
        if (this.getValidInputMethodManager() == null) {
            this.setDescendantFocusability(393216);
        }
        this.mInputText = (EditText)this.findViewById(R.id.numberpicker_input);
        this.mInputText.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            public void onFocusChange(View view, boolean bl2) {
                if (NumberPicker.this.getValidInputMethodManager() == null) {
                    return;
                }
                if (bl2) {
                    NumberPicker.this.mInputText.selectAll();
                    return;
                }
                NumberPicker.this.mInputText.setSelection(0, 0);
                NumberPicker.this.validateInputTextView(view);
            }
        });
        this.mInputText.setFilters(new InputFilter[]{new InputTextFilter()});
        this.mInputText.setRawInputType(2);
        this.mInputText.setImeOptions(6);
        context = ViewConfiguration.get((Context)context);
        this.mTouchSlop = context.getScaledTouchSlop();
        this.mMinimumFlingVelocity = context.getScaledMinimumFlingVelocity();
        this.mMaximumFlingVelocity = context.getScaledMaximumFlingVelocity() / 8;
        this.mTextSize = (int)this.mInputText.getTextSize();
        context = new Paint();
        context.setAntiAlias(true);
        context.setTextAlign(Paint.Align.CENTER);
        context.setTextSize((float)this.mTextSize);
        context.setTypeface(this.mInputText.getTypeface());
        context.setColor(this.mInputText.getTextColors().getColorForState(ENABLED_STATE_SET, -1));
        this.mSelectorWheelPaint = context;
        this.mFlingScroller = new Scroller(this.getContext(), null, true);
        this.mAdjustScroller = new Scroller(this.getContext(), (Interpolator)new DecelerateInterpolator(2.5f));
        this.updateInputTextView();
        if (this.getImportantForAccessibility() == 0) {
            this.setImportantForAccessibility(1);
        }
    }

    static /* synthetic */ boolean access$1402(NumberPicker numberPicker, boolean bl2) {
        numberPicker.mIncrementVirtualButtonPressed = bl2;
        return bl2;
    }

    static /* synthetic */ boolean access$1602(NumberPicker numberPicker, boolean bl2) {
        numberPicker.mDecrementVirtualButtonPressed = bl2;
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void changeValueByOne(boolean bl2) {
        if (this.mHasSelectorWheel) {
            this.mInputText.setVisibility(4);
            if (!this.moveToFinalScrollerPosition(this.mFlingScroller)) {
                this.moveToFinalScrollerPosition(this.mAdjustScroller);
            }
            this.mPreviousScrollerY = 0;
            if (bl2) {
                this.mFlingScroller.startScroll(0, 0, 0, -this.mSelectorElementHeight, 300);
            } else {
                this.mFlingScroller.startScroll(0, 0, 0, this.mSelectorElementHeight, 300);
            }
            this.invalidate();
            return;
        }
        if (bl2) {
            this.setValueInternal(this.mValue + 1, true);
            return;
        }
        this.setValueInternal(this.mValue - 1, true);
    }

    private void decrementSelectorIndices(int[] nArray) {
        int n2;
        int n3;
        for (n3 = nArray.length - 1; n3 > 0; --n3) {
            nArray[n3] = nArray[n3 - 1];
        }
        n3 = n2 = nArray[1] - 1;
        if (this.mWrapSelectorWheel) {
            n3 = n2;
            if (n2 < this.mMinValue) {
                n3 = this.mMaxValue;
            }
        }
        nArray[0] = n3;
        this.ensureCachedScrollSelectorValue(n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void ensureCachedScrollSelectorValue(int n2) {
        String string2;
        SparseArray<String> sparseArray = this.mSelectorIndexToStringCache;
        if ((String)sparseArray.get(n2) != null) {
            return;
        }
        if (n2 < this.mMinValue || n2 > this.mMaxValue) {
            string2 = "";
        } else if (this.mDisplayedValues != null) {
            int n3 = this.mMinValue;
            string2 = this.mDisplayedValues[n2 - n3];
        } else {
            string2 = this.formatNumber(n2);
        }
        sparseArray.put(n2, (Object)string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean ensureScrollWheelAdjusted() {
        boolean bl2 = false;
        int n2 = this.mInitialScrollOffset - this.mCurrentScrollOffset;
        if (n2 == 0) return bl2;
        this.mPreviousScrollerY = 0;
        int n3 = n2;
        if (Math.abs(n2) > this.mSelectorElementHeight / 2) {
            n3 = n2 > 0 ? -this.mSelectorElementHeight : this.mSelectorElementHeight;
            n3 = n2 + n3;
        }
        this.mAdjustScroller.startScroll(0, 0, 0, n3, 800);
        this.invalidate();
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void fling(int n2) {
        this.mPreviousScrollerY = 0;
        if (n2 > 0) {
            this.mFlingScroller.fling(0, 0, 0, n2, 0, 0, 0, Integer.MAX_VALUE);
        } else {
            this.mFlingScroller.fling(0, Integer.MAX_VALUE, 0, n2, 0, 0, 0, Integer.MAX_VALUE);
        }
        this.invalidate();
    }

    private void focusThisIfNeed() {
        if (this.getValidInputMethodManager() == null && this.hasFocusable() && !this.hasFocus()) {
            this.requestFocus();
        }
    }

    private String formatNumber(int n2) {
        if (this.mFormatter != null) {
            return this.mFormatter.format(n2);
        }
        return NumberPicker.formatNumberWithLocale(n2);
    }

    private static String formatNumberWithLocale(int n2) {
        return String.format(Locale.getDefault(), "%d", n2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int getSelectedPos(String string2) {
        if (this.mDisplayedValues == null) {
            try {
                return Integer.parseInt(string2);
            }
            catch (NumberFormatException numberFormatException) {
                return this.mMinValue;
            }
        }
        for (int i2 = 0; i2 < this.mDisplayedValues.length; string2 = string2.toLowerCase(), ++i2) {
            if (!this.mDisplayedValues[i2].toLowerCase().startsWith(string2)) continue;
            return this.mMinValue + i2;
        }
        try {
            return Integer.parseInt(string2);
        }
        catch (NumberFormatException numberFormatException) {
            return this.mMinValue;
        }
    }

    public static final Formatter getTwoDigitFormatter() {
        return sTwoDigitFormatter;
    }

    @Nullable
    private InputMethodManager getValidInputMethodManager() {
        InputMethodManager inputMethodManager = (InputMethodManager)this.getContext().getSystemService("input_method");
        if (inputMethodManager != null && !inputMethodManager.getEnabledInputMethodList().isEmpty()) {
            return inputMethodManager;
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getWrappedSelectorIndex(int n2) {
        if (n2 > this.mMaxValue) {
            return this.mMinValue + (n2 - this.mMaxValue) % (this.mMaxValue - this.mMinValue) - 1;
        }
        int n3 = n2;
        if (n2 >= this.mMinValue) return n3;
        return this.mMaxValue - (this.mMinValue - n2) % (this.mMaxValue - this.mMinValue) + 1;
    }

    private void hideSoftInput() {
        InputMethodManager inputMethodManager = this.getValidInputMethodManager();
        if (inputMethodManager != null && inputMethodManager.isActive((View)this.mInputText)) {
            inputMethodManager.hideSoftInputFromWindow(this.getWindowToken(), 0);
            if (this.mHasSelectorWheel) {
                this.mInputText.setVisibility(4);
            }
        }
    }

    private void incrementSelectorIndices(int[] nArray) {
        int n2;
        int n3;
        for (n3 = 0; n3 < nArray.length - 1; ++n3) {
            nArray[n3] = nArray[n3 + 1];
        }
        n3 = n2 = nArray[nArray.length - 2] + 1;
        if (this.mWrapSelectorWheel) {
            n3 = n2;
            if (n2 > this.mMaxValue) {
                n3 = this.mMinValue;
            }
        }
        nArray[nArray.length - 1] = n3;
        this.ensureCachedScrollSelectorValue(n3);
    }

    private void initializeFadingEdges() {
        this.setVerticalFadingEdgeEnabled(true);
        this.setFadingEdgeLength((this.getBottom() - this.getTop() - this.mTextSize) / 2);
    }

    private void initializeSelectorWheel() {
        this.initializeSelectorWheelIndices();
        int[] nArray = this.mSelectorIndices;
        int n2 = nArray.length;
        int n3 = this.mTextSize;
        this.mSelectorTextGapHeight = (int)((float)(this.getBottom() - this.getTop() - n2 * n3) / (float)nArray.length + 0.5f);
        this.mSelectorElementHeight = this.mTextSize + this.mSelectorTextGapHeight;
        this.mCurrentScrollOffset = this.mInitialScrollOffset = this.mInputText.getBaseline() + this.mInputText.getTop() - this.mSelectorElementHeight * 1;
        this.updateInputTextView();
    }

    private void initializeSelectorWheelIndices() {
        this.mSelectorIndexToStringCache.clear();
        int[] nArray = this.mSelectorIndices;
        int n2 = this.getValue();
        for (int i2 = 0; i2 < this.mSelectorIndices.length; ++i2) {
            int n3;
            int n4 = n3 = n2 + (i2 - 1);
            if (this.mWrapSelectorWheel) {
                n4 = this.getWrappedSelectorIndex(n3);
            }
            nArray[i2] = n4;
            this.ensureCachedScrollSelectorValue(nArray[i2]);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int makeMeasureSpec(int n2, int n3) {
        if (n3 == -1) {
            return n2;
        }
        int n4 = View.MeasureSpec.getSize((int)n2);
        int n5 = View.MeasureSpec.getMode((int)n2);
        switch (n5) {
            case 0x40000000: {
                return n2;
            }
            default: {
                throw new IllegalArgumentException("Unknown measure mode: " + n5);
            }
            case -2147483648: {
                return View.MeasureSpec.makeMeasureSpec((int)Math.min(n4, n3), (int)0x40000000);
            }
            case 0: 
        }
        return View.MeasureSpec.makeMeasureSpec((int)n3, (int)0x40000000);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean moveToFinalScrollerPosition(Scroller scroller) {
        scroller.forceFinished(true);
        int n2 = scroller.getFinalY() - scroller.getCurrY();
        int n3 = this.mCurrentScrollOffset;
        int n4 = this.mSelectorElementHeight;
        n4 = this.mInitialScrollOffset - (n3 + n2) % n4;
        if (n4 == 0) {
            return false;
        }
        n3 = n4;
        if (Math.abs(n4) > this.mSelectorElementHeight / 2) {
            n3 = n4 > 0 ? n4 - this.mSelectorElementHeight : n4 + this.mSelectorElementHeight;
        }
        this.scrollBy(0, n2 + n3);
        return true;
    }

    private void notifyChange(int n2, int n3) {
        if (this.mOnValueChangeListener != null) {
            this.mOnValueChangeListener.onValueChange(this, n2, this.mValue);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onScrollStateChange(int n2) {
        block5: {
            block4: {
                if (this.mScrollState == n2) break block4;
                if (n2 == 1 || n2 == 2) {
                    this.focusThisIfNeed();
                }
                this.mScrollState = n2;
                if (this.mOnScrollListener != null) break block5;
            }
            return;
        }
        this.mOnScrollListener.onScrollStateChange(this, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onScrollerFinished(Scroller scroller) {
        if (scroller == this.mFlingScroller) {
            if (!this.ensureScrollWheelAdjusted()) {
                this.updateInputTextView();
            }
            this.onScrollStateChange(0);
            return;
        } else {
            if (this.mScrollState == 1) return;
            this.updateInputTextView();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void postBeginSoftInputOnLongPressCommand() {
        if (this.mBeginSoftInputOnLongPressCommand == null) {
            this.mBeginSoftInputOnLongPressCommand = new BeginSoftInputOnLongPressCommand();
        } else {
            this.removeCallbacks(this.mBeginSoftInputOnLongPressCommand);
        }
        this.postDelayed(this.mBeginSoftInputOnLongPressCommand, ViewConfiguration.getLongPressTimeout());
    }

    /*
     * Enabled aggressive block sorting
     */
    private void postChangeCurrentByOneFromLongPress(boolean bl2, long l2) {
        if (this.mChangeCurrentByOneFromLongPressCommand == null) {
            this.mChangeCurrentByOneFromLongPressCommand = new ChangeCurrentByOneFromLongPressCommand();
        } else {
            this.removeCallbacks(this.mChangeCurrentByOneFromLongPressCommand);
        }
        this.mChangeCurrentByOneFromLongPressCommand.setStep(bl2);
        this.postDelayed(this.mChangeCurrentByOneFromLongPressCommand, l2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void postSetSelectionCommand(int n2, int n3) {
        if (this.mSetSelectionCommand == null) {
            this.mSetSelectionCommand = new SetSelectionCommand();
        } else {
            this.removeCallbacks(this.mSetSelectionCommand);
        }
        SetSelectionCommand.access$702(this.mSetSelectionCommand, n2);
        SetSelectionCommand.access$802(this.mSetSelectionCommand, n3);
        this.post(this.mSetSelectionCommand);
    }

    private void removeAllCallbacks() {
        if (this.mChangeCurrentByOneFromLongPressCommand != null) {
            this.removeCallbacks(this.mChangeCurrentByOneFromLongPressCommand);
        }
        if (this.mSetSelectionCommand != null) {
            this.removeCallbacks(this.mSetSelectionCommand);
        }
        if (this.mBeginSoftInputOnLongPressCommand != null) {
            this.removeCallbacks(this.mBeginSoftInputOnLongPressCommand);
        }
        this.mPressedStateHelper.cancel();
    }

    private void removeBeginSoftInputCommand() {
        if (this.mBeginSoftInputOnLongPressCommand != null) {
            this.removeCallbacks(this.mBeginSoftInputOnLongPressCommand);
        }
    }

    private void removeChangeCurrentByOneFromLongPress() {
        if (this.mChangeCurrentByOneFromLongPressCommand != null) {
            this.removeCallbacks(this.mChangeCurrentByOneFromLongPressCommand);
        }
    }

    private int resolveSizeAndStateRespectingMinSize(int n2, int n3, int n4) {
        int n5 = n3;
        if (n2 != -1) {
            n5 = NumberPicker.resolveSizeAndState((int)Math.max(n2, n3), (int)n4, (int)0);
        }
        return n5;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setValueInternal(int n2, boolean bl2) {
        if (this.mValue == n2) {
            return;
        }
        n2 = this.mWrapSelectorWheel ? this.getWrappedSelectorIndex(n2) : Math.min(Math.max(n2, this.mMinValue), this.mMaxValue);
        int n3 = this.mValue;
        this.mValue = n2;
        this.updateInputTextView();
        if (bl2) {
            this.notifyChange(n3, n2);
        }
        this.initializeSelectorWheelIndices();
        this.invalidate();
    }

    private void showSoftInput() {
        InputMethodManager inputMethodManager = this.getValidInputMethodManager();
        if (inputMethodManager != null) {
            if (this.mHasSelectorWheel) {
                this.mInputText.setVisibility(0);
            }
            this.mInputText.requestFocus();
            inputMethodManager.showSoftInput((View)this.mInputText, 0);
            return;
        }
        this.focusThisIfNeed();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void tryComputeMaxWidth() {
        int n2;
        block11: {
            block10: {
                int n3;
                if (!this.mComputeMaxWidth) break block10;
                n2 = 0;
                if (this.mDisplayedValues == null) {
                    float f2 = 0.0f;
                    for (n2 = 0; n2 <= 9; ++n2) {
                        float f3 = this.mSelectorWheelPaint.measureText(NumberPicker.formatNumberWithLocale(n2));
                        float f4 = f2;
                        if (f3 > f2) {
                            f4 = f3;
                        }
                        f2 = f4;
                    }
                    int n4 = 0;
                    for (n2 = this.mMaxValue; n2 > 0; ++n4, n2 /= 10) {
                    }
                    n3 = (int)((float)n4 * f2);
                } else {
                    int n5 = this.mDisplayedValues.length;
                    int n6 = 0;
                    while (true) {
                        n3 = n2;
                        if (n6 >= n5) break;
                        float f5 = this.mSelectorWheelPaint.measureText(this.mDisplayedValues[n6]);
                        n3 = n2;
                        if (f5 > (float)n2) {
                            n3 = (int)f5;
                        }
                        ++n6;
                        n2 = n3;
                    }
                }
                if (this.mMaxWidth != (n2 = n3 + (this.mInputText.getPaddingLeft() + this.mInputText.getPaddingRight()))) break block11;
            }
            return;
        }
        this.mMaxWidth = n2 > this.mMinWidth ? n2 : this.mMinWidth;
        this.invalidate();
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean updateInputTextView() {
        String string2 = this.mDisplayedValues == null ? this.formatNumber(this.mValue) : this.mDisplayedValues[this.mValue - this.mMinValue];
        if (!TextUtils.isEmpty((CharSequence)string2) && !string2.equals(this.mInputText.getText().toString())) {
            this.mInputText.setText((CharSequence)string2);
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateWrapSelectorWheel() {
        boolean bl2 = true;
        boolean bl3 = this.mMaxValue - this.mMinValue >= this.mSelectorIndices.length;
        if (!bl3 || !this.mWrapSelectorWheelPreferred) {
            bl2 = false;
        }
        this.mWrapSelectorWheel = bl2;
    }

    private void validateInputTextView(View object) {
        if (TextUtils.isEmpty((CharSequence)(object = String.valueOf(((TextView)object).getText())))) {
            this.updateInputTextView();
            return;
        }
        this.setValueInternal(this.getSelectedPos(((String)object).toString()), true);
    }

    public void computeScroll() {
        Scroller scroller;
        Scroller scroller2 = scroller = this.mFlingScroller;
        if (scroller.isFinished()) {
            scroller2 = scroller = this.mAdjustScroller;
            if (scroller.isFinished()) {
                return;
            }
        }
        scroller2.computeScrollOffset();
        int n2 = scroller2.getCurrY();
        if (this.mPreviousScrollerY == 0) {
            this.mPreviousScrollerY = scroller2.getStartY();
        }
        this.scrollBy(0, n2 - this.mPreviousScrollerY);
        this.mPreviousScrollerY = n2;
        if (scroller2.isFinished()) {
            this.onScrollerFinished(scroller2);
            return;
        }
        this.invalidate();
    }

    protected int computeVerticalScrollExtent() {
        return this.getHeight();
    }

    protected int computeVerticalScrollOffset() {
        return this.mCurrentScrollOffset;
    }

    protected int computeVerticalScrollRange() {
        return (this.mMaxValue - this.mMinValue + 1) * this.mSelectorElementHeight;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean dispatchHoverEvent(MotionEvent object) {
        if (!this.mHasSelectorWheel) {
            return super.dispatchHoverEvent((MotionEvent)object);
        }
        if (!((AccessibilityManager)this.getContext().getSystemService("accessibility")).isEnabled()) return false;
        int n2 = (int)object.getY();
        n2 = n2 < this.mTopSelectionDividerTop ? 3 : (n2 > this.mBottomSelectionDividerBottom ? 1 : 2);
        int n3 = object.getActionMasked();
        object = (AccessibilityNodeProviderImpl)this.getAccessibilityNodeProvider();
        switch (n3) {
            case 9: {
                ((AccessibilityNodeProviderImpl)((Object)object)).sendAccessibilityEventForVirtualView(n2, 128);
                this.mLastHoveredChildVirtualViewId = n2;
                ((AccessibilityNodeProviderImpl)((Object)object)).performAction(n2, 64, null);
                return false;
            }
            case 7: {
                if (this.mLastHoveredChildVirtualViewId == n2) return false;
                if (this.mLastHoveredChildVirtualViewId == -1) return false;
                ((AccessibilityNodeProviderImpl)((Object)object)).sendAccessibilityEventForVirtualView(this.mLastHoveredChildVirtualViewId, 256);
                ((AccessibilityNodeProviderImpl)((Object)object)).sendAccessibilityEventForVirtualView(n2, 128);
                this.mLastHoveredChildVirtualViewId = n2;
                ((AccessibilityNodeProviderImpl)((Object)object)).performAction(n2, 64, null);
                return false;
            }
            case 10: {
                ((AccessibilityNodeProviderImpl)((Object)object)).sendAccessibilityEventForVirtualView(n2, 256);
                this.mLastHoveredChildVirtualViewId = -1;
                return false;
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        boolean bl2 = true;
        int n2 = keyEvent.getKeyCode();
        switch (n2) {
            case 23: 
            case 66: {
                this.removeAllCallbacks();
            }
            default: {
                return super.dispatchKeyEvent(keyEvent);
            }
            case 19: 
            case 20: {
                if (!this.mHasSelectorWheel) return super.dispatchKeyEvent(keyEvent);
                switch (keyEvent.getAction()) {
                    default: {
                        return super.dispatchKeyEvent(keyEvent);
                    }
                    case 0: {
                        if (!this.mWrapSelectorWheel) {
                            if (n2 == 20) {
                                if (this.getValue() >= this.getMaxValue()) return super.dispatchKeyEvent(keyEvent);
                            } else if (this.getValue() <= this.getMinValue()) return super.dispatchKeyEvent(keyEvent);
                        }
                        this.requestFocus();
                        this.mLastHandledDownDpadKeyCode = n2;
                        this.removeAllCallbacks();
                        if (!this.mFlingScroller.isFinished()) return bl2;
                        bl2 = n2 == 20;
                        this.changeValueByOne(bl2);
                        return true;
                    }
                    case 1: {
                        if (this.mLastHandledDownDpadKeyCode != n2) return super.dispatchKeyEvent(keyEvent);
                        this.mLastHandledDownDpadKeyCode = -1;
                        return true;
                    }
                }
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getActionMasked()) {
            default: {
                return super.dispatchTouchEvent(motionEvent);
            }
            case 1: 
            case 3: 
        }
        this.removeAllCallbacks();
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override
    public boolean dispatchTouchSidePanelEvent(MotionEvent motionEvent, @NonNull SidePanelEventDispatcher.SuperCallback superCallback) {
        super.dispatchTouchEvent(motionEvent);
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        switch (motionEvent.getActionMasked()) {
            default: {
                return super.dispatchTrackballEvent(motionEvent);
            }
            case 1: 
            case 3: 
        }
        this.removeAllCallbacks();
        return super.dispatchTrackballEvent(motionEvent);
    }

    @CallSuper
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] nArray = this.getDrawableState();
        if (this.mSelectionDivider != null && this.mSelectionDivider.isStateful()) {
            this.mSelectionDivider.setState(nArray);
        }
    }

    public AccessibilityNodeProvider getAccessibilityNodeProvider() {
        if (!this.mHasSelectorWheel) {
            return super.getAccessibilityNodeProvider();
        }
        if (this.mAccessibilityNodeProvider == null) {
            this.mAccessibilityNodeProvider = new AccessibilityNodeProviderImpl();
        }
        return this.mAccessibilityNodeProvider;
    }

    protected float getBottomFadingEdgeStrength() {
        return 0.9f;
    }

    public String[] getDisplayedValues() {
        return this.mDisplayedValues;
    }

    public int getMaxValue() {
        return this.mMaxValue;
    }

    public int getMinValue() {
        return this.mMinValue;
    }

    public int getSolidColor() {
        return this.mSolidColor;
    }

    protected float getTopFadingEdgeStrength() {
        return 0.9f;
    }

    public int getValue() {
        return this.mValue;
    }

    public boolean getWrapSelectorWheel() {
        return this.mWrapSelectorWheel;
    }

    @CallSuper
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mSelectionDivider != null) {
            this.mSelectionDivider.jumpToCurrentState();
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.removeAllCallbacks();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onDraw(Canvas canvas) {
        if (!this.mHasSelectorWheel) {
            super.onDraw(canvas);
            return;
        } else {
            int n2;
            int n3;
            boolean bl2 = this.mHideWheelUntilFocused ? this.hasFocus() : true;
            float f2 = (this.getRight() - this.getLeft()) / 2;
            float f3 = this.mCurrentScrollOffset;
            if (bl2 && this.mVirtualButtonPressedDrawable != null && this.mScrollState == 0) {
                if (this.mDecrementVirtualButtonPressed) {
                    this.mVirtualButtonPressedDrawable.setState(PRESSED_STATE_SET);
                    this.mVirtualButtonPressedDrawable.setBounds(0, 0, this.getRight(), this.mTopSelectionDividerTop);
                    this.mVirtualButtonPressedDrawable.draw(canvas);
                }
                if (this.mIncrementVirtualButtonPressed) {
                    this.mVirtualButtonPressedDrawable.setState(PRESSED_STATE_SET);
                    this.mVirtualButtonPressedDrawable.setBounds(0, this.mBottomSelectionDividerBottom, this.getRight(), this.getBottom());
                    this.mVirtualButtonPressedDrawable.draw(canvas);
                }
            }
            int[] nArray = this.mSelectorIndices;
            for (n3 = 0; n3 < nArray.length; f3 += (float)this.mSelectorElementHeight, ++n3) {
                n2 = nArray[n3];
                String string2 = (String)this.mSelectorIndexToStringCache.get(n2);
                if ((!bl2 || n3 == 1) && (n3 != 1 || this.mInputText.getVisibility() == 0)) continue;
                canvas.drawText(string2, f2, f3, this.mSelectorWheelPaint);
            }
            if (!bl2 || this.mSelectionDivider == null) return;
            n3 = this.mTopSelectionDividerTop;
            n2 = this.mSelectionDividerHeight;
            this.mSelectionDivider.setBounds(0, n3, this.getRight(), n3 + n2);
            this.mSelectionDivider.draw(canvas);
            n3 = this.mBottomSelectionDividerBottom;
            n2 = this.mSelectionDividerHeight;
            this.mSelectionDivider.setBounds(0, n3 - n2, this.getRight(), n3);
            this.mSelectionDivider.draw(canvas);
            return;
        }
    }

    protected void onFocusChanged(boolean bl2, int n2, Rect rect) {
        super.onFocusChanged(bl2, n2, rect);
        if (DesignConfig.DEBUG_PICKERS && bl2) {
            Log.i((String)LOG_TAG, (String)("Picker " + this + " focused."));
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName((CharSequence)NumberPicker.class.getName());
        accessibilityEvent.setScrollable(true);
        accessibilityEvent.setScrollY((this.mMinValue + this.mValue) * this.mSelectorElementHeight);
        accessibilityEvent.setMaxScrollY((this.mMaxValue - this.mMinValue) * this.mSelectorElementHeight);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        float f2;
        if (!this.mHasSelectorWheel || !this.isEnabled()) {
            return false;
        }
        switch (motionEvent.getActionMasked()) {
            default: {
                return false;
            }
            case 0: 
        }
        this.removeAllCallbacks();
        this.mInputText.setVisibility(4);
        this.mLastDownEventY = f2 = motionEvent.getY();
        this.mLastDownOrMoveEventY = f2;
        this.mLastDownEventTime = motionEvent.getEventTime();
        this.mIgnoreMoveEvents = false;
        this.mPerformClickOnTap = false;
        if (this.mLastDownEventY < (float)this.mTopSelectionDividerTop) {
            if (this.mScrollState == 0) {
                this.mPressedStateHelper.buttonPressDelayed(2);
            }
        } else if (this.mLastDownEventY > (float)this.mBottomSelectionDividerBottom && this.mScrollState == 0) {
            this.mPressedStateHelper.buttonPressDelayed(1);
        }
        if (!this.mFlingScroller.isFinished()) {
            this.mFlingScroller.forceFinished(true);
            this.mAdjustScroller.forceFinished(true);
            this.onScrollStateChange(0);
            return true;
        }
        if (!this.mAdjustScroller.isFinished()) {
            this.mFlingScroller.forceFinished(true);
            this.mAdjustScroller.forceFinished(true);
            return true;
        }
        if (this.mLastDownEventY < (float)this.mTopSelectionDividerTop) {
            this.hideSoftInput();
            this.postChangeCurrentByOneFromLongPress(false, ViewConfiguration.getLongPressTimeout());
            return true;
        }
        if (this.mLastDownEventY > (float)this.mBottomSelectionDividerBottom) {
            this.hideSoftInput();
            this.postChangeCurrentByOneFromLongPress(true, ViewConfiguration.getLongPressTimeout());
            return true;
        }
        this.mPerformClickOnTap = true;
        this.postBeginSoftInputOnLongPressCommand();
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        if (!this.mHasSelectorWheel) {
            super.onLayout(bl2, n2, n3, n4, n5);
            return;
        } else {
            n5 = this.getMeasuredWidth();
            n4 = this.getMeasuredHeight();
            n2 = this.mInputText.getMeasuredWidth();
            n3 = this.mInputText.getMeasuredHeight();
            n5 = (n5 - n2) / 2;
            n4 = (n4 - n3) / 2;
            this.mInputText.layout(n5, n4, n5 + n2, n4 + n3);
            if (!bl2) return;
            this.initializeSelectorWheel();
            this.initializeFadingEdges();
            this.mTopSelectionDividerTop = (this.getHeight() - this.mSelectionDividersDistance) / 2 - this.mSelectionDividerHeight;
            this.mBottomSelectionDividerBottom = this.mTopSelectionDividerTop + this.mSelectionDividerHeight * 2 + this.mSelectionDividersDistance;
            return;
        }
    }

    protected void onMeasure(int n2, int n3) {
        if (!this.mHasSelectorWheel) {
            super.onMeasure(n2, n3);
            return;
        }
        super.onMeasure(this.makeMeasureSpec(n2, this.mMaxWidth), this.makeMeasureSpec(n3, this.mMaxHeight));
        this.setMeasuredDimension(this.resolveSizeAndStateRespectingMinSize(this.mMinWidth, this.getMeasuredWidth(), n2), this.resolveSizeAndStateRespectingMinSize(this.mMinHeight, this.getMeasuredHeight(), n3));
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        block13: {
            block16: {
                block14: {
                    int n2;
                    block15: {
                        block12: {
                            if (!this.isEnabled()) return false;
                            if (!this.mHasSelectorWheel) {
                                return false;
                            }
                            if (this.mVelocityTracker == null) {
                                this.mVelocityTracker = VelocityTracker.obtain();
                            }
                            this.mVelocityTracker.addMovement(motionEvent);
                            switch (motionEvent.getActionMasked()) {
                                case 2: {
                                    if (this.mIgnoreMoveEvents) return true;
                                    float f2 = motionEvent.getY();
                                    if (this.mScrollState != 1) {
                                        if ((int)Math.abs(f2 - this.mLastDownEventY) > this.mTouchSlop) {
                                            this.removeAllCallbacks();
                                            this.onScrollStateChange(1);
                                        }
                                    } else {
                                        this.scrollBy(0, (int)(f2 - this.mLastDownOrMoveEventY));
                                        this.invalidate();
                                    }
                                    this.mLastDownOrMoveEventY = f2;
                                }
                                default: {
                                    return true;
                                }
                                case 1: 
                            }
                            this.removeBeginSoftInputCommand();
                            this.removeChangeCurrentByOneFromLongPress();
                            this.mPressedStateHelper.cancel();
                            VelocityTracker velocityTracker = this.mVelocityTracker;
                            velocityTracker.computeCurrentVelocity(1000, (float)this.mMaximumFlingVelocity);
                            n2 = (int)velocityTracker.getYVelocity();
                            if (Math.abs(n2) <= this.mMinimumFlingVelocity) break block12;
                            this.fling(n2);
                            this.onScrollStateChange(2);
                            break block13;
                        }
                        n2 = (int)motionEvent.getY();
                        int n3 = (int)Math.abs((float)n2 - this.mLastDownEventY);
                        long l2 = motionEvent.getEventTime();
                        long l3 = this.mLastDownEventTime;
                        if (n3 > this.mTouchSlop || l2 - l3 >= (long)ViewConfiguration.getTapTimeout()) break block14;
                        if (!this.mPerformClickOnTap) break block15;
                        this.mPerformClickOnTap = false;
                        this.performClick();
                        break block16;
                    }
                    if ((n2 = n2 / this.mSelectorElementHeight - 1) > 0) {
                        this.changeValueByOne(true);
                        this.mPressedStateHelper.buttonTapped(1);
                        break block16;
                    } else if (n2 < 0) {
                        this.changeValueByOne(false);
                        this.mPressedStateHelper.buttonTapped(2);
                    }
                    break block16;
                }
                this.ensureScrollWheelAdjusted();
            }
            this.onScrollStateChange(0);
        }
        this.mVelocityTracker.recycle();
        this.mVelocityTracker = null;
        return true;
    }

    public boolean performClick() {
        if (!this.mHasSelectorWheel) {
            return super.performClick();
        }
        if (!super.performClick()) {
            this.showSoftInput();
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean performLongClick() {
        boolean bl2 = true;
        if (!this.mHasSelectorWheel) {
            return super.performLongClick();
        }
        if (super.performLongClick()) return bl2;
        this.showSoftInput();
        this.mIgnoreMoveEvents = true;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void scrollBy(int n2, int n3) {
        int[] nArray = this.mSelectorIndices;
        if (!this.mWrapSelectorWheel && n3 > 0 && nArray[1] <= this.mMinValue) {
            this.mCurrentScrollOffset = this.mInitialScrollOffset;
            return;
        } else {
            if (!this.mWrapSelectorWheel && n3 < 0 && nArray[1] >= this.mMaxValue) {
                this.mCurrentScrollOffset = this.mInitialScrollOffset;
                return;
            }
            this.mCurrentScrollOffset += n3;
            while (this.mCurrentScrollOffset - this.mInitialScrollOffset > this.mSelectorTextGapHeight) {
                this.mCurrentScrollOffset -= this.mSelectorElementHeight;
                this.decrementSelectorIndices(nArray);
                this.setValueInternal(nArray[1], true);
                if (this.mWrapSelectorWheel || nArray[1] > this.mMinValue) continue;
                this.mCurrentScrollOffset = this.mInitialScrollOffset;
            }
            while (this.mCurrentScrollOffset - this.mInitialScrollOffset < -this.mSelectorTextGapHeight) {
                this.mCurrentScrollOffset += this.mSelectorElementHeight;
                this.incrementSelectorIndices(nArray);
                this.setValueInternal(nArray[1], true);
                if (this.mWrapSelectorWheel || nArray[1] < this.mMaxValue) continue;
                this.mCurrentScrollOffset = this.mInitialScrollOffset;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setDisplayedValues(String[] stringArray) {
        if (this.mDisplayedValues == stringArray) {
            return;
        }
        this.mDisplayedValues = stringArray;
        if (this.mDisplayedValues != null) {
            this.mInputText.setRawInputType(524289);
        } else {
            this.mInputText.setRawInputType(2);
        }
        this.updateInputTextView();
        this.initializeSelectorWheelIndices();
        this.tryComputeMaxWidth();
    }

    public void setEnabled(boolean bl2) {
        super.setEnabled(bl2);
        if (!this.mHasSelectorWheel) {
            this.mIncrementButton.setEnabled(bl2);
        }
        if (!this.mHasSelectorWheel) {
            this.mDecrementButton.setEnabled(bl2);
        }
        this.mInputText.setEnabled(bl2);
    }

    public void setFormatter(Formatter formatter) {
        if (formatter == this.mFormatter) {
            return;
        }
        this.mFormatter = formatter;
        this.initializeSelectorWheelIndices();
        this.updateInputTextView();
    }

    public void setMaxValue(int n2) {
        if (this.mMaxValue == n2) {
            return;
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("maxValue must be >= 0");
        }
        this.mMaxValue = n2;
        if (this.mMaxValue < this.mValue) {
            this.mValue = this.mMaxValue;
        }
        this.updateWrapSelectorWheel();
        this.initializeSelectorWheelIndices();
        this.updateInputTextView();
        this.tryComputeMaxWidth();
        this.invalidate();
    }

    public void setMinValue(int n2) {
        if (this.mMinValue == n2) {
            return;
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("minValue must be >= 0");
        }
        this.mMinValue = n2;
        if (this.mMinValue > this.mValue) {
            this.mValue = this.mMinValue;
        }
        this.updateWrapSelectorWheel();
        this.initializeSelectorWheelIndices();
        this.updateInputTextView();
        this.tryComputeMaxWidth();
        this.invalidate();
    }

    public void setOnLongPressUpdateInterval(long l2) {
        this.mLongPressUpdateInterval = l2;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    public void setOnValueChangedListener(OnValueChangeListener onValueChangeListener) {
        this.mOnValueChangeListener = onValueChangeListener;
    }

    public void setValue(int n2) {
        this.setValueInternal(n2, false);
    }

    public void setWrapSelectorWheel(boolean bl2) {
        this.mWrapSelectorWheelPreferred = bl2;
        this.updateWrapSelectorWheel();
    }

    class AccessibilityNodeProviderImpl
    extends AccessibilityNodeProvider {
        private static final int UNDEFINED = Integer.MIN_VALUE;
        private static final int VIRTUAL_VIEW_ID_DECREMENT = 3;
        private static final int VIRTUAL_VIEW_ID_INCREMENT = 1;
        private static final int VIRTUAL_VIEW_ID_INPUT = 2;
        private int mAccessibilityFocusedView;
        private final int[] mTempArray;
        private final Rect mTempRect = new Rect();

        AccessibilityNodeProviderImpl() {
            this.mTempArray = new int[2];
            this.mAccessibilityFocusedView = Integer.MIN_VALUE;
        }

        private AccessibilityNodeInfo createAccessibilityNodeInfoForNumberPicker(int n2, int n3, int n4, int n5) {
            AccessibilityNodeInfo accessibilityNodeInfo = AccessibilityNodeInfo.obtain();
            accessibilityNodeInfo.setClassName((CharSequence)NumberPicker.class.getName());
            accessibilityNodeInfo.setPackageName((CharSequence)NumberPicker.this.getContext().getPackageName());
            accessibilityNodeInfo.setSource((View)NumberPicker.this);
            if (this.hasVirtualDecrementButton()) {
                accessibilityNodeInfo.addChild((View)NumberPicker.this, 3);
            }
            accessibilityNodeInfo.addChild((View)NumberPicker.this, 2);
            if (this.hasVirtualIncrementButton()) {
                accessibilityNodeInfo.addChild((View)NumberPicker.this, 1);
            }
            accessibilityNodeInfo.setParent((View)NumberPicker.this.getParentForAccessibility());
            accessibilityNodeInfo.setEnabled(NumberPicker.this.isEnabled());
            accessibilityNodeInfo.setScrollable(true);
            if (this.mAccessibilityFocusedView != -1) {
                accessibilityNodeInfo.addAction(64);
            }
            if (this.mAccessibilityFocusedView == -1) {
                accessibilityNodeInfo.addAction(128);
            }
            if (NumberPicker.this.isEnabled()) {
                if (NumberPicker.this.getWrapSelectorWheel() || NumberPicker.this.getValue() < NumberPicker.this.getMaxValue()) {
                    accessibilityNodeInfo.addAction(4096);
                }
                if (NumberPicker.this.getWrapSelectorWheel() || NumberPicker.this.getValue() > NumberPicker.this.getMinValue()) {
                    accessibilityNodeInfo.addAction(8192);
                }
            }
            return accessibilityNodeInfo;
        }

        private AccessibilityNodeInfo createAccessibilityNodeInfoForVirtualButton(int n2, String string2, int n3, int n4, int n5, int n6) {
            AccessibilityNodeInfo accessibilityNodeInfo = AccessibilityNodeInfo.obtain();
            accessibilityNodeInfo.setClassName((CharSequence)Button.class.getName());
            accessibilityNodeInfo.setPackageName((CharSequence)NumberPicker.this.getContext().getPackageName());
            accessibilityNodeInfo.setSource((View)NumberPicker.this, n2);
            accessibilityNodeInfo.setParent((View)NumberPicker.this);
            accessibilityNodeInfo.setText((CharSequence)string2);
            accessibilityNodeInfo.setClickable(true);
            accessibilityNodeInfo.setLongClickable(true);
            accessibilityNodeInfo.setEnabled(NumberPicker.this.isEnabled());
            string2 = this.mTempRect;
            string2.set(n3, n4, n5, n6);
            accessibilityNodeInfo.setBoundsInParent((Rect)string2);
            int[] nArray = this.mTempArray;
            NumberPicker.this.getLocationOnScreen(nArray);
            string2.offset(nArray[0], nArray[1]);
            accessibilityNodeInfo.setBoundsInScreen((Rect)string2);
            if (this.mAccessibilityFocusedView != n2) {
                accessibilityNodeInfo.addAction(64);
            }
            if (this.mAccessibilityFocusedView == n2) {
                accessibilityNodeInfo.addAction(128);
            }
            if (NumberPicker.this.isEnabled()) {
                accessibilityNodeInfo.addAction(16);
            }
            return accessibilityNodeInfo;
        }

        private AccessibilityNodeInfo createAccessibiltyNodeInfoForInputText(int n2, int n3, int n4, int n5) {
            AccessibilityNodeInfo accessibilityNodeInfo = NumberPicker.this.mInputText.createAccessibilityNodeInfo();
            accessibilityNodeInfo.setSource((View)NumberPicker.this, 2);
            if (this.mAccessibilityFocusedView != 2) {
                accessibilityNodeInfo.addAction(64);
            }
            if (this.mAccessibilityFocusedView == 2) {
                accessibilityNodeInfo.addAction(128);
            }
            Rect rect = this.mTempRect;
            rect.set(n2, n3, n4, n5);
            accessibilityNodeInfo.setBoundsInParent(rect);
            int[] nArray = this.mTempArray;
            NumberPicker.this.getLocationOnScreen(nArray);
            rect.offset(nArray[0], nArray[1]);
            accessibilityNodeInfo.setBoundsInScreen(rect);
            return accessibilityNodeInfo;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void findAccessibilityNodeInfosByTextInChild(String string2, int n2, List<AccessibilityNodeInfo> list) {
            switch (n2) {
                case 3: {
                    String string3 = this.getVirtualDecrementButtonText();
                    if (TextUtils.isEmpty((CharSequence)string3) || !string3.toString().toLowerCase().contains(string2)) return;
                    list.add(this.createAccessibilityNodeInfo(3));
                    return;
                }
                case 2: {
                    Editable editable = NumberPicker.this.mInputText.getText();
                    if (!TextUtils.isEmpty((CharSequence)editable) && editable.toString().toLowerCase().contains(string2)) {
                        list.add(this.createAccessibilityNodeInfo(2));
                        return;
                    }
                    editable = NumberPicker.this.mInputText.getText();
                    if (TextUtils.isEmpty((CharSequence)editable) || !editable.toString().toLowerCase().contains(string2)) return;
                    list.add(this.createAccessibilityNodeInfo(2));
                    return;
                }
                default: {
                    return;
                }
                case 1: 
            }
            String string4 = this.getVirtualIncrementButtonText();
            if (TextUtils.isEmpty((CharSequence)string4) || !string4.toString().toLowerCase().contains(string2)) return;
            list.add(this.createAccessibilityNodeInfo(1));
        }

        private String getVirtualDecrementButtonText() {
            int n2;
            int n3 = n2 = NumberPicker.this.mValue - 1;
            if (NumberPicker.this.mWrapSelectorWheel) {
                n3 = NumberPicker.this.getWrappedSelectorIndex(n2);
            }
            if (n3 >= NumberPicker.this.mMinValue) {
                if (NumberPicker.this.mDisplayedValues == null) {
                    return NumberPicker.this.formatNumber(n3);
                }
                return NumberPicker.this.mDisplayedValues[n3 - NumberPicker.this.mMinValue];
            }
            return null;
        }

        private String getVirtualIncrementButtonText() {
            int n2;
            int n3 = n2 = NumberPicker.this.mValue + 1;
            if (NumberPicker.this.mWrapSelectorWheel) {
                n3 = NumberPicker.this.getWrappedSelectorIndex(n2);
            }
            if (n3 <= NumberPicker.this.mMaxValue) {
                if (NumberPicker.this.mDisplayedValues == null) {
                    return NumberPicker.this.formatNumber(n3);
                }
                return NumberPicker.this.mDisplayedValues[n3 - NumberPicker.this.mMinValue];
            }
            return null;
        }

        private boolean hasVirtualDecrementButton() {
            return NumberPicker.this.getWrapSelectorWheel() || NumberPicker.this.getValue() > NumberPicker.this.getMinValue();
        }

        private boolean hasVirtualIncrementButton() {
            return NumberPicker.this.getWrapSelectorWheel() || NumberPicker.this.getValue() < NumberPicker.this.getMaxValue();
        }

        private void sendAccessibilityEventForVirtualButton(int n2, int n3, String string2) {
            if (((AccessibilityManager)NumberPicker.this.getContext().getSystemService("accessibility")).isEnabled()) {
                AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain((int)n3);
                accessibilityEvent.setClassName((CharSequence)Button.class.getName());
                accessibilityEvent.setPackageName((CharSequence)NumberPicker.this.getContext().getPackageName());
                accessibilityEvent.getText().add(string2);
                accessibilityEvent.setEnabled(NumberPicker.this.isEnabled());
                accessibilityEvent.setSource((View)NumberPicker.this, n2);
                NumberPicker.this.requestSendAccessibilityEvent((View)NumberPicker.this, accessibilityEvent);
            }
        }

        private void sendAccessibilityEventForVirtualText(int n2) {
            if (((AccessibilityManager)NumberPicker.this.getContext().getSystemService("accessibility")).isEnabled()) {
                AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain((int)n2);
                NumberPicker.this.mInputText.onInitializeAccessibilityEvent(accessibilityEvent);
                NumberPicker.this.mInputText.onPopulateAccessibilityEvent(accessibilityEvent);
                accessibilityEvent.setSource((View)NumberPicker.this, 2);
                NumberPicker.this.requestSendAccessibilityEvent((View)NumberPicker.this, accessibilityEvent);
            }
        }

        public AccessibilityNodeInfo createAccessibilityNodeInfo(int n2) {
            switch (n2) {
                default: {
                    return super.createAccessibilityNodeInfo(n2);
                }
                case -1: {
                    return this.createAccessibilityNodeInfoForNumberPicker(NumberPicker.this.getScrollX(), NumberPicker.this.getScrollY(), NumberPicker.this.getScrollX() + (NumberPicker.this.getRight() - NumberPicker.this.getLeft()), NumberPicker.this.getScrollY() + (NumberPicker.this.getBottom() - NumberPicker.this.getTop()));
                }
                case 3: {
                    String string2 = this.getVirtualDecrementButtonText();
                    n2 = NumberPicker.this.getScrollX();
                    int n3 = NumberPicker.this.getScrollY();
                    int n4 = NumberPicker.this.getScrollX();
                    int n5 = NumberPicker.this.getRight();
                    int n6 = NumberPicker.this.getLeft();
                    int n7 = NumberPicker.this.mTopSelectionDividerTop;
                    return this.createAccessibilityNodeInfoForVirtualButton(3, string2, n2, n3, n5 - n6 + n4, NumberPicker.this.mSelectionDividerHeight + n7);
                }
                case 2: {
                    return this.createAccessibiltyNodeInfoForInputText(NumberPicker.this.getScrollX(), NumberPicker.this.mTopSelectionDividerTop + NumberPicker.this.mSelectionDividerHeight, NumberPicker.this.getScrollX() + (NumberPicker.this.getRight() - NumberPicker.this.getLeft()), NumberPicker.this.mBottomSelectionDividerBottom - NumberPicker.this.mSelectionDividerHeight);
                }
                case 1: 
            }
            String string3 = this.getVirtualIncrementButtonText();
            n2 = NumberPicker.this.getScrollX();
            int n8 = NumberPicker.this.mBottomSelectionDividerBottom;
            int n9 = NumberPicker.this.mSelectionDividerHeight;
            int n10 = NumberPicker.this.getScrollX();
            int n11 = NumberPicker.this.getRight();
            int n12 = NumberPicker.this.getLeft();
            int n13 = NumberPicker.this.getScrollY();
            return this.createAccessibilityNodeInfoForVirtualButton(1, string3, n2, n8 - n9, n11 - n12 + n10, NumberPicker.this.getBottom() - NumberPicker.this.getTop() + n13);
        }

        public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String string2, int n2) {
            if (TextUtils.isEmpty((CharSequence)string2)) {
                return Collections.emptyList();
            }
            String string3 = string2.toLowerCase();
            ArrayList<AccessibilityNodeInfo> arrayList = new ArrayList<AccessibilityNodeInfo>();
            switch (n2) {
                default: {
                    return super.findAccessibilityNodeInfosByText(string2, n2);
                }
                case -1: {
                    this.findAccessibilityNodeInfosByTextInChild(string3, 3, arrayList);
                    this.findAccessibilityNodeInfosByTextInChild(string3, 2, arrayList);
                    this.findAccessibilityNodeInfosByTextInChild(string3, 1, arrayList);
                    return arrayList;
                }
                case 1: 
                case 2: 
                case 3: 
            }
            this.findAccessibilityNodeInfosByTextInChild(string3, n2, arrayList);
            return arrayList;
        }

        /*
         * Unable to fully structure code
         */
        public boolean performAction(int var1_1, int var2_2, Bundle var3_3) {
            var6_4 = false;
            var5_5 = false;
            switch (var1_1) lbl-1000:
            // 2 sources

            {
                default: {
                    var4_6 = super.performAction(var1_1, var2_2, var3_3);
lbl6:
                    // 21 sources

                    return var4_6;
                }
                case -1: {
                    switch (var2_2) {
                        default: {
                            ** GOTO lbl-1000
                        }
                        case 64: {
                            var4_6 = var5_5;
                            if (this.mAccessibilityFocusedView == var1_1) ** GOTO lbl6
                            this.mAccessibilityFocusedView = var1_1;
                            return true;
                        }
                        case 128: {
                            var4_6 = var5_5;
                            if (this.mAccessibilityFocusedView != var1_1) ** GOTO lbl6
                            this.mAccessibilityFocusedView = -2147483648;
                            return true;
                        }
                        case 4096: {
                            var4_6 = var5_5;
                            if (!NumberPicker.this.isEnabled()) ** GOTO lbl6
                            if (NumberPicker.this.getWrapSelectorWheel()) ** GOTO lbl28
                            var4_6 = var5_5;
                            if (NumberPicker.this.getValue() >= NumberPicker.this.getMaxValue()) ** GOTO lbl6
lbl28:
                            // 2 sources

                            NumberPicker.access$200(NumberPicker.this, true);
                            return true;
                        }
                        case 8192: 
                    }
                    var4_6 = var5_5;
                    if (!NumberPicker.this.isEnabled()) ** GOTO lbl6
                    if (NumberPicker.this.getWrapSelectorWheel()) ** GOTO lbl36
                    var4_6 = var5_5;
                    if (NumberPicker.this.getValue() <= NumberPicker.this.getMinValue()) ** GOTO lbl6
lbl36:
                    // 2 sources

                    NumberPicker.access$200(NumberPicker.this, false);
                    return true;
                }
                case 2: {
                    switch (var2_2) {
                        default: {
                            return NumberPicker.access$100(NumberPicker.this).performAccessibilityAction(var2_2, var3_3);
                        }
                        case 1: {
                            var4_6 = var5_5;
                            if (!NumberPicker.this.isEnabled()) ** GOTO lbl6
                            var4_6 = var5_5;
                            if (NumberPicker.access$100(NumberPicker.this).isFocused()) ** GOTO lbl6
                            return NumberPicker.access$100(NumberPicker.this).requestFocus();
                        }
                        case 2: {
                            var4_6 = var5_5;
                            if (!NumberPicker.this.isEnabled()) ** GOTO lbl6
                            var4_6 = var5_5;
                            if (!NumberPicker.access$100(NumberPicker.this).isFocused()) ** GOTO lbl6
                            NumberPicker.access$100(NumberPicker.this).clearFocus();
                            return true;
                        }
                        case 16: {
                            var4_6 = var5_5;
                            if (!NumberPicker.this.isEnabled()) ** GOTO lbl6
                            NumberPicker.this.performClick();
                            return true;
                        }
                        case 32: {
                            var4_6 = var5_5;
                            if (!NumberPicker.this.isEnabled()) ** GOTO lbl6
                            NumberPicker.this.performLongClick();
                            return true;
                        }
                        case 64: {
                            var4_6 = var5_5;
                            if (this.mAccessibilityFocusedView == var1_1) ** GOTO lbl6
                            this.mAccessibilityFocusedView = var1_1;
                            this.sendAccessibilityEventForVirtualView(var1_1, 32768);
                            NumberPicker.access$100(NumberPicker.this).invalidate();
                            return true;
                        }
                        case 128: 
                    }
                    var4_6 = var5_5;
                    if (this.mAccessibilityFocusedView != var1_1) ** GOTO lbl6
                    this.mAccessibilityFocusedView = -2147483648;
                    this.sendAccessibilityEventForVirtualView(var1_1, 65536);
                    NumberPicker.access$100(NumberPicker.this).invalidate();
                    return true;
                }
                case 1: {
                    switch (var2_2) {
                        default: {
                            return false;
                        }
                        case 16: {
                            var4_6 = var5_5;
                            if (!NumberPicker.this.isEnabled()) ** GOTO lbl6
                            NumberPicker.access$200(NumberPicker.this, true);
                            this.sendAccessibilityEventForVirtualView(var1_1, 1);
                            return true;
                        }
                        case 64: {
                            var4_6 = var5_5;
                            if (this.mAccessibilityFocusedView == var1_1) ** GOTO lbl6
                            this.mAccessibilityFocusedView = var1_1;
                            this.sendAccessibilityEventForVirtualView(var1_1, 32768);
                            NumberPicker.this.invalidate(0, NumberPicker.access$1500(NumberPicker.this), NumberPicker.this.getRight(), NumberPicker.this.getBottom());
                            return true;
                        }
                        case 128: 
                    }
                    var4_6 = var5_5;
                    if (this.mAccessibilityFocusedView != var1_1) ** GOTO lbl6
                    this.mAccessibilityFocusedView = -2147483648;
                    this.sendAccessibilityEventForVirtualView(var1_1, 65536);
                    NumberPicker.this.invalidate(0, NumberPicker.access$1500(NumberPicker.this), NumberPicker.this.getRight(), NumberPicker.this.getBottom());
                    return true;
                }
                case 3: 
            }
            switch (var2_2) {
                default: {
                    return false;
                }
                case 16: {
                    var4_6 = var5_5;
                    if (!NumberPicker.this.isEnabled()) ** GOTO lbl6
                    var4_6 = var6_4;
                    if (var1_1 == 1) {
                        var4_6 = true;
                    }
                    NumberPicker.access$200(NumberPicker.this, var4_6);
                    this.sendAccessibilityEventForVirtualView(var1_1, 1);
                    return true;
                }
                case 64: {
                    var4_6 = var5_5;
                    if (this.mAccessibilityFocusedView == var1_1) ** GOTO lbl6
                    this.mAccessibilityFocusedView = var1_1;
                    this.sendAccessibilityEventForVirtualView(var1_1, 32768);
                    NumberPicker.this.invalidate(0, 0, NumberPicker.this.getRight(), NumberPicker.access$1700(NumberPicker.this));
                    return true;
                }
                case 128: 
            }
            var4_6 = var5_5;
            if (this.mAccessibilityFocusedView == var1_1) ** break;
            ** while (true)
            this.mAccessibilityFocusedView = -2147483648;
            this.sendAccessibilityEventForVirtualView(var1_1, 65536);
            NumberPicker.this.invalidate(0, 0, NumberPicker.this.getRight(), NumberPicker.access$1700(NumberPicker.this));
            return true;
        }

        /*
         * Enabled aggressive block sorting
         */
        public void sendAccessibilityEventForVirtualView(int n2, int n3) {
            switch (n2) {
                case 3: {
                    if (!this.hasVirtualDecrementButton()) return;
                    this.sendAccessibilityEventForVirtualButton(n2, n3, this.getVirtualDecrementButtonText());
                    return;
                }
                default: {
                    return;
                }
                case 2: {
                    this.sendAccessibilityEventForVirtualText(n3);
                    return;
                }
                case 1: {
                    if (!this.hasVirtualIncrementButton()) return;
                    this.sendAccessibilityEventForVirtualButton(n2, n3, this.getVirtualIncrementButtonText());
                    return;
                }
            }
        }
    }

    class BeginSoftInputOnLongPressCommand
    implements Runnable {
        BeginSoftInputOnLongPressCommand() {
        }

        @Override
        public void run() {
            NumberPicker.this.performLongClick();
        }
    }

    class ChangeCurrentByOneFromLongPressCommand
    implements Runnable {
        private boolean mIncrement;

        ChangeCurrentByOneFromLongPressCommand() {
        }

        private void setStep(boolean bl2) {
            this.mIncrement = bl2;
        }

        @Override
        public void run() {
            NumberPicker.this.changeValueByOne(this.mIncrement);
            NumberPicker.this.postDelayed(this, NumberPicker.this.mLongPressUpdateInterval);
        }
    }

    public static class CustomEditText
    extends EditText {
        public CustomEditText(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public void onEditorAction(int n2) {
            super.onEditorAction(n2);
            if (n2 == 6) {
                this.clearFocus();
            }
        }
    }

    public static interface Formatter {
        public String format(int var1);
    }

    class InputTextFilter
    extends NumberKeyListener {
        InputTextFilter() {
        }

        public CharSequence filter(CharSequence charSequence, int n2, int n3, Spanned object, int n4, int n5) {
            int n6 = 0;
            if (NumberPicker.this.mDisplayedValues == null) {
                CharSequence charSequence2;
                CharSequence charSequence3 = charSequence2 = super.filter(charSequence, n2, n3, object, n4, n5);
                if (charSequence2 == null) {
                    charSequence3 = charSequence.subSequence(n2, n3);
                }
                if ("".equals(charSequence = String.valueOf(object.subSequence(0, n4)) + charSequence3 + object.subSequence(n5, object.length()))) {
                    return charSequence;
                }
                if (NumberPicker.this.getSelectedPos((String)charSequence) > NumberPicker.this.mMaxValue || ((String)charSequence).length() > String.valueOf(NumberPicker.this.mMaxValue).length()) {
                    return "";
                }
                return charSequence3;
            }
            if (TextUtils.isEmpty((CharSequence)(charSequence = String.valueOf(charSequence.subSequence(n2, n3))))) {
                return "";
            }
            charSequence = String.valueOf(object.subSequence(0, n4)) + charSequence + object.subSequence(n5, object.length());
            object = String.valueOf(charSequence).toLowerCase();
            String[] stringArray = NumberPicker.this.mDisplayedValues;
            n3 = stringArray.length;
            for (n2 = n6; n2 < n3; ++n2) {
                String string2 = stringArray[n2];
                if (!string2.toLowerCase().startsWith((String)object)) continue;
                NumberPicker.this.postSetSelectionCommand(((String)charSequence).length(), string2.length());
                return string2.subSequence(n4, string2.length());
            }
            return "";
        }

        protected char[] getAcceptedChars() {
            return DIGIT_CHARACTERS;
        }

        public int getInputType() {
            return 1;
        }
    }

    public static interface OnScrollListener {
        public static final int SCROLL_STATE_FLING = 2;
        public static final int SCROLL_STATE_IDLE = 0;
        public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

        public void onScrollStateChange(NumberPicker var1, int var2);

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface ScrollState {
        }
    }

    public static interface OnValueChangeListener {
        public void onValueChange(NumberPicker var1, int var2, int var3);
    }

    class PressedStateHelper
    implements Runnable {
        public static final int BUTTON_DECREMENT = 2;
        public static final int BUTTON_INCREMENT = 1;
        private final int MODE_PRESS;
        private final int MODE_TAPPED;
        private int mManagedButton;
        private int mMode;

        PressedStateHelper() {
            this.MODE_PRESS = 1;
            this.MODE_TAPPED = 2;
        }

        public void buttonPressDelayed(int n2) {
            this.cancel();
            this.mMode = 1;
            this.mManagedButton = n2;
            NumberPicker.this.postDelayed(this, ViewConfiguration.getTapTimeout());
        }

        public void buttonTapped(int n2) {
            this.cancel();
            this.mMode = 2;
            this.mManagedButton = n2;
            NumberPicker.this.post(this);
        }

        public void cancel() {
            this.mMode = 0;
            this.mManagedButton = 0;
            NumberPicker.this.removeCallbacks(this);
            if (NumberPicker.this.mIncrementVirtualButtonPressed) {
                NumberPicker.access$1402(NumberPicker.this, false);
                NumberPicker.this.invalidate(0, NumberPicker.this.mBottomSelectionDividerBottom, NumberPicker.this.getRight(), NumberPicker.this.getBottom());
            }
            NumberPicker.access$1602(NumberPicker.this, false);
            if (NumberPicker.this.mDecrementVirtualButtonPressed) {
                NumberPicker.this.invalidate(0, 0, NumberPicker.this.getRight(), NumberPicker.this.mTopSelectionDividerTop);
            }
        }

        @Override
        public void run() {
            switch (this.mMode) {
                default: {
                    return;
                }
                case 1: {
                    switch (this.mManagedButton) {
                        default: {
                            return;
                        }
                        case 1: {
                            NumberPicker.access$1402(NumberPicker.this, true);
                            NumberPicker.this.invalidate(0, NumberPicker.this.mBottomSelectionDividerBottom, NumberPicker.this.getRight(), NumberPicker.this.getBottom());
                            return;
                        }
                        case 2: 
                    }
                    NumberPicker.access$1602(NumberPicker.this, true);
                    NumberPicker.this.invalidate(0, 0, NumberPicker.this.getRight(), NumberPicker.this.mTopSelectionDividerTop);
                    return;
                }
                case 2: 
            }
            switch (this.mManagedButton) {
                default: {
                    return;
                }
                case 1: {
                    if (!NumberPicker.this.mIncrementVirtualButtonPressed) {
                        NumberPicker.this.postDelayed(this, ViewConfiguration.getPressedStateDuration());
                    }
                    NumberPicker.access$1402(NumberPicker.this, NumberPicker.this.mIncrementVirtualButtonPressed ^ true);
                    NumberPicker.this.invalidate(0, NumberPicker.this.mBottomSelectionDividerBottom, NumberPicker.this.getRight(), NumberPicker.this.getBottom());
                    return;
                }
                case 2: 
            }
            if (!NumberPicker.this.mDecrementVirtualButtonPressed) {
                NumberPicker.this.postDelayed(this, ViewConfiguration.getPressedStateDuration());
            }
            NumberPicker.access$1602(NumberPicker.this, NumberPicker.this.mDecrementVirtualButtonPressed ^ true);
            NumberPicker.this.invalidate(0, 0, NumberPicker.this.getRight(), NumberPicker.this.mTopSelectionDividerTop);
        }
    }

    class SetSelectionCommand
    implements Runnable {
        private int mSelectionEnd;
        private int mSelectionStart;

        SetSelectionCommand() {
        }

        static /* synthetic */ int access$702(SetSelectionCommand setSelectionCommand, int n2) {
            setSelectionCommand.mSelectionStart = n2;
            return n2;
        }

        static /* synthetic */ int access$802(SetSelectionCommand setSelectionCommand, int n2) {
            setSelectionCommand.mSelectionEnd = n2;
            return n2;
        }

        @Override
        public void run() {
            NumberPicker.this.mInputText.setSelection(this.mSelectionStart, this.mSelectionEnd);
        }
    }

    private static class TwoDigitFormatter
    implements Formatter {
        final Object[] mArgs;
        final StringBuilder mBuilder = new StringBuilder();
        java.util.Formatter mFmt;
        char mZeroDigit;

        TwoDigitFormatter() {
            this.mArgs = new Object[1];
            this.init(Locale.getDefault());
        }

        private java.util.Formatter createFormatter(Locale locale) {
            return new java.util.Formatter(this.mBuilder, locale);
        }

        private static char getZeroDigit(Locale locale) {
            return new DecimalFormatSymbols(locale).getZeroDigit();
        }

        private void init(Locale locale) {
            this.mFmt = this.createFormatter(locale);
            this.mZeroDigit = TwoDigitFormatter.getZeroDigit(locale);
        }

        @Override
        public String format(int n2) {
            Locale locale = Locale.getDefault();
            if (this.mZeroDigit != TwoDigitFormatter.getZeroDigit(locale)) {
                this.init(locale);
            }
            this.mArgs[0] = n2;
            this.mBuilder.delete(0, this.mBuilder.length());
            this.mFmt.format("%02d", this.mArgs);
            return this.mFmt.toString();
        }
    }
}

