/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.ContextMenu
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnAttachStateChangeListener
 */
package ticwear.design.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import ticwear.design.internal.view.menu.ContextMenuBuilder;
import ticwear.design.internal.view.menu.MenuBuilder;

public class FloatingContextMenu
implements MenuBuilder.Callback {
    private View mBindingView;
    private final Context mContext;
    private ContextMenuCreator mContextMenuCreator;
    private ContextMenuBuilder mMenuBuilder;
    private View.OnAttachStateChangeListener mOnAttachStateChangeListener = new View.OnAttachStateChangeListener(){

        public void onViewAttachedToWindow(View view) {
        }

        public void onViewDetachedFromWindow(View view) {
            FloatingContextMenu.this.close();
        }
    };
    private OnMenuSelectedListener mOnMenuSelectedListener;

    public FloatingContextMenu(Context context) {
        this.mContext = context;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void bindView(View view) {
        block3: {
            block2: {
                if (this.mBindingView == view) break block2;
                this.unbindView();
                this.mBindingView = view;
                if (this.mBindingView != null) break block3;
            }
            return;
        }
        this.mBindingView.addOnAttachStateChangeListener(this.mOnAttachStateChangeListener);
    }

    private void unbindView() {
        if (this.mBindingView != null) {
            this.mBindingView.removeOnAttachStateChangeListener(this.mOnAttachStateChangeListener);
            this.mBindingView = null;
        }
    }

    public void close() {
        if (this.mMenuBuilder != null) {
            this.mMenuBuilder.close();
        }
    }

    @Override
    public void onMenuClosed(MenuBuilder menuBuilder) {
        this.unbindView();
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuBuilder menuBuilder, MenuItem menuItem) {
        return this.mOnMenuSelectedListener != null && this.mOnMenuSelectedListener.onContextItemSelected(menuItem);
    }

    public FloatingContextMenu setContextMenuCreator(ContextMenuCreator contextMenuCreator) {
        this.mContextMenuCreator = contextMenuCreator;
        return this;
    }

    public FloatingContextMenu setOnMenuSelectedListener(OnMenuSelectedListener onMenuSelectedListener) {
        this.mOnMenuSelectedListener = onMenuSelectedListener;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean show(View view) {
        boolean bl2 = true;
        if (this.mContextMenuCreator == null) {
            return false;
        }
        if (this.mMenuBuilder == null) {
            this.mMenuBuilder = new ContextMenuBuilder(this.mContext);
            this.mMenuBuilder.setCallback(this);
        } else {
            this.mMenuBuilder.clear();
        }
        this.mContextMenuCreator.onCreateContextMenu(this.mMenuBuilder, view);
        if (this.mMenuBuilder.size() > 0) {
            this.bindView(view);
            if (this.mMenuBuilder.isOpen()) {
                this.mMenuBuilder.onItemsChanged(true);
            } else {
                this.mMenuBuilder.open();
            }
        } else {
            this.mMenuBuilder.close();
        }
        if (this.mMenuBuilder.size() <= 0) return false;
        return bl2;
    }

    public static interface ContextMenuCreator {
        public void onCreateContextMenu(ContextMenu var1, View var2);
    }

    public static interface OnMenuSelectedListener {
        public boolean onContextItemSelected(@NonNull MenuItem var1);
    }
}

