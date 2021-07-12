/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.os.Handler
 *  android.os.Message
 *  android.os.SystemClock
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 */
package android.support.wearable.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.wearable.R;
import android.support.wearable.view.CircledImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

@TargetApi(value=20)
public class DelayedConfirmationView
extends CircledImageView {
    private static final int DEFAULT_UPDATE_INTERVAL_MS = 33;
    private long mCurrentTimeMs;
    private DelayedConfirmationListener mListener;
    private long mStartTimeMs;
    private Handler mTimerHandler = new Handler(){

        /*
         * Enabled aggressive block sorting
         */
        public void handleMessage(Message message) {
            DelayedConfirmationView.access$002(DelayedConfirmationView.this, SystemClock.elapsedRealtime());
            DelayedConfirmationView.this.invalidate();
            if (DelayedConfirmationView.this.mCurrentTimeMs - DelayedConfirmationView.this.mStartTimeMs < DelayedConfirmationView.this.mTotalTimeMs) {
                DelayedConfirmationView.this.mTimerHandler.sendEmptyMessageDelayed(0, DelayedConfirmationView.this.mUpdateIntervalMs);
                return;
            } else {
                if (DelayedConfirmationView.this.mStartTimeMs <= 0L || DelayedConfirmationView.this.mListener == null) return;
                DelayedConfirmationView.this.mListener.onTimerFinished(DelayedConfirmationView.this);
                return;
            }
        }
    };
    private long mTotalTimeMs;
    private long mUpdateIntervalMs = 0L;

    public DelayedConfirmationView(Context context) {
        this(context, null);
    }

    public DelayedConfirmationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DelayedConfirmationView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.DelayedConfirmationView, n2, 0);
        this.mUpdateIntervalMs = context.getInteger(R.styleable.DelayedConfirmationView_update_interval, 33);
        this.setProgress(0.0f);
        context.recycle();
    }

    static /* synthetic */ long access$002(DelayedConfirmationView delayedConfirmationView, long l2) {
        delayedConfirmationView.mCurrentTimeMs = l2;
        return l2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (this.mStartTimeMs > 0L) {
            this.setProgress((float)(this.mCurrentTimeMs - this.mStartTimeMs) / (float)this.mTotalTimeMs);
        }
        super.onDraw(canvas);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            default: {
                return false;
            }
            case 0: 
            case 2: 
        }
        if (this.mListener == null) return false;
        this.mListener.onTimerSelected(this);
        return false;
    }

    public void reset() {
        this.mStartTimeMs = 0L;
        this.setProgress(0.0f);
        this.invalidate();
    }

    public void setListener(DelayedConfirmationListener delayedConfirmationListener) {
        this.mListener = delayedConfirmationListener;
    }

    public void setStartTimeMs(long l2) {
        this.mStartTimeMs = l2;
        this.invalidate();
    }

    public void setTotalTimeMs(long l2) {
        this.mTotalTimeMs = l2;
    }

    public void start() {
        this.mStartTimeMs = SystemClock.elapsedRealtime();
        this.mCurrentTimeMs = SystemClock.elapsedRealtime();
        this.mTimerHandler.sendEmptyMessageDelayed(0, this.mUpdateIntervalMs);
    }

    public static interface DelayedConfirmationListener {
        public void onTimerFinished(View var1);

        public void onTimerSelected(View var1);
    }
}

