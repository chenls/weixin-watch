/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.ViewGroup
 *  android.widget.Checkable
 */
package ticwear.design.preference;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Checkable;
import ticwear.design.R;
import ticwear.design.preference.Preference;
import ticwear.design.preference.PreferenceViewHolder;
import ticwear.design.preference.TwoStatePreference;

public class CheckBoxPreference
extends TwoStatePreference {
    public CheckBoxPreference(Context context) {
        this(context, null);
    }

    public CheckBoxPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842895);
    }

    public CheckBoxPreference(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, 0);
    }

    public CheckBoxPreference(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.CheckBoxPreference, n2, n3);
        this.setSummaryOn(context.getString(R.styleable.CheckBoxPreference_android_summaryOn));
        this.setSummaryOff(context.getString(R.styleable.CheckBoxPreference_android_summaryOff));
        this.setDisableDependentsState(context.getBoolean(R.styleable.CheckBoxPreference_android_disableDependentsState, false));
        context.recycle();
        this.mViewHolderCreator = new Preference.ViewHolderCreator(){

            @Override
            public Preference.ViewHolder create(@NonNull ViewGroup viewGroup, @LayoutRes int n2, @LayoutRes int n3) {
                return new ViewHolder(viewGroup, n2, n3);
            }
        };
    }

    static class ViewHolder
    extends TwoStatePreference.ViewHolder {
        protected Checkable checkboxView = (Checkable)this.findViewById(0x1020001);

        public ViewHolder(@NonNull ViewGroup viewGroup, @LayoutRes int n2, @LayoutRes int n3) {
            super(viewGroup, n2, n3);
        }

        @Override
        @CallSuper
        public void bind(@NonNull PreferenceViewHolder.PreferenceData preferenceData) {
            super.bind(preferenceData);
            if (this.checkboxView != null) {
                this.checkboxView.setChecked(((TwoStatePreference.ViewHolder.PreferenceData)preferenceData).isChecked);
            }
        }
    }
}

