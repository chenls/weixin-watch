/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.ViewConfiguration
 */
package android.support.design.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.MathUtils;
import android.support.design.widget.ViewOffsetBehavior;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

abstract class HeaderBehavior<V extends View>
extends ViewOffsetBehavior<V> {
    private static final int INVALID_POINTER = -1;
    private int mActivePointerId = -1;
    private Runnable mFlingRunnable;
    private boolean mIsBeingDragged;
    private int mLastMotionY;
    private ScrollerCompat mScroller;
    private int mTouchSlop = -1;
    private VelocityTracker mVelocityTracker;

    public HeaderBehavior() {
    }

    public HeaderBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void ensureVelocityTracker() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    boolean canDragView(V v2) {
        return false;
    }

    final boolean fling(CoordinatorLayout coordinatorLayout, V v2, int n2, int n3, float f2) {
        if (this.mFlingRunnable != null) {
            v2.removeCallbacks(this.mFlingRunnable);
            this.mFlingRunnable = null;
        }
        if (this.mScroller == null) {
            this.mScroller = ScrollerCompat.create(v2.getContext());
        }
        this.mScroller.fling(0, this.getTopAndBottomOffset(), 0, Math.round(f2), 0, 0, n2, n3);
        if (this.mScroller.computeScrollOffset()) {
            this.mFlingRunnable = new FlingRunnable(this, coordinatorLayout, v2);
            ViewCompat.postOnAnimation(v2, this.mFlingRunnable);
            return true;
        }
        this.onFlingFinished(coordinatorLayout, v2);
        return false;
    }

    int getMaxDragOffset(V v2) {
        return -v2.getHeight();
    }

    int getScrollRangeForDragFling(V v2) {
        return v2.getHeight();
    }

    int getTopBottomOffsetForScrollingSibling() {
        return this.getTopAndBottomOffset();
    }

    void onFlingFinished(CoordinatorLayout coordinatorLayout, V v2) {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v2, MotionEvent motionEvent) {
        if (this.mTouchSlop < 0) {
            this.mTouchSlop = ViewConfiguration.get((Context)coordinatorLayout.getContext()).getScaledTouchSlop();
        }
        if (motionEvent.getAction() == 2 && this.mIsBeingDragged) {
            return true;
        }
        switch (MotionEventCompat.getActionMasked(motionEvent)) {
            case 0: {
                this.mIsBeingDragged = false;
                int n2 = (int)motionEvent.getX();
                int n3 = (int)motionEvent.getY();
                if (!this.canDragView(v2) || !coordinatorLayout.isPointInChildBounds((View)v2, n2, n3)) break;
                this.mLastMotionY = n3;
                this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, 0);
                this.ensureVelocityTracker();
                break;
            }
            case 2: {
                int n4 = this.mActivePointerId;
                if (n4 == -1 || (n4 = MotionEventCompat.findPointerIndex(motionEvent, n4)) == -1 || Math.abs((n4 = (int)MotionEventCompat.getY(motionEvent, n4)) - this.mLastMotionY) <= this.mTouchSlop) break;
                this.mIsBeingDragged = true;
                this.mLastMotionY = n4;
                break;
            }
            case 1: 
            case 3: {
                this.mIsBeingDragged = false;
                this.mActivePointerId = -1;
                if (this.mVelocityTracker == null) break;
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
                break;
            }
        }
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.addMovement(motionEvent);
        }
        return this.mIsBeingDragged;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public boolean onTouchEvent(CoordinatorLayout var1_1, V var2_2, MotionEvent var3_3) {
        if (this.mTouchSlop < 0) {
            this.mTouchSlop = ViewConfiguration.get((Context)var1_1.getContext()).getScaledTouchSlop();
        }
        switch (MotionEventCompat.getActionMasked(var3_3)) lbl-1000:
        // 3 sources

        {
            default: lbl-1000:
            // 3 sources

            {
                while (true) {
                    if (this.mVelocityTracker != null) {
                        this.mVelocityTracker.addMovement(var3_3);
                    }
                    return true;
                }
            }
            case 0: {
                var5_4 = (int)var3_3.getX();
                var6_6 = (int)var3_3.getY();
                if (!var1_1.isPointInChildBounds((View)var2_2, var5_4, var6_6) || !this.canDragView(var2_2)) ** GOTO lbl17
                this.mLastMotionY = var6_6;
                this.mActivePointerId = MotionEventCompat.getPointerId(var3_3, 0);
                this.ensureVelocityTracker();
                ** GOTO lbl-1000
lbl17:
                // 1 sources

                return false;
            }
            case 2: {
                var5_5 = MotionEventCompat.findPointerIndex(var3_3, this.mActivePointerId);
                if (var5_5 == -1) {
                    return false;
                }
                var7_8 = (int)MotionEventCompat.getY(var3_3, var5_5);
                var5_5 = var6_7 = this.mLastMotionY - var7_8;
                if (this.mIsBeingDragged) ** GOTO lbl30
                var5_5 = var6_7;
                if (Math.abs(var6_7) <= this.mTouchSlop) ** GOTO lbl30
                this.mIsBeingDragged = true;
                if (var6_7 <= 0) ** GOTO lbl36
                var5_5 = var6_7 - this.mTouchSlop;
lbl30:
                // 4 sources

                while (true) {
                    if (this.mIsBeingDragged) {
                        this.mLastMotionY = var7_8;
                        this.scroll(var1_1, var2_2, var5_5, this.getMaxDragOffset(var2_2), 0);
                    }
                    ** GOTO lbl-1000
                    break;
                }
lbl36:
                // 1 sources

                var5_5 = var6_7 + this.mTouchSlop;
                ** continue;
            }
            case 1: {
                if (this.mVelocityTracker == null) break;
                this.mVelocityTracker.addMovement(var3_3);
                this.mVelocityTracker.computeCurrentVelocity(1000);
                var4_9 = VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId);
                this.fling(var1_1, var2_2, -this.getScrollRangeForDragFling(var2_2), 0, var4_9);
            }
            case 3: 
        }
        this.mIsBeingDragged = false;
        this.mActivePointerId = -1;
        if (this.mVelocityTracker == null) ** GOTO lbl-1000
        this.mVelocityTracker.recycle();
        this.mVelocityTracker = null;
        ** while (true)
    }

    final int scroll(CoordinatorLayout coordinatorLayout, V v2, int n2, int n3, int n4) {
        return this.setHeaderTopBottomOffset(coordinatorLayout, v2, this.getTopBottomOffsetForScrollingSibling() - n2, n3, n4);
    }

    int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, V v2, int n2) {
        return this.setHeaderTopBottomOffset(coordinatorLayout, v2, n2, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, V v2, int n2, int n3, int n4) {
        int n5;
        int n6 = this.getTopAndBottomOffset();
        int n7 = n5 = 0;
        if (n3 != 0) {
            n7 = n5;
            if (n6 >= n3) {
                n7 = n5;
                if (n6 <= n4) {
                    n2 = MathUtils.constrain(n2, n3, n4);
                    n7 = n5;
                    if (n6 != n2) {
                        this.setTopAndBottomOffset(n2);
                        n7 = n6 - n2;
                    }
                }
            }
        }
        return n7;
    }

    private static class FlingRunnable
    implements Runnable {
        private final V mLayout;
        private final CoordinatorLayout mParent;
        final /* synthetic */ HeaderBehavior this$0;

        FlingRunnable(CoordinatorLayout coordinatorLayout, V v2) {
            this.this$0 = var1_1;
            this.mParent = coordinatorLayout;
            this.mLayout = v2;
        }

        @Override
        public void run() {
            block3: {
                block2: {
                    if (this.mLayout == null || this.this$0.mScroller == null) break block2;
                    if (!this.this$0.mScroller.computeScrollOffset()) break block3;
                    this.this$0.setHeaderTopBottomOffset(this.mParent, this.mLayout, this.this$0.mScroller.getCurrY());
                    ViewCompat.postOnAnimation(this.mLayout, this);
                }
                return;
            }
            this.this$0.onFlingFinished(this.mParent, this.mLayout);
        }
    }
}

