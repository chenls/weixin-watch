/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.text.TextUtils
 *  android.util.AttributeSet
 */
package ticwear.design.preference;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ticwear.design.R;
import ticwear.design.preference.GenericInflater;
import ticwear.design.preference.Preference;

public abstract class PreferenceGroup
extends Preference
implements GenericInflater.Parent<Preference> {
    private boolean mAttachedToActivity = false;
    private int mCurrentPreferenceOrder = 0;
    private boolean mOrderingAsAdded = true;
    private List<Preference> mPreferenceList = new ArrayList<Preference>();

    public PreferenceGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PreferenceGroup(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, 0);
    }

    public PreferenceGroup(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.PreferenceGroup, n2, n3);
        this.mOrderingAsAdded = context.getBoolean(R.styleable.PreferenceGroup_android_orderingFromXml, this.mOrderingAsAdded);
        context.recycle();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean removePreferenceInt(Preference preference) {
        synchronized (this) {
            preference.onPrepareForRemoval();
            return this.mPreferenceList.remove(preference);
        }
    }

    @Override
    public void addItemFromInflater(Preference preference) {
        this.addPreference(preference);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean addPreference(Preference preference) {
        int n2;
        int n3;
        if (this.mPreferenceList.contains(preference)) {
            return true;
        }
        if (preference.getOrder() == Integer.MAX_VALUE) {
            if (this.mOrderingAsAdded) {
                n3 = this.mCurrentPreferenceOrder;
                this.mCurrentPreferenceOrder = n3 + 1;
                preference.setOrder(n3);
            }
            if (preference instanceof PreferenceGroup) {
                ((PreferenceGroup)preference).setOrderingAsAdded(this.mOrderingAsAdded);
            }
        }
        n3 = n2 = Collections.binarySearch(this.mPreferenceList, preference);
        if (n2 < 0) {
            n3 = n2 * -1 - 1;
        }
        if (!this.onPrepareAddPreference(preference)) {
            return false;
        }
        synchronized (this) {
            this.mPreferenceList.add(n3, preference);
        }
        preference.onAttachedToHierarchy(this.getPreferenceManager());
        if (this.mAttachedToActivity) {
            preference.onAttachedToActivity();
        }
        this.notifyHierarchyChanged();
        return true;
    }

    @Override
    protected void dispatchRestoreInstanceState(Bundle bundle) {
        super.dispatchRestoreInstanceState(bundle);
        int n2 = this.getPreferenceCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            this.getPreference(i2).dispatchRestoreInstanceState(bundle);
        }
    }

    @Override
    protected void dispatchSaveInstanceState(Bundle bundle) {
        super.dispatchSaveInstanceState(bundle);
        int n2 = this.getPreferenceCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            this.getPreference(i2).dispatchSaveInstanceState(bundle);
        }
    }

    public Preference findPreference(CharSequence charSequence) {
        if (TextUtils.equals((CharSequence)this.getKey(), (CharSequence)charSequence)) {
            return this;
        }
        int n2 = this.getPreferenceCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            Preference preference = this.getPreference(i2);
            String string2 = preference.getKey();
            if (string2 != null && string2.equals(charSequence)) {
                return preference;
            }
            if (!(preference instanceof PreferenceGroup) || (preference = ((PreferenceGroup)preference).findPreference(charSequence)) == null) continue;
            return preference;
        }
        return null;
    }

    public Preference getPreference(int n2) {
        return this.mPreferenceList.get(n2);
    }

    public int getPreferenceCount() {
        return this.mPreferenceList.size();
    }

    protected boolean isOnSameScreenAsChildren() {
        return true;
    }

    public boolean isOrderingAsAdded() {
        return this.mOrderingAsAdded;
    }

    @Override
    public void notifyDependencyChange(boolean bl2) {
        super.notifyDependencyChange(bl2);
        int n2 = this.getPreferenceCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            this.getPreference(i2).onParentChanged(this, bl2);
        }
    }

    @Override
    protected void onAttachedToActivity() {
        super.onAttachedToActivity();
        this.mAttachedToActivity = true;
        int n2 = this.getPreferenceCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            this.getPreference(i2).onAttachedToActivity();
        }
    }

    protected boolean onPrepareAddPreference(Preference preference) {
        preference.onParentChanged(this, this.shouldDisableDependents());
        return true;
    }

    @Override
    protected void onPrepareForRemoval() {
        super.onPrepareForRemoval();
        this.mAttachedToActivity = false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeAll() {
        synchronized (this) {
            List<Preference> list = this.mPreferenceList;
            int n2 = list.size() - 1;
            while (true) {
                if (n2 < 0) {
                    // MONITOREXIT @DISABLED, blocks:[3, 4, 5] lbl6 : MonitorExitStatement: MONITOREXIT : this
                    this.notifyHierarchyChanged();
                    return;
                }
                this.removePreferenceInt(list.get(0));
                --n2;
            }
        }
    }

    public boolean removePreference(Preference preference) {
        boolean bl2 = this.removePreferenceInt(preference);
        this.notifyHierarchyChanged();
        return bl2;
    }

    public void setOrderingAsAdded(boolean bl2) {
        this.mOrderingAsAdded = bl2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void sortPreferences() {
        synchronized (this) {
            Collections.sort(this.mPreferenceList);
            return;
        }
    }
}

