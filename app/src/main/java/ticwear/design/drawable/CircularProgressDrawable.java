/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.Paint$Cap
 *  android.graphics.Paint$Join
 *  android.graphics.Paint$Style
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.drawable.Animatable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.os.SystemClock
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 */
package ticwear.design.drawable;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import ticwear.design.R;
import ticwear.design.utils.ColorUtils;

public class CircularProgressDrawable
extends Drawable
implements Animatable {
    public static final long FRAME_DURATION = 16L;
    public static final int MODE_DETERMINATE = 0;
    public static final int MODE_INDETERMINATE = 1;
    private static final int PROGRESS_STATE_HIDE = -1;
    private static final int PROGRESS_STATE_KEEP_SHRINK = 3;
    private static final int PROGRESS_STATE_KEEP_STRETCH = 1;
    private static final int PROGRESS_STATE_SHRINK = 2;
    private static final int PROGRESS_STATE_STRETCH = 0;
    private static final int RUN_STATE_RUNNING = 3;
    private static final int RUN_STATE_STARTED = 2;
    private static final int RUN_STATE_STARTING = 1;
    private static final int RUN_STATE_STOPPED = 0;
    private static final int RUN_STATE_STOPPING = 4;
    private int mAlpha;
    private int mInAnimationDuration;
    private int[] mInColors;
    private float mInStepPercent;
    private float mInitialAngle;
    private int mKeepDuration;
    private long mLastProgressStateTime;
    private long mLastRunStateTime;
    private long mLastUpdateTime;
    private float mMaxSweepAngle;
    private float mMinSweepAngle;
    private boolean mMutated = false;
    private int mOutAnimationDuration;
    private int mPadding;
    private Paint mPaint;
    private int mProgressMode;
    private float mProgressPercent;
    private int mProgressState;
    private RectF mRect;
    private boolean mReverse;
    private int mRotateDuration;
    private int mRunState = 0;
    private float mSecondaryProgressPercent;
    private float mStartAngle;
    private ProgressState mState;
    private int mStrokeColorIndex;
    private int[] mStrokeColors;
    private int mStrokeSecondaryColor;
    private int mStrokeSize;
    private float mSweepAngle;
    private PorterDuffColorFilter mTintFilter;
    private int mTransformDuration;
    private Interpolator mTransformInterpolator;
    private final Runnable mUpdater = new Runnable(){

        @Override
        public void run() {
            CircularProgressDrawable.this.update();
        }
    };

    private CircularProgressDrawable(int n2, float f2, float f3, float f4, float f5, float f6, int n3, int[] nArray, int n4, boolean bl2, int n5, int n6, int n7, Interpolator interpolator2, int n8, int n9, float f7, int[] nArray2, int n10, int n11) {
        this.mPadding = n2;
        this.mInitialAngle = f2;
        this.mAlpha = n11;
        this.mProgressPercent = Math.min(1.0f, Math.max(0.0f, f3));
        this.setSecondaryProgress(f4);
        this.mMaxSweepAngle = f5;
        this.mMinSweepAngle = f6;
        this.mStrokeSize = n3;
        this.mStrokeColors = nArray;
        this.mStrokeSecondaryColor = n4;
        this.mReverse = bl2;
        this.mRotateDuration = n5;
        this.mTransformDuration = n6;
        this.mKeepDuration = n7;
        this.mTransformInterpolator = interpolator2;
        this.mProgressMode = n8;
        this.mInAnimationDuration = n9;
        this.mInStepPercent = f7;
        this.mInColors = nArray2;
        this.mOutAnimationDuration = n10;
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStrokeCap(Paint.Cap.BUTT);
        this.mPaint.setStrokeJoin(Paint.Join.ROUND);
        this.mRect = new RectF();
        this.mState = this.createConstantState(null, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void drawDeterminate(Canvas canvas) {
        float f2;
        float f3;
        float f4;
        float f5;
        block14: {
            block13: {
                Rect rect = this.getBounds();
                f5 = 0.0f;
                f4 = 0.0f;
                if (this.mRunState == 1) {
                    f4 = f3 = (float)this.mStrokeSize * (float)Math.min((long)this.mInAnimationDuration, SystemClock.uptimeMillis() - this.mLastRunStateTime) / (float)this.mInAnimationDuration;
                    if (f3 > 0.0f) {
                        f5 = ((float)(Math.min(rect.width(), rect.height()) - this.mPadding * 2 - this.mStrokeSize * 2) + f3) / 2.0f;
                        f4 = f3;
                    }
                } else if (this.mRunState == 4) {
                    f4 = f3 = (float)this.mStrokeSize * (float)Math.max(0L, (long)this.mOutAnimationDuration - SystemClock.uptimeMillis() + this.mLastRunStateTime) / (float)this.mOutAnimationDuration;
                    if (f3 > 0.0f) {
                        f5 = ((float)(Math.min(rect.width(), rect.height()) - this.mPadding * 2 - this.mStrokeSize * 2) + f3) / 2.0f;
                        f4 = f3;
                    }
                } else if (this.mRunState != 0) {
                    f4 = this.mStrokeSize;
                    f5 = (float)(Math.min(rect.width(), rect.height()) - this.mPadding * 2 - this.mStrokeSize) / 2.0f;
                }
                if (!(f5 > 0.0f)) break block13;
                f3 = (float)(rect.left + rect.right) / 2.0f;
                f2 = (float)(rect.top + rect.bottom) / 2.0f;
                this.mPaint.setStrokeWidth(f4);
                this.mPaint.setStyle(Paint.Style.STROKE);
                if (this.mProgressPercent != 1.0f) break block14;
                this.mPaint.setColor(this.mStrokeColors[0]);
                this.mPaint.setAlpha(this.mAlpha);
                canvas.drawCircle(f3, f2, f5, this.mPaint);
            }
            return;
        }
        if (this.mProgressPercent == 0.0f) {
            this.mPaint.setColor(this.mStrokeSecondaryColor);
            canvas.drawCircle(f3, f2, f5, this.mPaint);
            return;
        }
        int n2 = this.mReverse ? -360 : 360;
        f4 = (float)n2 * this.mProgressPercent;
        n2 = this.mReverse ? -360 : 360;
        float f6 = n2;
        float f7 = Math.max(this.mSecondaryProgressPercent - this.mProgressPercent, 0.0f);
        this.mRect.set(f3 - f5, f2 - f5, f3 + f5, f2 + f5);
        this.mPaint.setColor(this.mStrokeSecondaryColor);
        canvas.drawArc(this.mRect, this.mStartAngle + f4, f6 * f7, false, this.mPaint);
        this.mPaint.setColor(this.mStrokeColors[0]);
        this.mPaint.setAlpha(this.mAlpha);
        canvas.drawArc(this.mRect, this.mInitialAngle, f4, false, this.mPaint);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void drawIndeterminate(Canvas canvas) {
        if (this.mRunState != 1) {
            if (this.mRunState == 4) {
                float f2 = (float)this.mStrokeSize * (float)Math.max(0L, (long)this.mOutAnimationDuration - SystemClock.uptimeMillis() + this.mLastRunStateTime) / (float)this.mOutAnimationDuration;
                if (!(f2 > 0.0f)) return;
                Rect rect = this.getBounds();
                float f3 = ((float)(Math.min(rect.width(), rect.height()) - this.mPadding * 2 - this.mStrokeSize * 2) + f2) / 2.0f;
                float f4 = (float)(rect.left + rect.right) / 2.0f;
                float f5 = (float)(rect.top + rect.bottom) / 2.0f;
                this.mRect.set(f4 - f3, f5 - f3, f4 + f3, f5 + f3);
                this.mPaint.setStrokeWidth(f2);
                this.mPaint.setStyle(Paint.Style.STROKE);
                this.mPaint.setColor(this.getIndeterminateStrokeColor());
                canvas.drawArc(this.mRect, this.mStartAngle, this.mSweepAngle, false, this.mPaint);
                return;
            } else {
                if (this.mRunState == 0) return;
                Rect rect = this.getBounds();
                float f6 = (float)(Math.min(rect.width(), rect.height()) - this.mPadding * 2 - this.mStrokeSize) / 2.0f;
                float f7 = (float)(rect.left + rect.right) / 2.0f;
                float f8 = (float)(rect.top + rect.bottom) / 2.0f;
                this.mRect.set(f7 - f6, f8 - f6, f7 + f6, f8 + f6);
                this.mPaint.setStrokeWidth((float)this.mStrokeSize);
                this.mPaint.setStyle(Paint.Style.STROKE);
                this.mPaint.setColor(this.getIndeterminateStrokeColor());
                this.mPaint.setAlpha(this.mAlpha);
                canvas.drawArc(this.mRect, this.mStartAngle, this.mSweepAngle, false, this.mPaint);
                return;
            }
        }
        Rect rect = this.getBounds();
        float f9 = (float)(rect.left + rect.right) / 2.0f;
        float f10 = (float)(rect.top + rect.bottom) / 2.0f;
        float f11 = (float)(Math.min(rect.width(), rect.height()) - this.mPadding * 2) / 2.0f;
        float f12 = 1.0f / (this.mInStepPercent * (float)(this.mInColors.length + 2) + 1.0f);
        float f13 = (float)(SystemClock.uptimeMillis() - this.mLastRunStateTime) / (float)this.mInAnimationDuration;
        float f14 = f13 / f12;
        f12 = 0.0f;
        for (int i2 = (int)Math.floor(f14); i2 >= 0; --i2) {
            float f15 = Math.min(1.0f, (f14 - (float)i2) * this.mInStepPercent) * f11;
            if (i2 < this.mInColors.length) {
                if (f12 == 0.0f) {
                    this.mPaint.setColor(this.mInColors[i2]);
                    this.mPaint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(f9, f10, f15, this.mPaint);
                } else {
                    if (!(f15 > f12)) break;
                    float f16 = (f12 + f15) / 2.0f;
                    this.mRect.set(f9 - f16, f10 - f16, f9 + f16, f10 + f16);
                    this.mPaint.setStrokeWidth(f15 - f12);
                    this.mPaint.setStyle(Paint.Style.STROKE);
                    this.mPaint.setColor(this.mInColors[i2]);
                    canvas.drawCircle(f9, f10, f16, this.mPaint);
                }
            }
            f12 = f15;
        }
        if (this.mProgressState != -1) {
            f12 = f11 - (float)this.mStrokeSize / 2.0f;
            this.mRect.set(f9 - f12, f10 - f12, f9 + f12, f10 + f12);
            this.mPaint.setStrokeWidth((float)this.mStrokeSize);
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setColor(this.getIndeterminateStrokeColor());
            this.mPaint.setColor(this.getIndeterminateStrokeColor());
            canvas.drawArc(this.mRect, this.mStartAngle, this.mSweepAngle, false, this.mPaint);
            return;
        }
        if (!(f14 >= 1.0f / this.mInStepPercent) && !(f13 >= 1.0f)) return;
        this.resetAnimation();
        this.mProgressState = 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getIndeterminateStrokeColor() {
        int n2;
        if (this.mProgressState != 3 || this.mStrokeColors.length == 1) {
            return this.mStrokeColors[this.mStrokeColorIndex];
        }
        float f2 = Math.max(0.0f, Math.min(1.0f, (float)(SystemClock.uptimeMillis() - this.mLastProgressStateTime) / (float)this.mKeepDuration));
        if (this.mStrokeColorIndex == 0) {
            n2 = this.mStrokeColors.length - 1;
            return ColorUtils.getMiddleColor(this.mStrokeColors[n2], this.mStrokeColors[this.mStrokeColorIndex], f2);
        }
        n2 = this.mStrokeColorIndex - 1;
        return ColorUtils.getMiddleColor(this.mStrokeColors[n2], this.mStrokeColors[this.mStrokeColorIndex], f2);
    }

    private void initializeWithState(ProgressState progressState, Resources resources) {
        this.mState = this.createConstantState(this.mState, null);
        this.mTintFilter = this.createTintFilter(progressState.mTint, progressState.mTintMode);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void resetAnimation() {
        this.mLastProgressStateTime = this.mLastUpdateTime = SystemClock.uptimeMillis();
        this.mStartAngle = this.mInitialAngle;
        this.mStrokeColorIndex = 0;
        float f2 = this.mReverse ? -this.mMinSweepAngle : this.mMinSweepAngle;
        this.mSweepAngle = f2;
    }

    private void start(boolean bl2) {
        if (this.isRunning()) {
            return;
        }
        this.resetAnimation();
        if (bl2) {
            this.mRunState = 1;
            this.mLastRunStateTime = SystemClock.uptimeMillis();
            this.mProgressState = -1;
        }
        this.scheduleSelf(this.mUpdater, SystemClock.uptimeMillis() + 16L);
        this.invalidateSelf();
    }

    private void stop(boolean bl2) {
        if (!this.isRunning()) {
            return;
        }
        if (bl2) {
            this.mLastRunStateTime = SystemClock.uptimeMillis();
            if (this.mRunState == 2) {
                this.scheduleSelf(this.mUpdater, SystemClock.uptimeMillis() + 16L);
                this.invalidateSelf();
            }
            this.mRunState = 4;
            return;
        }
        this.mRunState = 0;
        this.unscheduleSelf(this.mUpdater);
        this.invalidateSelf();
    }

    private void update() {
        switch (this.mProgressMode) {
            default: {
                return;
            }
            case 0: {
                this.updateDeterminate();
                return;
            }
            case 1: 
        }
        this.updateIndeterminate();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateDeterminate() {
        float f2;
        long l2 = SystemClock.uptimeMillis();
        float f3 = f2 = (float)(l2 - this.mLastUpdateTime) * 360.0f / (float)this.mRotateDuration;
        if (this.mReverse) {
            f3 = -f2;
        }
        this.mLastUpdateTime = l2;
        this.mStartAngle += f3;
        if (this.mRunState == 1) {
            if (l2 - this.mLastRunStateTime > (long)this.mInAnimationDuration) {
                this.mRunState = 3;
            }
        } else if (this.mRunState == 4 && l2 - this.mLastRunStateTime > (long)this.mOutAnimationDuration) {
            this.stop(false);
            return;
        }
        if (this.isRunning()) {
            this.scheduleSelf(this.mUpdater, SystemClock.uptimeMillis() + 16L);
        }
        this.invalidateSelf();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateIndeterminate() {
        block14: {
            long l2;
            block13: {
                float f2;
                l2 = SystemClock.uptimeMillis();
                float f3 = f2 = (float)(l2 - this.mLastUpdateTime) * 360.0f / (float)this.mRotateDuration;
                if (this.mReverse) {
                    f3 = -f2;
                }
                this.mLastUpdateTime = l2;
                switch (this.mProgressState) {
                    case 0: {
                        if (this.mTransformDuration <= 0) {
                            f2 = this.mReverse ? -this.mMinSweepAngle : this.mMinSweepAngle;
                            this.mSweepAngle = f2;
                            this.mProgressState = 1;
                            this.mStartAngle += f3;
                            this.mLastProgressStateTime = l2;
                            break;
                        }
                        float f4 = (float)(l2 - this.mLastProgressStateTime) / (float)this.mTransformDuration;
                        f2 = this.mReverse ? -this.mMaxSweepAngle : this.mMaxSweepAngle;
                        float f5 = this.mReverse ? -this.mMinSweepAngle : this.mMinSweepAngle;
                        this.mStartAngle += f3;
                        this.mSweepAngle = this.mTransformInterpolator.getInterpolation(f4) * (f2 - f5) + f5;
                        if (!(f4 > 1.0f)) break;
                        this.mSweepAngle = f2;
                        this.mProgressState = 1;
                        this.mLastProgressStateTime = l2;
                        break;
                    }
                    case 1: {
                        this.mStartAngle += f3;
                        if (l2 - this.mLastProgressStateTime <= (long)this.mKeepDuration) break;
                        this.mProgressState = 2;
                        this.mLastProgressStateTime = l2;
                        break;
                    }
                    case 2: {
                        if (this.mTransformDuration <= 0) {
                            f2 = this.mReverse ? -this.mMinSweepAngle : this.mMinSweepAngle;
                            this.mSweepAngle = f2;
                            this.mProgressState = 3;
                            this.mStartAngle += f3;
                            this.mLastProgressStateTime = l2;
                            this.mStrokeColorIndex = (this.mStrokeColorIndex + 1) % this.mStrokeColors.length;
                            break;
                        }
                        float f6 = (float)(l2 - this.mLastProgressStateTime) / (float)this.mTransformDuration;
                        f2 = this.mReverse ? -this.mMaxSweepAngle : this.mMaxSweepAngle;
                        float f7 = this.mReverse ? -this.mMinSweepAngle : this.mMinSweepAngle;
                        f2 = (1.0f - this.mTransformInterpolator.getInterpolation(f6)) * (f2 - f7) + f7;
                        this.mStartAngle += this.mSweepAngle + f3 - f2;
                        this.mSweepAngle = f2;
                        if (!(f6 > 1.0f)) break;
                        this.mSweepAngle = f7;
                        this.mProgressState = 3;
                        this.mLastProgressStateTime = l2;
                        this.mStrokeColorIndex = (this.mStrokeColorIndex + 1) % this.mStrokeColors.length;
                        break;
                    }
                    case 3: {
                        this.mStartAngle += f3;
                        if (l2 - this.mLastProgressStateTime <= (long)this.mKeepDuration) break;
                        this.mProgressState = 0;
                        this.mLastProgressStateTime = l2;
                    }
                }
                if (this.mRunState != 1) break block13;
                if (l2 - this.mLastRunStateTime > (long)this.mInAnimationDuration) {
                    this.mRunState = 3;
                    if (this.mProgressState == -1) {
                        this.resetAnimation();
                        this.mProgressState = 0;
                    }
                }
                break block14;
            }
            if (this.mRunState == 4 && l2 - this.mLastRunStateTime > (long)this.mOutAnimationDuration) {
                this.stop(false);
                return;
            }
        }
        if (this.isRunning()) {
            this.scheduleSelf(this.mUpdater, SystemClock.uptimeMillis() + 16L);
        }
        this.invalidateSelf();
    }

    ProgressState createConstantState(Drawable.ConstantState constantState, Resources resources) {
        return new ProgressState(constantState, this);
    }

    PorterDuffColorFilter createTintFilter(ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(this.getState(), 0), mode);
    }

    public void draw(Canvas canvas) {
        if (this.mPaint.getColorFilter() != this.mTintFilter) {
            this.mPaint.setColorFilter((ColorFilter)this.mTintFilter);
        }
        switch (this.mProgressMode) {
            default: {
                return;
            }
            case 0: {
                this.drawDeterminate(canvas);
                return;
            }
            case 1: 
        }
        this.drawIndeterminate(canvas);
    }

    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mState.mChangingConfigurations;
    }

    public Drawable.ConstantState getConstantState() {
        this.mState.mChangingConfigurations = this.getChangingConfigurations();
        return this.mState;
    }

    public int getOpacity() {
        return -3;
    }

    public float getProgress() {
        return this.mProgressPercent;
    }

    public int getProgressMode() {
        return this.mProgressMode;
    }

    public float getSecondaryProgress() {
        return this.mSecondaryProgressPercent;
    }

    public int getStrokeSize() {
        return this.mStrokeSize;
    }

    public boolean isRunning() {
        return this.mRunState != 0;
    }

    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mState = this.createConstantState(this.mState, null);
            this.mMutated = true;
        }
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void scheduleSelf(Runnable runnable, long l2) {
        if (this.mRunState == 0) {
            int n2 = this.mInAnimationDuration > 0 ? 1 : 3;
            this.mRunState = n2;
        }
        super.scheduleSelf(runnable, l2);
    }

    public void setAlpha(int n2) {
        this.mPaint.setAlpha(n2);
        this.mAlpha = n2;
        if (this.mState != null) {
            Builder.access$102(this.mState.mBuilder, n2);
        }
        this.invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setProgress(float f2) {
        this.mProgressMode = 0;
        f2 = Math.min(1.0f, Math.max(0.0f, f2));
        if (this.mState != null) {
            this.mState.mBuilder.progressMode(0);
            this.mState.mBuilder.progressPercent(f2);
        }
        if (this.mProgressPercent != f2) {
            this.mProgressPercent = f2;
        }
        if (this.isRunning()) {
            this.resetAnimation();
            this.invalidateSelf();
            return;
        } else {
            if (this.mProgressPercent == 0.0f) return;
            this.start();
            return;
        }
    }

    public void setProgressMode(int n2) {
        if (this.mProgressMode != n2) {
            this.mProgressMode = n2;
            if (n2 == 1) {
                this.mProgressPercent = 0.0f;
            }
            if (this.mState != null) {
                this.mState.mBuilder.progressPercent(this.mProgressPercent);
                this.mState.mBuilder.progressMode(n2);
            }
            this.invalidateSelf();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setSecondaryProgress(float f2) {
        if (this.mSecondaryProgressPercent == (f2 = Math.min(1.0f, Math.max(0.0f, f2)))) return;
        this.mSecondaryProgressPercent = f2;
        if (this.isRunning()) {
            this.invalidateSelf();
            return;
        } else {
            if (this.mSecondaryProgressPercent == 0.0f) return;
            this.start();
            return;
        }
    }

    public void setTintList(ColorStateList colorStateList) {
        this.mState.mTint = colorStateList;
        this.mTintFilter = this.createTintFilter(colorStateList, this.mState.mTintMode);
        this.invalidateSelf();
    }

    public void setTintMode(@NonNull PorterDuff.Mode mode) {
        this.mState.mTintMode = mode;
        this.mTintFilter = this.createTintFilter(this.mState.mTint, mode);
        this.invalidateSelf();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void start() {
        boolean bl2 = this.mInAnimationDuration > 0;
        this.start(bl2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void stop() {
        boolean bl2 = this.mOutAnimationDuration > 0;
        this.stop(bl2);
    }

    public static class Builder {
        private int mAlpha;
        private int mInAnimationDuration;
        private int[] mInColors;
        private float mInStepPercent;
        private float mInitialAngle;
        private int mKeepDuration;
        private float mMaxSweepAngle;
        private float mMinSweepAngle;
        private int mOutAnimationDuration;
        private int mPadding;
        private int mProgressMode;
        private float mProgressPercent;
        private boolean mReverse;
        private int mRotateDuration;
        private float mSecondaryProgressPercent;
        private int[] mStrokeColors;
        private int mStrokeSecondaryColor;
        private int mStrokeSize;
        private int mTransformDuration;
        private Interpolator mTransformInterpolator;

        public Builder() {
        }

        public Builder(Context context) {
            this(context, R.style.Widget_Ticwear_CircularProgressDrawable);
        }

        public Builder(Context context, int n2) {
            TypedArray typedArray = context.obtainStyledAttributes(null, R.styleable.CircularProgressDrawable, 0, n2);
            this.strokeSize(typedArray.getDimensionPixelSize(R.styleable.CircularProgressDrawable_tic_cpd_strokeSize, 0));
            this.initialAngle(typedArray.getInteger(R.styleable.CircularProgressDrawable_tic_cpd_initialAngle, 0));
            this.progressPercent(typedArray.getFloat(R.styleable.CircularProgressDrawable_tic_cpd_progress, 0.0f));
            this.progressMode(typedArray.getInteger(R.styleable.CircularProgressDrawable_tic_cpd_progressMode, 1));
            this.secondaryProgressPercent(typedArray.getFloat(R.styleable.CircularProgressDrawable_tic_cpd_secondaryProgress, 0.0f));
            this.maxSweepAngle(typedArray.getInteger(R.styleable.CircularProgressDrawable_tic_cpd_maxSweepAngle, 0));
            this.minSweepAngle(typedArray.getInteger(R.styleable.CircularProgressDrawable_tic_cpd_minSweepAngle, 0));
            this.reverse(typedArray.getBoolean(R.styleable.CircularProgressDrawable_tic_cpd_reverse, false));
            this.rotateDuration(typedArray.getInteger(R.styleable.CircularProgressDrawable_tic_cpd_rotateDuration, context.getResources().getInteger(17694722)));
            this.transformDuration(typedArray.getInteger(R.styleable.CircularProgressDrawable_tic_cpd_transformDuration, context.getResources().getInteger(0x10E0001)));
            this.keepDuration(typedArray.getInteger(R.styleable.CircularProgressDrawable_tic_cpd_keepDuration, context.getResources().getInteger(0x10E0000)));
            this.inAnimDuration(typedArray.getInteger(R.styleable.CircularProgressDrawable_tic_cpd_inAnimDuration, context.getResources().getInteger(0x10E0001)));
            this.progressAlpha(typedArray.getInteger(R.styleable.CircularProgressDrawable_tic_cpd_progressAlpha, 128));
            this.inStepPercent(typedArray.getFloat(R.styleable.CircularProgressDrawable_tic_cpd_inStepPercent, 0.5f));
            this.outAnimDuration(typedArray.getInteger(R.styleable.CircularProgressDrawable_tic_cpd_outAnimDuration, context.getResources().getInteger(0x10E0001)));
            typedArray.recycle();
        }

        static /* synthetic */ float access$1002(Builder builder, float f2) {
            builder.mMaxSweepAngle = f2;
            return f2;
        }

        static /* synthetic */ int access$102(Builder builder, int n2) {
            builder.mAlpha = n2;
            return n2;
        }

        static /* synthetic */ float access$1202(Builder builder, float f2) {
            builder.mMinSweepAngle = f2;
            return f2;
        }

        static /* synthetic */ int access$1402(Builder builder, int n2) {
            builder.mStrokeSize = n2;
            return n2;
        }

        static /* synthetic */ int[] access$1602(Builder builder, int[] nArray) {
            builder.mStrokeColors = nArray;
            return nArray;
        }

        static /* synthetic */ int access$1802(Builder builder, int n2) {
            builder.mStrokeSecondaryColor = n2;
            return n2;
        }

        static /* synthetic */ boolean access$2002(Builder builder, boolean bl2) {
            builder.mReverse = bl2;
            return bl2;
        }

        static /* synthetic */ int access$202(Builder builder, int n2) {
            builder.mPadding = n2;
            return n2;
        }

        static /* synthetic */ int access$2202(Builder builder, int n2) {
            builder.mRotateDuration = n2;
            return n2;
        }

        static /* synthetic */ int access$2402(Builder builder, int n2) {
            builder.mTransformDuration = n2;
            return n2;
        }

        static /* synthetic */ int access$2602(Builder builder, int n2) {
            builder.mKeepDuration = n2;
            return n2;
        }

        static /* synthetic */ Interpolator access$2802(Builder builder, Interpolator interpolator2) {
            builder.mTransformInterpolator = interpolator2;
            return interpolator2;
        }

        static /* synthetic */ int access$3002(Builder builder, int n2) {
            builder.mProgressMode = n2;
            return n2;
        }

        static /* synthetic */ float access$3202(Builder builder, float f2) {
            builder.mInStepPercent = f2;
            return f2;
        }

        static /* synthetic */ int[] access$3402(Builder builder, int[] nArray) {
            builder.mInColors = nArray;
            return nArray;
        }

        static /* synthetic */ int access$3602(Builder builder, int n2) {
            builder.mInAnimationDuration = n2;
            return n2;
        }

        static /* synthetic */ int access$3802(Builder builder, int n2) {
            builder.mOutAnimationDuration = n2;
            return n2;
        }

        static /* synthetic */ float access$402(Builder builder, float f2) {
            builder.mInitialAngle = f2;
            return f2;
        }

        static /* synthetic */ float access$602(Builder builder, float f2) {
            builder.mProgressPercent = f2;
            return f2;
        }

        static /* synthetic */ float access$802(Builder builder, float f2) {
            builder.mSecondaryProgressPercent = f2;
            return f2;
        }

        public CircularProgressDrawable build() {
            if (this.mStrokeColors == null) {
                this.mStrokeColors = new int[]{-1};
            }
            if (this.mInColors == null && this.mInAnimationDuration > 0) {
                this.mInColors = new int[]{-4860673, -2168068, -327682};
            }
            if (this.mTransformInterpolator == null) {
                this.mTransformInterpolator = new DecelerateInterpolator();
            }
            return new CircularProgressDrawable(this.mPadding, this.mInitialAngle, this.mProgressPercent, this.mSecondaryProgressPercent, this.mMaxSweepAngle, this.mMinSweepAngle, this.mStrokeSize, this.mStrokeColors, this.mStrokeSecondaryColor, this.mReverse, this.mRotateDuration, this.mTransformDuration, this.mKeepDuration, this.mTransformInterpolator, this.mProgressMode, this.mInAnimationDuration, this.mInStepPercent, this.mInColors, this.mOutAnimationDuration, this.mAlpha);
        }

        public Builder inAnimDuration(int n2) {
            this.mInAnimationDuration = n2;
            return this;
        }

        public Builder inStepColors(int ... nArray) {
            this.mInColors = nArray;
            return this;
        }

        public Builder inStepPercent(float f2) {
            this.mInStepPercent = f2;
            return this;
        }

        public Builder initialAngle(float f2) {
            this.mInitialAngle = f2;
            return this;
        }

        public Builder keepDuration(int n2) {
            this.mKeepDuration = n2;
            return this;
        }

        public Builder maxSweepAngle(float f2) {
            this.mMaxSweepAngle = f2;
            return this;
        }

        public Builder minSweepAngle(float f2) {
            this.mMinSweepAngle = f2;
            return this;
        }

        public Builder outAnimDuration(int n2) {
            this.mOutAnimationDuration = n2;
            return this;
        }

        public Builder padding(int n2) {
            this.mPadding = n2;
            return this;
        }

        public Builder progressAlpha(int n2) {
            this.mAlpha = n2;
            return this;
        }

        public Builder progressMode(int n2) {
            this.mProgressMode = n2;
            return this;
        }

        public Builder progressPercent(float f2) {
            this.mProgressPercent = f2;
            return this;
        }

        public Builder reverse() {
            return this.reverse(true);
        }

        public Builder reverse(boolean bl2) {
            this.mReverse = bl2;
            return this;
        }

        public Builder rotateDuration(int n2) {
            this.mRotateDuration = n2;
            return this;
        }

        public Builder secondaryProgressPercent(float f2) {
            this.mSecondaryProgressPercent = f2;
            return this;
        }

        public Builder strokeColors(int ... nArray) {
            this.mStrokeColors = nArray;
            return this;
        }

        public Builder strokeSecondaryColor(int n2) {
            this.mStrokeSecondaryColor = n2;
            return this;
        }

        public Builder strokeSize(int n2) {
            this.mStrokeSize = n2;
            return this;
        }

        public Builder transformDuration(int n2) {
            this.mTransformDuration = n2;
            return this;
        }

        public Builder transformInterpolator(Interpolator interpolator2) {
            this.mTransformInterpolator = interpolator2;
            return this;
        }
    }

    static class ProgressState
    extends Drawable.ConstantState {
        Builder mBuilder;
        int mChangingConfigurations;
        ColorStateList mTint = null;
        PorterDuff.Mode mTintMode = PorterDuff.Mode.SRC_IN;

        /*
         * Enabled aggressive block sorting
         */
        public ProgressState(Drawable.ConstantState constantState, CircularProgressDrawable circularProgressDrawable) {
            this.mBuilder = new Builder();
            if (constantState != null && constantState instanceof ProgressState) {
                constantState = (ProgressState)constantState;
                this.mBuilder = constantState.mBuilder;
                this.mChangingConfigurations = constantState.mChangingConfigurations;
                return;
            } else {
                if (circularProgressDrawable == null) return;
                Builder.access$202(this.mBuilder, circularProgressDrawable.mPadding);
                Builder.access$402(this.mBuilder, circularProgressDrawable.mInitialAngle);
                Builder.access$602(this.mBuilder, circularProgressDrawable.mProgressPercent);
                Builder.access$802(this.mBuilder, circularProgressDrawable.mSecondaryProgressPercent);
                Builder.access$1002(this.mBuilder, circularProgressDrawable.mMaxSweepAngle);
                Builder.access$1202(this.mBuilder, circularProgressDrawable.mMinSweepAngle);
                Builder.access$1402(this.mBuilder, circularProgressDrawable.mStrokeSize);
                Builder.access$1602(this.mBuilder, circularProgressDrawable.mStrokeColors);
                Builder.access$1802(this.mBuilder, circularProgressDrawable.mStrokeSecondaryColor);
                Builder.access$2002(this.mBuilder, circularProgressDrawable.mReverse);
                Builder.access$2202(this.mBuilder, circularProgressDrawable.mRotateDuration);
                Builder.access$2402(this.mBuilder, circularProgressDrawable.mTransformDuration);
                Builder.access$2602(this.mBuilder, circularProgressDrawable.mKeepDuration);
                Builder.access$2802(this.mBuilder, circularProgressDrawable.mTransformInterpolator);
                Builder.access$3002(this.mBuilder, circularProgressDrawable.mProgressMode);
                Builder.access$3202(this.mBuilder, circularProgressDrawable.mInStepPercent);
                Builder.access$3402(this.mBuilder, circularProgressDrawable.mInColors);
                Builder.access$3602(this.mBuilder, circularProgressDrawable.mInAnimationDuration);
                Builder.access$3802(this.mBuilder, circularProgressDrawable.mOutAnimationDuration);
                Builder.access$102(this.mBuilder, circularProgressDrawable.mAlpha);
                return;
            }
        }

        public boolean canApplyTheme() {
            return super.canApplyTheme();
        }

        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }

        @NonNull
        public Drawable newDrawable() {
            CircularProgressDrawable circularProgressDrawable = this.mBuilder.build();
            circularProgressDrawable.initializeWithState(this, null);
            return circularProgressDrawable;
        }

        @NonNull
        public Drawable newDrawable(Resources resources) {
            CircularProgressDrawable circularProgressDrawable = this.mBuilder.build();
            circularProgressDrawable.initializeWithState(circularProgressDrawable.mState, resources);
            return circularProgressDrawable;
        }
    }
}

