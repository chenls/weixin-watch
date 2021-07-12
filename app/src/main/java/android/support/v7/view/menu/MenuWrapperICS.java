/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.view.KeyEvent
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.SubMenu
 */
package android.support.v7.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.internal.view.SupportMenu;
import android.support.v7.view.menu.BaseMenuWrapper;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

class MenuWrapperICS
extends BaseMenuWrapper<SupportMenu>
implements Menu {
    MenuWrapperICS(Context context, SupportMenu supportMenu) {
        super(context, supportMenu);
    }

    public MenuItem add(int n2) {
        return this.getMenuItemWrapper(((SupportMenu)this.mWrappedObject).add(n2));
    }

    public MenuItem add(int n2, int n3, int n4, int n5) {
        return this.getMenuItemWrapper(((SupportMenu)this.mWrappedObject).add(n2, n3, n4, n5));
    }

    public MenuItem add(int n2, int n3, int n4, CharSequence charSequence) {
        return this.getMenuItemWrapper(((SupportMenu)this.mWrappedObject).add(n2, n3, n4, charSequence));
    }

    public MenuItem add(CharSequence charSequence) {
        return this.getMenuItemWrapper(((SupportMenu)this.mWrappedObject).add(charSequence));
    }

    public int addIntentOptions(int n2, int n3, int n4, ComponentName componentName, Intent[] intentArray, Intent intent, int n5, MenuItem[] menuItemArray) {
        MenuItem[] menuItemArray2 = null;
        if (menuItemArray != null) {
            menuItemArray2 = new MenuItem[menuItemArray.length];
        }
        n3 = ((SupportMenu)this.mWrappedObject).addIntentOptions(n2, n3, n4, componentName, intentArray, intent, n5, menuItemArray2);
        if (menuItemArray2 != null) {
            n4 = menuItemArray2.length;
            for (n2 = 0; n2 < n4; ++n2) {
                menuItemArray[n2] = this.getMenuItemWrapper(menuItemArray2[n2]);
            }
        }
        return n3;
    }

    public SubMenu addSubMenu(int n2) {
        return this.getSubMenuWrapper(((SupportMenu)this.mWrappedObject).addSubMenu(n2));
    }

    public SubMenu addSubMenu(int n2, int n3, int n4, int n5) {
        return this.getSubMenuWrapper(((SupportMenu)this.mWrappedObject).addSubMenu(n2, n3, n4, n5));
    }

    public SubMenu addSubMenu(int n2, int n3, int n4, CharSequence charSequence) {
        return this.getSubMenuWrapper(((SupportMenu)this.mWrappedObject).addSubMenu(n2, n3, n4, charSequence));
    }

    public SubMenu addSubMenu(CharSequence charSequence) {
        return this.getSubMenuWrapper(((SupportMenu)this.mWrappedObject).addSubMenu(charSequence));
    }

    public void clear() {
        this.internalClear();
        ((SupportMenu)this.mWrappedObject).clear();
    }

    public void close() {
        ((SupportMenu)this.mWrappedObject).close();
    }

    public MenuItem findItem(int n2) {
        return this.getMenuItemWrapper(((SupportMenu)this.mWrappedObject).findItem(n2));
    }

    public MenuItem getItem(int n2) {
        return this.getMenuItemWrapper(((SupportMenu)this.mWrappedObject).getItem(n2));
    }

    public boolean hasVisibleItems() {
        return ((SupportMenu)this.mWrappedObject).hasVisibleItems();
    }

    public boolean isShortcutKey(int n2, KeyEvent keyEvent) {
        return ((SupportMenu)this.mWrappedObject).isShortcutKey(n2, keyEvent);
    }

    public boolean performIdentifierAction(int n2, int n3) {
        return ((SupportMenu)this.mWrappedObject).performIdentifierAction(n2, n3);
    }

    public boolean performShortcut(int n2, KeyEvent keyEvent, int n3) {
        return ((SupportMenu)this.mWrappedObject).performShortcut(n2, keyEvent, n3);
    }

    public void removeGroup(int n2) {
        this.internalRemoveGroup(n2);
        ((SupportMenu)this.mWrappedObject).removeGroup(n2);
    }

    public void removeItem(int n2) {
        this.internalRemoveItem(n2);
        ((SupportMenu)this.mWrappedObject).removeItem(n2);
    }

    public void setGroupCheckable(int n2, boolean bl2, boolean bl3) {
        ((SupportMenu)this.mWrappedObject).setGroupCheckable(n2, bl2, bl3);
    }

    public void setGroupEnabled(int n2, boolean bl2) {
        ((SupportMenu)this.mWrappedObject).setGroupEnabled(n2, bl2);
    }

    public void setGroupVisible(int n2, boolean bl2) {
        ((SupportMenu)this.mWrappedObject).setGroupVisible(n2, bl2);
    }

    public void setQwertyMode(boolean bl2) {
        ((SupportMenu)this.mWrappedObject).setQwertyMode(bl2);
    }

    public int size() {
        return ((SupportMenu)this.mWrappedObject).size();
    }
}

