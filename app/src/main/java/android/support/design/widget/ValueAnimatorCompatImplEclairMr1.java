/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.SystemClock
 *  android.view.animation.AccelerateDecelerateInterpolator
 *  android.view.animation.Interpolator
 */
package android.support.design.widget;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.design.widget.AnimationUtils;
import android.support.design.widget.ValueAnimatorCompat;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

class ValueAnimatorCompatImplEclairMr1
extends ValueAnimatorCompat.Impl {
    private static final int DEFAULT_DURATION = 200;
    private static final int HANDLER_DELAY = 10;
    private static final Handler sHandler = new Handler(Looper.getMainLooper());
    private float mAnimatedFraction;
    private int mDuration = 200;
    private final float[] mFloatValues;
    private final int[] mIntValues = new int[2];
    private Interpolator mInterpolator;
    private boolean mIsRunning;
    private ValueAnimatorCompat.Impl.AnimatorListenerProxy mListener;
    private final Runnable mRunnable;
    private long mStartTime;
    private ValueAnimatorCompat.Impl.AnimatorUpdateListenerProxy mUpdateListener;

    ValueAnimatorCompatImplEclairMr1() {
        this.mFloatValues = new float[2];
        this.mRunnable = new Runnable(){

            @Override
            public void run() {
                ValueAnimatorCompatImplEclairMr1.this.update();
            }
        };
    }

    private void update() {
        if (this.mIsRunning) {
            float f2;
            float f3 = f2 = (float)(SystemClock.uptimeMillis() - this.mStartTime) / (float)this.mDuration;
            if (this.mInterpolator != null) {
                f3 = this.mInterpolator.getInterpolation(f2);
            }
            this.mAnimatedFraction = f3;
            if (this.mUpdateListener != null) {
                this.mUpdateListener.onAnimationUpdate();
            }
            if (SystemClock.uptimeMillis() >= this.mStartTime + (long)this.mDuration) {
                this.mIsRunning = false;
                if (this.mListener != null) {
                    this.mListener.onAnimationEnd();
                }
            }
        }
        if (this.mIsRunning) {
            sHandler.postDelayed(this.mRunnable, 10L);
        }
    }

    @Override
    public void cancel() {
        this.mIsRunning = false;
        sHandler.removeCallbacks(this.mRunnable);
        if (this.mListener != null) {
            this.mListener.onAnimationCancel();
        }
    }

    @Override
    public void end() {
        if (this.mIsRunning) {
            this.mIsRunning = false;
            sHandler.removeCallbacks(this.mRunnable);
            this.mAnimatedFraction = 1.0f;
            if (this.mUpdateListener != null) {
                this.mUpdateListener.onAnimationUpdate();
            }
            if (this.mListener != null) {
                this.mListener.onAnimationEnd();
            }
        }
    }

    @Override
    public float getAnimatedFloatValue() {
        return AnimationUtils.lerp(this.mFloatValues[0], this.mFloatValues[1], this.getAnimatedFraction());
    }

    @Override
    public float getAnimatedFraction() {
        return this.mAnimatedFraction;
    }

    @Override
    public int getAnimatedIntValue() {
        return AnimationUtils.lerp(this.mIntValues[0], this.mIntValues[1], this.getAnimatedFraction());
    }

    @Override
    public long getDuration() {
        return this.mDuration;
    }

    @Override
    public boolean isRunning() {
        return this.mIsRunning;
    }

    @Override
    public void setDuration(int n2) {
        this.mDuration = n2;
    }

    @Override
    public void setFloatValues(float f2, float f3) {
        this.mFloatValues[0] = f2;
        this.mFloatValues[1] = f3;
    }

    @Override
    public void setIntValues(int n2, int n3) {
        this.mIntValues[0] = n2;
        this.mIntValues[1] = n3;
    }

    @Override
    public void setInterpolator(Interpolator interpolator2) {
        this.mInterpolator = interpolator2;
    }

    @Override
    public void setListener(ValueAnimatorCompat.Impl.AnimatorListenerProxy animatorListenerProxy) {
        this.mListener = animatorListenerProxy;
    }

    @Override
    public void setUpdateListener(ValueAnimatorCompat.Impl.AnimatorUpdateListenerProxy animatorUpdateListenerProxy) {
        this.mUpdateListener = animatorUpdateListenerProxy;
    }

    @Override
    public void start() {
        if (this.mIsRunning) {
            return;
        }
        if (this.mInterpolator == null) {
            this.mInterpolator = new AccelerateDecelerateInterpolator();
        }
        this.mStartTime = SystemClock.uptimeMillis();
        this.mIsRunning = true;
        if (this.mListener != null) {
            this.mListener.onAnimationStart();
        }
        sHandler.postDelayed(this.mRunnable, 10L);
    }
}

