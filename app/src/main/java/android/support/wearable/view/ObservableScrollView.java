/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.widget.ScrollView
 */
package android.support.wearable.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ObservableScrollView
extends ScrollView {
    private OnScrollListener mOnScrollListener;

    public ObservableScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    protected void onScrollChanged(int n2, int n3, int n4, int n5) {
        super.onScrollChanged(n2, n3, n4, n5);
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScroll(n5 - n3);
        }
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    public static interface OnScrollListener {
        public void onScroll(float var1);
    }
}

