/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.ActivityNotFoundException
 *  android.content.Intent
 *  android.graphics.drawable.Drawable
 *  android.util.Log
 *  android.view.ActionProvider
 *  android.view.ContextMenu$ContextMenuInfo
 *  android.view.MenuItem
 *  android.view.MenuItem$OnActionExpandListener
 *  android.view.MenuItem$OnMenuItemClickListener
 *  android.view.SubMenu
 *  android.view.View
 */
package ticwear.design.internal.view.menu;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import ticwear.design.internal.view.menu.MenuBuilder;

public class MenuItemImpl
implements MenuItem {
    static final int NO_ICON = 0;
    private static final String TAG = "TicMenuItemImpl";
    private MenuItem.OnMenuItemClickListener mClickListener;
    private int mGroupId;
    private Drawable mIconDrawable;
    private int mIconResId = 0;
    private int mId;
    private Intent mIntent;
    private MenuBuilder mMenu;
    private int mOrder;
    private CharSequence mTitle;

    MenuItemImpl(MenuBuilder menuBuilder, int n2, int n3, int n4, CharSequence charSequence) {
        this.mMenu = menuBuilder;
        this.mGroupId = n2;
        this.mId = n3;
        this.mOrder = n4;
        this.mTitle = charSequence;
    }

    public boolean collapseActionView() {
        return false;
    }

    public boolean expandActionView() {
        return false;
    }

    public ActionProvider getActionProvider() {
        return null;
    }

    public View getActionView() {
        return null;
    }

    public char getAlphabeticShortcut() {
        return '\u0000';
    }

    public int getGroupId() {
        return this.mGroupId;
    }

    public Drawable getIcon() {
        if (this.mIconDrawable != null) {
            return this.mIconDrawable;
        }
        if (this.mIconResId != 0) {
            Drawable drawable2 = this.mMenu.getContext().getDrawable(this.mIconResId);
            this.mIconResId = 0;
            this.mIconDrawable = drawable2;
            return drawable2;
        }
        return null;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public int getItemId() {
        return this.mId;
    }

    public ContextMenu.ContextMenuInfo getMenuInfo() {
        return null;
    }

    public char getNumericShortcut() {
        return '\u0000';
    }

    public int getOrder() {
        return this.mOrder;
    }

    public SubMenu getSubMenu() {
        return null;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public CharSequence getTitleCondensed() {
        return this.mTitle;
    }

    public boolean hasSubMenu() {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean invoke() {
        if (this.mClickListener != null && this.mClickListener.onMenuItemClick((MenuItem)this) || this.mMenu.dispatchMenuItemSelected(this.mMenu, this)) {
            return true;
        }
        if (this.mIntent != null) {
            try {
                this.mMenu.getContext().startActivity(this.mIntent);
                return true;
            }
            catch (ActivityNotFoundException activityNotFoundException) {
                Log.e((String)TAG, (String)"Can't find activity to handle intent; ignoring", (Throwable)activityNotFoundException);
            }
        }
        return false;
    }

    public boolean isActionViewExpanded() {
        return false;
    }

    public boolean isCheckable() {
        return false;
    }

    public boolean isChecked() {
        return false;
    }

    public boolean isEnabled() {
        return true;
    }

    public boolean isVisible() {
        return true;
    }

    public MenuItem setActionProvider(ActionProvider actionProvider) {
        return this;
    }

    public MenuItem setActionView(int n2) {
        return this;
    }

    public MenuItem setActionView(View view) {
        return this;
    }

    public MenuItem setAlphabeticShortcut(char c2) {
        return this;
    }

    public MenuItem setCheckable(boolean bl2) {
        return this;
    }

    public MenuItem setChecked(boolean bl2) {
        return this;
    }

    public MenuItem setEnabled(boolean bl2) {
        return this;
    }

    public MenuItem setIcon(int n2) {
        this.mIconDrawable = null;
        this.mIconResId = n2;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public MenuItem setIcon(Drawable drawable2) {
        this.mIconDrawable = drawable2;
        this.mIconResId = 0;
        return this;
    }

    public MenuItem setIntent(Intent intent) {
        this.mIntent = intent;
        return this;
    }

    public MenuItem setNumericShortcut(char c2) {
        return this;
    }

    public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        return this;
    }

    public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        this.mClickListener = onMenuItemClickListener;
        return this;
    }

    public MenuItem setShortcut(char c2, char c3) {
        return this;
    }

    public void setShowAsAction(int n2) {
    }

    public MenuItem setShowAsActionFlags(int n2) {
        return this;
    }

    public MenuItem setTitle(int n2) {
        this.mTitle = this.mMenu.getContext().getString(n2);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public MenuItem setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public MenuItem setTitleCondensed(CharSequence charSequence) {
        return this;
    }

    public MenuItem setVisible(boolean bl2) {
        return this;
    }
}

