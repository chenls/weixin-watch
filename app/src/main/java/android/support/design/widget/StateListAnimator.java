/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.util.StateSet
 *  android.view.View
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 */
package android.support.design.widget;

import android.util.StateSet;
import android.view.View;
import android.view.animation.Animation;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

final class StateListAnimator {
    private Animation.AnimationListener mAnimationListener;
    private Tuple mLastMatch = null;
    private Animation mRunningAnimation = null;
    private final ArrayList<Tuple> mTuples = new ArrayList();
    private WeakReference<View> mViewRef;

    StateListAnimator() {
        this.mAnimationListener = new Animation.AnimationListener(){

            public void onAnimationEnd(Animation animation) {
                if (StateListAnimator.this.mRunningAnimation == animation) {
                    StateListAnimator.access$002(StateListAnimator.this, null);
                }
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        };
    }

    static /* synthetic */ Animation access$002(StateListAnimator stateListAnimator, Animation animation) {
        stateListAnimator.mRunningAnimation = animation;
        return animation;
    }

    private void cancel() {
        if (this.mRunningAnimation != null) {
            View view = this.getTarget();
            if (view != null && view.getAnimation() == this.mRunningAnimation) {
                view.clearAnimation();
            }
            this.mRunningAnimation = null;
        }
    }

    private void clearTarget() {
        View view = this.getTarget();
        int n2 = this.mTuples.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            Animation animation = this.mTuples.get((int)i2).mAnimation;
            if (view.getAnimation() != animation) continue;
            view.clearAnimation();
        }
        this.mViewRef = null;
        this.mLastMatch = null;
        this.mRunningAnimation = null;
    }

    private void start(Tuple tuple) {
        this.mRunningAnimation = tuple.mAnimation;
        tuple = this.getTarget();
        if (tuple != null) {
            tuple.startAnimation(this.mRunningAnimation);
        }
    }

    public void addState(int[] object, Animation animation) {
        object = new Tuple((int[])object, animation);
        animation.setAnimationListener(this.mAnimationListener);
        this.mTuples.add((Tuple)object);
    }

    Animation getRunningAnimation() {
        return this.mRunningAnimation;
    }

    View getTarget() {
        if (this.mViewRef == null) {
            return null;
        }
        return (View)this.mViewRef.get();
    }

    ArrayList<Tuple> getTuples() {
        return this.mTuples;
    }

    public void jumpToCurrentState() {
        View view;
        if (this.mRunningAnimation != null && (view = this.getTarget()) != null && view.getAnimation() == this.mRunningAnimation) {
            view.clearAnimation();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void setState(int[] object) {
        Tuple tuple;
        Tuple tuple2 = null;
        int n2 = this.mTuples.size();
        int n3 = 0;
        while (true) {
            block8: {
                block7: {
                    tuple = tuple2;
                    if (n3 >= n2) break block7;
                    tuple = this.mTuples.get(n3);
                    if (!StateSet.stateSetMatches((int[])tuple.mSpecs, (int[])object)) break block8;
                }
                if (tuple != this.mLastMatch) break;
                return;
            }
            ++n3;
        }
        if (this.mLastMatch != null) {
            this.cancel();
        }
        this.mLastMatch = tuple;
        View view = (View)this.mViewRef.get();
        if (tuple == null || view == null || view.getVisibility() != 0) {
            return;
        }
        this.start(tuple);
    }

    /*
     * Enabled aggressive block sorting
     */
    void setTarget(View view) {
        block5: {
            block4: {
                View view2 = this.getTarget();
                if (view2 == view) break block4;
                if (view2 != null) {
                    this.clearTarget();
                }
                if (view != null) break block5;
            }
            return;
        }
        this.mViewRef = new WeakReference<View>(view);
    }

    static class Tuple {
        final Animation mAnimation;
        final int[] mSpecs;

        private Tuple(int[] nArray, Animation animation) {
            this.mSpecs = nArray;
            this.mAnimation = animation;
        }

        Animation getAnimation() {
            return this.mAnimation;
        }

        int[] getSpecs() {
            return this.mSpecs;
        }
    }
}

