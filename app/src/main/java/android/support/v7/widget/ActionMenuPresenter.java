/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.graphics.drawable.Drawable
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.SparseBooleanArray
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ActionProvider;
import android.support.v7.appcompat.R;
import android.support.v7.transition.ActionBarTransition;
import android.support.v7.view.ActionBarPolicy;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.view.menu.BaseMenuPresenter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.ListPopupWindow;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

class ActionMenuPresenter
extends BaseMenuPresenter
implements ActionProvider.SubUiVisibilityListener {
    private static final String TAG = "ActionMenuPresenter";
    private final SparseBooleanArray mActionButtonGroups = new SparseBooleanArray();
    private ActionButtonSubmenu mActionButtonPopup;
    private int mActionItemWidthLimit;
    private boolean mExpandedActionViewsExclusive;
    private int mMaxItems;
    private boolean mMaxItemsSet;
    private int mMinCellSize;
    int mOpenSubMenuId;
    private OverflowMenuButton mOverflowButton;
    private OverflowPopup mOverflowPopup;
    private Drawable mPendingOverflowIcon;
    private boolean mPendingOverflowIconSet;
    private ActionMenuPopupCallback mPopupCallback;
    final PopupPresenterCallback mPopupPresenterCallback = new PopupPresenterCallback();
    private OpenOverflowRunnable mPostedOpenRunnable;
    private boolean mReserveOverflow;
    private boolean mReserveOverflowSet;
    private View mScrapActionButtonView;
    private boolean mStrictWidthLimit;
    private int mWidthLimit;
    private boolean mWidthLimitSet;

    public ActionMenuPresenter(Context context) {
        super(context, R.layout.abc_action_menu_layout, R.layout.abc_action_menu_item_layout);
    }

    static /* synthetic */ OverflowPopup access$202(ActionMenuPresenter actionMenuPresenter, OverflowPopup overflowPopup) {
        actionMenuPresenter.mOverflowPopup = overflowPopup;
        return overflowPopup;
    }

    static /* synthetic */ OpenOverflowRunnable access$302(ActionMenuPresenter actionMenuPresenter, OpenOverflowRunnable openOverflowRunnable) {
        actionMenuPresenter.mPostedOpenRunnable = openOverflowRunnable;
        return openOverflowRunnable;
    }

    static /* synthetic */ ActionButtonSubmenu access$802(ActionMenuPresenter actionMenuPresenter, ActionButtonSubmenu actionButtonSubmenu) {
        actionMenuPresenter.mActionButtonPopup = actionButtonSubmenu;
        return actionButtonSubmenu;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private View findViewForItem(MenuItem menuItem) {
        ViewGroup viewGroup = (ViewGroup)this.mMenuView;
        if (viewGroup == null) {
            return null;
        }
        int n2 = viewGroup.getChildCount();
        int n3 = 0;
        while (n3 < n2) {
            View view = viewGroup.getChildAt(n3);
            if (view instanceof MenuView.ItemView) {
                View view2 = view;
                if (((MenuView.ItemView)view).getItemData() == menuItem) return view2;
            }
            ++n3;
        }
        return null;
    }

    @Override
    public void bindItemView(MenuItemImpl object, MenuView.ItemView itemView) {
        itemView.initialize((MenuItemImpl)object, 0);
        object = (ActionMenuView)this.mMenuView;
        itemView = (ActionMenuItemView)itemView;
        ((ActionMenuItemView)itemView).setItemInvoker((MenuBuilder.ItemInvoker)object);
        if (this.mPopupCallback == null) {
            this.mPopupCallback = new ActionMenuPopupCallback();
        }
        ((ActionMenuItemView)itemView).setPopupCallback(this.mPopupCallback);
    }

    public boolean dismissPopupMenus() {
        return this.hideOverflowMenu() | this.hideSubMenus();
    }

    @Override
    public boolean filterLeftoverView(ViewGroup viewGroup, int n2) {
        if (viewGroup.getChildAt(n2) == this.mOverflowButton) {
            return false;
        }
        return super.filterLeftoverView(viewGroup, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean flagActionItems() {
        int n2;
        MenuItemImpl menuItemImpl;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        ViewGroup viewGroup;
        int n8;
        int n9;
        int n10;
        int n11;
        ArrayList<MenuItemImpl> arrayList;
        block39: {
            block40: {
                arrayList = this.mMenu.getVisibleItems();
                n11 = arrayList.size();
                n10 = this.mMaxItems;
                n9 = this.mActionItemWidthLimit;
                n8 = View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
                viewGroup = (ViewGroup)this.mMenuView;
                n7 = 0;
                n6 = 0;
                n5 = 0;
                n4 = 0;
                for (n3 = 0; n3 < n11; ++n3) {
                    menuItemImpl = arrayList.get(n3);
                    if (menuItemImpl.requiresActionButton()) {
                        ++n7;
                    } else if (menuItemImpl.requestsActionButton()) {
                        ++n6;
                    } else {
                        n4 = 1;
                    }
                    n2 = n10;
                    if (this.mExpandedActionViewsExclusive) {
                        n2 = n10;
                        if (menuItemImpl.isActionViewExpanded()) {
                            n2 = 0;
                        }
                    }
                    n10 = n2;
                }
                n3 = n10;
                if (!this.mReserveOverflow) break block39;
                if (n4 != 0) break block40;
                n3 = n10;
                if (n7 + n6 <= n10) break block39;
            }
            n3 = n10 - 1;
        }
        n3 -= n7;
        menuItemImpl = this.mActionButtonGroups;
        menuItemImpl.clear();
        int n12 = 0;
        n7 = 0;
        if (this.mStrictWidthLimit) {
            n7 = n9 / this.mMinCellSize;
            n10 = this.mMinCellSize;
            n12 = this.mMinCellSize + n9 % n10 / n7;
        }
        n10 = 0;
        n4 = n9;
        n9 = n10;
        n10 = n5;
        while (n9 < n11) {
            Object object;
            MenuItemImpl menuItemImpl2 = arrayList.get(n9);
            if (menuItemImpl2.requiresActionButton()) {
                object = this.getItemView(menuItemImpl2, this.mScrapActionButtonView, viewGroup);
                if (this.mScrapActionButtonView == null) {
                    this.mScrapActionButtonView = object;
                }
                if (this.mStrictWidthLimit) {
                    n7 -= ActionMenuView.measureChildForCells((View)object, n12, n7, n8, 0);
                } else {
                    object.measure(n8, n8);
                }
                n2 = object.getMeasuredWidth();
                n6 = n4 - n2;
                n4 = n10;
                if (n10 == 0) {
                    n4 = n2;
                }
                if ((n10 = menuItemImpl2.getGroupId()) != 0) {
                    menuItemImpl.put(n10, true);
                }
                menuItemImpl2.setIsActionButton(true);
                n10 = n4;
            } else if (menuItemImpl2.requestsActionButton()) {
                int n13 = menuItemImpl2.getGroupId();
                boolean bl2 = menuItemImpl.get(n13);
                int n14 = !(n3 <= 0 && !bl2 || n4 <= 0 || this.mStrictWidthLimit && n7 <= 0) ? 1 : 0;
                n5 = n7;
                n2 = n10;
                int n15 = n14;
                n6 = n4;
                if (n14 != 0) {
                    object = this.getItemView(menuItemImpl2, this.mScrapActionButtonView, viewGroup);
                    if (this.mScrapActionButtonView == null) {
                        this.mScrapActionButtonView = object;
                    }
                    if (this.mStrictWidthLimit) {
                        n2 = ActionMenuView.measureChildForCells((View)object, n12, n7, n8, 0);
                        n7 = n6 = n7 - n2;
                        if (n2 == 0) {
                            n14 = 0;
                            n7 = n6;
                        }
                    } else {
                        object.measure(n8, n8);
                    }
                    n5 = object.getMeasuredWidth();
                    n6 = n4 - n5;
                    n2 = n10;
                    if (n10 == 0) {
                        n2 = n5;
                    }
                    if (this.mStrictWidthLimit) {
                        n10 = n6 >= 0 ? 1 : 0;
                        n15 = n14 & n10;
                        n5 = n7;
                    } else {
                        n10 = n6 + n2 > 0 ? 1 : 0;
                        n15 = n14 & n10;
                        n5 = n7;
                    }
                }
                if (n15 != 0 && n13 != 0) {
                    menuItemImpl.put(n13, true);
                    n10 = n3;
                } else {
                    n10 = n3;
                    if (bl2) {
                        menuItemImpl.put(n13, false);
                        n7 = 0;
                        while (true) {
                            n10 = n3;
                            if (n7 >= n9) break;
                            object = arrayList.get(n7);
                            n10 = n3;
                            if (((MenuItemImpl)object).getGroupId() == n13) {
                                n10 = n3;
                                if (((MenuItemImpl)object).isActionButton()) {
                                    n10 = n3 + 1;
                                }
                                ((MenuItemImpl)object).setIsActionButton(false);
                            }
                            ++n7;
                            n3 = n10;
                        }
                    }
                }
                n3 = n10;
                if (n15 != 0) {
                    n3 = n10 - 1;
                }
                menuItemImpl2.setIsActionButton(n15 != 0);
                n7 = n5;
                n10 = n2;
            } else {
                menuItemImpl2.setIsActionButton(false);
                n6 = n4;
            }
            ++n9;
            n4 = n6;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public View getItemView(MenuItemImpl object, View view, ViewGroup viewGroup) {
        View view2 = ((MenuItemImpl)object).getActionView();
        if (view2 == null || ((MenuItemImpl)object).hasCollapsibleActionView()) {
            view2 = super.getItemView((MenuItemImpl)object, view, viewGroup);
        }
        int n2 = ((MenuItemImpl)object).isActionViewExpanded() ? 8 : 0;
        view2.setVisibility(n2);
        object = (ActionMenuView)viewGroup;
        view = view2.getLayoutParams();
        if (!((ActionMenuView)object).checkLayoutParams((ViewGroup.LayoutParams)view)) {
            view2.setLayoutParams((ViewGroup.LayoutParams)((ActionMenuView)object).generateLayoutParams((ViewGroup.LayoutParams)view));
        }
        return view2;
    }

    @Override
    public MenuView getMenuView(ViewGroup object) {
        object = super.getMenuView((ViewGroup)object);
        ((ActionMenuView)object).setPresenter(this);
        return object;
    }

    public Drawable getOverflowIcon() {
        if (this.mOverflowButton != null) {
            return this.mOverflowButton.getDrawable();
        }
        if (this.mPendingOverflowIconSet) {
            return this.mPendingOverflowIcon;
        }
        return null;
    }

    public boolean hideOverflowMenu() {
        if (this.mPostedOpenRunnable != null && this.mMenuView != null) {
            ((View)this.mMenuView).removeCallbacks((Runnable)this.mPostedOpenRunnable);
            this.mPostedOpenRunnable = null;
            return true;
        }
        OverflowPopup overflowPopup = this.mOverflowPopup;
        if (overflowPopup != null) {
            overflowPopup.dismiss();
            return true;
        }
        return false;
    }

    public boolean hideSubMenus() {
        if (this.mActionButtonPopup != null) {
            this.mActionButtonPopup.dismiss();
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void initForMenu(Context object, MenuBuilder menuBuilder) {
        super.initForMenu((Context)object, menuBuilder);
        menuBuilder = object.getResources();
        object = ActionBarPolicy.get((Context)object);
        if (!this.mReserveOverflowSet) {
            this.mReserveOverflow = ((ActionBarPolicy)object).showsOverflowMenuButton();
        }
        if (!this.mWidthLimitSet) {
            this.mWidthLimit = ((ActionBarPolicy)object).getEmbeddedMenuWidthLimit();
        }
        if (!this.mMaxItemsSet) {
            this.mMaxItems = ((ActionBarPolicy)object).getMaxActionButtons();
        }
        int n2 = this.mWidthLimit;
        if (this.mReserveOverflow) {
            if (this.mOverflowButton == null) {
                this.mOverflowButton = new OverflowMenuButton(this.mSystemContext);
                if (this.mPendingOverflowIconSet) {
                    this.mOverflowButton.setImageDrawable(this.mPendingOverflowIcon);
                    this.mPendingOverflowIcon = null;
                    this.mPendingOverflowIconSet = false;
                }
                int n3 = View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
                this.mOverflowButton.measure(n3, n3);
            }
            n2 -= this.mOverflowButton.getMeasuredWidth();
        } else {
            this.mOverflowButton = null;
        }
        this.mActionItemWidthLimit = n2;
        this.mMinCellSize = (int)(56.0f * menuBuilder.getDisplayMetrics().density);
        this.mScrapActionButtonView = null;
    }

    public boolean isOverflowMenuShowPending() {
        return this.mPostedOpenRunnable != null || this.isOverflowMenuShowing();
    }

    public boolean isOverflowMenuShowing() {
        return this.mOverflowPopup != null && this.mOverflowPopup.isShowing();
    }

    public boolean isOverflowReserved() {
        return this.mReserveOverflow;
    }

    @Override
    public void onCloseMenu(MenuBuilder menuBuilder, boolean bl2) {
        this.dismissPopupMenus();
        super.onCloseMenu(menuBuilder, bl2);
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (!this.mMaxItemsSet) {
            this.mMaxItems = this.mContext.getResources().getInteger(R.integer.abc_max_action_buttons);
        }
        if (this.mMenu != null) {
            this.mMenu.onItemsChanged(true);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        block3: {
            block2: {
                if (!(parcelable instanceof SavedState)) break block2;
                parcelable = (SavedState)parcelable;
                if (parcelable.openSubMenuId > 0 && (parcelable = this.mMenu.findItem(parcelable.openSubMenuId)) != null) break block3;
            }
            return;
        }
        this.onSubMenuSelected((SubMenuBuilder)parcelable.getSubMenu());
    }

    @Override
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState();
        savedState.openSubMenuId = this.mOpenSubMenuId;
        return savedState;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        Object object;
        block4: {
            block5: {
                block3: {
                    if (!subMenuBuilder.hasVisibleItems()) break block3;
                    object = subMenuBuilder;
                    while (((SubMenuBuilder)object).getParentMenu() != this.mMenu) {
                        object = (SubMenuBuilder)((SubMenuBuilder)object).getParentMenu();
                    }
                    View view = this.findViewForItem(((SubMenuBuilder)object).getItem());
                    object = view;
                    if (view != null) break block4;
                    if (this.mOverflowButton != null) break block5;
                }
                return false;
            }
            object = this.mOverflowButton;
        }
        this.mOpenSubMenuId = subMenuBuilder.getItem().getItemId();
        this.mActionButtonPopup = new ActionButtonSubmenu(this.mContext, subMenuBuilder);
        this.mActionButtonPopup.setAnchorView((View)object);
        this.mActionButtonPopup.show();
        super.onSubMenuSelected(subMenuBuilder);
        return true;
    }

    @Override
    public void onSubUiVisibilityChanged(boolean bl2) {
        if (bl2) {
            super.onSubMenuSelected(null);
            return;
        }
        this.mMenu.close(false);
    }

    public void setExpandedActionViewsExclusive(boolean bl2) {
        this.mExpandedActionViewsExclusive = bl2;
    }

    public void setItemLimit(int n2) {
        this.mMaxItems = n2;
        this.mMaxItemsSet = true;
    }

    public void setMenuView(ActionMenuView actionMenuView) {
        this.mMenuView = actionMenuView;
        actionMenuView.initialize(this.mMenu);
    }

    public void setOverflowIcon(Drawable drawable2) {
        if (this.mOverflowButton != null) {
            this.mOverflowButton.setImageDrawable(drawable2);
            return;
        }
        this.mPendingOverflowIconSet = true;
        this.mPendingOverflowIcon = drawable2;
    }

    public void setReserveOverflow(boolean bl2) {
        this.mReserveOverflow = bl2;
        this.mReserveOverflowSet = true;
    }

    public void setWidthLimit(int n2, boolean bl2) {
        this.mWidthLimit = n2;
        this.mStrictWidthLimit = bl2;
        this.mWidthLimitSet = true;
    }

    @Override
    public boolean shouldIncludeItem(int n2, MenuItemImpl menuItemImpl) {
        return menuItemImpl.isActionButton();
    }

    public boolean showOverflowMenu() {
        if (this.mReserveOverflow && !this.isOverflowMenuShowing() && this.mMenu != null && this.mMenuView != null && this.mPostedOpenRunnable == null && !this.mMenu.getNonActionItems().isEmpty()) {
            this.mPostedOpenRunnable = new OpenOverflowRunnable(new OverflowPopup(this.mContext, this.mMenu, (View)this.mOverflowButton, true));
            ((View)this.mMenuView).post((Runnable)this.mPostedOpenRunnable);
            super.onSubMenuSelected(null);
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void updateMenuView(boolean bl2) {
        int n2;
        int n3;
        Object object = (ViewGroup)((View)this.mMenuView).getParent();
        if (object != null) {
            ActionBarTransition.beginDelayedTransition((ViewGroup)object);
        }
        super.updateMenuView(bl2);
        ((View)this.mMenuView).requestLayout();
        if (this.mMenu != null) {
            object = this.mMenu.getActionItems();
            n3 = ((ArrayList)object).size();
            for (n2 = 0; n2 < n3; ++n2) {
                ActionProvider actionProvider = ((MenuItemImpl)((ArrayList)object).get(n2)).getSupportActionProvider();
                if (actionProvider == null) continue;
                actionProvider.setSubUiVisibilityListener(this);
            }
        }
        object = this.mMenu != null ? this.mMenu.getNonActionItems() : null;
        n2 = n3 = 0;
        if (this.mReserveOverflow) {
            n2 = n3;
            if (object != null) {
                n2 = ((ArrayList)object).size();
                n2 = n2 == 1 ? (!((MenuItemImpl)((ArrayList)object).get(0)).isActionViewExpanded() ? 1 : 0) : (n2 > 0 ? 1 : 0);
            }
        }
        if (n2 != 0) {
            if (this.mOverflowButton == null) {
                this.mOverflowButton = new OverflowMenuButton(this.mSystemContext);
            }
            if ((object = (ViewGroup)this.mOverflowButton.getParent()) != this.mMenuView) {
                if (object != null) {
                    object.removeView((View)this.mOverflowButton);
                }
                object = (ActionMenuView)this.mMenuView;
                object.addView((View)this.mOverflowButton, (ViewGroup.LayoutParams)((ActionMenuView)object).generateOverflowButtonLayoutParams());
            }
        } else if (this.mOverflowButton != null && this.mOverflowButton.getParent() == this.mMenuView) {
            ((ViewGroup)this.mMenuView).removeView((View)this.mOverflowButton);
        }
        ((ActionMenuView)this.mMenuView).setOverflowReserved(this.mReserveOverflow);
    }

    private class ActionButtonSubmenu
    extends MenuPopupHelper {
        private SubMenuBuilder mSubMenu;

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        public ActionButtonSubmenu(Context object, SubMenuBuilder subMenuBuilder) {
            void var3_7;
            super((Context)object, (MenuBuilder)var3_7, null, false, R.attr.actionOverflowMenuStyle);
            this.mSubMenu = var3_7;
            if (!((MenuItemImpl)var3_7.getItem()).isActionButton()) {
                void var2_4;
                if (ActionMenuPresenter.this.mOverflowButton == null) {
                    View view = (View)ActionMenuPresenter.this.mMenuView;
                } else {
                    OverflowMenuButton overflowMenuButton = ActionMenuPresenter.this.mOverflowButton;
                }
                this.setAnchorView((View)var2_4);
            }
            this.setCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
            boolean bl2 = false;
            int n2 = var3_7.size();
            int n3 = 0;
            while (true) {
                block8: {
                    boolean bl3;
                    block7: {
                        bl3 = bl2;
                        if (n3 >= n2) break block7;
                        ActionMenuPresenter.this = var3_7.getItem(n3);
                        if (!ActionMenuPresenter.this.isVisible() || ActionMenuPresenter.this.getIcon() == null) break block8;
                        bl3 = true;
                    }
                    this.setForceShowIcon(bl3);
                    return;
                }
                ++n3;
            }
        }

        @Override
        public void onDismiss() {
            super.onDismiss();
            ActionMenuPresenter.access$802(ActionMenuPresenter.this, null);
            ActionMenuPresenter.this.mOpenSubMenuId = 0;
        }
    }

    private class ActionMenuPopupCallback
    extends ActionMenuItemView.PopupCallback {
        private ActionMenuPopupCallback() {
        }

        @Override
        public ListPopupWindow getPopup() {
            if (ActionMenuPresenter.this.mActionButtonPopup != null) {
                return ActionMenuPresenter.this.mActionButtonPopup.getPopup();
            }
            return null;
        }
    }

    private class OpenOverflowRunnable
    implements Runnable {
        private OverflowPopup mPopup;

        public OpenOverflowRunnable(OverflowPopup overflowPopup) {
            this.mPopup = overflowPopup;
        }

        @Override
        public void run() {
            ActionMenuPresenter.this.mMenu.changeMenuMode();
            View view = (View)ActionMenuPresenter.this.mMenuView;
            if (view != null && view.getWindowToken() != null && this.mPopup.tryShow()) {
                ActionMenuPresenter.access$202(ActionMenuPresenter.this, this.mPopup);
            }
            ActionMenuPresenter.access$302(ActionMenuPresenter.this, null);
        }
    }

    private class OverflowMenuButton
    extends AppCompatImageView
    implements ActionMenuView.ActionMenuChildView {
        private final float[] mTempPts;

        public OverflowMenuButton(Context context) {
            super(context, null, R.attr.actionOverflowButtonStyle);
            this.mTempPts = new float[2];
            this.setClickable(true);
            this.setFocusable(true);
            this.setVisibility(0);
            this.setEnabled(true);
            this.setOnTouchListener(new ListPopupWindow.ForwardingListener((View)this){

                @Override
                public ListPopupWindow getPopup() {
                    if (ActionMenuPresenter.this.mOverflowPopup == null) {
                        return null;
                    }
                    return ActionMenuPresenter.this.mOverflowPopup.getPopup();
                }

                @Override
                public boolean onForwardingStarted() {
                    ActionMenuPresenter.this.showOverflowMenu();
                    return true;
                }

                @Override
                public boolean onForwardingStopped() {
                    if (ActionMenuPresenter.this.mPostedOpenRunnable != null) {
                        return false;
                    }
                    ActionMenuPresenter.this.hideOverflowMenu();
                    return true;
                }
            });
        }

        @Override
        public boolean needsDividerAfter() {
            return false;
        }

        @Override
        public boolean needsDividerBefore() {
            return false;
        }

        public boolean performClick() {
            if (super.performClick()) {
                return true;
            }
            this.playSoundEffect(0);
            ActionMenuPresenter.this.showOverflowMenu();
            return true;
        }

        protected boolean setFrame(int n2, int n3, int n4, int n5) {
            boolean bl2 = super.setFrame(n2, n3, n4, n5);
            Drawable drawable2 = this.getDrawable();
            Drawable drawable3 = this.getBackground();
            if (drawable2 != null && drawable3 != null) {
                int n6 = this.getWidth();
                n3 = this.getHeight();
                n2 = Math.max(n6, n3) / 2;
                int n7 = this.getPaddingLeft();
                int n8 = this.getPaddingRight();
                n4 = this.getPaddingTop();
                n5 = this.getPaddingBottom();
                n6 = (n6 + (n7 - n8)) / 2;
                n3 = (n3 + (n4 - n5)) / 2;
                DrawableCompat.setHotspotBounds(drawable3, n6 - n2, n3 - n2, n6 + n2, n3 + n2);
            }
            return bl2;
        }
    }

    private class OverflowPopup
    extends MenuPopupHelper {
        public OverflowPopup(Context context, MenuBuilder menuBuilder, View view, boolean bl2) {
            super(context, menuBuilder, view, bl2, R.attr.actionOverflowMenuStyle);
            this.setGravity(0x800005);
            this.setCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
        }

        @Override
        public void onDismiss() {
            super.onDismiss();
            if (ActionMenuPresenter.this.mMenu != null) {
                ActionMenuPresenter.this.mMenu.close();
            }
            ActionMenuPresenter.access$202(ActionMenuPresenter.this, null);
        }
    }

    private class PopupPresenterCallback
    implements MenuPresenter.Callback {
        private PopupPresenterCallback() {
        }

        @Override
        public void onCloseMenu(MenuBuilder menuBuilder, boolean bl2) {
            MenuPresenter.Callback callback;
            if (menuBuilder instanceof SubMenuBuilder) {
                ((SubMenuBuilder)menuBuilder).getRootMenu().close(false);
            }
            if ((callback = ActionMenuPresenter.this.getCallback()) != null) {
                callback.onCloseMenu(menuBuilder, bl2);
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            if (menuBuilder == null) {
                return false;
            }
            ActionMenuPresenter.this.mOpenSubMenuId = ((SubMenuBuilder)menuBuilder).getItem().getItemId();
            MenuPresenter.Callback callback = ActionMenuPresenter.this.getCallback();
            if (callback == null) return false;
            return callback.onOpenSubMenu(menuBuilder);
        }
    }

    private static class SavedState
    implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n2) {
                return new SavedState[n2];
            }
        };
        public int openSubMenuId;

        SavedState() {
        }

        SavedState(Parcel parcel) {
            this.openSubMenuId = parcel.readInt();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int n2) {
            parcel.writeInt(this.openSubMenuId);
        }
    }
}

