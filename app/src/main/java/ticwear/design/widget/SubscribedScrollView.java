/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.widget.ScrollView
 */
package ticwear.design.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class SubscribedScrollView
extends ScrollView {
    private boolean isInFling = false;
    private final FlingChecker mFlingChecker = new FlingChecker();
    private int mLastScrollState = 0;
    private OnScrollListener mOnScrollListener;

    public SubscribedScrollView(Context context) {
        super(context);
    }

    public SubscribedScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SubscribedScrollView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    public SubscribedScrollView(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
    }

    static /* synthetic */ boolean access$102(SubscribedScrollView subscribedScrollView, boolean bl2) {
        subscribedScrollView.isInFling = bl2;
        return bl2;
    }

    public void fling(int n2) {
        this.reportScrollStateChange(2);
        this.isInFling = true;
        this.mFlingChecker.run();
        super.fling(n2);
    }

    public boolean onNestedFling(View view, float f2, float f3, boolean bl2) {
        if (bl2 = super.onNestedFling(view, f2, f3, bl2)) {
            this.reportScrollStateChange(2);
        }
        return bl2;
    }

    protected void onScrollChanged(int n2, int n3, int n4, int n5) {
        super.onScrollChanged(n2, n3, n4, n5);
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScroll(this, n2, n3, n4, n5);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 2: {
                this.removeCallbacks(this.mFlingChecker);
                this.reportScrollStateChange(1);
            }
            default: {
                return super.onTouchEvent(motionEvent);
            }
            case 1: 
            case 3: 
        }
        if (this.isInFling) return super.onTouchEvent(motionEvent);
        this.removeCallbacks(this.mFlingChecker);
        this.mFlingChecker.run();
        return super.onTouchEvent(motionEvent);
    }

    void reportScrollStateChange(int n2) {
        if (n2 != this.mLastScrollState && this.mOnScrollListener != null) {
            this.mLastScrollState = n2;
            this.mOnScrollListener.onScrollStateChanged(this, n2);
        }
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    private class FlingChecker
    implements Runnable {
        private static final int FLYWHEEL_TIMEOUT = 40;
        private int mPreviousPosition = Integer.MIN_VALUE;

        private FlingChecker() {
        }

        @Override
        public void run() {
            SubscribedScrollView.this.removeCallbacks(this);
            int n2 = SubscribedScrollView.this.getScrollY();
            if (this.mPreviousPosition == n2) {
                SubscribedScrollView.access$102(SubscribedScrollView.this, false);
                SubscribedScrollView.this.reportScrollStateChange(0);
                return;
            }
            this.mPreviousPosition = SubscribedScrollView.this.getScrollY();
            SubscribedScrollView.this.postDelayed(this, 40L);
        }
    }

    public static interface OnScrollListener {
        public static final int SCROLL_STATE_FLING = 2;
        public static final int SCROLL_STATE_IDLE = 0;
        public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

        public void onScroll(SubscribedScrollView var1, int var2, int var3, int var4, int var5);

        public void onScrollStateChanged(SubscribedScrollView var1, int var2);
    }
}

