/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package android.support.design.internal;

import android.content.Context;
import android.support.design.internal.NavigationMenu;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.SubMenuBuilder;

public class NavigationSubMenu
extends SubMenuBuilder {
    public NavigationSubMenu(Context context, NavigationMenu navigationMenu, MenuItemImpl menuItemImpl) {
        super(context, navigationMenu, menuItemImpl);
    }

    @Override
    public void onItemsChanged(boolean bl2) {
        super.onItemsChanged(bl2);
        ((MenuBuilder)this.getParentMenu()).onItemsChanged(bl2);
    }
}

