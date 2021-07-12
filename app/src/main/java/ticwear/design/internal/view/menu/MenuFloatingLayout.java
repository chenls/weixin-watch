/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 */
package ticwear.design.internal.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;
import ticwear.design.R;
import ticwear.design.internal.view.menu.MenuItemView;
import ticwear.design.widget.FocusableLinearLayoutManager;
import ticwear.design.widget.TicklableRecyclerView;

public class MenuFloatingLayout
extends LinearLayout {
    private static final int[] MENU_ITEM_TYPE_FOR_SIZE = new int[]{0, 1, 2, 3, 3};
    private final List<MenuItem> mItems;
    RecyclerView.Adapter mListAdapter;
    LinearLayout mMenuLinearLayout1;
    LinearLayout mMenuLinearLayout2;
    TicklableRecyclerView mMenuListLayout;
    private OnItemSelectedListener mOnItemSelectedListener;
    private Runnable mResetLayoutRunnable = new Runnable(){

        @Override
        public void run() {
            MenuFloatingLayout.this.resetLayout();
        }
    };

    public MenuFloatingLayout(Context context) {
        this(context, null);
    }

    public MenuFloatingLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MenuFloatingLayout(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, 0);
    }

    public MenuFloatingLayout(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
        this.mItems = new ArrayList<MenuItem>(1);
    }

    private MenuItemView createItemView(int n2, int n3, int n4) {
        MenuItemView menuItemView = (MenuItemView)LayoutInflater.from((Context)this.getContext()).inflate(R.layout.menu_item_view_ticwear, (ViewGroup)this, false);
        menuItemView.setMenuItemType(n2);
        menuItemView.setOrientation(n3);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)menuItemView.getLayoutParams();
        layoutParams.leftMargin = n4;
        layoutParams.rightMargin = n4;
        menuItemView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        return menuItemView;
    }

    private void notifyItemSelected(MenuItem menuItem) {
        if (this.mOnItemSelectedListener != null) {
            this.mOnItemSelectedListener.onItemSelected(menuItem);
        }
    }

    private void resetLayout() {
        this.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MenuFloatingLayout.this.notifyItemSelected(null);
            }
        });
        if (this.mItems.size() > 0 && this.mItems.size() <= 3) {
            this.mMenuLinearLayout1.setVisibility(0);
            this.mMenuLinearLayout2.setVisibility(8);
            this.mMenuListLayout.setVisibility(8);
            this.resetLinearLayout(this.mMenuLinearLayout1, 0, this.mItems.size());
            return;
        }
        if (this.mItems.size() == 4) {
            this.mMenuLinearLayout1.setVisibility(0);
            this.mMenuLinearLayout2.setVisibility(0);
            this.mMenuListLayout.setVisibility(8);
            this.resetLinearLayout(this.mMenuLinearLayout1, 0, 2);
            this.resetLinearLayout(this.mMenuLinearLayout2, 2, 2);
            return;
        }
        this.mMenuLinearLayout1.setVisibility(8);
        this.mMenuLinearLayout2.setVisibility(8);
        this.mMenuListLayout.setVisibility(0);
        this.resetListLayout();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void resetLinearLayout(LinearLayout linearLayout, int n2, int n3) {
        linearLayout.removeAllViews();
        linearLayout.setOrientation(0);
        linearLayout.setGravity(17);
        Resources resources = this.getResources();
        int n4 = n3 < 3 && this.mItems.size() > 3 ? R.dimen.tic_menu_item_margin_horizontal_large : R.dimen.tic_menu_item_margin_horizontal_small;
        int n5 = resources.getDimensionPixelOffset(n4);
        for (n4 = n2; n4 < n2 + n3 && n4 < this.mItems.size(); ++n4) {
            resources = this.mItems.get(n4);
            MenuItemView menuItemView = this.createItemView(MENU_ITEM_TYPE_FOR_SIZE[this.mItems.size()], 1, n5);
            menuItemView.setIcon(resources.getIcon());
            menuItemView.setTitle(resources.getTitle());
            menuItemView.setOnClickListener(new View.OnClickListener((MenuItem)resources){
                final /* synthetic */ MenuItem val$item;
                {
                    this.val$item = menuItem;
                }

                public void onClick(View view) {
                    MenuFloatingLayout.this.notifyItemSelected(this.val$item);
                }
            });
            linearLayout.addView((View)menuItemView);
        }
        linearLayout.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MenuFloatingLayout.this.notifyItemSelected(null);
            }
        });
    }

    private void resetListLayout() {
        if (this.mListAdapter == null) {
            this.mListAdapter = new RecyclerView.Adapter(){
                private static final int VIEW_TYPE_CLOSE = 1;
                private static final int VIEW_TYPE_NORMAL = 0;

                @Override
                public int getItemCount() {
                    return MenuFloatingLayout.this.mItems.size() + 1;
                }

                @Override
                public int getItemViewType(int n2) {
                    if (n2 < MenuFloatingLayout.this.mItems.size()) {
                        return 0;
                    }
                    return 1;
                }

                public void onBindViewHolder(RecyclerView.ViewHolder object, int n2) {
                    if (this.getItemViewType(n2) == 0) {
                        object = (MenuItemView)((RecyclerView.ViewHolder)object).itemView;
                        final MenuItem menuItem = (MenuItem)MenuFloatingLayout.this.mItems.get(n2);
                        ((MenuItemView)((Object)object)).setIcon(menuItem.getIcon());
                        ((MenuItemView)((Object)object)).setTitle(menuItem.getTitle());
                        ((MenuItemView)((Object)object)).setOnClickListener(new View.OnClickListener(){

                            public void onClick(View view) {
                                MenuFloatingLayout.this.notifyItemSelected(menuItem);
                            }
                        });
                    }
                }

                /*
                 * Enabled aggressive block sorting
                 */
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int n2) {
                    LayoutInflater layoutInflater = LayoutInflater.from((Context)MenuFloatingLayout.this.getContext());
                    int n3 = n2 == 0 ? R.layout.menu_list_item_view_ticwear : R.layout.menu_list_item_close_ticwear;
                    viewGroup = layoutInflater.inflate(n3, viewGroup, false);
                    if (n2 == 1) {
                        viewGroup.setOnClickListener(new View.OnClickListener(){

                            public void onClick(View view) {
                                MenuFloatingLayout.this.notifyItemSelected(null);
                            }
                        });
                    }
                    return new FocusableLinearLayoutManager.ViewHolder((View)viewGroup);
                }
            };
            this.mMenuListLayout.setAdapter(this.mListAdapter);
            this.mMenuListLayout.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    MenuFloatingLayout.this.notifyItemSelected(null);
                }
            });
            return;
        }
        this.mListAdapter.notifyDataSetChanged();
    }

    public void addMenuItem(MenuItem menuItem) {
        this.mItems.add(menuItem);
        this.notifyItemsChanged();
    }

    public void clear() {
        this.mItems.clear();
        this.notifyItemsChanged();
    }

    public void notifyItemsChanged() {
        this.removeCallbacks(this.mResetLayoutRunnable);
        this.post(this.mResetLayoutRunnable);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mMenuLinearLayout1 = (LinearLayout)this.findViewById(R.id.tic_menu_linear_layout_1);
        this.mMenuLinearLayout2 = (LinearLayout)this.findViewById(R.id.tic_menu_linear_layout_2);
        this.mMenuListLayout = (TicklableRecyclerView)this.findViewById(R.id.tic_menu_list_layout);
        this.mMenuListLayout.setLayoutManager(new FocusableLinearLayoutManager(this.getContext()));
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.mOnItemSelectedListener = onItemSelectedListener;
    }

    public static interface OnItemSelectedListener {
        public void onItemSelected(MenuItem var1);
    }
}

