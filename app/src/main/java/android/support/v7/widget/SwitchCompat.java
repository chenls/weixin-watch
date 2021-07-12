/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.Region$Op
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.os.Build$VERSION
 *  android.text.Layout
 *  android.text.Layout$Alignment
 *  android.text.StaticLayout
 *  android.text.TextPaint
 *  android.text.TextUtils
 *  android.text.method.TransformationMethod
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.ViewConfiguration
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.view.animation.Transformation
 *  android.widget.CompoundButton
 */
package android.support.v7.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.text.AllCapsTransformationMethod;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.ViewUtils;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.CompoundButton;

public class SwitchCompat
extends CompoundButton {
    private static final String ACCESSIBILITY_EVENT_CLASS_NAME = "android.widget.Switch";
    private static final int[] CHECKED_STATE_SET = new int[]{0x10100A0};
    private static final int MONOSPACE = 3;
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private static final int THUMB_ANIMATION_DURATION = 250;
    private static final int TOUCH_MODE_DOWN = 1;
    private static final int TOUCH_MODE_DRAGGING = 2;
    private static final int TOUCH_MODE_IDLE = 0;
    private final AppCompatDrawableManager mDrawableManager;
    private int mMinFlingVelocity;
    private Layout mOffLayout;
    private Layout mOnLayout;
    private ThumbAnimation mPositionAnimator;
    private boolean mShowText;
    private boolean mSplitTrack;
    private int mSwitchBottom;
    private int mSwitchHeight;
    private int mSwitchLeft;
    private int mSwitchMinWidth;
    private int mSwitchPadding;
    private int mSwitchRight;
    private int mSwitchTop;
    private TransformationMethod mSwitchTransformationMethod;
    private int mSwitchWidth;
    private final Rect mTempRect;
    private ColorStateList mTextColors;
    private CharSequence mTextOff;
    private CharSequence mTextOn;
    private TextPaint mTextPaint;
    private Drawable mThumbDrawable;
    private float mThumbPosition;
    private int mThumbTextPadding;
    private int mThumbWidth;
    private int mTouchMode;
    private int mTouchSlop;
    private float mTouchX;
    private float mTouchY;
    private Drawable mTrackDrawable;
    private VelocityTracker mVelocityTracker = VelocityTracker.obtain();

    public SwitchCompat(Context context) {
        this(context, null);
    }

    public SwitchCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.switchStyle);
    }

    public SwitchCompat(Context context, AttributeSet object, int n2) {
        super(context, (AttributeSet)object, n2);
        this.mTempRect = new Rect();
        this.mTextPaint = new TextPaint(1);
        Resources resources = this.getResources();
        this.mTextPaint.density = resources.getDisplayMetrics().density;
        object = TintTypedArray.obtainStyledAttributes(context, (AttributeSet)object, R.styleable.SwitchCompat, n2, 0);
        this.mThumbDrawable = ((TintTypedArray)object).getDrawable(R.styleable.SwitchCompat_android_thumb);
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.setCallback((Drawable.Callback)this);
        }
        this.mTrackDrawable = ((TintTypedArray)object).getDrawable(R.styleable.SwitchCompat_track);
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setCallback((Drawable.Callback)this);
        }
        this.mTextOn = ((TintTypedArray)object).getText(R.styleable.SwitchCompat_android_textOn);
        this.mTextOff = ((TintTypedArray)object).getText(R.styleable.SwitchCompat_android_textOff);
        this.mShowText = ((TintTypedArray)object).getBoolean(R.styleable.SwitchCompat_showText, true);
        this.mThumbTextPadding = ((TintTypedArray)object).getDimensionPixelSize(R.styleable.SwitchCompat_thumbTextPadding, 0);
        this.mSwitchMinWidth = ((TintTypedArray)object).getDimensionPixelSize(R.styleable.SwitchCompat_switchMinWidth, 0);
        this.mSwitchPadding = ((TintTypedArray)object).getDimensionPixelSize(R.styleable.SwitchCompat_switchPadding, 0);
        this.mSplitTrack = ((TintTypedArray)object).getBoolean(R.styleable.SwitchCompat_splitTrack, false);
        n2 = ((TintTypedArray)object).getResourceId(R.styleable.SwitchCompat_switchTextAppearance, 0);
        if (n2 != 0) {
            this.setSwitchTextAppearance(context, n2);
        }
        this.mDrawableManager = AppCompatDrawableManager.get();
        ((TintTypedArray)object).recycle();
        context = ViewConfiguration.get((Context)context);
        this.mTouchSlop = context.getScaledTouchSlop();
        this.mMinFlingVelocity = context.getScaledMinimumFlingVelocity();
        this.refreshDrawableState();
        this.setChecked(this.isChecked());
    }

    static /* synthetic */ ThumbAnimation access$102(SwitchCompat switchCompat, ThumbAnimation thumbAnimation) {
        switchCompat.mPositionAnimator = thumbAnimation;
        return thumbAnimation;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void animateThumbToCheckedState(final boolean bl2) {
        if (this.mPositionAnimator != null) {
            this.cancelPositionAnimator();
        }
        float f2 = this.mThumbPosition;
        float f3 = bl2 ? 1.0f : 0.0f;
        this.mPositionAnimator = new ThumbAnimation(f2, f3);
        this.mPositionAnimator.setDuration(250L);
        this.mPositionAnimator.setAnimationListener(new Animation.AnimationListener(){

            /*
             * Enabled aggressive block sorting
             */
            public void onAnimationEnd(Animation object) {
                if (SwitchCompat.this.mPositionAnimator == object) {
                    SwitchCompat switchCompat = SwitchCompat.this;
                    float f2 = bl2 ? 1.0f : 0.0f;
                    switchCompat.setThumbPosition(f2);
                    SwitchCompat.access$102(SwitchCompat.this, null);
                }
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });
        this.startAnimation(this.mPositionAnimator);
    }

    private void cancelPositionAnimator() {
        if (this.mPositionAnimator != null) {
            this.clearAnimation();
            this.mPositionAnimator = null;
        }
    }

    private void cancelSuperTouch(MotionEvent motionEvent) {
        motionEvent = MotionEvent.obtain((MotionEvent)motionEvent);
        motionEvent.setAction(3);
        super.onTouchEvent(motionEvent);
        motionEvent.recycle();
    }

    private static float constrain(float f2, float f3, float f4) {
        if (f2 < f3) {
            return f3;
        }
        if (f2 > f4) {
            return f4;
        }
        return f2;
    }

    private boolean getTargetCheckedState() {
        return this.mThumbPosition > 0.5f;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getThumbOffset() {
        float f2;
        if (ViewUtils.isLayoutRtl((View)this)) {
            f2 = 1.0f - this.mThumbPosition;
            return (int)((float)this.getThumbScrollRange() * f2 + 0.5f);
        }
        f2 = this.mThumbPosition;
        return (int)((float)this.getThumbScrollRange() * f2 + 0.5f);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getThumbScrollRange() {
        Rect rect;
        if (this.mTrackDrawable == null) return 0;
        Rect rect2 = this.mTempRect;
        this.mTrackDrawable.getPadding(rect2);
        if (this.mThumbDrawable != null) {
            rect = DrawableUtils.getOpticalBounds(this.mThumbDrawable);
            return this.mSwitchWidth - this.mThumbWidth - rect2.left - rect2.right - rect.left - rect.right;
        }
        rect = DrawableUtils.INSETS_NONE;
        return this.mSwitchWidth - this.mThumbWidth - rect2.left - rect2.right - rect.left - rect.right;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean hitThumb(float f2, float f3) {
        block3: {
            block2: {
                if (this.mThumbDrawable == null) break block2;
                int n2 = this.getThumbOffset();
                this.mThumbDrawable.getPadding(this.mTempRect);
                int n3 = this.mSwitchTop;
                int n4 = this.mTouchSlop;
                n2 = this.mSwitchLeft + n2 - this.mTouchSlop;
                int n5 = this.mThumbWidth;
                int n6 = this.mTempRect.left;
                int n7 = this.mTempRect.right;
                int n8 = this.mTouchSlop;
                int n9 = this.mSwitchBottom;
                int n10 = this.mTouchSlop;
                if (f2 > (float)n2 && f2 < (float)(n5 + n2 + n6 + n7 + n8) && f3 > (float)(n3 - n4) && f3 < (float)(n9 + n10)) break block3;
            }
            return false;
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Layout makeLayout(CharSequence charSequence) {
        int n2;
        if (this.mSwitchTransformationMethod != null) {
            charSequence = this.mSwitchTransformationMethod.getTransformation(charSequence, (View)this);
        }
        TextPaint textPaint = this.mTextPaint;
        if (charSequence != null) {
            n2 = (int)Math.ceil(Layout.getDesiredWidth((CharSequence)charSequence, (TextPaint)this.mTextPaint));
            return new StaticLayout(charSequence, textPaint, n2, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
        }
        n2 = 0;
        return new StaticLayout(charSequence, textPaint, n2, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setSwitchTypefaceByIndex(int n2, int n3) {
        Typeface typeface = null;
        switch (n2) {
            case 1: {
                typeface = Typeface.SANS_SERIF;
                break;
            }
            case 2: {
                typeface = Typeface.SERIF;
                break;
            }
            case 3: {
                typeface = Typeface.MONOSPACE;
                break;
            }
        }
        this.setSwitchTypeface(typeface, n3);
    }

    private void setThumbPosition(float f2) {
        this.mThumbPosition = f2;
        this.invalidate();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void stopDrag(MotionEvent motionEvent) {
        boolean bl2;
        this.mTouchMode = 0;
        boolean bl3 = motionEvent.getAction() == 1 && this.isEnabled();
        boolean bl4 = this.isChecked();
        if (bl3) {
            this.mVelocityTracker.computeCurrentVelocity(1000);
            float f2 = this.mVelocityTracker.getXVelocity();
            bl2 = Math.abs(f2) > (float)this.mMinFlingVelocity ? (ViewUtils.isLayoutRtl((View)this) ? f2 < 0.0f : f2 > 0.0f) : this.getTargetCheckedState();
        } else {
            bl2 = bl4;
        }
        if (bl2 != bl4) {
            this.playSoundEffect(0);
        }
        this.setChecked(bl2);
        this.cancelSuperTouch(motionEvent);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void draw(Canvas canvas) {
        Rect rect = this.mTempRect;
        int n2 = this.mSwitchLeft;
        int n3 = this.mSwitchTop;
        int n4 = this.mSwitchRight;
        int n5 = this.mSwitchBottom;
        int n6 = n2 + this.getThumbOffset();
        Rect rect2 = this.mThumbDrawable != null ? DrawableUtils.getOpticalBounds(this.mThumbDrawable) : DrawableUtils.INSETS_NONE;
        int n7 = n6;
        if (this.mTrackDrawable != null) {
            int n8;
            this.mTrackDrawable.getPadding(rect);
            int n9 = n6 + rect.left;
            n6 = n3;
            int n10 = n8 = n5;
            int n11 = n2;
            int n12 = n4;
            int n13 = n6;
            if (rect2 != null) {
                n7 = n2;
                if (rect2.left > rect.left) {
                    n7 = n2 + (rect2.left - rect.left);
                }
                n2 = n6;
                if (rect2.top > rect.top) {
                    n2 = n6 + (rect2.top - rect.top);
                }
                n6 = n4;
                if (rect2.right > rect.right) {
                    n6 = n4 - (rect2.right - rect.right);
                }
                n10 = n8;
                n11 = n7;
                n12 = n6;
                n13 = n2;
                if (rect2.bottom > rect.bottom) {
                    n10 = n8 - (rect2.bottom - rect.bottom);
                    n13 = n2;
                    n12 = n6;
                    n11 = n7;
                }
            }
            this.mTrackDrawable.setBounds(n11, n13, n12, n10);
            n7 = n9;
        }
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.getPadding(rect);
            n4 = n7 - rect.left;
            n7 = this.mThumbWidth + n7 + rect.right;
            this.mThumbDrawable.setBounds(n4, n3, n7, n5);
            rect2 = this.getBackground();
            if (rect2 != null) {
                DrawableCompat.setHotspotBounds((Drawable)rect2, n4, n3, n7, n5);
            }
        }
        super.draw(canvas);
    }

    public void drawableHotspotChanged(float f2, float f3) {
        if (Build.VERSION.SDK_INT >= 21) {
            super.drawableHotspotChanged(f2, f3);
        }
        if (this.mThumbDrawable != null) {
            DrawableCompat.setHotspot(this.mThumbDrawable, f2, f3);
        }
        if (this.mTrackDrawable != null) {
            DrawableCompat.setHotspot(this.mTrackDrawable, f2, f3);
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] nArray = this.getDrawableState();
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.setState(nArray);
        }
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setState(nArray);
        }
        this.invalidate();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getCompoundPaddingLeft() {
        int n2;
        if (!ViewUtils.isLayoutRtl((View)this)) {
            return super.getCompoundPaddingLeft();
        }
        int n3 = n2 = super.getCompoundPaddingLeft() + this.mSwitchWidth;
        if (TextUtils.isEmpty((CharSequence)this.getText())) return n3;
        return n2 + this.mSwitchPadding;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getCompoundPaddingRight() {
        int n2;
        if (ViewUtils.isLayoutRtl((View)this)) {
            return super.getCompoundPaddingRight();
        }
        int n3 = n2 = super.getCompoundPaddingRight() + this.mSwitchWidth;
        if (TextUtils.isEmpty((CharSequence)this.getText())) return n3;
        return n2 + this.mSwitchPadding;
    }

    public boolean getShowText() {
        return this.mShowText;
    }

    public boolean getSplitTrack() {
        return this.mSplitTrack;
    }

    public int getSwitchMinWidth() {
        return this.mSwitchMinWidth;
    }

    public int getSwitchPadding() {
        return this.mSwitchPadding;
    }

    public CharSequence getTextOff() {
        return this.mTextOff;
    }

    public CharSequence getTextOn() {
        return this.mTextOn;
    }

    public Drawable getThumbDrawable() {
        return this.mThumbDrawable;
    }

    public int getThumbTextPadding() {
        return this.mThumbTextPadding;
    }

    public Drawable getTrackDrawable() {
        return this.mTrackDrawable;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void jumpDrawablesToCurrentState() {
        if (Build.VERSION.SDK_INT >= 11) {
            super.jumpDrawablesToCurrentState();
            if (this.mThumbDrawable != null) {
                this.mThumbDrawable.jumpToCurrentState();
            }
            if (this.mTrackDrawable != null) {
                this.mTrackDrawable.jumpToCurrentState();
            }
            this.cancelPositionAnimator();
            float f2 = this.isChecked() ? 1.0f : 0.0f;
            this.setThumbPosition(f2);
        }
    }

    protected int[] onCreateDrawableState(int n2) {
        int[] nArray = super.onCreateDrawableState(n2 + 1);
        if (this.isChecked()) {
            SwitchCompat.mergeDrawableStates((int[])nArray, (int[])CHECKED_STATE_SET);
        }
        return nArray;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onDraw(Canvas canvas) {
        int n2;
        super.onDraw(canvas);
        Rect rect = this.mTempRect;
        Drawable drawable2 = this.mTrackDrawable;
        if (drawable2 != null) {
            drawable2.getPadding(rect);
        } else {
            rect.setEmpty();
        }
        int n3 = this.mSwitchTop;
        int n4 = this.mSwitchBottom;
        int n5 = rect.top;
        int n6 = rect.bottom;
        Drawable drawable3 = this.mThumbDrawable;
        if (drawable2 != null) {
            if (this.mSplitTrack && drawable3 != null) {
                Rect rect2 = DrawableUtils.getOpticalBounds(drawable3);
                drawable3.copyBounds(rect);
                rect.left += rect2.left;
                rect.right -= rect2.right;
                n2 = canvas.save();
                canvas.clipRect(rect, Region.Op.DIFFERENCE);
                drawable2.draw(canvas);
                canvas.restoreToCount(n2);
            } else {
                drawable2.draw(canvas);
            }
        }
        int n7 = canvas.save();
        if (drawable3 != null) {
            drawable3.draw(canvas);
        }
        if ((rect = this.getTargetCheckedState() ? this.mOnLayout : this.mOffLayout) != null) {
            int[] nArray = this.getDrawableState();
            if (this.mTextColors != null) {
                this.mTextPaint.setColor(this.mTextColors.getColorForState(nArray, 0));
            }
            this.mTextPaint.drawableState = nArray;
            if (drawable3 != null) {
                drawable3 = drawable3.getBounds();
                n2 = drawable3.left + drawable3.right;
            } else {
                n2 = this.getWidth();
            }
            int n8 = rect.getWidth() / 2;
            n3 = (n3 + n5 + (n4 - n6)) / 2;
            n4 = rect.getHeight() / 2;
            canvas.translate((float)((n2 /= 2) - n8), (float)(n3 - n4));
            rect.draw(canvas);
        }
        canvas.restoreToCount(n7);
    }

    @TargetApi(value=14)
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName((CharSequence)ACCESSIBILITY_EVENT_CLASS_NAME);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (Build.VERSION.SDK_INT >= 14) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName((CharSequence)ACCESSIBILITY_EVENT_CLASS_NAME);
            CharSequence charSequence = this.isChecked() ? this.mTextOn : this.mTextOff;
            if (!TextUtils.isEmpty((CharSequence)charSequence)) {
                CharSequence charSequence2 = accessibilityNodeInfo.getText();
                if (!TextUtils.isEmpty((CharSequence)charSequence2)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(charSequence2).append(' ').append(charSequence);
                    accessibilityNodeInfo.setText((CharSequence)stringBuilder);
                    return;
                }
                accessibilityNodeInfo.setText(charSequence);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        super.onLayout(bl2, n2, n3, n4, n5);
        n2 = 0;
        n3 = 0;
        if (this.mThumbDrawable != null) {
            Rect rect = this.mTempRect;
            if (this.mTrackDrawable != null) {
                this.mTrackDrawable.getPadding(rect);
            } else {
                rect.setEmpty();
            }
            Rect rect2 = DrawableUtils.getOpticalBounds(this.mThumbDrawable);
            n2 = Math.max(0, rect2.left - rect.left);
            n3 = Math.max(0, rect2.right - rect.right);
        }
        if (ViewUtils.isLayoutRtl((View)this)) {
            n4 = this.getPaddingLeft() + n2;
            n5 = this.mSwitchWidth + n4 - n2 - n3;
        } else {
            n5 = this.getWidth() - this.getPaddingRight() - n3;
            n4 = n5 - this.mSwitchWidth + n2 + n3;
        }
        switch (this.getGravity() & 0x70) {
            default: {
                n3 = this.getPaddingTop();
                n2 = n3 + this.mSwitchHeight;
                break;
            }
            case 16: {
                n3 = (this.getPaddingTop() + this.getHeight() - this.getPaddingBottom()) / 2 - this.mSwitchHeight / 2;
                n2 = n3 + this.mSwitchHeight;
                break;
            }
            case 80: {
                n2 = this.getHeight() - this.getPaddingBottom();
                n3 = n2 - this.mSwitchHeight;
            }
        }
        this.mSwitchLeft = n4;
        this.mSwitchTop = n3;
        this.mSwitchBottom = n2;
        this.mSwitchRight = n5;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onMeasure(int n2, int n3) {
        int n4;
        int n5;
        if (this.mShowText) {
            if (this.mOnLayout == null) {
                this.mOnLayout = this.makeLayout(this.mTextOn);
            }
            if (this.mOffLayout == null) {
                this.mOffLayout = this.makeLayout(this.mTextOff);
            }
        }
        Rect rect = this.mTempRect;
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.getPadding(rect);
            n5 = this.mThumbDrawable.getIntrinsicWidth() - rect.left - rect.right;
            n4 = this.mThumbDrawable.getIntrinsicHeight();
        } else {
            n5 = 0;
            n4 = 0;
        }
        int n6 = this.mShowText ? Math.max(this.mOnLayout.getWidth(), this.mOffLayout.getWidth()) + this.mThumbTextPadding * 2 : 0;
        this.mThumbWidth = Math.max(n6, n5);
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.getPadding(rect);
            n5 = this.mTrackDrawable.getIntrinsicHeight();
        } else {
            rect.setEmpty();
            n5 = 0;
        }
        int n7 = rect.left;
        int n8 = rect.right;
        int n9 = n7;
        n6 = n8;
        if (this.mThumbDrawable != null) {
            rect = DrawableUtils.getOpticalBounds(this.mThumbDrawable);
            n9 = Math.max(n7, rect.left);
            n6 = Math.max(n8, rect.right);
        }
        n6 = Math.max(this.mSwitchMinWidth, this.mThumbWidth * 2 + n9 + n6);
        n4 = Math.max(n5, n4);
        this.mSwitchWidth = n6;
        this.mSwitchHeight = n4;
        super.onMeasure(n2, n3);
        if (this.getMeasuredHeight() < n4) {
            this.setMeasuredDimension(ViewCompat.getMeasuredWidthAndState((View)this), n4);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @TargetApi(value=14)
    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        CharSequence charSequence = this.isChecked() ? this.mTextOn : this.mTextOff;
        if (charSequence != null) {
            accessibilityEvent.getText().add(charSequence);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.mVelocityTracker.addMovement(motionEvent);
        switch (MotionEventCompat.getActionMasked(motionEvent)) {
            case 0: {
                float f2 = motionEvent.getX();
                float f3 = motionEvent.getY();
                if (!this.isEnabled()) return super.onTouchEvent(motionEvent);
                if (!this.hitThumb(f2, f3)) return super.onTouchEvent(motionEvent);
                this.mTouchMode = 1;
                this.mTouchX = f2;
                this.mTouchY = f3;
                return super.onTouchEvent(motionEvent);
            }
            case 2: {
                switch (this.mTouchMode) {
                    default: {
                        return super.onTouchEvent(motionEvent);
                    }
                    case 1: {
                        float f4 = motionEvent.getX();
                        float f5 = motionEvent.getY();
                        if (!(Math.abs(f4 - this.mTouchX) > (float)this.mTouchSlop)) {
                            if (!(Math.abs(f5 - this.mTouchY) > (float)this.mTouchSlop)) return super.onTouchEvent(motionEvent);
                        }
                        this.mTouchMode = 2;
                        this.getParent().requestDisallowInterceptTouchEvent(true);
                        this.mTouchX = f4;
                        this.mTouchY = f5;
                        return true;
                    }
                    case 2: {
                        float f6 = motionEvent.getX();
                        int n2 = this.getThumbScrollRange();
                        float f7 = f6 - this.mTouchX;
                        f7 = n2 != 0 ? (f7 /= (float)n2) : (f7 > 0.0f ? 1.0f : -1.0f);
                        float f8 = f7;
                        if (ViewUtils.isLayoutRtl((View)this)) {
                            f8 = -f7;
                        }
                        if ((f7 = SwitchCompat.constrain(this.mThumbPosition + f8, 0.0f, 1.0f)) == this.mThumbPosition) return true;
                        this.mTouchX = f6;
                        this.setThumbPosition(f7);
                        return true;
                    }
                    case 0: 
                }
                return super.onTouchEvent(motionEvent);
            }
            case 1: 
            case 3: {
                if (this.mTouchMode == 2) {
                    this.stopDrag(motionEvent);
                    super.onTouchEvent(motionEvent);
                    return true;
                }
                this.mTouchMode = 0;
                this.mVelocityTracker.clear();
                return super.onTouchEvent(motionEvent);
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setChecked(boolean bl2) {
        super.setChecked(bl2);
        bl2 = this.isChecked();
        if (this.getWindowToken() != null && ViewCompat.isLaidOut((View)this) && this.isShown()) {
            this.animateThumbToCheckedState(bl2);
            return;
        }
        this.cancelPositionAnimator();
        float f2 = bl2 ? 1.0f : 0.0f;
        this.setThumbPosition(f2);
    }

    public void setShowText(boolean bl2) {
        if (this.mShowText != bl2) {
            this.mShowText = bl2;
            this.requestLayout();
        }
    }

    public void setSplitTrack(boolean bl2) {
        this.mSplitTrack = bl2;
        this.invalidate();
    }

    public void setSwitchMinWidth(int n2) {
        this.mSwitchMinWidth = n2;
        this.requestLayout();
    }

    public void setSwitchPadding(int n2) {
        this.mSwitchPadding = n2;
        this.requestLayout();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setSwitchTextAppearance(Context context, int n2) {
        ColorStateList colorStateList = (context = context.obtainStyledAttributes(n2, R.styleable.TextAppearance)).getColorStateList(R.styleable.TextAppearance_android_textColor);
        this.mTextColors = colorStateList != null ? colorStateList : this.getTextColors();
        n2 = context.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0);
        if (n2 != 0 && (float)n2 != this.mTextPaint.getTextSize()) {
            this.mTextPaint.setTextSize((float)n2);
            this.requestLayout();
        }
        this.setSwitchTypefaceByIndex(context.getInt(R.styleable.TextAppearance_android_typeface, -1), context.getInt(R.styleable.TextAppearance_android_textStyle, -1));
        this.mSwitchTransformationMethod = context.getBoolean(R.styleable.TextAppearance_textAllCaps, false) ? new AllCapsTransformationMethod(this.getContext()) : null;
        context.recycle();
    }

    public void setSwitchTypeface(Typeface typeface) {
        if (this.mTextPaint.getTypeface() != typeface) {
            this.mTextPaint.setTypeface(typeface);
            this.requestLayout();
            this.invalidate();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setSwitchTypeface(Typeface typeface, int n2) {
        boolean bl2 = false;
        if (n2 <= 0) {
            this.mTextPaint.setFakeBoldText(false);
            this.mTextPaint.setTextSkewX(0.0f);
            this.setSwitchTypeface(typeface);
            return;
        }
        typeface = typeface == null ? Typeface.defaultFromStyle((int)n2) : Typeface.create((Typeface)typeface, (int)n2);
        this.setSwitchTypeface(typeface);
        int n3 = typeface != null ? typeface.getStyle() : 0;
        typeface = this.mTextPaint;
        if (((n2 &= ~n3) & 1) != 0) {
            bl2 = true;
        }
        typeface.setFakeBoldText(bl2);
        typeface = this.mTextPaint;
        float f2 = (n2 & 2) != 0 ? -0.25f : 0.0f;
        typeface.setTextSkewX(f2);
    }

    public void setTextOff(CharSequence charSequence) {
        this.mTextOff = charSequence;
        this.requestLayout();
    }

    public void setTextOn(CharSequence charSequence) {
        this.mTextOn = charSequence;
        this.requestLayout();
    }

    public void setThumbDrawable(Drawable drawable2) {
        this.mThumbDrawable = drawable2;
        this.requestLayout();
    }

    public void setThumbResource(int n2) {
        this.setThumbDrawable(this.mDrawableManager.getDrawable(this.getContext(), n2));
    }

    public void setThumbTextPadding(int n2) {
        this.mThumbTextPadding = n2;
        this.requestLayout();
    }

    public void setTrackDrawable(Drawable drawable2) {
        this.mTrackDrawable = drawable2;
        this.requestLayout();
    }

    public void setTrackResource(int n2) {
        this.setTrackDrawable(this.mDrawableManager.getDrawable(this.getContext(), n2));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void toggle() {
        boolean bl2 = !this.isChecked();
        this.setChecked(bl2);
    }

    protected boolean verifyDrawable(Drawable drawable2) {
        return super.verifyDrawable(drawable2) || drawable2 == this.mThumbDrawable || drawable2 == this.mTrackDrawable;
    }

    private class ThumbAnimation
    extends Animation {
        final float mDiff;
        final float mEndPosition;
        final float mStartPosition;

        private ThumbAnimation(float f2, float f3) {
            this.mStartPosition = f2;
            this.mEndPosition = f3;
            this.mDiff = f3 - f2;
        }

        protected void applyTransformation(float f2, Transformation transformation) {
            SwitchCompat.this.setThumbPosition(this.mStartPosition + this.mDiff * f2);
        }
    }
}

