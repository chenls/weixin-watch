/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.ViewGroup
 *  android.widget.Checkable
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.Switch
 */
package ticwear.design.preference;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.Switch;
import ticwear.design.R;
import ticwear.design.preference.Preference;
import ticwear.design.preference.PreferenceViewHolder;
import ticwear.design.preference.TwoStatePreference;

public class SwitchPreference
extends TwoStatePreference {
    private final Listener mListener = new Listener();
    private CharSequence mSwitchOff;
    private CharSequence mSwitchOn;

    public SwitchPreference(Context context) {
        this(context, null);
    }

    public SwitchPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843629);
    }

    public SwitchPreference(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, 0);
    }

    public SwitchPreference(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.SwitchPreference, n2, n3);
        this.setSummaryOn(context.getString(R.styleable.SwitchPreference_android_summaryOn));
        this.setSummaryOff(context.getString(R.styleable.SwitchPreference_android_summaryOff));
        this.setSwitchTextOn(context.getString(R.styleable.SwitchPreference_android_switchTextOn));
        this.setSwitchTextOff(context.getString(R.styleable.SwitchPreference_android_switchTextOff));
        this.setDisableDependentsState(context.getBoolean(R.styleable.SwitchPreference_android_disableDependentsState, false));
        context.recycle();
        this.mViewHolderCreator = new Preference.ViewHolderCreator(){

            @Override
            public Preference.ViewHolder create(@NonNull ViewGroup viewGroup, @LayoutRes int n2, @LayoutRes int n3) {
                return new ViewHolder(viewGroup, n2, n3);
            }
        };
    }

    public CharSequence getSwitchTextOff() {
        return this.mSwitchOff;
    }

    public CharSequence getSwitchTextOn() {
        return this.mSwitchOn;
    }

    public void setSwitchTextOff(int n2) {
        this.setSwitchTextOff(this.getContext().getString(n2));
    }

    public void setSwitchTextOff(CharSequence charSequence) {
        this.mSwitchOff = charSequence;
        this.notifyChanged();
    }

    public void setSwitchTextOn(int n2) {
        this.setSwitchTextOn(this.getContext().getString(n2));
    }

    public void setSwitchTextOn(CharSequence charSequence) {
        this.mSwitchOn = charSequence;
        this.notifyChanged();
    }

    private class Listener
    implements CompoundButton.OnCheckedChangeListener {
        private Listener() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onCheckedChanged(CompoundButton compoundButton, boolean bl2) {
            if (SwitchPreference.this.callChangeListener(bl2)) {
                SwitchPreference.this.setChecked(bl2);
                return;
            }
            bl2 = !bl2;
            compoundButton.setChecked(bl2);
        }
    }

    static class ViewHolder
    extends TwoStatePreference.ViewHolder {
        protected Checkable checkableView = (Checkable)this.findViewById(R.id.switchWidget);

        public ViewHolder(@NonNull ViewGroup viewGroup, @LayoutRes int n2, @LayoutRes int n3) {
            super(viewGroup, n2, n3, new PreferenceData());
        }

        @Override
        @CallSuper
        public void bind(@NonNull PreferenceViewHolder.PreferenceData preferenceData) {
            super.bind(preferenceData);
            if (this.checkableView != null) {
                if (this.checkableView instanceof CompoundButton) {
                    ((CompoundButton)this.checkableView).setOnCheckedChangeListener(null);
                }
                preferenceData = (PreferenceData)preferenceData;
                this.checkableView.setChecked(((PreferenceData)preferenceData).isChecked);
                if (this.checkableView instanceof Switch) {
                    Switch switch_ = (Switch)this.checkableView;
                    switch_.setTextOn(((PreferenceData)preferenceData).switchOn);
                    switch_.setTextOff(((PreferenceData)preferenceData).switchOff);
                }
                if (this.checkableView instanceof CompoundButton) {
                    ((CompoundButton)this.checkableView).setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)((PreferenceData)preferenceData).listener);
                }
            }
        }

        @Override
        @CallSuper
        public void bindPreferenceToData(@NonNull Preference preference) {
            super.bindPreferenceToData(preference);
            PreferenceData preferenceData = (PreferenceData)this.data;
            preference = (SwitchPreference)preference;
            preferenceData.switchOn = ((SwitchPreference)preference).mSwitchOn;
            preferenceData.switchOff = ((SwitchPreference)preference).mSwitchOff;
            preferenceData.listener = ((SwitchPreference)preference).mListener;
        }

        static class PreferenceData
        extends TwoStatePreference.ViewHolder.PreferenceData {
            Listener listener;
            CharSequence switchOff;
            CharSequence switchOn;

            PreferenceData() {
            }
        }
    }
}

