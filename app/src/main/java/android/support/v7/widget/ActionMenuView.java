/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.view.ContextThemeWrapper
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewDebug$ExportedProperty
 *  android.view.ViewGroup$LayoutParams
 *  android.view.accessibility.AccessibilityEvent
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.ActionMenuPresenter;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.ViewUtils;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

public class ActionMenuView
extends LinearLayoutCompat
implements MenuBuilder.ItemInvoker,
MenuView {
    static final int GENERATED_ITEM_PADDING = 4;
    static final int MIN_CELL_SIZE = 56;
    private static final String TAG = "ActionMenuView";
    private MenuPresenter.Callback mActionMenuPresenterCallback;
    private boolean mFormatItems;
    private int mFormatItemsWidth;
    private int mGeneratedItemPadding;
    private MenuBuilder mMenu;
    private MenuBuilder.Callback mMenuBuilderCallback;
    private int mMinCellSize;
    private OnMenuItemClickListener mOnMenuItemClickListener;
    private Context mPopupContext;
    private int mPopupTheme;
    private ActionMenuPresenter mPresenter;
    private boolean mReserveOverflow;

    public ActionMenuView(Context context) {
        this(context, null);
    }

    public ActionMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setBaselineAligned(false);
        float f2 = context.getResources().getDisplayMetrics().density;
        this.mMinCellSize = (int)(56.0f * f2);
        this.mGeneratedItemPadding = (int)(4.0f * f2);
        this.mPopupContext = context;
        this.mPopupTheme = 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    static int measureChildForCells(View view, int n2, int n3, int n4, int n5) {
        int n6;
        LayoutParams layoutParams;
        block6: {
            int n7;
            block7: {
                layoutParams = (LayoutParams)view.getLayoutParams();
                n6 = View.MeasureSpec.makeMeasureSpec((int)(View.MeasureSpec.getSize((int)n4) - n5), (int)View.MeasureSpec.getMode((int)n4));
                ActionMenuItemView actionMenuItemView = view instanceof ActionMenuItemView ? (ActionMenuItemView)view : null;
                n5 = actionMenuItemView != null && actionMenuItemView.hasText() ? 1 : 0;
                n4 = n7 = 0;
                if (n3 <= 0) break block6;
                if (n5 == 0) break block7;
                n4 = n7;
                if (n3 < 2) break block6;
            }
            view.measure(View.MeasureSpec.makeMeasureSpec((int)(n2 * n3), (int)Integer.MIN_VALUE), n6);
            n7 = view.getMeasuredWidth();
            n3 = n4 = n7 / n2;
            if (n7 % n2 != 0) {
                n3 = n4 + 1;
            }
            n4 = n3;
            if (n5 != 0) {
                n4 = n3;
                if (n3 < 2) {
                    n4 = 2;
                }
            }
        }
        boolean bl2 = !layoutParams.isOverflowButton && n5 != 0;
        layoutParams.expandable = bl2;
        layoutParams.cellsUsed = n4;
        view.measure(View.MeasureSpec.makeMeasureSpec((int)(n4 * n2), (int)0x40000000), n6);
        return n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onMeasureExactFormat(int n2, int n3) {
        int n4;
        int n5;
        int n6;
        int n7;
        block40: {
            int n8;
            int n9;
            int n10;
            LayoutParams layoutParams;
            int n11;
            int n12;
            long l2;
            int n13;
            n7 = View.MeasureSpec.getMode((int)n3);
            n2 = View.MeasureSpec.getSize((int)n2);
            n6 = View.MeasureSpec.getSize((int)n3);
            n5 = this.getPaddingLeft();
            int n14 = this.getPaddingRight();
            int n15 = this.getPaddingTop() + this.getPaddingBottom();
            int n16 = ActionMenuView.getChildMeasureSpec((int)n3, (int)n15, (int)-2);
            n4 = n2 - (n5 + n14);
            n2 = n4 / this.mMinCellSize;
            n3 = this.mMinCellSize;
            if (n2 == 0) {
                this.setMeasuredDimension(n4, 0);
                return;
            }
            int n17 = this.mMinCellSize + n4 % n3 / n2;
            n5 = 0;
            int n18 = 0;
            int n19 = 0;
            int n20 = 0;
            n14 = 0;
            long l3 = 0L;
            int n21 = this.getChildCount();
            for (n13 = 0; n13 < n21; ++n13) {
                View view = this.getChildAt(n13);
                if (view.getVisibility() == 8) {
                    l2 = l3;
                    n12 = n14;
                } else {
                    boolean bl2 = view instanceof ActionMenuItemView;
                    n11 = n20 + 1;
                    if (bl2) {
                        view.setPadding(this.mGeneratedItemPadding, 0, this.mGeneratedItemPadding, 0);
                    }
                    layoutParams = (LayoutParams)view.getLayoutParams();
                    layoutParams.expanded = false;
                    layoutParams.extraPixels = 0;
                    layoutParams.cellsUsed = 0;
                    layoutParams.expandable = false;
                    layoutParams.leftMargin = 0;
                    layoutParams.rightMargin = 0;
                    bl2 = bl2 && ((ActionMenuItemView)view).hasText();
                    layoutParams.preventEdgeOffset = bl2;
                    n3 = layoutParams.isOverflowButton ? 1 : n2;
                    int n22 = ActionMenuView.measureChildForCells(view, n17, n3, n16, n15);
                    n10 = Math.max(n18, n22);
                    n3 = n19;
                    if (layoutParams.expandable) {
                        n3 = n19 + 1;
                    }
                    if (layoutParams.isOverflowButton) {
                        n14 = 1;
                    }
                    n9 = n2 - n22;
                    n8 = Math.max(n5, view.getMeasuredHeight());
                    n2 = n9;
                    n19 = n3;
                    n12 = n14;
                    n18 = n10;
                    n5 = n8;
                    l2 = l3;
                    n20 = n11;
                    if (n22 == 1) {
                        l2 = l3 | (long)(1 << n13);
                        n2 = n9;
                        n19 = n3;
                        n12 = n14;
                        n18 = n10;
                        n5 = n8;
                        n20 = n11;
                    }
                }
                n14 = n12;
                l3 = l2;
            }
            n13 = n14 != 0 && n20 == 2 ? 1 : 0;
            n3 = 0;
            n12 = n2;
            while (true) {
                long l4;
                block42: {
                    block43: {
                        float f2;
                        block44: {
                            block41: {
                                l2 = l3;
                                if (n19 <= 0) break block41;
                                l2 = l3;
                                if (n12 <= 0) break block41;
                                n11 = Integer.MAX_VALUE;
                                l4 = 0L;
                                n8 = 0;
                                for (n10 = 0; n10 < n21; ++n10) {
                                    LayoutParams layoutParams2 = (LayoutParams)this.getChildAt(n10).getLayoutParams();
                                    if (!layoutParams2.expandable) {
                                        l2 = l4;
                                        n2 = n8;
                                        n9 = n11;
                                    } else if (layoutParams2.cellsUsed < n11) {
                                        n9 = layoutParams2.cellsUsed;
                                        l2 = 1 << n10;
                                        n2 = 1;
                                    } else {
                                        n9 = n11;
                                        n2 = n8;
                                        l2 = l4;
                                        if (layoutParams2.cellsUsed == n11) {
                                            l2 = l4 | (long)(1 << n10);
                                            n2 = n8 + 1;
                                            n9 = n11;
                                        }
                                    }
                                    n11 = n9;
                                    n8 = n2;
                                    l4 = l2;
                                }
                                l3 |= l4;
                                if (n8 <= n12) break block42;
                                l2 = l3;
                            }
                            n2 = n14 == 0 && n20 == 1 ? 1 : 0;
                            n14 = n3;
                            if (n12 <= 0) break block43;
                            n14 = n3;
                            if (l2 == 0L) break block43;
                            if (n12 < n20 - 1 || n2 != 0) break block44;
                            n14 = n3;
                            if (n18 <= true) break block43;
                        }
                        float f3 = f2 = (float)Long.bitCount(l2);
                        if (n2 == 0) {
                            float f4 = f2;
                            if ((1L & l2) != 0L) {
                                f4 = f2;
                                if (!((LayoutParams)this.getChildAt((int)0).getLayoutParams()).preventEdgeOffset) {
                                    f4 = f2 - 0.5f;
                                }
                            }
                            f3 = f4;
                            if (((long)(1 << n21 - 1) & l2) != 0L) {
                                f3 = f4;
                                if (!((LayoutParams)this.getChildAt((int)(n21 - 1)).getLayoutParams()).preventEdgeOffset) {
                                    f3 = f4 - 0.5f;
                                }
                            }
                        }
                        n14 = f3 > 0.0f ? (int)((float)(n12 * n17) / f3) : 0;
                        for (n19 = 0; n19 < n21; ++n19) {
                            if (((long)(1 << n19) & l2) == 0L) {
                                n2 = n3;
                            } else {
                                View view = this.getChildAt(n19);
                                layoutParams = (LayoutParams)view.getLayoutParams();
                                if (view instanceof ActionMenuItemView) {
                                    layoutParams.extraPixels = n14;
                                    layoutParams.expanded = true;
                                    if (n19 == 0 && !layoutParams.preventEdgeOffset) {
                                        layoutParams.leftMargin = -n14 / 2;
                                    }
                                    n2 = 1;
                                } else if (layoutParams.isOverflowButton) {
                                    layoutParams.extraPixels = n14;
                                    layoutParams.expanded = true;
                                    layoutParams.rightMargin = -n14 / 2;
                                    n2 = 1;
                                } else {
                                    if (n19 != 0) {
                                        layoutParams.leftMargin = n14 / 2;
                                    }
                                    n2 = n3;
                                    if (n19 != n21 - 1) {
                                        layoutParams.rightMargin = n14 / 2;
                                        n2 = n3;
                                    }
                                }
                            }
                            n3 = n2;
                        }
                        n14 = n3;
                    }
                    if (n14 != 0) {
                        break;
                    }
                    break block40;
                }
                for (n2 = 0; n2 < n21; ++n2) {
                    View view = this.getChildAt(n2);
                    layoutParams = (LayoutParams)view.getLayoutParams();
                    if (((long)(1 << n2) & l4) == 0L) {
                        n3 = n12;
                        l2 = l3;
                        if (layoutParams.cellsUsed == n11 + 1) {
                            l2 = l3 | (long)(1 << n2);
                            n3 = n12;
                        }
                    } else {
                        if (n13 != 0 && layoutParams.preventEdgeOffset && n12 == 1) {
                            view.setPadding(this.mGeneratedItemPadding + n17, 0, this.mGeneratedItemPadding, 0);
                        }
                        ++layoutParams.cellsUsed;
                        layoutParams.expanded = true;
                        n3 = n12 - 1;
                        l2 = l3;
                    }
                    n12 = n3;
                    l3 = l2;
                }
                n3 = 1;
            }
            for (n2 = 0; n2 < n21; ++n2) {
                View view = this.getChildAt(n2);
                layoutParams = (LayoutParams)view.getLayoutParams();
                if (!layoutParams.expanded) continue;
                view.measure(View.MeasureSpec.makeMeasureSpec((int)(layoutParams.cellsUsed * n17 + layoutParams.extraPixels), (int)0x40000000), n16);
            }
        }
        n2 = n6;
        if (n7 != 0x40000000) {
            n2 = n5;
        }
        this.setMeasuredDimension(n4, n2);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams != null && layoutParams instanceof LayoutParams;
    }

    public void dismissPopupMenus() {
        if (this.mPresenter != null) {
            this.mPresenter.dismissPopupMenus();
        }
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return false;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = 16;
        return layoutParams;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams object) {
        void var1_3;
        if (object == null) {
            return this.generateDefaultLayoutParams();
        }
        if (object instanceof LayoutParams) {
            LayoutParams layoutParams = new LayoutParams((LayoutParams)((Object)object));
        } else {
            LayoutParams layoutParams = new LayoutParams((ViewGroup.LayoutParams)object);
        }
        if (var1_3.gravity <= 0) {
            var1_3.gravity = 16;
        }
        return var1_3;
    }

    public LayoutParams generateOverflowButtonLayoutParams() {
        LayoutParams layoutParams = this.generateDefaultLayoutParams();
        layoutParams.isOverflowButton = true;
        return layoutParams;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public Menu getMenu() {
        if (this.mMenu == null) {
            void var1_3;
            Context context = this.getContext();
            this.mMenu = new MenuBuilder(context);
            this.mMenu.setCallback(new MenuBuilderCallback());
            this.mPresenter = new ActionMenuPresenter(context);
            this.mPresenter.setReserveOverflow(true);
            ActionMenuPresenter actionMenuPresenter = this.mPresenter;
            if (this.mActionMenuPresenterCallback != null) {
                MenuPresenter.Callback callback = this.mActionMenuPresenterCallback;
            } else {
                ActionMenuPresenterCallback actionMenuPresenterCallback = new ActionMenuPresenterCallback();
            }
            actionMenuPresenter.setCallback((MenuPresenter.Callback)var1_3);
            this.mMenu.addMenuPresenter(this.mPresenter, this.mPopupContext);
            this.mPresenter.setMenuView(this);
        }
        return this.mMenu;
    }

    @Nullable
    public Drawable getOverflowIcon() {
        this.getMenu();
        return this.mPresenter.getOverflowIcon();
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    @Override
    public int getWindowAnimations() {
        return 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected boolean hasSupportDividerBeforeChildAt(int n2) {
        boolean bl2;
        if (n2 == 0) {
            return false;
        }
        View view = this.getChildAt(n2 - 1);
        View view2 = this.getChildAt(n2);
        boolean bl3 = bl2 = false;
        if (n2 < this.getChildCount()) {
            bl3 = bl2;
            if (view instanceof ActionMenuChildView) {
                bl3 = false | ((ActionMenuChildView)view).needsDividerAfter();
            }
        }
        bl2 = bl3;
        if (n2 <= 0) return bl2;
        bl2 = bl3;
        if (!(view2 instanceof ActionMenuChildView)) return bl2;
        return bl3 | ((ActionMenuChildView)view2).needsDividerBefore();
    }

    public boolean hideOverflowMenu() {
        return this.mPresenter != null && this.mPresenter.hideOverflowMenu();
    }

    @Override
    public void initialize(MenuBuilder menuBuilder) {
        this.mMenu = menuBuilder;
    }

    @Override
    public boolean invokeItem(MenuItemImpl menuItemImpl) {
        return this.mMenu.performItemAction(menuItemImpl, 0);
    }

    public boolean isOverflowMenuShowPending() {
        return this.mPresenter != null && this.mPresenter.isOverflowMenuShowPending();
    }

    public boolean isOverflowMenuShowing() {
        return this.mPresenter != null && this.mPresenter.isOverflowMenuShowing();
    }

    public boolean isOverflowReserved() {
        return this.mReserveOverflow;
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (Build.VERSION.SDK_INT >= 8) {
            super.onConfigurationChanged(configuration);
        }
        if (this.mPresenter != null) {
            this.mPresenter.updateMenuView(false);
            if (this.mPresenter.isOverflowMenuShowing()) {
                this.mPresenter.hideOverflowMenu();
                this.mPresenter.showOverflowMenu();
            }
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.dismissPopupMenus();
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        LayoutParams layoutParams;
        View view;
        int n6;
        if (!this.mFormatItems) {
            super.onLayout(bl2, n2, n3, n4, n5);
            return;
        }
        int n7 = this.getChildCount();
        int n8 = (n5 - n3) / 2;
        int n9 = this.getDividerWidth();
        int n10 = 0;
        n5 = 0;
        n3 = n4 - n2 - this.getPaddingRight() - this.getPaddingLeft();
        int n11 = 0;
        bl2 = ViewUtils.isLayoutRtl((View)this);
        for (n6 = 0; n6 < n7; ++n6) {
            int n12;
            view = this.getChildAt(n6);
            if (view.getVisibility() == 8) continue;
            layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams.isOverflowButton) {
                int n13;
                n11 = n12 = view.getMeasuredWidth();
                if (this.hasSupportDividerBeforeChildAt(n6)) {
                    n11 = n12 + n9;
                }
                int n14 = view.getMeasuredHeight();
                if (bl2) {
                    n12 = this.getPaddingLeft() + layoutParams.leftMargin;
                    n13 = n12 + n11;
                } else {
                    n13 = this.getWidth() - this.getPaddingRight() - layoutParams.rightMargin;
                    n12 = n13 - n11;
                }
                int n15 = n8 - n14 / 2;
                view.layout(n12, n15, n13, n15 + n14);
                n3 -= n11;
                n11 = 1;
                continue;
            }
            n12 = view.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            n10 += n12;
            n12 = n3 - n12;
            n3 = n10;
            if (this.hasSupportDividerBeforeChildAt(n6)) {
                n3 = n10 + n9;
            }
            ++n5;
            n10 = n3;
            n3 = n12;
        }
        if (n7 == 1 && n11 == 0) {
            view = this.getChildAt(0);
            n3 = view.getMeasuredWidth();
            n5 = view.getMeasuredHeight();
            n2 = (n4 - n2) / 2 - n3 / 2;
            n4 = n8 - n5 / 2;
            view.layout(n2, n4, n2 + n3, n4 + n5);
            return;
        }
        n2 = n11 != 0 ? 0 : 1;
        n2 = (n2 = n5 - n2) > 0 ? n3 / n2 : 0;
        n5 = Math.max(0, n2);
        if (bl2) {
            n3 = this.getWidth() - this.getPaddingRight();
            n2 = 0;
            while (n2 < n7) {
                view = this.getChildAt(n2);
                layoutParams = (LayoutParams)view.getLayoutParams();
                n4 = n3;
                if (view.getVisibility() != 8) {
                    if (layoutParams.isOverflowButton) {
                        n4 = n3;
                    } else {
                        n4 = view.getMeasuredWidth();
                        n6 = view.getMeasuredHeight();
                        n10 = n8 - n6 / 2;
                        view.layout((n3 -= layoutParams.rightMargin) - n4, n10, n3, n10 + n6);
                        n4 = n3 - (layoutParams.leftMargin + n4 + n5);
                    }
                }
                ++n2;
                n3 = n4;
            }
            return;
        }
        n3 = this.getPaddingLeft();
        n2 = 0;
        while (n2 < n7) {
            view = this.getChildAt(n2);
            layoutParams = (LayoutParams)view.getLayoutParams();
            n4 = n3;
            if (view.getVisibility() != 8) {
                if (layoutParams.isOverflowButton) {
                    n4 = n3;
                } else {
                    n4 = view.getMeasuredWidth();
                    n6 = view.getMeasuredHeight();
                    n10 = n8 - n6 / 2;
                    view.layout(n3 += layoutParams.leftMargin, n10, n3 + n4, n10 + n6);
                    n4 = n3 + (layoutParams.rightMargin + n4 + n5);
                }
            }
            ++n2;
            n3 = n4;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onMeasure(int n2, int n3) {
        boolean bl2 = this.mFormatItems;
        boolean bl3 = View.MeasureSpec.getMode((int)n2) == 0x40000000;
        this.mFormatItems = bl3;
        if (bl2 != this.mFormatItems) {
            this.mFormatItemsWidth = 0;
        }
        int n4 = View.MeasureSpec.getSize((int)n2);
        if (this.mFormatItems && this.mMenu != null && n4 != this.mFormatItemsWidth) {
            this.mFormatItemsWidth = n4;
            this.mMenu.onItemsChanged(true);
        }
        int n5 = this.getChildCount();
        if (this.mFormatItems && n5 > 0) {
            this.onMeasureExactFormat(n2, n3);
            return;
        }
        n4 = 0;
        while (true) {
            if (n4 >= n5) {
                super.onMeasure(n2, n3);
                return;
            }
            LayoutParams layoutParams = (LayoutParams)this.getChildAt(n4).getLayoutParams();
            layoutParams.rightMargin = 0;
            layoutParams.leftMargin = 0;
            ++n4;
        }
    }

    public MenuBuilder peekMenu() {
        return this.mMenu;
    }

    public void setExpandedActionViewsExclusive(boolean bl2) {
        this.mPresenter.setExpandedActionViewsExclusive(bl2);
    }

    public void setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        this.mActionMenuPresenterCallback = callback;
        this.mMenuBuilderCallback = callback2;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public void setOverflowIcon(@Nullable Drawable drawable2) {
        this.getMenu();
        this.mPresenter.setOverflowIcon(drawable2);
    }

    public void setOverflowReserved(boolean bl2) {
        this.mReserveOverflow = bl2;
    }

    public void setPopupTheme(@StyleRes int n2) {
        block3: {
            block2: {
                if (this.mPopupTheme == n2) break block2;
                this.mPopupTheme = n2;
                if (n2 != 0) break block3;
                this.mPopupContext = this.getContext();
            }
            return;
        }
        this.mPopupContext = new ContextThemeWrapper(this.getContext(), n2);
    }

    public void setPresenter(ActionMenuPresenter actionMenuPresenter) {
        this.mPresenter = actionMenuPresenter;
        this.mPresenter.setMenuView(this);
    }

    public boolean showOverflowMenu() {
        return this.mPresenter != null && this.mPresenter.showOverflowMenu();
    }

    public static interface ActionMenuChildView {
        public boolean needsDividerAfter();

        public boolean needsDividerBefore();
    }

    private class ActionMenuPresenterCallback
    implements MenuPresenter.Callback {
        private ActionMenuPresenterCallback() {
        }

        @Override
        public void onCloseMenu(MenuBuilder menuBuilder, boolean bl2) {
        }

        @Override
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            return false;
        }
    }

    public static class LayoutParams
    extends LinearLayoutCompat.LayoutParams {
        @ViewDebug.ExportedProperty
        public int cellsUsed;
        @ViewDebug.ExportedProperty
        public boolean expandable;
        boolean expanded;
        @ViewDebug.ExportedProperty
        public int extraPixels;
        @ViewDebug.ExportedProperty
        public boolean isOverflowButton;
        @ViewDebug.ExportedProperty
        public boolean preventEdgeOffset;

        public LayoutParams(int n2, int n3) {
            super(n2, n3);
            this.isOverflowButton = false;
        }

        LayoutParams(int n2, int n3, boolean bl2) {
            super(n2, n3);
            this.isOverflowButton = bl2;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.LayoutParams)layoutParams);
            this.isOverflowButton = layoutParams.isOverflowButton;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    private class MenuBuilderCallback
    implements MenuBuilder.Callback {
        private MenuBuilderCallback() {
        }

        @Override
        public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            return ActionMenuView.this.mOnMenuItemClickListener != null && ActionMenuView.this.mOnMenuItemClickListener.onMenuItemClick(menuItem);
        }

        @Override
        public void onMenuModeChange(MenuBuilder menuBuilder) {
            if (ActionMenuView.this.mMenuBuilderCallback != null) {
                ActionMenuView.this.mMenuBuilderCallback.onMenuModeChange(menuBuilder);
            }
        }
    }

    public static interface OnMenuItemClickListener {
        public boolean onMenuItemClick(MenuItem var1);
    }
}

