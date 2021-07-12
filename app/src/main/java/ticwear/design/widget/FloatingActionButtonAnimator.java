/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ObjectAnimator
 *  android.animation.StateListAnimator
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.view.View
 *  android.view.animation.AnimationUtils
 *  android.view.animation.Interpolator
 */
package ticwear.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.animation.Interpolator;
import ticwear.design.widget.AnimationUtils;
import ticwear.design.widget.VisibilityAwareImageButton;

class FloatingActionButtonAnimator {
    static final int[] EMPTY_STATE_SET;
    static final int[] FOCUSED_ENABLED_STATE_SET;
    static final int[] PRESSED_ENABLED_STATE_SET;
    static final int SHOW_HIDE_ANIM_DURATION = 200;
    private AnimState mAnimState = AnimState.Idle;
    private Interpolator mInterpolator;
    private int mMinimizeTranslationX = 0;
    private int mMinimizeTranslationY = 0;
    final VisibilityAwareImageButton mView;

    static {
        PRESSED_ENABLED_STATE_SET = new int[]{16842919, 16842910};
        FOCUSED_ENABLED_STATE_SET = new int[]{16842908, 16842910};
        EMPTY_STATE_SET = new int[0];
    }

    FloatingActionButtonAnimator(VisibilityAwareImageButton visibilityAwareImageButton) {
        this.mView = visibilityAwareImageButton;
        if (!visibilityAwareImageButton.isInEditMode()) {
            this.mInterpolator = android.view.animation.AnimationUtils.loadInterpolator((Context)this.mView.getContext(), (int)17563661);
        }
    }

    static /* synthetic */ AnimState access$002(FloatingActionButtonAnimator floatingActionButtonAnimator, AnimState animState) {
        floatingActionButtonAnimator.mAnimState = animState;
        return animState;
    }

    private boolean isHiding() {
        return this.mAnimState == AnimState.Hiding;
    }

    private boolean isMinimizing() {
        return this.mAnimState == AnimState.Minimizing;
    }

    private boolean notShown() {
        return this.isHiding() || this.isMinimizing() || this.mView.getVisibility() != 0 || this.mView.getScaleX() != 1.0f;
    }

    private Animator setupAnimator(Animator animator2) {
        animator2.setInterpolator((TimeInterpolator)this.mInterpolator);
        return animator2;
    }

    /*
     * Enabled aggressive block sorting
     */
    void hide(final @Nullable InternalVisibilityChangedListener internalVisibilityChangedListener, final boolean bl2) {
        if (this.isHiding() || this.mView.getVisibility() != 0) {
            if (internalVisibilityChangedListener == null) return;
            internalVisibilityChangedListener.onHidden();
            return;
        }
        if (ViewCompat.isLaidOut((View)this.mView) && !this.mView.isInEditMode()) {
            this.mView.animate().cancel();
            this.mView.animate().scaleX(0.0f).scaleY(0.0f).translationX((float)this.mMinimizeTranslationX).translationY((float)this.mMinimizeTranslationY).setDuration(200L).setInterpolator((TimeInterpolator)AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){
                private boolean mCancelled;

                public void onAnimationCancel(Animator animator2) {
                    FloatingActionButtonAnimator.access$002(FloatingActionButtonAnimator.this, AnimState.Idle);
                    this.mCancelled = true;
                }

                public void onAnimationEnd(Animator animator2) {
                    FloatingActionButtonAnimator.access$002(FloatingActionButtonAnimator.this, AnimState.Idle);
                    if (!this.mCancelled) {
                        FloatingActionButtonAnimator.this.mView.internalSetVisibility(8, bl2);
                        if (internalVisibilityChangedListener != null) {
                            internalVisibilityChangedListener.onHidden();
                        }
                    }
                }

                public void onAnimationStart(Animator animator2) {
                    FloatingActionButtonAnimator.access$002(FloatingActionButtonAnimator.this, AnimState.Hiding);
                    this.mCancelled = false;
                    FloatingActionButtonAnimator.this.mView.internalSetVisibility(0, bl2);
                }
            });
            return;
        }
        this.mAnimState = AnimState.Idle;
        this.mView.internalSetVisibility(8, bl2);
        if (internalVisibilityChangedListener == null) return;
        internalVisibilityChangedListener.onHidden();
    }

    /*
     * Enabled aggressive block sorting
     */
    void minimize(final @Nullable InternalVisibilityChangedListener internalVisibilityChangedListener, final boolean bl2) {
        if (this.isMinimizing()) {
            if (internalVisibilityChangedListener == null) return;
            internalVisibilityChangedListener.onMinimum();
            return;
        }
        if (ViewCompat.isLaidOut((View)this.mView) && !this.mView.isInEditMode()) {
            this.mView.animate().cancel();
            if (this.mView.getVisibility() != 0) {
                this.mView.setScaleY(0.0f);
                this.mView.setScaleX(0.0f);
                this.mView.setTranslationX(this.mMinimizeTranslationX);
                this.mView.setTranslationY(this.mMinimizeTranslationY);
                this.mView.setImageAlpha(0);
            }
            this.mView.animate().scaleX(0.1f).scaleY(0.1f).translationX((float)this.mMinimizeTranslationX).translationY((float)this.mMinimizeTranslationY).setDuration(200L).setInterpolator((TimeInterpolator)AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                public void onAnimationEnd(Animator animator2) {
                    FloatingActionButtonAnimator.access$002(FloatingActionButtonAnimator.this, AnimState.Idle);
                    FloatingActionButtonAnimator.this.mView.setClickable(false);
                    FloatingActionButtonAnimator.this.mView.setImageAlpha(0);
                    if (internalVisibilityChangedListener != null) {
                        internalVisibilityChangedListener.onMinimum();
                    }
                }

                public void onAnimationStart(Animator animator2) {
                    FloatingActionButtonAnimator.access$002(FloatingActionButtonAnimator.this, AnimState.Minimizing);
                    FloatingActionButtonAnimator.this.mView.internalSetVisibility(0, bl2);
                }
            });
            return;
        }
        this.mAnimState = AnimState.Idle;
        this.mView.internalSetVisibility(0, bl2);
        this.mView.setScaleY(0.1f);
        this.mView.setScaleX(0.1f);
        this.mView.setTranslationX(this.mMinimizeTranslationX);
        this.mView.setTranslationY(this.mMinimizeTranslationY);
        this.mView.setClickable(false);
        this.mView.setImageAlpha(0);
        if (internalVisibilityChangedListener == null) return;
        internalVisibilityChangedListener.onMinimum();
    }

    void setMinimizeTranslation(int n2, int n3) {
        this.mMinimizeTranslationX = n2;
        this.mMinimizeTranslationY = n3;
    }

    void setPressedTranslationZ(float f2) {
        StateListAnimator stateListAnimator;
        StateListAnimator stateListAnimator2 = stateListAnimator = this.mView.getStateListAnimator();
        if (stateListAnimator == null) {
            stateListAnimator2 = new StateListAnimator();
        }
        stateListAnimator2.addState(PRESSED_ENABLED_STATE_SET, this.setupAnimator((Animator)ObjectAnimator.ofFloat((Object)((Object)this.mView), (String)"translationZ", (float[])new float[]{f2})));
        stateListAnimator2.addState(FOCUSED_ENABLED_STATE_SET, this.setupAnimator((Animator)ObjectAnimator.ofFloat((Object)((Object)this.mView), (String)"translationZ", (float[])new float[]{f2})));
        stateListAnimator2.addState(EMPTY_STATE_SET, this.setupAnimator((Animator)ObjectAnimator.ofFloat((Object)((Object)this.mView), (String)"translationZ", (float[])new float[]{0.0f})));
        this.mView.setStateListAnimator(stateListAnimator2);
    }

    /*
     * Enabled aggressive block sorting
     */
    void show(final @Nullable InternalVisibilityChangedListener internalVisibilityChangedListener, final boolean bl2) {
        if (!this.notShown()) return;
        if (ViewCompat.isLaidOut((View)this.mView) && !this.mView.isInEditMode()) {
            this.mView.animate().cancel();
            if (this.mView.getVisibility() != 0) {
                this.mView.setScaleY(0.0f);
                this.mView.setScaleX(0.0f);
                this.mView.setTranslationX(this.mMinimizeTranslationX);
                this.mView.setTranslationY(this.mMinimizeTranslationY);
            }
            this.mView.animate().scaleX(1.0f).scaleY(1.0f).translationX(0.0f).translationY(0.0f).setDuration(200L).setInterpolator((TimeInterpolator)AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                public void onAnimationEnd(Animator animator2) {
                    if (internalVisibilityChangedListener != null) {
                        internalVisibilityChangedListener.onShown();
                    }
                }

                public void onAnimationStart(Animator animator2) {
                    FloatingActionButtonAnimator.access$002(FloatingActionButtonAnimator.this, AnimState.Idle);
                    FloatingActionButtonAnimator.this.mView.internalSetVisibility(0, bl2);
                    FloatingActionButtonAnimator.this.mView.setClickable(true);
                    FloatingActionButtonAnimator.this.mView.setImageAlpha(255);
                }
            });
            return;
        } else {
            this.mAnimState = AnimState.Idle;
            this.mView.internalSetVisibility(0, bl2);
            this.mView.setScaleY(1.0f);
            this.mView.setScaleX(1.0f);
            this.mView.setTranslationX(0.0f);
            this.mView.setTranslationY(0.0f);
            this.mView.setClickable(true);
            this.mView.setImageAlpha(255);
            if (internalVisibilityChangedListener == null) return;
            internalVisibilityChangedListener.onShown();
            return;
        }
    }

    private static enum AnimState {
        Idle,
        Hiding,
        Minimizing;

    }

    static interface InternalVisibilityChangedListener {
        public void onHidden();

        public void onMinimum();

        public void onShown();
    }
}

