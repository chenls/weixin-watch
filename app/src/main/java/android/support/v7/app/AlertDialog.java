/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
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
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.Button
 *  android.widget.ListAdapter
 *  android.widget.ListView
 */
package android.support.v7.app;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertController;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.appcompat.R;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class AlertDialog
extends AppCompatDialog
implements DialogInterface {
    static final int LAYOUT_HINT_NONE = 0;
    static final int LAYOUT_HINT_SIDE = 1;
    private AlertController mAlert;

    protected AlertDialog(Context context) {
        this(context, AlertDialog.resolveDialogTheme(context, 0), true);
    }

    protected AlertDialog(Context context, int n2) {
        this(context, n2, true);
    }

    AlertDialog(Context context, int n2, boolean bl2) {
        super(context, AlertDialog.resolveDialogTheme(context, n2));
        this.mAlert = new AlertController(this.getContext(), this, this.getWindow());
    }

    protected AlertDialog(Context context, boolean bl2, DialogInterface.OnCancelListener onCancelListener) {
        super(context, AlertDialog.resolveDialogTheme(context, 0));
        this.setCancelable(bl2);
        this.setOnCancelListener(onCancelListener);
        this.mAlert = new AlertController(context, this, this.getWindow());
    }

    static int resolveDialogTheme(Context context, int n2) {
        if (n2 >= 0x1000000) {
            return n2;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.alertDialogTheme, typedValue, true);
        return typedValue.resourceId;
    }

    public Button getButton(int n2) {
        return this.mAlert.getButton(n2);
    }

    public ListView getListView() {
        return this.mAlert.getListView();
    }

    @Override
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

    public void setButton(int n2, CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
        this.mAlert.setButton(n2, charSequence, onClickListener, null);
    }

    public void setButton(int n2, CharSequence charSequence, Message message) {
        this.mAlert.setButton(n2, charSequence, null, message);
    }

    void setButtonPanelLayoutHint(int n2) {
        this.mAlert.setButtonPanelLayoutHint(n2);
    }

    public void setCustomTitle(View view) {
        this.mAlert.setCustomTitle(view);
    }

    public void setIcon(int n2) {
        this.mAlert.setIcon(n2);
    }

    public void setIcon(Drawable drawable2) {
        this.mAlert.setIcon(drawable2);
    }

    public void setIconAttribute(int n2) {
        TypedValue typedValue = new TypedValue();
        this.getContext().getTheme().resolveAttribute(n2, typedValue, true);
        this.mAlert.setIcon(typedValue.resourceId);
    }

    public void setMessage(CharSequence charSequence) {
        this.mAlert.setMessage(charSequence);
    }

    @Override
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

    public static class Builder {
        private final AlertController.AlertParams P;
        private int mTheme;

        public Builder(Context context) {
            this(context, AlertDialog.resolveDialogTheme(context, 0));
        }

        public Builder(Context context, int n2) {
            this.P = new AlertController.AlertParams((Context)new ContextThemeWrapper(context, AlertDialog.resolveDialogTheme(context, n2)));
            this.mTheme = n2;
        }

        public AlertDialog create() {
            AlertDialog alertDialog = new AlertDialog(this.P.mContext, this.mTheme, false);
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

        public Builder setAdapter(ListAdapter listAdapter, DialogInterface.OnClickListener onClickListener) {
            this.P.mAdapter = listAdapter;
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

        public Builder setIcon(int n2) {
            this.P.mIconId = n2;
            return this;
        }

        public Builder setIcon(Drawable drawable2) {
            this.P.mIcon = drawable2;
            return this;
        }

        public Builder setIconAttribute(int n2) {
            TypedValue typedValue = new TypedValue();
            this.P.mContext.getTheme().resolveAttribute(n2, typedValue, true);
            this.P.mIconId = typedValue.resourceId;
            return this;
        }

        public Builder setInverseBackgroundForced(boolean bl2) {
            this.P.mForceInverseBackground = bl2;
            return this;
        }

        public Builder setItems(int n2, DialogInterface.OnClickListener onClickListener) {
            this.P.mItems = this.P.mContext.getResources().getTextArray(n2);
            this.P.mOnClickListener = onClickListener;
            return this;
        }

        public Builder setItems(CharSequence[] charSequenceArray, DialogInterface.OnClickListener onClickListener) {
            this.P.mItems = charSequenceArray;
            this.P.mOnClickListener = onClickListener;
            return this;
        }

        public Builder setMessage(int n2) {
            this.P.mMessage = this.P.mContext.getText(n2);
            return this;
        }

        public Builder setMessage(CharSequence charSequence) {
            this.P.mMessage = charSequence;
            return this;
        }

        public Builder setMultiChoiceItems(int n2, boolean[] blArray, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
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

        public Builder setNegativeButton(int n2, DialogInterface.OnClickListener onClickListener) {
            this.P.mNegativeButtonText = this.P.mContext.getText(n2);
            this.P.mNegativeButtonListener = onClickListener;
            return this;
        }

        public Builder setNegativeButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            this.P.mNegativeButtonText = charSequence;
            this.P.mNegativeButtonListener = onClickListener;
            return this;
        }

        public Builder setNeutralButton(int n2, DialogInterface.OnClickListener onClickListener) {
            this.P.mNeutralButtonText = this.P.mContext.getText(n2);
            this.P.mNeutralButtonListener = onClickListener;
            return this;
        }

        public Builder setNeutralButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            this.P.mNeutralButtonText = charSequence;
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

        public Builder setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
            this.P.mOnItemSelectedListener = onItemSelectedListener;
            return this;
        }

        public Builder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
            this.P.mOnKeyListener = onKeyListener;
            return this;
        }

        public Builder setPositiveButton(int n2, DialogInterface.OnClickListener onClickListener) {
            this.P.mPositiveButtonText = this.P.mContext.getText(n2);
            this.P.mPositiveButtonListener = onClickListener;
            return this;
        }

        public Builder setPositiveButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            this.P.mPositiveButtonText = charSequence;
            this.P.mPositiveButtonListener = onClickListener;
            return this;
        }

        public Builder setRecycleOnMeasureEnabled(boolean bl2) {
            this.P.mRecycleOnMeasure = bl2;
            return this;
        }

        public Builder setSingleChoiceItems(int n2, int n3, DialogInterface.OnClickListener onClickListener) {
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

        public Builder setSingleChoiceItems(ListAdapter listAdapter, int n2, DialogInterface.OnClickListener onClickListener) {
            this.P.mAdapter = listAdapter;
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

        public Builder setTitle(int n2) {
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

