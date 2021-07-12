/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.Region
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.graphics.drawable.Drawable$ConstantState
 */
package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableWrapper;
import android.support.v4.graphics.drawable.TintAwareDrawable;

class DrawableWrapperGingerbread
extends Drawable
implements Drawable.Callback,
DrawableWrapper,
TintAwareDrawable {
    static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN;
    private boolean mColorFilterSet;
    private int mCurrentColor;
    private PorterDuff.Mode mCurrentMode;
    Drawable mDrawable;
    private boolean mMutated;
    DrawableWrapperState mState;

    DrawableWrapperGingerbread(@Nullable Drawable drawable2) {
        this.mState = this.mutateConstantState();
        this.setWrappedDrawable(drawable2);
    }

    DrawableWrapperGingerbread(@NonNull DrawableWrapperState drawableWrapperState, @Nullable Resources resources) {
        this.mState = drawableWrapperState;
        this.updateLocalState(resources);
    }

    private void updateLocalState(@Nullable Resources resources) {
        if (this.mState != null && this.mState.mDrawableState != null) {
            this.setWrappedDrawable(this.newDrawableFromState(this.mState.mDrawableState, resources));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean updateTint(int[] nArray) {
        block3: {
            int n2;
            PorterDuff.Mode mode;
            block4: {
                block2: {
                    if (!this.isCompatTintEnabled()) break block2;
                    ColorStateList colorStateList = this.mState.mTint;
                    mode = this.mState.mTintMode;
                    if (colorStateList == null || mode == null) break block3;
                    n2 = colorStateList.getColorForState(nArray, colorStateList.getDefaultColor());
                    if (!this.mColorFilterSet || n2 != this.mCurrentColor || mode != this.mCurrentMode) break block4;
                }
                return false;
            }
            this.setColorFilter(n2, mode);
            this.mCurrentColor = n2;
            this.mCurrentMode = mode;
            this.mColorFilterSet = true;
            return true;
        }
        this.mColorFilterSet = false;
        this.clearColorFilter();
        return false;
    }

    public void draw(Canvas canvas) {
        this.mDrawable.draw(canvas);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getChangingConfigurations() {
        int n2;
        int n3 = super.getChangingConfigurations();
        if (this.mState != null) {
            n2 = this.mState.getChangingConfigurations();
            return n2 | n3 | this.mDrawable.getChangingConfigurations();
        }
        n2 = 0;
        return n2 | n3 | this.mDrawable.getChangingConfigurations();
    }

    @Nullable
    public Drawable.ConstantState getConstantState() {
        if (this.mState != null && this.mState.canConstantState()) {
            this.mState.mChangingConfigurations = this.getChangingConfigurations();
            return this.mState;
        }
        return null;
    }

    public Drawable getCurrent() {
        return this.mDrawable.getCurrent();
    }

    public int getIntrinsicHeight() {
        return this.mDrawable.getIntrinsicHeight();
    }

    public int getIntrinsicWidth() {
        return this.mDrawable.getIntrinsicWidth();
    }

    public int getMinimumHeight() {
        return this.mDrawable.getMinimumHeight();
    }

    public int getMinimumWidth() {
        return this.mDrawable.getMinimumWidth();
    }

    public int getOpacity() {
        return this.mDrawable.getOpacity();
    }

    public boolean getPadding(Rect rect) {
        return this.mDrawable.getPadding(rect);
    }

    public int[] getState() {
        return this.mDrawable.getState();
    }

    public Region getTransparentRegion() {
        return this.mDrawable.getTransparentRegion();
    }

    @Override
    public final Drawable getWrappedDrawable() {
        return this.mDrawable;
    }

    public void invalidateDrawable(Drawable drawable2) {
        this.invalidateSelf();
    }

    protected boolean isCompatTintEnabled() {
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isStateful() {
        ColorStateList colorStateList;
        if (this.isCompatTintEnabled() && this.mState != null) {
            colorStateList = this.mState.mTint;
            return colorStateList != null && colorStateList.isStateful() || this.mDrawable.isStateful();
        }
        colorStateList = null;
        return colorStateList != null && colorStateList.isStateful() || this.mDrawable.isStateful();
    }

    /*
     * Enabled aggressive block sorting
     */
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mState = this.mutateConstantState();
            if (this.mDrawable != null) {
                this.mDrawable.mutate();
            }
            if (this.mState != null) {
                DrawableWrapperState drawableWrapperState = this.mState;
                Drawable.ConstantState constantState = this.mDrawable != null ? this.mDrawable.getConstantState() : null;
                drawableWrapperState.mDrawableState = constantState;
            }
            this.mMutated = true;
        }
        return this;
    }

    @NonNull
    DrawableWrapperState mutateConstantState() {
        return new DrawableWrapperStateBase(this.mState, null);
    }

    protected Drawable newDrawableFromState(@NonNull Drawable.ConstantState constantState, @Nullable Resources resources) {
        return constantState.newDrawable(resources);
    }

    protected void onBoundsChange(Rect rect) {
        if (this.mDrawable != null) {
            this.mDrawable.setBounds(rect);
        }
    }

    protected boolean onLevelChange(int n2) {
        return this.mDrawable.setLevel(n2);
    }

    public void scheduleDrawable(Drawable drawable2, Runnable runnable, long l2) {
        this.scheduleSelf(runnable, l2);
    }

    public void setAlpha(int n2) {
        this.mDrawable.setAlpha(n2);
    }

    public void setChangingConfigurations(int n2) {
        this.mDrawable.setChangingConfigurations(n2);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mDrawable.setColorFilter(colorFilter);
    }

    public void setDither(boolean bl2) {
        this.mDrawable.setDither(bl2);
    }

    public void setFilterBitmap(boolean bl2) {
        this.mDrawable.setFilterBitmap(bl2);
    }

    public boolean setState(int[] nArray) {
        boolean bl2 = this.mDrawable.setState(nArray);
        return this.updateTint(nArray) || bl2;
    }

    @Override
    public void setTint(int n2) {
        this.setTintList(ColorStateList.valueOf((int)n2));
    }

    @Override
    public void setTintList(ColorStateList colorStateList) {
        this.mState.mTint = colorStateList;
        this.updateTint(this.getState());
    }

    @Override
    public void setTintMode(PorterDuff.Mode mode) {
        this.mState.mTintMode = mode;
        this.updateTint(this.getState());
    }

    public boolean setVisible(boolean bl2, boolean bl3) {
        return super.setVisible(bl2, bl3) || this.mDrawable.setVisible(bl2, bl3);
    }

    @Override
    public final void setWrappedDrawable(Drawable drawable2) {
        if (this.mDrawable != null) {
            this.mDrawable.setCallback(null);
        }
        this.mDrawable = drawable2;
        if (drawable2 != null) {
            drawable2.setCallback((Drawable.Callback)this);
            this.setVisible(drawable2.isVisible(), true);
            this.setState(drawable2.getState());
            this.setLevel(drawable2.getLevel());
            this.setBounds(drawable2.getBounds());
            if (this.mState != null) {
                this.mState.mDrawableState = drawable2.getConstantState();
            }
        }
        this.invalidateSelf();
    }

    public void unscheduleDrawable(Drawable drawable2, Runnable runnable) {
        this.unscheduleSelf(runnable);
    }

    protected static abstract class DrawableWrapperState
    extends Drawable.ConstantState {
        int mChangingConfigurations;
        Drawable.ConstantState mDrawableState;
        ColorStateList mTint = null;
        PorterDuff.Mode mTintMode = DEFAULT_TINT_MODE;

        DrawableWrapperState(@Nullable DrawableWrapperState drawableWrapperState, @Nullable Resources resources) {
            if (drawableWrapperState != null) {
                this.mChangingConfigurations = drawableWrapperState.mChangingConfigurations;
                this.mDrawableState = drawableWrapperState.mDrawableState;
                this.mTint = drawableWrapperState.mTint;
                this.mTintMode = drawableWrapperState.mTintMode;
            }
        }

        boolean canConstantState() {
            return this.mDrawableState != null;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public int getChangingConfigurations() {
            int n2;
            int n3 = this.mChangingConfigurations;
            if (this.mDrawableState != null) {
                n2 = this.mDrawableState.getChangingConfigurations();
                return n2 | n3;
            }
            n2 = 0;
            return n2 | n3;
        }

        public Drawable newDrawable() {
            return this.newDrawable(null);
        }

        public abstract Drawable newDrawable(@Nullable Resources var1);
    }

    private static class DrawableWrapperStateBase
    extends DrawableWrapperState {
        DrawableWrapperStateBase(@Nullable DrawableWrapperState drawableWrapperState, @Nullable Resources resources) {
            super(drawableWrapperState, resources);
        }

        @Override
        public Drawable newDrawable(@Nullable Resources resources) {
            return new DrawableWrapperGingerbread(this, resources);
        }
    }
}

