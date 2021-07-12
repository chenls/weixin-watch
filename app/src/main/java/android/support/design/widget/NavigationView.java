/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.TypedValue
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 */
package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.design.R;
import android.support.design.internal.NavigationMenu;
import android.support.design.internal.NavigationMenuPresenter;
import android.support.design.internal.ScrimInsetsFrameLayout;
import android.support.design.widget.ThemeUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.view.ViewCompat;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class NavigationView
extends ScrimInsetsFrameLayout {
    private static final int[] CHECKED_STATE_SET = new int[]{0x10100A0};
    private static final int[] DISABLED_STATE_SET = new int[]{-16842910};
    private static final int PRESENTER_NAVIGATION_VIEW_ID = 1;
    private OnNavigationItemSelectedListener mListener;
    private int mMaxWidth;
    private final NavigationMenu mMenu;
    private MenuInflater mMenuInflater;
    private final NavigationMenuPresenter mPresenter = new NavigationMenuPresenter();

    public NavigationView(Context context) {
        this(context, null);
    }

    public NavigationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    public NavigationView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        ThemeUtils.checkAppCompatTheme(context);
        this.mMenu = new NavigationMenu(context);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.NavigationView, n2, R.style.Widget_Design_NavigationView);
        this.setBackgroundDrawable(typedArray.getDrawable(R.styleable.NavigationView_android_background));
        if (typedArray.hasValue(R.styleable.NavigationView_elevation)) {
            ViewCompat.setElevation((View)this, typedArray.getDimensionPixelSize(R.styleable.NavigationView_elevation, 0));
        }
        ViewCompat.setFitsSystemWindows((View)this, typedArray.getBoolean(R.styleable.NavigationView_android_fitsSystemWindows, false));
        this.mMaxWidth = typedArray.getDimensionPixelSize(R.styleable.NavigationView_android_maxWidth, 0);
        ColorStateList colorStateList = typedArray.hasValue(R.styleable.NavigationView_itemIconTint) ? typedArray.getColorStateList(R.styleable.NavigationView_itemIconTint) : this.createDefaultColorStateList(16842808);
        boolean bl2 = false;
        n2 = 0;
        if (typedArray.hasValue(R.styleable.NavigationView_itemTextAppearance)) {
            n2 = typedArray.getResourceId(R.styleable.NavigationView_itemTextAppearance, 0);
            bl2 = true;
        }
        attributeSet = null;
        if (typedArray.hasValue(R.styleable.NavigationView_itemTextColor)) {
            attributeSet = typedArray.getColorStateList(R.styleable.NavigationView_itemTextColor);
        }
        AttributeSet attributeSet2 = attributeSet;
        if (!bl2) {
            attributeSet2 = attributeSet;
            if (attributeSet == null) {
                attributeSet2 = this.createDefaultColorStateList(16842806);
            }
        }
        attributeSet = typedArray.getDrawable(R.styleable.NavigationView_itemBackground);
        this.mMenu.setCallback(new MenuBuilder.Callback(){

            @Override
            public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
                return NavigationView.this.mListener != null && NavigationView.this.mListener.onNavigationItemSelected(menuItem);
            }

            @Override
            public void onMenuModeChange(MenuBuilder menuBuilder) {
            }
        });
        this.mPresenter.setId(1);
        this.mPresenter.initForMenu(context, this.mMenu);
        this.mPresenter.setItemIconTintList(colorStateList);
        if (bl2) {
            this.mPresenter.setItemTextAppearance(n2);
        }
        this.mPresenter.setItemTextColor((ColorStateList)attributeSet2);
        this.mPresenter.setItemBackground((Drawable)attributeSet);
        this.mMenu.addMenuPresenter(this.mPresenter);
        this.addView((View)this.mPresenter.getMenuView((ViewGroup)this));
        if (typedArray.hasValue(R.styleable.NavigationView_menu)) {
            this.inflateMenu(typedArray.getResourceId(R.styleable.NavigationView_menu, 0));
        }
        if (typedArray.hasValue(R.styleable.NavigationView_headerLayout)) {
            this.inflateHeaderView(typedArray.getResourceId(R.styleable.NavigationView_headerLayout, 0));
        }
        typedArray.recycle();
    }

    /*
     * Enabled aggressive block sorting
     */
    private ColorStateList createDefaultColorStateList(int n2) {
        ColorStateList colorStateList;
        TypedValue typedValue;
        block3: {
            block2: {
                typedValue = new TypedValue();
                if (!this.getContext().getTheme().resolveAttribute(n2, typedValue, true)) break block2;
                colorStateList = this.getResources().getColorStateList(typedValue.resourceId);
                if (this.getContext().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true)) break block3;
            }
            return null;
        }
        n2 = typedValue.data;
        int n3 = colorStateList.getDefaultColor();
        int[] nArray = DISABLED_STATE_SET;
        int[] nArray2 = CHECKED_STATE_SET;
        int[] nArray3 = EMPTY_STATE_SET;
        int n4 = colorStateList.getColorForState(DISABLED_STATE_SET, n3);
        return new ColorStateList((int[][])new int[][]{nArray, nArray2, nArray3}, new int[]{n4, n2, n3});
    }

    private MenuInflater getMenuInflater() {
        if (this.mMenuInflater == null) {
            this.mMenuInflater = new SupportMenuInflater(this.getContext());
        }
        return this.mMenuInflater;
    }

    public void addHeaderView(@NonNull View view) {
        this.mPresenter.addHeaderView(view);
    }

    public int getHeaderCount() {
        return this.mPresenter.getHeaderCount();
    }

    public View getHeaderView(int n2) {
        return this.mPresenter.getHeaderView(n2);
    }

    @Nullable
    public Drawable getItemBackground() {
        return this.mPresenter.getItemBackground();
    }

    @Nullable
    public ColorStateList getItemIconTintList() {
        return this.mPresenter.getItemTintList();
    }

    @Nullable
    public ColorStateList getItemTextColor() {
        return this.mPresenter.getItemTextColor();
    }

    public Menu getMenu() {
        return this.mMenu;
    }

    public View inflateHeaderView(@LayoutRes int n2) {
        return this.mPresenter.inflateHeaderView(n2);
    }

    public void inflateMenu(int n2) {
        this.mPresenter.setUpdateSuspended(true);
        this.getMenuInflater().inflate(n2, (Menu)this.mMenu);
        this.mPresenter.setUpdateSuspended(false);
        this.mPresenter.updateMenuView(false);
    }

    @Override
    protected void onInsetsChanged(Rect rect) {
        this.mPresenter.setPaddingTopDefault(rect.top);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n2, int n3) {
        int n4 = n2;
        switch (View.MeasureSpec.getMode((int)n2)) {
            default: {
                n4 = n2;
                break;
            }
            case -2147483648: {
                n4 = View.MeasureSpec.makeMeasureSpec((int)Math.min(View.MeasureSpec.getSize((int)n2), this.mMaxWidth), (int)0x40000000);
            }
            case 0x40000000: {
                break;
            }
            case 0: {
                n4 = View.MeasureSpec.makeMeasureSpec((int)this.mMaxWidth, (int)0x40000000);
            }
        }
        super.onMeasure(n4, n3);
    }

    protected void onRestoreInstanceState(Parcelable object) {
        if (!(object instanceof SavedState)) {
            super.onRestoreInstanceState(object);
            return;
        }
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.mMenu.restorePresenterStates(object.menuState);
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.menuState = new Bundle();
        this.mMenu.savePresenterStates(savedState.menuState);
        return savedState;
    }

    public void removeHeaderView(@NonNull View view) {
        this.mPresenter.removeHeaderView(view);
    }

    public void setCheckedItem(@IdRes int n2) {
        MenuItem menuItem = this.mMenu.findItem(n2);
        if (menuItem != null) {
            this.mPresenter.setCheckedItem((MenuItemImpl)menuItem);
        }
    }

    public void setItemBackground(@Nullable Drawable drawable2) {
        this.mPresenter.setItemBackground(drawable2);
    }

    public void setItemBackgroundResource(@DrawableRes int n2) {
        this.setItemBackground(ContextCompat.getDrawable(this.getContext(), n2));
    }

    public void setItemIconTintList(@Nullable ColorStateList colorStateList) {
        this.mPresenter.setItemIconTintList(colorStateList);
    }

    public void setItemTextAppearance(@StyleRes int n2) {
        this.mPresenter.setItemTextAppearance(n2);
    }

    public void setItemTextColor(@Nullable ColorStateList colorStateList) {
        this.mPresenter.setItemTextColor(colorStateList);
    }

    public void setNavigationItemSelectedListener(OnNavigationItemSelectedListener onNavigationItemSelectedListener) {
        this.mListener = onNavigationItemSelectedListener;
    }

    public static interface OnNavigationItemSelectedListener {
        public boolean onNavigationItemSelected(MenuItem var1);
    }

    public static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<SavedState>(){

            @Override
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState[] newArray(int n2) {
                return new SavedState[n2];
            }
        });
        public Bundle menuState;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel);
            this.menuState = parcel.readBundle(classLoader);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(@NonNull Parcel parcel, int n2) {
            super.writeToParcel(parcel, n2);
            parcel.writeBundle(this.menuState);
        }
    }
}

