/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 */
package ticwear.design.preference;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import ticwear.design.R;

public class PreferenceFrameLayout
extends FrameLayout {
    private static final int DEFAULT_BORDER_BOTTOM = 0;
    private static final int DEFAULT_BORDER_LEFT = 0;
    private static final int DEFAULT_BORDER_RIGHT = 0;
    private static final int DEFAULT_BORDER_TOP = 0;
    private final int mBorderBottom;
    private final int mBorderLeft;
    private final int mBorderRight;
    private final int mBorderTop;
    private boolean mPaddingApplied;

    public PreferenceFrameLayout(Context context) {
        this(context, null);
    }

    public PreferenceFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.tic_preferenceFrameLayoutStyle);
    }

    public PreferenceFrameLayout(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, 0);
    }

    public PreferenceFrameLayout(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
        attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.PreferenceFrameLayout, n2, n3);
        float f2 = context.getResources().getDisplayMetrics().density;
        n2 = (int)(f2 * 0.0f + 0.5f);
        n3 = (int)(f2 * 0.0f + 0.5f);
        int n4 = (int)(f2 * 0.0f + 0.5f);
        int n5 = (int)(f2 * 0.0f + 0.5f);
        this.mBorderTop = attributeSet.getDimensionPixelSize(R.styleable.PreferenceFrameLayout_tic_borderTop, n2);
        this.mBorderBottom = attributeSet.getDimensionPixelSize(R.styleable.PreferenceFrameLayout_tic_borderBottom, n3);
        this.mBorderLeft = attributeSet.getDimensionPixelSize(R.styleable.PreferenceFrameLayout_tic_borderLeft, n4);
        this.mBorderRight = attributeSet.getDimensionPixelSize(R.styleable.PreferenceFrameLayout_tic_borderRight, n5);
        attributeSet.recycle();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void addView(View view) {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6 = this.getPaddingTop();
        int n7 = this.getPaddingBottom();
        int n8 = this.getPaddingLeft();
        int n9 = this.getPaddingRight();
        LayoutParams layoutParams = view.getLayoutParams() instanceof LayoutParams ? (LayoutParams)view.getLayoutParams() : null;
        if (layoutParams != null && layoutParams.removeBorders) {
            n5 = n7;
            n4 = n8;
            n3 = n9;
            n2 = n6;
            if (this.mPaddingApplied) {
                n2 = n6 - this.mBorderTop;
                n5 = n7 - this.mBorderBottom;
                n4 = n8 - this.mBorderLeft;
                n3 = n9 - this.mBorderRight;
                this.mPaddingApplied = false;
            }
        } else {
            n5 = n7;
            n4 = n8;
            n3 = n9;
            n2 = n6;
            if (!this.mPaddingApplied) {
                n2 = n6 + this.mBorderTop;
                n5 = n7 + this.mBorderBottom;
                n4 = n8 + this.mBorderLeft;
                n3 = n9 + this.mBorderRight;
                this.mPaddingApplied = true;
            }
        }
        n9 = this.getPaddingTop();
        n8 = this.getPaddingBottom();
        n7 = this.getPaddingLeft();
        n6 = this.getPaddingRight();
        if (n9 != n2 || n8 != n5 || n7 != n4 || n6 != n3) {
            this.setPadding(n4, n2, n3, n5);
        }
        super.addView(view);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    public static class LayoutParams
    extends FrameLayout.LayoutParams {
        public boolean removeBorders = false;

        public LayoutParams(int n2, int n3) {
            super(n2, n3);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, R.styleable.PreferenceFrameLayout_Layout);
            this.removeBorders = context.getBoolean(R.styleable.PreferenceFrameLayout_Layout_tic_layout_removeBorders, false);
            context.recycle();
        }
    }
}

