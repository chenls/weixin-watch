/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.Outline
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.DrawableContainer
 *  android.graphics.drawable.GradientDrawable
 *  android.graphics.drawable.InsetDrawable
 *  android.os.Build$VERSION
 */
package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableWrapperGingerbread;
import android.support.v4.graphics.drawable.DrawableWrapperKitKat;

class DrawableWrapperLollipop
extends DrawableWrapperKitKat {
    DrawableWrapperLollipop(Drawable drawable2) {
        super(drawable2);
    }

    DrawableWrapperLollipop(DrawableWrapperGingerbread.DrawableWrapperState drawableWrapperState, Resources resources) {
        super(drawableWrapperState, resources);
    }

    public Rect getDirtyBounds() {
        return this.mDrawable.getDirtyBounds();
    }

    public void getOutline(Outline outline) {
        this.mDrawable.getOutline(outline);
    }

    @Override
    protected boolean isCompatTintEnabled() {
        boolean bl2;
        block2: {
            block3: {
                boolean bl3;
                bl2 = bl3 = false;
                if (Build.VERSION.SDK_INT != 21) break block2;
                Drawable drawable2 = this.mDrawable;
                if (drawable2 instanceof GradientDrawable || drawable2 instanceof DrawableContainer) break block3;
                bl2 = bl3;
                if (!(drawable2 instanceof InsetDrawable)) break block2;
            }
            bl2 = true;
        }
        return bl2;
    }

    @Override
    @NonNull
    DrawableWrapperGingerbread.DrawableWrapperState mutateConstantState() {
        return new DrawableWrapperStateLollipop(this.mState, null);
    }

    public void setHotspot(float f2, float f3) {
        this.mDrawable.setHotspot(f2, f3);
    }

    public void setHotspotBounds(int n2, int n3, int n4, int n5) {
        this.mDrawable.setHotspotBounds(n2, n3, n4, n5);
    }

    @Override
    public boolean setState(int[] nArray) {
        if (super.setState(nArray)) {
            this.invalidateSelf();
            return true;
        }
        return false;
    }

    @Override
    public void setTint(int n2) {
        if (this.isCompatTintEnabled()) {
            super.setTint(n2);
            return;
        }
        this.mDrawable.setTint(n2);
    }

    @Override
    public void setTintList(ColorStateList colorStateList) {
        if (this.isCompatTintEnabled()) {
            super.setTintList(colorStateList);
            return;
        }
        this.mDrawable.setTintList(colorStateList);
    }

    @Override
    public void setTintMode(PorterDuff.Mode mode) {
        if (this.isCompatTintEnabled()) {
            super.setTintMode(mode);
            return;
        }
        this.mDrawable.setTintMode(mode);
    }

    private static class DrawableWrapperStateLollipop
    extends DrawableWrapperGingerbread.DrawableWrapperState {
        DrawableWrapperStateLollipop(@Nullable DrawableWrapperGingerbread.DrawableWrapperState drawableWrapperState, @Nullable Resources resources) {
            super(drawableWrapperState, resources);
        }

        @Override
        public Drawable newDrawable(@Nullable Resources resources) {
            return new DrawableWrapperLollipop(this, resources);
        }
    }
}

