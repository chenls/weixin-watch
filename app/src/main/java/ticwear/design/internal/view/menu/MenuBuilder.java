/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.res.Resources
 *  android.view.KeyEvent
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.SubMenu
 */
package ticwear.design.internal.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import java.util.ArrayList;
import java.util.List;
import ticwear.design.internal.view.menu.MenuItemImpl;

public class MenuBuilder
implements Menu {
    private Callback mCallback;
    private final Context mContext;
    private ArrayList<MenuItemImpl> mItems;
    private final Resources mResources;

    public MenuBuilder(Context context) {
        this.mContext = context;
        this.mResources = context.getResources();
        this.mItems = new ArrayList();
    }

    private MenuItem addInternal(int n2, int n3, int n4, CharSequence object) {
        object = this.createNewMenuItem(n2, n3, n4, (CharSequence)object);
        this.mItems.add(MenuBuilder.findInsertIndex(this.mItems, n4), (MenuItemImpl)object);
        this.onItemsChanged(true);
        return object;
    }

    private MenuItemImpl createNewMenuItem(int n2, int n3, int n4, CharSequence charSequence) {
        return new MenuItemImpl(this, n2, n3, n4, charSequence);
    }

    private static int findInsertIndex(ArrayList<MenuItemImpl> arrayList, int n2) {
        for (int i2 = arrayList.size() - 1; i2 >= 0; --i2) {
            if (arrayList.get(i2).getOrder() > n2) continue;
            return i2 + 1;
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void removeItemAtInt(int n2, boolean bl2) {
        block3: {
            block2: {
                if (n2 < 0 || n2 >= this.mItems.size()) break block2;
                this.mItems.remove(n2);
                if (bl2) break block3;
            }
            return;
        }
        this.onItemsChanged(true);
    }

    public MenuItem add(int n2) {
        return this.addInternal(0, 0, 0, this.mResources.getString(n2));
    }

    public MenuItem add(int n2, int n3, int n4, int n5) {
        return this.addInternal(n2, n3, n4, this.mResources.getString(n5));
    }

    public MenuItem add(int n2, int n3, int n4, CharSequence charSequence) {
        return this.addInternal(n2, n3, n4, charSequence);
    }

    public MenuItem add(CharSequence charSequence) {
        return this.addInternal(0, 0, 0, charSequence);
    }

    /*
     * Enabled aggressive block sorting
     */
    public int addIntentOptions(int n2, int n3, int n4, ComponentName componentName, Intent[] intentArray, Intent intent, int n5, MenuItem[] menuItemArray) {
        PackageManager packageManager = this.mContext.getPackageManager();
        List list = packageManager.queryIntentActivityOptions(componentName, intentArray, intent, 0);
        int n6 = list != null ? list.size() : 0;
        if ((n5 & 1) == 0) {
            this.removeGroup(n2);
        }
        n5 = 0;
        while (n5 < n6) {
            ResolveInfo resolveInfo = (ResolveInfo)list.get(n5);
            componentName = resolveInfo.specificIndex < 0 ? intent : intentArray[resolveInfo.specificIndex];
            componentName = new Intent((Intent)componentName);
            componentName.setComponent(new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName, resolveInfo.activityInfo.name));
            componentName = this.add(n2, n3, n4, resolveInfo.loadLabel(packageManager)).setIcon(resolveInfo.loadIcon(packageManager)).setIntent((Intent)componentName);
            if (menuItemArray != null && resolveInfo.specificIndex >= 0) {
                menuItemArray[resolveInfo.specificIndex] = componentName;
            }
            ++n5;
        }
        return n6;
    }

    public SubMenu addSubMenu(int n2) {
        return null;
    }

    public SubMenu addSubMenu(int n2, int n3, int n4, int n5) {
        return null;
    }

    public SubMenu addSubMenu(int n2, int n3, int n4, CharSequence charSequence) {
        return null;
    }

    public SubMenu addSubMenu(CharSequence charSequence) {
        return null;
    }

    public void clear() {
        this.mItems.clear();
        this.onItemsChanged(true);
    }

    public void close() {
        this.dispatchMenuClosed(this);
    }

    void dispatchMenuClosed(MenuBuilder menuBuilder) {
        if (this.mCallback != null) {
            this.mCallback.onMenuClosed(menuBuilder);
        }
    }

    boolean dispatchMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        return this.mCallback != null && this.mCallback.onMenuItemSelected(menuBuilder, menuItem);
    }

    public int findGroupIndex(int n2) {
        return this.findGroupIndex(n2, 0);
    }

    public int findGroupIndex(int n2, int n3) {
        int n4 = this.size();
        int n5 = n3;
        if (n3 < 0) {
            n5 = 0;
        }
        while (n5 < n4) {
            if (this.mItems.get(n5).getGroupId() == n2) {
                return n5;
            }
            ++n5;
        }
        return -1;
    }

    public MenuItem findItem(int n2) {
        int n3 = this.size();
        for (int i2 = 0; i2 < n3; ++i2) {
            MenuItemImpl menuItemImpl = this.mItems.get(i2);
            if (menuItemImpl.getItemId() == n2) {
                return menuItemImpl;
            }
            if (!menuItemImpl.hasSubMenu() || (menuItemImpl = menuItemImpl.getSubMenu().findItem(n2)) == null) continue;
            return menuItemImpl;
        }
        return null;
    }

    public int findItemIndex(int n2) {
        int n3 = this.size();
        for (int i2 = 0; i2 < n3; ++i2) {
            if (this.mItems.get(i2).getItemId() != n2) continue;
            return i2;
        }
        return -1;
    }

    public Context getContext() {
        return this.mContext;
    }

    public MenuItem getItem(int n2) {
        return this.mItems.get(n2);
    }

    public Resources getResources() {
        return this.mResources;
    }

    public boolean hasVisibleItems() {
        return !this.mItems.isEmpty();
    }

    public boolean isShortcutKey(int n2, KeyEvent keyEvent) {
        return false;
    }

    public void onItemsChanged(boolean bl2) {
    }

    public boolean performIdentifierAction(int n2, int n3) {
        return false;
    }

    public boolean performItemAction(MenuItem menuItem) {
        return (menuItem = (MenuItemImpl)menuItem) != null && menuItem.isEnabled() && menuItem.invoke();
    }

    public boolean performShortcut(int n2, KeyEvent keyEvent, int n3) {
        return false;
    }

    public void removeGroup(int n2) {
        int n3 = this.findGroupIndex(n2);
        if (n3 >= 0) {
            int n4 = this.mItems.size();
            for (int i2 = 0; i2 < n4 - n3 && this.mItems.get(n3).getGroupId() == n2; ++i2) {
                this.removeItemAtInt(n3, false);
            }
            this.onItemsChanged(true);
        }
    }

    public void removeItem(int n2) {
        this.removeItemAtInt(this.findItemIndex(n2), true);
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public void setGroupCheckable(int n2, boolean bl2, boolean bl3) {
    }

    public void setGroupEnabled(int n2, boolean bl2) {
    }

    public void setGroupVisible(int n2, boolean bl2) {
    }

    public void setQwertyMode(boolean bl2) {
    }

    public int size() {
        return this.mItems.size();
    }

    public static interface Callback {
        public void onMenuClosed(MenuBuilder var1);

        public boolean onMenuItemSelected(MenuBuilder var1, MenuItem var2);
    }
}

