/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Typeface
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 */
package ticwear.design.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import ticwear.design.R;
import ticwear.design.widget.AnimationUtils;
import ticwear.design.widget.AppBarLayout;
import ticwear.design.widget.CollapsingTextHelper;
import ticwear.design.widget.ViewOffsetHelper;

public class StretchingLayout
extends FrameLayout {
    private final CollapsingTextHelper mCollapsingTextHelper = new CollapsingTextHelper((View)this);
    private boolean mCollapsingTitleEnabled;
    private int mCurrentOffset;
    private int mExpandedMarginBottom;
    private int mExpandedMarginEnd;
    private int mExpandedMarginStart;
    private int mExpandedMarginTop;
    private WindowInsetsCompat mLastInsets;
    private AppBarLayout.OnOffsetChangedListener mOnOffsetChangedListener;

    public StretchingLayout(Context context) {
        this(context, null);
    }

    public StretchingLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public StretchingLayout(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.mCollapsingTextHelper.setTextSizeInterpolator(AnimationUtils.DECELERATE_INTERPOLATOR);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.CollapsingToolbarLayout, n2, R.style.Widget_Ticwear_CollapsingToolbar);
        this.mCollapsingTextHelper.setExpandedTextGravity(context.getInt(R.styleable.CollapsingToolbarLayout_tic_expandedTitleGravity, 8388691));
        this.mCollapsingTextHelper.setCollapsedTextGravity(context.getInt(R.styleable.CollapsingToolbarLayout_tic_collapsedTitleGravity, 8388627));
        this.mExpandedMarginBottom = n2 = context.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_tic_expandedTitleMargin, 0);
        this.mExpandedMarginEnd = n2;
        this.mExpandedMarginTop = n2;
        this.mExpandedMarginStart = n2;
        if (context.hasValue(R.styleable.CollapsingToolbarLayout_tic_expandedTitleMarginStart)) {
            this.mExpandedMarginStart = context.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_tic_expandedTitleMarginStart, 0);
        }
        if (context.hasValue(R.styleable.CollapsingToolbarLayout_tic_expandedTitleMarginEnd)) {
            this.mExpandedMarginEnd = context.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_tic_expandedTitleMarginEnd, 0);
        }
        if (context.hasValue(R.styleable.CollapsingToolbarLayout_tic_expandedTitleMarginTop)) {
            this.mExpandedMarginTop = context.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_tic_expandedTitleMarginTop, 0);
        }
        if (context.hasValue(R.styleable.CollapsingToolbarLayout_tic_expandedTitleMarginBottom)) {
            this.mExpandedMarginBottom = context.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_tic_expandedTitleMarginBottom, 0);
        }
        this.mCollapsingTitleEnabled = context.getBoolean(R.styleable.CollapsingToolbarLayout_tic_titleEnabled, true);
        this.setTitle(context.getText(R.styleable.CollapsingToolbarLayout_android_title));
        this.mCollapsingTextHelper.setExpandedTextAppearance(R.style.TextAppearance_Ticwear_CollapsingToolbar_Expanded);
        this.mCollapsingTextHelper.setCollapsedTextAppearance(R.style.TextAppearance_Ticwear_TitleBar_Title);
        if (context.hasValue(R.styleable.CollapsingToolbarLayout_tic_expandedTitleTextAppearance)) {
            this.mCollapsingTextHelper.setExpandedTextAppearance(context.getResourceId(R.styleable.CollapsingToolbarLayout_tic_expandedTitleTextAppearance, 0));
        }
        if (context.hasValue(R.styleable.CollapsingToolbarLayout_tic_collapsedTitleTextAppearance)) {
            this.mCollapsingTextHelper.setCollapsedTextAppearance(context.getResourceId(R.styleable.CollapsingToolbarLayout_tic_collapsedTitleTextAppearance, 0));
        }
        context.recycle();
        this.setWillNotDraw(false);
        ViewCompat.setOnApplyWindowInsetsListener((View)this, new OnApplyWindowInsetsListener(){

            @Override
            public WindowInsetsCompat onApplyWindowInsets(View object, WindowInsetsCompat windowInsetsCompat) {
                object = windowInsetsCompat;
                if (StretchingLayout.this.isShown()) {
                    StretchingLayout.access$002(StretchingLayout.this, windowInsetsCompat);
                    StretchingLayout.this.requestLayout();
                    object = windowInsetsCompat.consumeSystemWindowInsets();
                }
                return object;
            }
        });
    }

    static /* synthetic */ WindowInsetsCompat access$002(StretchingLayout stretchingLayout, WindowInsetsCompat windowInsetsCompat) {
        stretchingLayout.mLastInsets = windowInsetsCompat;
        return windowInsetsCompat;
    }

    static /* synthetic */ int access$202(StretchingLayout stretchingLayout, int n2) {
        stretchingLayout.mCurrentOffset = n2;
        return n2;
    }

    private static ViewOffsetHelper getViewOffsetHelper(View view) {
        ViewOffsetHelper viewOffsetHelper;
        ViewOffsetHelper viewOffsetHelper2 = viewOffsetHelper = (ViewOffsetHelper)view.getTag(R.id.tic_view_offset_helper);
        if (viewOffsetHelper == null) {
            viewOffsetHelper2 = new ViewOffsetHelper(view);
            view.setTag(R.id.tic_view_offset_helper, (Object)viewOffsetHelper2);
        }
        return viewOffsetHelper2;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mCollapsingTitleEnabled) {
            this.mCollapsingTextHelper.draw(canvas);
        }
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(super.generateDefaultLayoutParams());
    }

    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected FrameLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public int getCollapsedTitleGravity() {
        return this.mCollapsingTextHelper.getCollapsedTextGravity();
    }

    @NonNull
    public Typeface getCollapsedTitleTypeface() {
        return this.mCollapsingTextHelper.getCollapsedTypeface();
    }

    public int getExpandedTitleGravity() {
        return this.mCollapsingTextHelper.getExpandedTextGravity();
    }

    public int getExpandedTitleMarginBottom() {
        return this.mExpandedMarginBottom;
    }

    public int getExpandedTitleMarginEnd() {
        return this.mExpandedMarginEnd;
    }

    public int getExpandedTitleMarginStart() {
        return this.mExpandedMarginStart;
    }

    public int getExpandedTitleMarginTop() {
        return this.mExpandedMarginTop;
    }

    @NonNull
    public Typeface getExpandedTitleTypeface() {
        return this.mCollapsingTextHelper.getExpandedTypeface();
    }

    public int getMaxStretchingHeight() {
        throw new RuntimeException("Stub!");
    }

    @Nullable
    public CharSequence getTitle() {
        if (this.mCollapsingTitleEnabled) {
            return this.mCollapsingTextHelper.getText();
        }
        return null;
    }

    public boolean isTitleEnabled() {
        return this.mCollapsingTitleEnabled;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent viewParent = this.getParent();
        if (viewParent instanceof AppBarLayout) {
            if (this.mOnOffsetChangedListener == null) {
                this.mOnOffsetChangedListener = new OffsetUpdateListener();
            }
            ((AppBarLayout)viewParent).addOnOffsetChangedListener(this.mOnOffsetChangedListener);
        }
    }

    protected void onDetachedFromWindow() {
        ViewParent viewParent = this.getParent();
        if (this.mOnOffsetChangedListener != null && viewParent instanceof AppBarLayout) {
            ((AppBarLayout)viewParent).removeOnOffsetChangedListener(this.mOnOffsetChangedListener);
        }
        super.onDetachedFromWindow();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        CollapsingTextHelper collapsingTextHelper;
        super.onLayout(bl2, n2, n3, n4, n5);
        if (this.mCollapsingTitleEnabled) {
            int n6 = ViewCompat.getLayoutDirection((View)this) == 1 ? 1 : 0;
            collapsingTextHelper = this.mCollapsingTextHelper;
            int n7 = n6 != 0 ? this.mExpandedMarginEnd : this.mExpandedMarginStart;
            int n8 = this.getMinimumHeight();
            int n9 = n6 != 0 ? this.mExpandedMarginStart : this.mExpandedMarginEnd;
            collapsingTextHelper.setCollapsedBounds(n7, n5 - n8, n4 - n2 - n9, n5);
            collapsingTextHelper = this.mCollapsingTextHelper;
            n7 = n6 != 0 ? this.mExpandedMarginEnd : this.mExpandedMarginStart;
            n9 = this.mExpandedMarginTop;
            n6 = n6 != 0 ? this.mExpandedMarginStart : this.mExpandedMarginEnd;
            collapsingTextHelper.setExpandedBounds(n7, n3 + n9, n4 - n2 - n6, n5 - n3 - this.mExpandedMarginBottom);
            this.mCollapsingTextHelper.recalculate();
        }
        n2 = 0;
        n3 = this.getChildCount();
        while (n2 < n3) {
            collapsingTextHelper = this.getChildAt(n2);
            if (this.mLastInsets != null && !ViewCompat.getFitsSystemWindows((View)collapsingTextHelper)) {
                n4 = this.mLastInsets.getSystemWindowInsetTop();
                if (collapsingTextHelper.getTop() < n4) {
                    collapsingTextHelper.offsetTopAndBottom(n4);
                }
            }
            StretchingLayout.getViewOffsetHelper((View)collapsingTextHelper).onViewLayout();
            ++n2;
        }
        return;
    }

    public void setCollapsedTitleGravity(int n2) {
        this.mCollapsingTextHelper.setCollapsedTextGravity(n2);
    }

    public void setCollapsedTitleTextAppearance(@StyleRes int n2) {
        this.mCollapsingTextHelper.setCollapsedTextAppearance(n2);
    }

    public void setCollapsedTitleTextColor(@ColorInt int n2) {
        this.mCollapsingTextHelper.setCollapsedTextColor(n2);
    }

    public void setCollapsedTitleTypeface(@Nullable Typeface typeface) {
        this.mCollapsingTextHelper.setCollapsedTypeface(typeface);
    }

    public void setExpandedTitleColor(@ColorInt int n2) {
        this.mCollapsingTextHelper.setExpandedTextColor(n2);
    }

    public void setExpandedTitleGravity(int n2) {
        this.mCollapsingTextHelper.setExpandedTextGravity(n2);
    }

    public void setExpandedTitleMargin(int n2, int n3, int n4, int n5) {
        this.mExpandedMarginStart = n2;
        this.mExpandedMarginTop = n3;
        this.mExpandedMarginEnd = n4;
        this.mExpandedMarginBottom = n5;
        this.requestLayout();
    }

    public void setExpandedTitleMarginBottom(int n2) {
        this.mExpandedMarginBottom = n2;
        this.requestLayout();
    }

    public void setExpandedTitleMarginEnd(int n2) {
        this.mExpandedMarginEnd = n2;
        this.requestLayout();
    }

    public void setExpandedTitleMarginStart(int n2) {
        this.mExpandedMarginStart = n2;
        this.requestLayout();
    }

    public void setExpandedTitleMarginTop(int n2) {
        this.mExpandedMarginTop = n2;
        this.requestLayout();
    }

    public void setExpandedTitleTextAppearance(@StyleRes int n2) {
        this.mCollapsingTextHelper.setExpandedTextAppearance(n2);
    }

    public void setExpandedTitleTypeface(@Nullable Typeface typeface) {
        this.mCollapsingTextHelper.setExpandedTypeface(typeface);
    }

    public void setMaxStretchingHeight(int n2) {
        throw new RuntimeException("Stub!");
    }

    public void setTitle(@Nullable CharSequence charSequence) {
        this.mCollapsingTextHelper.setText(charSequence);
    }

    public void setTitleEnabled(boolean bl2) {
        if (bl2 != this.mCollapsingTitleEnabled) {
            this.mCollapsingTitleEnabled = bl2;
            this.requestLayout();
        }
    }

    public static class LayoutParams
    extends FrameLayout.LayoutParams {
        public static final int COLLAPSE_MODE_OFF = 0;
        public static final int COLLAPSE_MODE_PARALLAX = 2;
        public static final int COLLAPSE_MODE_PIN = 1;
        private static final float DEFAULT_PARALLAX_MULTIPLIER = 0.5f;
        int mCollapseMode = 0;
        float mParallaxMult = 0.5f;

        public LayoutParams(int n2, int n3) {
            super(n2, n3);
        }

        public LayoutParams(int n2, int n3, int n4) {
            super(n2, n3, n4);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, R.styleable.CollapsingAppBarLayout_LayoutParams);
            this.mCollapseMode = context.getInt(R.styleable.CollapsingAppBarLayout_LayoutParams_tic_layout_collapseMode, 0);
            this.setParallaxMultiplier(context.getFloat(R.styleable.CollapsingAppBarLayout_LayoutParams_tic_layout_collapseParallaxMultiplier, 0.5f));
            context.recycle();
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

        public int getCollapseMode() {
            return this.mCollapseMode;
        }

        public float getParallaxMultiplier() {
            return this.mParallaxMult;
        }

        public void setCollapseMode(int n2) {
            this.mCollapseMode = n2;
        }

        public void setParallaxMultiplier(float f2) {
            this.mParallaxMult = f2;
        }
    }

    private class OffsetUpdateListener
    implements AppBarLayout.OnOffsetChangedListener {
        private OffsetUpdateListener() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int n2) {
            int n3;
            StretchingLayout.access$202(StretchingLayout.this, n2);
            int n4 = StretchingLayout.this.mLastInsets != null ? StretchingLayout.this.mLastInsets.getSystemWindowInsetTop() : 0;
            int n5 = appBarLayout.getTotalScrollRange();
            int n6 = StretchingLayout.this.getChildCount();
            block4: for (n3 = 0; n3 < n6; ++n3) {
                View view = StretchingLayout.this.getChildAt(n3);
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                ViewOffsetHelper viewOffsetHelper = StretchingLayout.getViewOffsetHelper(view);
                switch (layoutParams.mCollapseMode) {
                    case 1: {
                        if (StretchingLayout.this.getHeight() - n4 + n2 >= view.getHeight()) {
                            viewOffsetHelper.setTopAndBottomOffset(-n2);
                        }
                    }
                    default: {
                        continue block4;
                    }
                    case 2: {
                        viewOffsetHelper.setTopAndBottomOffset(Math.round((float)(-n2) * layoutParams.mParallaxMult));
                    }
                }
            }
            n3 = StretchingLayout.this.getHeight();
            n6 = ViewCompat.getMinimumHeight((View)StretchingLayout.this);
            StretchingLayout.this.mCollapsingTextHelper.setExpansionFraction((float)Math.abs(n2) / (float)(n3 - n6 - n4));
            if (Math.abs(n2) == n5) {
                ViewCompat.setElevation((View)appBarLayout, appBarLayout.getTargetElevation());
                return;
            }
            ViewCompat.setElevation((View)appBarLayout, 0.0f);
        }
    }
}

