/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.animation.ArgbEvaluator
 *  android.animation.TypeEvaluator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.RadialGradient
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 */
package android.support.wearable.view;

import android.animation.ArgbEvaluator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.wearable.R;
import android.support.wearable.view.ProgressDrawable;
import android.util.AttributeSet;
import android.view.View;
import java.util.Objects;

@TargetApi(value=21)
public class CircledImageView
extends View {
    private static final ArgbEvaluator ARGB_EVALUATOR = new ArgbEvaluator();
    private static final int SQUARE_DIMEN_HEIGHT = 1;
    private static final int SQUARE_DIMEN_NONE = 0;
    private static final int SQUARE_DIMEN_WIDTH = 2;
    private final ValueAnimator.AnimatorUpdateListener mAnimationListener;
    private int mCircleBorderColor;
    private float mCircleBorderWidth;
    private ColorStateList mCircleColor;
    private boolean mCircleHidden = false;
    private float mCircleRadius;
    private float mCircleRadiusPercent;
    private float mCircleRadiusPressed;
    private float mCircleRadiusPressedPercent;
    private ValueAnimator mColorAnimator;
    private long mColorChangeAnimationDurationMs = 0L;
    private int mCurrentColor;
    private Drawable mDrawable;
    private final Drawable.Callback mDrawableCallback;
    private float mImageCirclePercentage = 1.0f;
    private float mImageHorizontalOffcenterPercentage = 0.0f;
    private Integer mImageTint;
    private Rect mIndeterminateBounds = new Rect();
    private ProgressDrawable mIndeterminateDrawable;
    private float mInitialCircleRadius;
    private final RectF mOval;
    private final Paint mPaint;
    private boolean mPressed = false;
    private float mProgress = 1.0f;
    private boolean mProgressIndeterminate;
    private float mRadiusInset;
    private float mShadowVisibility;
    private final float mShadowWidth;
    private Integer mSquareDimen;

    public CircledImageView(Context context) {
        this(context, null);
    }

    public CircledImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CircledImageView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        float f2;
        this.mDrawableCallback = new Drawable.Callback(){

            public void invalidateDrawable(Drawable drawable2) {
                CircledImageView.this.invalidate();
            }

            public void scheduleDrawable(Drawable drawable2, Runnable runnable, long l2) {
            }

            public void unscheduleDrawable(Drawable drawable2, Runnable runnable) {
            }
        };
        this.mAnimationListener = new ValueAnimator.AnimatorUpdateListener(){

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int n2 = (Integer)valueAnimator.getAnimatedValue();
                if (n2 != CircledImageView.this.mCurrentColor) {
                    CircledImageView.access$002(CircledImageView.this, n2);
                    CircledImageView.this.invalidate();
                }
            }
        };
        context = this.getContext().obtainStyledAttributes(attributeSet, R.styleable.CircledImageView);
        this.mDrawable = context.getDrawable(R.styleable.CircledImageView_android_src);
        this.mCircleColor = context.getColorStateList(R.styleable.CircledImageView_circle_color);
        if (this.mCircleColor == null) {
            this.mCircleColor = ColorStateList.valueOf((int)0x1060000);
        }
        this.mInitialCircleRadius = this.mCircleRadius = context.getDimension(R.styleable.CircledImageView_circle_radius, 0.0f);
        this.mCircleRadiusPressed = context.getDimension(R.styleable.CircledImageView_circle_radius_pressed, this.mCircleRadius);
        this.mCircleBorderColor = context.getColor(R.styleable.CircledImageView_circle_border_color, -16777216);
        this.mCircleBorderWidth = context.getDimension(R.styleable.CircledImageView_circle_border_width, 0.0f);
        if (this.mCircleBorderWidth > 0.0f) {
            this.mRadiusInset += this.mCircleBorderWidth;
        }
        if ((f2 = context.getDimension(R.styleable.CircledImageView_circle_padding, 0.0f)) > 0.0f) {
            this.mRadiusInset += f2;
        }
        this.mShadowWidth = context.getDimension(R.styleable.CircledImageView_shadow_width, 0.0f);
        this.mImageCirclePercentage = context.getFloat(R.styleable.CircledImageView_image_circle_percentage, 0.0f);
        this.mImageHorizontalOffcenterPercentage = context.getFloat(R.styleable.CircledImageView_image_horizontal_offcenter_percentage, 0.0f);
        if (context.hasValue(R.styleable.CircledImageView_image_tint)) {
            this.mImageTint = context.getColor(R.styleable.CircledImageView_image_tint, 0);
        }
        if (context.hasValue(R.styleable.CircledImageView_square_dimen)) {
            this.mSquareDimen = context.getInt(R.styleable.CircledImageView_square_dimen, 0);
        }
        this.mCircleRadiusPercent = context.getFraction(R.styleable.CircledImageView_circle_radius_percent, 1, 1, 0.0f);
        this.mCircleRadiusPressedPercent = context.getFraction(R.styleable.CircledImageView_circle_radius_pressed_percent, 1, 1, this.mCircleRadiusPercent);
        context.recycle();
        this.mOval = new RectF();
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mIndeterminateDrawable = new ProgressDrawable();
        this.mIndeterminateDrawable.setCallback(this.mDrawableCallback);
        this.setWillNotDraw(false);
        this.setColorForCurrentState();
    }

    static /* synthetic */ int access$002(CircledImageView circledImageView, int n2) {
        circledImageView.mCurrentColor = n2;
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setColorForCurrentState() {
        int n2 = this.mCircleColor.getColorForState(this.getDrawableState(), this.mCircleColor.getDefaultColor());
        if (this.mColorChangeAnimationDurationMs > 0L) {
            if (this.mColorAnimator != null) {
                this.mColorAnimator.cancel();
            } else {
                this.mColorAnimator = new ValueAnimator();
            }
            this.mColorAnimator.setIntValues(new int[]{this.mCurrentColor, n2});
            this.mColorAnimator.setEvaluator((TypeEvaluator)ARGB_EVALUATOR);
            this.mColorAnimator.setDuration(this.mColorChangeAnimationDurationMs);
            this.mColorAnimator.addUpdateListener(this.mAnimationListener);
            this.mColorAnimator.start();
            return;
        } else {
            if (n2 == this.mCurrentColor) return;
            this.mCurrentColor = n2;
            this.invalidate();
            return;
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.setColorForCurrentState();
    }

    public ColorStateList getCircleColorStateList() {
        return this.mCircleColor;
    }

    public float getCircleRadius() {
        float f2;
        float f3 = f2 = this.mCircleRadius;
        if (this.mCircleRadius <= 0.0f) {
            f3 = f2;
            if (this.mCircleRadiusPercent > 0.0f) {
                f3 = (float)Math.max(this.getMeasuredHeight(), this.getMeasuredWidth()) * this.mCircleRadiusPercent;
            }
        }
        return f3 - this.mRadiusInset;
    }

    public float getCircleRadiusPercent() {
        return this.mCircleRadiusPercent;
    }

    public float getCircleRadiusPressed() {
        float f2;
        float f3 = f2 = this.mCircleRadiusPressed;
        if (this.mCircleRadiusPressed <= 0.0f) {
            f3 = f2;
            if (this.mCircleRadiusPressedPercent > 0.0f) {
                f3 = (float)Math.max(this.getMeasuredHeight(), this.getMeasuredWidth()) * this.mCircleRadiusPressedPercent;
            }
        }
        return f3 - this.mRadiusInset;
    }

    public float getCircleRadiusPressedPercent() {
        return this.mCircleRadiusPressedPercent;
    }

    public long getColorChangeAnimationDuration() {
        return this.mColorChangeAnimationDurationMs;
    }

    public int getDefaultCircleColor() {
        return this.mCircleColor.getDefaultColor();
    }

    public Drawable getImageDrawable() {
        return this.mDrawable;
    }

    public float getInitialCircleRadius() {
        return this.mInitialCircleRadius;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onDraw(Canvas canvas) {
        int n2 = this.getPaddingLeft();
        int n3 = this.getPaddingTop();
        float f2 = this.mPressed ? this.getCircleRadiusPressed() : this.getCircleRadius();
        if (this.mShadowWidth > 0.0f && this.mShadowVisibility > 0.0f) {
            this.mOval.set((float)n2, (float)n3, (float)(this.getWidth() - this.getPaddingRight()), (float)(this.getHeight() - this.getPaddingBottom()));
            float f3 = this.mCircleBorderWidth + f2 + this.mShadowWidth * this.mShadowVisibility;
            this.mPaint.setColor(-16777216);
            this.mPaint.setAlpha(Math.round((float)this.mPaint.getAlpha() * this.getAlpha()));
            this.mPaint.setStyle(Paint.Style.FILL);
            Paint paint = this.mPaint;
            float f4 = this.mOval.centerX();
            float f5 = this.mOval.centerY();
            Shader.TileMode tileMode = Shader.TileMode.MIRROR;
            paint.setShader((Shader)new RadialGradient(f4, f5, f3, new int[]{-16777216, 0}, new float[]{0.6f, 1.0f}, tileMode));
            canvas.drawCircle(this.mOval.centerX(), this.mOval.centerY(), f3, this.mPaint);
            this.mPaint.setShader(null);
        }
        if (this.mCircleBorderWidth > 0.0f) {
            this.mOval.set((float)n2, (float)n3, (float)(this.getWidth() - this.getPaddingRight()), (float)(this.getHeight() - this.getPaddingBottom()));
            this.mOval.set(this.mOval.centerX() - f2, this.mOval.centerY() - f2, this.mOval.centerX() + f2, this.mOval.centerY() + f2);
            this.mPaint.setColor(this.mCircleBorderColor);
            this.mPaint.setAlpha(Math.round((float)this.mPaint.getAlpha() * this.getAlpha()));
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setStrokeWidth(this.mCircleBorderWidth);
            if (this.mProgressIndeterminate) {
                this.mOval.roundOut(this.mIndeterminateBounds);
                this.mIndeterminateDrawable.setBounds(this.mIndeterminateBounds);
                this.mIndeterminateDrawable.setRingColor(this.mCircleBorderColor);
                this.mIndeterminateDrawable.setRingWidth(this.mCircleBorderWidth);
                this.mIndeterminateDrawable.draw(canvas);
            } else {
                canvas.drawArc(this.mOval, -90.0f, 360.0f * this.mProgress, false, this.mPaint);
            }
        }
        if (!this.mCircleHidden) {
            this.mOval.set((float)n2, (float)n3, (float)(this.getWidth() - this.getPaddingRight()), (float)(this.getHeight() - this.getPaddingBottom()));
            this.mPaint.setColor(this.mCurrentColor);
            this.mPaint.setAlpha(Math.round((float)this.mPaint.getAlpha() * this.getAlpha()));
            this.mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(this.mOval.centerX(), this.mOval.centerY(), f2, this.mPaint);
        }
        if (this.mDrawable != null) {
            this.mDrawable.setAlpha(Math.round(this.getAlpha() * 255.0f));
            if (this.mImageTint != null) {
                this.mDrawable.setTint(this.mImageTint.intValue());
            }
            this.mDrawable.draw(canvas);
        }
        super.onDraw(canvas);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        if (this.mDrawable != null) {
            int n6 = this.mDrawable.getIntrinsicWidth();
            int n7 = this.mDrawable.getIntrinsicHeight();
            int n8 = this.getMeasuredWidth();
            int n9 = this.getMeasuredHeight();
            float f2 = this.mImageCirclePercentage > 0.0f ? this.mImageCirclePercentage : 1.0f;
            float f3 = (float)n6 != 0.0f ? (float)n8 * f2 / (float)n6 : 1.0f;
            f2 = (float)n7 != 0.0f ? (float)n9 * f2 / (float)n7 : 1.0f;
            f3 = Math.min(1.0f, Math.min(f3, f2));
            n6 = Math.round((float)n6 * f3);
            n7 = Math.round((float)n7 * f3);
            n8 = (n8 - n6) / 2 + Math.round(this.mImageHorizontalOffcenterPercentage * (float)n6);
            n9 = (n9 - n7) / 2;
            this.mDrawable.setBounds(n8, n9, n8 + n6, n9 + n7);
        }
        super.onLayout(bl2, n2, n3, n4, n5);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n2, int n3) {
        float f2 = this.getCircleRadius() + this.mCircleBorderWidth + this.mShadowWidth * this.mShadowVisibility;
        float f3 = f2 * 2.0f;
        f2 *= 2.0f;
        int n4 = View.MeasureSpec.getMode((int)n2);
        n2 = View.MeasureSpec.getSize((int)n2);
        int n5 = View.MeasureSpec.getMode((int)n3);
        n3 = View.MeasureSpec.getSize((int)n3);
        if (n4 != 0x40000000) {
            n2 = n4 == Integer.MIN_VALUE ? (int)Math.min(f3, (float)n2) : (int)f3;
        }
        if (n5 != 0x40000000) {
            n3 = n5 == Integer.MIN_VALUE ? (int)Math.min(f2, (float)n3) : (int)f2;
        }
        n5 = n3;
        n4 = n2;
        if (this.mSquareDimen != null) {
            switch (this.mSquareDimen) {
                default: {
                    n4 = n2;
                    n5 = n3;
                    break;
                }
                case 1: {
                    n4 = n3;
                    n5 = n3;
                    break;
                }
                case 2: {
                    n5 = n2;
                    n4 = n2;
                }
            }
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec((int)n4, (int)0x40000000), View.MeasureSpec.makeMeasureSpec((int)n5, (int)0x40000000));
    }

    protected boolean onSetAlpha(int n2) {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onVisibilityChanged(View view, int n2) {
        super.onVisibilityChanged(view, n2);
        if (n2 != 0) {
            this.showIndeterminateProgress(false);
            return;
        } else {
            if (!this.mProgressIndeterminate) return;
            this.showIndeterminateProgress(true);
            return;
        }
    }

    public void setCircleBorderColor(int n2) {
        this.mCircleBorderColor = n2;
    }

    public void setCircleBorderWidth(float f2) {
        if (f2 != this.mCircleBorderWidth) {
            this.mCircleBorderWidth = f2;
            this.invalidate();
        }
    }

    public void setCircleColor(int n2) {
        this.setCircleColorStateList(ColorStateList.valueOf((int)n2));
    }

    public void setCircleColorStateList(ColorStateList colorStateList) {
        if (!Objects.equals(colorStateList, this.mCircleColor)) {
            this.mCircleColor = colorStateList;
            this.setColorForCurrentState();
            this.invalidate();
        }
    }

    public void setCircleHidden(boolean bl2) {
        if (bl2 != this.mCircleHidden) {
            this.mCircleHidden = bl2;
            this.invalidate();
        }
    }

    public void setCircleRadius(float f2) {
        if (f2 != this.mCircleRadius) {
            this.mCircleRadius = f2;
            this.invalidate();
        }
    }

    public void setCircleRadiusPercent(float f2) {
        if (f2 != this.mCircleRadiusPercent) {
            this.mCircleRadiusPercent = f2;
            this.invalidate();
        }
    }

    public void setCircleRadiusPressed(float f2) {
        if (f2 != this.mCircleRadiusPressed) {
            this.mCircleRadiusPressed = f2;
            this.invalidate();
        }
    }

    public void setCircleRadiusPressedPercent(float f2) {
        if (f2 != this.mCircleRadiusPressedPercent) {
            this.mCircleRadiusPressedPercent = f2;
            this.invalidate();
        }
    }

    public void setColorChangeAnimationDuration(long l2) {
        this.mColorChangeAnimationDurationMs = l2;
    }

    public void setImageCirclePercentage(float f2) {
        if ((f2 = Math.max(0.0f, Math.min(1.0f, f2))) != this.mImageCirclePercentage) {
            this.mImageCirclePercentage = f2;
            this.invalidate();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setImageDrawable(Drawable drawable2) {
        if (drawable2 != this.mDrawable) {
            Drawable drawable3 = this.mDrawable;
            this.mDrawable = drawable2;
            boolean bl2 = drawable2 != null && drawable3 != null && drawable3.getIntrinsicHeight() == drawable2.getIntrinsicHeight() && drawable3.getIntrinsicWidth() == drawable2.getIntrinsicWidth();
            if (bl2) {
                this.mDrawable.setBounds(drawable3.getBounds());
            } else {
                this.requestLayout();
            }
            this.invalidate();
        }
    }

    public void setImageHorizontalOffcenterPercentage(float f2) {
        if (f2 != this.mImageHorizontalOffcenterPercentage) {
            this.mImageHorizontalOffcenterPercentage = f2;
            this.invalidate();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setImageResource(int n2) {
        Drawable drawable2 = n2 == 0 ? null : this.getContext().getDrawable(n2);
        this.setImageDrawable(drawable2);
    }

    public void setImageTint(int n2) {
        if (this.mImageTint == null || n2 != this.mImageTint) {
            this.mImageTint = n2;
            this.invalidate();
        }
    }

    public void setPressed(boolean bl2) {
        super.setPressed(bl2);
        if (bl2 != this.mPressed) {
            this.mPressed = bl2;
            this.invalidate();
        }
    }

    public void setProgress(float f2) {
        if (f2 != this.mProgress) {
            this.mProgress = f2;
            this.invalidate();
        }
    }

    public void setShadowVisibility(float f2) {
        if (f2 != this.mShadowVisibility) {
            this.mShadowVisibility = f2;
            this.invalidate();
        }
    }

    public void showIndeterminateProgress(boolean bl2) {
        block3: {
            block2: {
                this.mProgressIndeterminate = bl2;
                if (this.mIndeterminateDrawable == null) break block2;
                if (!bl2) break block3;
                this.mIndeterminateDrawable.startAnimation();
            }
            return;
        }
        this.mIndeterminateDrawable.stopAnimation();
    }
}

