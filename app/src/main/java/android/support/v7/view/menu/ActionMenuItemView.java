/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.View$OnLongClickListener
 *  android.widget.Toast
 */
package android.support.v7.view.menu;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ListPopupWindow;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class ActionMenuItemView
extends AppCompatTextView
implements MenuView.ItemView,
View.OnClickListener,
View.OnLongClickListener,
ActionMenuView.ActionMenuChildView {
    private static final int MAX_ICON_SIZE = 32;
    private static final String TAG = "ActionMenuItemView";
    private boolean mAllowTextWithIcon;
    private boolean mExpandedFormat;
    private ListPopupWindow.ForwardingListener mForwardingListener;
    private Drawable mIcon;
    private MenuItemImpl mItemData;
    private MenuBuilder.ItemInvoker mItemInvoker;
    private int mMaxIconSize;
    private int mMinWidth;
    private PopupCallback mPopupCallback;
    private int mSavedPaddingLeft;
    private CharSequence mTitle;

    public ActionMenuItemView(Context context) {
        this(context, null);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        Resources resources = context.getResources();
        this.mAllowTextWithIcon = resources.getBoolean(R.bool.abc_config_allowActionMenuItemTextWithIcon);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.ActionMenuItemView, n2, 0);
        this.mMinWidth = context.getDimensionPixelSize(R.styleable.ActionMenuItemView_android_minWidth, 0);
        context.recycle();
        this.mMaxIconSize = (int)(32.0f * resources.getDisplayMetrics().density + 0.5f);
        this.setOnClickListener(this);
        this.setOnLongClickListener(this);
        this.mSavedPaddingLeft = -1;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateTextButtonVisibility() {
        boolean bl2;
        boolean bl3;
        block3: {
            block2: {
                boolean bl4 = false;
                bl3 = !TextUtils.isEmpty((CharSequence)this.mTitle);
                if (this.mIcon == null) break block2;
                bl2 = bl4;
                if (!this.mItemData.showsTextAsAction()) break block3;
                if (this.mAllowTextWithIcon) break block2;
                bl2 = bl4;
                if (!this.mExpandedFormat) break block3;
            }
            bl2 = true;
        }
        CharSequence charSequence = bl3 & bl2 ? this.mTitle : null;
        this.setText(charSequence);
    }

    @Override
    public MenuItemImpl getItemData() {
        return this.mItemData;
    }

    public boolean hasText() {
        return !TextUtils.isEmpty((CharSequence)this.getText());
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void initialize(MenuItemImpl menuItemImpl, int n2) {
        this.mItemData = menuItemImpl;
        this.setIcon(menuItemImpl.getIcon());
        this.setTitle(menuItemImpl.getTitleForItemView(this));
        this.setId(menuItemImpl.getItemId());
        n2 = menuItemImpl.isVisible() ? 0 : 8;
        this.setVisibility(n2);
        this.setEnabled(menuItemImpl.isEnabled());
        if (menuItemImpl.hasSubMenu() && this.mForwardingListener == null) {
            this.mForwardingListener = new ActionMenuItemForwardingListener();
        }
    }

    @Override
    public boolean needsDividerAfter() {
        return this.hasText();
    }

    @Override
    public boolean needsDividerBefore() {
        return this.hasText() && this.mItemData.getIcon() == null;
    }

    public void onClick(View view) {
        if (this.mItemInvoker != null) {
            this.mItemInvoker.invokeItem(this.mItemData);
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (Build.VERSION.SDK_INT >= 8) {
            super.onConfigurationChanged(configuration);
        }
        this.mAllowTextWithIcon = this.getContext().getResources().getBoolean(R.bool.abc_config_allowActionMenuItemTextWithIcon);
        this.updateTextButtonVisibility();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onLongClick(View view) {
        int n2;
        if (this.hasText()) {
            return false;
        }
        int[] nArray = new int[2];
        Rect rect = new Rect();
        this.getLocationOnScreen(nArray);
        this.getWindowVisibleDisplayFrame(rect);
        Context context = this.getContext();
        int n3 = this.getWidth();
        int n4 = this.getHeight();
        int n5 = nArray[1];
        int n6 = n4 / 2;
        n3 = n2 = nArray[0] + n3 / 2;
        if (ViewCompat.getLayoutDirection(view) == 0) {
            n3 = context.getResources().getDisplayMetrics().widthPixels - n2;
        }
        view = Toast.makeText((Context)context, (CharSequence)this.mItemData.getTitle(), (int)0);
        if (n5 + n6 < rect.height()) {
            view.setGravity(8388661, n3, nArray[1] + n4 - rect.top);
        } else {
            view.setGravity(81, 0, n4);
        }
        view.show();
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n2, int n3) {
        boolean bl2 = this.hasText();
        if (bl2 && this.mSavedPaddingLeft >= 0) {
            super.setPadding(this.mSavedPaddingLeft, this.getPaddingTop(), this.getPaddingRight(), this.getPaddingBottom());
        }
        super.onMeasure(n2, n3);
        int n4 = View.MeasureSpec.getMode((int)n2);
        n2 = View.MeasureSpec.getSize((int)n2);
        int n5 = this.getMeasuredWidth();
        n2 = n4 == Integer.MIN_VALUE ? Math.min(n2, this.mMinWidth) : this.mMinWidth;
        if (n4 != 0x40000000 && this.mMinWidth > 0 && n5 < n2) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec((int)n2, (int)0x40000000), n3);
        }
        if (!bl2 && this.mIcon != null) {
            super.setPadding((this.getMeasuredWidth() - this.mIcon.getBounds().width()) / 2, this.getPaddingTop(), this.getPaddingRight(), this.getPaddingBottom());
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mItemData.hasSubMenu() && this.mForwardingListener != null && this.mForwardingListener.onTouch((View)this, motionEvent)) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override
    public boolean prefersCondensedTitle() {
        return true;
    }

    @Override
    public void setCheckable(boolean bl2) {
    }

    @Override
    public void setChecked(boolean bl2) {
    }

    public void setExpandedFormat(boolean bl2) {
        if (this.mExpandedFormat != bl2) {
            this.mExpandedFormat = bl2;
            if (this.mItemData != null) {
                this.mItemData.actionFormatChanged();
            }
        }
    }

    @Override
    public void setIcon(Drawable drawable2) {
        this.mIcon = drawable2;
        if (drawable2 != null) {
            float f2;
            int n2;
            int n3 = drawable2.getIntrinsicWidth();
            int n4 = n2 = drawable2.getIntrinsicHeight();
            int n5 = n3;
            if (n3 > this.mMaxIconSize) {
                f2 = (float)this.mMaxIconSize / (float)n3;
                n5 = this.mMaxIconSize;
                n4 = (int)((float)n2 * f2);
            }
            n3 = n4;
            n2 = n5;
            if (n4 > this.mMaxIconSize) {
                f2 = (float)this.mMaxIconSize / (float)n4;
                n3 = this.mMaxIconSize;
                n2 = (int)((float)n5 * f2);
            }
            drawable2.setBounds(0, 0, n2, n3);
        }
        this.setCompoundDrawables(drawable2, null, null, null);
        this.updateTextButtonVisibility();
    }

    public void setItemInvoker(MenuBuilder.ItemInvoker itemInvoker) {
        this.mItemInvoker = itemInvoker;
    }

    public void setPadding(int n2, int n3, int n4, int n5) {
        this.mSavedPaddingLeft = n2;
        super.setPadding(n2, n3, n4, n5);
    }

    public void setPopupCallback(PopupCallback popupCallback) {
        this.mPopupCallback = popupCallback;
    }

    @Override
    public void setShortcut(boolean bl2, char c2) {
    }

    @Override
    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        this.setContentDescription(this.mTitle);
        this.updateTextButtonVisibility();
    }

    @Override
    public boolean showsIcon() {
        return true;
    }

    private class ActionMenuItemForwardingListener
    extends ListPopupWindow.ForwardingListener {
        public ActionMenuItemForwardingListener() {
            super((View)ActionMenuItemView.this);
        }

        @Override
        public ListPopupWindow getPopup() {
            if (ActionMenuItemView.this.mPopupCallback != null) {
                return ActionMenuItemView.this.mPopupCallback.getPopup();
            }
            return null;
        }

        @Override
        protected boolean onForwardingStarted() {
            boolean bl2;
            boolean bl3 = bl2 = false;
            if (ActionMenuItemView.this.mItemInvoker != null) {
                bl3 = bl2;
                if (ActionMenuItemView.this.mItemInvoker.invokeItem(ActionMenuItemView.this.mItemData)) {
                    ListPopupWindow listPopupWindow = this.getPopup();
                    bl3 = bl2;
                    if (listPopupWindow != null) {
                        bl3 = bl2;
                        if (listPopupWindow.isShowing()) {
                            bl3 = true;
                        }
                    }
                }
            }
            return bl3;
        }
    }

    public static abstract class PopupCallback {
        public abstract ListPopupWindow getPopup();
    }
}

