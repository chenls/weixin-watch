/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.ObjectAnimator
 *  android.animation.StateListAnimator
 *  android.animation.TimeInterpolator
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.InsetDrawable
 *  android.graphics.drawable.LayerDrawable
 *  android.graphics.drawable.RippleDrawable
 *  android.view.animation.AnimationUtils
 *  android.view.animation.Interpolator
 */
package android.support.design.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.support.design.widget.CircularBorderDrawable;
import android.support.design.widget.CircularBorderDrawableLollipop;
import android.support.design.widget.FloatingActionButtonIcs;
import android.support.design.widget.ShadowDrawableWrapper;
import android.support.design.widget.ShadowViewDelegate;
import android.support.design.widget.VisibilityAwareImageButton;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

@TargetApi(value=21)
class FloatingActionButtonLollipop
extends FloatingActionButtonIcs {
    private InsetDrawable mInsetDrawable;
    private final Interpolator mInterpolator;

    /*
     * Enabled aggressive block sorting
     */
    FloatingActionButtonLollipop(VisibilityAwareImageButton visibilityAwareImageButton, ShadowViewDelegate shadowViewDelegate) {
        super(visibilityAwareImageButton, shadowViewDelegate);
        visibilityAwareImageButton = visibilityAwareImageButton.isInEditMode() ? null : AnimationUtils.loadInterpolator((Context)this.mView.getContext(), (int)17563661);
        this.mInterpolator = visibilityAwareImageButton;
    }

    private Animator setupAnimator(Animator animator2) {
        animator2.setInterpolator((TimeInterpolator)this.mInterpolator);
        return animator2;
    }

    @Override
    public float getElevation() {
        return this.mView.getElevation();
    }

    @Override
    void getPadding(Rect rect) {
        if (this.mShadowViewDelegate.isCompatPaddingEnabled()) {
            float f2 = this.mShadowViewDelegate.getRadius();
            float f3 = this.getElevation() + this.mPressedTranslationZ;
            int n2 = (int)Math.ceil(ShadowDrawableWrapper.calculateHorizontalPadding(f3, f2, false));
            int n3 = (int)Math.ceil(ShadowDrawableWrapper.calculateVerticalPadding(f3, f2, false));
            rect.set(n2, n3, n2, n3);
            return;
        }
        rect.set(0, 0, 0, 0);
    }

    @Override
    void jumpDrawableToCurrentState() {
    }

    @Override
    CircularBorderDrawable newCircularDrawable() {
        return new CircularBorderDrawableLollipop();
    }

    @Override
    void onCompatShadowChanged() {
        this.updatePadding();
    }

    @Override
    void onDrawableStateChanged(int[] nArray) {
    }

    @Override
    public void onElevationChanged(float f2) {
        this.mView.setElevation(f2);
        if (this.mShadowViewDelegate.isCompatPaddingEnabled()) {
            this.updatePadding();
        }
    }

    @Override
    void onPaddingUpdated(Rect rect) {
        if (this.mShadowViewDelegate.isCompatPaddingEnabled()) {
            this.mInsetDrawable = new InsetDrawable(this.mRippleDrawable, rect.left, rect.top, rect.right, rect.bottom);
            this.mShadowViewDelegate.setBackgroundDrawable((Drawable)this.mInsetDrawable);
            return;
        }
        this.mShadowViewDelegate.setBackgroundDrawable(this.mRippleDrawable);
    }

    @Override
    void onTranslationZChanged(float f2) {
        StateListAnimator stateListAnimator = new StateListAnimator();
        stateListAnimator.addState(PRESSED_ENABLED_STATE_SET, this.setupAnimator((Animator)ObjectAnimator.ofFloat((Object)((Object)this.mView), (String)"translationZ", (float[])new float[]{f2})));
        stateListAnimator.addState(FOCUSED_ENABLED_STATE_SET, this.setupAnimator((Animator)ObjectAnimator.ofFloat((Object)((Object)this.mView), (String)"translationZ", (float[])new float[]{f2})));
        stateListAnimator.addState(EMPTY_STATE_SET, this.setupAnimator((Animator)ObjectAnimator.ofFloat((Object)((Object)this.mView), (String)"translationZ", (float[])new float[]{0.0f})));
        this.mView.setStateListAnimator(stateListAnimator);
        if (this.mShadowViewDelegate.isCompatPaddingEnabled()) {
            this.updatePadding();
        }
    }

    @Override
    boolean requirePreDrawListener() {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    void setBackgroundDrawable(ColorStateList colorStateList, PorterDuff.Mode mode, int n2, int n3) {
        this.mShapeDrawable = DrawableCompat.wrap((Drawable)this.createShapeDrawable());
        DrawableCompat.setTintList(this.mShapeDrawable, colorStateList);
        if (mode != null) {
            DrawableCompat.setTintMode(this.mShapeDrawable, mode);
        }
        if (n3 > 0) {
            this.mBorderDrawable = this.createBorderDrawable(n3, colorStateList);
            colorStateList = new LayerDrawable(new Drawable[]{this.mBorderDrawable, this.mShapeDrawable});
        } else {
            this.mBorderDrawable = null;
            colorStateList = this.mShapeDrawable;
        }
        this.mContentBackground = this.mRippleDrawable = new RippleDrawable(ColorStateList.valueOf((int)n2), (Drawable)colorStateList, null);
        this.mShadowViewDelegate.setBackgroundDrawable(this.mRippleDrawable);
    }

    @Override
    void setRippleColor(int n2) {
        if (this.mRippleDrawable instanceof RippleDrawable) {
            ((RippleDrawable)this.mRippleDrawable).setColor(ColorStateList.valueOf((int)n2));
            return;
        }
        super.setRippleColor(n2);
    }
}

