/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnDismissListener
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
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import ticwear.design.R;
import ticwear.design.preference.Preference;
import ticwear.design.preference.PreferenceGroup;
import ticwear.design.preference.PreferenceGroupAdapter;
import ticwear.design.widget.FocusableLinearLayoutManager;
import ticwear.design.widget.TicklableRecyclerView;

public final class PreferenceScreen
extends PreferenceGroup
implements PreferenceGroupAdapter.OnPreferenceItemClickListener,
DialogInterface.OnDismissListener {
    private Dialog mDialog;
    private TicklableRecyclerView mListView;
    private PreferenceGroupAdapter mRootAdapter;

    public PreferenceScreen(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 16842891);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void showDialog(Bundle bundle) {
        Context context = this.getContext();
        if (this.mListView != null) {
            this.mListView.setAdapter(null);
        }
        View view = ((LayoutInflater)context.getSystemService("layout_inflater")).inflate(R.layout.preference_list_fragment, null);
        this.mListView = (TicklableRecyclerView)view.findViewById(16908298);
        this.mListView.setLayoutManager(new FocusableLinearLayoutManager(context));
        this.bind(this.mListView);
        this.bindTitle((TextView)view.findViewById(16908310));
        CharSequence charSequence = this.getTitle();
        context = new Dialog(context, R.style.Theme_Ticwear_Dialog);
        this.mDialog = context;
        if (TextUtils.isEmpty((CharSequence)charSequence)) {
            context.getWindow().requestFeature(1);
        } else {
            context.setTitle(charSequence);
        }
        context.setContentView(view);
        context.setOnDismissListener((DialogInterface.OnDismissListener)this);
        if (bundle != null) {
            context.onRestoreInstanceState(bundle);
        }
        this.getPreferenceManager().addPreferencesScreen((DialogInterface)context);
        context.show();
    }

    public void bind(TicklableRecyclerView ticklableRecyclerView) {
        PreferenceGroupAdapter preferenceGroupAdapter = this.getRootAdapter();
        preferenceGroupAdapter.setOnPreferenceItemClickListener(this);
        ticklableRecyclerView.setAdapter(preferenceGroupAdapter);
        this.onAttachedToActivity();
    }

    public void bindTitle(TextView textView) {
        CharSequence charSequence;
        block3: {
            block2: {
                charSequence = this.getTitle();
                if (textView == null) break block2;
                if (!TextUtils.isEmpty((CharSequence)charSequence)) break block3;
                textView.setVisibility(8);
            }
            return;
        }
        textView.setText(charSequence);
        textView.setVisibility(0);
    }

    public Dialog getDialog() {
        return this.mDialog;
    }

    public PreferenceGroupAdapter getRootAdapter() {
        if (this.mRootAdapter == null) {
            this.mRootAdapter = this.onCreateRootAdapter();
        }
        return this.mRootAdapter;
    }

    @Override
    protected boolean isOnSameScreenAsChildren() {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onClick() {
        if (this.getIntent() != null || this.getFragment() != null || this.getPreferenceCount() == 0 || this.mDialog != null) {
            return;
        }
        this.showDialog(null);
    }

    protected PreferenceGroupAdapter onCreateRootAdapter() {
        return new PreferenceGroupAdapter(this);
    }

    public void onDismiss(DialogInterface dialogInterface) {
        this.mDialog = null;
        this.getPreferenceManager().removePreferencesScreen(dialogInterface);
    }

    @Override
    public void onPreferenceItemClick(@NonNull Preference preference) {
        preference.performClick(this);
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
        Dialog dialog = this.mDialog;
        if (dialog == null || !dialog.isShowing()) {
            return object;
        }
        object = new SavedState((Parcelable)object);
        object.isDialogShowing = true;
        object.dialogBundle = dialog.onSaveInstanceState();
        return object;
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

