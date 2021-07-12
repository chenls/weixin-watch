/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.animation.Interpolator
 */
package android.support.v7.view;

import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.view.View;
import android.view.animation.Interpolator;
import java.util.ArrayList;
import java.util.Iterator;

public class ViewPropertyAnimatorCompatSet {
    private final ArrayList<ViewPropertyAnimatorCompat> mAnimators;
    private long mDuration = -1L;
    private Interpolator mInterpolator;
    private boolean mIsStarted;
    private ViewPropertyAnimatorListener mListener;
    private final ViewPropertyAnimatorListenerAdapter mProxyListener = new ViewPropertyAnimatorListenerAdapter(){
        private int mProxyEndCount = 0;
        private boolean mProxyStarted = false;

        @Override
        public void onAnimationEnd(View view) {
            int n2;
            this.mProxyEndCount = n2 = this.mProxyEndCount + 1;
            if (n2 == ViewPropertyAnimatorCompatSet.this.mAnimators.size()) {
                if (ViewPropertyAnimatorCompatSet.this.mListener != null) {
                    ViewPropertyAnimatorCompatSet.this.mListener.onAnimationEnd(null);
                }
                this.onEnd();
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onAnimationStart(View view) {
            block3: {
                block2: {
                    if (this.mProxyStarted) break block2;
                    this.mProxyStarted = true;
                    if (ViewPropertyAnimatorCompatSet.this.mListener != null) break block3;
                }
                return;
            }
            ViewPropertyAnimatorCompatSet.this.mListener.onAnimationStart(null);
        }

        void onEnd() {
            this.mProxyEndCount = 0;
            this.mProxyStarted = false;
            ViewPropertyAnimatorCompatSet.this.onAnimationsEnded();
        }
    };

    public ViewPropertyAnimatorCompatSet() {
        this.mAnimators = new ArrayList();
    }

    private void onAnimationsEnded() {
        this.mIsStarted = false;
    }

    public void cancel() {
        if (!this.mIsStarted) {
            return;
        }
        Iterator<ViewPropertyAnimatorCompat> iterator = this.mAnimators.iterator();
        while (iterator.hasNext()) {
            iterator.next().cancel();
        }
        this.mIsStarted = false;
    }

    public ViewPropertyAnimatorCompatSet play(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat) {
        if (!this.mIsStarted) {
            this.mAnimators.add(viewPropertyAnimatorCompat);
        }
        return this;
    }

    public ViewPropertyAnimatorCompatSet playSequentially(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2) {
        this.mAnimators.add(viewPropertyAnimatorCompat);
        viewPropertyAnimatorCompat2.setStartDelay(viewPropertyAnimatorCompat.getDuration());
        this.mAnimators.add(viewPropertyAnimatorCompat2);
        return this;
    }

    public ViewPropertyAnimatorCompatSet setDuration(long l2) {
        if (!this.mIsStarted) {
            this.mDuration = l2;
        }
        return this;
    }

    public ViewPropertyAnimatorCompatSet setInterpolator(Interpolator interpolator2) {
        if (!this.mIsStarted) {
            this.mInterpolator = interpolator2;
        }
        return this;
    }

    public ViewPropertyAnimatorCompatSet setListener(ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        if (!this.mIsStarted) {
            this.mListener = viewPropertyAnimatorListener;
        }
        return this;
    }

    public void start() {
        if (this.mIsStarted) {
            return;
        }
        for (ViewPropertyAnimatorCompat viewPropertyAnimatorCompat : this.mAnimators) {
            if (this.mDuration >= 0L) {
                viewPropertyAnimatorCompat.setDuration(this.mDuration);
            }
            if (this.mInterpolator != null) {
                viewPropertyAnimatorCompat.setInterpolator(this.mInterpolator);
            }
            if (this.mListener != null) {
                viewPropertyAnimatorCompat.setListener(this.mProxyListener);
            }
            viewPropertyAnimatorCompat.start();
        }
        this.mIsStarted = true;
    }
}

