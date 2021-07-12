/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  android.util.AttributeSet
 */
package ticwear.design.preference;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import ticwear.design.R;
import ticwear.design.app.AlertDialog;
import ticwear.design.preference.DialogPreference;
import ticwear.design.preference.Preference;

public class ListPreference
extends DialogPreference {
    private int mClickedDialogEntryIndex;
    private CharSequence[] mEntries;
    private CharSequence[] mEntryValues;
    private String mSummary;
    private String mValue;
    private boolean mValueSet;

    public ListPreference(Context context) {
        this(context, null);
    }

    public ListPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0x1010091);
    }

    public ListPreference(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, 0);
    }

    public ListPreference(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ListPreference, n2, n3);
        this.mEntries = typedArray.getTextArray(R.styleable.ListPreference_android_entries);
        this.mEntryValues = typedArray.getTextArray(R.styleable.ListPreference_android_entryValues);
        typedArray.recycle();
        context = context.obtainStyledAttributes(attributeSet, R.styleable.Preference, n2, n3);
        this.mSummary = context.getString(R.styleable.Preference_android_summary);
        context.recycle();
    }

    static /* synthetic */ int access$002(ListPreference listPreference, int n2) {
        listPreference.mClickedDialogEntryIndex = n2;
        return n2;
    }

    private int getValueIndex() {
        return this.findIndexOfValue(this.mValue);
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

    public CharSequence getEntry() {
        int n2 = this.getValueIndex();
        if (n2 >= 0 && this.mEntries != null) {
            return this.mEntries[n2];
        }
        return null;
    }

    public CharSequence[] getEntryValues() {
        return this.mEntryValues;
    }

    @Override
    public CharSequence getSummary() {
        CharSequence charSequence = this.getEntry();
        if (this.mSummary == null) {
            return super.getSummary();
        }
        String string2 = this.mSummary;
        CharSequence charSequence2 = charSequence;
        if (charSequence == null) {
            charSequence2 = "";
        }
        return String.format(string2, charSequence2);
    }

    public String getValue() {
        return this.mValue;
    }

    @Override
    protected void onDialogClosed(boolean bl2) {
        String string2;
        super.onDialogClosed(bl2);
        if (this.mClickedDialogEntryIndex >= 0 && this.mEntryValues != null && this.callChangeListener(string2 = this.mEntryValues[this.mClickedDialogEntryIndex].toString())) {
            this.setValue(string2);
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray typedArray, int n2) {
        return typedArray.getString(n2);
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        super.onPrepareDialogBuilder(builder);
        if (this.mEntries == null || this.mEntryValues == null) {
            throw new IllegalStateException("ListPreference requires an entries array and an entryValues array.");
        }
        this.mClickedDialogEntryIndex = this.getValueIndex();
        builder.setSingleChoiceItems(this.mEntries, this.mClickedDialogEntryIndex, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n2) {
                ListPreference.access$002(ListPreference.this, n2);
                ListPreference.this.onClick(dialogInterface, -1);
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton((Drawable)null, null);
        builder.setPositiveButton((CharSequence)null, null);
        builder.setNegativeButton((Drawable)null, null);
        builder.setNegativeButton((CharSequence)null, null);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable object) {
        if (object == null || !object.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState((Parcelable)object);
            return;
        }
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.setValue(object.value);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Object object = super.onSaveInstanceState();
        if (this.isPersistent()) {
            return object;
        }
        object = new SavedState((Parcelable)object);
        object.value = this.getValue();
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onSetInitialValue(boolean bl2, Object object) {
        object = bl2 ? this.getPersistedString(this.mValue) : (String)object;
        this.setValue((String)object);
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

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setSummary(CharSequence charSequence) {
        super.setSummary(charSequence);
        if (charSequence == null && this.mSummary != null) {
            this.mSummary = null;
            return;
        } else {
            if (charSequence == null || charSequence.equals(this.mSummary)) return;
            this.mSummary = charSequence.toString();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setValue(String string2) {
        boolean bl2 = !TextUtils.equals((CharSequence)this.mValue, (CharSequence)string2);
        if (bl2 || !this.mValueSet) {
            this.mValue = string2;
            this.mValueSet = true;
            this.persistString(string2);
            if (bl2) {
                this.notifyChanged();
            }
        }
    }

    public void setValueIndex(int n2) {
        if (this.mEntryValues != null) {
            this.setValue(this.mEntryValues[n2].toString());
        }
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
        String value;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.value = parcel.readString();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int n2) {
            super.writeToParcel(parcel, n2);
            parcel.writeString(this.value);
        }
    }
}

