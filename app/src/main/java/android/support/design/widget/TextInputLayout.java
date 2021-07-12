/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.DrawableContainer
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.Editable
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.ViewGroup$LayoutParams
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.animation.AccelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.widget.EditText
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.design.R;
import android.support.design.widget.AnimationUtils;
import android.support.design.widget.CollapsingTextHelper;
import android.support.design.widget.DrawableUtils;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.ThemeUtils;
import android.support.design.widget.ValueAnimatorCompat;
import android.support.design.widget.ViewUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.Space;
import android.support.v7.widget.AppCompatDrawableManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TextInputLayout
extends LinearLayout {
    private static final int ANIMATION_DURATION = 200;
    private static final int INVALID_MAX_LENGTH = -1;
    private static final String LOG_TAG = "TextInputLayout";
    private ValueAnimatorCompat mAnimator;
    private final CollapsingTextHelper mCollapsingTextHelper = new CollapsingTextHelper((View)this);
    private boolean mCounterEnabled;
    private int mCounterMaxLength;
    private int mCounterOverflowTextAppearance;
    private boolean mCounterOverflowed;
    private int mCounterTextAppearance;
    private TextView mCounterView;
    private ColorStateList mDefaultTextColor;
    private EditText mEditText;
    private CharSequence mError;
    private boolean mErrorEnabled;
    private boolean mErrorShown;
    private int mErrorTextAppearance;
    private TextView mErrorView;
    private ColorStateList mFocusedTextColor;
    private boolean mHasReconstructedEditTextBackground;
    private CharSequence mHint;
    private boolean mHintAnimationEnabled;
    private boolean mHintEnabled;
    private LinearLayout mIndicatorArea;
    private int mIndicatorsAdded;
    private Paint mTmpPaint;

    public TextInputLayout(Context context) {
        this(context, null);
    }

    public TextInputLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TextInputLayout(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet);
        ThemeUtils.checkAppCompatTheme(context);
        this.setOrientation(1);
        this.setWillNotDraw(false);
        this.setAddStatesFromChildren(true);
        this.mCollapsingTextHelper.setTextSizeInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        this.mCollapsingTextHelper.setPositionInterpolator((Interpolator)new AccelerateInterpolator());
        this.mCollapsingTextHelper.setCollapsedTextGravity(0x800033);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.TextInputLayout, n2, R.style.Widget_Design_TextInputLayout);
        this.mHintEnabled = context.getBoolean(R.styleable.TextInputLayout_hintEnabled, true);
        this.setHint(context.getText(R.styleable.TextInputLayout_android_hint));
        this.mHintAnimationEnabled = context.getBoolean(R.styleable.TextInputLayout_hintAnimationEnabled, true);
        if (context.hasValue(R.styleable.TextInputLayout_android_textColorHint)) {
            attributeSet = context.getColorStateList(R.styleable.TextInputLayout_android_textColorHint);
            this.mFocusedTextColor = attributeSet;
            this.mDefaultTextColor = attributeSet;
        }
        if (context.getResourceId(R.styleable.TextInputLayout_hintTextAppearance, -1) != -1) {
            this.setHintTextAppearance(context.getResourceId(R.styleable.TextInputLayout_hintTextAppearance, 0));
        }
        this.mErrorTextAppearance = context.getResourceId(R.styleable.TextInputLayout_errorTextAppearance, 0);
        boolean bl2 = context.getBoolean(R.styleable.TextInputLayout_errorEnabled, false);
        boolean bl3 = context.getBoolean(R.styleable.TextInputLayout_counterEnabled, false);
        this.setCounterMaxLength(context.getInt(R.styleable.TextInputLayout_counterMaxLength, -1));
        this.mCounterTextAppearance = context.getResourceId(R.styleable.TextInputLayout_counterTextAppearance, 0);
        this.mCounterOverflowTextAppearance = context.getResourceId(R.styleable.TextInputLayout_counterOverflowTextAppearance, 0);
        context.recycle();
        this.setErrorEnabled(bl2);
        this.setCounterEnabled(bl3);
        if (ViewCompat.getImportantForAccessibility((View)this) == 0) {
            ViewCompat.setImportantForAccessibility((View)this, 1);
        }
        ViewCompat.setAccessibilityDelegate((View)this, new TextInputAccessibilityDelegate());
    }

    private void addIndicator(TextView textView, int n2) {
        if (this.mIndicatorArea == null) {
            this.mIndicatorArea = new LinearLayout(this.getContext());
            this.mIndicatorArea.setOrientation(0);
            this.addView((View)this.mIndicatorArea, -1, -2);
            Space space = new Space(this.getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, 0, 1.0f);
            this.mIndicatorArea.addView((View)space, (ViewGroup.LayoutParams)layoutParams);
            if (this.mEditText != null) {
                this.adjustIndicatorPadding();
            }
        }
        this.mIndicatorArea.setVisibility(0);
        this.mIndicatorArea.addView((View)textView, n2);
        ++this.mIndicatorsAdded;
    }

    private void adjustIndicatorPadding() {
        ViewCompat.setPaddingRelative((View)this.mIndicatorArea, ViewCompat.getPaddingStart((View)this.mEditText), 0, ViewCompat.getPaddingEnd((View)this.mEditText), this.mEditText.getPaddingBottom());
    }

    private void animateToExpansionFraction(float f2) {
        if (this.mCollapsingTextHelper.getExpansionFraction() == f2) {
            return;
        }
        if (this.mAnimator == null) {
            this.mAnimator = ViewUtils.createAnimator();
            this.mAnimator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
            this.mAnimator.setDuration(200);
            this.mAnimator.setUpdateListener(new ValueAnimatorCompat.AnimatorUpdateListener(){

                @Override
                public void onAnimationUpdate(ValueAnimatorCompat valueAnimatorCompat) {
                    TextInputLayout.this.mCollapsingTextHelper.setExpansionFraction(valueAnimatorCompat.getAnimatedFloatValue());
                }
            });
        }
        this.mAnimator.setFloatValues(this.mCollapsingTextHelper.getExpansionFraction(), f2);
        this.mAnimator.start();
    }

    private static boolean arrayContains(int[] nArray, int n2) {
        int n3 = nArray.length;
        for (int i2 = 0; i2 < n3; ++i2) {
            if (nArray[i2] != n2) continue;
            return true;
        }
        return false;
    }

    private void collapseHint(boolean bl2) {
        if (this.mAnimator != null && this.mAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        if (bl2 && this.mHintAnimationEnabled) {
            this.animateToExpansionFraction(1.0f);
            return;
        }
        this.mCollapsingTextHelper.setExpansionFraction(1.0f);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void ensureBackgroundDrawableStateWorkaround() {
        Drawable drawable2;
        block5: {
            block4: {
                Drawable drawable3;
                int n2 = Build.VERSION.SDK_INT;
                if (n2 != 21 || n2 != 22 || (drawable3 = this.mEditText.getBackground()) == null || this.mHasReconstructedEditTextBackground) break block4;
                drawable2 = drawable3.getConstantState().newDrawable();
                if (drawable3 instanceof DrawableContainer) {
                    this.mHasReconstructedEditTextBackground = DrawableUtils.setContainerConstantState((DrawableContainer)drawable3, drawable2.getConstantState());
                }
                if (!this.mHasReconstructedEditTextBackground) break block5;
            }
            return;
        }
        this.mEditText.setBackgroundDrawable(drawable2);
        this.mHasReconstructedEditTextBackground = true;
    }

    private void expandHint(boolean bl2) {
        if (this.mAnimator != null && this.mAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        if (bl2 && this.mHintAnimationEnabled) {
            this.animateToExpansionFraction(0.0f);
            return;
        }
        this.mCollapsingTextHelper.setExpansionFraction(0.0f);
    }

    private void removeIndicator(TextView textView) {
        if (this.mIndicatorArea != null) {
            int n2;
            this.mIndicatorArea.removeView((View)textView);
            this.mIndicatorsAdded = n2 = this.mIndicatorsAdded - 1;
            if (n2 == 0) {
                this.mIndicatorArea.setVisibility(8);
            }
        }
    }

    private void setEditText(EditText editText) {
        if (this.mEditText != null) {
            throw new IllegalArgumentException("We already have an EditText, can only have one");
        }
        if (!(editText instanceof TextInputEditText)) {
            Log.i((String)LOG_TAG, (String)"EditText added is not a TextInputEditText. Please switch to using that class instead.");
        }
        this.mEditText = editText;
        this.mCollapsingTextHelper.setTypefaces(this.mEditText.getTypeface());
        this.mCollapsingTextHelper.setExpandedTextSize(this.mEditText.getTextSize());
        int n2 = this.mEditText.getGravity();
        this.mCollapsingTextHelper.setCollapsedTextGravity(0x800007 & n2 | 0x30);
        this.mCollapsingTextHelper.setExpandedTextGravity(n2);
        this.mEditText.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
                TextInputLayout.this.updateLabelState(true);
                if (TextInputLayout.this.mCounterEnabled) {
                    TextInputLayout.this.updateCounter(editable.length());
                }
            }

            public void beforeTextChanged(CharSequence charSequence, int n2, int n3, int n4) {
            }

            public void onTextChanged(CharSequence charSequence, int n2, int n3, int n4) {
            }
        });
        if (this.mDefaultTextColor == null) {
            this.mDefaultTextColor = this.mEditText.getHintTextColors();
        }
        if (this.mHintEnabled && TextUtils.isEmpty((CharSequence)this.mHint)) {
            this.setHint(this.mEditText.getHint());
            this.mEditText.setHint(null);
        }
        if (this.mCounterView != null) {
            this.updateCounter(this.mEditText.getText().length());
        }
        if (this.mIndicatorArea != null) {
            this.adjustIndicatorPadding();
        }
        this.updateLabelState(false);
    }

    private void setHintInternal(CharSequence charSequence) {
        this.mHint = charSequence;
        this.mCollapsingTextHelper.setText(charSequence);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateCounter(int n2) {
        boolean bl2 = this.mCounterOverflowed;
        if (this.mCounterMaxLength == -1) {
            this.mCounterView.setText((CharSequence)String.valueOf(n2));
            this.mCounterOverflowed = false;
        } else {
            boolean bl3 = n2 > this.mCounterMaxLength;
            this.mCounterOverflowed = bl3;
            if (bl2 != this.mCounterOverflowed) {
                TextView textView = this.mCounterView;
                Context context = this.getContext();
                int n3 = this.mCounterOverflowed ? this.mCounterOverflowTextAppearance : this.mCounterTextAppearance;
                textView.setTextAppearance(context, n3);
            }
            this.mCounterView.setText((CharSequence)this.getContext().getString(R.string.character_counter_pattern, new Object[]{n2, this.mCounterMaxLength}));
        }
        if (this.mEditText != null && bl2 != this.mCounterOverflowed) {
            this.updateLabelState(false);
            this.updateEditTextBackground();
        }
    }

    private void updateEditTextBackground() {
        this.ensureBackgroundDrawableStateWorkaround();
        Drawable drawable2 = this.mEditText.getBackground();
        if (drawable2 == null) {
            return;
        }
        Drawable drawable3 = drawable2;
        if (android.support.v7.widget.DrawableUtils.canSafelyMutateDrawable(drawable2)) {
            drawable3 = drawable2.mutate();
        }
        if (this.mErrorShown && this.mErrorView != null) {
            drawable3.setColorFilter((ColorFilter)AppCompatDrawableManager.getPorterDuffColorFilter(this.mErrorView.getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
            return;
        }
        if (this.mCounterOverflowed && this.mCounterView != null) {
            drawable3.setColorFilter((ColorFilter)AppCompatDrawableManager.getPorterDuffColorFilter(this.mCounterView.getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
            return;
        }
        drawable3.clearColorFilter();
        this.mEditText.refreshDrawableState();
    }

    /*
     * Enabled aggressive block sorting
     */
    private LinearLayout.LayoutParams updateEditTextMargin(ViewGroup.LayoutParams layoutParams) {
        layoutParams = layoutParams instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams)layoutParams : new LinearLayout.LayoutParams(layoutParams);
        if (!this.mHintEnabled) {
            layoutParams.topMargin = 0;
            return layoutParams;
        }
        if (this.mTmpPaint == null) {
            this.mTmpPaint = new Paint();
        }
        this.mTmpPaint.setTypeface(this.mCollapsingTextHelper.getCollapsedTypeface());
        this.mTmpPaint.setTextSize(this.mCollapsingTextHelper.getCollapsedTextSize());
        layoutParams.topMargin = (int)(-this.mTmpPaint.ascent());
        return layoutParams;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateLabelState(boolean bl2) {
        boolean bl3 = this.mEditText != null && !TextUtils.isEmpty((CharSequence)this.mEditText.getText());
        boolean bl4 = TextInputLayout.arrayContains(this.getDrawableState(), 16842908);
        boolean bl5 = !TextUtils.isEmpty((CharSequence)this.getError());
        if (this.mDefaultTextColor != null) {
            this.mCollapsingTextHelper.setExpandedTextColor(this.mDefaultTextColor.getDefaultColor());
        }
        if (this.mCounterOverflowed && this.mCounterView != null) {
            this.mCollapsingTextHelper.setCollapsedTextColor(this.mCounterView.getCurrentTextColor());
        } else if (bl4 && this.mFocusedTextColor != null) {
            this.mCollapsingTextHelper.setCollapsedTextColor(this.mFocusedTextColor.getDefaultColor());
        } else if (this.mDefaultTextColor != null) {
            this.mCollapsingTextHelper.setCollapsedTextColor(this.mDefaultTextColor.getDefaultColor());
        }
        if (!(bl3 || bl4 || bl5)) {
            this.expandHint(bl2);
            return;
        }
        this.collapseHint(bl2);
    }

    public void addView(View view, int n2, ViewGroup.LayoutParams layoutParams) {
        if (view instanceof EditText) {
            this.setEditText((EditText)view);
            super.addView(view, 0, (ViewGroup.LayoutParams)this.updateEditTextMargin(layoutParams));
            return;
        }
        super.addView(view, n2, layoutParams);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mHintEnabled) {
            this.mCollapsingTextHelper.draw(canvas);
        }
    }

    public int getCounterMaxLength() {
        return this.mCounterMaxLength;
    }

    @Nullable
    public EditText getEditText() {
        return this.mEditText;
    }

    @Nullable
    public CharSequence getError() {
        if (this.mErrorEnabled) {
            return this.mError;
        }
        return null;
    }

    @Nullable
    public CharSequence getHint() {
        if (this.mHintEnabled) {
            return this.mHint;
        }
        return null;
    }

    @NonNull
    public Typeface getTypeface() {
        return this.mCollapsingTextHelper.getCollapsedTypeface();
    }

    public boolean isCounterEnabled() {
        return this.mCounterEnabled;
    }

    public boolean isErrorEnabled() {
        return this.mErrorEnabled;
    }

    public boolean isHintAnimationEnabled() {
        return this.mHintAnimationEnabled;
    }

    public boolean isHintEnabled() {
        return this.mHintEnabled;
    }

    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        super.onLayout(bl2, n2, n3, n4, n5);
        if (this.mHintEnabled && this.mEditText != null) {
            n2 = this.mEditText.getLeft() + this.mEditText.getCompoundPaddingLeft();
            n4 = this.mEditText.getRight() - this.mEditText.getCompoundPaddingRight();
            this.mCollapsingTextHelper.setExpandedBounds(n2, this.mEditText.getTop() + this.mEditText.getCompoundPaddingTop(), n4, this.mEditText.getBottom() - this.mEditText.getCompoundPaddingBottom());
            this.mCollapsingTextHelper.setCollapsedBounds(n2, this.getPaddingTop(), n4, n5 - n3 - this.getPaddingBottom());
            this.mCollapsingTextHelper.recalculate();
        }
    }

    protected void onRestoreInstanceState(Parcelable object) {
        if (!(object instanceof SavedState)) {
            super.onRestoreInstanceState(object);
            return;
        }
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.setError(object.error);
        this.requestLayout();
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.mErrorShown) {
            savedState.error = this.getError();
        }
        return savedState;
    }

    public void refreshDrawableState() {
        super.refreshDrawableState();
        this.updateLabelState(ViewCompat.isLaidOut((View)this));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setCounterEnabled(boolean bl2) {
        if (this.mCounterEnabled != bl2) {
            if (bl2) {
                this.mCounterView = new TextView(this.getContext());
                this.mCounterView.setMaxLines(1);
                try {
                    this.mCounterView.setTextAppearance(this.getContext(), this.mCounterTextAppearance);
                }
                catch (Exception exception) {
                    this.mCounterView.setTextAppearance(this.getContext(), R.style.TextAppearance_AppCompat_Caption);
                    this.mCounterView.setTextColor(ContextCompat.getColor(this.getContext(), R.color.design_textinput_error_color_light));
                }
                this.addIndicator(this.mCounterView, -1);
                if (this.mEditText == null) {
                    this.updateCounter(0);
                } else {
                    this.updateCounter(this.mEditText.getText().length());
                }
            } else {
                this.removeIndicator(this.mCounterView);
                this.mCounterView = null;
            }
            this.mCounterEnabled = bl2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setCounterMaxLength(int n2) {
        if (this.mCounterMaxLength != n2) {
            this.mCounterMaxLength = n2 > 0 ? n2 : -1;
            if (this.mCounterEnabled) {
                n2 = this.mEditText == null ? 0 : this.mEditText.getText().length();
                this.updateCounter(n2);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setError(final @Nullable CharSequence charSequence) {
        block13: {
            block14: {
                block12: {
                    if (TextUtils.equals((CharSequence)this.mError, (CharSequence)charSequence)) break block12;
                    this.mError = charSequence;
                    if (this.mErrorEnabled) break block13;
                    if (!TextUtils.isEmpty((CharSequence)charSequence)) break block14;
                }
                return;
            }
            this.setErrorEnabled(true);
        }
        boolean bl2 = ViewCompat.isLaidOut((View)this);
        boolean bl3 = !TextUtils.isEmpty((CharSequence)charSequence);
        this.mErrorShown = bl3;
        if (this.mErrorShown) {
            this.mErrorView.setText(charSequence);
            this.mErrorView.setVisibility(0);
            if (bl2) {
                if (ViewCompat.getAlpha((View)this.mErrorView) == 1.0f) {
                    ViewCompat.setAlpha((View)this.mErrorView, 0.0f);
                }
                ViewCompat.animate((View)this.mErrorView).alpha(1.0f).setDuration(200L).setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR).setListener(new ViewPropertyAnimatorListenerAdapter(){

                    @Override
                    public void onAnimationStart(View view) {
                        view.setVisibility(0);
                    }
                }).start();
            }
        } else if (this.mErrorView.getVisibility() == 0) {
            if (bl2) {
                ViewCompat.animate((View)this.mErrorView).alpha(0.0f).setDuration(200L).setInterpolator(AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR).setListener(new ViewPropertyAnimatorListenerAdapter(){

                    @Override
                    public void onAnimationEnd(View view) {
                        TextInputLayout.this.mErrorView.setText(charSequence);
                        view.setVisibility(4);
                    }
                }).start();
            } else {
                this.mErrorView.setVisibility(4);
            }
        }
        this.updateEditTextBackground();
        this.updateLabelState(true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setErrorEnabled(boolean bl2) {
        if (this.mErrorEnabled != bl2) {
            if (this.mErrorView != null) {
                ViewCompat.animate((View)this.mErrorView).cancel();
            }
            if (bl2) {
                this.mErrorView = new TextView(this.getContext());
                try {
                    this.mErrorView.setTextAppearance(this.getContext(), this.mErrorTextAppearance);
                }
                catch (Exception exception) {
                    this.mErrorView.setTextAppearance(this.getContext(), R.style.TextAppearance_AppCompat_Caption);
                    this.mErrorView.setTextColor(ContextCompat.getColor(this.getContext(), R.color.design_textinput_error_color_light));
                }
                this.mErrorView.setVisibility(4);
                ViewCompat.setAccessibilityLiveRegion((View)this.mErrorView, 1);
                this.addIndicator(this.mErrorView, 0);
            } else {
                this.mErrorShown = false;
                this.updateEditTextBackground();
                this.removeIndicator(this.mErrorView);
                this.mErrorView = null;
            }
            this.mErrorEnabled = bl2;
        }
    }

    public void setHint(@Nullable CharSequence charSequence) {
        if (this.mHintEnabled) {
            this.setHintInternal(charSequence);
            this.sendAccessibilityEvent(2048);
        }
    }

    public void setHintAnimationEnabled(boolean bl2) {
        this.mHintAnimationEnabled = bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setHintEnabled(boolean bl2) {
        if (bl2 != this.mHintEnabled) {
            this.mHintEnabled = bl2;
            CharSequence charSequence = this.mEditText.getHint();
            if (!this.mHintEnabled) {
                if (!TextUtils.isEmpty((CharSequence)this.mHint) && TextUtils.isEmpty((CharSequence)charSequence)) {
                    this.mEditText.setHint(this.mHint);
                }
                this.setHintInternal(null);
            } else if (!TextUtils.isEmpty((CharSequence)charSequence)) {
                if (TextUtils.isEmpty((CharSequence)this.mHint)) {
                    this.setHint(charSequence);
                }
                this.mEditText.setHint(null);
            }
            if (this.mEditText != null) {
                charSequence = this.updateEditTextMargin(this.mEditText.getLayoutParams());
                this.mEditText.setLayoutParams((ViewGroup.LayoutParams)charSequence);
            }
        }
    }

    public void setHintTextAppearance(@StyleRes int n2) {
        this.mCollapsingTextHelper.setCollapsedTextAppearance(n2);
        this.mFocusedTextColor = ColorStateList.valueOf((int)this.mCollapsingTextHelper.getCollapsedTextColor());
        if (this.mEditText != null) {
            this.updateLabelState(false);
            LinearLayout.LayoutParams layoutParams = this.updateEditTextMargin(this.mEditText.getLayoutParams());
            this.mEditText.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
            this.mEditText.requestLayout();
        }
    }

    public void setTypeface(@Nullable Typeface typeface) {
        this.mCollapsingTextHelper.setTypefaces(typeface);
    }

    static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n2) {
                return new SavedState[n2];
            }
        };
        CharSequence error;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.error = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "TextInputLayout.SavedState{" + Integer.toHexString(System.identityHashCode((Object)this)) + " error=" + this.error + "}";
        }

        public void writeToParcel(Parcel parcel, int n2) {
            super.writeToParcel(parcel, n2);
            TextUtils.writeToParcel((CharSequence)this.error, (Parcel)parcel, (int)n2);
        }
    }

    private class TextInputAccessibilityDelegate
    extends AccessibilityDelegateCompat {
        private TextInputAccessibilityDelegate() {
        }

        @Override
        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName((CharSequence)TextInputLayout.class.getSimpleName());
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        @Override
        public void onInitializeAccessibilityNodeInfo(View object, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            void var1_4;
            void var2_6;
            super.onInitializeAccessibilityNodeInfo((View)object, (AccessibilityNodeInfoCompat)var2_6);
            var2_6.setClassName(TextInputLayout.class.getSimpleName());
            CharSequence charSequence = TextInputLayout.this.mCollapsingTextHelper.getText();
            if (!TextUtils.isEmpty((CharSequence)charSequence)) {
                var2_6.setText(charSequence);
            }
            if (TextInputLayout.this.mEditText != null) {
                var2_6.setLabelFor((View)TextInputLayout.this.mEditText);
            }
            if (TextInputLayout.this.mErrorView != null) {
                CharSequence charSequence2 = TextInputLayout.this.mErrorView.getText();
            } else {
                Object var1_5 = null;
            }
            if (!TextUtils.isEmpty((CharSequence)var1_4)) {
                var2_6.setContentInvalid(true);
                var2_6.setError((CharSequence)var1_4);
            }
        }

        @Override
        public void onPopulateAccessibilityEvent(View object, AccessibilityEvent accessibilityEvent) {
            super.onPopulateAccessibilityEvent((View)object, accessibilityEvent);
            object = TextInputLayout.this.mCollapsingTextHelper.getText();
            if (!TextUtils.isEmpty((CharSequence)object)) {
                accessibilityEvent.getText().add(object);
            }
        }
    }
}

