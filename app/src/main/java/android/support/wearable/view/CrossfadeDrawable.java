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
import android.support.wearable.view.Func;

@TargetApi(value=20)
public class CrossfadeDrawable
extends Drawable
implements Drawable.Callback {
    private int mAlpha;
    private Drawable mBase;
    private int mChangingConfigs;
    private ColorFilter mColorFilter;
    private int mColorFilterColor;
    private PorterDuff.Mode mColorFilterMode;
    private boolean mDither;
    private Drawable mFading;
    private boolean mFilterBitmap;
    private float mProgress;

    private void initDrawable(Drawable drawable2) {
        drawable2.setCallback((Drawable.Callback)this);
        drawable2.setState(this.getState());
        if (this.mColorFilter != null) {
            drawable2.setColorFilter(this.mColorFilter);
        }
        if (this.mColorFilterMode != null) {
            drawable2.setColorFilter(this.mColorFilterColor, this.mColorFilterMode);
        }
        drawable2.setDither(this.mDither);
        drawable2.setFilterBitmap(this.mFilterBitmap);
        drawable2.setBounds(this.getBounds());
    }

    public void clearColorFilter() {
        if (this.mColorFilterMode != null) {
            this.mColorFilterMode = null;
            if (this.mFading != null) {
                this.mFading.clearColorFilter();
            }
            if (this.mBase != null) {
                this.mBase.clearColorFilter();
            }
        }
    }

    public void draw(Canvas canvas) {
        if (this.mBase != null && (this.mProgress < 1.0f || this.mFading == null)) {
            this.mBase.setAlpha(255);
            this.mBase.draw(canvas);
        }
        if (this.mFading != null && this.mProgress > 0.0f) {
            this.mFading.setAlpha((int)(255.0f * this.mProgress));
            this.mFading.draw(canvas);
        }
    }

    public int getAlpha() {
        return this.mAlpha;
    }

    public Drawable getBase() {
        return this.mBase;
    }

    public int getChangingConfigurations() {
        return this.mChangingConfigs;
    }

    public Drawable getFading() {
        return this.mFading;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int getIntrinsicHeight() {
        int n2;
        int n3 = this.mFading == null ? -1 : this.mFading.getIntrinsicHeight();
        if (this.mBase == null) {
            n2 = -1;
            return Math.max(n3, n2);
        }
        n2 = this.mBase.getIntrinsicHeight();
        return Math.max(n3, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public int getIntrinsicWidth() {
        int n2;
        int n3 = this.mFading == null ? -1 : this.mFading.getIntrinsicWidth();
        if (this.mBase == null) {
            n2 = -1;
            return Math.max(n3, n2);
        }
        n2 = this.mBase.getIntrinsicHeight();
        return Math.max(n3, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public int getOpacity() {
        int n2 = 0;
        int n3 = this.mFading == null ? 0 : this.mFading.getOpacity();
        if (this.mBase == null) {
            return CrossfadeDrawable.resolveOpacity((int)n3, (int)n2);
        }
        n2 = this.mBase.getOpacity();
        return CrossfadeDrawable.resolveOpacity((int)n3, (int)n2);
    }

    public void invalidateDrawable(Drawable drawable2) {
        if ((drawable2 == this.mFading || drawable2 == this.mBase) && this.getCallback() != null) {
            this.getCallback().invalidateDrawable((Drawable)this);
        }
    }

    public boolean isStateful() {
        return this.mFading != null && this.mFading.isStateful() || this.mBase != null && this.mBase.isStateful();
    }

    public void jumpToCurrentState() {
        if (this.mFading != null) {
            this.mFading.jumpToCurrentState();
        }
        if (this.mBase != null) {
            this.mBase.jumpToCurrentState();
        }
    }

    protected void onBoundsChange(Rect rect) {
        if (this.mBase != null) {
            this.mBase.setBounds(rect);
        }
        if (this.mFading != null) {
            this.mFading.setBounds(rect);
        }
        this.invalidateSelf();
    }

    protected boolean onLevelChange(int n2) {
        boolean bl2 = false;
        if (this.mFading != null) {
            bl2 = false | this.mFading.setLevel(n2);
        }
        boolean bl3 = bl2;
        if (this.mBase != null) {
            bl3 = bl2 | this.mBase.setLevel(n2);
        }
        return bl3;
    }

    protected boolean onStateChange(int[] nArray) {
        boolean bl2 = false;
        if (this.mFading != null) {
            bl2 = false | this.mFading.setState(nArray);
        }
        boolean bl3 = bl2;
        if (this.mBase != null) {
            bl3 = bl2 | this.mBase.setState(nArray);
        }
        return bl3;
    }

    public void scheduleDrawable(Drawable drawable2, Runnable runnable, long l2) {
        if ((drawable2 == this.mFading || drawable2 == this.mBase) && this.getCallback() != null) {
            this.getCallback().scheduleDrawable((Drawable)this, runnable, l2);
        }
    }

    public void setAlpha(int n2) {
        if (n2 != this.mAlpha) {
            this.mAlpha = n2;
            this.invalidateSelf();
        }
    }

    public void setBase(Drawable drawable2) {
        if (this.mBase != drawable2) {
            if (this.mBase != null) {
                this.mBase.setCallback(null);
            }
            this.mBase = drawable2;
            this.initDrawable(drawable2);
            this.invalidateSelf();
        }
    }

    public void setChangingConfigurations(int n2) {
        if (this.mChangingConfigs != n2) {
            this.mChangingConfigs = n2;
            if (this.mFading != null) {
                this.mFading.setChangingConfigurations(n2);
            }
            if (this.mBase != null) {
                this.mBase.setChangingConfigurations(n2);
            }
        }
    }

    public void setColorFilter(int n2, PorterDuff.Mode mode) {
        if (this.mColorFilterColor != n2 || this.mColorFilterMode != mode) {
            this.mColorFilterColor = n2;
            this.mColorFilterMode = mode;
            if (this.mFading != null) {
                this.mFading.setColorFilter(n2, mode);
            }
            if (this.mBase != null) {
                this.mBase.setColorFilter(n2, mode);
            }
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        if (this.mColorFilter != colorFilter) {
            this.mColorFilter = colorFilter;
            if (this.mFading != null) {
                this.mFading.setColorFilter(colorFilter);
            }
            if (this.mBase != null) {
                this.mBase.setColorFilter(colorFilter);
            }
        }
    }

    public void setDither(boolean bl2) {
        if (this.mDither != bl2) {
            this.mDither = bl2;
            if (this.mFading != null) {
                this.mFading.setDither(bl2);
            }
            if (this.mBase != null) {
                this.mBase.setDither(bl2);
            }
        }
    }

    public void setFading(Drawable drawable2) {
        if (this.mFading != drawable2) {
            if (this.mFading != null) {
                this.mFading.setCallback(null);
            }
            this.mFading = drawable2;
            if (drawable2 != null) {
                this.initDrawable(drawable2);
            }
            this.invalidateSelf();
        }
    }

    public void setFilterBitmap(boolean bl2) {
        if (this.mFilterBitmap != bl2) {
            this.mFilterBitmap = bl2;
            if (this.mFading != null) {
                this.mFading.setFilterBitmap(bl2);
            }
            if (this.mBase != null) {
                this.mBase.setFilterBitmap(bl2);
            }
        }
    }

    public void setProgress(float f2) {
        if ((f2 = Func.clamp(f2, 0, 1)) != this.mProgress) {
            this.mProgress = f2;
            this.invalidateSelf();
        }
    }

    public void unscheduleDrawable(Drawable drawable2, Runnable runnable) {
        if ((drawable2 == this.mFading || drawable2 == this.mBase) && this.getCallback() != null) {
            this.getCallback().unscheduleDrawable((Drawable)this, runnable);
        }
    }
}

