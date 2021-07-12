/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 */
package ticwear.design.preference;

import android.content.Context;
import android.util.AttributeSet;
import ticwear.design.preference.Preference;
import ticwear.design.preference.PreferenceGroup;

public class PreferenceCategory
extends PreferenceGroup {
    private static final String TAG = "PreferenceCategory";

    public PreferenceCategory(Context context) {
        this(context, null);
    }

    public PreferenceCategory(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842892);
    }

    public PreferenceCategory(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, 0);
    }

    public PreferenceCategory(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    protected boolean onPrepareAddPreference(Preference preference) {
        if (preference instanceof PreferenceCategory) {
            throw new IllegalArgumentException("Cannot add a PreferenceCategory directly to a PreferenceCategory");
        }
        return super.onPrepareAddPreference(preference);
    }

    @Override
    public boolean shouldDisableDependents() {
        return !super.isEnabled();
    }
}

