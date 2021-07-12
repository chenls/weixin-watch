/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
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
 */
package android.support.design.widget;

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
import android.support.design.R;
import android.support.design.widget.AnimationUtils;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingTextHelper;
import android.support.design.widget.ThemeUtils;
import android.support.design.widget.ValueAnimatorCompat;
import android.support.design.widget.ViewGroupUtils;
import android.support.design.widget.ViewOffsetHelper;
import android.support.design.widget.ViewUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

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
    private ValueAnimatorCompat mScrimAnimator;
    private boolean mScrimsAreShown;
    private Drawable mStatusBarScrim;
    private final Rect mTmpRect = new Rect();
    private Toolbar mToolbar;
    private View mToolbarDirectChild;
    private int mToolbarId;

    public CollapsingToolbarLayout(Context context) {
        this(context, null);
    }

    public CollapsingToolbarLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CollapsingToolbarLayout(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        ThemeUtils.checkAppCompatTheme(context);
        this.mCollapsingTextHelper = new CollapsingTextHelper((View)this);
        this.mCollapsingTextHelper.setTextSizeInterpolator(AnimationUtils.DECELERATE_INTERPOLATOR);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.CollapsingToolbarLayout, n2, R.style.Widget_Design_CollapsingToolbar);
        this.mCollapsingTextHelper.setExpandedTextGravity(context.getInt(R.styleable.CollapsingToolbarLayout_expandedTitleGravity, 8388691));
        this.mCollapsingTextHelper.setCollapsedTextGravity(context.getInt(R.styleable.CollapsingToolbarLayout_collapsedTitleGravity, 8388627));
        this.mExpandedMarginBottom = n2 = context.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMargin, 0);
        this.mExpandedMarginEnd = n2;
        this.mExpandedMarginTop = n2;
        this.mExpandedMarginStart = n2;
        if (context.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginStart)) {
            this.mExpandedMarginStart = context.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginStart, 0);
        }
        if (context.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginEnd)) {
            this.mExpandedMarginEnd = context.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginEnd, 0);
        }
        if (context.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginTop)) {
            this.mExpandedMarginTop = context.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginTop, 0);
        }
        if (context.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginBottom)) {
            this.mExpandedMarginBottom = context.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginBottom, 0);
        }
        this.mCollapsingTitleEnabled = context.getBoolean(R.styleable.CollapsingToolbarLayout_titleEnabled, true);
        this.setTitle(context.getText(R.styleable.CollapsingToolbarLayout_title));
        this.mCollapsingTextHelper.setExpandedTextAppearance(R.style.TextAppearance_Design_CollapsingToolbar_Expanded);
        this.mCollapsingTextHelper.setCollapsedTextAppearance(R.style.TextAppearance_AppCompat_Widget_ActionBar_Title);
        if (context.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleTextAppearance)) {
            this.mCollapsingTextHelper.setExpandedTextAppearance(context.getResourceId(R.styleable.CollapsingToolbarLayout_expandedTitleTextAppearance, 0));
        }
        if (context.hasValue(R.styleable.CollapsingToolbarLayout_collapsedTitleTextAppearance)) {
            this.mCollapsingTextHelper.setCollapsedTextAppearance(context.getResourceId(R.styleable.CollapsingToolbarLayout_collapsedTitleTextAppearance, 0));
        }
        this.setContentScrim(context.getDrawable(R.styleable.CollapsingToolbarLayout_contentScrim));
        this.setStatusBarScrim(context.getDrawable(R.styleable.CollapsingToolbarLayout_statusBarScrim));
        this.mToolbarId = context.getResourceId(R.styleable.CollapsingToolbarLayout_toolbarId, -1);
        context.recycle();
        this.setWillNotDraw(false);
        ViewCompat.setOnApplyWindowInsetsListener((View)this, new OnApplyWindowInsetsListener(){

            @Override
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return CollapsingToolbarLayout.this.setWindowInsets(windowInsetsCompat);
            }
        });
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
            this.mScrimAnimator = ViewUtils.createAnimator();
            this.mScrimAnimator.setDuration(600);
            ValueAnimatorCompat valueAnimatorCompat = this.mScrimAnimator;
            Interpolator interpolator2 = n2 > this.mScrimAlpha ? AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR : AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR;
            valueAnimatorCompat.setInterpolator(interpolator2);
            this.mScrimAnimator.setUpdateListener(new ValueAnimatorCompat.AnimatorUpdateListener(){

                @Override
                public void onAnimationUpdate(ValueAnimatorCompat valueAnimatorCompat) {
                    CollapsingToolbarLayout.this.setScrimAlpha(valueAnimatorCompat.getAnimatedIntValue());
                }
            });
        } else if (this.mScrimAnimator.isRunning()) {
            this.mScrimAnimator.cancel();
        }
        this.mScrimAnimator.setIntValues(this.mScrimAlpha, n2);
        this.mScrimAnimator.start();
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private void ensureToolbar() {
        block7: {
            if (!this.mRefreshToolbar) {
                return;
            }
            this.mToolbar = null;
            this.mToolbarDirectChild = null;
            if (this.mToolbarId != -1) {
                this.mToolbar = (Toolbar)this.findViewById(this.mToolbarId);
                if (this.mToolbar != null) {
                    this.mToolbarDirectChild = this.findDirectChild((View)this.mToolbar);
                }
            }
            if (this.mToolbar != null) break block7;
            Object var4_1 = null;
            int n2 = 0;
            int n3 = this.getChildCount();
            while (true) {
                block9: {
                    void var3_7;
                    block8: {
                        Object var3_5 = var4_1;
                        if (n2 >= n3) break block8;
                        View view = this.getChildAt(n2);
                        if (!(view instanceof Toolbar)) break block9;
                        Toolbar toolbar = (Toolbar)view;
                    }
                    this.mToolbar = var3_7;
                    break;
                }
                ++n2;
            }
        }
        this.updateDummyView();
        this.mRefreshToolbar = false;
    }

    private View findDirectChild(View view) {
        View view2 = view;
        for (view = view.getParent(); view != this && view != null; view = view.getParent()) {
            if (!(view instanceof View)) continue;
            view2 = view;
        }
        return view2;
    }

    private static int getHeightWithMargins(@NonNull View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            layoutParams = (ViewGroup.MarginLayoutParams)layoutParams;
            return view.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
        }
        return view.getHeight();
    }

    private static ViewOffsetHelper getViewOffsetHelper(View view) {
        ViewOffsetHelper viewOffsetHelper;
        ViewOffsetHelper viewOffsetHelper2 = viewOffsetHelper = (ViewOffsetHelper)view.getTag(R.id.view_offset_helper);
        if (viewOffsetHelper == null) {
            viewOffsetHelper2 = new ViewOffsetHelper(view);
            view.setTag(R.id.view_offset_helper, (Object)viewOffsetHelper2);
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

    private WindowInsetsCompat setWindowInsets(WindowInsetsCompat windowInsetsCompat) {
        if (this.mLastInsets != windowInsetsCompat) {
            this.mLastInsets = windowInsetsCompat;
            this.requestLayout();
        }
        return windowInsetsCompat.consumeSystemWindowInsets();
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

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] nArray = this.getDrawableState();
        boolean bl2 = false;
        Drawable drawable2 = this.mStatusBarScrim;
        boolean bl3 = bl2;
        if (drawable2 != null) {
            bl3 = bl2;
            if (drawable2.isStateful()) {
                bl3 = false | drawable2.setState(nArray);
            }
        }
        drawable2 = this.mContentScrim;
        bl2 = bl3;
        if (drawable2 != null) {
            bl2 = bl3;
            if (drawable2.isStateful()) {
                bl2 = bl3 | drawable2.setState(nArray);
            }
        }
        if (bl2) {
            this.invalidate();
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

    @Nullable
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

    @Nullable
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
        ViewCompat.requestApplyInsets((View)this);
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
            bl2 = ViewCompat.isAttachedToWindow(this.mDummyView) && this.mDummyView.getVisibility() == 0;
            this.mDrawCollapsingTitle = bl2;
            if (this.mDrawCollapsingTitle) {
                int n6;
                int n7 = n6 = 0;
                if (this.mToolbarDirectChild != null) {
                    n7 = n6;
                    if (this.mToolbarDirectChild != this) {
                        n7 = ((LayoutParams)this.mToolbarDirectChild.getLayoutParams()).bottomMargin;
                    }
                }
                ViewGroupUtils.getDescendantRect((ViewGroup)this, this.mDummyView, this.mTmpRect);
                this.mCollapsingTextHelper.setCollapsedBounds(this.mTmpRect.left, n5 - this.mTmpRect.height() - n7, this.mTmpRect.right, n5 - n7);
                n6 = ViewCompat.getLayoutDirection((View)this) == 1 ? 1 : 0;
                object = this.mCollapsingTextHelper;
                n7 = n6 != 0 ? this.mExpandedMarginEnd : this.mExpandedMarginStart;
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
                    ViewCompat.offsetTopAndBottom((View)object, n4);
                }
            }
            CollapsingToolbarLayout.getViewOffsetHelper((View)object).onViewLayout();
        }
        if (this.mToolbar != null) {
            if (this.mCollapsingTitleEnabled && TextUtils.isEmpty((CharSequence)this.mCollapsingTextHelper.getText())) {
                this.mCollapsingTextHelper.setText(this.mToolbar.getTitle());
            }
            if (this.mToolbarDirectChild != null && this.mToolbarDirectChild != this) {
                this.setMinimumHeight(CollapsingToolbarLayout.getHeightWithMargins(this.mToolbarDirectChild));
                return;
            }
            this.setMinimumHeight(CollapsingToolbarLayout.getHeightWithMargins((View)this.mToolbar));
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

    public void setContentScrim(@Nullable Drawable drawable2) {
        Drawable drawable3 = null;
        if (this.mContentScrim != drawable2) {
            if (this.mContentScrim != null) {
                this.mContentScrim.setCallback(null);
            }
            if (drawable2 != null) {
                drawable3 = drawable2.mutate();
            }
            this.mContentScrim = drawable3;
            if (this.mContentScrim != null) {
                this.mContentScrim.setBounds(0, 0, this.getWidth(), this.getHeight());
                this.mContentScrim.setCallback((Drawable.Callback)this);
                this.mContentScrim.setAlpha(this.mScrimAlpha);
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

    /*
     * Enabled aggressive block sorting
     */
    public void setStatusBarScrim(@Nullable Drawable drawable2) {
        Drawable drawable3 = null;
        if (this.mStatusBarScrim == drawable2) return;
        if (this.mStatusBarScrim != null) {
            this.mStatusBarScrim.setCallback(null);
        }
        if (drawable2 != null) {
            drawable3 = drawable2.mutate();
        }
        this.mStatusBarScrim = drawable3;
        if (this.mStatusBarScrim != null) {
            if (this.mStatusBarScrim.isStateful()) {
                this.mStatusBarScrim.setState(this.getDrawableState());
            }
            DrawableCompat.setLayoutDirection((Drawable)this.mStatusBarScrim, (int)ViewCompat.getLayoutDirection((View)this));
            drawable2 = this.mStatusBarScrim;
            boolean bl2 = this.getVisibility() == 0;
            drawable2.setVisible(bl2, false);
            this.mStatusBarScrim.setCallback((Drawable.Callback)this);
            this.mStatusBarScrim.setAlpha(this.mScrimAlpha);
        }
        ViewCompat.postInvalidateOnAnimation((View)this);
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

    /*
     * Enabled aggressive block sorting
     */
    public void setVisibility(int n2) {
        super.setVisibility(n2);
        boolean bl2 = n2 == 0;
        if (this.mStatusBarScrim != null && this.mStatusBarScrim.isVisible() != bl2) {
            this.mStatusBarScrim.setVisible(bl2, false);
        }
        if (this.mContentScrim != null && this.mContentScrim.isVisible() != bl2) {
            this.mContentScrim.setVisible(bl2, false);
        }
    }

    protected boolean verifyDrawable(Drawable drawable2) {
        return super.verifyDrawable(drawable2) || drawable2 == this.mContentScrim || drawable2 == this.mStatusBarScrim;
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
            this.mCollapseMode = context.getInt(R.styleable.CollapsingAppBarLayout_LayoutParams_layout_collapseMode, 0);
            this.setParallaxMultiplier(context.getFloat(R.styleable.CollapsingAppBarLayout_LayoutParams_layout_collapseParallaxMultiplier, 0.5f));
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

