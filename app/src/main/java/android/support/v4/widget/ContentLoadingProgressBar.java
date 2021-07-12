/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.widget.ProgressBar
 */
package android.support.v4.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class ContentLoadingProgressBar
extends ProgressBar {
    private static final int MIN_DELAY = 500;
    private static final int MIN_SHOW_TIME = 500;
    private final Runnable mDelayedHide = new Runnable(){

        @Override
        public void run() {
            ContentLoadingProgressBar.this.mPostedHide = false;
            ContentLoadingProgressBar.this.mStartTime = -1L;
            ContentLoadingProgressBar.this.setVisibility(8);
        }
    };
    private final Runnable mDelayedShow = new Runnable(){

        @Override
        public void run() {
            ContentLoadingProgressBar.this.mPostedShow = false;
            if (!ContentLoadingProgressBar.this.mDismissed) {
                ContentLoadingProgressBar.this.mStartTime = System.currentTimeMillis();
                ContentLoadingProgressBar.this.setVisibility(0);
            }
        }
    };
    boolean mDismissed = false;
    boolean mPostedHide = false;
    boolean mPostedShow = false;
    long mStartTime = -1L;

    public ContentLoadingProgressBar(Context context) {
        this(context, null);
    }

    public ContentLoadingProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
    }

    private void removeCallbacks() {
        this.removeCallbacks(this.mDelayedHide);
        this.removeCallbacks(this.mDelayedShow);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void hide() {
        this.mDismissed = true;
        this.removeCallbacks(this.mDelayedShow);
        long l2 = System.currentTimeMillis() - this.mStartTime;
        if (l2 >= 500L || this.mStartTime == -1L) {
            this.setVisibility(8);
            return;
        } else {
            if (this.mPostedHide) return;
            this.postDelayed(this.mDelayedHide, 500L - l2);
            this.mPostedHide = true;
            return;
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.removeCallbacks();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.removeCallbacks();
    }

    public void show() {
        this.mStartTime = -1L;
        this.mDismissed = false;
        this.removeCallbacks(this.mDelayedHide);
        if (!this.mPostedShow) {
            this.postDelayed(this.mDelayedShow, 500L);
            this.mPostedShow = true;
        }
    }
}

