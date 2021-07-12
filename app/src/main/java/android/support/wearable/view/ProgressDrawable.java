/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.animation.ObjectAnimator
 *  android.animation.TimeInterpolator
 *  android.annotation.TargetApi
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.util.Property
 *  android.view.animation.LinearInterpolator
 */
package android.support.wearable.view;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.wearable.view.Gusterpolator;
import android.util.Property;
import android.view.animation.LinearInterpolator;

@TargetApi(value=20)
class ProgressDrawable
extends Drawable {
    private static final long ANIMATION_DURATION = 6000L;
    private static final int CORRECTION_ANGLE = 54;
    private static final int FULL_CIRCLE = 360;
    private static final float GROW_SHRINK_RATIO = 0.5f;
    private static Property<ProgressDrawable, Integer> LEVEL = new Property<ProgressDrawable, Integer>(Integer.class, "level"){

        public Integer get(ProgressDrawable progressDrawable) {
            return progressDrawable.getLevel();
        }

        public void set(ProgressDrawable progressDrawable, Integer n2) {
            progressDrawable.setLevel(n2);
            progressDrawable.invalidateSelf();
        }
    };
    private static final int LEVELS_PER_SEGMENT = 2000;
    private static final int MAX_LEVEL = 10000;
    private static final int MAX_SWEEP = 306;
    private static final int NUMBER_OF_SEGMENTS = 5;
    private static final float STARTING_ANGLE = -90.0f;
    private static final TimeInterpolator mInterpolator = Gusterpolator.INSTANCE;
    private final ObjectAnimator mAnimator;
    private int mCircleBorderColor;
    private float mCircleBorderWidth;
    private final RectF mInnerCircleBounds = new RectF();
    private final Paint mPaint = new Paint();

    public ProgressDrawable() {
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mAnimator = ObjectAnimator.ofInt((Object)((Object)this), LEVEL, (int[])new int[]{0, 10000});
        this.mAnimator.setRepeatCount(-1);
        this.mAnimator.setRepeatMode(1);
        this.mAnimator.setDuration(6000L);
        this.mAnimator.setInterpolator((TimeInterpolator)new LinearInterpolator());
    }

    private static float lerpInv(float f2, float f3, float f4) {
        if (f2 != f3) {
            return (f4 - f2) / (f3 - f2);
        }
        return 0.0f;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void draw(Canvas canvas) {
        canvas.save();
        this.mInnerCircleBounds.set(this.getBounds());
        this.mInnerCircleBounds.inset(this.mCircleBorderWidth / 2.0f, this.mCircleBorderWidth / 2.0f);
        this.mPaint.setStrokeWidth(this.mCircleBorderWidth);
        this.mPaint.setColor(this.mCircleBorderColor);
        int n2 = this.getLevel();
        float f2 = (float)(n2 - n2 / 2000 * 2000) / 2000.0f;
        boolean bl2 = f2 < 0.5f;
        float f3 = bl2 ? 306.0f * mInterpolator.getInterpolation(ProgressDrawable.lerpInv(0.0f, 0.5f, f2)) : 306.0f * (1.0f - mInterpolator.getInterpolation(ProgressDrawable.lerpInv(0.5f, 1.0f, f2)));
        float f4 = Math.max(1.0f, f3);
        canvas.rotate((float)n2 * 1.0E-4f * 2.0f * 360.0f - 90.0f + 54.0f * f2, this.mInnerCircleBounds.centerX(), this.mInnerCircleBounds.centerY());
        RectF rectF = this.mInnerCircleBounds;
        f3 = bl2 ? 0.0f : 306.0f - f4;
        canvas.drawArc(rectF, f3, f4, false, this.mPaint);
        canvas.restore();
    }

    public int getOpacity() {
        return -1;
    }

    protected boolean onLevelChange(int n2) {
        return true;
    }

    public void setAlpha(int n2) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public void setRingColor(int n2) {
        this.mCircleBorderColor = n2;
    }

    public void setRingWidth(float f2) {
        this.mCircleBorderWidth = f2;
    }

    public void startAnimation() {
        this.mAnimator.start();
    }

    public void stopAnimation() {
        this.mAnimator.cancel();
    }
}

