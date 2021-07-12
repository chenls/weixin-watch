/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.view.ViewGroup
 */
package ticwear.design.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.ViewGroup;
import ticwear.design.R;
import ticwear.design.preference.Preference;
import ticwear.design.preference.PreferenceViewHolder;
import ticwear.design.widget.VolumeBar;

public class VolumePreference
extends Preference {
    private VolumeBar.OnVolumeChangedListener mInternalVolumeChangedListener;
    private VolumeBar.OnVolumeChangedListener mVolumeChangeListener;
    private int volume;

    public VolumePreference(Context context) {
        this(context, null);
    }

    public VolumePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VolumePreference(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, R.style.Preference_Ticwear_VolumePreference);
        this.mInternalVolumeChangedListener = new VolumeBar.OnVolumeChangedListener(){

            @Override
            public void onVolumeChanged(VolumeBar volumeBar, int n2, boolean bl2) {
                VolumePreference.access$002(VolumePreference.this, volumeBar.getProgress());
                if (bl2) {
                    VolumePreference.this.persistInt(VolumePreference.this.volume);
                }
                VolumePreference.this.setVolume(volumeBar.getProgress());
                if (VolumePreference.this.mVolumeChangeListener != null) {
                    VolumePreference.this.mVolumeChangeListener.onVolumeChanged(volumeBar, n2, bl2);
                }
            }
        };
    }

    public VolumePreference(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
        context.obtainStyledAttributes(attributeSet, R.styleable.Preference, n2, n3).recycle();
        this.mViewHolderCreator = new Preference.ViewHolderCreator(){

            @Override
            public Preference.ViewHolder create(@NonNull ViewGroup viewGroup, @LayoutRes int n2, @LayoutRes int n3) {
                return new ViewHolder(viewGroup, n2, n3);
            }
        };
    }

    static /* synthetic */ int access$002(VolumePreference volumePreference, int n2) {
        volumePreference.volume = n2;
        return n2;
    }

    @Override
    protected Object onGetDefaultValue(TypedArray typedArray, int n2) {
        return typedArray.getInt(n2, 0);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable object) {
        if (object == null || !object.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState((Parcelable)object);
            return;
        }
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.setVolume(object.volume);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Object object = super.onSaveInstanceState();
        if (this.isPersistent()) {
            return object;
        }
        object = new SavedState((Parcelable)object);
        object.volume = this.volume;
        return object;
    }

    @Override
    protected void onSetInitialValue(boolean bl2, Object object) {
        this.setVolume(this.getPersistedInt(0));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void setVolume(int n2) {
        if (this.volume == n2) return;
        boolean bl2 = true;
        if (!bl2) return;
        this.volume = n2;
        this.persistInt(this.volume);
        this.notifyChanged();
    }

    public void setVolumeChangedListener(VolumeBar.OnVolumeChangedListener onVolumeChangedListener) {
        this.mVolumeChangeListener = onVolumeChangedListener;
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
        int volume;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.volume = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int n2) {
            super.writeToParcel(parcel, n2);
            parcel.writeInt(this.volume);
        }
    }

    static class ViewHolder
    extends Preference.ViewHolder {
        protected VolumeBar volumeBar = (VolumeBar)((Object)this.findViewById(R.id.volume_seekbar));

        public ViewHolder(@NonNull ViewGroup viewGroup, @LayoutRes int n2, @LayoutRes int n3) {
            this(viewGroup, n2, n3, new PreferenceData());
        }

        public ViewHolder(@NonNull ViewGroup viewGroup, @LayoutRes int n2, @LayoutRes int n3, PreferenceData preferenceData) {
            super(viewGroup, n2, n3, preferenceData);
        }

        @Override
        @CallSuper
        public void bind(@NonNull PreferenceViewHolder.PreferenceData preferenceData) {
            super.bind(preferenceData);
            preferenceData = (PreferenceData)preferenceData;
            if (this.volumeBar != null) {
                this.volumeBar.setOnVolumeChangedListetener(((PreferenceData)preferenceData).volumeChangedListener);
                this.volumeBar.setProgress(((PreferenceData)preferenceData).volume);
            }
        }

        @Override
        protected void bindPreferenceToData(@NonNull Preference preference) {
            super.bindPreferenceToData(preference);
            preference = (VolumePreference)preference;
            PreferenceData preferenceData = (PreferenceData)this.data;
            preferenceData.volume = ((VolumePreference)preference).volume;
            preferenceData.volumeChangedListener = ((VolumePreference)preference).mInternalVolumeChangedListener;
        }

        protected static class PreferenceData
        extends PreferenceViewHolder.PreferenceData {
            protected int volume;
            protected VolumeBar.OnVolumeChangedListener volumeChangedListener;

            protected PreferenceData() {
            }
        }
    }
}

