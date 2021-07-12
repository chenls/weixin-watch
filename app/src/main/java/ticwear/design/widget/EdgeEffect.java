/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.util.FloatMath
 *  android.view.animation.AnimationUtils
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 */
package ticwear.design.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.FloatMath;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import ticwear.design.R;

public abstract class EdgeEffect {
    private static final float EPSILON = 0.001f;
    private static final float MAX_ALPHA = 1.0f;
    private static final float MAX_GLOW_SCALE = 2.0f;
    private static final int MAX_VELOCITY = 10000;
    private static final int MIN_VELOCITY = 100;
    private static final int PULL_DECAY_TIME = 2000;
    private static final float PULL_DISTANCE_ALPHA_GLOW_FACTOR = 0.8f;
    private static final float PULL_GLOW_BEGIN = 0.0f;
    private static final int PULL_TIME = 167;
    private static final int RECEDE_TIME = 600;
    private static final int STATE_ABSORB = 2;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PULL = 1;
    private static final int STATE_PULL_DECAY = 4;
    private static final int STATE_RECEDE = 3;
    private static final String TAG = "ClassicEdgeEffect";
    private static final int VELOCITY_GLOW_FACTOR = 6;
    private final Rect mBounds = new Rect();
    private float mDisplacement = 0.5f;
    private float mDuration;
    private float mGlowAlpha;
    private float mGlowAlphaFinish;
    private float mGlowAlphaStart;
    private int mGlowColor;
    private float mGlowScaleY;
    private float mGlowScaleYFinish;
    private float mGlowScaleYStart;
    private final Interpolator mInterpolator;
    private float mPullDistance;
    private long mStartTime;
    private int mState = 0;
    private float mTargetDisplacement = 0.5f;

    public EdgeEffect(Context context) {
        context = context.obtainStyledAttributes(R.styleable.EdgeEffect);
        int n2 = context.getColor(R.styleable.EdgeEffect_android_colorEdgeEffect, -10066330);
        context.recycle();
        this.mGlowColor = n2;
        this.mInterpolator = new DecelerateInterpolator();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void update() {
        float f2 = Math.min((float)(AnimationUtils.currentAnimationTimeMillis() - this.mStartTime) / this.mDuration, 1.0f);
        float f3 = this.mInterpolator.getInterpolation(f2);
        this.mGlowAlpha = this.mGlowAlphaStart + (this.mGlowAlphaFinish - this.mGlowAlphaStart) * f3;
        this.mGlowScaleY = this.mGlowScaleYStart + (this.mGlowScaleYFinish - this.mGlowScaleYStart) * f3;
        this.mDisplacement = (this.mDisplacement + this.mTargetDisplacement) / 2.0f;
        if (!(f2 >= 0.999f)) return;
        switch (this.mState) {
            default: {
                return;
            }
            case 2: {
                this.mState = 3;
                this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
                this.mDuration = 600.0f;
                this.mGlowAlphaStart = this.mGlowAlpha;
                this.mGlowScaleYStart = this.mGlowScaleY;
                this.mGlowAlphaFinish = 0.0f;
                this.mGlowScaleYFinish = 0.0f;
                return;
            }
            case 1: {
                this.mState = 4;
                this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
                this.mDuration = 2000.0f;
                this.mGlowAlphaStart = this.mGlowAlpha;
                this.mGlowScaleYStart = this.mGlowScaleY;
                this.mGlowAlphaFinish = 0.0f;
                this.mGlowScaleYFinish = 0.0f;
                return;
            }
            case 4: {
                this.mState = 3;
                return;
            }
            case 3: 
        }
        this.mState = 0;
    }

    public boolean draw(Canvas canvas) {
        boolean bl2;
        boolean bl3 = false;
        this.update();
        this.onDraw(canvas);
        boolean bl4 = bl2 = false;
        if (this.mState == 3) {
            bl4 = bl2;
            if (this.mGlowScaleY == 0.0f) {
                this.mState = 0;
                bl4 = true;
            }
        }
        if (this.mState != 0 || bl4) {
            bl3 = true;
        }
        return bl3;
    }

    public void finish() {
        this.mState = 0;
    }

    protected Rect getBounds() {
        return this.mBounds;
    }

    public int getColor() {
        return this.mGlowColor;
    }

    protected float getDisplacement() {
        return this.mDisplacement;
    }

    protected float getGlowAlpha() {
        return this.mGlowAlpha;
    }

    protected float getGlowScaleY() {
        return this.mGlowScaleY;
    }

    public int getMaxHeight() {
        return (int)((float)this.mBounds.height() * 2.0f + 0.5f);
    }

    public boolean isFinished() {
        return this.mState == 0;
    }

    public void onAbsorb(int n2) {
        this.mState = 2;
        n2 = Math.min(Math.max(100, Math.abs(n2)), 10000);
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.mDuration = 0.15f + (float)n2 * 0.02f;
        this.mGlowAlphaStart = 0.3f;
        this.mGlowScaleYStart = Math.max(this.mGlowScaleY, 0.0f);
        this.mGlowScaleYFinish = Math.min(0.025f + (float)(n2 / 100 * n2) * 1.5E-4f / 2.0f, 1.0f);
        this.mGlowAlphaFinish = Math.max(this.mGlowAlphaStart, Math.min((float)(n2 * 6) * 1.0E-5f, 1.0f));
        this.mTargetDisplacement = 0.5f;
    }

    public abstract void onDraw(Canvas var1);

    public void onPull(float f2) {
        this.onPull(f2, 0.5f);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onPull(float f2, float f3) {
        long l2 = AnimationUtils.currentAnimationTimeMillis();
        this.mTargetDisplacement = f3;
        if (this.mState == 4 && (float)(l2 - this.mStartTime) < this.mDuration) {
            return;
        }
        if (this.mState != 1) {
            this.mGlowScaleY = Math.max(0.0f, this.mGlowScaleY);
        }
        this.mState = 1;
        this.mStartTime = l2;
        this.mDuration = 167.0f;
        this.mPullDistance += f2;
        f2 = Math.abs(f2);
        this.mGlowAlphaStart = f2 = Math.min(1.0f, this.mGlowAlpha + 0.8f * f2);
        this.mGlowAlpha = f2;
        if (this.mPullDistance == 0.0f) {
            this.mGlowScaleYStart = 0.0f;
            this.mGlowScaleY = 0.0f;
        } else {
            this.mGlowScaleYStart = f2 = Math.max(0.0f, 1.0f - 1.0f / FloatMath.sqrt((float)(Math.abs(this.mPullDistance) * (float)this.mBounds.height())) - 0.3f) / 0.7f;
            this.mGlowScaleY = f2;
        }
        this.mGlowAlphaFinish = this.mGlowAlpha;
        this.mGlowScaleYFinish = this.mGlowScaleY;
    }

    public void onRelease() {
        this.mPullDistance = 0.0f;
        if (this.mState != 1 && this.mState != 4) {
            return;
        }
        this.mState = 3;
        this.mGlowAlphaStart = this.mGlowAlpha;
        this.mGlowScaleYStart = this.mGlowScaleY;
        this.mGlowAlphaFinish = 0.0f;
        this.mGlowScaleYFinish = 0.0f;
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.mDuration = 600.0f;
    }

    public void setColor(int n2) {
        this.mGlowColor = n2;
    }

    public void setSize(int n2, int n3) {
        this.mBounds.set(0, 0, n2, n3);
    }
}

