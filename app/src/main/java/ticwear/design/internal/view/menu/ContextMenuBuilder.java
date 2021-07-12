/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnDismissListener
 *  android.content.DialogInterface$OnShowListener
 *  android.graphics.drawable.Drawable
 *  android.view.ContextMenu
 *  android.view.LayoutInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.Window
 *  android.widget.RelativeLayout
 */
package ticwear.design.internal.view.menu;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import ticwear.design.R;
import ticwear.design.app.AlertDialog;
import ticwear.design.internal.view.menu.MenuBuilder;
import ticwear.design.internal.view.menu.MenuFloatingLayout;
import ticwear.design.utils.blur.BlurBehind;

public class ContextMenuBuilder
extends MenuBuilder
implements ContextMenu {
    private View mHeaderView;
    private Drawable mIcon;
    private AlertDialog mMenuDialog;
    private MenuFloatingLayout mMenuLayout;
    private CharSequence mTitle;

    public ContextMenuBuilder(Context context) {
        super(context);
    }

    @NonNull
    private RelativeLayout createDialogContent(Context context) {
        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setGravity(17);
        this.mMenuLayout = (MenuFloatingLayout)LayoutInflater.from((Context)context).inflate(R.layout.menu_floating_layout_ticwear, (ViewGroup)relativeLayout, false);
        this.mMenuLayout.clear();
        relativeLayout.addView((View)this.mMenuLayout);
        return relativeLayout;
    }

    @Nullable
    private Window getBackgroundWindow() {
        Window window = null;
        if (this.getContext() instanceof Activity) {
            window = ((Activity)this.getContext()).getWindow();
        }
        return window;
    }

    public void clearHeader() {
        this.mHeaderView = null;
        this.mTitle = null;
        this.mIcon = null;
    }

    @Override
    public void close() {
        if (!this.isOpen()) {
            return;
        }
        super.close();
        this.mMenuLayout = null;
        this.mMenuDialog.dismiss();
        this.mMenuDialog = null;
    }

    public boolean isOpen() {
        return this.mMenuDialog != null;
    }

    @Override
    public void onItemsChanged(boolean bl2) {
        super.onItemsChanged(bl2);
        if (this.mMenuLayout != null) {
            if (bl2) {
                this.mMenuLayout.clear();
                for (int i2 = 0; i2 < this.size(); ++i2) {
                    this.mMenuLayout.addMenuItem(this.getItem(i2));
                }
            }
            this.mMenuLayout.notifyItemsChanged();
        }
    }

    public void open() {
        this.mMenuDialog = new AlertDialog.Builder(this.getContext(), R.style.Theme_Ticwear_Dialog_Alert_ContextMenu).setCustomTitle(this.mHeaderView).setTitle(this.mTitle).setIcon(this.mIcon).setOnDismissListener(new DialogInterface.OnDismissListener(){

            public void onDismiss(DialogInterface dialogInterface) {
                ContextMenuBuilder.this.close();
            }
        }).create();
        RelativeLayout relativeLayout = this.createDialogContent(this.mMenuDialog.getContext());
        this.mMenuDialog.setView((View)relativeLayout);
        this.mMenuLayout.setOnItemSelectedListener(new MenuFloatingLayout.OnItemSelectedListener(){

            @Override
            public void onItemSelected(MenuItem menuItem) {
                if (menuItem != null) {
                    ContextMenuBuilder.this.performItemAction(menuItem);
                }
                ContextMenuBuilder.this.close();
            }
        });
        this.onItemsChanged(true);
        final int n2 = this.getResources().getColor(R.color.tic_background_mask_dark);
        final int n3 = this.getResources().getInteger(17694722);
        this.mMenuDialog.setOnShowListener(new DialogInterface.OnShowListener(){

            public void onShow(DialogInterface dialogInterface) {
                ContextMenuBuilder.this.mMenuLayout.postDelayed(new Runnable(){

                    @Override
                    public void run() {
                        if (ContextMenuBuilder.this.mMenuDialog == null) {
                            return;
                        }
                        BlurBehind.from(ContextMenuBuilder.this.getContext()).animate(n3).color(n2).sampling(2).capture(ContextMenuBuilder.this.getBackgroundWindow()).into(ContextMenuBuilder.this.mMenuDialog.getWindow());
                    }
                }, 500L);
            }
        });
        this.mMenuDialog.show();
        BlurBehind.from(this.getContext()).color(n2).sampling(2).capture(this.getBackgroundWindow()).into(this.mMenuDialog.getWindow());
    }

    public ContextMenu setHeaderIcon(int n2) {
        this.mIcon = this.getResources().getDrawable(n2, this.getContext().getTheme());
        return this;
    }

    public ContextMenu setHeaderIcon(Drawable drawable2) {
        this.mIcon = drawable2;
        return this;
    }

    public ContextMenu setHeaderTitle(int n2) {
        this.mTitle = this.getResources().getString(n2);
        return this;
    }

    public ContextMenu setHeaderTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        return this;
    }

    public ContextMenu setHeaderView(View view) {
        this.mHeaderView = view;
        return this;
    }
}

