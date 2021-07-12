/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.annotation.TargetApi
 */
package android.support.wearable.view;

import android.animation.Animator;
import android.annotation.TargetApi;

@TargetApi(value=20)
public class SimpleAnimatorListener
implements Animator.AnimatorListener {
    private boolean mWasCanceled;

    public void onAnimationCancel(Animator animator2) {
        this.mWasCanceled = true;
    }

    public void onAnimationComplete(Animator animator2) {
    }

    public void onAnimationEnd(Animator animator2) {
        if (!this.mWasCanceled) {
            this.onAnimationComplete(animator2);
        }
    }

    public void onAnimationRepeat(Animator animator2) {
    }

    public void onAnimationStart(Animator animator2) {
        this.mWasCanceled = false;
    }

    public boolean wasCanceled() {
        return this.mWasCanceled;
    }
}

