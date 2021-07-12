/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package ticwear.design.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ticwear.design.preference.Preference;
import ticwear.design.preference.PreferenceViewHolder;

public abstract class TwoStatePreference
extends Preference {
    boolean mChecked;
    private boolean mCheckedSet;
    private boolean mDisableDependentsState;
    private CharSequence mSummaryOff;
    private CharSequence mSummaryOn;

    public TwoStatePreference(Context context) {
        this(context, null);
    }

    public TwoStatePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TwoStatePreference(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, 0);
    }

    public TwoStatePreference(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
    }

    public boolean getDisableDependentsState() {
        return this.mDisableDependentsState;
    }

    public CharSequence getSummaryOff() {
        return this.mSummaryOff;
    }

    public CharSequence getSummaryOn() {
        return this.mSummaryOn;
    }

    public boolean isChecked() {
        return this.mChecked;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onClick() {
        super.onClick();
        boolean bl2 = !this.isChecked();
        if (this.callChangeListener(bl2)) {
            this.setChecked(bl2);
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray typedArray, int n2) {
        return typedArray.getBoolean(n2, false);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable object) {
        if (object == null || !object.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState((Parcelable)object);
            return;
        }
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.setChecked(object.checked);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Object object = super.onSaveInstanceState();
        if (this.isPersistent()) {
            return object;
        }
        object = new SavedState((Parcelable)object);
        object.checked = this.isChecked();
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onSetInitialValue(boolean bl2, Object object) {
        bl2 = bl2 ? this.getPersistedBoolean(this.mChecked) : ((Boolean)object).booleanValue();
        this.setChecked(bl2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setChecked(boolean bl2) {
        boolean bl3 = this.mChecked != bl2;
        if (bl3 || !this.mCheckedSet) {
            this.mChecked = bl2;
            this.mCheckedSet = true;
            this.persistBoolean(bl2);
            if (bl3) {
                this.notifyDependencyChange(this.shouldDisableDependents());
                this.notifyChanged();
            }
        }
    }

    public void setDisableDependentsState(boolean bl2) {
        this.mDisableDependentsState = bl2;
    }

    public void setSummaryOff(int n2) {
        this.setSummaryOff(this.getContext().getString(n2));
    }

    public void setSummaryOff(CharSequence charSequence) {
        this.mSummaryOff = charSequence;
        if (!this.isChecked()) {
            this.notifyChanged();
        }
    }

    public void setSummaryOn(int n2) {
        this.setSummaryOn(this.getContext().getString(n2));
    }

    public void setSummaryOn(CharSequence charSequence) {
        this.mSummaryOn = charSequence;
        if (this.isChecked()) {
            this.notifyChanged();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean shouldDisableDependents() {
        boolean bl2;
        boolean bl3 = false;
        if (this.mDisableDependentsState) {
            bl2 = this.mChecked;
        } else {
            if (!this.mChecked) {
                return true;
            }
            bl2 = false;
        }
        if (bl2) return true;
        bl2 = bl3;
        if (!super.shouldDisableDependents()) return bl2;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    void syncSummaryView(View view) {
        if ((view = (TextView)view.findViewById(0x1020010)) != null) {
            int n2;
            int n3 = 1;
            if (this.mChecked && !TextUtils.isEmpty((CharSequence)this.mSummaryOn)) {
                view.setText(this.mSummaryOn);
                n2 = 0;
            } else {
                n2 = n3;
                if (!this.mChecked) {
                    n2 = n3;
                    if (!TextUtils.isEmpty((CharSequence)this.mSummaryOff)) {
                        view.setText(this.mSummaryOff);
                        n2 = 0;
                    }
                }
            }
            n3 = n2;
            if (n2 != 0) {
                CharSequence charSequence = this.getSummary();
                n3 = n2;
                if (!TextUtils.isEmpty((CharSequence)charSequence)) {
                    view.setText(charSequence);
                    n3 = 0;
                }
            }
            n2 = 8;
            if (n3 == 0) {
                n2 = 0;
            }
            if (n2 != view.getVisibility()) {
                view.setVisibility(n2);
            }
        }
    }

    static class SavedState
    extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n2) {
                return new SavedState[n2];
            }
        };
        boolean checked;

        /*
         * Enabled aggressive block sorting
         */
        public SavedState(Parcel parcel) {
            boolean bl2 = true;
            super(parcel);
            if (parcel.readInt() != 1) {
                bl2 = false;
            }
            this.checked = bl2;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        /*
         * Enabled aggressive block sorting
         */
        public void writeToParcel(Parcel parcel, int n2) {
            super.writeToParcel(parcel, n2);
            n2 = this.checked ? 1 : 0;
            parcel.writeInt(n2);
        }
    }

    static class ViewHolder
    extends Preference.ViewHolder {
        public ViewHolder(@NonNull ViewGroup viewGroup, @LayoutRes int n2, @LayoutRes int n3) {
            this(viewGroup, n2, n3, new PreferenceData());
        }

        public ViewHolder(@NonNull ViewGroup viewGroup, @LayoutRes int n2, @LayoutRes int n3, PreferenceData preferenceData) {
            super(viewGroup, n2, n3, preferenceData);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        protected void bindPreferenceToData(@NonNull Preference object) {
            boolean bl2;
            CharSequence charSequence;
            super.bindPreferenceToData((Preference)object);
            TwoStatePreference twoStatePreference = (TwoStatePreference)object;
            boolean bl3 = true;
            boolean bl4 = twoStatePreference.isChecked();
            CharSequence charSequence2 = null;
            if (bl4 && !TextUtils.isEmpty((CharSequence)twoStatePreference.getSummaryOn())) {
                charSequence = twoStatePreference.getSummaryOn();
                bl2 = false;
            } else {
                charSequence = charSequence2;
                bl2 = bl3;
                if (!bl4) {
                    charSequence = charSequence2;
                    bl2 = bl3;
                    if (!TextUtils.isEmpty((CharSequence)twoStatePreference.getSummaryOff())) {
                        charSequence = twoStatePreference.getSummaryOff();
                        bl2 = false;
                    }
                }
            }
            if (bl2) {
                charSequence = ((Preference)object).getSummary();
            }
            object = (PreferenceData)this.data;
            ((PreferenceData)object).isChecked = bl4;
            ((PreferenceData)object).summary = charSequence;
        }

        static class PreferenceData
        extends PreferenceViewHolder.PreferenceData {
            boolean isChecked;

            PreferenceData() {
            }
        }
    }
}

