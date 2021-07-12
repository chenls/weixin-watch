/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.LayerDrawable
 *  android.view.View
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.view.animation.AnimationUtils
 *  android.view.animation.Transformation
 */
package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.Nullable;
import android.support.design.R;
import android.support.design.widget.AnimationUtils;
import android.support.design.widget.FloatingActionButtonImpl;
import android.support.design.widget.ShadowDrawableWrapper;
import android.support.design.widget.ShadowViewDelegate;
import android.support.design.widget.StateListAnimator;
import android.support.design.widget.VisibilityAwareImageButton;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

class FloatingActionButtonEclairMr1
extends FloatingActionButtonImpl {
    private int mAnimationDuration;
    private boolean mIsHiding;
    ShadowDrawableWrapper mShadowDrawable;
    private StateListAnimator mStateListAnimator;

    FloatingActionButtonEclairMr1(VisibilityAwareImageButton visibilityAwareImageButton, ShadowViewDelegate shadowViewDelegate) {
        super(visibilityAwareImageButton, shadowViewDelegate);
        this.mAnimationDuration = visibilityAwareImageButton.getResources().getInteger(0x10E0000);
        this.mStateListAnimator = new StateListAnimator();
        this.mStateListAnimator.setTarget((View)visibilityAwareImageButton);
        this.mStateListAnimator.addState(PRESSED_ENABLED_STATE_SET, this.setupAnimation(new ElevateToTranslationZAnimation()));
        this.mStateListAnimator.addState(FOCUSED_ENABLED_STATE_SET, this.setupAnimation(new ElevateToTranslationZAnimation()));
        this.mStateListAnimator.addState(EMPTY_STATE_SET, this.setupAnimation(new ResetElevationAnimation()));
    }

    static /* synthetic */ boolean access$202(FloatingActionButtonEclairMr1 floatingActionButtonEclairMr1, boolean bl2) {
        floatingActionButtonEclairMr1.mIsHiding = bl2;
        return bl2;
    }

    private static ColorStateList createColorStateList(int n2) {
        int[][] nArrayArray = new int[3][];
        int[] nArray = new int[3];
        nArrayArray[0] = FOCUSED_ENABLED_STATE_SET;
        nArray[0] = n2;
        int n3 = 0 + 1;
        nArrayArray[n3] = PRESSED_ENABLED_STATE_SET;
        nArray[n3] = n2;
        n2 = n3 + 1;
        nArrayArray[n2] = new int[0];
        nArray[n2] = 0;
        return new ColorStateList((int[][])nArrayArray, nArray);
    }

    private Animation setupAnimation(Animation animation) {
        animation.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        animation.setDuration((long)this.mAnimationDuration);
        return animation;
    }

    @Override
    float getElevation() {
        return this.mElevation;
    }

    @Override
    void getPadding(Rect rect) {
        this.mShadowDrawable.getPadding(rect);
    }

    @Override
    void hide(final @Nullable FloatingActionButtonImpl.InternalVisibilityChangedListener internalVisibilityChangedListener, final boolean bl2) {
        if (this.mIsHiding || this.mView.getVisibility() != 0) {
            if (internalVisibilityChangedListener != null) {
                internalVisibilityChangedListener.onHidden();
            }
            return;
        }
        Animation animation = android.view.animation.AnimationUtils.loadAnimation((Context)this.mView.getContext(), (int)R.anim.design_fab_out);
        animation.setInterpolator(AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
        animation.setDuration(200L);
        animation.setAnimationListener((Animation.AnimationListener)new AnimationUtils.AnimationListenerAdapter(){

            @Override
            public void onAnimationEnd(Animation animation) {
                FloatingActionButtonEclairMr1.access$202(FloatingActionButtonEclairMr1.this, false);
                FloatingActionButtonEclairMr1.this.mView.internalSetVisibility(8, bl2);
                if (internalVisibilityChangedListener != null) {
                    internalVisibilityChangedListener.onHidden();
                }
            }

            @Override
            public void onAnimationStart(Animation animation) {
                FloatingActionButtonEclairMr1.access$202(FloatingActionButtonEclairMr1.this, true);
            }
        });
        this.mView.startAnimation(animation);
    }

    @Override
    void jumpDrawableToCurrentState() {
        this.mStateListAnimator.jumpToCurrentState();
    }

    @Override
    void onCompatShadowChanged() {
    }

    @Override
    void onDrawableStateChanged(int[] nArray) {
        this.mStateListAnimator.setState(nArray);
    }

    @Override
    void onElevationChanged(float f2) {
        if (this.mShadowDrawable != null) {
            this.mShadowDrawable.setShadowSize(f2, this.mPressedTranslationZ + f2);
            this.updatePadding();
        }
    }

    @Override
    void onTranslationZChanged(float f2) {
        if (this.mShadowDrawable != null) {
            this.mShadowDrawable.setMaxShadowSize(this.mElevation + f2);
            this.updatePadding();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    void setBackgroundDrawable(ColorStateList drawableArray, PorterDuff.Mode mode, int n2, int n3) {
        this.mShapeDrawable = DrawableCompat.wrap((Drawable)this.createShapeDrawable());
        DrawableCompat.setTintList(this.mShapeDrawable, (ColorStateList)drawableArray);
        if (mode != null) {
            DrawableCompat.setTintMode(this.mShapeDrawable, mode);
        }
        this.mRippleDrawable = DrawableCompat.wrap((Drawable)this.createShapeDrawable());
        DrawableCompat.setTintList(this.mRippleDrawable, FloatingActionButtonEclairMr1.createColorStateList(n2));
        if (n3 > 0) {
            this.mBorderDrawable = this.createBorderDrawable(n3, (ColorStateList)drawableArray);
            drawableArray = new Drawable[]{this.mBorderDrawable, this.mShapeDrawable, this.mRippleDrawable};
        } else {
            this.mBorderDrawable = null;
            drawableArray = new Drawable[]{this.mShapeDrawable, this.mRippleDrawable};
        }
        this.mContentBackground = new LayerDrawable(drawableArray);
        this.mShadowDrawable = new ShadowDrawableWrapper(this.mView.getResources(), this.mContentBackground, this.mShadowViewDelegate.getRadius(), this.mElevation, this.mElevation + this.mPressedTranslationZ);
        this.mShadowDrawable.setAddPaddingForCorners(false);
        this.mShadowViewDelegate.setBackgroundDrawable(this.mShadowDrawable);
    }

    @Override
    void setBackgroundTintList(ColorStateList colorStateList) {
        if (this.mShapeDrawable != null) {
            DrawableCompat.setTintList(this.mShapeDrawable, colorStateList);
        }
        if (this.mBorderDrawable != null) {
            this.mBorderDrawable.setBorderTint(colorStateList);
        }
    }

    @Override
    void setBackgroundTintMode(PorterDuff.Mode mode) {
        if (this.mShapeDrawable != null) {
            DrawableCompat.setTintMode(this.mShapeDrawable, mode);
        }
    }

    @Override
    void setRippleColor(int n2) {
        if (this.mRippleDrawable != null) {
            DrawableCompat.setTintList(this.mRippleDrawable, FloatingActionButtonEclairMr1.createColorStateList(n2));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    void show(final @Nullable FloatingActionButtonImpl.InternalVisibilityChangedListener internalVisibilityChangedListener, boolean bl2) {
        if (this.mView.getVisibility() != 0 || this.mIsHiding) {
            this.mView.clearAnimation();
            this.mView.internalSetVisibility(0, bl2);
            Animation animation = android.view.animation.AnimationUtils.loadAnimation((Context)this.mView.getContext(), (int)R.anim.design_fab_in);
            animation.setDuration(200L);
            animation.setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
            animation.setAnimationListener((Animation.AnimationListener)new AnimationUtils.AnimationListenerAdapter(){

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (internalVisibilityChangedListener != null) {
                        internalVisibilityChangedListener.onShown();
                    }
                }
            });
            this.mView.startAnimation(animation);
            return;
        } else {
            if (internalVisibilityChangedListener == null) return;
            internalVisibilityChangedListener.onShown();
            return;
        }
    }

    private abstract class BaseShadowAnimation
    extends Animation {
        private float mShadowSizeDiff;
        private float mShadowSizeStart;

        private BaseShadowAnimation() {
        }

        protected void applyTransformation(float f2, Transformation transformation) {
            FloatingActionButtonEclairMr1.this.mShadowDrawable.setShadowSize(this.mShadowSizeStart + this.mShadowSizeDiff * f2);
        }

        protected abstract float getTargetShadowSize();

        public void reset() {
            super.reset();
            this.mShadowSizeStart = FloatingActionButtonEclairMr1.this.mShadowDrawable.getShadowSize();
            this.mShadowSizeDiff = this.getTargetShadowSize() - this.mShadowSizeStart;
        }
    }

    private class ElevateToTranslationZAnimation
    extends BaseShadowAnimation {
        private ElevateToTranslationZAnimation() {
        }

        @Override
        protected float getTargetShadowSize() {
            return FloatingActionButtonEclairMr1.this.mElevation + FloatingActionButtonEclairMr1.this.mPressedTranslationZ;
        }
    }

    private class ResetElevationAnimation
    extends BaseShadowAnimation {
        private ResetElevationAnimation() {
        }

        @Override
        protected float getTargetShadowSize() {
            return FloatingActionButtonEclairMr1.this.mElevation;
        }
    }
}

