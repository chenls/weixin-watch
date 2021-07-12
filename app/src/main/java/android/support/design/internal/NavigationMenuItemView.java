/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.StateListDrawable
 *  android.util.AttributeSet
 *  android.util.TypedValue
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewStub
 *  android.widget.CheckedTextView
 *  android.widget.FrameLayout
 *  android.widget.TextView
 */
package android.support.design.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.design.R;
import android.support.design.internal.ForegroundLinearLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.TextView;

public class NavigationMenuItemView
extends ForegroundLinearLayout
implements MenuView.ItemView {
    private static final int[] CHECKED_STATE_SET = new int[]{0x10100A0};
    private FrameLayout mActionArea;
    private final int mIconSize;
    private ColorStateList mIconTintList;
    private MenuItemImpl mItemData;
    private final CheckedTextView mTextView;

    public NavigationMenuItemView(Context context) {
        this(context, null);
    }

    public NavigationMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NavigationMenuItemView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.setOrientation(0);
        LayoutInflater.from((Context)context).inflate(R.layout.design_navigation_menu_item, (ViewGroup)this, true);
        this.mIconSize = context.getResources().getDimensionPixelSize(R.dimen.design_navigation_icon_size);
        this.mTextView = (CheckedTextView)this.findViewById(R.id.design_menu_item_text);
        this.mTextView.setDuplicateParentStateEnabled(true);
    }

    private StateListDrawable createDefaultBackground() {
        TypedValue typedValue = new TypedValue();
        if (this.getContext().getTheme().resolveAttribute(R.attr.colorControlHighlight, typedValue, true)) {
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(CHECKED_STATE_SET, (Drawable)new ColorDrawable(typedValue.data));
            stateListDrawable.addState(EMPTY_STATE_SET, (Drawable)new ColorDrawable(0));
            return stateListDrawable;
        }
        return null;
    }

    private void setActionView(View view) {
        if (this.mActionArea == null) {
            this.mActionArea = (FrameLayout)((ViewStub)this.findViewById(R.id.design_menu_item_action_area_stub)).inflate();
        }
        this.mActionArea.removeAllViews();
        if (view != null) {
            this.mActionArea.addView(view);
        }
    }

    @Override
    public MenuItemImpl getItemData() {
        return this.mItemData;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void initialize(MenuItemImpl menuItemImpl, int n2) {
        this.mItemData = menuItemImpl;
        n2 = menuItemImpl.isVisible() ? 0 : 8;
        this.setVisibility(n2);
        if (this.getBackground() == null) {
            this.setBackgroundDrawable((Drawable)this.createDefaultBackground());
        }
        this.setCheckable(menuItemImpl.isCheckable());
        this.setChecked(menuItemImpl.isChecked());
        this.setEnabled(menuItemImpl.isEnabled());
        this.setTitle(menuItemImpl.getTitle());
        this.setIcon(menuItemImpl.getIcon());
        this.setActionView(menuItemImpl.getActionView());
    }

    protected int[] onCreateDrawableState(int n2) {
        int[] nArray = super.onCreateDrawableState(n2 + 1);
        if (this.mItemData != null && this.mItemData.isCheckable() && this.mItemData.isChecked()) {
            NavigationMenuItemView.mergeDrawableStates((int[])nArray, (int[])CHECKED_STATE_SET);
        }
        return nArray;
    }

    @Override
    public boolean prefersCondensedTitle() {
        return false;
    }

    public void recycle() {
        if (this.mActionArea != null) {
            this.mActionArea.removeAllViews();
        }
        this.mTextView.setCompoundDrawables(null, null, null, null);
    }

    @Override
    public void setCheckable(boolean bl2) {
        this.refreshDrawableState();
    }

    @Override
    public void setChecked(boolean bl2) {
        this.refreshDrawableState();
        this.mTextView.setChecked(bl2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setIcon(Drawable drawable2) {
        Drawable drawable3 = drawable2;
        if (drawable2 != null) {
            drawable3 = drawable2.getConstantState();
            if (drawable3 != null) {
                drawable2 = drawable3.newDrawable();
            }
            drawable3 = DrawableCompat.wrap(drawable2).mutate();
            drawable3.setBounds(0, 0, this.mIconSize, this.mIconSize);
            DrawableCompat.setTintList(drawable3, this.mIconTintList);
        }
        TextViewCompat.setCompoundDrawablesRelative((TextView)this.mTextView, drawable3, null, null, null);
    }

    void setIconTintList(ColorStateList colorStateList) {
        this.mIconTintList = colorStateList;
        if (this.mItemData != null) {
            this.setIcon(this.mItemData.getIcon());
        }
    }

    @Override
    public void setShortcut(boolean bl2, char c2) {
    }

    public void setTextAppearance(Context context, int n2) {
        this.mTextView.setTextAppearance(context, n2);
    }

    public void setTextColor(ColorStateList colorStateList) {
        this.mTextView.setTextColor(colorStateList);
    }

    @Override
    public void setTitle(CharSequence charSequence) {
        this.mTextView.setText(charSequence);
    }

    @Override
    public boolean showsIcon() {
        return true;
    }
}

