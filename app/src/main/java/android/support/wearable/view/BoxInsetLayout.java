/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.view.Gravity
 *  android.view.View
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.WindowInsets
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 */
package android.support.wearable.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.wearable.R;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;

@TargetApi(value=20)
public class BoxInsetLayout
extends FrameLayout {
    private static final int DEFAULT_CHILD_GRAVITY = 0x800033;
    private static float FACTOR = 0.146467f;
    private Rect mForegroundPadding;
    private Rect mInsets;
    private boolean mLastKnownRound;

    public BoxInsetLayout(Context context) {
        this(context, null);
    }

    public BoxInsetLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BoxInsetLayout(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        if (this.mForegroundPadding == null) {
            this.mForegroundPadding = new Rect();
        }
        if (this.mInsets == null) {
            this.mInsets = new Rect();
        }
    }

    private void measureChild(int n2, int n3, int n4, int n5) {
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        LayoutParams layoutParams;
        View view;
        block19: {
            int n11;
            block20: {
                int n12;
                block17: {
                    block18: {
                        int n13;
                        int n14;
                        block15: {
                            block16: {
                                int n15;
                                block13: {
                                    block14: {
                                        view = this.getChildAt(n5);
                                        layoutParams = (LayoutParams)view.getLayoutParams();
                                        n5 = n10 = layoutParams.gravity;
                                        if (n10 == -1) {
                                            n5 = 0x800033;
                                        }
                                        n12 = n5 & 0x70;
                                        n15 = n5 & 7;
                                        n14 = layoutParams.leftMargin + this.getPaddingLeft() + this.mForegroundPadding.left;
                                        n13 = layoutParams.rightMargin + this.getPaddingRight() + this.mForegroundPadding.right;
                                        n9 = layoutParams.topMargin + this.getPaddingTop() + this.mForegroundPadding.top;
                                        n8 = layoutParams.bottomMargin + this.getPaddingBottom() + this.mForegroundPadding.bottom;
                                        n10 = view.getPaddingLeft();
                                        n7 = view.getPaddingRight();
                                        n11 = view.getPaddingBottom();
                                        n6 = view.getPaddingTop();
                                        n5 = n10;
                                        if (!this.mLastKnownRound) break block13;
                                        n5 = n10;
                                        if ((layoutParams.boxedEdges & 1) == 0) break block13;
                                        if (layoutParams.width == -1) break block14;
                                        n5 = n10;
                                        if (n15 != 3) break block13;
                                    }
                                    n5 = Math.max(Math.max(0, n4 - n14), n10);
                                }
                                n10 = n7;
                                if (!this.mLastKnownRound) break block15;
                                n10 = n7;
                                if ((layoutParams.boxedEdges & 4) == 0) break block15;
                                if (layoutParams.width == -1) break block16;
                                n10 = n7;
                                if (n15 != 5) break block15;
                            }
                            n10 = Math.max(Math.max(0, n4 - n13), n7);
                        }
                        n7 = BoxInsetLayout.getChildMeasureSpec((int)n2, (int)(0 + n14 + n13), (int)layoutParams.width);
                        n2 = n6;
                        if (!this.mLastKnownRound) break block17;
                        n2 = n6;
                        if ((layoutParams.boxedEdges & 2) == 0) break block17;
                        if (layoutParams.height == -1) break block18;
                        n2 = n6;
                        if (n12 != 48) break block17;
                    }
                    n2 = Math.max(Math.max(0, n4 - n9), n6);
                }
                n6 = n11;
                if (!this.mLastKnownRound) break block19;
                n6 = n11;
                if ((layoutParams.boxedEdges & 8) == 0) break block19;
                if (layoutParams.height == -1) break block20;
                n6 = n11;
                if (n12 != 80) break block19;
            }
            n6 = Math.max(Math.max(0, n4 - n8), n11);
        }
        n3 = BoxInsetLayout.getChildMeasureSpec((int)n3, (int)(0 + n9 + n8), (int)layoutParams.height);
        view.setPadding(n5, n2, n10, n6);
        view.measure(n7, n3);
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public Rect getInsets() {
        return this.mInsets;
    }

    public boolean isRound() {
        return this.mLastKnownRound;
    }

    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        windowInsets = super.onApplyWindowInsets(windowInsets);
        if (Build.VERSION.SDK_INT < 23) {
            boolean bl2 = windowInsets.isRound();
            if (bl2 != this.mLastKnownRound) {
                this.mLastKnownRound = bl2;
                this.requestLayout();
            }
            this.mInsets.set(windowInsets.getSystemWindowInsetLeft(), windowInsets.getSystemWindowInsetTop(), windowInsets.getSystemWindowInsetRight(), windowInsets.getSystemWindowInsetBottom());
        }
        return windowInsets;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (Build.VERSION.SDK_INT < 23) {
            this.requestApplyInsets();
            return;
        }
        this.mLastKnownRound = this.getResources().getConfiguration().isScreenRound();
        WindowInsets windowInsets = this.getRootWindowInsets();
        this.mInsets.set(windowInsets.getSystemWindowInsetLeft(), windowInsets.getSystemWindowInsetTop(), windowInsets.getSystemWindowInsetRight(), windowInsets.getSystemWindowInsetBottom());
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        int n6 = this.getChildCount();
        int n7 = this.getPaddingLeft() + this.mForegroundPadding.left;
        int n8 = n4 - n2 - this.getPaddingRight() - this.mForegroundPadding.right;
        int n9 = this.getPaddingTop() + this.mForegroundPadding.top;
        n5 = n5 - n3 - this.getPaddingBottom() - this.mForegroundPadding.bottom;
        n4 = 0;
        while (n4 < n6) {
            View view = this.getChildAt(n4);
            if (view.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                int n10 = view.getMeasuredWidth();
                int n11 = view.getMeasuredHeight();
                n3 = n2 = layoutParams.gravity;
                if (n2 == -1) {
                    n3 = 0x800033;
                }
                n2 = Gravity.getAbsoluteGravity((int)n3, (int)this.getLayoutDirection());
                if (layoutParams.width == -1) {
                    n2 = n7 + layoutParams.leftMargin;
                } else {
                    switch (n2 & 7) {
                        default: {
                            n2 = n7 + layoutParams.leftMargin;
                            break;
                        }
                        case 1: {
                            n2 = (n8 - n7 - n10) / 2 + n7 + layoutParams.leftMargin - layoutParams.rightMargin;
                            break;
                        }
                        case 5: {
                            n2 = n8 - n10 - layoutParams.rightMargin;
                        }
                    }
                }
                if (layoutParams.height == -1) {
                    n3 = n9 + layoutParams.topMargin;
                } else {
                    switch (n3 & 0x70) {
                        default: {
                            n3 = n9 + layoutParams.topMargin;
                            break;
                        }
                        case 16: {
                            n3 = (n5 - n9 - n11) / 2 + n9 + layoutParams.topMargin - layoutParams.bottomMargin;
                            break;
                        }
                        case 80: {
                            n3 = n5 - n11 - layoutParams.bottomMargin;
                        }
                    }
                }
                view.layout(n2, n3, n2 + n10, n3 + n11);
            }
            ++n4;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n2, int n3) {
        int n4;
        int n5;
        int n6;
        int n7;
        View view;
        int n8;
        int n9 = this.getChildCount();
        int n10 = 0;
        int n11 = 0;
        int n12 = 0;
        for (n8 = 0; n8 < n9; ++n8) {
            view = this.getChildAt(n8);
            n7 = n12;
            n6 = n11;
            n5 = n10;
            if (view.getVisibility() != 8) {
                int n13;
                int n14;
                int n15;
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                n5 = 0;
                n6 = 0;
                n4 = 0;
                n7 = 0;
                if (this.mLastKnownRound) {
                    if ((layoutParams.boxedEdges & 1) == 0) {
                        n5 = layoutParams.leftMargin;
                    }
                    if ((layoutParams.boxedEdges & 4) == 0) {
                        n6 = layoutParams.rightMargin;
                    }
                    if ((layoutParams.boxedEdges & 2) == 0) {
                        n4 = layoutParams.topMargin;
                    }
                    n15 = n5;
                    n14 = n6;
                    n13 = n4;
                    if ((layoutParams.boxedEdges & 8) == 0) {
                        n7 = layoutParams.bottomMargin;
                        n13 = n4;
                        n14 = n6;
                        n15 = n5;
                    }
                } else {
                    n15 = layoutParams.leftMargin;
                    n13 = layoutParams.topMargin;
                    n14 = layoutParams.rightMargin;
                    n7 = layoutParams.bottomMargin;
                }
                this.measureChildWithMargins(view, n2, 0, n3, 0);
                n5 = Math.max(n10, view.getMeasuredWidth() + n15 + n14);
                n6 = Math.max(n11, view.getMeasuredHeight() + n13 + n7);
                n7 = BoxInsetLayout.combineMeasuredStates((int)n12, (int)view.getMeasuredState());
            }
            n12 = n7;
            n11 = n6;
            n10 = n5;
        }
        n5 = this.getPaddingLeft();
        n6 = this.mForegroundPadding.left;
        n4 = this.getPaddingRight();
        n8 = this.mForegroundPadding.right;
        n7 = Math.max(n11 + (this.getPaddingTop() + this.mForegroundPadding.top + this.getPaddingBottom() + this.mForegroundPadding.bottom), this.getSuggestedMinimumHeight());
        n4 = Math.max(n10 + (n5 + n6 + n4 + n8), this.getSuggestedMinimumWidth());
        view = this.getForeground();
        n6 = n7;
        n5 = n4;
        if (view != null) {
            n6 = Math.max(n7, view.getMinimumHeight());
            n5 = Math.max(n4, view.getMinimumWidth());
        }
        this.setMeasuredDimension(BoxInsetLayout.resolveSizeAndState((int)n5, (int)n2, (int)n12), BoxInsetLayout.resolveSizeAndState((int)n6, (int)n3, (int)(n12 << 16)));
        n6 = (int)(FACTOR * (float)Math.max(this.getMeasuredWidth(), this.getMeasuredHeight()));
        n5 = 0;
        while (n5 < n9) {
            this.measureChild(n2, n3, n6, n5);
            ++n5;
        }
        return;
    }

    public void setForeground(Drawable drawable2) {
        super.setForeground(drawable2);
        if (this.mForegroundPadding == null) {
            this.mForegroundPadding = new Rect();
        }
        drawable2.getPadding(this.mForegroundPadding);
    }

    public static class LayoutParams
    extends FrameLayout.LayoutParams {
        public static final int BOX_ALL = 15;
        public static final int BOX_BOTTOM = 8;
        public static final int BOX_LEFT = 1;
        public static final int BOX_NONE = 0;
        public static final int BOX_RIGHT = 4;
        public static final int BOX_TOP = 2;
        public int boxedEdges = 0;

        public LayoutParams(int n2, int n3) {
            super(n2, n3);
        }

        public LayoutParams(int n2, int n3, int n4) {
            super(n2, n3, n4);
        }

        public LayoutParams(int n2, int n3, int n4, int n5) {
            super(n2, n3, n4);
            this.boxedEdges = n5;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, R.styleable.BoxInsetLayout_Layout, 0, 0);
            this.boxedEdges = context.getInt(R.styleable.BoxInsetLayout_Layout_layout_box, 0);
            context.recycle();
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((FrameLayout.LayoutParams)layoutParams);
            this.boxedEdges = layoutParams.boxedEdges;
            this.gravity = layoutParams.gravity;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(FrameLayout.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }
}

