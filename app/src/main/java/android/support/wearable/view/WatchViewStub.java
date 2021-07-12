/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.WindowInsets
 *  android.widget.FrameLayout
 */
package android.support.wearable.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.wearable.R;
import android.support.wearable.view.Func;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;

@TargetApi(value=20)
public class WatchViewStub
extends FrameLayout {
    private static final String TAG = "WatchViewStub";
    private boolean mInflateNeeded;
    private boolean mLastKnownRound;
    private OnLayoutInflatedListener mListener;
    private int mRectLayout;
    private int mRoundLayout;
    private boolean mWindowInsetsApplied;
    private boolean mWindowOverscan;

    public WatchViewStub(Context context) {
        this(context, null);
    }

    public WatchViewStub(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WatchViewStub(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.WatchViewStub, 0, 0);
        this.mRectLayout = context.getResourceId(R.styleable.WatchViewStub_rectLayout, 0);
        this.mRoundLayout = context.getResourceId(R.styleable.WatchViewStub_roundLayout, 0);
        this.mInflateNeeded = true;
        context.recycle();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void inflate() {
        this.removeAllViews();
        if (this.mRoundLayout == 0 && !this.isInEditMode()) {
            throw new IllegalStateException("You must supply a roundLayout resource");
        }
        if (this.mRectLayout == 0 && !this.isInEditMode()) {
            throw new IllegalStateException("You must supply a rectLayout resource");
        }
        int n2 = this.mLastKnownRound ? this.mRoundLayout : this.mRectLayout;
        LayoutInflater.from((Context)this.getContext()).inflate(n2, (ViewGroup)this);
        this.mInflateNeeded = false;
        if (this.mListener != null) {
            this.mListener.onLayoutInflated(this);
        }
    }

    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        this.mWindowInsetsApplied = true;
        boolean bl2 = windowInsets.isRound();
        if (bl2 != this.mLastKnownRound) {
            this.mLastKnownRound = bl2;
            this.mInflateNeeded = true;
        }
        if (this.mInflateNeeded) {
            this.inflate();
        }
        return windowInsets;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mWindowOverscan = Func.getWindowOverscan((View)this);
        this.mWindowInsetsApplied = false;
        this.requestApplyInsets();
    }

    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        if (this.mWindowOverscan && !this.mWindowInsetsApplied) {
            Log.w((String)TAG, (String)"onApplyWindowInsets was not called. WatchViewStub should be the the root of your layout. If an OnApplyWindowInsetsListener was attached to this view, it must forward the insets on by calling view.onApplyWindowInsets.");
        }
        super.onLayout(bl2, n2, n3, n4, n5);
    }

    public void onMeasure(int n2, int n3) {
        if (this.mInflateNeeded && !this.mWindowOverscan) {
            this.inflate();
        }
        super.onMeasure(n2, n3);
    }

    public void setOnLayoutInflatedListener(OnLayoutInflatedListener onLayoutInflatedListener) {
        this.mListener = onLayoutInflatedListener;
    }

    public void setRectLayout(@LayoutRes int n2) {
        if (!this.mInflateNeeded) {
            Log.w((String)TAG, (String)"Views have already been inflated. setRectLayout will have no effect.");
            return;
        }
        this.mRectLayout = n2;
    }

    public void setRoundLayout(@LayoutRes int n2) {
        if (!this.mInflateNeeded) {
            Log.w((String)TAG, (String)"Views have already been inflated. setRoundLayout will have no effect.");
            return;
        }
        this.mRoundLayout = n2;
    }

    public static interface OnLayoutInflatedListener {
        public void onLayoutInflated(WatchViewStub var1);
    }
}

