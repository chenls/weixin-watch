/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.Typeface
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.animation.Interpolator
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.Toolbar
 */
package ticwear.design.widget;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.Toolbar;
import ticwear.design.R;
import ticwear.design.widget.AnimationUtils;
import ticwear.design.widget.AppBarLayout;
import ticwear.design.widget.CollapsingTextHelper;
import ticwear.design.widget.ViewGroupUtils;
import ticwear.design.widget.ViewOffsetHelper;

public class CollapsingToolbarLayout
extends FrameLayout {
    private static final int SCRIM_ANIMATION_DURATION = 600;
    private final CollapsingTextHelper mCollapsingTextHelper;
    private boolean mCollapsingTitleEnabled;
    private Drawable mContentScrim;
    private int mCurrentOffset;
    private boolean mDrawCollapsingTitle;
    private View mDummyView;
    private int mExpandedMarginBottom;
    private int mExpandedMarginEnd;
    private int mExpandedMarginStart;
    private int mExpandedMarginTop;
    private WindowInsetsCompat mLastInsets;
    private AppBarLayout.OnOffsetChangedListener mOnOffsetChangedListener;
    private boolean mRefreshToolbar = true;
    private int mScrimAlpha;
    private ValueAnimator mScrimAnimator;
    private boolean mScrimsAreShown;
    private Drawable mStatusBarScrim;
    private final Rect mTmpRect = new Rect();
    private Toolbar mToolbar;
    private int mToolbarId;

    public CollapsingToolbarLayout(Context context) {
        this(context, null);
    }

    public CollapsingToolbarLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CollapsingToolbarLayout(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.mCollapsingTextHelper = new CollapsingTextHelper((View)this);
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
        this.setContentScrim(context.getDrawable(R.styleable.CollapsingToolbarLayout_tic_contentScrim));
        this.setStatusBarScrim(context.getDrawable(R.styleable.CollapsingToolbarLayout_tic_statusBarScrim));
        this.mToolbarId = context.getResourceId(R.styleable.CollapsingToolbarLayout_tic_toolbarId, -1);
        context.recycle();
        this.setWillNotDraw(false);
        ViewCompat.setOnApplyWindowInsetsListener((View)this, new OnApplyWindowInsetsListener(){

            @Override
            public WindowInsetsCompat onApplyWindowInsets(View object, WindowInsetsCompat windowInsetsCompat) {
                object = windowInsetsCompat;
                if (CollapsingToolbarLayout.this.isShown()) {
                    CollapsingToolbarLayout.access$002(CollapsingToolbarLayout.this, windowInsetsCompat);
                    CollapsingToolbarLayout.this.requestLayout();
                    object = windowInsetsCompat.consumeSystemWindowInsets();
                }
                return object;
            }
        });
    }

    static /* synthetic */ WindowInsetsCompat access$002(CollapsingToolbarLayout collapsingToolbarLayout, WindowInsetsCompat windowInsetsCompat) {
        collapsingToolbarLayout.mLastInsets = windowInsetsCompat;
        return windowInsetsCompat;
    }

    static /* synthetic */ int access$302(CollapsingToolbarLayout collapsingToolbarLayout, int n2) {
        collapsingToolbarLayout.mCurrentOffset = n2;
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void animateScrim(int n2) {
        this.ensureToolbar();
        if (this.mScrimAnimator == null) {
            this.mScrimAnimator = new ValueAnimator();
            this.mScrimAnimator.setDuration(600L);
            ValueAnimator valueAnimator = this.mScrimAnimator;
            Interpolator interpolator2 = n2 > this.mScrimAlpha ? AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR : AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR;
            valueAnimator.setInterpolator((TimeInterpolator)interpolator2);
            this.mScrimAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    CollapsingToolbarLayout.this.setScrimAlpha((Integer)valueAnimator.getAnimatedValue());
                }
            });
        } else if (this.mScrimAnimator.isRunning()) {
            this.mScrimAnimator.cancel();
        }
        this.mScrimAnimator.setIntValues(new int[]{this.mScrimAlpha, n2});
        this.mScrimAnimator.start();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void ensureToolbar() {
        Toolbar toolbar;
        Toolbar toolbar2;
        Toolbar toolbar3;
        block6: {
            View view;
            if (!this.mRefreshToolbar) {
                return;
            }
            toolbar3 = null;
            toolbar2 = null;
            int n2 = 0;
            int n3 = this.getChildCount();
            while (true) {
                toolbar = toolbar2;
                if (n2 >= n3) break block6;
                view = this.getChildAt(n2);
                toolbar = toolbar3;
                if (view instanceof Toolbar) {
                    if (this.mToolbarId == -1) break;
                    if (this.mToolbarId == view.getId()) {
                        toolbar = (Toolbar)view;
                        break block6;
                    }
                    toolbar = toolbar3;
                    if (toolbar3 == null) {
                        toolbar = (Toolbar)view;
                    }
                }
                ++n2;
                toolbar3 = toolbar;
            }
            toolbar = (Toolbar)view;
        }
        toolbar2 = toolbar;
        if (toolbar == null) {
            toolbar2 = toolbar3;
        }
        this.mToolbar = toolbar2;
        this.updateDummyView();
        this.mRefreshToolbar = false;
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

    private void setScrimAlpha(int n2) {
        if (n2 != this.mScrimAlpha) {
            if (this.mContentScrim != null && this.mToolbar != null) {
                ViewCompat.postInvalidateOnAnimation((View)this.mToolbar);
            }
            this.mScrimAlpha = n2;
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    private void updateDummyView() {
        ViewParent viewParent;
        if (!this.mCollapsingTitleEnabled && this.mDummyView != null && (viewParent = this.mDummyView.getParent()) instanceof ViewGroup) {
            ((ViewGroup)viewParent).removeView(this.mDummyView);
        }
        if (this.mCollapsingTitleEnabled && this.mToolbar != null) {
            if (this.mDummyView == null) {
                this.mDummyView = new View(this.getContext());
            }
            if (this.mDummyView.getParent() == null) {
                this.mToolbar.addView(this.mDummyView, -1, -1);
            }
        }
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.ensureToolbar();
        if (this.mToolbar == null && this.mContentScrim != null && this.mScrimAlpha > 0) {
            this.mContentScrim.mutate().setAlpha(this.mScrimAlpha);
            this.mContentScrim.draw(canvas);
        }
        if (this.mCollapsingTitleEnabled && this.mDrawCollapsingTitle) {
            this.mCollapsingTextHelper.draw(canvas);
        }
        if (this.mStatusBarScrim == null) return;
        if (this.mScrimAlpha <= 0) return;
        if (this.mLastInsets == null) return;
        int n2 = this.mLastInsets.getSystemWindowInsetTop();
        if (n2 <= 0) return;
        this.mStatusBarScrim.setBounds(0, -this.mCurrentOffset, this.getWidth(), n2 - this.mCurrentOffset);
        this.mStatusBarScrim.mutate().setAlpha(this.mScrimAlpha);
        this.mStatusBarScrim.draw(canvas);
    }

    protected boolean drawChild(Canvas canvas, View view, long l2) {
        this.ensureToolbar();
        if (view == this.mToolbar && this.mContentScrim != null && this.mScrimAlpha > 0) {
            this.mContentScrim.mutate().setAlpha(this.mScrimAlpha);
            this.mContentScrim.draw(canvas);
        }
        return super.drawChild(canvas, view, l2);
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

    public Drawable getContentScrim() {
        return this.mContentScrim;
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

    final int getScrimTriggerOffset() {
        return ViewCompat.getMinimumHeight((View)this) * 2;
    }

    public Drawable getStatusBarScrim() {
        return this.mStatusBarScrim;
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
        Object object;
        super.onLayout(bl2, n2, n3, n4, n5);
        if (this.mCollapsingTitleEnabled && this.mDummyView != null) {
            this.mDrawCollapsingTitle = this.mDummyView.isShown();
            if (this.mDrawCollapsingTitle) {
                ViewGroupUtils.getDescendantRect((ViewGroup)this, this.mDummyView, this.mTmpRect);
                this.mCollapsingTextHelper.setCollapsedBounds(this.mTmpRect.left, n5 - this.mTmpRect.height(), this.mTmpRect.right, n5);
                int n6 = ViewCompat.getLayoutDirection((View)this) == 1 ? 1 : 0;
                object = this.mCollapsingTextHelper;
                int n7 = n6 != 0 ? this.mExpandedMarginEnd : this.mExpandedMarginStart;
                int n8 = this.mTmpRect.bottom;
                int n9 = this.mExpandedMarginTop;
                n6 = n6 != 0 ? this.mExpandedMarginStart : this.mExpandedMarginEnd;
                ((CollapsingTextHelper)object).setExpandedBounds(n7, n9 + n8, n4 - n2 - n6, n5 - n3 - this.mExpandedMarginBottom);
                this.mCollapsingTextHelper.recalculate();
            }
        }
        n3 = this.getChildCount();
        for (n2 = 0; n2 < n3; ++n2) {
            object = this.getChildAt(n2);
            if (this.mLastInsets != null && !ViewCompat.getFitsSystemWindows((View)object)) {
                n4 = this.mLastInsets.getSystemWindowInsetTop();
                if (object.getTop() < n4) {
                    object.offsetTopAndBottom(n4);
                }
            }
            CollapsingToolbarLayout.getViewOffsetHelper((View)object).onViewLayout();
        }
        if (this.mToolbar != null) {
            if (this.mCollapsingTitleEnabled && TextUtils.isEmpty((CharSequence)this.mCollapsingTextHelper.getText())) {
                this.mCollapsingTextHelper.setText(this.mToolbar.getTitle());
            }
            this.setMinimumHeight(this.mToolbar.getHeight());
        }
    }

    protected void onMeasure(int n2, int n3) {
        this.ensureToolbar();
        super.onMeasure(n2, n3);
    }

    protected void onSizeChanged(int n2, int n3, int n4, int n5) {
        super.onSizeChanged(n2, n3, n4, n5);
        if (this.mContentScrim != null) {
            this.mContentScrim.setBounds(0, 0, n2, n3);
        }
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

    /*
     * Enabled aggressive block sorting
     */
    public void setContentScrim(@Nullable Drawable drawable2) {
        if (this.mContentScrim != drawable2) {
            if (this.mContentScrim != null) {
                this.mContentScrim.setCallback(null);
            }
            if (drawable2 != null) {
                this.mContentScrim = drawable2.mutate();
                drawable2.setBounds(0, 0, this.getWidth(), this.getHeight());
                drawable2.setCallback((Drawable.Callback)this);
                drawable2.setAlpha(this.mScrimAlpha);
            } else {
                this.mContentScrim = null;
            }
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    public void setContentScrimColor(@ColorInt int n2) {
        this.setContentScrim((Drawable)new ColorDrawable(n2));
    }

    public void setContentScrimResource(@DrawableRes int n2) {
        this.setContentScrim(ContextCompat.getDrawable(this.getContext(), n2));
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

    /*
     * Enabled aggressive block sorting
     */
    public void setScrimsShown(boolean bl2) {
        boolean bl3 = ViewCompat.isLaidOut((View)this) && !this.isInEditMode();
        this.setScrimsShown(bl2, bl3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setScrimsShown(boolean bl2, boolean bl3) {
        int n2 = 255;
        if (this.mScrimsAreShown == bl2) return;
        if (bl3) {
            if (!bl2) {
                n2 = 0;
            }
            this.animateScrim(n2);
        } else {
            if (!bl2) {
                n2 = 0;
            }
            this.setScrimAlpha(n2);
        }
        this.mScrimsAreShown = bl2;
    }

    public void setStatusBarScrim(@Nullable Drawable drawable2) {
        if (this.mStatusBarScrim != drawable2) {
            if (this.mStatusBarScrim != null) {
                this.mStatusBarScrim.setCallback(null);
            }
            this.mStatusBarScrim = drawable2;
            drawable2.setCallback((Drawable.Callback)this);
            drawable2.mutate().setAlpha(this.mScrimAlpha);
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    public void setStatusBarScrimColor(@ColorInt int n2) {
        this.setStatusBarScrim((Drawable)new ColorDrawable(n2));
    }

    public void setStatusBarScrimResource(@DrawableRes int n2) {
        this.setStatusBarScrim(ContextCompat.getDrawable(this.getContext(), n2));
    }

    public void setTitle(@Nullable CharSequence charSequence) {
        this.mCollapsingTextHelper.setText(charSequence);
    }

    public void setTitleEnabled(boolean bl2) {
        if (bl2 != this.mCollapsingTitleEnabled) {
            this.mCollapsingTitleEnabled = bl2;
            this.updateDummyView();
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
            Object object;
            int n3;
            boolean bl2 = false;
            CollapsingToolbarLayout.access$302(CollapsingToolbarLayout.this, n2);
            int n4 = CollapsingToolbarLayout.this.mLastInsets != null ? CollapsingToolbarLayout.this.mLastInsets.getSystemWindowInsetTop() : 0;
            int n5 = appBarLayout.getTotalScrollRange();
            int n6 = CollapsingToolbarLayout.this.getChildCount();
            block4: for (n3 = 0; n3 < n6; ++n3) {
                object = CollapsingToolbarLayout.this.getChildAt(n3);
                LayoutParams layoutParams = (LayoutParams)object.getLayoutParams();
                ViewOffsetHelper viewOffsetHelper = CollapsingToolbarLayout.getViewOffsetHelper((View)object);
                switch (layoutParams.mCollapseMode) {
                    case 1: {
                        if (CollapsingToolbarLayout.this.getHeight() - n4 + n2 >= object.getHeight()) {
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
            if (CollapsingToolbarLayout.this.mContentScrim != null || CollapsingToolbarLayout.this.mStatusBarScrim != null) {
                object = CollapsingToolbarLayout.this;
                if (CollapsingToolbarLayout.this.getHeight() + n2 < CollapsingToolbarLayout.this.getScrimTriggerOffset() + n4) {
                    bl2 = true;
                }
                ((CollapsingToolbarLayout)((Object)object)).setScrimsShown(bl2);
            }
            if (CollapsingToolbarLayout.this.mStatusBarScrim != null && n4 > 0) {
                ViewCompat.postInvalidateOnAnimation((View)CollapsingToolbarLayout.this);
            }
            n3 = CollapsingToolbarLayout.this.getHeight();
            n6 = ViewCompat.getMinimumHeight((View)CollapsingToolbarLayout.this);
            CollapsingToolbarLayout.this.mCollapsingTextHelper.setExpansionFraction((float)Math.abs(n2) / (float)(n3 - n6 - n4));
            if (Math.abs(n2) == n5) {
                ViewCompat.setElevation((View)appBarLayout, appBarLayout.getTargetElevation());
                return;
            }
            ViewCompat.setElevation((View)appBarLayout, 0.0f);
        }
    }
}

