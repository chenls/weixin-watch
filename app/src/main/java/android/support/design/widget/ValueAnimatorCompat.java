/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.view.animation.Interpolator
 */
package android.support.design.widget;

import android.view.animation.Interpolator;

class ValueAnimatorCompat {
    private final Impl mImpl;

    ValueAnimatorCompat(Impl impl) {
        this.mImpl = impl;
    }

    public void cancel() {
        this.mImpl.cancel();
    }

    public void end() {
        this.mImpl.end();
    }

    public float getAnimatedFloatValue() {
        return this.mImpl.getAnimatedFloatValue();
    }

    public float getAnimatedFraction() {
        return this.mImpl.getAnimatedFraction();
    }

    public int getAnimatedIntValue() {
        return this.mImpl.getAnimatedIntValue();
    }

    public long getDuration() {
        return this.mImpl.getDuration();
    }

    public boolean isRunning() {
        return this.mImpl.isRunning();
    }

    public void setDuration(int n2) {
        this.mImpl.setDuration(n2);
    }

    public void setFloatValues(float f2, float f3) {
        this.mImpl.setFloatValues(f2, f3);
    }

    public void setIntValues(int n2, int n3) {
        this.mImpl.setIntValues(n2, n3);
    }

    public void setInterpolator(Interpolator interpolator2) {
        this.mImpl.setInterpolator(interpolator2);
    }

    public void setListener(final AnimatorListener animatorListener) {
        if (animatorListener != null) {
            this.mImpl.setListener(new Impl.AnimatorListenerProxy(){

                @Override
                public void onAnimationCancel() {
                    animatorListener.onAnimationCancel(ValueAnimatorCompat.this);
                }

                @Override
                public void onAnimationEnd() {
                    animatorListener.onAnimationEnd(ValueAnimatorCompat.this);
                }

                @Override
                public void onAnimationStart() {
                    animatorListener.onAnimationStart(ValueAnimatorCompat.this);
                }
            });
            return;
        }
        this.mImpl.setListener(null);
    }

    public void setUpdateListener(final AnimatorUpdateListener animatorUpdateListener) {
        if (animatorUpdateListener != null) {
            this.mImpl.setUpdateListener(new Impl.AnimatorUpdateListenerProxy(){

                @Override
                public void onAnimationUpdate() {
                    animatorUpdateListener.onAnimationUpdate(ValueAnimatorCompat.this);
                }
            });
            return;
        }
        this.mImpl.setUpdateListener(null);
    }

    public void start() {
        this.mImpl.start();
    }

    static interface AnimatorListener {
        public void onAnimationCancel(ValueAnimatorCompat var1);

        public void onAnimationEnd(ValueAnimatorCompat var1);

        public void onAnimationStart(ValueAnimatorCompat var1);
    }

    static class AnimatorListenerAdapter
    implements AnimatorListener {
        AnimatorListenerAdapter() {
        }

        @Override
        public void onAnimationCancel(ValueAnimatorCompat valueAnimatorCompat) {
        }

        @Override
        public void onAnimationEnd(ValueAnimatorCompat valueAnimatorCompat) {
        }

        @Override
        public void onAnimationStart(ValueAnimatorCompat valueAnimatorCompat) {
        }
    }

    static interface AnimatorUpdateListener {
        public void onAnimationUpdate(ValueAnimatorCompat var1);
    }

    static interface Creator {
        public ValueAnimatorCompat createAnimator();
    }

    static abstract class Impl {
        Impl() {
        }

        abstract void cancel();

        abstract void end();

        abstract float getAnimatedFloatValue();

        abstract float getAnimatedFraction();

        abstract int getAnimatedIntValue();

        abstract long getDuration();

        abstract boolean isRunning();

        abstract void setDuration(int var1);

        abstract void setFloatValues(float var1, float var2);

        abstract void setIntValues(int var1, int var2);

        abstract void setInterpolator(Interpolator var1);

        abstract void setListener(AnimatorListenerProxy var1);

        abstract void setUpdateListener(AnimatorUpdateListenerProxy var1);

        abstract void start();

        static interface AnimatorListenerProxy {
            public void onAnimationCancel();

            public void onAnimationEnd();

            public void onAnimationStart();
        }

        static interface AnimatorUpdateListenerProxy {
            public void onAnimationUpdate();
        }
    }
}

