/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.ViewGroup$LayoutParams
 *  android.view.WindowInsets
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.TextView
 */
package android.support.wearable.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.wearable.R;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.TextView;

@TargetApi(value=20)
public class WearableHeaderTextView
extends TextView {
    private static final String TAG = "WearableHeaderTextView";
    private int mCircularLayoutGravity = 0;
    private int mCircularTextSize = 0;

    public WearableHeaderTextView(Context context) {
        this(context, null);
    }

    public WearableHeaderTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WearableHeaderTextView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.WearableHeaderTextView, 0, 0);
        this.mCircularLayoutGravity = context.getInt(R.styleable.WearableHeaderTextView_circular_layout_gravity, 0);
        this.mCircularTextSize = context.getDimensionPixelSize(R.styleable.WearableHeaderTextView_circular_text_size, 0);
        context.recycle();
    }

    private void applyCircularStyles() {
        if (this.mCircularLayoutGravity != 0) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)this.getLayoutParams();
            layoutParams.gravity = this.mCircularLayoutGravity;
            this.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        }
        if (this.mCircularTextSize != 0) {
            this.setTextSize(this.mCircularTextSize);
        }
    }

    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if ((windowInsets = super.onApplyWindowInsets(windowInsets)).isRound()) {
            this.applyCircularStyles();
            this.requestLayout();
        }
        return windowInsets;
    }
}

