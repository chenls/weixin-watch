/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.os.Parcelable
 *  android.text.TextUtils
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.Window$Callback
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.ActionMenuItem;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.widget.ActionMenuPresenter;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DecorToolbar;
import android.support.v7.widget.ScrollingTabContainerView;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class ToolbarWidgetWrapper
implements DecorToolbar {
    private static final int AFFECTS_LOGO_MASK = 3;
    private static final long DEFAULT_FADE_DURATION_MS = 200L;
    private static final String TAG = "ToolbarWidgetWrapper";
    private ActionMenuPresenter mActionMenuPresenter;
    private View mCustomView;
    private int mDefaultNavigationContentDescription = 0;
    private Drawable mDefaultNavigationIcon;
    private int mDisplayOpts;
    private final AppCompatDrawableManager mDrawableManager;
    private CharSequence mHomeDescription;
    private Drawable mIcon;
    private Drawable mLogo;
    private boolean mMenuPrepared;
    private Drawable mNavIcon;
    private int mNavigationMode = 0;
    private Spinner mSpinner;
    private CharSequence mSubtitle;
    private View mTabView;
    private CharSequence mTitle;
    private boolean mTitleSet;
    private Toolbar mToolbar;
    private Window.Callback mWindowCallback;

    public ToolbarWidgetWrapper(Toolbar toolbar, boolean bl2) {
        this(toolbar, bl2, R.string.abc_action_bar_up_description, R.drawable.abc_ic_ab_back_mtrl_am_alpha);
    }

    /*
     * Enabled aggressive block sorting
     */
    public ToolbarWidgetWrapper(Toolbar object, boolean bl2, int n2, int n3) {
        this.mToolbar = object;
        this.mTitle = ((Toolbar)((Object)object)).getTitle();
        this.mSubtitle = ((Toolbar)((Object)object)).getSubtitle();
        boolean bl3 = this.mTitle != null;
        this.mTitleSet = bl3;
        this.mNavIcon = ((Toolbar)((Object)object)).getNavigationIcon();
        if (bl2) {
            CharSequence charSequence = ((TintTypedArray)(object = TintTypedArray.obtainStyledAttributes(object.getContext(), null, R.styleable.ActionBar, R.attr.actionBarStyle, 0))).getText(R.styleable.ActionBar_title);
            if (!TextUtils.isEmpty((CharSequence)charSequence)) {
                this.setTitle(charSequence);
            }
            if (!TextUtils.isEmpty((CharSequence)(charSequence = ((TintTypedArray)object).getText(R.styleable.ActionBar_subtitle)))) {
                this.setSubtitle(charSequence);
            }
            if ((charSequence = ((TintTypedArray)object).getDrawable(R.styleable.ActionBar_logo)) != null) {
                this.setLogo((Drawable)charSequence);
            }
            charSequence = ((TintTypedArray)object).getDrawable(R.styleable.ActionBar_icon);
            if (this.mNavIcon == null && charSequence != null) {
                this.setIcon((Drawable)charSequence);
            }
            if ((charSequence = ((TintTypedArray)object).getDrawable(R.styleable.ActionBar_homeAsUpIndicator)) != null) {
                this.setNavigationIcon((Drawable)charSequence);
            }
            this.setDisplayOptions(((TintTypedArray)object).getInt(R.styleable.ActionBar_displayOptions, 0));
            int n4 = ((TintTypedArray)object).getResourceId(R.styleable.ActionBar_customNavigationLayout, 0);
            if (n4 != 0) {
                this.setCustomView(LayoutInflater.from((Context)this.mToolbar.getContext()).inflate(n4, (ViewGroup)this.mToolbar, false));
                this.setDisplayOptions(this.mDisplayOpts | 0x10);
            }
            if ((n4 = ((TintTypedArray)object).getLayoutDimension(R.styleable.ActionBar_height, 0)) > 0) {
                charSequence = this.mToolbar.getLayoutParams();
                ((ViewGroup.LayoutParams)charSequence).height = n4;
                this.mToolbar.setLayoutParams((ViewGroup.LayoutParams)charSequence);
            }
            n4 = ((TintTypedArray)object).getDimensionPixelOffset(R.styleable.ActionBar_contentInsetStart, -1);
            int n5 = ((TintTypedArray)object).getDimensionPixelOffset(R.styleable.ActionBar_contentInsetEnd, -1);
            if (n4 >= 0 || n5 >= 0) {
                this.mToolbar.setContentInsetsRelative(Math.max(n4, 0), Math.max(n5, 0));
            }
            if ((n4 = ((TintTypedArray)object).getResourceId(R.styleable.ActionBar_titleTextStyle, 0)) != 0) {
                this.mToolbar.setTitleTextAppearance(this.mToolbar.getContext(), n4);
            }
            if ((n4 = ((TintTypedArray)object).getResourceId(R.styleable.ActionBar_subtitleTextStyle, 0)) != 0) {
                this.mToolbar.setSubtitleTextAppearance(this.mToolbar.getContext(), n4);
            }
            if ((n4 = ((TintTypedArray)object).getResourceId(R.styleable.ActionBar_popupTheme, 0)) != 0) {
                this.mToolbar.setPopupTheme(n4);
            }
            ((TintTypedArray)object).recycle();
        } else {
            this.mDisplayOpts = this.detectDisplayOptions();
        }
        this.mDrawableManager = AppCompatDrawableManager.get();
        this.setDefaultNavigationContentDescription(n2);
        this.mHomeDescription = this.mToolbar.getNavigationContentDescription();
        this.setDefaultNavigationIcon(this.mDrawableManager.getDrawable(this.getContext(), n3));
        this.mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
            final ActionMenuItem mNavItem;
            {
                this.mNavItem = new ActionMenuItem(ToolbarWidgetWrapper.this.mToolbar.getContext(), 0, 16908332, 0, 0, ToolbarWidgetWrapper.this.mTitle);
            }

            public void onClick(View view) {
                if (ToolbarWidgetWrapper.this.mWindowCallback != null && ToolbarWidgetWrapper.this.mMenuPrepared) {
                    ToolbarWidgetWrapper.this.mWindowCallback.onMenuItemSelected(0, (MenuItem)this.mNavItem);
                }
            }
        });
    }

    private int detectDisplayOptions() {
        int n2 = 11;
        if (this.mToolbar.getNavigationIcon() != null) {
            n2 = 0xB | 4;
        }
        return n2;
    }

    private void ensureSpinner() {
        if (this.mSpinner == null) {
            this.mSpinner = new AppCompatSpinner(this.getContext(), null, R.attr.actionDropDownStyle);
            Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(-2, -2, 8388627);
            this.mSpinner.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        }
    }

    private void setTitleInt(CharSequence charSequence) {
        this.mTitle = charSequence;
        if ((this.mDisplayOpts & 8) != 0) {
            this.mToolbar.setTitle(charSequence);
        }
    }

    private void updateHomeAccessibility() {
        block3: {
            block2: {
                if ((this.mDisplayOpts & 4) == 0) break block2;
                if (!TextUtils.isEmpty((CharSequence)this.mHomeDescription)) break block3;
                this.mToolbar.setNavigationContentDescription(this.mDefaultNavigationContentDescription);
            }
            return;
        }
        this.mToolbar.setNavigationContentDescription(this.mHomeDescription);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateNavigationIcon() {
        if ((this.mDisplayOpts & 4) != 0) {
            Toolbar toolbar = this.mToolbar;
            Drawable drawable2 = this.mNavIcon != null ? this.mNavIcon : this.mDefaultNavigationIcon;
            toolbar.setNavigationIcon(drawable2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateToolbarLogo() {
        Drawable drawable2 = null;
        if ((this.mDisplayOpts & 2) != 0) {
            drawable2 = (this.mDisplayOpts & 1) != 0 ? (this.mLogo != null ? this.mLogo : this.mIcon) : this.mIcon;
        }
        this.mToolbar.setLogo(drawable2);
    }

    @Override
    public void animateToVisibility(int n2) {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = this.setupAnimatorToVisibility(n2, 200L);
        if (viewPropertyAnimatorCompat != null) {
            viewPropertyAnimatorCompat.start();
        }
    }

    @Override
    public boolean canShowOverflowMenu() {
        return this.mToolbar.canShowOverflowMenu();
    }

    @Override
    public void collapseActionView() {
        this.mToolbar.collapseActionView();
    }

    @Override
    public void dismissPopupMenus() {
        this.mToolbar.dismissPopupMenus();
    }

    @Override
    public Context getContext() {
        return this.mToolbar.getContext();
    }

    @Override
    public View getCustomView() {
        return this.mCustomView;
    }

    @Override
    public int getDisplayOptions() {
        return this.mDisplayOpts;
    }

    @Override
    public int getDropdownItemCount() {
        if (this.mSpinner != null) {
            return this.mSpinner.getCount();
        }
        return 0;
    }

    @Override
    public int getDropdownSelectedPosition() {
        if (this.mSpinner != null) {
            return this.mSpinner.getSelectedItemPosition();
        }
        return 0;
    }

    @Override
    public int getHeight() {
        return this.mToolbar.getHeight();
    }

    @Override
    public Menu getMenu() {
        return this.mToolbar.getMenu();
    }

    @Override
    public int getNavigationMode() {
        return this.mNavigationMode;
    }

    @Override
    public CharSequence getSubtitle() {
        return this.mToolbar.getSubtitle();
    }

    @Override
    public CharSequence getTitle() {
        return this.mToolbar.getTitle();
    }

    @Override
    public ViewGroup getViewGroup() {
        return this.mToolbar;
    }

    @Override
    public int getVisibility() {
        return this.mToolbar.getVisibility();
    }

    @Override
    public boolean hasEmbeddedTabs() {
        return this.mTabView != null;
    }

    @Override
    public boolean hasExpandedActionView() {
        return this.mToolbar.hasExpandedActionView();
    }

    @Override
    public boolean hasIcon() {
        return this.mIcon != null;
    }

    @Override
    public boolean hasLogo() {
        return this.mLogo != null;
    }

    @Override
    public boolean hideOverflowMenu() {
        return this.mToolbar.hideOverflowMenu();
    }

    @Override
    public void initIndeterminateProgress() {
        Log.i((String)TAG, (String)"Progress display unsupported");
    }

    @Override
    public void initProgress() {
        Log.i((String)TAG, (String)"Progress display unsupported");
    }

    @Override
    public boolean isOverflowMenuShowPending() {
        return this.mToolbar.isOverflowMenuShowPending();
    }

    @Override
    public boolean isOverflowMenuShowing() {
        return this.mToolbar.isOverflowMenuShowing();
    }

    @Override
    public boolean isTitleTruncated() {
        return this.mToolbar.isTitleTruncated();
    }

    @Override
    public void restoreHierarchyState(SparseArray<Parcelable> sparseArray) {
        this.mToolbar.restoreHierarchyState(sparseArray);
    }

    @Override
    public void saveHierarchyState(SparseArray<Parcelable> sparseArray) {
        this.mToolbar.saveHierarchyState(sparseArray);
    }

    @Override
    public void setBackgroundDrawable(Drawable drawable2) {
        this.mToolbar.setBackgroundDrawable(drawable2);
    }

    @Override
    public void setCollapsible(boolean bl2) {
        this.mToolbar.setCollapsible(bl2);
    }

    @Override
    public void setCustomView(View view) {
        if (this.mCustomView != null && (this.mDisplayOpts & 0x10) != 0) {
            this.mToolbar.removeView(this.mCustomView);
        }
        this.mCustomView = view;
        if (view != null && (this.mDisplayOpts & 0x10) != 0) {
            this.mToolbar.addView(this.mCustomView);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setDefaultNavigationContentDescription(int n2) {
        block3: {
            block2: {
                if (n2 == this.mDefaultNavigationContentDescription) break block2;
                this.mDefaultNavigationContentDescription = n2;
                if (TextUtils.isEmpty((CharSequence)this.mToolbar.getNavigationContentDescription())) break block3;
            }
            return;
        }
        this.setNavigationContentDescription(this.mDefaultNavigationContentDescription);
    }

    @Override
    public void setDefaultNavigationIcon(Drawable drawable2) {
        if (this.mDefaultNavigationIcon != drawable2) {
            this.mDefaultNavigationIcon = drawable2;
            this.updateNavigationIcon();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setDisplayOptions(int n2) {
        int n3 = this.mDisplayOpts ^ n2;
        this.mDisplayOpts = n2;
        if (n3 != 0) {
            if ((n3 & 4) != 0) {
                if ((n2 & 4) != 0) {
                    this.updateNavigationIcon();
                    this.updateHomeAccessibility();
                } else {
                    this.mToolbar.setNavigationIcon(null);
                }
            }
            if ((n3 & 3) != 0) {
                this.updateToolbarLogo();
            }
            if ((n3 & 8) != 0) {
                if ((n2 & 8) != 0) {
                    this.mToolbar.setTitle(this.mTitle);
                    this.mToolbar.setSubtitle(this.mSubtitle);
                } else {
                    this.mToolbar.setTitle(null);
                    this.mToolbar.setSubtitle(null);
                }
            }
            if ((n3 & 0x10) != 0 && this.mCustomView != null) {
                if ((n2 & 0x10) == 0) {
                    this.mToolbar.removeView(this.mCustomView);
                    return;
                }
                this.mToolbar.addView(this.mCustomView);
            }
        }
    }

    @Override
    public void setDropdownParams(SpinnerAdapter spinnerAdapter, AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.ensureSpinner();
        this.mSpinner.setAdapter(spinnerAdapter);
        this.mSpinner.setOnItemSelectedListener(onItemSelectedListener);
    }

    @Override
    public void setDropdownSelectedPosition(int n2) {
        if (this.mSpinner == null) {
            throw new IllegalStateException("Can't set dropdown selected position without an adapter");
        }
        this.mSpinner.setSelection(n2);
    }

    @Override
    public void setEmbeddedTabView(ScrollingTabContainerView scrollingTabContainerView) {
        if (this.mTabView != null && this.mTabView.getParent() == this.mToolbar) {
            this.mToolbar.removeView(this.mTabView);
        }
        this.mTabView = scrollingTabContainerView;
        if (scrollingTabContainerView != null && this.mNavigationMode == 2) {
            this.mToolbar.addView(this.mTabView, 0);
            Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams)this.mTabView.getLayoutParams();
            layoutParams.width = -2;
            layoutParams.height = -2;
            layoutParams.gravity = 8388691;
            scrollingTabContainerView.setAllowCollapse(true);
        }
    }

    @Override
    public void setHomeButtonEnabled(boolean bl2) {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setIcon(int n2) {
        Drawable drawable2 = n2 != 0 ? this.mDrawableManager.getDrawable(this.getContext(), n2) : null;
        this.setIcon(drawable2);
    }

    @Override
    public void setIcon(Drawable drawable2) {
        this.mIcon = drawable2;
        this.updateToolbarLogo();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setLogo(int n2) {
        Drawable drawable2 = n2 != 0 ? this.mDrawableManager.getDrawable(this.getContext(), n2) : null;
        this.setLogo(drawable2);
    }

    @Override
    public void setLogo(Drawable drawable2) {
        this.mLogo = drawable2;
        this.updateToolbarLogo();
    }

    @Override
    public void setMenu(Menu menu, MenuPresenter.Callback callback) {
        if (this.mActionMenuPresenter == null) {
            this.mActionMenuPresenter = new ActionMenuPresenter(this.mToolbar.getContext());
            this.mActionMenuPresenter.setId(R.id.action_menu_presenter);
        }
        this.mActionMenuPresenter.setCallback(callback);
        this.mToolbar.setMenu((MenuBuilder)menu, this.mActionMenuPresenter);
    }

    @Override
    public void setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        this.mToolbar.setMenuCallbacks(callback, callback2);
    }

    @Override
    public void setMenuPrepared() {
        this.mMenuPrepared = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setNavigationContentDescription(int n2) {
        String string2 = n2 == 0 ? null : this.getContext().getString(n2);
        this.setNavigationContentDescription(string2);
    }

    @Override
    public void setNavigationContentDescription(CharSequence charSequence) {
        this.mHomeDescription = charSequence;
        this.updateHomeAccessibility();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setNavigationIcon(int n2) {
        Drawable drawable2 = n2 != 0 ? AppCompatDrawableManager.get().getDrawable(this.getContext(), n2) : null;
        this.setNavigationIcon(drawable2);
    }

    @Override
    public void setNavigationIcon(Drawable drawable2) {
        this.mNavIcon = drawable2;
        this.updateNavigationIcon();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setNavigationMode(int n2) {
        int n3 = this.mNavigationMode;
        if (n2 == n3) return;
        switch (n3) {
            case 1: {
                if (this.mSpinner != null && this.mSpinner.getParent() == this.mToolbar) {
                    this.mToolbar.removeView((View)this.mSpinner);
                }
            }
            default: {
                break;
            }
            case 2: {
                if (this.mTabView == null || this.mTabView.getParent() != this.mToolbar) break;
                this.mToolbar.removeView(this.mTabView);
            }
        }
        this.mNavigationMode = n2;
        switch (n2) {
            default: {
                throw new IllegalArgumentException("Invalid navigation mode " + n2);
            }
            case 1: {
                this.ensureSpinner();
                this.mToolbar.addView((View)this.mSpinner, 0);
            }
            case 0: {
                return;
            }
            case 2: {
                if (this.mTabView == null) return;
                this.mToolbar.addView(this.mTabView, 0);
                Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams)this.mTabView.getLayoutParams();
                layoutParams.width = -2;
                layoutParams.height = -2;
                layoutParams.gravity = 8388691;
                return;
            }
        }
    }

    @Override
    public void setSubtitle(CharSequence charSequence) {
        this.mSubtitle = charSequence;
        if ((this.mDisplayOpts & 8) != 0) {
            this.mToolbar.setSubtitle(charSequence);
        }
    }

    @Override
    public void setTitle(CharSequence charSequence) {
        this.mTitleSet = true;
        this.setTitleInt(charSequence);
    }

    @Override
    public void setVisibility(int n2) {
        this.mToolbar.setVisibility(n2);
    }

    @Override
    public void setWindowCallback(Window.Callback callback) {
        this.mWindowCallback = callback;
    }

    @Override
    public void setWindowTitle(CharSequence charSequence) {
        if (!this.mTitleSet) {
            this.setTitleInt(charSequence);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public ViewPropertyAnimatorCompat setupAnimatorToVisibility(final int n2, long l2) {
        float f2;
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = ViewCompat.animate((View)this.mToolbar);
        if (n2 == 0) {
            f2 = 1.0f;
            return viewPropertyAnimatorCompat.alpha(f2).setDuration(l2).setListener(new ViewPropertyAnimatorListenerAdapter(){
                private boolean mCanceled = false;

                @Override
                public void onAnimationCancel(View view) {
                    this.mCanceled = true;
                }

                @Override
                public void onAnimationEnd(View view) {
                    if (!this.mCanceled) {
                        ToolbarWidgetWrapper.this.mToolbar.setVisibility(n2);
                    }
                }

                @Override
                public void onAnimationStart(View view) {
                    ToolbarWidgetWrapper.this.mToolbar.setVisibility(0);
                }
            });
        }
        f2 = 0.0f;
        return viewPropertyAnimatorCompat.alpha(f2).setDuration(l2).setListener(new /* invalid duplicate definition of identical inner class */);
    }

    @Override
    public boolean showOverflowMenu() {
        return this.mToolbar.showOverflowMenu();
    }
}

