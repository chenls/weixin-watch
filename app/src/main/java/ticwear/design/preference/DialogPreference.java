/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.widget.TextView
 */
package ticwear.design.preference;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import ticwear.design.R;
import ticwear.design.app.AlertDialog;
import ticwear.design.preference.Preference;
import ticwear.design.preference.PreferenceManager;

public abstract class DialogPreference
extends Preference
implements DialogInterface.OnClickListener,
DialogInterface.OnDismissListener,
PreferenceManager.OnActivityDestroyListener {
    private AlertDialog.Builder mBuilder;
    private Dialog mDialog;
    private Drawable mDialogIcon;
    private int mDialogLayoutResId;
    private CharSequence mDialogMessage;
    private CharSequence mDialogTitle;
    private Drawable mNegativeButtonIcon;
    private CharSequence mNegativeButtonText;
    private Drawable mPositiveButtonIcon;
    private CharSequence mPositiveButtonText;
    private int mWhichButtonClicked;

    public DialogPreference(Context context) {
        this(context, null);
    }

    public DialogPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0x1010091);
    }

    public DialogPreference(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, 0);
    }

    public DialogPreference(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.DialogPreference, n2, n3);
        this.mDialogTitle = context.getString(R.styleable.DialogPreference_android_dialogTitle);
        if (this.mDialogTitle == null) {
            this.mDialogTitle = this.getTitle();
        }
        this.mDialogMessage = context.getString(R.styleable.DialogPreference_android_dialogMessage);
        this.mDialogIcon = context.getDrawable(R.styleable.DialogPreference_android_dialogIcon);
        this.mPositiveButtonText = context.getString(R.styleable.DialogPreference_android_positiveButtonText);
        this.mNegativeButtonText = context.getString(R.styleable.DialogPreference_android_negativeButtonText);
        this.mPositiveButtonIcon = context.getDrawable(R.styleable.DialogPreference_tic_positiveButtonIcon);
        this.mNegativeButtonIcon = context.getDrawable(R.styleable.DialogPreference_tic_negativeButtonIcon);
        this.mDialogLayoutResId = context.getResourceId(R.styleable.DialogPreference_android_dialogLayout, this.mDialogLayoutResId);
        context.recycle();
    }

    private void requestInputMethod(Dialog dialog) {
        dialog.getWindow().setSoftInputMode(5);
    }

    public Dialog getDialog() {
        return this.mDialog;
    }

    public Drawable getDialogIcon() {
        return this.mDialogIcon;
    }

    public int getDialogLayoutResource() {
        return this.mDialogLayoutResId;
    }

    public CharSequence getDialogMessage() {
        return this.mDialogMessage;
    }

    public CharSequence getDialogTitle() {
        return this.mDialogTitle;
    }

    public Drawable getNegativeButtonIcon() {
        return this.mNegativeButtonIcon;
    }

    public CharSequence getNegativeButtonText() {
        return this.mNegativeButtonText;
    }

    public Drawable getPositiveButtonIcon() {
        return this.mPositiveButtonIcon;
    }

    public CharSequence getPositiveButtonText() {
        return this.mPositiveButtonText;
    }

    protected boolean needInputMethod() {
        return false;
    }

    @Override
    public void onActivityDestroy() {
        if (this.mDialog == null || !this.mDialog.isShowing()) {
            return;
        }
        this.mDialog.dismiss();
    }

    protected void onBindDialogView(View view) {
        if ((view = view.findViewById(R.id.message)) != null) {
            CharSequence charSequence = this.getDialogMessage();
            int n2 = 8;
            if (!TextUtils.isEmpty((CharSequence)charSequence)) {
                if (view instanceof TextView) {
                    ((TextView)view).setText(charSequence);
                }
                n2 = 0;
            }
            if (view.getVisibility() != n2) {
                view.setVisibility(n2);
            }
        }
    }

    @Override
    protected void onClick() {
        if (this.mDialog != null && this.mDialog.isShowing()) {
            return;
        }
        this.showDialog(null);
    }

    public void onClick(DialogInterface dialogInterface, int n2) {
        this.mWhichButtonClicked = n2;
    }

    protected View onCreateDialogView() {
        if (this.mDialogLayoutResId == 0) {
            return null;
        }
        return LayoutInflater.from((Context)this.mBuilder.getContext()).inflate(this.mDialogLayoutResId, null);
    }

    protected void onDialogClosed(boolean bl2) {
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onDismiss(DialogInterface dialogInterface) {
        this.getPreferenceManager().unregisterOnActivityDestroyListener(this);
        this.mDialog = null;
        boolean bl2 = this.mWhichButtonClicked == -1;
        this.onDialogClosed(bl2);
    }

    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onRestoreInstanceState(Parcelable object) {
        if (object == null || !object.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState((Parcelable)object);
            return;
        } else {
            SavedState savedState = (SavedState)((Object)object);
            super.onRestoreInstanceState(savedState.getSuperState());
            if (!savedState.isDialogShowing) return;
            this.showDialog(savedState.dialogBundle);
            return;
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Object object = super.onSaveInstanceState();
        if (this.mDialog == null || !this.mDialog.isShowing()) {
            return object;
        }
        object = new SavedState((Parcelable)object);
        object.isDialogShowing = true;
        object.dialogBundle = this.mDialog.onSaveInstanceState();
        return object;
    }

    public void setDialogIcon(int n2) {
        this.mDialogIcon = this.getContext().getDrawable(n2);
    }

    public void setDialogIcon(Drawable drawable2) {
        this.mDialogIcon = drawable2;
    }

    public void setDialogLayoutResource(int n2) {
        this.mDialogLayoutResId = n2;
    }

    public void setDialogMessage(int n2) {
        this.setDialogMessage(this.getContext().getString(n2));
    }

    public void setDialogMessage(CharSequence charSequence) {
        this.mDialogMessage = charSequence;
    }

    public void setDialogTitle(int n2) {
        this.setDialogTitle(this.getContext().getString(n2));
    }

    public void setDialogTitle(CharSequence charSequence) {
        this.mDialogTitle = charSequence;
    }

    public void setNegativeButtonIcon(int n2) {
        this.setNegativeButtonIcon(this.getContext().getDrawable(n2));
    }

    public void setNegativeButtonIcon(Drawable drawable2) {
        this.mNegativeButtonIcon = drawable2;
    }

    public void setNegativeButtonText(int n2) {
        this.setNegativeButtonText(this.getContext().getString(n2));
    }

    public void setNegativeButtonText(CharSequence charSequence) {
        this.mNegativeButtonText = charSequence;
    }

    public void setPositiveButtonIcon(int n2) {
        this.setPositiveButtonIcon(this.getContext().getDrawable(n2));
    }

    public void setPositiveButtonIcon(Drawable drawable2) {
        this.mPositiveButtonIcon = drawable2;
    }

    public void setPositiveButtonText(int n2) {
        this.setPositiveButtonText(this.getContext().getString(n2));
    }

    public void setPositiveButtonText(CharSequence charSequence) {
        this.mPositiveButtonText = charSequence;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void showDialog(Bundle bundle) {
        Context context = this.getContext();
        this.mWhichButtonClicked = -2;
        this.mBuilder = new AlertDialog.Builder(context).setTitle(this.mDialogTitle).setIcon(this.mDialogIcon).setPositiveButton(this.mPositiveButtonText, (DialogInterface.OnClickListener)this).setNegativeButton(this.mNegativeButtonText, (DialogInterface.OnClickListener)this).setPositiveButton(this.mNegativeButtonIcon, (DialogInterface.OnClickListener)this).setNegativeButton(this.mNegativeButtonIcon, (DialogInterface.OnClickListener)this);
        View view = this.onCreateDialogView();
        if (view != null) {
            this.onBindDialogView(view);
            this.mBuilder.setView(view);
        } else {
            this.mBuilder.setMessage(this.mDialogMessage);
        }
        this.onPrepareDialogBuilder(this.mBuilder);
        this.getPreferenceManager().registerOnActivityDestroyListener(this);
        AlertDialog alertDialog = this.mBuilder.create();
        this.mDialog = alertDialog;
        if (bundle != null) {
            alertDialog.onRestoreInstanceState(bundle);
        }
        if (this.needInputMethod()) {
            this.requestInputMethod(alertDialog);
        }
        alertDialog.setOnDismissListener(this);
        alertDialog.show();
    }

    private static class SavedState
    extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n2) {
                return new SavedState[n2];
            }
        };
        Bundle dialogBundle;
        boolean isDialogShowing;

        /*
         * Enabled aggressive block sorting
         */
        public SavedState(Parcel parcel) {
            boolean bl2 = true;
            super(parcel);
            if (parcel.readInt() != 1) {
                bl2 = false;
            }
            this.isDialogShowing = bl2;
            this.dialogBundle = parcel.readBundle();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        /*
         * Enabled aggressive block sorting
         */
        public void writeToParcel(Parcel parcel, int n2) {
            super.writeToParcel(parcel, n2);
            n2 = this.isDialogShowing ? 1 : 0;
            parcel.writeInt(n2);
            parcel.writeBundle(this.dialogBundle);
        }
    }
}

