/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.database.DataSetObserver
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.drawable.Drawable
 *  android.text.Layout
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnLongClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.HorizontalScrollView
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 *  android.widget.Toast
 */
package android.support.design.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.R;
import android.support.design.widget.AnimationUtils;
import android.support.design.widget.TabItem;
import android.support.design.widget.ThemeUtils;
import android.support.design.widget.ValueAnimatorCompat;
import android.support.design.widget.ViewUtils;
import android.support.v4.util.Pools;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatDrawableManager;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

public class TabLayout
extends HorizontalScrollView {
    private static final int ANIMATION_DURATION = 300;
    private static final int DEFAULT_GAP_TEXT_ICON = 8;
    private static final int DEFAULT_HEIGHT = 48;
    private static final int DEFAULT_HEIGHT_WITH_TEXT_ICON = 72;
    private static final int FIXED_WRAP_GUTTER_MIN = 16;
    public static final int GRAVITY_CENTER = 1;
    public static final int GRAVITY_FILL = 0;
    private static final int INVALID_WIDTH = -1;
    public static final int MODE_FIXED = 1;
    public static final int MODE_SCROLLABLE = 0;
    private static final int MOTION_NON_ADJACENT_OFFSET = 24;
    private static final int TAB_MIN_WIDTH_MARGIN = 56;
    private static final Pools.Pool<Tab> sTabPool = new Pools.SynchronizedPool<Tab>(16);
    private int mContentInsetStart;
    private int mMode;
    private OnTabSelectedListener mOnTabSelectedListener;
    private TabLayoutOnPageChangeListener mPageChangeListener;
    private PagerAdapter mPagerAdapter;
    private DataSetObserver mPagerAdapterObserver;
    private final int mRequestedTabMaxWidth;
    private final int mRequestedTabMinWidth;
    private ValueAnimatorCompat mScrollAnimator;
    private final int mScrollableTabMinWidth;
    private Tab mSelectedTab;
    private final int mTabBackgroundResId;
    private int mTabGravity;
    private int mTabMaxWidth;
    private int mTabPaddingBottom;
    private int mTabPaddingEnd;
    private int mTabPaddingStart;
    private int mTabPaddingTop;
    private final SlidingTabStrip mTabStrip;
    private int mTabTextAppearance;
    private ColorStateList mTabTextColors;
    private float mTabTextMultiLineSize;
    private float mTabTextSize;
    private final Pools.Pool<TabView> mTabViewPool;
    private final ArrayList<Tab> mTabs;
    private ViewPager mViewPager;

    public TabLayout(Context context) {
        this(context, null);
    }

    public TabLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TabLayout(Context context, AttributeSet attributeSet, int n2) {
        block4: {
            super(context, attributeSet, n2);
            this.mTabs = new ArrayList();
            this.mTabMaxWidth = Integer.MAX_VALUE;
            this.mTabViewPool = new Pools.SimplePool<TabView>(12);
            ThemeUtils.checkAppCompatTheme(context);
            this.setHorizontalScrollBarEnabled(false);
            this.mTabStrip = new SlidingTabStrip(context);
            super.addView((View)this.mTabStrip, 0, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -1));
            attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.TabLayout, n2, R.style.Widget_Design_TabLayout);
            this.mTabStrip.setSelectedIndicatorHeight(attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabIndicatorHeight, 0));
            this.mTabStrip.setSelectedIndicatorColor(attributeSet.getColor(R.styleable.TabLayout_tabIndicatorColor, 0));
            this.mTabPaddingBottom = n2 = attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabPadding, 0);
            this.mTabPaddingEnd = n2;
            this.mTabPaddingTop = n2;
            this.mTabPaddingStart = n2;
            this.mTabPaddingStart = attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingStart, this.mTabPaddingStart);
            this.mTabPaddingTop = attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingTop, this.mTabPaddingTop);
            this.mTabPaddingEnd = attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingEnd, this.mTabPaddingEnd);
            this.mTabPaddingBottom = attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingBottom, this.mTabPaddingBottom);
            this.mTabTextAppearance = attributeSet.getResourceId(R.styleable.TabLayout_tabTextAppearance, R.style.TextAppearance_Design_Tab);
            context = context.obtainStyledAttributes(this.mTabTextAppearance, R.styleable.TextAppearance);
            this.mTabTextSize = context.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0);
            this.mTabTextColors = context.getColorStateList(R.styleable.TextAppearance_android_textColor);
            if (!attributeSet.hasValue(R.styleable.TabLayout_tabTextColor)) break block4;
            this.mTabTextColors = attributeSet.getColorStateList(R.styleable.TabLayout_tabTextColor);
        }
        if (attributeSet.hasValue(R.styleable.TabLayout_tabSelectedTextColor)) {
            n2 = attributeSet.getColor(R.styleable.TabLayout_tabSelectedTextColor, 0);
            this.mTabTextColors = TabLayout.createColorStateList(this.mTabTextColors.getDefaultColor(), n2);
        }
        this.mRequestedTabMinWidth = attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabMinWidth, -1);
        this.mRequestedTabMaxWidth = attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabMaxWidth, -1);
        this.mTabBackgroundResId = attributeSet.getResourceId(R.styleable.TabLayout_tabBackground, 0);
        this.mContentInsetStart = attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabContentStart, 0);
        this.mMode = attributeSet.getInt(R.styleable.TabLayout_tabMode, 1);
        this.mTabGravity = attributeSet.getInt(R.styleable.TabLayout_tabGravity, 0);
        attributeSet.recycle();
        context = this.getResources();
        this.mTabTextMultiLineSize = context.getDimensionPixelSize(R.dimen.design_tab_text_size_2line);
        this.mScrollableTabMinWidth = context.getDimensionPixelSize(R.dimen.design_tab_scrollable_min_width);
        this.applyModeAndGravity();
        return;
        finally {
            context.recycle();
        }
    }

    static /* synthetic */ int access$2202(TabLayout tabLayout, int n2) {
        tabLayout.mTabGravity = n2;
        return n2;
    }

    private void addTabFromItemView(@NonNull TabItem tabItem) {
        Tab tab = this.newTab();
        if (tabItem.mText != null) {
            tab.setText(tabItem.mText);
        }
        if (tabItem.mIcon != null) {
            tab.setIcon(tabItem.mIcon);
        }
        if (tabItem.mCustomLayout != 0) {
            tab.setCustomView(tabItem.mCustomLayout);
        }
        this.addTab(tab);
    }

    private void addTabView(Tab object, int n2, boolean bl2) {
        object = ((Tab)object).mView;
        this.mTabStrip.addView((View)object, n2, (ViewGroup.LayoutParams)this.createLayoutParamsForTabs());
        if (bl2) {
            ((TabView)((Object)object)).setSelected(true);
        }
    }

    private void addTabView(Tab object, boolean bl2) {
        object = ((Tab)object).mView;
        this.mTabStrip.addView((View)object, (ViewGroup.LayoutParams)this.createLayoutParamsForTabs());
        if (bl2) {
            ((TabView)((Object)object)).setSelected(true);
        }
    }

    private void addViewInternal(View view) {
        if (view instanceof TabItem) {
            this.addTabFromItemView((TabItem)view);
            return;
        }
        throw new IllegalArgumentException("Only TabItem instances can be added to TabLayout");
    }

    private void animateToTab(int n2) {
        int n3;
        if (n2 == -1) {
            return;
        }
        if (this.getWindowToken() == null || !ViewCompat.isLaidOut((View)this) || this.mTabStrip.childrenNeedLayout()) {
            this.setScrollPosition(n2, 0.0f, true);
            return;
        }
        int n4 = this.getScrollX();
        if (n4 != (n3 = this.calculateScrollXForTab(n2, 0.0f))) {
            if (this.mScrollAnimator == null) {
                this.mScrollAnimator = ViewUtils.createAnimator();
                this.mScrollAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
                this.mScrollAnimator.setDuration(300);
                this.mScrollAnimator.setUpdateListener(new ValueAnimatorCompat.AnimatorUpdateListener(){

                    @Override
                    public void onAnimationUpdate(ValueAnimatorCompat valueAnimatorCompat) {
                        TabLayout.this.scrollTo(valueAnimatorCompat.getAnimatedIntValue(), 0);
                    }
                });
            }
            this.mScrollAnimator.setIntValues(n4, n3);
            this.mScrollAnimator.start();
        }
        this.mTabStrip.animateIndicatorToPosition(n2, 300);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void applyModeAndGravity() {
        int n2 = 0;
        if (this.mMode == 0) {
            n2 = Math.max(0, this.mContentInsetStart - this.mTabPaddingStart);
        }
        ViewCompat.setPaddingRelative((View)this.mTabStrip, n2, 0, 0, 0);
        switch (this.mMode) {
            case 1: {
                this.mTabStrip.setGravity(1);
            }
            default: {
                break;
            }
            case 0: {
                this.mTabStrip.setGravity(0x800003);
            }
        }
        this.updateTabViews(true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int calculateScrollXForTab(int n2, float f2) {
        int n3 = 0;
        int n4 = 0;
        if (this.mMode != 0) return n3;
        View view = this.mTabStrip.getChildAt(n2);
        View view2 = n2 + 1 < this.mTabStrip.getChildCount() ? this.mTabStrip.getChildAt(n2 + 1) : null;
        n2 = view != null ? view.getWidth() : 0;
        n3 = n4;
        if (view2 == null) return view.getLeft() + (int)((float)(n2 + n3) * f2 * 0.5f) + view.getWidth() / 2 - this.getWidth() / 2;
        n3 = view2.getWidth();
        return view.getLeft() + (int)((float)(n2 + n3) * f2 * 0.5f) + view.getWidth() / 2 - this.getWidth() / 2;
    }

    private void configureTab(Tab tab, int n2) {
        tab.setPosition(n2);
        this.mTabs.add(n2, tab);
        int n3 = this.mTabs.size();
        ++n2;
        while (n2 < n3) {
            this.mTabs.get(n2).setPosition(n2);
            ++n2;
        }
    }

    private static ColorStateList createColorStateList(int n2, int n3) {
        int[][] nArrayArray = new int[2][];
        int[] nArray = new int[2];
        nArrayArray[0] = SELECTED_STATE_SET;
        nArray[0] = n3;
        n3 = 0 + 1;
        nArrayArray[n3] = EMPTY_STATE_SET;
        nArray[n3] = n2;
        return new ColorStateList((int[][])nArrayArray, nArray);
    }

    private LinearLayout.LayoutParams createLayoutParamsForTabs() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -1);
        this.updateTabViewLayoutParams(layoutParams);
        return layoutParams;
    }

    /*
     * Enabled aggressive block sorting
     */
    private TabView createTabView(@NonNull Tab tab) {
        TabView tabView = this.mTabViewPool != null ? this.mTabViewPool.acquire() : null;
        TabView tabView2 = tabView;
        if (tabView == null) {
            tabView2 = new TabView(this.getContext());
        }
        tabView2.setTab(tab);
        tabView2.setFocusable(true);
        tabView2.setMinimumWidth(this.getTabMinWidth());
        return tabView2;
    }

    private int dpToPx(int n2) {
        return Math.round(this.getResources().getDisplayMetrics().density * (float)n2);
    }

    private int getDefaultHeight() {
        boolean bl2 = false;
        int n2 = 0;
        int n3 = this.mTabs.size();
        while (true) {
            block4: {
                boolean bl3;
                block3: {
                    bl3 = bl2;
                    if (n2 >= n3) break block3;
                    Tab tab = this.mTabs.get(n2);
                    if (tab == null || tab.getIcon() == null || TextUtils.isEmpty((CharSequence)tab.getText())) break block4;
                    bl3 = true;
                }
                if (!bl3) break;
                return 72;
            }
            ++n2;
        }
        return 48;
    }

    private float getScrollPosition() {
        return this.mTabStrip.getIndicatorPosition();
    }

    private int getTabMaxWidth() {
        return this.mTabMaxWidth;
    }

    private int getTabMinWidth() {
        if (this.mRequestedTabMinWidth != -1) {
            return this.mRequestedTabMinWidth;
        }
        if (this.mMode == 0) {
            return this.mScrollableTabMinWidth;
        }
        return 0;
    }

    private int getTabScrollRange() {
        return Math.max(0, this.mTabStrip.getWidth() - this.getWidth() - this.getPaddingLeft() - this.getPaddingRight());
    }

    private void populateFromPagerAdapter() {
        this.removeAllTabs();
        if (this.mPagerAdapter != null) {
            int n2;
            int n3 = this.mPagerAdapter.getCount();
            for (n2 = 0; n2 < n3; ++n2) {
                this.addTab(this.newTab().setText(this.mPagerAdapter.getPageTitle(n2)), false);
            }
            if (this.mViewPager != null && n3 > 0 && (n2 = this.mViewPager.getCurrentItem()) != this.getSelectedTabPosition() && n2 < this.getTabCount()) {
                this.selectTab(this.getTabAt(n2));
            }
            return;
        }
        this.removeAllTabs();
    }

    private void removeTabViewAt(int n2) {
        TabView tabView = (TabView)this.mTabStrip.getChildAt(n2);
        this.mTabStrip.removeViewAt(n2);
        if (tabView != null) {
            tabView.reset();
            this.mTabViewPool.release(tabView);
        }
        this.requestLayout();
    }

    private void setPagerAdapter(@Nullable PagerAdapter pagerAdapter, boolean bl2) {
        if (this.mPagerAdapter != null && this.mPagerAdapterObserver != null) {
            this.mPagerAdapter.unregisterDataSetObserver(this.mPagerAdapterObserver);
        }
        this.mPagerAdapter = pagerAdapter;
        if (bl2 && pagerAdapter != null) {
            if (this.mPagerAdapterObserver == null) {
                this.mPagerAdapterObserver = new PagerAdapterObserver();
            }
            pagerAdapter.registerDataSetObserver(this.mPagerAdapterObserver);
        }
        this.populateFromPagerAdapter();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setScrollPosition(int n2, float f2, boolean bl2, boolean bl3) {
        int n3;
        block6: {
            block5: {
                n3 = Math.round((float)n2 + f2);
                if (n3 < 0 || n3 >= this.mTabStrip.getChildCount()) break block5;
                if (bl3) {
                    this.mTabStrip.setIndicatorPositionFromTabPosition(n2, f2);
                }
                if (this.mScrollAnimator != null && this.mScrollAnimator.isRunning()) {
                    this.mScrollAnimator.cancel();
                }
                this.scrollTo(this.calculateScrollXForTab(n2, f2), 0);
                if (bl2) break block6;
            }
            return;
        }
        this.setSelectedTabView(n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setSelectedTabView(int n2) {
        int n3 = this.mTabStrip.getChildCount();
        if (n2 < n3 && !this.mTabStrip.getChildAt(n2).isSelected()) {
            for (int i2 = 0; i2 < n3; ++i2) {
                View view = this.mTabStrip.getChildAt(i2);
                boolean bl2 = i2 == n2;
                view.setSelected(bl2);
            }
        }
    }

    private void updateAllTabs() {
        int n2 = this.mTabs.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            this.mTabs.get(i2).updateView();
        }
    }

    private void updateTabViewLayoutParams(LinearLayout.LayoutParams layoutParams) {
        if (this.mMode == 1 && this.mTabGravity == 0) {
            layoutParams.width = 0;
            layoutParams.weight = 1.0f;
            return;
        }
        layoutParams.width = -2;
        layoutParams.weight = 0.0f;
    }

    private void updateTabViews(boolean bl2) {
        for (int i2 = 0; i2 < this.mTabStrip.getChildCount(); ++i2) {
            View view = this.mTabStrip.getChildAt(i2);
            view.setMinimumWidth(this.getTabMinWidth());
            this.updateTabViewLayoutParams((LinearLayout.LayoutParams)view.getLayoutParams());
            if (!bl2) continue;
            view.requestLayout();
        }
    }

    public void addTab(@NonNull Tab tab) {
        this.addTab(tab, this.mTabs.isEmpty());
    }

    public void addTab(@NonNull Tab tab, int n2) {
        this.addTab(tab, n2, this.mTabs.isEmpty());
    }

    public void addTab(@NonNull Tab tab, int n2, boolean bl2) {
        if (tab.mParent != this) {
            throw new IllegalArgumentException("Tab belongs to a different TabLayout.");
        }
        this.addTabView(tab, n2, bl2);
        this.configureTab(tab, n2);
        if (bl2) {
            tab.select();
        }
    }

    public void addTab(@NonNull Tab tab, boolean bl2) {
        if (tab.mParent != this) {
            throw new IllegalArgumentException("Tab belongs to a different TabLayout.");
        }
        this.addTabView(tab, bl2);
        this.configureTab(tab, this.mTabs.size());
        if (bl2) {
            tab.select();
        }
    }

    public void addView(View view) {
        this.addViewInternal(view);
    }

    public void addView(View view, int n2) {
        this.addViewInternal(view);
    }

    public void addView(View view, int n2, ViewGroup.LayoutParams layoutParams) {
        this.addViewInternal(view);
    }

    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        this.addViewInternal(view);
    }

    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return this.generateDefaultLayoutParams();
    }

    public int getSelectedTabPosition() {
        if (this.mSelectedTab != null) {
            return this.mSelectedTab.getPosition();
        }
        return -1;
    }

    @Nullable
    public Tab getTabAt(int n2) {
        return this.mTabs.get(n2);
    }

    public int getTabCount() {
        return this.mTabs.size();
    }

    public int getTabGravity() {
        return this.mTabGravity;
    }

    public int getTabMode() {
        return this.mMode;
    }

    @Nullable
    public ColorStateList getTabTextColors() {
        return this.mTabTextColors;
    }

    @NonNull
    public Tab newTab() {
        Tab tab;
        Tab tab2 = tab = sTabPool.acquire();
        if (tab == null) {
            tab2 = new Tab();
        }
        Tab.access$002(tab2, this);
        Tab.access$202(tab2, this.createTabView(tab2));
        return tab2;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n2, int n3) {
        int n4 = this.dpToPx(this.getDefaultHeight()) + this.getPaddingTop() + this.getPaddingBottom();
        switch (View.MeasureSpec.getMode((int)n3)) {
            case -2147483648: {
                n3 = View.MeasureSpec.makeMeasureSpec((int)Math.min(n4, View.MeasureSpec.getSize((int)n3)), (int)0x40000000);
                break;
            }
            case 0: {
                n3 = View.MeasureSpec.makeMeasureSpec((int)n4, (int)0x40000000);
            }
        }
        n4 = View.MeasureSpec.getSize((int)n2);
        if (View.MeasureSpec.getMode((int)n2) != 0) {
            n4 = this.mRequestedTabMaxWidth > 0 ? this.mRequestedTabMaxWidth : (n4 -= this.dpToPx(56));
            this.mTabMaxWidth = n4;
        }
        super.onMeasure(n2, n3);
        if (this.getChildCount() != 1) return;
        View view = this.getChildAt(0);
        n2 = 0;
        switch (this.mMode) {
            case 0: {
                if (view.getMeasuredWidth() < this.getMeasuredWidth()) {
                    n2 = 1;
                    break;
                }
                n2 = 0;
            }
            default: {
                break;
            }
            case 1: {
                if (view.getMeasuredWidth() == this.getMeasuredWidth()) return;
                n2 = 1;
                break;
            }
        }
        if (n2 == 0) return;
        n2 = TabLayout.getChildMeasureSpec((int)n3, (int)(this.getPaddingTop() + this.getPaddingBottom()), (int)view.getLayoutParams().height);
        view.measure(View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredWidth(), (int)0x40000000), n2);
    }

    public void removeAllTabs() {
        for (int i2 = this.mTabStrip.getChildCount() - 1; i2 >= 0; --i2) {
            this.removeTabViewAt(i2);
        }
        Iterator<Tab> iterator = this.mTabs.iterator();
        while (iterator.hasNext()) {
            Tab tab = iterator.next();
            iterator.remove();
            tab.reset();
            sTabPool.release(tab);
        }
        this.mSelectedTab = null;
    }

    public void removeTab(Tab tab) {
        if (tab.mParent != this) {
            throw new IllegalArgumentException("Tab does not belong to this TabLayout.");
        }
        this.removeTabAt(tab.getPosition());
    }

    /*
     * Enabled aggressive block sorting
     */
    public void removeTabAt(int n2) {
        int n3 = this.mSelectedTab != null ? this.mSelectedTab.getPosition() : 0;
        this.removeTabViewAt(n2);
        Tab tab = this.mTabs.remove(n2);
        if (tab != null) {
            tab.reset();
            sTabPool.release(tab);
        }
        int n4 = this.mTabs.size();
        for (int i2 = n2; i2 < n4; ++i2) {
            this.mTabs.get(i2).setPosition(i2);
        }
        if (n3 == n2) {
            tab = this.mTabs.isEmpty() ? null : this.mTabs.get(Math.max(0, n2 - 1));
            this.selectTab(tab);
        }
    }

    void selectTab(Tab tab) {
        this.selectTab(tab, true);
    }

    /*
     * Enabled aggressive block sorting
     */
    void selectTab(Tab tab, boolean bl2) {
        if (this.mSelectedTab == tab) {
            if (this.mSelectedTab == null) return;
            if (this.mOnTabSelectedListener != null) {
                this.mOnTabSelectedListener.onTabReselected(this.mSelectedTab);
            }
            this.animateToTab(tab.getPosition());
            return;
        }
        if (bl2) {
            int n2 = tab != null ? tab.getPosition() : -1;
            if (n2 != -1) {
                this.setSelectedTabView(n2);
            }
            if ((this.mSelectedTab == null || this.mSelectedTab.getPosition() == -1) && n2 != -1) {
                this.setScrollPosition(n2, 0.0f, true);
            } else {
                this.animateToTab(n2);
            }
        }
        if (this.mSelectedTab != null && this.mOnTabSelectedListener != null) {
            this.mOnTabSelectedListener.onTabUnselected(this.mSelectedTab);
        }
        this.mSelectedTab = tab;
        if (this.mSelectedTab == null || this.mOnTabSelectedListener == null) return;
        this.mOnTabSelectedListener.onTabSelected(this.mSelectedTab);
    }

    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.mOnTabSelectedListener = onTabSelectedListener;
    }

    public void setScrollPosition(int n2, float f2, boolean bl2) {
        this.setScrollPosition(n2, f2, bl2, true);
    }

    public void setSelectedTabIndicatorColor(@ColorInt int n2) {
        this.mTabStrip.setSelectedIndicatorColor(n2);
    }

    public void setSelectedTabIndicatorHeight(int n2) {
        this.mTabStrip.setSelectedIndicatorHeight(n2);
    }

    public void setTabGravity(int n2) {
        if (this.mTabGravity != n2) {
            this.mTabGravity = n2;
            this.applyModeAndGravity();
        }
    }

    public void setTabMode(int n2) {
        if (n2 != this.mMode) {
            this.mMode = n2;
            this.applyModeAndGravity();
        }
    }

    public void setTabTextColors(int n2, int n3) {
        this.setTabTextColors(TabLayout.createColorStateList(n2, n3));
    }

    public void setTabTextColors(@Nullable ColorStateList colorStateList) {
        if (this.mTabTextColors != colorStateList) {
            this.mTabTextColors = colorStateList;
            this.updateAllTabs();
        }
    }

    @Deprecated
    public void setTabsFromPagerAdapter(@Nullable PagerAdapter pagerAdapter) {
        this.setPagerAdapter(pagerAdapter, false);
    }

    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        if (this.mViewPager != null && this.mPageChangeListener != null) {
            this.mViewPager.removeOnPageChangeListener(this.mPageChangeListener);
        }
        if (viewPager != null) {
            PagerAdapter pagerAdapter = viewPager.getAdapter();
            if (pagerAdapter == null) {
                throw new IllegalArgumentException("ViewPager does not have a PagerAdapter set");
            }
            this.mViewPager = viewPager;
            if (this.mPageChangeListener == null) {
                this.mPageChangeListener = new TabLayoutOnPageChangeListener(this);
            }
            this.mPageChangeListener.reset();
            viewPager.addOnPageChangeListener(this.mPageChangeListener);
            this.setOnTabSelectedListener(new ViewPagerOnTabSelectedListener(viewPager));
            this.setPagerAdapter(pagerAdapter, true);
            return;
        }
        this.mViewPager = null;
        this.setOnTabSelectedListener(null);
        this.setPagerAdapter(null, true);
    }

    public boolean shouldDelayChildPressedState() {
        return this.getTabScrollRange() > 0;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Mode {
    }

    public static interface OnTabSelectedListener {
        public void onTabReselected(Tab var1);

        public void onTabSelected(Tab var1);

        public void onTabUnselected(Tab var1);
    }

    private class PagerAdapterObserver
    extends DataSetObserver {
        private PagerAdapterObserver() {
        }

        public void onChanged() {
            TabLayout.this.populateFromPagerAdapter();
        }

        public void onInvalidated() {
            TabLayout.this.populateFromPagerAdapter();
        }
    }

    private class SlidingTabStrip
    extends LinearLayout {
        private ValueAnimatorCompat mIndicatorAnimator;
        private int mIndicatorLeft;
        private int mIndicatorRight;
        private int mSelectedIndicatorHeight;
        private final Paint mSelectedIndicatorPaint;
        private int mSelectedPosition;
        private float mSelectionOffset;

        SlidingTabStrip(Context context) {
            super(context);
            this.mSelectedPosition = -1;
            this.mIndicatorLeft = -1;
            this.mIndicatorRight = -1;
            this.setWillNotDraw(false);
            this.mSelectedIndicatorPaint = new Paint();
        }

        static /* synthetic */ int access$2502(SlidingTabStrip slidingTabStrip, int n2) {
            slidingTabStrip.mSelectedPosition = n2;
            return n2;
        }

        static /* synthetic */ float access$2602(SlidingTabStrip slidingTabStrip, float f2) {
            slidingTabStrip.mSelectionOffset = f2;
            return f2;
        }

        private void setIndicatorPosition(int n2, int n3) {
            if (n2 != this.mIndicatorLeft || n3 != this.mIndicatorRight) {
                this.mIndicatorLeft = n2;
                this.mIndicatorRight = n3;
                ViewCompat.postInvalidateOnAnimation((View)this);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private void updateIndicatorPosition() {
            int n2;
            int n3;
            block3: {
                block2: {
                    View view = this.getChildAt(this.mSelectedPosition);
                    if (view == null || view.getWidth() <= 0) break block2;
                    int n4 = view.getLeft();
                    int n5 = view.getRight();
                    n3 = n4;
                    n2 = n5;
                    if (this.mSelectionOffset > 0.0f) {
                        n3 = n4;
                        n2 = n5;
                        if (this.mSelectedPosition < this.getChildCount() - 1) {
                            view = this.getChildAt(this.mSelectedPosition + 1);
                            n3 = (int)(this.mSelectionOffset * (float)view.getLeft() + (1.0f - this.mSelectionOffset) * (float)n4);
                            n2 = (int)(this.mSelectionOffset * (float)view.getRight() + (1.0f - this.mSelectionOffset) * (float)n5);
                        }
                    }
                    break block3;
                }
                n2 = -1;
                n3 = -1;
            }
            this.setIndicatorPosition(n3, n2);
        }

        /*
         * Enabled aggressive block sorting
         */
        void animateIndicatorToPosition(final int n2, int n3) {
            if (this.mIndicatorAnimator != null && this.mIndicatorAnimator.isRunning()) {
                this.mIndicatorAnimator.cancel();
            }
            final int n4 = ViewCompat.getLayoutDirection((View)this) == 1 ? 1 : 0;
            Object object = this.getChildAt(n2);
            if (object == null) {
                this.updateIndicatorPosition();
                return;
            } else {
                int n5;
                final int n6 = object.getLeft();
                final int n7 = object.getRight();
                if (Math.abs(n2 - this.mSelectedPosition) <= 1) {
                    n4 = this.mIndicatorLeft;
                    n5 = this.mIndicatorRight;
                } else {
                    n5 = TabLayout.this.dpToPx(24);
                    n4 = n2 < this.mSelectedPosition ? (n4 != 0 ? (n5 = n6 - n5) : (n5 = n7 + n5)) : (n4 != 0 ? (n5 = n7 + n5) : (n5 = n6 - n5));
                }
                if (n4 == n6 && n5 == n7) return;
                object = ViewUtils.createAnimator();
                this.mIndicatorAnimator = object;
                ((ValueAnimatorCompat)object).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
                ((ValueAnimatorCompat)object).setDuration(n3);
                ((ValueAnimatorCompat)object).setFloatValues(0.0f, 1.0f);
                ((ValueAnimatorCompat)object).setUpdateListener(new ValueAnimatorCompat.AnimatorUpdateListener(){

                    @Override
                    public void onAnimationUpdate(ValueAnimatorCompat valueAnimatorCompat) {
                        float f2 = valueAnimatorCompat.getAnimatedFraction();
                        SlidingTabStrip.this.setIndicatorPosition(AnimationUtils.lerp(n4, n6, f2), AnimationUtils.lerp(n5, n7, f2));
                    }
                });
                ((ValueAnimatorCompat)object).setListener(new ValueAnimatorCompat.AnimatorListenerAdapter(){

                    @Override
                    public void onAnimationEnd(ValueAnimatorCompat valueAnimatorCompat) {
                        SlidingTabStrip.access$2502(SlidingTabStrip.this, n2);
                        SlidingTabStrip.access$2602(SlidingTabStrip.this, 0.0f);
                    }
                });
                ((ValueAnimatorCompat)object).start();
                return;
            }
        }

        boolean childrenNeedLayout() {
            int n2 = this.getChildCount();
            for (int i2 = 0; i2 < n2; ++i2) {
                if (this.getChildAt(i2).getWidth() > 0) continue;
                return true;
            }
            return false;
        }

        public void draw(Canvas canvas) {
            super.draw(canvas);
            if (this.mIndicatorLeft >= 0 && this.mIndicatorRight > this.mIndicatorLeft) {
                canvas.drawRect((float)this.mIndicatorLeft, (float)(this.getHeight() - this.mSelectedIndicatorHeight), (float)this.mIndicatorRight, (float)this.getHeight(), this.mSelectedIndicatorPaint);
            }
        }

        float getIndicatorPosition() {
            return (float)this.mSelectedPosition + this.mSelectionOffset;
        }

        protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
            super.onLayout(bl2, n2, n3, n4, n5);
            if (this.mIndicatorAnimator != null && this.mIndicatorAnimator.isRunning()) {
                this.mIndicatorAnimator.cancel();
                long l2 = this.mIndicatorAnimator.getDuration();
                this.animateIndicatorToPosition(this.mSelectedPosition, Math.round((1.0f - this.mIndicatorAnimator.getAnimatedFraction()) * (float)l2));
                return;
            }
            this.updateIndicatorPosition();
        }

        /*
         * Enabled aggressive block sorting
         */
        protected void onMeasure(int n2, int n3) {
            block12: {
                block11: {
                    int n4;
                    int n5;
                    View view;
                    int n6;
                    super.onMeasure(n2, n3);
                    if (View.MeasureSpec.getMode((int)n2) != 0x40000000 || TabLayout.this.mMode != 1 || TabLayout.this.mTabGravity != 1) break block11;
                    int n7 = this.getChildCount();
                    int n8 = 0;
                    for (n6 = 0; n6 < n7; ++n6) {
                        view = this.getChildAt(n6);
                        n5 = n8;
                        if (view.getVisibility() == 0) {
                            n5 = Math.max(n8, view.getMeasuredWidth());
                        }
                        n8 = n5;
                    }
                    if (n8 <= 0) break block11;
                    n5 = TabLayout.this.dpToPx(16);
                    n6 = 0;
                    if (n8 * n7 <= this.getMeasuredWidth() - n5 * 2) {
                        n5 = 0;
                        while (true) {
                            n4 = n6;
                            if (n5 < n7) {
                                view = (LinearLayout.LayoutParams)this.getChildAt(n5).getLayoutParams();
                                if (view.width != n8 || view.weight != 0.0f) {
                                    view.width = n8;
                                    view.weight = 0.0f;
                                    n6 = 1;
                                }
                                ++n5;
                                continue;
                            }
                            break;
                        }
                    } else {
                        TabLayout.access$2202(TabLayout.this, 0);
                        TabLayout.this.updateTabViews(false);
                        n4 = 1;
                    }
                    if (n4 != 0) break block12;
                }
                return;
            }
            super.onMeasure(n2, n3);
        }

        void setIndicatorPositionFromTabPosition(int n2, float f2) {
            if (this.mIndicatorAnimator != null && this.mIndicatorAnimator.isRunning()) {
                this.mIndicatorAnimator.cancel();
            }
            this.mSelectedPosition = n2;
            this.mSelectionOffset = f2;
            this.updateIndicatorPosition();
        }

        void setSelectedIndicatorColor(int n2) {
            if (this.mSelectedIndicatorPaint.getColor() != n2) {
                this.mSelectedIndicatorPaint.setColor(n2);
                ViewCompat.postInvalidateOnAnimation((View)this);
            }
        }

        void setSelectedIndicatorHeight(int n2) {
            if (this.mSelectedIndicatorHeight != n2) {
                this.mSelectedIndicatorHeight = n2;
                ViewCompat.postInvalidateOnAnimation((View)this);
            }
        }
    }

    public static final class Tab {
        public static final int INVALID_POSITION = -1;
        private CharSequence mContentDesc;
        private View mCustomView;
        private Drawable mIcon;
        private TabLayout mParent;
        private int mPosition = -1;
        private Object mTag;
        private CharSequence mText;
        private TabView mView;

        private Tab() {
        }

        static /* synthetic */ TabLayout access$002(Tab tab, TabLayout tabLayout) {
            tab.mParent = tabLayout;
            return tabLayout;
        }

        static /* synthetic */ TabView access$202(Tab tab, TabView tabView) {
            tab.mView = tabView;
            return tabView;
        }

        private void reset() {
            this.mParent = null;
            this.mView = null;
            this.mTag = null;
            this.mIcon = null;
            this.mText = null;
            this.mContentDesc = null;
            this.mPosition = -1;
            this.mCustomView = null;
        }

        private void updateView() {
            if (this.mView != null) {
                this.mView.update();
            }
        }

        @Nullable
        public CharSequence getContentDescription() {
            return this.mContentDesc;
        }

        @Nullable
        public View getCustomView() {
            return this.mCustomView;
        }

        @Nullable
        public Drawable getIcon() {
            return this.mIcon;
        }

        public int getPosition() {
            return this.mPosition;
        }

        @Nullable
        public Object getTag() {
            return this.mTag;
        }

        @Nullable
        public CharSequence getText() {
            return this.mText;
        }

        public boolean isSelected() {
            if (this.mParent == null) {
                throw new IllegalArgumentException("Tab not attached to a TabLayout");
            }
            return this.mParent.getSelectedTabPosition() == this.mPosition;
        }

        public void select() {
            if (this.mParent == null) {
                throw new IllegalArgumentException("Tab not attached to a TabLayout");
            }
            this.mParent.selectTab(this);
        }

        @NonNull
        public Tab setContentDescription(@StringRes int n2) {
            if (this.mParent == null) {
                throw new IllegalArgumentException("Tab not attached to a TabLayout");
            }
            return this.setContentDescription(this.mParent.getResources().getText(n2));
        }

        @NonNull
        public Tab setContentDescription(@Nullable CharSequence charSequence) {
            this.mContentDesc = charSequence;
            this.updateView();
            return this;
        }

        @NonNull
        public Tab setCustomView(@LayoutRes int n2) {
            return this.setCustomView(LayoutInflater.from((Context)this.mView.getContext()).inflate(n2, (ViewGroup)this.mView, false));
        }

        @NonNull
        public Tab setCustomView(@Nullable View view) {
            this.mCustomView = view;
            this.updateView();
            return this;
        }

        @NonNull
        public Tab setIcon(@DrawableRes int n2) {
            if (this.mParent == null) {
                throw new IllegalArgumentException("Tab not attached to a TabLayout");
            }
            return this.setIcon(AppCompatDrawableManager.get().getDrawable(this.mParent.getContext(), n2));
        }

        @NonNull
        public Tab setIcon(@Nullable Drawable drawable2) {
            this.mIcon = drawable2;
            this.updateView();
            return this;
        }

        void setPosition(int n2) {
            this.mPosition = n2;
        }

        @NonNull
        public Tab setTag(@Nullable Object object) {
            this.mTag = object;
            return this;
        }

        @NonNull
        public Tab setText(@StringRes int n2) {
            if (this.mParent == null) {
                throw new IllegalArgumentException("Tab not attached to a TabLayout");
            }
            return this.setText(this.mParent.getResources().getText(n2));
        }

        @NonNull
        public Tab setText(@Nullable CharSequence charSequence) {
            this.mText = charSequence;
            this.updateView();
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface TabGravity {
    }

    public static class TabLayoutOnPageChangeListener
    implements ViewPager.OnPageChangeListener {
        private int mPreviousScrollState;
        private int mScrollState;
        private final WeakReference<TabLayout> mTabLayoutRef;

        public TabLayoutOnPageChangeListener(TabLayout tabLayout) {
            this.mTabLayoutRef = new WeakReference<TabLayout>(tabLayout);
        }

        private void reset() {
            this.mScrollState = 0;
            this.mPreviousScrollState = 0;
        }

        @Override
        public void onPageScrollStateChanged(int n2) {
            this.mPreviousScrollState = this.mScrollState;
            this.mScrollState = n2;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onPageScrolled(int n2, float f2, int n3) {
            TabLayout tabLayout = (TabLayout)((Object)this.mTabLayoutRef.get());
            if (tabLayout != null) {
                boolean bl2 = this.mScrollState != 2 || this.mPreviousScrollState == 1;
                boolean bl3 = this.mScrollState != 2 || this.mPreviousScrollState != 0;
                tabLayout.setScrollPosition(n2, f2, bl2, bl3);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onPageSelected(int n2) {
            TabLayout tabLayout = (TabLayout)((Object)this.mTabLayoutRef.get());
            if (tabLayout != null && tabLayout.getSelectedTabPosition() != n2) {
                boolean bl2 = this.mScrollState == 0 || this.mScrollState == 2 && this.mPreviousScrollState == 0;
                tabLayout.selectTab(tabLayout.getTabAt(n2), bl2);
            }
        }
    }

    class TabView
    extends LinearLayout
    implements View.OnLongClickListener {
        private ImageView mCustomIconView;
        private TextView mCustomTextView;
        private View mCustomView;
        private int mDefaultMaxLines;
        private ImageView mIconView;
        private Tab mTab;
        private TextView mTextView;

        public TabView(Context context) {
            super(context);
            this.mDefaultMaxLines = 2;
            if (TabLayout.this.mTabBackgroundResId != 0) {
                this.setBackgroundDrawable(AppCompatDrawableManager.get().getDrawable(context, TabLayout.this.mTabBackgroundResId));
            }
            ViewCompat.setPaddingRelative((View)this, TabLayout.this.mTabPaddingStart, TabLayout.this.mTabPaddingTop, TabLayout.this.mTabPaddingEnd, TabLayout.this.mTabPaddingBottom);
            this.setGravity(17);
            this.setOrientation(1);
            this.setClickable(true);
        }

        private float approximateLineWidth(Layout layout2, int n2, float f2) {
            return layout2.getLineWidth(n2) * (f2 / layout2.getPaint().getTextSize());
        }

        private void reset() {
            this.setTab(null);
            this.setSelected(false);
        }

        private void setTab(@Nullable Tab tab) {
            if (tab != this.mTab) {
                this.mTab = tab;
                this.update();
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private void updateTextAndIcon(@Nullable TextView textView, @Nullable ImageView imageView) {
            Drawable drawable2 = this.mTab != null ? this.mTab.getIcon() : null;
            CharSequence charSequence = this.mTab != null ? this.mTab.getText() : null;
            CharSequence charSequence2 = this.mTab != null ? this.mTab.getContentDescription() : null;
            if (imageView != null) {
                if (drawable2 != null) {
                    imageView.setImageDrawable(drawable2);
                    imageView.setVisibility(0);
                    this.setVisibility(0);
                } else {
                    imageView.setVisibility(8);
                    imageView.setImageDrawable(null);
                }
                imageView.setContentDescription(charSequence2);
            }
            boolean bl2 = !TextUtils.isEmpty((CharSequence)charSequence);
            if (textView != null) {
                if (bl2) {
                    textView.setText(charSequence);
                    textView.setVisibility(0);
                    this.setVisibility(0);
                } else {
                    textView.setVisibility(8);
                    textView.setText(null);
                }
                textView.setContentDescription(charSequence2);
            }
            if (imageView != null) {
                int n2;
                textView = (ViewGroup.MarginLayoutParams)imageView.getLayoutParams();
                int n3 = n2 = 0;
                if (bl2) {
                    n3 = n2;
                    if (imageView.getVisibility() == 0) {
                        n3 = TabLayout.this.dpToPx(8);
                    }
                }
                if (n3 != textView.bottomMargin) {
                    textView.bottomMargin = n3;
                    imageView.requestLayout();
                }
            }
            if (!bl2 && !TextUtils.isEmpty((CharSequence)charSequence2)) {
                this.setOnLongClickListener(this);
                return;
            }
            this.setOnLongClickListener(null);
            this.setLongClickable(false);
        }

        public Tab getTab() {
            return this.mTab;
        }

        @TargetApi(value=14)
        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setClassName((CharSequence)ActionBar.Tab.class.getName());
        }

        @TargetApi(value=14)
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName((CharSequence)ActionBar.Tab.class.getName());
        }

        public boolean onLongClick(View object) {
            object = new int[2];
            this.getLocationOnScreen((int[])object);
            Context context = this.getContext();
            int n2 = this.getWidth();
            int n3 = this.getHeight();
            int n4 = context.getResources().getDisplayMetrics().widthPixels;
            context = Toast.makeText((Context)context, (CharSequence)this.mTab.getContentDescription(), (int)0);
            context.setGravity(49, (int)(object[0] + n2 / 2 - n4 / 2), n3);
            context.show();
            return true;
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onMeasure(int n2, int n3) {
            float f2;
            int n4 = View.MeasureSpec.getSize((int)n2);
            int n5 = View.MeasureSpec.getMode((int)n2);
            int n6 = TabLayout.this.getTabMaxWidth();
            if (n6 > 0 && (n5 == 0 || n4 > n6)) {
                n2 = View.MeasureSpec.makeMeasureSpec((int)TabLayout.this.mTabMaxWidth, (int)Integer.MIN_VALUE);
            }
            super.onMeasure(n2, n3);
            if (this.mTextView == null) return;
            this.getResources();
            float f3 = TabLayout.this.mTabTextSize;
            n5 = this.mDefaultMaxLines;
            if (this.mIconView != null && this.mIconView.getVisibility() == 0) {
                n4 = 1;
                f2 = f3;
            } else {
                n4 = n5;
                f2 = f3;
                if (this.mTextView != null) {
                    n4 = n5;
                    f2 = f3;
                    if (this.mTextView.getLineCount() > 1) {
                        f2 = TabLayout.this.mTabTextMultiLineSize;
                        n4 = n5;
                    }
                }
            }
            f3 = this.mTextView.getTextSize();
            int n7 = this.mTextView.getLineCount();
            n5 = TextViewCompat.getMaxLines(this.mTextView);
            if (f2 == f3) {
                if (n5 < 0) return;
                if (n4 == n5) return;
            }
            n5 = n6 = 1;
            if (TabLayout.this.mMode == 1) {
                n5 = n6;
                if (f2 > f3) {
                    n5 = n6;
                    if (n7 == 1) {
                        Layout layout2 = this.mTextView.getLayout();
                        if (layout2 == null) return;
                        n5 = n6;
                        if (this.approximateLineWidth(layout2, 0, f2) > (float)layout2.getWidth()) {
                            return;
                        }
                    }
                }
            }
            if (n5 == 0) return;
            this.mTextView.setTextSize(0, f2);
            this.mTextView.setMaxLines(n4);
            super.onMeasure(n2, n3);
        }

        public boolean performClick() {
            boolean bl2 = super.performClick();
            if (this.mTab != null) {
                this.mTab.select();
                bl2 = true;
            }
            return bl2;
        }

        /*
         * Enabled aggressive block sorting
         */
        public void setSelected(boolean bl2) {
            boolean bl3 = this.isSelected() != bl2;
            super.setSelected(bl2);
            if (bl3 && bl2) {
                this.sendAccessibilityEvent(4);
                if (this.mTextView != null) {
                    this.mTextView.setSelected(bl2);
                }
                if (this.mIconView != null) {
                    this.mIconView.setSelected(bl2);
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        final void update() {
            Tab tab = this.mTab;
            tab = tab != null ? tab.getCustomView() : null;
            if (tab != null) {
                ViewParent viewParent = tab.getParent();
                if (viewParent != this) {
                    if (viewParent != null) {
                        ((ViewGroup)viewParent).removeView((View)tab);
                    }
                    this.addView((View)tab);
                }
                this.mCustomView = tab;
                if (this.mTextView != null) {
                    this.mTextView.setVisibility(8);
                }
                if (this.mIconView != null) {
                    this.mIconView.setVisibility(8);
                    this.mIconView.setImageDrawable(null);
                }
                this.mCustomTextView = (TextView)tab.findViewById(16908308);
                if (this.mCustomTextView != null) {
                    this.mDefaultMaxLines = TextViewCompat.getMaxLines(this.mCustomTextView);
                }
                this.mCustomIconView = (ImageView)tab.findViewById(16908294);
            } else {
                if (this.mCustomView != null) {
                    this.removeView(this.mCustomView);
                    this.mCustomView = null;
                }
                this.mCustomTextView = null;
                this.mCustomIconView = null;
            }
            if (this.mCustomView == null) {
                if (this.mIconView == null) {
                    tab = (ImageView)LayoutInflater.from((Context)this.getContext()).inflate(R.layout.design_layout_tab_icon, (ViewGroup)this, false);
                    this.addView((View)tab, 0);
                    this.mIconView = tab;
                }
                if (this.mTextView == null) {
                    tab = (TextView)LayoutInflater.from((Context)this.getContext()).inflate(R.layout.design_layout_tab_text, (ViewGroup)this, false);
                    this.addView((View)tab);
                    this.mTextView = tab;
                    this.mDefaultMaxLines = TextViewCompat.getMaxLines(this.mTextView);
                }
                this.mTextView.setTextAppearance(this.getContext(), TabLayout.this.mTabTextAppearance);
                if (TabLayout.this.mTabTextColors != null) {
                    this.mTextView.setTextColor(TabLayout.this.mTabTextColors);
                }
                this.updateTextAndIcon(this.mTextView, this.mIconView);
                return;
            } else {
                if (this.mCustomTextView == null && this.mCustomIconView == null) return;
                this.updateTextAndIcon(this.mCustomTextView, this.mCustomIconView);
                return;
            }
        }
    }

    public static class ViewPagerOnTabSelectedListener
    implements OnTabSelectedListener {
        private final ViewPager mViewPager;

        public ViewPagerOnTabSelectedListener(ViewPager viewPager) {
            this.mViewPager = viewPager;
        }

        @Override
        public void onTabReselected(Tab tab) {
        }

        @Override
        public void onTabSelected(Tab tab) {
            this.mViewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(Tab tab) {
        }
    }
}

