/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Message
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnDoubleTapListener
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.ViewConfiguration
 */
package android.support.v4.view;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public final class GestureDetectorCompat {
    private final GestureDetectorCompatImpl mImpl;

    public GestureDetectorCompat(Context context, GestureDetector.OnGestureListener onGestureListener) {
        this(context, onGestureListener, null);
    }

    public GestureDetectorCompat(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
        if (Build.VERSION.SDK_INT > 17) {
            this.mImpl = new GestureDetectorCompatImplJellybeanMr2(context, onGestureListener, handler);
            return;
        }
        this.mImpl = new GestureDetectorCompatImplBase(context, onGestureListener, handler);
    }

    public boolean isLongpressEnabled() {
        return this.mImpl.isLongpressEnabled();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mImpl.onTouchEvent(motionEvent);
    }

    public void setIsLongpressEnabled(boolean bl2) {
        this.mImpl.setIsLongpressEnabled(bl2);
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        this.mImpl.setOnDoubleTapListener(onDoubleTapListener);
    }

    static interface GestureDetectorCompatImpl {
        public boolean isLongpressEnabled();

        public boolean onTouchEvent(MotionEvent var1);

        public void setIsLongpressEnabled(boolean var1);

        public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener var1);
    }

    static class GestureDetectorCompatImplBase
    implements GestureDetectorCompatImpl {
        private static final int DOUBLE_TAP_TIMEOUT;
        private static final int LONGPRESS_TIMEOUT;
        private static final int LONG_PRESS = 2;
        private static final int SHOW_PRESS = 1;
        private static final int TAP = 3;
        private static final int TAP_TIMEOUT;
        private boolean mAlwaysInBiggerTapRegion;
        private boolean mAlwaysInTapRegion;
        MotionEvent mCurrentDownEvent;
        boolean mDeferConfirmSingleTap;
        GestureDetector.OnDoubleTapListener mDoubleTapListener;
        private int mDoubleTapSlopSquare;
        private float mDownFocusX;
        private float mDownFocusY;
        private final Handler mHandler;
        private boolean mInLongPress;
        private boolean mIsDoubleTapping;
        private boolean mIsLongpressEnabled;
        private float mLastFocusX;
        private float mLastFocusY;
        final GestureDetector.OnGestureListener mListener;
        private int mMaximumFlingVelocity;
        private int mMinimumFlingVelocity;
        private MotionEvent mPreviousUpEvent;
        boolean mStillDown;
        private int mTouchSlopSquare;
        private VelocityTracker mVelocityTracker;

        static {
            LONGPRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
            TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
            DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
        }

        /*
         * Enabled aggressive block sorting
         */
        public GestureDetectorCompatImplBase(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
            this.mHandler = handler != null ? new GestureHandler(handler) : new GestureHandler();
            this.mListener = onGestureListener;
            if (onGestureListener instanceof GestureDetector.OnDoubleTapListener) {
                this.setOnDoubleTapListener((GestureDetector.OnDoubleTapListener)onGestureListener);
            }
            this.init(context);
        }

        private void cancel() {
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(3);
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
            this.mIsDoubleTapping = false;
            this.mStillDown = false;
            this.mAlwaysInTapRegion = false;
            this.mAlwaysInBiggerTapRegion = false;
            this.mDeferConfirmSingleTap = false;
            if (this.mInLongPress) {
                this.mInLongPress = false;
            }
        }

        private void cancelTaps() {
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(3);
            this.mIsDoubleTapping = false;
            this.mAlwaysInTapRegion = false;
            this.mAlwaysInBiggerTapRegion = false;
            this.mDeferConfirmSingleTap = false;
            if (this.mInLongPress) {
                this.mInLongPress = false;
            }
        }

        private void init(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null");
            }
            if (this.mListener == null) {
                throw new IllegalArgumentException("OnGestureListener must not be null");
            }
            this.mIsLongpressEnabled = true;
            context = ViewConfiguration.get((Context)context);
            int n2 = context.getScaledTouchSlop();
            int n3 = context.getScaledDoubleTapSlop();
            this.mMinimumFlingVelocity = context.getScaledMinimumFlingVelocity();
            this.mMaximumFlingVelocity = context.getScaledMaximumFlingVelocity();
            this.mTouchSlopSquare = n2 * n2;
            this.mDoubleTapSlopSquare = n3 * n3;
        }

        /*
         * Enabled aggressive block sorting
         */
        private boolean isConsideredDoubleTap(MotionEvent motionEvent, MotionEvent motionEvent2, MotionEvent motionEvent3) {
            int n2;
            int n3;
            return this.mAlwaysInBiggerTapRegion && motionEvent3.getEventTime() - motionEvent2.getEventTime() <= (long)DOUBLE_TAP_TIMEOUT && (n3 = (int)motionEvent.getX() - (int)motionEvent3.getX()) * n3 + (n2 = (int)motionEvent.getY() - (int)motionEvent3.getY()) * n2 < this.mDoubleTapSlopSquare;
        }

        void dispatchLongPress() {
            this.mHandler.removeMessages(3);
            this.mDeferConfirmSingleTap = false;
            this.mInLongPress = true;
            this.mListener.onLongPress(this.mCurrentDownEvent);
        }

        @Override
        public boolean isLongpressEnabled() {
            return this.mIsLongpressEnabled;
        }

        /*
         * Unable to fully structure code
         */
        @Override
        public boolean onTouchEvent(MotionEvent var1_1) {
            block35: {
                block34: {
                    block37: {
                        block36: {
                            var10_2 = var1_1.getAction();
                            if (this.mVelocityTracker == null) {
                                this.mVelocityTracker = VelocityTracker.obtain();
                            }
                            this.mVelocityTracker.addMovement(var1_1);
                            if ((var10_2 & 255) != 6) break block36;
                            var6_3 = 1;
lbl7:
                            // 2 sources

                            while (var6_3 != 0) {
                                var7_4 = MotionEventCompat.getActionIndex(var1_1);
lbl9:
                                // 2 sources

                                while (true) {
                                    var3_5 = 0.0f;
                                    var2_6 = 0.0f;
                                    var9_7 = var1_1.getPointerCount();
                                    block11: for (var8_8 = 0; var8_8 < var9_7; ++var8_8) {
                                        if (var7_4 == var8_8) lbl-1000:
                                        // 2 sources

                                        {
                                            continue block11;
                                        }
                                        break block34;
                                    }
                                    break block35;
                                    break;
                                }
                            }
                            break block37;
                        }
                        var6_3 = 0;
                        ** GOTO lbl7
                    }
                    var7_4 = -1;
                    ** while (true)
                }
                var3_5 += var1_1.getX(var8_8);
                var2_6 += var1_1.getY(var8_8);
                ** while (true)
            }
            if (var6_3 == 0) ** GOTO lbl45
            var6_3 = var9_7 - 1;
            block13: while (true) {
                var3_5 /= (float)var6_3;
                var2_6 /= (float)var6_3;
                var7_4 = 0;
                var13_9 = false;
                var14_10 = false;
                var12_12 = var11_11 = false;
                switch (var10_2 & 255) {
                    default: {
                        var12_12 = var11_11;
                    }
lbl43:
                    // 6 sources

                    case 4: {
                        return var12_12;
                    }
lbl45:
                    // 1 sources

                    var6_3 = var9_7;
                    continue block13;
                    case 5: {
                        this.mLastFocusX = var3_5;
                        this.mDownFocusX = var3_5;
                        this.mLastFocusY = var2_6;
                        this.mDownFocusY = var2_6;
                        this.cancelTaps();
                        return false;
                    }
                    case 6: {
                        this.mLastFocusX = var3_5;
                        this.mDownFocusX = var3_5;
                        this.mLastFocusY = var2_6;
                        this.mDownFocusY = var2_6;
                        this.mVelocityTracker.computeCurrentVelocity(1000, (float)this.mMaximumFlingVelocity);
                        var7_4 = MotionEventCompat.getActionIndex(var1_1);
                        var6_3 = var1_1.getPointerId(var7_4);
                        var2_6 = VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, var6_3);
                        var3_5 = VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, var6_3);
                        var6_3 = 0;
                        block15: while (true) {
                            var12_12 = var11_11;
                            if (var6_3 >= var9_7) ** GOTO lbl43
                            if (var6_3 != var7_4) break;
                            while (true) {
                                ++var6_3;
                                continue block15;
                                break;
                            }
                            break;
                        }
                        if (!(var2_6 * VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, var8_8 = var1_1.getPointerId(var6_3)) + var3_5 * VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, var8_8) < 0.0f)) ** continue;
                        this.mVelocityTracker.clear();
                        return false;
                    }
                    case 0: {
                        var6_3 = var7_4;
                        if (this.mDoubleTapListener == null) ** GOTO lbl84
                        var11_11 = this.mHandler.hasMessages(3);
                        if (var11_11) {
                            this.mHandler.removeMessages(3);
                        }
                        if (this.mCurrentDownEvent == null || this.mPreviousUpEvent == null || !var11_11 || !this.isConsideredDoubleTap(this.mCurrentDownEvent, this.mPreviousUpEvent, var1_1)) ** GOTO lbl104
                        this.mIsDoubleTapping = true;
                        var6_3 = false | this.mDoubleTapListener.onDoubleTap(this.mCurrentDownEvent) | this.mDoubleTapListener.onDoubleTapEvent(var1_1);
lbl84:
                        // 3 sources

                        while (true) {
                            this.mLastFocusX = var3_5;
                            this.mDownFocusX = var3_5;
                            this.mLastFocusY = var2_6;
                            this.mDownFocusY = var2_6;
                            if (this.mCurrentDownEvent != null) {
                                this.mCurrentDownEvent.recycle();
                            }
                            this.mCurrentDownEvent = MotionEvent.obtain((MotionEvent)var1_1);
                            this.mAlwaysInTapRegion = true;
                            this.mAlwaysInBiggerTapRegion = true;
                            this.mStillDown = true;
                            this.mInLongPress = false;
                            this.mDeferConfirmSingleTap = false;
                            if (this.mIsLongpressEnabled) {
                                this.mHandler.removeMessages(2);
                                this.mHandler.sendEmptyMessageAtTime(2, this.mCurrentDownEvent.getDownTime() + (long)GestureDetectorCompatImplBase.TAP_TIMEOUT + (long)GestureDetectorCompatImplBase.LONGPRESS_TIMEOUT);
                            }
                            this.mHandler.sendEmptyMessageAtTime(1, this.mCurrentDownEvent.getDownTime() + (long)GestureDetectorCompatImplBase.TAP_TIMEOUT);
                            return (boolean)(var6_3 | this.mListener.onDown(var1_1));
                        }
lbl104:
                        // 1 sources

                        this.mHandler.sendEmptyMessageDelayed(3, (long)GestureDetectorCompatImplBase.DOUBLE_TAP_TIMEOUT);
                        var6_3 = var7_4;
                        ** continue;
                    }
                    case 2: {
                        var12_12 = var11_11;
                        if (this.mInLongPress) ** GOTO lbl43
                        var4_13 = this.mLastFocusX - var3_5;
                        var5_14 = this.mLastFocusY - var2_6;
                        if (this.mIsDoubleTapping) {
                            return false | this.mDoubleTapListener.onDoubleTapEvent(var1_1);
                        }
                        if (!this.mAlwaysInTapRegion) ** GOTO lbl132
                        var6_3 = (int)(var3_5 - this.mDownFocusX);
                        var7_4 = (int)(var2_6 - this.mDownFocusY);
                        var6_3 = var6_3 * var6_3 + var7_4 * var7_4;
                        var11_11 = var13_9;
                        if (var6_3 > this.mTouchSlopSquare) {
                            var11_11 = this.mListener.onScroll(this.mCurrentDownEvent, var1_1, var4_13, var5_14);
                            this.mLastFocusX = var3_5;
                            this.mLastFocusY = var2_6;
                            this.mAlwaysInTapRegion = false;
                            this.mHandler.removeMessages(3);
                            this.mHandler.removeMessages(1);
                            this.mHandler.removeMessages(2);
                        }
                        var12_12 = var11_11;
                        if (var6_3 <= this.mTouchSlopSquare) ** GOTO lbl43
                        this.mAlwaysInBiggerTapRegion = false;
                        return var11_11;
lbl132:
                        // 1 sources

                        if (Math.abs(var4_13) >= 1.0f) ** GOTO lbl135
                        var12_12 = var11_11;
                        if (!(Math.abs(var5_14) >= 1.0f)) ** GOTO lbl43
lbl135:
                        // 2 sources

                        var11_11 = this.mListener.onScroll(this.mCurrentDownEvent, var1_1, var4_13, var5_14);
                        this.mLastFocusX = var3_5;
                        this.mLastFocusY = var2_6;
                        return var11_11;
                    }
                    case 1: {
                        this.mStillDown = false;
                        var15_15 = MotionEvent.obtain((MotionEvent)var1_1);
                        if (this.mIsDoubleTapping) {
                            var11_11 = false | this.mDoubleTapListener.onDoubleTapEvent(var1_1);
lbl144:
                            // 7 sources

                            while (true) {
                                if (this.mPreviousUpEvent != null) {
                                    this.mPreviousUpEvent.recycle();
                                }
                                this.mPreviousUpEvent = var15_15;
                                if (this.mVelocityTracker != null) {
                                    this.mVelocityTracker.recycle();
                                    this.mVelocityTracker = null;
                                }
                                this.mIsDoubleTapping = false;
                                this.mDeferConfirmSingleTap = false;
                                this.mHandler.removeMessages(1);
                                this.mHandler.removeMessages(2);
                                return var11_11;
                            }
                        }
                        if (!this.mInLongPress) ** GOTO lbl161
                        this.mHandler.removeMessages(3);
                        this.mInLongPress = false;
                        var11_11 = var14_10;
                        ** GOTO lbl144
lbl161:
                        // 1 sources

                        if (!this.mAlwaysInTapRegion) ** GOTO lbl170
                        var11_11 = var12_12 = this.mListener.onSingleTapUp(var1_1);
                        if (!this.mDeferConfirmSingleTap) ** GOTO lbl144
                        var11_11 = var12_12;
                        if (this.mDoubleTapListener == null) ** GOTO lbl144
                        this.mDoubleTapListener.onSingleTapConfirmed(var1_1);
                        var11_11 = var12_12;
                        ** GOTO lbl144
lbl170:
                        // 1 sources

                        var16_16 = this.mVelocityTracker;
                        var6_3 = var1_1.getPointerId(0);
                        var16_16.computeCurrentVelocity(1000, (float)this.mMaximumFlingVelocity);
                        var2_6 = VelocityTrackerCompat.getYVelocity(var16_16, var6_3);
                        var3_5 = VelocityTrackerCompat.getXVelocity(var16_16, var6_3);
                        if (Math.abs(var2_6) > (float)this.mMinimumFlingVelocity) ** GOTO lbl178
                        var11_11 = var14_10;
                        if (!(Math.abs(var3_5) > (float)this.mMinimumFlingVelocity)) ** GOTO lbl144
lbl178:
                        // 2 sources

                        var11_11 = this.mListener.onFling(this.mCurrentDownEvent, var1_1, var3_5, var2_6);
                        ** continue;
                    }
                    case 3: 
                }
                break;
            }
            this.cancel();
            return false;
        }

        @Override
        public void setIsLongpressEnabled(boolean bl2) {
            this.mIsLongpressEnabled = bl2;
        }

        @Override
        public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
            this.mDoubleTapListener = onDoubleTapListener;
        }

        private class GestureHandler
        extends Handler {
            GestureHandler() {
            }

            GestureHandler(Handler handler) {
                super(handler.getLooper());
            }

            /*
             * Enabled aggressive block sorting
             */
            public void handleMessage(Message message) {
                switch (message.what) {
                    default: {
                        throw new RuntimeException("Unknown message " + message);
                    }
                    case 1: {
                        GestureDetectorCompatImplBase.this.mListener.onShowPress(GestureDetectorCompatImplBase.this.mCurrentDownEvent);
                        return;
                    }
                    case 2: {
                        GestureDetectorCompatImplBase.this.dispatchLongPress();
                        return;
                    }
                    case 3: {
                        if (GestureDetectorCompatImplBase.this.mDoubleTapListener == null) return;
                        if (!GestureDetectorCompatImplBase.this.mStillDown) {
                            GestureDetectorCompatImplBase.this.mDoubleTapListener.onSingleTapConfirmed(GestureDetectorCompatImplBase.this.mCurrentDownEvent);
                            return;
                        } else {
                            break;
                        }
                    }
                }
                GestureDetectorCompatImplBase.this.mDeferConfirmSingleTap = true;
            }
        }
    }

    static class GestureDetectorCompatImplJellybeanMr2
    implements GestureDetectorCompatImpl {
        private final GestureDetector mDetector;

        public GestureDetectorCompatImplJellybeanMr2(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
            this.mDetector = new GestureDetector(context, onGestureListener, handler);
        }

        @Override
        public boolean isLongpressEnabled() {
            return this.mDetector.isLongpressEnabled();
        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            return this.mDetector.onTouchEvent(motionEvent);
        }

        @Override
        public void setIsLongpressEnabled(boolean bl2) {
            this.mDetector.setIsLongpressEnabled(bl2);
        }

        @Override
        public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
            this.mDetector.setOnDoubleTapListener(onDoubleTapListener);
        }
    }
}

