/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package ticwear.design.widget;

import android.support.annotation.CallSuper;
import android.util.Log;
import ticwear.design.DesignConfig;
import ticwear.design.widget.ViewScrollingStatusAccessor;

class ScrollViewFlingChecker
implements Runnable {
    private static final long IDLE_SCROLL_DURATION = 600L;
    private static final int SCROLL_AXES_ALL = 3;
    static final String TAG = "SVFlingChecker";
    private long mLastCheckTime;
    private int mLastScrollX;
    private int mLastScrollY;
    private long mNoneScrollingTime;
    private ViewScrollingStatusAccessor mScrollingViewAccessor;
    private float mVelocityX;
    private float mVelocityY;

    public ScrollViewFlingChecker(ViewScrollingStatusAccessor viewScrollingStatusAccessor) {
        this.mScrollingViewAccessor = viewScrollingStatusAccessor;
        this.reset();
    }

    private boolean scrollingFinished(int n2) {
        if ((n2 & 2) != 0) {
            int n3 = this.mScrollingViewAccessor.computeVerticalScrollOffset();
            if (this.mLastScrollY == Integer.MAX_VALUE || n3 != this.mLastScrollY) {
                this.mLastScrollY = n3;
                return false;
            }
        }
        if ((n2 & 1) != 0) {
            n2 = this.mScrollingViewAccessor.computeHorizontalScrollOffset();
            if (this.mLastScrollX == Integer.MAX_VALUE || n2 != this.mLastScrollX) {
                this.mLastScrollX = n2;
                return false;
            }
        }
        return true;
    }

    public float getVelocityX() {
        return this.mVelocityX;
    }

    public float getVelocityY() {
        return this.mVelocityY;
    }

    public boolean isStarted() {
        return this.mLastCheckTime > 0L;
    }

    public boolean isValid() {
        return this.mScrollingViewAccessor != null && this.mScrollingViewAccessor.isValid();
    }

    @CallSuper
    public void reset() {
        this.mNoneScrollingTime = 600L;
        this.mLastCheckTime = 0L;
        this.mLastScrollX = Integer.MAX_VALUE;
        this.mLastScrollY = Integer.MAX_VALUE;
        this.mVelocityX = 0.0f;
        this.mVelocityY = 0.0f;
    }

    @Override
    public final void run() {
        this.runCheck();
    }

    /*
     * Enabled aggressive block sorting
     */
    @CallSuper
    protected boolean runCheck() {
        if (!this.isValid()) {
            this.reset();
            return false;
        }
        long l2 = System.currentTimeMillis();
        long l3 = this.mLastCheckTime > 0L ? l2 - this.mLastCheckTime : 0L;
        this.mLastCheckTime = l2;
        if (this.isStarted() && this.mLastScrollY != Integer.MAX_VALUE) {
            this.mVelocityY = (float)(this.mScrollingViewAccessor.computeVerticalScrollOffset() - this.mLastScrollY) * 1000.0f / (float)l3;
        }
        if (this.isStarted() && this.mLastScrollX != Integer.MAX_VALUE) {
            this.mVelocityX = (float)(this.mScrollingViewAccessor.computeHorizontalScrollOffset() - this.mLastScrollX) * 1000.0f / (float)l3;
        }
        if (DesignConfig.DEBUG_COORDINATOR) {
            Log.v((String)TAG, (String)("runCheck, current " + this.mScrollingViewAccessor.computeVerticalScrollOffset() + ", last " + this.mLastScrollY + ", duration " + l3 + ", velocity " + this.mVelocityY + ", view " + this.mScrollingViewAccessor));
        }
        if (this.scrollingFinished(3)) {
            this.mNoneScrollingTime = l3 = this.mNoneScrollingTime - l3;
            if (l3 > 0L) {
                this.reset();
                return false;
            }
        }
        return true;
    }
}

