/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.View$OnLongClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.widget.AbsListView$LayoutParams
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.BaseAdapter
 *  android.widget.HorizontalScrollView
 *  android.widget.ImageView
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 *  android.widget.TextView
 *  android.widget.Toast
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.ActionBar;
import android.support.v7.appcompat.R;
import android.support.v7.view.ActionBarPolicy;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.TintTypedArray;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ScrollingTabContainerView
extends HorizontalScrollView
implements AdapterView.OnItemSelectedListener {
    private static final int FADE_DURATION = 200;
    private static final String TAG = "ScrollingTabContainerView";
    private static final Interpolator sAlphaInterpolator = new DecelerateInterpolator();
    private boolean mAllowCollapse;
    private int mContentHeight;
    int mMaxTabWidth;
    private int mSelectedTabIndex;
    int mStackedTabMaxWidth;
    private TabClickListener mTabClickListener;
    private LinearLayoutCompat mTabLayout;
    Runnable mTabSelector;
    private Spinner mTabSpinner;
    protected final VisibilityAnimListener mVisAnimListener = new VisibilityAnimListener();
    protected ViewPropertyAnimatorCompat mVisibilityAnim;

    public ScrollingTabContainerView(Context object) {
        super((Context)object);
        this.setHorizontalScrollBarEnabled(false);
        object = ActionBarPolicy.get((Context)object);
        this.setContentHeight(((ActionBarPolicy)object).getTabContainerHeight());
        this.mStackedTabMaxWidth = ((ActionBarPolicy)object).getStackedTabMaxWidth();
        this.mTabLayout = this.createTabLayout();
        this.addView((View)this.mTabLayout, new ViewGroup.LayoutParams(-2, -1));
    }

    private Spinner createSpinner() {
        AppCompatSpinner appCompatSpinner = new AppCompatSpinner(this.getContext(), null, R.attr.actionDropDownStyle);
        appCompatSpinner.setLayoutParams((ViewGroup.LayoutParams)new LinearLayoutCompat.LayoutParams(-2, -1));
        appCompatSpinner.setOnItemSelectedListener(this);
        return appCompatSpinner;
    }

    private LinearLayoutCompat createTabLayout() {
        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(this.getContext(), null, R.attr.actionBarTabBarStyle);
        linearLayoutCompat.setMeasureWithLargestChildEnabled(true);
        linearLayoutCompat.setGravity(17);
        linearLayoutCompat.setLayoutParams((ViewGroup.LayoutParams)new LinearLayoutCompat.LayoutParams(-2, -1));
        return linearLayoutCompat;
    }

    private TabView createTabView(ActionBar.Tab object, boolean bl2) {
        object = new TabView(this.getContext(), (ActionBar.Tab)object, bl2);
        if (bl2) {
            object.setBackgroundDrawable(null);
            object.setLayoutParams((ViewGroup.LayoutParams)new AbsListView.LayoutParams(-1, this.mContentHeight));
            return object;
        }
        object.setFocusable(true);
        if (this.mTabClickListener == null) {
            this.mTabClickListener = new TabClickListener();
        }
        object.setOnClickListener(this.mTabClickListener);
        return object;
    }

    private boolean isCollapsed() {
        return this.mTabSpinner != null && this.mTabSpinner.getParent() == this;
    }

    private void performCollapse() {
        if (this.isCollapsed()) {
            return;
        }
        if (this.mTabSpinner == null) {
            this.mTabSpinner = this.createSpinner();
        }
        this.removeView((View)this.mTabLayout);
        this.addView((View)this.mTabSpinner, new ViewGroup.LayoutParams(-2, -1));
        if (this.mTabSpinner.getAdapter() == null) {
            this.mTabSpinner.setAdapter((SpinnerAdapter)new TabAdapter());
        }
        if (this.mTabSelector != null) {
            this.removeCallbacks(this.mTabSelector);
            this.mTabSelector = null;
        }
        this.mTabSpinner.setSelection(this.mSelectedTabIndex);
    }

    private boolean performExpand() {
        if (!this.isCollapsed()) {
            return false;
        }
        this.removeView((View)this.mTabSpinner);
        this.addView((View)this.mTabLayout, new ViewGroup.LayoutParams(-2, -1));
        this.setTabSelected(this.mTabSpinner.getSelectedItemPosition());
        return false;
    }

    public void addTab(ActionBar.Tab object, int n2, boolean bl2) {
        object = this.createTabView((ActionBar.Tab)object, false);
        this.mTabLayout.addView((View)object, n2, (ViewGroup.LayoutParams)new LinearLayoutCompat.LayoutParams(0, -1, 1.0f));
        if (this.mTabSpinner != null) {
            ((TabAdapter)this.mTabSpinner.getAdapter()).notifyDataSetChanged();
        }
        if (bl2) {
            ((TabView)((Object)object)).setSelected(true);
        }
        if (this.mAllowCollapse) {
            this.requestLayout();
        }
    }

    public void addTab(ActionBar.Tab object, boolean bl2) {
        object = this.createTabView((ActionBar.Tab)object, false);
        this.mTabLayout.addView((View)object, (ViewGroup.LayoutParams)new LinearLayoutCompat.LayoutParams(0, -1, 1.0f));
        if (this.mTabSpinner != null) {
            ((TabAdapter)this.mTabSpinner.getAdapter()).notifyDataSetChanged();
        }
        if (bl2) {
            ((TabView)((Object)object)).setSelected(true);
        }
        if (this.mAllowCollapse) {
            this.requestLayout();
        }
    }

    public void animateToTab(int n2) {
        final View view = this.mTabLayout.getChildAt(n2);
        if (this.mTabSelector != null) {
            this.removeCallbacks(this.mTabSelector);
        }
        this.mTabSelector = new Runnable(){

            @Override
            public void run() {
                int n2 = view.getLeft();
                int n3 = (ScrollingTabContainerView.this.getWidth() - view.getWidth()) / 2;
                ScrollingTabContainerView.this.smoothScrollTo(n2 - n3, 0);
                ScrollingTabContainerView.this.mTabSelector = null;
            }
        };
        this.post(this.mTabSelector);
    }

    public void animateToVisibility(int n2) {
        if (this.mVisibilityAnim != null) {
            this.mVisibilityAnim.cancel();
        }
        if (n2 == 0) {
            if (this.getVisibility() != 0) {
                ViewCompat.setAlpha((View)this, 0.0f);
            }
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = ViewCompat.animate((View)this).alpha(1.0f);
            viewPropertyAnimatorCompat.setDuration(200L);
            viewPropertyAnimatorCompat.setInterpolator(sAlphaInterpolator);
            viewPropertyAnimatorCompat.setListener(this.mVisAnimListener.withFinalVisibility(viewPropertyAnimatorCompat, n2));
            viewPropertyAnimatorCompat.start();
            return;
        }
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = ViewCompat.animate((View)this).alpha(0.0f);
        viewPropertyAnimatorCompat.setDuration(200L);
        viewPropertyAnimatorCompat.setInterpolator(sAlphaInterpolator);
        viewPropertyAnimatorCompat.setListener(this.mVisAnimListener.withFinalVisibility(viewPropertyAnimatorCompat, n2));
        viewPropertyAnimatorCompat.start();
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mTabSelector != null) {
            this.post(this.mTabSelector);
        }
    }

    protected void onConfigurationChanged(Configuration object) {
        if (Build.VERSION.SDK_INT >= 8) {
            super.onConfigurationChanged((Configuration)object);
        }
        object = ActionBarPolicy.get(this.getContext());
        this.setContentHeight(((ActionBarPolicy)object).getTabContainerHeight());
        this.mStackedTabMaxWidth = ((ActionBarPolicy)object).getStackedTabMaxWidth();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mTabSelector != null) {
            this.removeCallbacks(this.mTabSelector);
        }
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int n2, long l2) {
        ((TabView)view).getTab().select();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onMeasure(int n2, int n3) {
        n3 = View.MeasureSpec.getMode((int)n2);
        boolean bl2 = n3 == 0x40000000;
        this.setFillViewport(bl2);
        int n4 = this.mTabLayout.getChildCount();
        if (n4 > 1 && (n3 == 0x40000000 || n3 == Integer.MIN_VALUE)) {
            this.mMaxTabWidth = n4 > 2 ? (int)((float)View.MeasureSpec.getSize((int)n2) * 0.4f) : View.MeasureSpec.getSize((int)n2) / 2;
            this.mMaxTabWidth = Math.min(this.mMaxTabWidth, this.mStackedTabMaxWidth);
        } else {
            this.mMaxTabWidth = -1;
        }
        n4 = View.MeasureSpec.makeMeasureSpec((int)this.mContentHeight, (int)0x40000000);
        n3 = !bl2 && this.mAllowCollapse ? 1 : 0;
        if (n3 != 0) {
            this.mTabLayout.measure(0, n4);
            if (this.mTabLayout.getMeasuredWidth() > View.MeasureSpec.getSize((int)n2)) {
                this.performCollapse();
            } else {
                this.performExpand();
            }
        } else {
            this.performExpand();
        }
        n3 = this.getMeasuredWidth();
        super.onMeasure(n2, n4);
        n2 = this.getMeasuredWidth();
        if (bl2 && n3 != n2) {
            this.setTabSelected(this.mSelectedTabIndex);
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void removeAllTabs() {
        this.mTabLayout.removeAllViews();
        if (this.mTabSpinner != null) {
            ((TabAdapter)this.mTabSpinner.getAdapter()).notifyDataSetChanged();
        }
        if (this.mAllowCollapse) {
            this.requestLayout();
        }
    }

    public void removeTabAt(int n2) {
        this.mTabLayout.removeViewAt(n2);
        if (this.mTabSpinner != null) {
            ((TabAdapter)this.mTabSpinner.getAdapter()).notifyDataSetChanged();
        }
        if (this.mAllowCollapse) {
            this.requestLayout();
        }
    }

    public void setAllowCollapse(boolean bl2) {
        this.mAllowCollapse = bl2;
    }

    public void setContentHeight(int n2) {
        this.mContentHeight = n2;
        this.requestLayout();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setTabSelected(int n2) {
        this.mSelectedTabIndex = n2;
        int n3 = this.mTabLayout.getChildCount();
        for (int i2 = 0; i2 < n3; ++i2) {
            View view = this.mTabLayout.getChildAt(i2);
            boolean bl2 = i2 == n2;
            view.setSelected(bl2);
            if (!bl2) continue;
            this.animateToTab(n2);
        }
        if (this.mTabSpinner != null && n2 >= 0) {
            this.mTabSpinner.setSelection(n2);
        }
    }

    public void updateTab(int n2) {
        ((TabView)this.mTabLayout.getChildAt(n2)).update();
        if (this.mTabSpinner != null) {
            ((TabAdapter)this.mTabSpinner.getAdapter()).notifyDataSetChanged();
        }
        if (this.mAllowCollapse) {
            this.requestLayout();
        }
    }

    private class TabAdapter
    extends BaseAdapter {
        private TabAdapter() {
        }

        public int getCount() {
            return ScrollingTabContainerView.this.mTabLayout.getChildCount();
        }

        public Object getItem(int n2) {
            return ((TabView)ScrollingTabContainerView.this.mTabLayout.getChildAt(n2)).getTab();
        }

        public long getItemId(int n2) {
            return n2;
        }

        public View getView(int n2, View view, ViewGroup viewGroup) {
            if (view == null) {
                return ScrollingTabContainerView.this.createTabView((ActionBar.Tab)this.getItem(n2), true);
            }
            ((TabView)view).bindTab((ActionBar.Tab)this.getItem(n2));
            return view;
        }
    }

    private class TabClickListener
    implements View.OnClickListener {
        private TabClickListener() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onClick(View view) {
            ((TabView)view).getTab().select();
            int n2 = ScrollingTabContainerView.this.mTabLayout.getChildCount();
            int n3 = 0;
            while (n3 < n2) {
                View view2 = ScrollingTabContainerView.this.mTabLayout.getChildAt(n3);
                boolean bl2 = view2 == view;
                view2.setSelected(bl2);
                ++n3;
            }
            return;
        }
    }

    private class TabView
    extends LinearLayoutCompat
    implements View.OnLongClickListener {
        private final int[] BG_ATTRS;
        private View mCustomView;
        private ImageView mIconView;
        private ActionBar.Tab mTab;
        private TextView mTextView;

        public TabView(Context context, ActionBar.Tab tab, boolean bl2) {
            super(context, null, R.attr.actionBarTabStyle);
            this.BG_ATTRS = new int[]{16842964};
            this.mTab = tab;
            ScrollingTabContainerView.this = TintTypedArray.obtainStyledAttributes(context, null, this.BG_ATTRS, R.attr.actionBarTabStyle, 0);
            if (((TintTypedArray)ScrollingTabContainerView.this).hasValue(0)) {
                this.setBackgroundDrawable(((TintTypedArray)ScrollingTabContainerView.this).getDrawable(0));
            }
            ((TintTypedArray)ScrollingTabContainerView.this).recycle();
            if (bl2) {
                this.setGravity(8388627);
            }
            this.update();
        }

        public void bindTab(ActionBar.Tab tab) {
            this.mTab = tab;
            this.update();
        }

        public ActionBar.Tab getTab() {
            return this.mTab;
        }

        @Override
        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setClassName((CharSequence)ActionBar.Tab.class.getName());
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            if (Build.VERSION.SDK_INT >= 14) {
                accessibilityNodeInfo.setClassName((CharSequence)ActionBar.Tab.class.getName());
            }
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

        @Override
        public void onMeasure(int n2, int n3) {
            super.onMeasure(n2, n3);
            if (ScrollingTabContainerView.this.mMaxTabWidth > 0 && this.getMeasuredWidth() > ScrollingTabContainerView.this.mMaxTabWidth) {
                super.onMeasure(View.MeasureSpec.makeMeasureSpec((int)ScrollingTabContainerView.this.mMaxTabWidth, (int)0x40000000), n3);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        public void setSelected(boolean bl2) {
            boolean bl3 = this.isSelected() != bl2;
            super.setSelected(bl2);
            if (bl3 && bl2) {
                this.sendAccessibilityEvent(4);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        public void update() {
            ActionBar.Tab tab = this.mTab;
            View view = tab.getCustomView();
            if (view != null) {
                tab = view.getParent();
                if (tab != this) {
                    if (tab != null) {
                        ((ViewGroup)tab).removeView(view);
                    }
                    this.addView(view);
                }
                this.mCustomView = view;
                if (this.mTextView != null) {
                    this.mTextView.setVisibility(8);
                }
                if (this.mIconView != null) {
                    this.mIconView.setVisibility(8);
                    this.mIconView.setImageDrawable(null);
                }
                return;
            }
            if (this.mCustomView != null) {
                this.removeView(this.mCustomView);
                this.mCustomView = null;
            }
            Drawable drawable2 = tab.getIcon();
            CharSequence charSequence = tab.getText();
            if (drawable2 != null) {
                if (this.mIconView == null) {
                    ImageView imageView = new ImageView(this.getContext());
                    LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(-2, -2);
                    layoutParams.gravity = 16;
                    imageView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
                    this.addView((View)imageView, 0);
                    this.mIconView = imageView;
                }
                this.mIconView.setImageDrawable(drawable2);
                this.mIconView.setVisibility(0);
            } else if (this.mIconView != null) {
                this.mIconView.setVisibility(8);
                this.mIconView.setImageDrawable(null);
            }
            boolean bl2 = !TextUtils.isEmpty((CharSequence)charSequence);
            if (bl2) {
                if (this.mTextView == null) {
                    AppCompatTextView appCompatTextView = new AppCompatTextView(this.getContext(), null, R.attr.actionBarTabTextStyle);
                    appCompatTextView.setEllipsize(TextUtils.TruncateAt.END);
                    LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(-2, -2);
                    layoutParams.gravity = 16;
                    appCompatTextView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
                    this.addView((View)appCompatTextView);
                    this.mTextView = appCompatTextView;
                }
                this.mTextView.setText(charSequence);
                this.mTextView.setVisibility(0);
            } else if (this.mTextView != null) {
                this.mTextView.setVisibility(8);
                this.mTextView.setText(null);
            }
            if (this.mIconView != null) {
                this.mIconView.setContentDescription(tab.getContentDescription());
            }
            if (!bl2 && !TextUtils.isEmpty((CharSequence)tab.getContentDescription())) {
                this.setOnLongClickListener(this);
                return;
            }
            this.setOnLongClickListener(null);
            this.setLongClickable(false);
        }
    }

    protected class VisibilityAnimListener
    implements ViewPropertyAnimatorListener {
        private boolean mCanceled = false;
        private int mFinalVisibility;

        protected VisibilityAnimListener() {
        }

        @Override
        public void onAnimationCancel(View view) {
            this.mCanceled = true;
        }

        @Override
        public void onAnimationEnd(View view) {
            if (this.mCanceled) {
                return;
            }
            ScrollingTabContainerView.this.mVisibilityAnim = null;
            ScrollingTabContainerView.this.setVisibility(this.mFinalVisibility);
        }

        @Override
        public void onAnimationStart(View view) {
            ScrollingTabContainerView.this.setVisibility(0);
            this.mCanceled = false;
        }

        public VisibilityAnimListener withFinalVisibility(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, int n2) {
            this.mFinalVisibility = n2;
            ScrollingTabContainerView.this.mVisibilityAnim = viewPropertyAnimatorCompat;
            return this;
        }
    }
}

