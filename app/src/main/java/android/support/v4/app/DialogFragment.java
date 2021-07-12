/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 */
package android.support.v4.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;

public class DialogFragment
extends Fragment
implements DialogInterface.OnCancelListener,
DialogInterface.OnDismissListener {
    private static final String SAVED_BACK_STACK_ID = "android:backStackId";
    private static final String SAVED_CANCELABLE = "android:cancelable";
    private static final String SAVED_DIALOG_STATE_TAG = "android:savedDialogState";
    private static final String SAVED_SHOWS_DIALOG = "android:showsDialog";
    private static final String SAVED_STYLE = "android:style";
    private static final String SAVED_THEME = "android:theme";
    public static final int STYLE_NORMAL = 0;
    public static final int STYLE_NO_FRAME = 2;
    public static final int STYLE_NO_INPUT = 3;
    public static final int STYLE_NO_TITLE = 1;
    int mBackStackId = -1;
    boolean mCancelable = true;
    Dialog mDialog;
    boolean mDismissed;
    boolean mShownByMe;
    boolean mShowsDialog = true;
    int mStyle = 0;
    int mTheme = 0;
    boolean mViewDestroyed;

    public void dismiss() {
        this.dismissInternal(false);
    }

    public void dismissAllowingStateLoss() {
        this.dismissInternal(true);
    }

    void dismissInternal(boolean bl2) {
        if (this.mDismissed) {
            return;
        }
        this.mDismissed = true;
        this.mShownByMe = false;
        if (this.mDialog != null) {
            this.mDialog.dismiss();
            this.mDialog = null;
        }
        this.mViewDestroyed = true;
        if (this.mBackStackId >= 0) {
            this.getFragmentManager().popBackStack(this.mBackStackId, 1);
            this.mBackStackId = -1;
            return;
        }
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.remove(this);
        if (bl2) {
            fragmentTransaction.commitAllowingStateLoss();
            return;
        }
        fragmentTransaction.commit();
    }

    public Dialog getDialog() {
        return this.mDialog;
    }

    @Override
    public LayoutInflater getLayoutInflater(Bundle bundle) {
        if (!this.mShowsDialog) {
            return super.getLayoutInflater(bundle);
        }
        this.mDialog = this.onCreateDialog(bundle);
        if (this.mDialog != null) {
            this.setupDialog(this.mDialog, this.mStyle);
            return (LayoutInflater)this.mDialog.getContext().getSystemService("layout_inflater");
        }
        return (LayoutInflater)this.mHost.getContext().getSystemService("layout_inflater");
    }

    public boolean getShowsDialog() {
        return this.mShowsDialog;
    }

    @StyleRes
    public int getTheme() {
        return this.mTheme;
    }

    public boolean isCancelable() {
        return this.mCancelable;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onActivityCreated(Bundle bundle) {
        block7: {
            block6: {
                FragmentActivity fragmentActivity;
                super.onActivityCreated(bundle);
                if (!this.mShowsDialog) break block6;
                View view = this.getView();
                if (view != null) {
                    if (view.getParent() != null) {
                        throw new IllegalStateException("DialogFragment can not be attached to a container view");
                    }
                    this.mDialog.setContentView(view);
                }
                if ((fragmentActivity = this.getActivity()) != null) {
                    this.mDialog.setOwnerActivity((Activity)fragmentActivity);
                }
                this.mDialog.setCancelable(this.mCancelable);
                this.mDialog.setOnCancelListener((DialogInterface.OnCancelListener)this);
                this.mDialog.setOnDismissListener((DialogInterface.OnDismissListener)this);
                if (bundle != null && (bundle = bundle.getBundle(SAVED_DIALOG_STATE_TAG)) != null) break block7;
            }
            return;
        }
        this.mDialog.onRestoreInstanceState(bundle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!this.mShownByMe) {
            this.mDismissed = false;
        }
    }

    public void onCancel(DialogInterface dialogInterface) {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        boolean bl2 = this.mContainerId == 0;
        this.mShowsDialog = bl2;
        if (bundle != null) {
            this.mStyle = bundle.getInt(SAVED_STYLE, 0);
            this.mTheme = bundle.getInt(SAVED_THEME, 0);
            this.mCancelable = bundle.getBoolean(SAVED_CANCELABLE, true);
            this.mShowsDialog = bundle.getBoolean(SAVED_SHOWS_DIALOG, this.mShowsDialog);
            this.mBackStackId = bundle.getInt(SAVED_BACK_STACK_ID, -1);
        }
    }

    @NonNull
    public Dialog onCreateDialog(Bundle bundle) {
        return new Dialog((Context)this.getActivity(), this.getTheme());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (this.mDialog != null) {
            this.mViewDestroyed = true;
            this.mDialog.dismiss();
            this.mDialog = null;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (!this.mShownByMe && !this.mDismissed) {
            this.mDismissed = true;
        }
    }

    public void onDismiss(DialogInterface dialogInterface) {
        if (!this.mViewDestroyed) {
            this.dismissInternal(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        Bundle bundle2;
        super.onSaveInstanceState(bundle);
        if (this.mDialog != null && (bundle2 = this.mDialog.onSaveInstanceState()) != null) {
            bundle.putBundle(SAVED_DIALOG_STATE_TAG, bundle2);
        }
        if (this.mStyle != 0) {
            bundle.putInt(SAVED_STYLE, this.mStyle);
        }
        if (this.mTheme != 0) {
            bundle.putInt(SAVED_THEME, this.mTheme);
        }
        if (!this.mCancelable) {
            bundle.putBoolean(SAVED_CANCELABLE, this.mCancelable);
        }
        if (!this.mShowsDialog) {
            bundle.putBoolean(SAVED_SHOWS_DIALOG, this.mShowsDialog);
        }
        if (this.mBackStackId != -1) {
            bundle.putInt(SAVED_BACK_STACK_ID, this.mBackStackId);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (this.mDialog != null) {
            this.mViewDestroyed = false;
            this.mDialog.show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (this.mDialog != null) {
            this.mDialog.hide();
        }
    }

    public void setCancelable(boolean bl2) {
        this.mCancelable = bl2;
        if (this.mDialog != null) {
            this.mDialog.setCancelable(bl2);
        }
    }

    public void setShowsDialog(boolean bl2) {
        this.mShowsDialog = bl2;
    }

    public void setStyle(int n2, @StyleRes int n3) {
        this.mStyle = n2;
        if (this.mStyle == 2 || this.mStyle == 3) {
            this.mTheme = 16973913;
        }
        if (n3 != 0) {
            this.mTheme = n3;
        }
    }

    public void setupDialog(Dialog dialog, int n2) {
        switch (n2) {
            default: {
                return;
            }
            case 3: {
                dialog.getWindow().addFlags(24);
            }
            case 1: 
            case 2: 
        }
        dialog.requestWindowFeature(1);
    }

    public int show(FragmentTransaction fragmentTransaction, String string2) {
        this.mDismissed = false;
        this.mShownByMe = true;
        fragmentTransaction.add(this, string2);
        this.mViewDestroyed = false;
        this.mBackStackId = fragmentTransaction.commit();
        return this.mBackStackId;
    }

    public void show(FragmentManager object, String string2) {
        this.mDismissed = false;
        this.mShownByMe = true;
        object = ((FragmentManager)object).beginTransaction();
        ((FragmentTransaction)object).add(this, string2);
        ((FragmentTransaction)object).commit();
    }
}

