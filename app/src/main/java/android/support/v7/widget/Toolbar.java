/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.Layout
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.util.AttributeSet
 *  android.view.ContextThemeWrapper
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.widget.ImageButton
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.appcompat.R;
import android.support.v7.view.CollapsibleActionView;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.ActionMenuPresenter;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.DecorToolbar;
import android.support.v7.widget.RtlSpacingHelper;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.support.v7.widget.ViewUtils;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class Toolbar
extends ViewGroup {
    private static final String TAG = "Toolbar";
    private MenuPresenter.Callback mActionMenuPresenterCallback;
    private int mButtonGravity;
    private ImageButton mCollapseButtonView;
    private CharSequence mCollapseDescription;
    private Drawable mCollapseIcon;
    private boolean mCollapsible;
    private final RtlSpacingHelper mContentInsets = new RtlSpacingHelper();
    private final AppCompatDrawableManager mDrawableManager;
    private boolean mEatingHover;
    private boolean mEatingTouch;
    View mExpandedActionView;
    private ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
    private int mGravity = 8388627;
    private final ArrayList<View> mHiddenViews;
    private ImageView mLogoView;
    private int mMaxButtonHeight;
    private MenuBuilder.Callback mMenuBuilderCallback;
    private ActionMenuView mMenuView;
    private final ActionMenuView.OnMenuItemClickListener mMenuViewItemClickListener;
    private ImageButton mNavButtonView;
    private OnMenuItemClickListener mOnMenuItemClickListener;
    private ActionMenuPresenter mOuterActionMenuPresenter;
    private Context mPopupContext;
    private int mPopupTheme;
    private final Runnable mShowOverflowMenuRunnable;
    private CharSequence mSubtitleText;
    private int mSubtitleTextAppearance;
    private int mSubtitleTextColor;
    private TextView mSubtitleTextView;
    private final int[] mTempMargins;
    private final ArrayList<View> mTempViews = new ArrayList();
    private int mTitleMarginBottom;
    private int mTitleMarginEnd;
    private int mTitleMarginStart;
    private int mTitleMarginTop;
    private CharSequence mTitleText;
    private int mTitleTextAppearance;
    private int mTitleTextColor;
    private TextView mTitleTextView;
    private ToolbarWidgetWrapper mWrapper;

    public Toolbar(Context context) {
        this(context, null);
    }

    public Toolbar(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.toolbarStyle);
    }

    public Toolbar(Context object, @Nullable AttributeSet object2, int n2) {
        super((Context)object, object2, n2);
        this.mHiddenViews = new ArrayList();
        this.mTempMargins = new int[2];
        this.mMenuViewItemClickListener = new ActionMenuView.OnMenuItemClickListener(){

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (Toolbar.this.mOnMenuItemClickListener != null) {
                    return Toolbar.this.mOnMenuItemClickListener.onMenuItemClick(menuItem);
                }
                return false;
            }
        };
        this.mShowOverflowMenuRunnable = new Runnable(){

            @Override
            public void run() {
                Toolbar.this.showOverflowMenu();
            }
        };
        object = TintTypedArray.obtainStyledAttributes(this.getContext(), object2, R.styleable.Toolbar, n2, 0);
        this.mTitleTextAppearance = ((TintTypedArray)object).getResourceId(R.styleable.Toolbar_titleTextAppearance, 0);
        this.mSubtitleTextAppearance = ((TintTypedArray)object).getResourceId(R.styleable.Toolbar_subtitleTextAppearance, 0);
        this.mGravity = ((TintTypedArray)object).getInteger(R.styleable.Toolbar_android_gravity, this.mGravity);
        this.mButtonGravity = 48;
        this.mTitleMarginBottom = n2 = ((TintTypedArray)object).getDimensionPixelOffset(R.styleable.Toolbar_titleMargins, 0);
        this.mTitleMarginTop = n2;
        this.mTitleMarginEnd = n2;
        this.mTitleMarginStart = n2;
        n2 = ((TintTypedArray)object).getDimensionPixelOffset(R.styleable.Toolbar_titleMarginStart, -1);
        if (n2 >= 0) {
            this.mTitleMarginStart = n2;
        }
        if ((n2 = ((TintTypedArray)object).getDimensionPixelOffset(R.styleable.Toolbar_titleMarginEnd, -1)) >= 0) {
            this.mTitleMarginEnd = n2;
        }
        if ((n2 = ((TintTypedArray)object).getDimensionPixelOffset(R.styleable.Toolbar_titleMarginTop, -1)) >= 0) {
            this.mTitleMarginTop = n2;
        }
        if ((n2 = ((TintTypedArray)object).getDimensionPixelOffset(R.styleable.Toolbar_titleMarginBottom, -1)) >= 0) {
            this.mTitleMarginBottom = n2;
        }
        this.mMaxButtonHeight = ((TintTypedArray)object).getDimensionPixelSize(R.styleable.Toolbar_maxButtonHeight, -1);
        n2 = ((TintTypedArray)object).getDimensionPixelOffset(R.styleable.Toolbar_contentInsetStart, Integer.MIN_VALUE);
        int n3 = ((TintTypedArray)object).getDimensionPixelOffset(R.styleable.Toolbar_contentInsetEnd, Integer.MIN_VALUE);
        int n4 = ((TintTypedArray)object).getDimensionPixelSize(R.styleable.Toolbar_contentInsetLeft, 0);
        int n5 = ((TintTypedArray)object).getDimensionPixelSize(R.styleable.Toolbar_contentInsetRight, 0);
        this.mContentInsets.setAbsolute(n4, n5);
        if (n2 != Integer.MIN_VALUE || n3 != Integer.MIN_VALUE) {
            this.mContentInsets.setRelative(n2, n3);
        }
        this.mCollapseIcon = ((TintTypedArray)object).getDrawable(R.styleable.Toolbar_collapseIcon);
        this.mCollapseDescription = ((TintTypedArray)object).getText(R.styleable.Toolbar_collapseContentDescription);
        object2 = ((TintTypedArray)object).getText(R.styleable.Toolbar_title);
        if (!TextUtils.isEmpty((CharSequence)object2)) {
            this.setTitle((CharSequence)object2);
        }
        if (!TextUtils.isEmpty((CharSequence)(object2 = ((TintTypedArray)object).getText(R.styleable.Toolbar_subtitle)))) {
            this.setSubtitle((CharSequence)object2);
        }
        this.mPopupContext = this.getContext();
        this.setPopupTheme(((TintTypedArray)object).getResourceId(R.styleable.Toolbar_popupTheme, 0));
        object2 = ((TintTypedArray)object).getDrawable(R.styleable.Toolbar_navigationIcon);
        if (object2 != null) {
            this.setNavigationIcon((Drawable)object2);
        }
        if (!TextUtils.isEmpty((CharSequence)(object2 = ((TintTypedArray)object).getText(R.styleable.Toolbar_navigationContentDescription)))) {
            this.setNavigationContentDescription((CharSequence)object2);
        }
        if ((object2 = ((TintTypedArray)object).getDrawable(R.styleable.Toolbar_logo)) != null) {
            this.setLogo((Drawable)object2);
        }
        if (!TextUtils.isEmpty((CharSequence)(object2 = ((TintTypedArray)object).getText(R.styleable.Toolbar_logoDescription)))) {
            this.setLogoDescription((CharSequence)object2);
        }
        if (((TintTypedArray)object).hasValue(R.styleable.Toolbar_titleTextColor)) {
            this.setTitleTextColor(((TintTypedArray)object).getColor(R.styleable.Toolbar_titleTextColor, -1));
        }
        if (((TintTypedArray)object).hasValue(R.styleable.Toolbar_subtitleTextColor)) {
            this.setSubtitleTextColor(((TintTypedArray)object).getColor(R.styleable.Toolbar_subtitleTextColor, -1));
        }
        ((TintTypedArray)object).recycle();
        this.mDrawableManager = AppCompatDrawableManager.get();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void addCustomViewsWithGravity(List<View> list, int n2) {
        boolean bl2 = true;
        if (ViewCompat.getLayoutDirection((View)this) != 1) {
            bl2 = false;
        }
        int n3 = this.getChildCount();
        int n4 = GravityCompat.getAbsoluteGravity(n2, ViewCompat.getLayoutDirection((View)this));
        list.clear();
        if (bl2) {
            for (n2 = n3 - 1; n2 >= 0; --n2) {
                View view = this.getChildAt(n2);
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (layoutParams.mViewType != 0 || !this.shouldLayout(view) || this.getChildHorizontalGravity(layoutParams.gravity) != n4) continue;
                list.add(view);
            }
            return;
        } else {
            for (n2 = 0; n2 < n3; ++n2) {
                View view = this.getChildAt(n2);
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (layoutParams.mViewType != 0 || !this.shouldLayout(view) || this.getChildHorizontalGravity(layoutParams.gravity) != n4) continue;
                list.add(view);
            }
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private void addSystemView(View view, boolean bl2) {
        void var3_5;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            LayoutParams layoutParams2 = this.generateDefaultLayoutParams();
        } else if (!this.checkLayoutParams(layoutParams)) {
            LayoutParams layoutParams3 = this.generateLayoutParams(layoutParams);
        } else {
            LayoutParams layoutParams4 = (LayoutParams)layoutParams;
        }
        var3_5.mViewType = 1;
        if (bl2 && this.mExpandedActionView != null) {
            view.setLayoutParams((ViewGroup.LayoutParams)var3_5);
            this.mHiddenViews.add(view);
            return;
        }
        this.addView(view, (ViewGroup.LayoutParams)var3_5);
    }

    private void ensureCollapseButtonView() {
        if (this.mCollapseButtonView == null) {
            this.mCollapseButtonView = new ImageButton(this.getContext(), null, R.attr.toolbarNavigationButtonStyle);
            this.mCollapseButtonView.setImageDrawable(this.mCollapseIcon);
            this.mCollapseButtonView.setContentDescription(this.mCollapseDescription);
            LayoutParams layoutParams = this.generateDefaultLayoutParams();
            layoutParams.gravity = 0x800003 | this.mButtonGravity & 0x70;
            layoutParams.mViewType = 2;
            this.mCollapseButtonView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
            this.mCollapseButtonView.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    Toolbar.this.collapseActionView();
                }
            });
        }
    }

    private void ensureLogoView() {
        if (this.mLogoView == null) {
            this.mLogoView = new ImageView(this.getContext());
        }
    }

    private void ensureMenu() {
        this.ensureMenuView();
        if (this.mMenuView.peekMenu() == null) {
            MenuBuilder menuBuilder = (MenuBuilder)this.mMenuView.getMenu();
            if (this.mExpandedMenuPresenter == null) {
                this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
            }
            this.mMenuView.setExpandedActionViewsExclusive(true);
            menuBuilder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
        }
    }

    private void ensureMenuView() {
        if (this.mMenuView == null) {
            this.mMenuView = new ActionMenuView(this.getContext());
            this.mMenuView.setPopupTheme(this.mPopupTheme);
            this.mMenuView.setOnMenuItemClickListener(this.mMenuViewItemClickListener);
            this.mMenuView.setMenuCallbacks(this.mActionMenuPresenterCallback, this.mMenuBuilderCallback);
            LayoutParams layoutParams = this.generateDefaultLayoutParams();
            layoutParams.gravity = 0x800005 | this.mButtonGravity & 0x70;
            this.mMenuView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
            this.addSystemView((View)this.mMenuView, false);
        }
    }

    private void ensureNavButtonView() {
        if (this.mNavButtonView == null) {
            this.mNavButtonView = new ImageButton(this.getContext(), null, R.attr.toolbarNavigationButtonStyle);
            LayoutParams layoutParams = this.generateDefaultLayoutParams();
            layoutParams.gravity = 0x800003 | this.mButtonGravity & 0x70;
            this.mNavButtonView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getChildHorizontalGravity(int n2) {
        int n3;
        int n4 = ViewCompat.getLayoutDirection((View)this);
        n2 = n3 = GravityCompat.getAbsoluteGravity(n2, n4) & 7;
        switch (n3) {
            default: {
                if (n4 != 1) return 3;
                n2 = 5;
            }
            case 1: 
            case 3: 
            case 5: {
                return n2;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private int getChildTop(View view, int n2) {
        int n3;
        int n4;
        int n5;
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n6 = view.getMeasuredHeight();
        n2 = n2 > 0 ? (n6 - n2) / 2 : 0;
        switch (this.getChildVerticalGravity(layoutParams.gravity)) {
            default: {
                n5 = this.getPaddingTop();
                n2 = this.getPaddingBottom();
                n4 = this.getHeight();
                n3 = (n4 - n5 - n2 - n6) / 2;
                if (n3 >= layoutParams.topMargin) break;
                n2 = layoutParams.topMargin;
                return n5 + n2;
            }
            case 48: {
                return this.getPaddingTop() - n2;
            }
            case 80: {
                return this.getHeight() - this.getPaddingBottom() - n6 - layoutParams.bottomMargin - n2;
            }
        }
        n6 = n4 - n2 - n6 - n3 - n5;
        n2 = n3;
        if (n6 >= layoutParams.bottomMargin) return n5 + n2;
        n2 = Math.max(0, n3 - (layoutParams.bottomMargin - n6));
        return n5 + n2;
    }

    private int getChildVerticalGravity(int n2) {
        int n3;
        n2 = n3 = n2 & 0x70;
        switch (n3) {
            default: {
                n2 = this.mGravity & 0x70;
            }
            case 16: 
            case 48: 
            case 80: 
        }
        return n2;
    }

    private int getHorizontalMargins(View view) {
        view = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        return MarginLayoutParamsCompat.getMarginStart((ViewGroup.MarginLayoutParams)view) + MarginLayoutParamsCompat.getMarginEnd((ViewGroup.MarginLayoutParams)view);
    }

    private MenuInflater getMenuInflater() {
        return new SupportMenuInflater(this.getContext());
    }

    private int getVerticalMargins(View view) {
        view = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        return view.topMargin + view.bottomMargin;
    }

    private int getViewListMeasuredWidth(List<View> list, int[] object) {
        int n2 = object[0];
        int n3 = object[1];
        int n4 = 0;
        int n5 = list.size();
        for (int i2 = 0; i2 < n5; ++i2) {
            object = list.get(i2);
            LayoutParams layoutParams = (LayoutParams)object.getLayoutParams();
            n2 = layoutParams.leftMargin - n2;
            n3 = layoutParams.rightMargin - n3;
            int n6 = Math.max(0, n2);
            int n7 = Math.max(0, n3);
            n2 = Math.max(0, -n2);
            n3 = Math.max(0, -n3);
            n4 += object.getMeasuredWidth() + n6 + n7;
        }
        return n4;
    }

    private boolean isChildOrHidden(View view) {
        return view.getParent() == this || this.mHiddenViews.contains(view);
    }

    private static boolean isCustomView(View view) {
        return ((LayoutParams)view.getLayoutParams()).mViewType == 0;
    }

    private int layoutChildLeft(View view, int n2, int[] nArray, int n3) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n4 = layoutParams.leftMargin - nArray[0];
        n2 += Math.max(0, n4);
        nArray[0] = Math.max(0, -n4);
        n3 = this.getChildTop(view, n3);
        n4 = view.getMeasuredWidth();
        view.layout(n2, n3, n2 + n4, view.getMeasuredHeight() + n3);
        return n2 + (layoutParams.rightMargin + n4);
    }

    private int layoutChildRight(View view, int n2, int[] nArray, int n3) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n4 = layoutParams.rightMargin - nArray[1];
        n2 -= Math.max(0, n4);
        nArray[1] = Math.max(0, -n4);
        n3 = this.getChildTop(view, n3);
        n4 = view.getMeasuredWidth();
        view.layout(n2 - n4, n3, n2, view.getMeasuredHeight() + n3);
        return n2 - (layoutParams.leftMargin + n4);
    }

    private int measureChildCollapseMargins(View view, int n2, int n3, int n4, int n5, int[] nArray) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        int n6 = marginLayoutParams.leftMargin - nArray[0];
        int n7 = marginLayoutParams.rightMargin - nArray[1];
        int n8 = Math.max(0, n6) + Math.max(0, n7);
        nArray[0] = Math.max(0, -n6);
        nArray[1] = Math.max(0, -n7);
        view.measure(Toolbar.getChildMeasureSpec((int)n2, (int)(this.getPaddingLeft() + this.getPaddingRight() + n8 + n3), (int)marginLayoutParams.width), Toolbar.getChildMeasureSpec((int)n4, (int)(this.getPaddingTop() + this.getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + n5), (int)marginLayoutParams.height));
        return view.getMeasuredWidth() + n8;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void measureChildConstrained(View view, int n2, int n3, int n4, int n5, int n6) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        int n7 = Toolbar.getChildMeasureSpec((int)n2, (int)(this.getPaddingLeft() + this.getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + n3), (int)marginLayoutParams.width);
        n3 = Toolbar.getChildMeasureSpec((int)n4, (int)(this.getPaddingTop() + this.getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + n5), (int)marginLayoutParams.height);
        n4 = View.MeasureSpec.getMode((int)n3);
        n2 = n3;
        if (n4 != 0x40000000) {
            n2 = n3;
            if (n6 >= 0) {
                n2 = n4 != 0 ? Math.min(View.MeasureSpec.getSize((int)n3), n6) : n6;
                n2 = View.MeasureSpec.makeMeasureSpec((int)n2, (int)0x40000000);
            }
        }
        view.measure(n7, n2);
    }

    private void postShowOverflowMenu() {
        this.removeCallbacks(this.mShowOverflowMenuRunnable);
        this.post(this.mShowOverflowMenuRunnable);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean shouldCollapse() {
        if (this.mCollapsible) {
            int n2 = this.getChildCount();
            int n3 = 0;
            while (true) {
                if (n3 >= n2) {
                    return true;
                }
                View view = this.getChildAt(n3);
                if (this.shouldLayout(view) && view.getMeasuredWidth() > 0 && view.getMeasuredHeight() > 0) break;
                ++n3;
            }
        }
        return false;
    }

    private boolean shouldLayout(View view) {
        return view != null && view.getParent() == this && view.getVisibility() != 8;
    }

    void addChildrenForExpandedActionView() {
        for (int i2 = this.mHiddenViews.size() - 1; i2 >= 0; --i2) {
            this.addView(this.mHiddenViews.get(i2));
        }
        this.mHiddenViews.clear();
    }

    public boolean canShowOverflowMenu() {
        return this.getVisibility() == 0 && this.mMenuView != null && this.mMenuView.isOverflowReserved();
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return super.checkLayoutParams(layoutParams) && layoutParams instanceof LayoutParams;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void collapseActionView() {
        if (this.mExpandedMenuPresenter == null) {
            return;
        }
        MenuItemImpl menuItemImpl = this.mExpandedMenuPresenter.mCurrentExpandedItem;
        if (menuItemImpl == null) return;
        menuItemImpl.collapseActionView();
    }

    public void dismissPopupMenus() {
        if (this.mMenuView != null) {
            this.mMenuView.dismissPopupMenus();
        }
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams)layoutParams);
        }
        if (layoutParams instanceof ActionBar.LayoutParams) {
            return new LayoutParams((ActionBar.LayoutParams)layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    public int getContentInsetEnd() {
        return this.mContentInsets.getEnd();
    }

    public int getContentInsetLeft() {
        return this.mContentInsets.getLeft();
    }

    public int getContentInsetRight() {
        return this.mContentInsets.getRight();
    }

    public int getContentInsetStart() {
        return this.mContentInsets.getStart();
    }

    public Drawable getLogo() {
        if (this.mLogoView != null) {
            return this.mLogoView.getDrawable();
        }
        return null;
    }

    public CharSequence getLogoDescription() {
        if (this.mLogoView != null) {
            return this.mLogoView.getContentDescription();
        }
        return null;
    }

    public Menu getMenu() {
        this.ensureMenu();
        return this.mMenuView.getMenu();
    }

    @Nullable
    public CharSequence getNavigationContentDescription() {
        if (this.mNavButtonView != null) {
            return this.mNavButtonView.getContentDescription();
        }
        return null;
    }

    @Nullable
    public Drawable getNavigationIcon() {
        if (this.mNavButtonView != null) {
            return this.mNavButtonView.getDrawable();
        }
        return null;
    }

    @Nullable
    public Drawable getOverflowIcon() {
        this.ensureMenu();
        return this.mMenuView.getOverflowIcon();
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitleText;
    }

    public CharSequence getTitle() {
        return this.mTitleText;
    }

    public DecorToolbar getWrapper() {
        if (this.mWrapper == null) {
            this.mWrapper = new ToolbarWidgetWrapper(this, true);
        }
        return this.mWrapper;
    }

    public boolean hasExpandedActionView() {
        return this.mExpandedMenuPresenter != null && this.mExpandedMenuPresenter.mCurrentExpandedItem != null;
    }

    public boolean hideOverflowMenu() {
        return this.mMenuView != null && this.mMenuView.hideOverflowMenu();
    }

    public void inflateMenu(@MenuRes int n2) {
        this.getMenuInflater().inflate(n2, this.getMenu());
    }

    public boolean isOverflowMenuShowPending() {
        return this.mMenuView != null && this.mMenuView.isOverflowMenuShowPending();
    }

    public boolean isOverflowMenuShowing() {
        return this.mMenuView != null && this.mMenuView.isOverflowMenuShowing();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isTitleTruncated() {
        Layout layout2;
        if (this.mTitleTextView != null && (layout2 = this.mTitleTextView.getLayout()) != null) {
            int n2 = layout2.getLineCount();
            for (int i2 = 0; i2 < n2; ++i2) {
                if (layout2.getEllipsisCount(i2) <= 0) continue;
                return true;
            }
        }
        return false;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.removeCallbacks(this.mShowOverflowMenuRunnable);
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        int n2 = MotionEventCompat.getActionMasked(motionEvent);
        if (n2 == 9) {
            this.mEatingHover = false;
        }
        if (!this.mEatingHover) {
            boolean bl2 = super.onHoverEvent(motionEvent);
            if (n2 == 9 && !bl2) {
                this.mEatingHover = true;
            }
        }
        if (n2 == 10 || n2 == 3) {
            this.mEatingHover = false;
        }
        return true;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean var1_1, int var2_2, int var3_3, int var4_4, int var5_5) {
        block38: {
            block39: {
                block37: {
                    var7_6 = ViewCompat.getLayoutDirection((View)this) == 1 ? 1 : 0;
                    var12_7 = this.getWidth();
                    var14_8 = this.getHeight();
                    var10_9 = this.getPaddingLeft();
                    var13_10 = this.getPaddingRight();
                    var9_11 = this.getPaddingTop();
                    var15_12 = this.getPaddingBottom();
                    var4_4 = var10_9;
                    var5_5 = var12_7 - var13_10;
                    var19_13 = this.mTempMargins;
                    var19_13[1] = 0;
                    var19_13[0] = 0;
                    var11_14 = ViewCompat.getMinimumHeight((View)this);
                    var2_2 = var4_4;
                    var3_3 = var5_5;
                    if (this.shouldLayout((View)this.mNavButtonView)) {
                        if (var7_6 != 0) {
                            var3_3 = this.layoutChildRight((View)this.mNavButtonView, var5_5, var19_13, var11_14);
                            var2_2 = var4_4;
                        } else {
                            var2_2 = this.layoutChildLeft((View)this.mNavButtonView, var4_4, var19_13, var11_14);
                            var3_3 = var5_5;
                        }
                    }
                    var4_4 = var2_2;
                    var5_5 = var3_3;
                    if (this.shouldLayout((View)this.mCollapseButtonView)) {
                        if (var7_6 != 0) {
                            var5_5 = this.layoutChildRight((View)this.mCollapseButtonView, var3_3, var19_13, var11_14);
                            var4_4 = var2_2;
                        } else {
                            var4_4 = this.layoutChildLeft((View)this.mCollapseButtonView, var2_2, var19_13, var11_14);
                            var5_5 = var3_3;
                        }
                    }
                    var3_3 = var4_4;
                    var2_2 = var5_5;
                    if (this.shouldLayout((View)this.mMenuView)) {
                        if (var7_6 != 0) {
                            var3_3 = this.layoutChildLeft((View)this.mMenuView, var4_4, var19_13, var11_14);
                            var2_2 = var5_5;
                        } else {
                            var2_2 = this.layoutChildRight((View)this.mMenuView, var5_5, var19_13, var11_14);
                            var3_3 = var4_4;
                        }
                    }
                    var19_13[0] = Math.max(0, this.getContentInsetLeft() - var3_3);
                    var19_13[1] = Math.max(0, this.getContentInsetRight() - (var12_7 - var13_10 - var2_2));
                    var4_4 = Math.max(var3_3, this.getContentInsetLeft());
                    var5_5 = Math.min(var2_2, var12_7 - var13_10 - this.getContentInsetRight());
                    var2_2 = var4_4;
                    var3_3 = var5_5;
                    if (this.shouldLayout(this.mExpandedActionView)) {
                        if (var7_6 != 0) {
                            var3_3 = this.layoutChildRight(this.mExpandedActionView, var5_5, var19_13, var11_14);
                            var2_2 = var4_4;
                        } else {
                            var2_2 = this.layoutChildLeft(this.mExpandedActionView, var4_4, var19_13, var11_14);
                            var3_3 = var5_5;
                        }
                    }
                    var4_4 = var2_2;
                    var5_5 = var3_3;
                    if (this.shouldLayout((View)this.mLogoView)) {
                        if (var7_6 != 0) {
                            var5_5 = this.layoutChildRight((View)this.mLogoView, var3_3, var19_13, var11_14);
                            var4_4 = var2_2;
                        } else {
                            var4_4 = this.layoutChildLeft((View)this.mLogoView, var2_2, var19_13, var11_14);
                            var5_5 = var3_3;
                        }
                    }
                    var1_1 = this.shouldLayout((View)this.mTitleTextView);
                    var16_15 = this.shouldLayout((View)this.mSubtitleTextView);
                    var2_2 = 0;
                    if (var1_1) {
                        var17_16 = (LayoutParams)this.mTitleTextView.getLayoutParams();
                        var2_2 = 0 + (var17_16.topMargin + this.mTitleTextView.getMeasuredHeight() + var17_16.bottomMargin);
                    }
                    var8_17 = var2_2;
                    if (var16_15) {
                        var17_16 = (LayoutParams)this.mSubtitleTextView.getLayoutParams();
                        var8_17 = var2_2 + (var17_16.topMargin + this.mSubtitleTextView.getMeasuredHeight() + var17_16.bottomMargin);
                    }
                    if (var1_1) break block37;
                    var3_3 = var4_4;
                    var2_2 = var5_5;
                    if (!var16_15) break block38;
                }
                var17_16 = var1_1 != false ? this.mTitleTextView : this.mSubtitleTextView;
                var18_18 /* !! */  = var16_15 != false ? this.mSubtitleTextView : this.mTitleTextView;
                var17_16 = (LayoutParams)var17_16.getLayoutParams();
                var18_18 /* !! */  = (LayoutParams)var18_18 /* !! */ .getLayoutParams();
                var6_19 = var1_1 != false && this.mTitleTextView.getMeasuredWidth() > 0 || var16_15 != false && this.mSubtitleTextView.getMeasuredWidth() > 0 ? 1 : 0;
                switch (this.mGravity & 112) {
                    default: {
                        var3_3 = (var14_8 - var9_11 - var15_12 - var8_17) / 2;
                        if (var3_3 >= var17_16.topMargin + this.mTitleMarginTop) ** GOTO lbl92
                        var2_2 = var17_16.topMargin + this.mTitleMarginTop;
                        ** GOTO lbl96
                    }
                    case 48: {
                        var2_2 = this.getPaddingTop() + var17_16.topMargin + this.mTitleMarginTop;
                        break block39;
                    }
lbl92:
                    // 1 sources

                    var8_17 = var14_8 - var15_12 - var8_17 - var3_3 - var9_11;
                    var2_2 = var3_3;
                    if (var8_17 < var17_16.bottomMargin + this.mTitleMarginBottom) {
                        var2_2 = Math.max(0, var3_3 - (var18_18 /* !! */ .bottomMargin + this.mTitleMarginBottom - var8_17));
                    }
lbl96:
                    // 4 sources

                    var2_2 = var9_11 + var2_2;
                    break block39;
                    case 80: 
                }
                var2_2 = var14_8 - var15_12 - var18_18 /* !! */ .bottomMargin - this.mTitleMarginBottom - var8_17;
            }
            if (var7_6 != 0) {
                var3_3 = var6_19 != 0 ? this.mTitleMarginStart : 0;
                var19_13[1] = Math.max(0, -var3_3);
                var8_17 = var5_5 -= Math.max(0, var3_3 -= var19_13[1]);
                var3_3 = var5_5;
                var7_6 = var8_17;
                var9_11 = var2_2;
                if (var1_1) {
                    var17_16 = (LayoutParams)this.mTitleTextView.getLayoutParams();
                    var7_6 = var8_17 - this.mTitleTextView.getMeasuredWidth();
                    var9_11 = var2_2 + this.mTitleTextView.getMeasuredHeight();
                    this.mTitleTextView.layout(var7_6, var2_2, var8_17, var9_11);
                    var7_6 -= this.mTitleMarginEnd;
                    var9_11 += var17_16.bottomMargin;
                }
                var8_17 = var3_3;
                if (var16_15) {
                    var17_16 = (LayoutParams)this.mSubtitleTextView.getLayoutParams();
                    var2_2 = var9_11 + var17_16.topMargin;
                    var8_17 = this.mSubtitleTextView.getMeasuredWidth();
                    var9_11 = var2_2 + this.mSubtitleTextView.getMeasuredHeight();
                    this.mSubtitleTextView.layout(var3_3 - var8_17, var2_2, var3_3, var9_11);
                    var8_17 = var3_3 - this.mTitleMarginEnd;
                    var2_2 = var17_16.bottomMargin;
                }
                var3_3 = var4_4;
                var2_2 = var5_5;
                if (var6_19 != 0) {
                    var2_2 = Math.min(var7_6, var8_17);
                    var3_3 = var4_4;
                }
            } else {
                var3_3 = var6_19 != 0 ? this.mTitleMarginStart : 0;
                var7_6 = var3_3 - var19_13[0];
                var3_3 = var4_4 + Math.max(0, var7_6);
                var19_13[0] = Math.max(0, -var7_6);
                var8_17 = var3_3;
                var4_4 = var3_3;
                var7_6 = var8_17;
                var9_11 = var2_2;
                if (var1_1) {
                    var17_16 = (LayoutParams)this.mTitleTextView.getLayoutParams();
                    var7_6 = var8_17 + this.mTitleTextView.getMeasuredWidth();
                    var9_11 = var2_2 + this.mTitleTextView.getMeasuredHeight();
                    this.mTitleTextView.layout(var8_17, var2_2, var7_6, var9_11);
                    var7_6 += this.mTitleMarginEnd;
                    var9_11 += var17_16.bottomMargin;
                }
                var8_17 = var4_4;
                if (var16_15) {
                    var17_16 = (LayoutParams)this.mSubtitleTextView.getLayoutParams();
                    var2_2 = var9_11 + var17_16.topMargin;
                    var8_17 = var4_4 + this.mSubtitleTextView.getMeasuredWidth();
                    var9_11 = var2_2 + this.mSubtitleTextView.getMeasuredHeight();
                    this.mSubtitleTextView.layout(var4_4, var2_2, var8_17, var9_11);
                    var8_17 += this.mTitleMarginEnd;
                    var2_2 = var17_16.bottomMargin;
                }
                var2_2 = var5_5;
                if (var6_19 != 0) {
                    var3_3 = Math.max(var7_6, var8_17);
                    var2_2 = var5_5;
                }
            }
        }
        this.addCustomViewsWithGravity(this.mTempViews, 3);
        var5_5 = this.mTempViews.size();
        for (var4_4 = 0; var4_4 < var5_5; ++var4_4) {
            var3_3 = this.layoutChildLeft(this.mTempViews.get(var4_4), var3_3, var19_13, var11_14);
        }
        this.addCustomViewsWithGravity(this.mTempViews, 5);
        var6_19 = this.mTempViews.size();
        var5_5 = 0;
        var4_4 = var2_2;
        for (var2_2 = var5_5; var2_2 < var6_19; ++var2_2) {
            var4_4 = this.layoutChildRight(this.mTempViews.get(var2_2), var4_4, var19_13, var11_14);
        }
        this.addCustomViewsWithGravity(this.mTempViews, 1);
        var2_2 = this.getViewListMeasuredWidth(this.mTempViews, var19_13);
        var5_5 = var10_9 + (var12_7 - var10_9 - var13_10) / 2 - var2_2 / 2;
        var6_19 = var5_5 + var2_2;
        if (var5_5 < var3_3) {
            var2_2 = var3_3;
        } else {
            var2_2 = var5_5;
            if (var6_19 > var4_4) {
                var2_2 = var5_5 - (var6_19 - var4_4);
            }
        }
        var4_4 = this.mTempViews.size();
        var3_3 = 0;
        while (true) {
            if (var3_3 >= var4_4) {
                this.mTempViews.clear();
                return;
            }
            var2_2 = this.layoutChildLeft(this.mTempViews.get(var3_3), var2_2, var19_13, var11_14);
            ++var3_3;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n2, int n3) {
        int n4;
        int n5;
        int n6 = 0;
        int n7 = 0;
        int[] nArray = this.mTempMargins;
        if (ViewUtils.isLayoutRtl((View)this)) {
            n5 = 1;
            n4 = 0;
        } else {
            n5 = 0;
            n4 = 1;
        }
        int n8 = 0;
        if (this.shouldLayout((View)this.mNavButtonView)) {
            this.measureChildConstrained((View)this.mNavButtonView, n2, 0, n3, 0, this.mMaxButtonHeight);
            n8 = this.mNavButtonView.getMeasuredWidth() + this.getHorizontalMargins((View)this.mNavButtonView);
            n6 = Math.max(0, this.mNavButtonView.getMeasuredHeight() + this.getVerticalMargins((View)this.mNavButtonView));
            n7 = ViewUtils.combineMeasuredStates(0, ViewCompat.getMeasuredState((View)this.mNavButtonView));
        }
        int n9 = n7;
        int n10 = n6;
        if (this.shouldLayout((View)this.mCollapseButtonView)) {
            this.measureChildConstrained((View)this.mCollapseButtonView, n2, 0, n3, 0, this.mMaxButtonHeight);
            n8 = this.mCollapseButtonView.getMeasuredWidth() + this.getHorizontalMargins((View)this.mCollapseButtonView);
            n10 = Math.max(n6, this.mCollapseButtonView.getMeasuredHeight() + this.getVerticalMargins((View)this.mCollapseButtonView));
            n9 = ViewUtils.combineMeasuredStates(n7, ViewCompat.getMeasuredState((View)this.mCollapseButtonView));
        }
        n7 = this.getContentInsetStart();
        int n11 = 0 + Math.max(n7, n8);
        nArray[n5] = Math.max(0, n7 - n8);
        n8 = 0;
        n7 = n9;
        n6 = n10;
        if (this.shouldLayout((View)this.mMenuView)) {
            this.measureChildConstrained((View)this.mMenuView, n2, n11, n3, 0, this.mMaxButtonHeight);
            n8 = this.mMenuView.getMeasuredWidth() + this.getHorizontalMargins((View)this.mMenuView);
            n6 = Math.max(n10, this.mMenuView.getMeasuredHeight() + this.getVerticalMargins((View)this.mMenuView));
            n7 = ViewUtils.combineMeasuredStates(n9, ViewCompat.getMeasuredState((View)this.mMenuView));
        }
        n9 = this.getContentInsetEnd();
        n5 = n11 + Math.max(n9, n8);
        nArray[n4] = Math.max(0, n9 - n8);
        n4 = n5;
        n9 = n7;
        n10 = n6;
        if (this.shouldLayout(this.mExpandedActionView)) {
            n4 = n5 + this.measureChildCollapseMargins(this.mExpandedActionView, n2, n5, n3, 0, nArray);
            n10 = Math.max(n6, this.mExpandedActionView.getMeasuredHeight() + this.getVerticalMargins(this.mExpandedActionView));
            n9 = ViewUtils.combineMeasuredStates(n7, ViewCompat.getMeasuredState(this.mExpandedActionView));
        }
        n7 = n4;
        n6 = n9;
        n8 = n10;
        if (this.shouldLayout((View)this.mLogoView)) {
            n7 = n4 + this.measureChildCollapseMargins((View)this.mLogoView, n2, n4, n3, 0, nArray);
            n8 = Math.max(n10, this.mLogoView.getMeasuredHeight() + this.getVerticalMargins((View)this.mLogoView));
            n6 = ViewUtils.combineMeasuredStates(n9, ViewCompat.getMeasuredState((View)this.mLogoView));
        }
        n11 = this.getChildCount();
        n4 = n8;
        n9 = n6;
        n8 = n7;
        for (n10 = 0; n10 < n11; ++n10) {
            View view = this.getChildAt(n10);
            n7 = n8;
            n6 = n9;
            n5 = n4;
            if (((LayoutParams)view.getLayoutParams()).mViewType == 0) {
                if (!this.shouldLayout(view)) {
                    n5 = n4;
                    n6 = n9;
                    n7 = n8;
                } else {
                    n7 = n8 + this.measureChildCollapseMargins(view, n2, n8, n3, 0, nArray);
                    n5 = Math.max(n4, view.getMeasuredHeight() + this.getVerticalMargins(view));
                    n6 = ViewUtils.combineMeasuredStates(n9, ViewCompat.getMeasuredState(view));
                }
            }
            n8 = n7;
            n9 = n6;
            n4 = n5;
        }
        n6 = 0;
        n7 = 0;
        int n12 = this.mTitleMarginTop + this.mTitleMarginBottom;
        int n13 = this.mTitleMarginStart + this.mTitleMarginEnd;
        n10 = n9;
        if (this.shouldLayout((View)this.mTitleTextView)) {
            this.measureChildCollapseMargins((View)this.mTitleTextView, n2, n8 + n13, n3, n12, nArray);
            n6 = this.mTitleTextView.getMeasuredWidth() + this.getHorizontalMargins((View)this.mTitleTextView);
            n7 = this.mTitleTextView.getMeasuredHeight() + this.getVerticalMargins((View)this.mTitleTextView);
            n10 = ViewUtils.combineMeasuredStates(n9, ViewCompat.getMeasuredState((View)this.mTitleTextView));
        }
        n5 = n10;
        n11 = n7;
        n9 = n6;
        if (this.shouldLayout((View)this.mSubtitleTextView)) {
            n9 = Math.max(n6, this.measureChildCollapseMargins((View)this.mSubtitleTextView, n2, n8 + n13, n3, n7 + n12, nArray));
            n11 = n7 + (this.mSubtitleTextView.getMeasuredHeight() + this.getVerticalMargins((View)this.mSubtitleTextView));
            n5 = ViewUtils.combineMeasuredStates(n10, ViewCompat.getMeasuredState((View)this.mSubtitleTextView));
        }
        n10 = Math.max(n4, n11);
        n4 = this.getPaddingLeft();
        n11 = this.getPaddingRight();
        n7 = this.getPaddingTop();
        n6 = this.getPaddingBottom();
        n9 = ViewCompat.resolveSizeAndState(Math.max(n8 + n9 + (n4 + n11), this.getSuggestedMinimumWidth()), n2, 0xFF000000 & n5);
        n2 = ViewCompat.resolveSizeAndState(Math.max(n10 + (n7 + n6), this.getSuggestedMinimumHeight()), n3, n5 << 16);
        if (this.shouldCollapse()) {
            n2 = 0;
        }
        this.setMeasuredDimension(n9, n2);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    protected void onRestoreInstanceState(Parcelable object) {
        if (!(object instanceof SavedState)) {
            super.onRestoreInstanceState(object);
            return;
        } else {
            MenuItem menuItem;
            void var1_3;
            SavedState savedState = (SavedState)((Object)object);
            super.onRestoreInstanceState(savedState.getSuperState());
            if (this.mMenuView != null) {
                MenuBuilder menuBuilder = this.mMenuView.peekMenu();
            } else {
                Object var1_5 = null;
            }
            if (savedState.expandedMenuItemId != 0 && this.mExpandedMenuPresenter != null && var1_3 != null && (menuItem = var1_3.findItem(savedState.expandedMenuItemId)) != null) {
                MenuItemCompat.expandActionView(menuItem);
            }
            if (!savedState.isOverflowOpen) return;
            this.postShowOverflowMenu();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onRtlPropertiesChanged(int n2) {
        boolean bl2 = true;
        if (Build.VERSION.SDK_INT >= 17) {
            super.onRtlPropertiesChanged(n2);
        }
        RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
        if (n2 != 1) {
            bl2 = false;
        }
        rtlSpacingHelper.setDirection(bl2);
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.mExpandedMenuPresenter != null && this.mExpandedMenuPresenter.mCurrentExpandedItem != null) {
            savedState.expandedMenuItemId = this.mExpandedMenuPresenter.mCurrentExpandedItem.getItemId();
        }
        savedState.isOverflowOpen = this.isOverflowMenuShowing();
        return savedState;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int n2 = MotionEventCompat.getActionMasked(motionEvent);
        if (n2 == 0) {
            this.mEatingTouch = false;
        }
        if (!this.mEatingTouch) {
            boolean bl2 = super.onTouchEvent(motionEvent);
            if (n2 == 0 && !bl2) {
                this.mEatingTouch = true;
            }
        }
        if (n2 == 1 || n2 == 3) {
            this.mEatingTouch = false;
        }
        return true;
    }

    void removeChildrenForExpandedActionView() {
        for (int i2 = this.getChildCount() - 1; i2 >= 0; --i2) {
            View view = this.getChildAt(i2);
            if (((LayoutParams)view.getLayoutParams()).mViewType == 2 || view == this.mMenuView) continue;
            this.removeViewAt(i2);
            this.mHiddenViews.add(view);
        }
    }

    public void setCollapsible(boolean bl2) {
        this.mCollapsible = bl2;
        this.requestLayout();
    }

    public void setContentInsetsAbsolute(int n2, int n3) {
        this.mContentInsets.setAbsolute(n2, n3);
    }

    public void setContentInsetsRelative(int n2, int n3) {
        this.mContentInsets.setRelative(n2, n3);
    }

    public void setLogo(@DrawableRes int n2) {
        this.setLogo(this.mDrawableManager.getDrawable(this.getContext(), n2));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setLogo(Drawable drawable2) {
        if (drawable2 != null) {
            this.ensureLogoView();
            if (!this.isChildOrHidden((View)this.mLogoView)) {
                this.addSystemView((View)this.mLogoView, true);
            }
        } else if (this.mLogoView != null && this.isChildOrHidden((View)this.mLogoView)) {
            this.removeView((View)this.mLogoView);
            this.mHiddenViews.remove(this.mLogoView);
        }
        if (this.mLogoView != null) {
            this.mLogoView.setImageDrawable(drawable2);
        }
    }

    public void setLogoDescription(@StringRes int n2) {
        this.setLogoDescription(this.getContext().getText(n2));
    }

    public void setLogoDescription(CharSequence charSequence) {
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            this.ensureLogoView();
        }
        if (this.mLogoView != null) {
            this.mLogoView.setContentDescription(charSequence);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setMenu(MenuBuilder menuBuilder, ActionMenuPresenter actionMenuPresenter) {
        MenuBuilder menuBuilder2;
        block8: {
            block7: {
                if (menuBuilder == null && this.mMenuView == null) break block7;
                this.ensureMenuView();
                menuBuilder2 = this.mMenuView.peekMenu();
                if (menuBuilder2 != menuBuilder) break block8;
            }
            return;
        }
        if (menuBuilder2 != null) {
            menuBuilder2.removeMenuPresenter(this.mOuterActionMenuPresenter);
            menuBuilder2.removeMenuPresenter(this.mExpandedMenuPresenter);
        }
        if (this.mExpandedMenuPresenter == null) {
            this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
        }
        actionMenuPresenter.setExpandedActionViewsExclusive(true);
        if (menuBuilder != null) {
            menuBuilder.addMenuPresenter(actionMenuPresenter, this.mPopupContext);
            menuBuilder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
        } else {
            actionMenuPresenter.initForMenu(this.mPopupContext, null);
            this.mExpandedMenuPresenter.initForMenu(this.mPopupContext, null);
            actionMenuPresenter.updateMenuView(true);
            this.mExpandedMenuPresenter.updateMenuView(true);
        }
        this.mMenuView.setPopupTheme(this.mPopupTheme);
        this.mMenuView.setPresenter(actionMenuPresenter);
        this.mOuterActionMenuPresenter = actionMenuPresenter;
    }

    public void setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        this.mActionMenuPresenterCallback = callback;
        this.mMenuBuilderCallback = callback2;
        if (this.mMenuView != null) {
            this.mMenuView.setMenuCallbacks(callback, callback2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setNavigationContentDescription(@StringRes int n2) {
        CharSequence charSequence = n2 != 0 ? this.getContext().getText(n2) : null;
        this.setNavigationContentDescription(charSequence);
    }

    public void setNavigationContentDescription(@Nullable CharSequence charSequence) {
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            this.ensureNavButtonView();
        }
        if (this.mNavButtonView != null) {
            this.mNavButtonView.setContentDescription(charSequence);
        }
    }

    public void setNavigationIcon(@DrawableRes int n2) {
        this.setNavigationIcon(this.mDrawableManager.getDrawable(this.getContext(), n2));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setNavigationIcon(@Nullable Drawable drawable2) {
        if (drawable2 != null) {
            this.ensureNavButtonView();
            if (!this.isChildOrHidden((View)this.mNavButtonView)) {
                this.addSystemView((View)this.mNavButtonView, true);
            }
        } else if (this.mNavButtonView != null && this.isChildOrHidden((View)this.mNavButtonView)) {
            this.removeView((View)this.mNavButtonView);
            this.mHiddenViews.remove(this.mNavButtonView);
        }
        if (this.mNavButtonView != null) {
            this.mNavButtonView.setImageDrawable(drawable2);
        }
    }

    public void setNavigationOnClickListener(View.OnClickListener onClickListener) {
        this.ensureNavButtonView();
        this.mNavButtonView.setOnClickListener(onClickListener);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public void setOverflowIcon(@Nullable Drawable drawable2) {
        this.ensureMenu();
        this.mMenuView.setOverflowIcon(drawable2);
    }

    public void setPopupTheme(@StyleRes int n2) {
        block3: {
            block2: {
                if (this.mPopupTheme == n2) break block2;
                this.mPopupTheme = n2;
                if (n2 != 0) break block3;
                this.mPopupContext = this.getContext();
            }
            return;
        }
        this.mPopupContext = new ContextThemeWrapper(this.getContext(), n2);
    }

    public void setSubtitle(@StringRes int n2) {
        this.setSubtitle(this.getContext().getText(n2));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setSubtitle(CharSequence charSequence) {
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            if (this.mSubtitleTextView == null) {
                Context context = this.getContext();
                this.mSubtitleTextView = new TextView(context);
                this.mSubtitleTextView.setSingleLine();
                this.mSubtitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                if (this.mSubtitleTextAppearance != 0) {
                    this.mSubtitleTextView.setTextAppearance(context, this.mSubtitleTextAppearance);
                }
                if (this.mSubtitleTextColor != 0) {
                    this.mSubtitleTextView.setTextColor(this.mSubtitleTextColor);
                }
            }
            if (!this.isChildOrHidden((View)this.mSubtitleTextView)) {
                this.addSystemView((View)this.mSubtitleTextView, true);
            }
        } else if (this.mSubtitleTextView != null && this.isChildOrHidden((View)this.mSubtitleTextView)) {
            this.removeView((View)this.mSubtitleTextView);
            this.mHiddenViews.remove(this.mSubtitleTextView);
        }
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setText(charSequence);
        }
        this.mSubtitleText = charSequence;
    }

    public void setSubtitleTextAppearance(Context context, @StyleRes int n2) {
        this.mSubtitleTextAppearance = n2;
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setTextAppearance(context, n2);
        }
    }

    public void setSubtitleTextColor(@ColorInt int n2) {
        this.mSubtitleTextColor = n2;
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setTextColor(n2);
        }
    }

    public void setTitle(@StringRes int n2) {
        this.setTitle(this.getContext().getText(n2));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setTitle(CharSequence charSequence) {
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            if (this.mTitleTextView == null) {
                Context context = this.getContext();
                this.mTitleTextView = new TextView(context);
                this.mTitleTextView.setSingleLine();
                this.mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                if (this.mTitleTextAppearance != 0) {
                    this.mTitleTextView.setTextAppearance(context, this.mTitleTextAppearance);
                }
                if (this.mTitleTextColor != 0) {
                    this.mTitleTextView.setTextColor(this.mTitleTextColor);
                }
            }
            if (!this.isChildOrHidden((View)this.mTitleTextView)) {
                this.addSystemView((View)this.mTitleTextView, true);
            }
        } else if (this.mTitleTextView != null && this.isChildOrHidden((View)this.mTitleTextView)) {
            this.removeView((View)this.mTitleTextView);
            this.mHiddenViews.remove(this.mTitleTextView);
        }
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setText(charSequence);
        }
        this.mTitleText = charSequence;
    }

    public void setTitleTextAppearance(Context context, @StyleRes int n2) {
        this.mTitleTextAppearance = n2;
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setTextAppearance(context, n2);
        }
    }

    public void setTitleTextColor(@ColorInt int n2) {
        this.mTitleTextColor = n2;
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setTextColor(n2);
        }
    }

    public boolean showOverflowMenu() {
        return this.mMenuView != null && this.mMenuView.showOverflowMenu();
    }

    private class ExpandedActionViewMenuPresenter
    implements MenuPresenter {
        MenuItemImpl mCurrentExpandedItem;
        MenuBuilder mMenu;

        private ExpandedActionViewMenuPresenter() {
        }

        @Override
        public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
            if (Toolbar.this.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView)Toolbar.this.mExpandedActionView).onActionViewCollapsed();
            }
            Toolbar.this.removeView(Toolbar.this.mExpandedActionView);
            Toolbar.this.removeView((View)Toolbar.this.mCollapseButtonView);
            Toolbar.this.mExpandedActionView = null;
            Toolbar.this.addChildrenForExpandedActionView();
            this.mCurrentExpandedItem = null;
            Toolbar.this.requestLayout();
            menuItemImpl.setActionViewExpanded(false);
            return true;
        }

        @Override
        public boolean expandItemActionView(MenuBuilder object, MenuItemImpl menuItemImpl) {
            Toolbar.this.ensureCollapseButtonView();
            if (Toolbar.this.mCollapseButtonView.getParent() != Toolbar.this) {
                Toolbar.this.addView((View)Toolbar.this.mCollapseButtonView);
            }
            Toolbar.this.mExpandedActionView = menuItemImpl.getActionView();
            this.mCurrentExpandedItem = menuItemImpl;
            if (Toolbar.this.mExpandedActionView.getParent() != Toolbar.this) {
                object = Toolbar.this.generateDefaultLayoutParams();
                ((LayoutParams)object).gravity = 0x800003 | Toolbar.this.mButtonGravity & 0x70;
                ((LayoutParams)object).mViewType = 2;
                Toolbar.this.mExpandedActionView.setLayoutParams((ViewGroup.LayoutParams)object);
                Toolbar.this.addView(Toolbar.this.mExpandedActionView);
            }
            Toolbar.this.removeChildrenForExpandedActionView();
            Toolbar.this.requestLayout();
            menuItemImpl.setActionViewExpanded(true);
            if (Toolbar.this.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView)Toolbar.this.mExpandedActionView).onActionViewExpanded();
            }
            return true;
        }

        @Override
        public boolean flagActionItems() {
            return false;
        }

        @Override
        public int getId() {
            return 0;
        }

        @Override
        public MenuView getMenuView(ViewGroup viewGroup) {
            return null;
        }

        @Override
        public void initForMenu(Context context, MenuBuilder menuBuilder) {
            if (this.mMenu != null && this.mCurrentExpandedItem != null) {
                this.mMenu.collapseItemActionView(this.mCurrentExpandedItem);
            }
            this.mMenu = menuBuilder;
        }

        @Override
        public void onCloseMenu(MenuBuilder menuBuilder, boolean bl2) {
        }

        @Override
        public void onRestoreInstanceState(Parcelable parcelable) {
        }

        @Override
        public Parcelable onSaveInstanceState() {
            return null;
        }

        @Override
        public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
            return false;
        }

        @Override
        public void setCallback(MenuPresenter.Callback callback) {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void updateMenuView(boolean bl2) {
            boolean bl3;
            if (this.mCurrentExpandedItem == null) return;
            boolean bl4 = bl3 = false;
            if (this.mMenu != null) {
                int n2 = this.mMenu.size();
                int n3 = 0;
                while (true) {
                    bl4 = bl3;
                    if (n3 >= n2) break;
                    if (this.mMenu.getItem(n3) == this.mCurrentExpandedItem) {
                        return;
                    }
                    ++n3;
                }
            }
            if (bl4) return;
            this.collapseItemActionView(this.mMenu, this.mCurrentExpandedItem);
        }
    }

    public static class LayoutParams
    extends ActionBar.LayoutParams {
        static final int CUSTOM = 0;
        static final int EXPANDED = 2;
        static final int SYSTEM = 1;
        int mViewType = 0;

        public LayoutParams(int n2) {
            this(-2, -1, n2);
        }

        public LayoutParams(int n2, int n3) {
            super(n2, n3);
            this.gravity = 8388627;
        }

        public LayoutParams(int n2, int n3, int n4) {
            super(n2, n3);
            this.gravity = n4;
        }

        public LayoutParams(@NonNull Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ActionBar.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
            this.mViewType = layoutParams.mViewType;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super((ViewGroup.LayoutParams)marginLayoutParams);
            this.copyMarginsFromCompat(marginLayoutParams);
        }

        void copyMarginsFromCompat(ViewGroup.MarginLayoutParams marginLayoutParams) {
            this.leftMargin = marginLayoutParams.leftMargin;
            this.topMargin = marginLayoutParams.topMargin;
            this.rightMargin = marginLayoutParams.rightMargin;
            this.bottomMargin = marginLayoutParams.bottomMargin;
        }
    }

    public static interface OnMenuItemClickListener {
        public boolean onMenuItemClick(MenuItem var1);
    }

    public static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n2) {
                return new SavedState[n2];
            }
        };
        int expandedMenuItemId;
        boolean isOverflowOpen;

        /*
         * Enabled aggressive block sorting
         */
        public SavedState(Parcel parcel) {
            super(parcel);
            this.expandedMenuItemId = parcel.readInt();
            boolean bl2 = parcel.readInt() != 0;
            this.isOverflowOpen = bl2;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        /*
         * Enabled aggressive block sorting
         */
        public void writeToParcel(Parcel parcel, int n2) {
            super.writeToParcel(parcel, n2);
            parcel.writeInt(this.expandedMenuItemId);
            n2 = this.isOverflowOpen ? 1 : 0;
            parcel.writeInt(n2);
        }
    }
}

