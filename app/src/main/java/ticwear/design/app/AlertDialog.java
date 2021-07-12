/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnClickListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.content.DialogInterface$OnKeyListener
 *  android.content.DialogInterface$OnMultiChoiceClickListener
 *  android.database.Cursor
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Message
 *  android.util.TypedValue
 *  android.view.ContextThemeWrapper
 *  android.view.KeyEvent
 *  android.view.View
 *  android.widget.Button
 *  android.widget.ImageButton
 */
package ticwear.design.app;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.ArrayRes;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import ticwear.design.internal.app.AlertController;
import ticwear.design.utils.ThemeUtils;
import ticwear.design.widget.TicklableRecyclerView;
import ticwear.design.widget.TrackSelectionAdapterWrapper;

public class AlertDialog
extends Dialog
implements DialogInterface {
    public static final int LAYOUT_HINT_NONE = 0;
    public static final int LAYOUT_HINT_SIDE = 1;
    private AlertController mAlert;

    protected AlertDialog(Context context) {
        this(context, 0);
    }

    protected AlertDialog(Context context, @StyleRes int n2) {
        super(context, AlertDialog.resolveDialogTheme(context, n2));
        ThemeUtils.checkDesignTheme(this.getContext());
        this.mAlert = new AlertController(this.getContext(), this, this.getWindow());
    }

    protected AlertDialog(Context context, boolean bl2, DialogInterface.OnCancelListener onCancelListener) {
        this(context, 0);
        this.setCancelable(bl2);
        this.setOnCancelListener(onCancelListener);
    }

    @StyleRes
    static int resolveDialogTheme(Context context, @StyleRes int n2) {
        if (n2 >= 0x1000000) {
            return n2;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16843529, typedValue, true);
        return typedValue.resourceId;
    }

    public Button getButton(int n2) {
        return this.mAlert.getButton(n2);
    }

    public ImageButton getIconButton(int n2) {
        return this.mAlert.getIconButton(n2);
    }

    public TicklableRecyclerView getListView() {
        return this.mAlert.getListView();
    }

    public void hideButtons() {
        this.mAlert.hideButtons();
    }

    public void minimizeButtons() {
        this.mAlert.minimizeButtons();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mAlert.installContent();
    }

    public boolean onKeyDown(int n2, KeyEvent keyEvent) {
        if (this.mAlert.onKeyDown(n2, keyEvent)) {
            return true;
        }
        return super.onKeyDown(n2, keyEvent);
    }

    public boolean onKeyUp(int n2, KeyEvent keyEvent) {
        if (this.mAlert.onKeyUp(n2, keyEvent)) {
            return true;
        }
        return super.onKeyUp(n2, keyEvent);
    }

    public void setButton(int n2, Drawable drawable2, DialogInterface.OnClickListener onClickListener) {
        this.mAlert.setButton(n2, null, drawable2, onClickListener, null);
    }

    public void setButton(int n2, Drawable drawable2, Message message) {
        this.mAlert.setButton(n2, null, drawable2, null, message);
    }

    public void setButton(int n2, CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
        this.mAlert.setButton(n2, charSequence, null, onClickListener, null);
    }

    public void setButton(int n2, CharSequence charSequence, Message message) {
        this.mAlert.setButton(n2, charSequence, null, null, message);
    }

    void setButtonPanelLayoutHint(int n2) {
        this.mAlert.setButtonPanelLayoutHint(n2);
    }

    public void setCustomTitle(View view) {
        this.mAlert.setCustomTitle(view);
    }

    public void setIcon(@DrawableRes int n2) {
        this.mAlert.setIcon(n2);
    }

    public void setIcon(Drawable drawable2) {
        this.mAlert.setIcon(drawable2);
    }

    public void setIconAttribute(@AttrRes int n2) {
        TypedValue typedValue = new TypedValue();
        this.getContext().getTheme().resolveAttribute(n2, typedValue, true);
        this.mAlert.setIcon(typedValue.resourceId);
    }

    public void setInverseBackgroundForced(boolean bl2) {
        this.mAlert.setInverseBackgroundForced(bl2);
    }

    public void setMessage(CharSequence charSequence) {
        this.mAlert.setMessage(charSequence);
    }

    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        this.mAlert.setTitle(charSequence);
    }

    public void setView(View view) {
        this.mAlert.setView(view);
    }

    public void setView(View view, int n2, int n3, int n4, int n5) {
        this.mAlert.setView(view, n2, n3, n4, n5);
    }

    public void showButtons() {
        this.mAlert.showButtons();
    }

    public void showButtonsDelayed() {
        this.mAlert.showButtonsDelayed();
    }

    public static class Builder {
        private final AlertController.AlertParams P;
        private int mTheme;

        public Builder(Context context) {
            this(context, 0);
        }

        public Builder(Context context, @StyleRes int n2) {
            this.P = new AlertController.AlertParams((Context)new ContextThemeWrapper(context, AlertDialog.resolveDialogTheme(context, n2)));
            this.mTheme = n2;
        }

        public AlertDialog create() {
            AlertDialog alertDialog = new AlertDialog(this.P.mContext, this.mTheme);
            this.P.apply(alertDialog.mAlert);
            alertDialog.setCancelable(this.P.mCancelable);
            if (this.P.mCancelable) {
                alertDialog.setCanceledOnTouchOutside(true);
            }
            alertDialog.setOnCancelListener(this.P.mOnCancelListener);
            alertDialog.setOnDismissListener(this.P.mOnDismissListener);
            if (this.P.mOnKeyListener != null) {
                alertDialog.setOnKeyListener(this.P.mOnKeyListener);
            }
            return alertDialog;
        }

        public Context getContext() {
            return this.P.mContext;
        }

        public Builder setAdapter(RecyclerView.Adapter adapter, DialogInterface.OnClickListener onClickListener) {
            this.P.mAdapter = adapter;
            this.P.mOnClickListener = onClickListener;
            return this;
        }

        public Builder setCancelable(boolean bl2) {
            this.P.mCancelable = bl2;
            return this;
        }

        public Builder setCursor(Cursor cursor, DialogInterface.OnClickListener onClickListener, String string2) {
            this.P.mCursor = cursor;
            this.P.mLabelColumn = string2;
            this.P.mOnClickListener = onClickListener;
            return this;
        }

        public Builder setCustomTitle(View view) {
            this.P.mCustomTitleView = view;
            return this;
        }

        public Builder setDelayConfirmAction(int n2, long l2) {
            this.P.mDelayConfirmRequest = new AlertController.DelayConfirmRequest(n2, l2);
            return this;
        }

        public Builder setIcon(@DrawableRes int n2) {
            this.P.mIconId = n2;
            return this;
        }

        public Builder setIcon(Drawable drawable2) {
            this.P.mIcon = drawable2;
            return this;
        }

        public Builder setIconAttribute(@AttrRes int n2) {
            TypedValue typedValue = new TypedValue();
            this.P.mContext.getTheme().resolveAttribute(n2, typedValue, true);
            this.P.mIconId = typedValue.resourceId;
            return this;
        }

        @Deprecated
        public Builder setInverseBackgroundForced(boolean bl2) {
            this.P.mForceInverseBackground = bl2;
            return this;
        }

        public Builder setItems(@ArrayRes int n2, DialogInterface.OnClickListener onClickListener) {
            this.P.mItems = this.P.mContext.getResources().getTextArray(n2);
            this.P.mOnClickListener = onClickListener;
            return this;
        }

        public Builder setItems(CharSequence[] charSequenceArray, DialogInterface.OnClickListener onClickListener) {
            this.P.mItems = charSequenceArray;
            this.P.mOnClickListener = onClickListener;
            return this;
        }

        public Builder setMessage(@StringRes int n2) {
            this.P.mMessage = this.P.mContext.getText(n2);
            return this;
        }

        public Builder setMessage(CharSequence charSequence) {
            this.P.mMessage = charSequence;
            return this;
        }

        public Builder setMultiChoiceItems(@ArrayRes int n2, boolean[] blArray, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
            this.P.mItems = this.P.mContext.getResources().getTextArray(n2);
            this.P.mOnCheckboxClickListener = onMultiChoiceClickListener;
            this.P.mCheckedItems = blArray;
            this.P.mIsMultiChoice = true;
            return this;
        }

        public Builder setMultiChoiceItems(Cursor cursor, String string2, String string3, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
            this.P.mCursor = cursor;
            this.P.mOnCheckboxClickListener = onMultiChoiceClickListener;
            this.P.mIsCheckedColumn = string2;
            this.P.mLabelColumn = string3;
            this.P.mIsMultiChoice = true;
            return this;
        }

        public Builder setMultiChoiceItems(CharSequence[] charSequenceArray, boolean[] blArray, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
            this.P.mItems = charSequenceArray;
            this.P.mOnCheckboxClickListener = onMultiChoiceClickListener;
            this.P.mCheckedItems = blArray;
            this.P.mIsMultiChoice = true;
            return this;
        }

        public Builder setNegativeButton(Drawable drawable2, DialogInterface.OnClickListener onClickListener) {
            this.P.mNegativeButtonIcon = drawable2;
            this.P.mNegativeButtonListener = onClickListener;
            return this;
        }

        public Builder setNegativeButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            this.P.mNegativeButtonText = charSequence;
            this.P.mNegativeButtonListener = onClickListener;
            return this;
        }

        public Builder setNegativeButtonIcon(@DrawableRes int n2, DialogInterface.OnClickListener onClickListener) {
            this.P.mNegativeButtonIcon = this.P.mContext.getDrawable(n2);
            this.P.mNegativeButtonListener = onClickListener;
            return this;
        }

        public Builder setNegativeButtonText(@StringRes int n2, DialogInterface.OnClickListener onClickListener) {
            this.P.mNegativeButtonText = this.P.mContext.getText(n2);
            this.P.mNegativeButtonListener = onClickListener;
            return this;
        }

        public Builder setNeutralButton(Drawable drawable2, DialogInterface.OnClickListener onClickListener) {
            this.P.mNeutralButtonIcon = drawable2;
            this.P.mNeutralButtonListener = onClickListener;
            return this;
        }

        public Builder setNeutralButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            this.P.mNeutralButtonText = charSequence;
            this.P.mNeutralButtonListener = onClickListener;
            return this;
        }

        public Builder setNeutralButtonIcon(@DrawableRes int n2, DialogInterface.OnClickListener onClickListener) {
            this.P.mNeutralButtonIcon = this.P.mContext.getDrawable(n2);
            this.P.mNeutralButtonListener = onClickListener;
            return this;
        }

        public Builder setNeutralButtonText(@StringRes int n2, DialogInterface.OnClickListener onClickListener) {
            this.P.mNeutralButtonText = this.P.mContext.getText(n2);
            this.P.mNeutralButtonListener = onClickListener;
            return this;
        }

        public Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            this.P.mOnCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.P.mOnDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnItemSelectedListener(TrackSelectionAdapterWrapper.OnItemSelectedListener onItemSelectedListener) {
            this.P.mOnItemSelectedListener = onItemSelectedListener;
            return this;
        }

        public Builder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
            this.P.mOnKeyListener = onKeyListener;
            return this;
        }

        public Builder setPositiveButton(Drawable drawable2, DialogInterface.OnClickListener onClickListener) {
            this.P.mPositiveButtonIcon = drawable2;
            this.P.mPositiveButtonListener = onClickListener;
            return this;
        }

        public Builder setPositiveButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            this.P.mPositiveButtonText = charSequence;
            this.P.mPositiveButtonListener = onClickListener;
            return this;
        }

        public Builder setPositiveButtonIcon(@DrawableRes int n2, DialogInterface.OnClickListener onClickListener) {
            this.P.mPositiveButtonIcon = this.P.mContext.getDrawable(n2);
            this.P.mPositiveButtonListener = onClickListener;
            return this;
        }

        public Builder setPositiveButtonText(@StringRes int n2, DialogInterface.OnClickListener onClickListener) {
            this.P.mPositiveButtonText = this.P.mContext.getText(n2);
            this.P.mPositiveButtonListener = onClickListener;
            return this;
        }

        public Builder setSingleChoiceItems(@ArrayRes int n2, int n3, DialogInterface.OnClickListener onClickListener) {
            this.P.mItems = this.P.mContext.getResources().getTextArray(n2);
            this.P.mOnClickListener = onClickListener;
            this.P.mCheckedItem = n3;
            this.P.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(Cursor cursor, int n2, String string2, DialogInterface.OnClickListener onClickListener) {
            this.P.mCursor = cursor;
            this.P.mOnClickListener = onClickListener;
            this.P.mCheckedItem = n2;
            this.P.mLabelColumn = string2;
            this.P.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(RecyclerView.Adapter adapter, int n2, DialogInterface.OnClickListener onClickListener) {
            this.P.mAdapter = adapter;
            this.P.mOnClickListener = onClickListener;
            this.P.mCheckedItem = n2;
            this.P.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(CharSequence[] charSequenceArray, int n2, DialogInterface.OnClickListener onClickListener) {
            this.P.mItems = charSequenceArray;
            this.P.mOnClickListener = onClickListener;
            this.P.mCheckedItem = n2;
            this.P.mIsSingleChoice = true;
            return this;
        }

        public Builder setTitle(@StringRes int n2) {
            this.P.mTitle = this.P.mContext.getText(n2);
            return this;
        }

        public Builder setTitle(CharSequence charSequence) {
            this.P.mTitle = charSequence;
            return this;
        }

        public Builder setView(int n2) {
            this.P.mView = null;
            this.P.mViewLayoutResId = n2;
            this.P.mViewSpacingSpecified = false;
            return this;
        }

        public Builder setView(View view) {
            this.P.mView = view;
            this.P.mViewLayoutResId = 0;
            this.P.mViewSpacingSpecified = false;
            return this;
        }

        @Deprecated
        public Builder setView(View view, int n2, int n3, int n4, int n5) {
            this.P.mView = view;
            this.P.mViewLayoutResId = 0;
            this.P.mViewSpacingSpecified = true;
            this.P.mViewSpacingLeft = n2;
            this.P.mViewSpacingTop = n3;
            this.P.mViewSpacingRight = n4;
            this.P.mViewSpacingBottom = n5;
            return this;
        }

        public AlertDialog show() {
            AlertDialog alertDialog = this.create();
            alertDialog.show();
            return alertDialog;
        }
    }
}

