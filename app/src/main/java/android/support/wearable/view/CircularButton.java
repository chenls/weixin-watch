/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.ObjectAnimator
 *  android.animation.StateListAnimator
 *  android.animation.TimeInterpolator
 *  android.annotation.SuppressLint
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Canvas
 *  android.graphics.Outline
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.graphics.drawable.RippleDrawable
 *  android.graphics.drawable.ShapeDrawable
 *  android.graphics.drawable.shapes.OvalShape
 *  android.graphics.drawable.shapes.Shape
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewOutlineProvider
 *  android.view.animation.AccelerateInterpolator
 *  android.view.animation.Interpolator
 */
package android.support.wearable.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.DrawableRes;
import android.support.wearable.R;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

@SuppressLint(value={"ClickableViewAccessibility"})
@TargetApi(value=21)
public class CircularButton
extends View {
    private static final float DEFAULT_ICON_SIZE_DP = 48.0f;
    public static final int SCALE_MODE_CENTER = 1;
    public static final int SCALE_MODE_FIT = 0;
    private static final double SQRT_2 = Math.sqrt(2.0);
    private ColorStateList mColors;
    private int mDiameter;
    private Drawable mImage;
    private Interpolator mInterpolator;
    private int mRippleColor = -1;
    private RippleDrawable mRippleDrawable;
    private int mScaleMode;
    private ShapeDrawable mShapeDrawable = new ShapeDrawable((Shape)new OvalShape());

    public CircularButton(Context context) {
        this(context, null);
    }

    public CircularButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CircularButton(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    public CircularButton(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
        this.mShapeDrawable.getPaint().setColor(-3355444);
        super.setBackgroundDrawable((Drawable)this.mShapeDrawable);
        this.setOutlineProvider(new CircleOutlineProvider());
        this.mInterpolator = new AccelerateInterpolator(2.0f);
        this.mScaleMode = 0;
        context = context.obtainStyledAttributes(attributeSet, R.styleable.CircularButton, n2, n3);
        n2 = 0;
        while (true) {
            if (n2 >= context.getIndexCount()) {
                context.recycle();
                this.setClickable(true);
                return;
            }
            n3 = context.getIndex(n2);
            if (n3 == R.styleable.CircularButton_android_color) {
                this.mColors = context.getColorStateList(n3);
                this.mShapeDrawable.getPaint().setColor(this.mColors.getDefaultColor());
            } else if (n3 == R.styleable.CircularButton_android_src) {
                this.mImage = context.getDrawable(n3);
            } else if (n3 == R.styleable.CircularButton_buttonRippleColor) {
                this.setRippleColor(context.getColor(n3, -1));
            } else if (n3 == R.styleable.CircularButton_pressedButtonTranslationZ) {
                this.setPressedTranslationZ(context.getDimension(n3, 0.0f));
            } else if (n3 == R.styleable.CircularButton_imageScaleMode) {
                this.mScaleMode = context.getInt(n3, this.mScaleMode);
            }
            ++n2;
        }
    }

    private int dpToPx(float f2) {
        return (int)Math.ceil(TypedValue.applyDimension((int)1, (float)f2, (DisplayMetrics)this.getResources().getDisplayMetrics()));
    }

    private static int encircledRadius(int n2) {
        return (int)Math.floor((double)n2 / SQRT_2);
    }

    private static boolean hasIntrinsicSize(Drawable drawable2) {
        return drawable2 != null && drawable2.getIntrinsicHeight() > 0 && drawable2.getIntrinsicWidth() > 0;
    }

    private static int inscribedSize(int n2) {
        return (int)Math.floor((double)n2 * SQRT_2);
    }

    private Animator setupAnimator(Animator animator2) {
        animator2.setInterpolator((TimeInterpolator)this.mInterpolator);
        return animator2;
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mColors != null && this.mColors.isStateful()) {
            this.mShapeDrawable.getPaint().setColor(this.mColors.getColorForState(this.getDrawableState(), this.mColors.getDefaultColor()));
            this.mShapeDrawable.invalidateSelf();
        }
    }

    public Drawable getImageDrawable() {
        return this.mImage;
    }

    public int getImageScaleMode() {
        return this.mScaleMode;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mImage != null) {
            this.mImage.draw(canvas);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        int n6;
        int n7;
        block9: {
            block8: {
                super.onLayout(bl2, n2, n3, n4, n5);
                if (this.mImage == null) break block8;
                n7 = this.mImage.getIntrinsicWidth();
                n6 = this.mImage.getIntrinsicHeight();
                if (this.mScaleMode != 0 && CircularButton.hasIntrinsicSize(this.mImage)) {
                    n2 = (int)((float)(n4 - n2 - n7) / 2.0f);
                    n3 = (int)((float)(n5 - n3 - n6) / 2.0f);
                    this.mImage.setBounds(n2, n3, n2 + n7, n3 + n6);
                    return;
                }
                n2 = CircularButton.inscribedSize(this.mDiameter / 2);
                n5 = n4 = (this.mDiameter - n2) / 2;
                if (CircularButton.hasIntrinsicSize(this.mImage)) break block9;
                this.mImage.setBounds(n5, n4, n5 + n2, n4 + n2);
            }
            return;
        }
        if (n7 == n6) {
            n7 = n2;
            n3 = n2;
            n2 = n7;
        } else {
            float f2 = (float)n7 / (float)n6;
            if (n7 > n6) {
                n3 = n2;
                n7 = (int)((float)n3 / f2);
                n4 = (int)((float)(n2 - n7) / 2.0f);
                n2 = n7;
            } else {
                n3 = n2;
                n7 = (int)((float)n3 * f2);
                n5 = (int)((float)(n2 - n7) / 2.0f);
                n2 = n3;
                n3 = n7;
            }
        }
        this.mImage.setBounds(n5, n4, n5 + n3, n4 + n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n2, int n3) {
        int n4 = View.MeasureSpec.getMode((int)n2);
        n2 = View.MeasureSpec.getSize((int)n2);
        int n5 = View.MeasureSpec.getMode((int)n3);
        int n6 = View.MeasureSpec.getSize((int)n3);
        if (n4 == 0x40000000 && n5 == 0x40000000) {
            this.mDiameter = Math.min(n2, n6);
        } else if (n4 == 0x40000000) {
            this.mDiameter = n2;
        } else if (n5 == 0x40000000) {
            this.mDiameter = n6;
        } else {
            n3 = CircularButton.hasIntrinsicSize(this.mImage) ? Math.max(this.mImage.getIntrinsicHeight(), this.mImage.getIntrinsicWidth()) : this.dpToPx(48.0f);
            if (n4 == Integer.MIN_VALUE || n5 == Integer.MIN_VALUE) {
                if (n4 != Integer.MIN_VALUE) {
                    n2 = n6;
                } else if (n5 == Integer.MIN_VALUE) {
                    n2 = Math.min(n2, n6);
                }
                this.mDiameter = Math.min(n2, CircularButton.encircledRadius(n3) * 2);
            } else {
                this.mDiameter = n3;
            }
        }
        this.setMeasuredDimension(this.mDiameter, this.mDiameter);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean bl2 = super.onTouchEvent(motionEvent);
        if (!bl2) return bl2;
        switch (motionEvent.getAction() & 0xFF) {
            default: {
                return bl2;
            }
            case 0: 
        }
        this.getBackground().setHotspot(motionEvent.getX(), motionEvent.getY());
        return bl2;
    }

    public void setBackgroundDrawable(Drawable drawable2) {
    }

    public void setColor(int n2) {
        this.mColors = ColorStateList.valueOf((int)n2);
        this.mShapeDrawable.getPaint().setColor(this.mColors.getDefaultColor());
    }

    public void setColor(ColorStateList colorStateList) {
        this.mColors = colorStateList;
        this.mShapeDrawable.getPaint().setColor(this.mColors.getDefaultColor());
    }

    public void setImageDrawable(Drawable drawable2) {
        if (this.mImage != null) {
            this.mImage.setCallback(null);
        }
        if (this.mImage != drawable2) {
            this.mImage = drawable2;
            this.requestLayout();
            this.invalidate();
        }
        if (this.mImage != null) {
            this.mImage.setCallback((Drawable.Callback)this);
        }
    }

    public void setImageResource(@DrawableRes int n2) {
        this.setImageDrawable(this.getResources().getDrawable(n2, null));
    }

    public void setImageScaleMode(int n2) {
        this.mScaleMode = n2;
        if (this.mImage != null) {
            this.invalidate();
            this.requestLayout();
        }
    }

    public void setPressedTranslationZ(float f2) {
        StateListAnimator stateListAnimator = new StateListAnimator();
        stateListAnimator.addState(PRESSED_ENABLED_STATE_SET, this.setupAnimator((Animator)ObjectAnimator.ofFloat((Object)((Object)this), (String)"translationZ", (float[])new float[]{f2})));
        stateListAnimator.addState(ENABLED_FOCUSED_STATE_SET, this.setupAnimator((Animator)ObjectAnimator.ofFloat((Object)((Object)this), (String)"translationZ", (float[])new float[]{f2})));
        stateListAnimator.addState(EMPTY_STATE_SET, this.setupAnimator((Animator)ObjectAnimator.ofFloat((Object)((Object)this), (String)"translationZ", (float[])new float[]{this.getElevation()})));
        this.setStateListAnimator(stateListAnimator);
    }

    public void setRippleColor(int n2) {
        this.mRippleColor = n2;
        if (this.mRippleDrawable != null) {
            this.mRippleDrawable.setColor(ColorStateList.valueOf((int)n2));
            return;
        }
        if (this.mRippleColor != -1 && !this.isInEditMode()) {
            this.mRippleDrawable = new RippleDrawable(ColorStateList.valueOf((int)n2), (Drawable)this.mShapeDrawable, (Drawable)this.mShapeDrawable);
            super.setBackgroundDrawable((Drawable)this.mRippleDrawable);
            return;
        }
        this.mRippleDrawable = null;
        super.setBackgroundDrawable((Drawable)this.mShapeDrawable);
    }

    protected boolean verifyDrawable(Drawable drawable2) {
        return this.mImage == drawable2 || super.verifyDrawable(drawable2);
    }

    private class CircleOutlineProvider
    extends ViewOutlineProvider {
        private CircleOutlineProvider() {
        }

        public void getOutline(View view, Outline outline) {
            outline.setOval(0, 0, CircularButton.this.mDiameter, CircularButton.this.mDiameter);
        }
    }
}

