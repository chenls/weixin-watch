/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View$MeasureSpec
 *  android.widget.ImageView
 */
package ticwear.design.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class ProgressBarButton
extends ImageView {
    private static final int LONG_PRESS_DELAY = 300;
    private static int mDefaultImageSize;
    private final Runnable mLongPressUpdateRunnable = new Runnable(){

        @Override
        public void run() {
            if (ProgressBarButton.this.mTouchListener != null) {
                ProgressBarButton.this.mTouchListener.onLongPress();
                ProgressBarButton.this.postDelayed(this, 60L);
            }
        }
    };
    private TouchListener mTouchListener;

    public ProgressBarButton(Context context) {
        super(context);
    }

    public ProgressBarButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
    }

    public ProgressBarButton(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
    }

    private void stopLongPressUpdate() {
        this.removeCallbacks(this.mLongPressUpdateRunnable);
    }

    protected void onDetachedFromWindow() {
        this.stopLongPressUpdate();
        super.onDetachedFromWindow();
    }

    protected void onMeasure(int n2, int n3) {
        n2 = View.MeasureSpec.getSize((int)n3);
        n3 = View.MeasureSpec.getSize((int)n3);
        int n4 = (n3 - mDefaultImageSize) / 2;
        this.setPadding(n4, n4, n4, n4);
        this.setMeasuredDimension(n2, n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        block5: {
            block4: {
                super.onTouchEvent(motionEvent);
                if (this.mTouchListener == null) break block4;
                int n2 = motionEvent.getAction();
                if (n2 == 0) {
                    this.mTouchListener.onDown();
                    this.postDelayed(this.mLongPressUpdateRunnable, 300L);
                    return true;
                }
                this.stopLongPressUpdate();
                if (n2 == 1) break block5;
            }
            return true;
        }
        this.mTouchListener.onUp();
        return true;
    }

    public void setDefaultImageSize(int n2) {
        mDefaultImageSize = n2;
    }

    public void setTouchListener(TouchListener touchListener) {
        if (this.mTouchListener == touchListener) {
            return;
        }
        this.stopLongPressUpdate();
        this.mTouchListener = touchListener;
    }

    public static interface TouchListener {
        public void onDown();

        public void onLongPress();

        public void onUp();
    }
}

