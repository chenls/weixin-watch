/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 */
package android.support.wearable.view;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

@TargetApi(value=20)
class ViewportDrawable
extends Drawable
implements Drawable.Callback {
    private static final boolean DEBUG = false;
    private static final float STEP_SIZE_PCT = 0.2f;
    private static final String TAG = "ViewportDrawable";
    private int mAlpha = 255;
    private int mCenterOffsetX;
    private int mCenterOffsetY;
    private int mChangingConfigs;
    private ColorFilter mColorFilter;
    private int mColorFilterColor;
    private PorterDuff.Mode mColorFilterMode;
    private boolean mDither = true;
    private Drawable mDrawable;
    private final Rect mDrawableBounds = new Rect();
    private boolean mFilterBitmap = true;
    private float mHeightStepSize;
    private int mMaxPosX = 2;
    private int mMaxPosY = 2;
    private float mPositionX = 1.0f;
    private float mPositionY = 1.0f;
    private float mScale = 1.0f;
    private int mSrcHeight;
    private int mSrcWidth;
    private float mWidthStepSize;

    public ViewportDrawable() {
        this(null);
    }

    public ViewportDrawable(Drawable drawable2) {
        this.setDrawable(drawable2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static float limit(float f2, int n2, int n3) {
        if (f2 < (float)n2) {
            return n2;
        }
        float f3 = f2;
        if (!(f2 > (float)n3)) return f3;
        return n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void recomputeScale() {
        if (this.mDrawable == null || this.mDrawableBounds.width() == 0 || this.mDrawableBounds.height() == 0) {
            return;
        }
        this.mSrcWidth = this.mDrawable.getIntrinsicWidth();
        this.mSrcHeight = this.mDrawable.getIntrinsicHeight();
        if (this.mSrcWidth == -1 || this.mSrcHeight == -1) {
            this.mSrcWidth = this.mDrawableBounds.width();
            this.mSrcHeight = this.mDrawableBounds.height();
            this.mScale = 1.0f;
            this.mWidthStepSize = 0.0f;
            this.mHeightStepSize = 0.0f;
            this.mCenterOffsetX = 0;
            this.mCenterOffsetY = 0;
            return;
        }
        this.mWidthStepSize = (float)this.mDrawableBounds.width() * 0.2f;
        this.mHeightStepSize = (float)this.mDrawableBounds.height() * 0.2f;
        float f2 = (float)this.mDrawableBounds.width() + (float)this.mMaxPosX * this.mWidthStepSize;
        float f3 = (float)this.mDrawableBounds.height() + (float)this.mMaxPosY * this.mHeightStepSize;
        this.mScale = Math.max(f2 / (float)this.mSrcWidth, f3 / (float)this.mSrcHeight);
        float f4 = (float)this.mSrcWidth * this.mScale;
        float f5 = this.mSrcHeight;
        float f6 = this.mScale;
        if (f4 > f2) {
            this.mCenterOffsetX = (int)((f4 - f2) / 2.0f);
            this.mCenterOffsetY = 0;
            return;
        }
        this.mCenterOffsetY = (int)((f5 * f6 - f3) / 2.0f);
        this.mCenterOffsetX = 0;
    }

    private void updateDrawableBounds(Rect rect) {
        block3: {
            block2: {
                if (this.mDrawable == null) break block2;
                int n2 = this.mDrawable.getIntrinsicWidth();
                int n3 = this.mDrawable.getIntrinsicHeight();
                if (n2 == -1 || n3 == -1) break block3;
                this.mDrawable.setBounds(rect.left, rect.top, rect.left + n2, rect.top + n3);
            }
            return;
        }
        this.mDrawable.setBounds(rect);
    }

    public void clearColorFilter() {
        if (this.mColorFilterMode != null) {
            this.mColorFilterMode = null;
            if (this.mDrawable != null) {
                this.mDrawable.clearColorFilter();
            }
        }
    }

    public void draw(Canvas canvas) {
        if (this.mDrawable != null) {
            canvas.save();
            canvas.clipRect(this.getBounds());
            float f2 = this.mCenterOffsetX;
            float f3 = this.mPositionX;
            float f4 = this.mWidthStepSize;
            float f5 = this.mCenterOffsetY;
            float f6 = this.mPositionY;
            float f7 = this.mHeightStepSize;
            canvas.translate(-(f2 + f3 * f4), -(f5 + f6 * f7));
            canvas.scale(this.mScale, this.mScale);
            this.mDrawable.draw(canvas);
            canvas.restore();
        }
    }

    public int getAlpha() {
        return this.mAlpha;
    }

    public int getChangingConfigurations() {
        return this.mChangingConfigs;
    }

    public int getOpacity() {
        if (this.mDrawable != null) {
            return this.mDrawable.getOpacity();
        }
        return 0;
    }

    public void invalidateDrawable(Drawable drawable2) {
        if (drawable2 == this.mDrawable && this.getCallback() != null) {
            this.getCallback().invalidateDrawable((Drawable)this);
        }
    }

    public boolean isStateful() {
        if (this.mDrawable != null) {
            return this.mDrawable.isStateful();
        }
        return false;
    }

    public void jumpToCurrentState() {
        if (this.mDrawable != null) {
            this.mDrawable.jumpToCurrentState();
        }
    }

    protected void onBoundsChange(Rect rect) {
        this.mDrawableBounds.set(rect);
        this.updateDrawableBounds(rect);
        this.recomputeScale();
        this.invalidateSelf();
    }

    protected boolean onLevelChange(int n2) {
        if (this.mDrawable != null) {
            return this.mDrawable.setLevel(n2);
        }
        return false;
    }

    protected boolean onStateChange(int[] nArray) {
        if (this.mDrawable != null) {
            return this.mDrawable.setState(nArray);
        }
        return false;
    }

    public void scheduleDrawable(Drawable drawable2, Runnable runnable, long l2) {
        if (drawable2 == this.mDrawable && this.getCallback() != null) {
            this.getCallback().scheduleDrawable((Drawable)this, runnable, l2);
        }
    }

    public void setAlpha(int n2) {
        if (this.mAlpha != n2) {
            this.mAlpha = n2;
            if (this.mDrawable != null) {
                this.mDrawable.setAlpha(n2);
            }
        }
    }

    public void setChangingConfigurations(int n2) {
        if (this.mChangingConfigs != n2) {
            this.mChangingConfigs = n2;
            if (this.mDrawable != null) {
                this.mDrawable.setChangingConfigurations(n2);
            }
        }
    }

    public void setColorFilter(int n2, PorterDuff.Mode mode) {
        if (this.mColorFilterColor != n2 || this.mColorFilterMode != mode) {
            this.mColorFilterColor = n2;
            this.mColorFilterMode = mode;
            if (this.mDrawable != null) {
                this.mDrawable.setColorFilter(n2, mode);
            }
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        if (this.mColorFilter != colorFilter) {
            this.mColorFilter = colorFilter;
            if (this.mDrawable != null) {
                this.mDrawable.setColorFilter(colorFilter);
            }
        }
    }

    public void setDither(boolean bl2) {
        if (this.mDither != bl2) {
            this.mDither = bl2;
            if (this.mDrawable != null) {
                this.mDrawable.setDither(bl2);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setDrawable(Drawable drawable2) {
        block7: {
            block6: {
                if (this.mDrawable == drawable2) break block6;
                if (this.mDrawable != null) {
                    this.mDrawable.setCallback(null);
                }
                this.mDrawable = drawable2;
                if (this.mDrawable != null) break block7;
            }
            return;
        }
        this.mDrawable.setAlpha(this.getAlpha());
        this.updateDrawableBounds(this.getBounds());
        this.mDrawable.setCallback((Drawable.Callback)this);
        if (this.mColorFilter != null) {
            this.mDrawable.setColorFilter(this.mColorFilter);
        }
        if (this.mColorFilterMode != null) {
            this.mDrawable.setColorFilter(this.mColorFilterColor, this.mColorFilterMode);
        }
        this.mDrawable.setDither(this.mDither);
        this.mDrawable.setFilterBitmap(this.mFilterBitmap);
        this.mDrawable.setState(this.getState());
        this.recomputeScale();
        this.invalidateSelf();
    }

    public void setFilterBitmap(boolean bl2) {
        if (this.mFilterBitmap != bl2) {
            this.mFilterBitmap = bl2;
            if (this.mDrawable != null) {
                this.mDrawable.setFilterBitmap(bl2);
            }
        }
    }

    public void setHorizontalPosition(float f2) {
        this.setPosition(f2, this.mPositionY);
    }

    public void setHorizontalStops(int n2) {
        this.setStops(n2, this.mMaxPosY + 1);
    }

    public void setPosition(float f2, float f3) {
        if (this.mPositionX != f2 || this.mPositionY != f3) {
            this.mPositionX = ViewportDrawable.limit(f2, 0, this.mMaxPosX);
            this.mPositionY = ViewportDrawable.limit(f3, 0, this.mMaxPosY);
            this.invalidateSelf();
        }
    }

    public void setStops(int n2, int n3) {
        n2 = Math.max(0, n2 - 1);
        n3 = Math.max(0, n3 - 1);
        if (n2 != this.mMaxPosX || n3 != this.mMaxPosY) {
            this.mMaxPosX = n2;
            this.mMaxPosY = n3;
            this.mPositionX = ViewportDrawable.limit(this.mPositionX, 0, this.mMaxPosX);
            this.mPositionY = ViewportDrawable.limit(this.mPositionY, 0, this.mMaxPosY);
            this.recomputeScale();
            this.invalidateSelf();
        }
    }

    public void setVerticalPosition(float f2) {
        this.setPosition(this.mPositionX, f2);
    }

    public void setVerticalStops(int n2) {
        this.setStops(this.mMaxPosX + 1, n2);
    }

    public void unscheduleDrawable(Drawable drawable2, Runnable runnable) {
        if (drawable2 == this.mDrawable && this.getCallback() != null) {
            this.getCallback().unscheduleDrawable((Drawable)this, runnable);
        }
    }
}

