/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.SparseArray
 *  android.view.LayoutInflater
 *  android.view.MenuItem
 *  android.view.SubMenu
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package android.support.design.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.design.R;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.internal.NavigationMenuView;
import android.support.design.internal.ParcelableSparseArray;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;

public class NavigationMenuPresenter
implements MenuPresenter {
    private static final String STATE_ADAPTER = "android:menu:adapter";
    private static final String STATE_HIERARCHY = "android:menu:list";
    private NavigationMenuAdapter mAdapter;
    private MenuPresenter.Callback mCallback;
    private LinearLayout mHeaderLayout;
    private ColorStateList mIconTintList;
    private int mId;
    private Drawable mItemBackground;
    private LayoutInflater mLayoutInflater;
    private MenuBuilder mMenu;
    private NavigationMenuView mMenuView;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener(){

        public void onClick(View object) {
            object = (NavigationMenuItemView)object;
            NavigationMenuPresenter.this.setUpdateSuspended(true);
            object = ((NavigationMenuItemView)object).getItemData();
            boolean bl2 = NavigationMenuPresenter.this.mMenu.performItemAction((MenuItem)object, NavigationMenuPresenter.this, 0);
            if (object != null && ((MenuItemImpl)object).isCheckable() && bl2) {
                NavigationMenuPresenter.this.mAdapter.setCheckedItem((MenuItemImpl)object);
            }
            NavigationMenuPresenter.this.setUpdateSuspended(false);
            NavigationMenuPresenter.this.updateMenuView(false);
        }
    };
    private int mPaddingSeparator;
    private int mPaddingTopDefault;
    private int mTextAppearance;
    private boolean mTextAppearanceSet;
    private ColorStateList mTextColor;

    public void addHeaderView(@NonNull View view) {
        this.mHeaderLayout.addView(view);
        this.mMenuView.setPadding(0, 0, 0, this.mMenuView.getPaddingBottom());
    }

    @Override
    public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    @Override
    public boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    @Override
    public boolean flagActionItems() {
        return false;
    }

    public int getHeaderCount() {
        return this.mHeaderLayout.getChildCount();
    }

    public View getHeaderView(int n2) {
        return this.mHeaderLayout.getChildAt(n2);
    }

    @Override
    public int getId() {
        return this.mId;
    }

    @Nullable
    public Drawable getItemBackground() {
        return this.mItemBackground;
    }

    @Nullable
    public ColorStateList getItemTextColor() {
        return this.mTextColor;
    }

    @Nullable
    public ColorStateList getItemTintList() {
        return this.mIconTintList;
    }

    @Override
    public MenuView getMenuView(ViewGroup viewGroup) {
        if (this.mMenuView == null) {
            this.mMenuView = (NavigationMenuView)this.mLayoutInflater.inflate(R.layout.design_navigation_menu, viewGroup, false);
            if (this.mAdapter == null) {
                this.mAdapter = new NavigationMenuAdapter();
            }
            this.mHeaderLayout = (LinearLayout)this.mLayoutInflater.inflate(R.layout.design_navigation_item_header, (ViewGroup)this.mMenuView, false);
            this.mMenuView.setAdapter(this.mAdapter);
        }
        return this.mMenuView;
    }

    public View inflateHeaderView(@LayoutRes int n2) {
        View view = this.mLayoutInflater.inflate(n2, (ViewGroup)this.mHeaderLayout, false);
        this.addHeaderView(view);
        return view;
    }

    @Override
    public void initForMenu(Context context, MenuBuilder menuBuilder) {
        this.mLayoutInflater = LayoutInflater.from((Context)context);
        this.mMenu = menuBuilder;
        this.mPaddingSeparator = context.getResources().getDimensionPixelOffset(R.dimen.design_navigation_separator_vertical_padding);
    }

    @Override
    public void onCloseMenu(MenuBuilder menuBuilder, boolean bl2) {
        if (this.mCallback != null) {
            this.mCallback.onCloseMenu(menuBuilder, bl2);
        }
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        SparseArray sparseArray = (parcelable = (Bundle)parcelable).getSparseParcelableArray(STATE_HIERARCHY);
        if (sparseArray != null) {
            this.mMenuView.restoreHierarchyState(sparseArray);
        }
        if ((parcelable = parcelable.getBundle(STATE_ADAPTER)) != null) {
            this.mAdapter.restoreInstanceState((Bundle)parcelable);
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        if (this.mMenuView != null) {
            SparseArray sparseArray = new SparseArray();
            this.mMenuView.saveHierarchyState(sparseArray);
            bundle.putSparseParcelableArray(STATE_HIERARCHY, sparseArray);
        }
        if (this.mAdapter != null) {
            bundle.putBundle(STATE_ADAPTER, this.mAdapter.createInstanceState());
        }
        return bundle;
    }

    @Override
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        return false;
    }

    public void removeHeaderView(@NonNull View view) {
        this.mHeaderLayout.removeView(view);
        if (this.mHeaderLayout.getChildCount() == 0) {
            this.mMenuView.setPadding(0, this.mPaddingTopDefault, 0, this.mMenuView.getPaddingBottom());
        }
    }

    @Override
    public void setCallback(MenuPresenter.Callback callback) {
        this.mCallback = callback;
    }

    public void setCheckedItem(MenuItemImpl menuItemImpl) {
        this.mAdapter.setCheckedItem(menuItemImpl);
    }

    public void setId(int n2) {
        this.mId = n2;
    }

    public void setItemBackground(@Nullable Drawable drawable2) {
        this.mItemBackground = drawable2;
        this.updateMenuView(false);
    }

    public void setItemIconTintList(@Nullable ColorStateList colorStateList) {
        this.mIconTintList = colorStateList;
        this.updateMenuView(false);
    }

    public void setItemTextAppearance(@StyleRes int n2) {
        this.mTextAppearance = n2;
        this.mTextAppearanceSet = true;
        this.updateMenuView(false);
    }

    public void setItemTextColor(@Nullable ColorStateList colorStateList) {
        this.mTextColor = colorStateList;
        this.updateMenuView(false);
    }

    public void setPaddingTopDefault(int n2) {
        if (this.mPaddingTopDefault != n2) {
            this.mPaddingTopDefault = n2;
            if (this.mHeaderLayout.getChildCount() == 0) {
                this.mMenuView.setPadding(0, this.mPaddingTopDefault, 0, this.mMenuView.getPaddingBottom());
            }
        }
    }

    public void setUpdateSuspended(boolean bl2) {
        if (this.mAdapter != null) {
            this.mAdapter.setUpdateSuspended(bl2);
        }
    }

    @Override
    public void updateMenuView(boolean bl2) {
        if (this.mAdapter != null) {
            this.mAdapter.update();
        }
    }

    private static class HeaderViewHolder
    extends ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
        }
    }

    private class NavigationMenuAdapter
    extends RecyclerView.Adapter<ViewHolder> {
        private static final String STATE_ACTION_VIEWS = "android:menu:action_views";
        private static final String STATE_CHECKED_ITEM = "android:menu:checked";
        private static final int VIEW_TYPE_HEADER = 3;
        private static final int VIEW_TYPE_NORMAL = 0;
        private static final int VIEW_TYPE_SEPARATOR = 2;
        private static final int VIEW_TYPE_SUBHEADER = 1;
        private MenuItemImpl mCheckedItem;
        private final ArrayList<NavigationMenuItem> mItems = new ArrayList();
        private ColorDrawable mTransparentIcon;
        private boolean mUpdateSuspended;

        NavigationMenuAdapter() {
            this.prepareMenuItems();
        }

        private void appendTransparentIconIfMissing(int n2, int n3) {
            while (n2 < n3) {
                MenuItemImpl menuItemImpl = ((NavigationMenuTextItem)this.mItems.get(n2)).getMenuItem();
                if (menuItemImpl.getIcon() == null) {
                    if (this.mTransparentIcon == null) {
                        this.mTransparentIcon = new ColorDrawable(0);
                    }
                    menuItemImpl.setIcon((Drawable)this.mTransparentIcon);
                }
                ++n2;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private void prepareMenuItems() {
            if (this.mUpdateSuspended) {
                return;
            }
            this.mUpdateSuspended = true;
            this.mItems.clear();
            this.mItems.add(new NavigationMenuHeaderItem());
            int n2 = -1;
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            int n6 = NavigationMenuPresenter.this.mMenu.getVisibleItems().size();
            while (true) {
                int n7;
                int n8;
                int n9;
                block21: {
                    int n10;
                    MenuItemImpl menuItemImpl;
                    block20: {
                        if (n5 >= n6) {
                            this.mUpdateSuspended = false;
                            return;
                        }
                        menuItemImpl = NavigationMenuPresenter.this.mMenu.getVisibleItems().get(n5);
                        if (menuItemImpl.isChecked()) {
                            this.setCheckedItem(menuItemImpl);
                        }
                        if (menuItemImpl.isCheckable()) {
                            menuItemImpl.setExclusiveCheckable(false);
                        }
                        if (!menuItemImpl.hasSubMenu()) break block20;
                        SubMenu subMenu = menuItemImpl.getSubMenu();
                        n9 = n4;
                        n8 = n2;
                        n7 = n3;
                        if (subMenu.hasVisibleItems()) {
                            if (n5 != 0) {
                                this.mItems.add(new NavigationMenuSeparatorItem(NavigationMenuPresenter.this.mPaddingSeparator, 0));
                            }
                            this.mItems.add(new NavigationMenuTextItem(menuItemImpl));
                            n10 = 0;
                            int n11 = this.mItems.size();
                            n7 = subMenu.size();
                            for (n8 = 0; n8 < n7; ++n8) {
                                MenuItemImpl menuItemImpl2 = (MenuItemImpl)subMenu.getItem(n8);
                                n9 = n10;
                                if (menuItemImpl2.isVisible()) {
                                    n9 = n10;
                                    if (n10 == 0) {
                                        n9 = n10;
                                        if (menuItemImpl2.getIcon() != null) {
                                            n9 = 1;
                                        }
                                    }
                                    if (menuItemImpl2.isCheckable()) {
                                        menuItemImpl2.setExclusiveCheckable(false);
                                    }
                                    if (menuItemImpl.isChecked()) {
                                        this.setCheckedItem(menuItemImpl);
                                    }
                                    this.mItems.add(new NavigationMenuTextItem(menuItemImpl2));
                                }
                                n10 = n9;
                            }
                            n9 = n4;
                            n8 = n2;
                            n7 = n3;
                            if (n10 != 0) {
                                this.appendTransparentIconIfMissing(n11, this.mItems.size());
                                n7 = n3;
                                n8 = n2;
                                n9 = n4;
                            }
                        }
                        break block21;
                    }
                    n8 = menuItemImpl.getGroupId();
                    if (n8 != n2) {
                        n4 = this.mItems.size();
                        n3 = menuItemImpl.getIcon() != null ? 1 : 0;
                        n9 = n3;
                        n10 = n4;
                        if (n5 != 0) {
                            n10 = n4 + 1;
                            this.mItems.add(new NavigationMenuSeparatorItem(NavigationMenuPresenter.this.mPaddingSeparator, NavigationMenuPresenter.this.mPaddingSeparator));
                            n9 = n3;
                        }
                    } else {
                        n9 = n4;
                        n10 = n3;
                        if (n4 == 0) {
                            n9 = n4;
                            n10 = n3;
                            if (menuItemImpl.getIcon() != null) {
                                n9 = 1;
                                this.appendTransparentIconIfMissing(n3, this.mItems.size());
                                n10 = n3;
                            }
                        }
                    }
                    if (n9 != 0 && menuItemImpl.getIcon() == null) {
                        menuItemImpl.setIcon(17170445);
                    }
                    this.mItems.add(new NavigationMenuTextItem(menuItemImpl));
                    n7 = n10;
                }
                ++n5;
                n4 = n9;
                n2 = n8;
                n3 = n7;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        public Bundle createInstanceState() {
            Bundle bundle = new Bundle();
            if (this.mCheckedItem != null) {
                bundle.putInt(STATE_CHECKED_ITEM, this.mCheckedItem.getItemId());
            }
            SparseArray sparseArray = new SparseArray();
            Iterator<NavigationMenuItem> iterator = this.mItems.iterator();
            while (true) {
                MenuItemImpl menuItemImpl;
                if (!iterator.hasNext()) {
                    bundle.putSparseParcelableArray(STATE_ACTION_VIEWS, sparseArray);
                    return bundle;
                }
                NavigationMenuItem navigationMenuItem = iterator.next();
                if (!(navigationMenuItem instanceof NavigationMenuTextItem) || (navigationMenuItem = (menuItemImpl = ((NavigationMenuTextItem)navigationMenuItem).getMenuItem()) != null ? menuItemImpl.getActionView() : null) == null) continue;
                ParcelableSparseArray parcelableSparseArray = new ParcelableSparseArray();
                navigationMenuItem.saveHierarchyState(parcelableSparseArray);
                sparseArray.put(menuItemImpl.getItemId(), (Object)parcelableSparseArray);
            }
        }

        @Override
        public int getItemCount() {
            return this.mItems.size();
        }

        @Override
        public long getItemId(int n2) {
            return n2;
        }

        @Override
        public int getItemViewType(int n2) {
            NavigationMenuItem navigationMenuItem = this.mItems.get(n2);
            if (navigationMenuItem instanceof NavigationMenuSeparatorItem) {
                return 2;
            }
            if (navigationMenuItem instanceof NavigationMenuHeaderItem) {
                return 3;
            }
            if (navigationMenuItem instanceof NavigationMenuTextItem) {
                if (((NavigationMenuTextItem)navigationMenuItem).getMenuItem().hasSubMenu()) {
                    return 1;
                }
                return 0;
            }
            throw new RuntimeException("Unknown item type.");
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int n2) {
            switch (this.getItemViewType(n2)) {
                default: {
                    return;
                }
                case 0: {
                    NavigationMenuItemView navigationMenuItemView = (NavigationMenuItemView)viewHolder.itemView;
                    navigationMenuItemView.setIconTintList(NavigationMenuPresenter.this.mIconTintList);
                    if (NavigationMenuPresenter.this.mTextAppearanceSet) {
                        navigationMenuItemView.setTextAppearance(navigationMenuItemView.getContext(), NavigationMenuPresenter.this.mTextAppearance);
                    }
                    if (NavigationMenuPresenter.this.mTextColor != null) {
                        navigationMenuItemView.setTextColor(NavigationMenuPresenter.this.mTextColor);
                    }
                    viewHolder = NavigationMenuPresenter.this.mItemBackground != null ? NavigationMenuPresenter.this.mItemBackground.getConstantState().newDrawable() : null;
                    navigationMenuItemView.setBackgroundDrawable((Drawable)viewHolder);
                    navigationMenuItemView.initialize(((NavigationMenuTextItem)this.mItems.get(n2)).getMenuItem(), 0);
                    return;
                }
                case 1: {
                    ((TextView)viewHolder.itemView).setText(((NavigationMenuTextItem)this.mItems.get(n2)).getMenuItem().getTitle());
                    return;
                }
                case 2: 
            }
            NavigationMenuSeparatorItem navigationMenuSeparatorItem = (NavigationMenuSeparatorItem)this.mItems.get(n2);
            viewHolder.itemView.setPadding(0, navigationMenuSeparatorItem.getPaddingTop(), 0, navigationMenuSeparatorItem.getPaddingBottom());
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int n2) {
            switch (n2) {
                default: {
                    return null;
                }
                case 0: {
                    return new NormalViewHolder(NavigationMenuPresenter.this.mLayoutInflater, viewGroup, NavigationMenuPresenter.this.mOnClickListener);
                }
                case 1: {
                    return new SubheaderViewHolder(NavigationMenuPresenter.this.mLayoutInflater, viewGroup);
                }
                case 2: {
                    return new SeparatorViewHolder(NavigationMenuPresenter.this.mLayoutInflater, viewGroup);
                }
                case 3: 
            }
            return new HeaderViewHolder((View)NavigationMenuPresenter.this.mHeaderLayout);
        }

        @Override
        public void onViewRecycled(ViewHolder viewHolder) {
            if (viewHolder instanceof NormalViewHolder) {
                ((NavigationMenuItemView)viewHolder.itemView).recycle();
            }
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        public void restoreInstanceState(Bundle object) {
            int n2 = object.getInt(STATE_CHECKED_ITEM, 0);
            if (n2 != 0) {
                this.mUpdateSuspended = true;
                for (NavigationMenuItem navigationMenuItem : this.mItems) {
                    MenuItemImpl menuItemImpl;
                    if (!(navigationMenuItem instanceof NavigationMenuTextItem) || (menuItemImpl = ((NavigationMenuTextItem)navigationMenuItem).getMenuItem()) == null || menuItemImpl.getItemId() != n2) continue;
                    this.setCheckedItem(menuItemImpl);
                    break;
                }
                this.mUpdateSuspended = false;
                this.prepareMenuItems();
            }
            SparseArray sparseArray = object.getSparseParcelableArray(STATE_ACTION_VIEWS);
            Iterator<NavigationMenuItem> iterator = this.mItems.iterator();
            while (iterator.hasNext()) {
                void var1_5;
                NavigationMenuItem navigationMenuItem = iterator.next();
                if (!(navigationMenuItem instanceof NavigationMenuTextItem)) continue;
                MenuItemImpl menuItemImpl = ((NavigationMenuTextItem)navigationMenuItem).getMenuItem();
                if (menuItemImpl != null) {
                    View view = menuItemImpl.getActionView();
                } else {
                    Object var1_6 = null;
                }
                if (var1_5 == null) continue;
                var1_5.restoreHierarchyState((SparseArray)sparseArray.get(menuItemImpl.getItemId()));
            }
            return;
        }

        public void setCheckedItem(MenuItemImpl menuItemImpl) {
            if (this.mCheckedItem == menuItemImpl || !menuItemImpl.isCheckable()) {
                return;
            }
            if (this.mCheckedItem != null) {
                this.mCheckedItem.setChecked(false);
            }
            this.mCheckedItem = menuItemImpl;
            menuItemImpl.setChecked(true);
        }

        public void setUpdateSuspended(boolean bl2) {
            this.mUpdateSuspended = bl2;
        }

        public void update() {
            this.prepareMenuItems();
            this.notifyDataSetChanged();
        }
    }

    private static class NavigationMenuHeaderItem
    implements NavigationMenuItem {
        private NavigationMenuHeaderItem() {
        }
    }

    private static interface NavigationMenuItem {
    }

    private static class NavigationMenuSeparatorItem
    implements NavigationMenuItem {
        private final int mPaddingBottom;
        private final int mPaddingTop;

        public NavigationMenuSeparatorItem(int n2, int n3) {
            this.mPaddingTop = n2;
            this.mPaddingBottom = n3;
        }

        public int getPaddingBottom() {
            return this.mPaddingBottom;
        }

        public int getPaddingTop() {
            return this.mPaddingTop;
        }
    }

    private static class NavigationMenuTextItem
    implements NavigationMenuItem {
        private final MenuItemImpl mMenuItem;

        private NavigationMenuTextItem(MenuItemImpl menuItemImpl) {
            this.mMenuItem = menuItemImpl;
        }

        public MenuItemImpl getMenuItem() {
            return this.mMenuItem;
        }
    }

    private static class NormalViewHolder
    extends ViewHolder {
        public NormalViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup, View.OnClickListener onClickListener) {
            super(layoutInflater.inflate(R.layout.design_navigation_item, viewGroup, false));
            this.itemView.setOnClickListener(onClickListener);
        }
    }

    private static class SeparatorViewHolder
    extends ViewHolder {
        public SeparatorViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.design_navigation_item_separator, viewGroup, false));
        }
    }

    private static class SubheaderViewHolder
    extends ViewHolder {
        public SubheaderViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.design_navigation_item_subheader, viewGroup, false));
        }
    }

    private static abstract class ViewHolder
    extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }
}

