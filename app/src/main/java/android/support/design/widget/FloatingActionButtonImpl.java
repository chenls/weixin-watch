/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.GradientDrawable
 *  android.view.ViewTreeObserver$OnPreDrawListener
 */
package android.support.design.widget;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.design.R;
import android.support.design.widget.CircularBorderDrawable;
import android.support.design.widget.ShadowViewDelegate;
import android.support.design.widget.VisibilityAwareImageButton;
import android.view.ViewTreeObserver;

abstract class FloatingActionButtonImpl {
    static final int[] EMPTY_STATE_SET;
    static final int[] FOCUSED_ENABLED_STATE_SET;
    static final int[] PRESSED_ENABLED_STATE_SET;
    static final int SHOW_HIDE_ANIM_DURATION = 200;
    CircularBorderDrawable mBorderDrawable;
    Drawable mContentBackground;
    float mElevation;
    private ViewTreeObserver.OnPreDrawListener mPreDrawListener;
    float mPressedTranslationZ;
    Drawable mRippleDrawable;
    final ShadowViewDelegate mShadowViewDelegate;
    Drawable mShapeDrawable;
    private final Rect mTmpRect = new Rect();
    final VisibilityAwareImageButton mView;

    static {
        PRESSED_ENABLED_STATE_SET = new int[]{16842919, 16842910};
        FOCUSED_ENABLED_STATE_SET = new int[]{16842908, 16842910};
        EMPTY_STATE_SET = new int[0];
    }

    FloatingActionButtonImpl(VisibilityAwareImageButton visibilityAwareImageButton, ShadowViewDelegate shadowViewDelegate) {
        this.mView = visibilityAwareImageButton;
        this.mShadowViewDelegate = shadowViewDelegate;
    }

    private void ensurePreDrawListener() {
        if (this.mPreDrawListener == null) {
            this.mPreDrawListener = new ViewTreeObserver.OnPreDrawListener(){

                public boolean onPreDraw() {
                    FloatingActionButtonImpl.this.onPreDraw();
                    return true;
                }
            };
        }
    }

    CircularBorderDrawable createBorderDrawable(int n2, ColorStateList colorStateList) {
        Resources resources = this.mView.getResources();
        CircularBorderDrawable circularBorderDrawable = this.newCircularDrawable();
        circularBorderDrawable.setGradientColors(resources.getColor(R.color.design_fab_stroke_top_outer_color), resources.getColor(R.color.design_fab_stroke_top_inner_color), resources.getColor(R.color.design_fab_stroke_end_inner_color), resources.getColor(R.color.design_fab_stroke_end_outer_color));
        circularBorderDrawable.setBorderWidth(n2);
        circularBorderDrawable.setBorderTint(colorStateList);
        return circularBorderDrawable;
    }

    GradientDrawable createShapeDrawable() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(1);
        gradientDrawable.setColor(-1);
        return gradientDrawable;
    }

    final Drawable getContentBackground() {
        return this.mContentBackground;
    }

    abstract float getElevation();

    abstract void getPadding(Rect var1);

    abstract void hide(@Nullable InternalVisibilityChangedListener var1, boolean var2);

    abstract void jumpDrawableToCurrentState();

    CircularBorderDrawable newCircularDrawable() {
        return new CircularBorderDrawable();
    }

    void onAttachedToWindow() {
        if (this.requirePreDrawListener()) {
            this.ensurePreDrawListener();
            this.mView.getViewTreeObserver().addOnPreDrawListener(this.mPreDrawListener);
        }
    }

    abstract void onCompatShadowChanged();

    void onDetachedFromWindow() {
        if (this.mPreDrawListener != null) {
            this.mView.getViewTreeObserver().removeOnPreDrawListener(this.mPreDrawListener);
            this.mPreDrawListener = null;
        }
    }

    abstract void onDrawableStateChanged(int[] var1);

    abstract void onElevationChanged(float var1);

    void onPaddingUpdated(Rect rect) {
    }

    void onPreDraw() {
    }

    abstract void onTranslationZChanged(float var1);

    boolean requirePreDrawListener() {
        return false;
    }

    abstract void setBackgroundDrawable(ColorStateList var1, PorterDuff.Mode var2, int var3, int var4);

    abstract void setBackgroundTintList(ColorStateList var1);

    abstract void setBackgroundTintMode(PorterDuff.Mode var1);

    final void setElevation(float f2) {
        if (this.mElevation != f2) {
            this.mElevation = f2;
            this.onElevationChanged(f2);
        }
    }

    final void setPressedTranslationZ(float f2) {
        if (this.mPressedTranslationZ != f2) {
            this.mPressedTranslationZ = f2;
            this.onTranslationZChanged(f2);
        }
    }

    abstract void setRippleColor(int var1);

    abstract void show(@Nullable InternalVisibilityChangedListener var1, boolean var2);

    final void updatePadding() {
        Rect rect = this.mTmpRect;
        this.getPadding(rect);
        this.onPaddingUpdated(rect);
        this.mShadowViewDelegate.setShadowPadding(rect.left, rect.top, rect.right, rect.bottom);
    }

    static interface InternalVisibilityChangedListener {
        public void onHidden();

        public void onShown();
    }
}

