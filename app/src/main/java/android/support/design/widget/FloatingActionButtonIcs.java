/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.TimeInterpolator
 *  android.view.View
 */
package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.support.annotation.Nullable;
import android.support.design.widget.AnimationUtils;
import android.support.design.widget.FloatingActionButtonEclairMr1;
import android.support.design.widget.FloatingActionButtonImpl;
import android.support.design.widget.ShadowViewDelegate;
import android.support.design.widget.VisibilityAwareImageButton;
import android.support.v4.view.ViewCompat;
import android.view.View;

class FloatingActionButtonIcs
extends FloatingActionButtonEclairMr1 {
    private boolean mIsHiding;

    FloatingActionButtonIcs(VisibilityAwareImageButton visibilityAwareImageButton, ShadowViewDelegate shadowViewDelegate) {
        super(visibilityAwareImageButton, shadowViewDelegate);
    }

    static /* synthetic */ boolean access$002(FloatingActionButtonIcs floatingActionButtonIcs, boolean bl2) {
        floatingActionButtonIcs.mIsHiding = bl2;
        return bl2;
    }

    private void updateFromViewRotation(float f2) {
        if (this.mShadowDrawable != null) {
            this.mShadowDrawable.setRotation(-f2);
        }
        if (this.mBorderDrawable != null) {
            this.mBorderDrawable.setRotation(-f2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    void hide(final @Nullable FloatingActionButtonImpl.InternalVisibilityChangedListener internalVisibilityChangedListener, final boolean bl2) {
        if (this.mIsHiding || this.mView.getVisibility() != 0) {
            if (internalVisibilityChangedListener == null) return;
            internalVisibilityChangedListener.onHidden();
            return;
        }
        if (ViewCompat.isLaidOut((View)this.mView) && !this.mView.isInEditMode()) {
            this.mView.animate().cancel();
            this.mView.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setDuration(200L).setInterpolator((TimeInterpolator)AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){
                private boolean mCancelled;

                public void onAnimationCancel(Animator animator2) {
                    FloatingActionButtonIcs.access$002(FloatingActionButtonIcs.this, false);
                    this.mCancelled = true;
                }

                public void onAnimationEnd(Animator animator2) {
                    FloatingActionButtonIcs.access$002(FloatingActionButtonIcs.this, false);
                    if (!this.mCancelled) {
                        FloatingActionButtonIcs.this.mView.internalSetVisibility(8, bl2);
                        if (internalVisibilityChangedListener != null) {
                            internalVisibilityChangedListener.onHidden();
                        }
                    }
                }

                public void onAnimationStart(Animator animator2) {
                    FloatingActionButtonIcs.access$002(FloatingActionButtonIcs.this, true);
                    this.mCancelled = false;
                    FloatingActionButtonIcs.this.mView.internalSetVisibility(0, bl2);
                }
            });
            return;
        }
        this.mView.internalSetVisibility(8, bl2);
        if (internalVisibilityChangedListener == null) return;
        internalVisibilityChangedListener.onHidden();
    }

    @Override
    void onPreDraw() {
        this.updateFromViewRotation(this.mView.getRotation());
    }

    @Override
    boolean requirePreDrawListener() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    void show(final @Nullable FloatingActionButtonImpl.InternalVisibilityChangedListener internalVisibilityChangedListener, final boolean bl2) {
        if (!this.mIsHiding && this.mView.getVisibility() == 0) return;
        if (ViewCompat.isLaidOut((View)this.mView) && !this.mView.isInEditMode()) {
            this.mView.animate().cancel();
            if (this.mView.getVisibility() != 0) {
                this.mView.setAlpha(0.0f);
                this.mView.setScaleY(0.0f);
                this.mView.setScaleX(0.0f);
            }
            this.mView.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200L).setInterpolator((TimeInterpolator)AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                public void onAnimationEnd(Animator animator2) {
                    if (internalVisibilityChangedListener != null) {
                        internalVisibilityChangedListener.onShown();
                    }
                }

                public void onAnimationStart(Animator animator2) {
                    FloatingActionButtonIcs.this.mView.internalSetVisibility(0, bl2);
                }
            });
            return;
        } else {
            this.mView.internalSetVisibility(0, bl2);
            this.mView.setAlpha(1.0f);
            this.mView.setScaleY(1.0f);
            this.mView.setScaleX(1.0f);
            if (internalVisibilityChangedListener == null) return;
            internalVisibilityChangedListener.onShown();
            return;
        }
    }
}

