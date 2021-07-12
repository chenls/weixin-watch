/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.ImageButton
 *  android.widget.ImageView$ScaleType
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package ticwear.design.internal.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import ticwear.design.R;

public class MenuItemView
extends LinearLayout {
    public static final int MENU_ITEM_TYPE_DEFAULT = 0;
    public static final int MENU_ITEM_TYPE_LARGE = 1;
    public static final int MENU_ITEM_TYPE_MIDDLE = 2;
    public static final int MENU_ITEM_TYPE_SMALL = 3;
    ImageButton mImageIcon;
    private int mItemType = 0;
    TextView mTextTitle;

    public MenuItemView(Context context) {
        this(context, null);
    }

    public MenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MenuItemView(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, 0);
    }

    public MenuItemView(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mImageIcon = (ImageButton)this.findViewById(16908294);
        this.mTextTitle = (TextView)this.findViewById(16908310);
        this.mImageIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    public void setIcon(Drawable drawable2) {
        if (this.mImageIcon != null) {
            this.mImageIcon.setImageDrawable(drawable2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setMenuItemType(int n2) {
        int n3;
        int n4;
        int n5;
        this.mItemType = n2;
        if (this.mImageIcon == null) return;
        if (this.mTextTitle == null) {
            return;
        }
        switch (n2) {
            case 0: {
                return;
            }
            default: {
                n5 = this.getResources().getDimensionPixelOffset(R.dimen.tic_menu_item_content_margin_small);
                n2 = this.getResources().getDimensionPixelOffset(R.dimen.tic_menu_item_icon_padding_2);
                n4 = this.getResources().getDimensionPixelSize(R.dimen.tic_menu_item_title_size_2);
                n3 = this.getResources().getDimensionPixelSize(R.dimen.tic_menu_item_icon_size_2);
                break;
            }
            case 1: {
                n5 = this.getResources().getDimensionPixelOffset(R.dimen.tic_menu_item_content_margin_large);
                n2 = this.getResources().getDimensionPixelOffset(R.dimen.tic_menu_item_icon_padding_1);
                n4 = this.getResources().getDimensionPixelSize(R.dimen.tic_menu_item_title_size_1);
                n3 = this.getResources().getDimensionPixelSize(R.dimen.tic_menu_item_icon_size_1);
                break;
            }
            case 2: {
                n5 = this.getResources().getDimensionPixelOffset(R.dimen.tic_menu_item_content_margin_large);
                n2 = this.getResources().getDimensionPixelOffset(R.dimen.tic_menu_item_icon_padding_2);
                n4 = this.getResources().getDimensionPixelSize(R.dimen.tic_menu_item_title_size_2);
                n3 = this.getResources().getDimensionPixelSize(R.dimen.tic_menu_item_icon_size_2);
                break;
            }
            case 3: {
                n5 = this.getResources().getDimensionPixelOffset(R.dimen.tic_menu_item_content_margin_small);
                n2 = this.getResources().getDimensionPixelOffset(R.dimen.tic_menu_item_icon_padding_3);
                n4 = this.getResources().getDimensionPixelSize(R.dimen.tic_menu_item_title_size_3);
                n3 = this.getResources().getDimensionPixelSize(R.dimen.tic_menu_item_icon_size_3);
            }
        }
        this.mTextTitle.setTextSize(0, (float)n4);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)this.mTextTitle.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.topMargin = n5;
            layoutParams.bottomMargin = n5;
            this.mTextTitle.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        }
        this.mImageIcon.setPadding(n2, n2, n2, n2);
        this.mImageIcon.setMinimumWidth(n3);
        this.mImageIcon.setMinimumHeight(n3);
        layoutParams = (LinearLayout.LayoutParams)this.mImageIcon.getLayoutParams();
        if (layoutParams == null) return;
        layoutParams.width = n3;
        layoutParams.height = n3;
        layoutParams.topMargin = n5;
        layoutParams.bottomMargin = n5;
        layoutParams.leftMargin = n5;
        layoutParams.rightMargin = n5;
        this.mImageIcon.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        if (this.mImageIcon != null && this.mItemType != 0) {
            this.mImageIcon.setOnClickListener(onClickListener);
            return;
        }
        if (this.mImageIcon != null) {
            this.mImageIcon.setClickable(false);
        }
        super.setOnClickListener(onClickListener);
    }

    public void setTitle(CharSequence charSequence) {
        if (this.mTextTitle != null) {
            this.mTextTitle.setText(charSequence);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface MenuItemType {
    }
}

