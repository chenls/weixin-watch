/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Parcelable
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnKeyListener
 *  android.view.ViewGroup
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.BaseAdapter
 *  android.widget.FrameLayout
 *  android.widget.ListAdapter
 *  android.widget.PopupWindow$OnDismissListener
 */
package android.support.v7.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcelable;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.ListMenuItemView;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.ListPopupWindow;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import java.util.ArrayList;

public class MenuPopupHelper
implements AdapterView.OnItemClickListener,
View.OnKeyListener,
ViewTreeObserver.OnGlobalLayoutListener,
PopupWindow.OnDismissListener,
MenuPresenter {
    static final int ITEM_LAYOUT = R.layout.abc_popup_menu_item_layout;
    private static final String TAG = "MenuPopupHelper";
    private final MenuAdapter mAdapter;
    private View mAnchorView;
    private int mContentWidth;
    private final Context mContext;
    private int mDropDownGravity = 0;
    boolean mForceShowIcon;
    private boolean mHasContentWidth;
    private final LayoutInflater mInflater;
    private ViewGroup mMeasureParent;
    private final MenuBuilder mMenu;
    private final boolean mOverflowOnly;
    private ListPopupWindow mPopup;
    private final int mPopupMaxWidth;
    private final int mPopupStyleAttr;
    private final int mPopupStyleRes;
    private MenuPresenter.Callback mPresenterCallback;
    private ViewTreeObserver mTreeObserver;

    public MenuPopupHelper(Context context, MenuBuilder menuBuilder) {
        this(context, menuBuilder, null, false, R.attr.popupMenuStyle);
    }

    public MenuPopupHelper(Context context, MenuBuilder menuBuilder, View view) {
        this(context, menuBuilder, view, false, R.attr.popupMenuStyle);
    }

    public MenuPopupHelper(Context context, MenuBuilder menuBuilder, View view, boolean bl2, int n2) {
        this(context, menuBuilder, view, bl2, n2, 0);
    }

    public MenuPopupHelper(Context context, MenuBuilder menuBuilder, View view, boolean bl2, int n2, int n3) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from((Context)context);
        this.mMenu = menuBuilder;
        this.mAdapter = new MenuAdapter(this.mMenu);
        this.mOverflowOnly = bl2;
        this.mPopupStyleAttr = n2;
        this.mPopupStyleRes = n3;
        Resources resources = context.getResources();
        this.mPopupMaxWidth = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
        this.mAnchorView = view;
        menuBuilder.addMenuPresenter(this, context);
    }

    private int measureContentWidth() {
        int n2 = 0;
        View view = null;
        int n3 = 0;
        MenuAdapter menuAdapter = this.mAdapter;
        int n4 = View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
        int n5 = View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
        int n6 = menuAdapter.getCount();
        int n7 = 0;
        while (true) {
            int n8;
            int n9;
            block8: {
                block7: {
                    n9 = n2;
                    if (n7 >= n6) break block7;
                    n8 = menuAdapter.getItemViewType(n7);
                    n9 = n3;
                    if (n8 != n3) {
                        n9 = n8;
                        view = null;
                    }
                    if (this.mMeasureParent == null) {
                        this.mMeasureParent = new FrameLayout(this.mContext);
                    }
                    view = menuAdapter.getView(n7, view, this.mMeasureParent);
                    view.measure(n4, n5);
                    n3 = view.getMeasuredWidth();
                    if (n3 < this.mPopupMaxWidth) break block8;
                    n9 = this.mPopupMaxWidth;
                }
                return n9;
            }
            n8 = n2;
            if (n3 > n2) {
                n8 = n3;
            }
            ++n7;
            n3 = n9;
            n2 = n8;
        }
    }

    @Override
    public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    public void dismiss() {
        if (this.isShowing()) {
            this.mPopup.dismiss();
        }
    }

    @Override
    public boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    @Override
    public boolean flagActionItems() {
        return false;
    }

    public int getGravity() {
        return this.mDropDownGravity;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public MenuView getMenuView(ViewGroup viewGroup) {
        throw new UnsupportedOperationException("MenuPopupHelpers manage their own views");
    }

    public ListPopupWindow getPopup() {
        return this.mPopup;
    }

    @Override
    public void initForMenu(Context context, MenuBuilder menuBuilder) {
    }

    public boolean isShowing() {
        return this.mPopup != null && this.mPopup.isShowing();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onCloseMenu(MenuBuilder menuBuilder, boolean bl2) {
        block3: {
            block2: {
                if (menuBuilder != this.mMenu) break block2;
                this.dismiss();
                if (this.mPresenterCallback != null) break block3;
            }
            return;
        }
        this.mPresenterCallback.onCloseMenu(menuBuilder, bl2);
    }

    public void onDismiss() {
        this.mPopup = null;
        this.mMenu.close();
        if (this.mTreeObserver != null) {
            if (!this.mTreeObserver.isAlive()) {
                this.mTreeObserver = this.mAnchorView.getViewTreeObserver();
            }
            this.mTreeObserver.removeGlobalOnLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
            this.mTreeObserver = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onGlobalLayout() {
        if (!this.isShowing()) return;
        View view = this.mAnchorView;
        if (view == null || !view.isShown()) {
            this.dismiss();
            return;
        } else {
            if (!this.isShowing()) return;
            this.mPopup.show();
            return;
        }
    }

    public void onItemClick(AdapterView<?> object, View view, int n2, long l2) {
        object = this.mAdapter;
        ((MenuAdapter)object).mAdapterMenu.performItemAction(((MenuAdapter)((Object)object)).getItem(n2), 0);
    }

    public boolean onKey(View view, int n2, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1 && n2 == 82) {
            this.dismiss();
            return true;
        }
        return false;
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
        block5: {
            if (!subMenuBuilder.hasVisibleItems()) break block5;
            MenuPopupHelper menuPopupHelper = new MenuPopupHelper(this.mContext, subMenuBuilder, this.mAnchorView);
            menuPopupHelper.setCallback(this.mPresenterCallback);
            boolean bl2 = false;
            int n2 = subMenuBuilder.size();
            int n3 = 0;
            while (true) {
                block7: {
                    boolean bl3;
                    block6: {
                        bl3 = bl2;
                        if (n3 >= n2) break block6;
                        MenuItem menuItem = subMenuBuilder.getItem(n3);
                        if (!menuItem.isVisible() || menuItem.getIcon() == null) break block7;
                        bl3 = true;
                    }
                    menuPopupHelper.setForceShowIcon(bl3);
                    if (!menuPopupHelper.tryShow()) break;
                    if (this.mPresenterCallback != null) {
                        this.mPresenterCallback.onOpenSubMenu(subMenuBuilder);
                    }
                    return true;
                }
                ++n3;
            }
        }
        return false;
    }

    public void setAnchorView(View view) {
        this.mAnchorView = view;
    }

    @Override
    public void setCallback(MenuPresenter.Callback callback) {
        this.mPresenterCallback = callback;
    }

    public void setForceShowIcon(boolean bl2) {
        this.mForceShowIcon = bl2;
    }

    public void setGravity(int n2) {
        this.mDropDownGravity = n2;
    }

    public void show() {
        if (!this.tryShow()) {
            throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
        }
    }

    public boolean tryShow() {
        boolean bl2 = false;
        this.mPopup = new ListPopupWindow(this.mContext, null, this.mPopupStyleAttr, this.mPopupStyleRes);
        this.mPopup.setOnDismissListener(this);
        this.mPopup.setOnItemClickListener(this);
        this.mPopup.setAdapter((ListAdapter)this.mAdapter);
        this.mPopup.setModal(true);
        View view = this.mAnchorView;
        if (view != null) {
            if (this.mTreeObserver == null) {
                bl2 = true;
            }
            this.mTreeObserver = view.getViewTreeObserver();
            if (bl2) {
                this.mTreeObserver.addOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
            }
            this.mPopup.setAnchorView(view);
            this.mPopup.setDropDownGravity(this.mDropDownGravity);
            if (!this.mHasContentWidth) {
                this.mContentWidth = this.measureContentWidth();
                this.mHasContentWidth = true;
            }
            this.mPopup.setContentWidth(this.mContentWidth);
            this.mPopup.setInputMethodMode(2);
            this.mPopup.show();
            this.mPopup.getListView().setOnKeyListener((View.OnKeyListener)this);
            return true;
        }
        return false;
    }

    @Override
    public void updateMenuView(boolean bl2) {
        this.mHasContentWidth = false;
        if (this.mAdapter != null) {
            this.mAdapter.notifyDataSetChanged();
        }
    }

    private class MenuAdapter
    extends BaseAdapter {
        private MenuBuilder mAdapterMenu;
        private int mExpandedIndex = -1;

        public MenuAdapter(MenuBuilder menuBuilder) {
            this.mAdapterMenu = menuBuilder;
            this.findExpandedIndex();
        }

        void findExpandedIndex() {
            MenuItemImpl menuItemImpl = MenuPopupHelper.this.mMenu.getExpandedItem();
            if (menuItemImpl != null) {
                ArrayList<MenuItemImpl> arrayList = MenuPopupHelper.this.mMenu.getNonActionItems();
                int n2 = arrayList.size();
                for (int i2 = 0; i2 < n2; ++i2) {
                    if (arrayList.get(i2) != menuItemImpl) continue;
                    this.mExpandedIndex = i2;
                    return;
                }
            }
            this.mExpandedIndex = -1;
        }

        /*
         * Enabled aggressive block sorting
         */
        public int getCount() {
            ArrayList<MenuItemImpl> arrayList = MenuPopupHelper.this.mOverflowOnly ? this.mAdapterMenu.getNonActionItems() : this.mAdapterMenu.getVisibleItems();
            if (this.mExpandedIndex < 0) {
                return arrayList.size();
            }
            return arrayList.size() - 1;
        }

        /*
         * Enabled aggressive block sorting
         */
        public MenuItemImpl getItem(int n2) {
            ArrayList<MenuItemImpl> arrayList = MenuPopupHelper.this.mOverflowOnly ? this.mAdapterMenu.getNonActionItems() : this.mAdapterMenu.getVisibleItems();
            int n3 = n2;
            if (this.mExpandedIndex >= 0) {
                n3 = n2;
                if (n2 >= this.mExpandedIndex) {
                    n3 = n2 + 1;
                }
            }
            return arrayList.get(n3);
        }

        public long getItemId(int n2) {
            return n2;
        }

        public View getView(int n2, View object, ViewGroup viewGroup) {
            View view = object;
            if (object == null) {
                view = MenuPopupHelper.this.mInflater.inflate(ITEM_LAYOUT, viewGroup, false);
            }
            object = (MenuView.ItemView)view;
            if (MenuPopupHelper.this.mForceShowIcon) {
                ((ListMenuItemView)view).setForceShowIcon(true);
            }
            object.initialize(this.getItem(n2), 0);
            return view;
        }

        public void notifyDataSetChanged() {
            this.findExpandedIndex();
            super.notifyDataSetChanged();
        }
    }
}

