/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnMultiChoiceClickListener
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 */
package ticwear.design.preference;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import ticwear.design.R;
import ticwear.design.app.AlertDialog;
import ticwear.design.preference.DialogPreference;
import ticwear.design.preference.Preference;

public class MultiSelectListPreference
extends DialogPreference {
    private CharSequence[] mEntries;
    private CharSequence[] mEntryValues;
    private Set<String> mNewValues;
    private boolean mPreferenceChanged;
    private Set<String> mValues = new HashSet<String>();

    public MultiSelectListPreference(Context context) {
        this(context, null);
    }

    public MultiSelectListPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0x1010091);
    }

    public MultiSelectListPreference(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, 0);
    }

    public MultiSelectListPreference(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
        this.mNewValues = new HashSet<String>();
        context = context.obtainStyledAttributes(attributeSet, R.styleable.MultiSelectListPreference, n2, n3);
        this.mEntries = context.getTextArray(R.styleable.MultiSelectListPreference_android_entries);
        this.mEntryValues = context.getTextArray(R.styleable.MultiSelectListPreference_android_entryValues);
        context.recycle();
    }

    static /* synthetic */ boolean access$002(MultiSelectListPreference multiSelectListPreference, boolean bl2) {
        multiSelectListPreference.mPreferenceChanged = bl2;
        return bl2;
    }

    private boolean[] getSelectedItems() {
        CharSequence[] charSequenceArray = this.mEntryValues;
        int n2 = charSequenceArray.length;
        Set<String> set = this.mValues;
        boolean[] blArray = new boolean[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            blArray[i2] = set.contains(charSequenceArray[i2].toString());
        }
        return blArray;
    }

    public int findIndexOfValue(String string2) {
        if (string2 != null && this.mEntryValues != null) {
            for (int i2 = this.mEntryValues.length - 1; i2 >= 0; --i2) {
                if (!this.mEntryValues[i2].equals(string2)) continue;
                return i2;
            }
        }
        return -1;
    }

    public CharSequence[] getEntries() {
        return this.mEntries;
    }

    public CharSequence[] getEntryValues() {
        return this.mEntryValues;
    }

    public Set<String> getValues() {
        return this.mValues;
    }

    @Override
    protected void onDialogClosed(boolean bl2) {
        Set<String> set;
        super.onDialogClosed(bl2);
        if (this.mPreferenceChanged && this.callChangeListener(set = this.mNewValues)) {
            this.setValues(set);
        }
        this.mPreferenceChanged = false;
    }

    @Override
    protected Object onGetDefaultValue(TypedArray charSequenceArray, int n2) {
        charSequenceArray = charSequenceArray.getTextArray(n2);
        int n3 = charSequenceArray.length;
        HashSet<String> hashSet = new HashSet<String>();
        for (n2 = 0; n2 < n3; ++n2) {
            hashSet.add(charSequenceArray[n2].toString());
        }
        return hashSet;
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        super.onPrepareDialogBuilder(builder);
        if (this.mEntries == null || this.mEntryValues == null) {
            throw new IllegalStateException("MultiSelectListPreference requires an entries array and an entryValues array.");
        }
        boolean[] blArray = this.getSelectedItems();
        builder.setMultiChoiceItems(this.mEntries, blArray, new DialogInterface.OnMultiChoiceClickListener(){

            public void onClick(DialogInterface dialogInterface, int n2, boolean bl2) {
                if (bl2) {
                    MultiSelectListPreference.access$002(MultiSelectListPreference.this, MultiSelectListPreference.this.mPreferenceChanged | MultiSelectListPreference.this.mNewValues.add(MultiSelectListPreference.this.mEntryValues[n2].toString()));
                    return;
                }
                MultiSelectListPreference.access$002(MultiSelectListPreference.this, MultiSelectListPreference.this.mPreferenceChanged | MultiSelectListPreference.this.mNewValues.remove(MultiSelectListPreference.this.mEntryValues[n2].toString()));
            }
        });
        this.mNewValues.clear();
        this.mNewValues.addAll(this.mValues);
        builder.setPositiveButton((Drawable)null, null);
        builder.setPositiveButton((CharSequence)null, null);
        builder.setNegativeButton((Drawable)null, null);
        builder.setNegativeButton((CharSequence)null, null);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Object object = super.onSaveInstanceState();
        if (this.isPersistent()) {
            return object;
        }
        object = new SavedState((Parcelable)object);
        object.values = this.getValues();
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onSetInitialValue(boolean bl2, Object set) {
        if (bl2) {
            set = this.getPersistedStringSet(this.mValues);
        }
        this.setValues(set);
    }

    public void setEntries(int n2) {
        this.setEntries(this.getContext().getResources().getTextArray(n2));
    }

    public void setEntries(CharSequence[] charSequenceArray) {
        this.mEntries = charSequenceArray;
    }

    public void setEntryValues(int n2) {
        this.setEntryValues(this.getContext().getResources().getTextArray(n2));
    }

    public void setEntryValues(CharSequence[] charSequenceArray) {
        this.mEntryValues = charSequenceArray;
    }

    public void setValues(Set<String> set) {
        this.mValues.clear();
        this.mValues.addAll(set);
        this.persistStringSet(set);
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
        Set<String> values;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.values = new HashSet<String>();
            int n2 = parcel.readInt();
            if (n2 != -1) {
                String[] stringArray = new String[n2];
                parcel.readStringArray(stringArray);
                this.values.addAll(Arrays.asList(stringArray));
            }
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int n2) {
            super.writeToParcel(parcel, n2);
            parcel.writeStringArray(this.values.toArray(new String[0]));
        }
    }
}

