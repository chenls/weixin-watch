/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.util.AttributeSet
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package ticwear.design.preference;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.AttributeSet;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import ticwear.design.internal.XmlUtils;
import ticwear.design.preference.GenericInflater;
import ticwear.design.preference.Preference;
import ticwear.design.preference.PreferenceGroup;
import ticwear.design.preference.PreferenceManager;

class PreferenceInflater
extends GenericInflater<Preference, PreferenceGroup> {
    private static final String EXTRA_TAG_NAME = "extra";
    private static final String INTENT_TAG_NAME = "intent";
    private static final String TAG = "PreferenceInflater";
    private PreferenceManager mPreferenceManager;

    public PreferenceInflater(Context context, PreferenceManager preferenceManager) {
        super(context);
        this.init(preferenceManager);
    }

    PreferenceInflater(GenericInflater<Preference, PreferenceGroup> genericInflater, PreferenceManager preferenceManager, Context context) {
        super(genericInflater, context);
        this.init(preferenceManager);
    }

    private void init(PreferenceManager preferenceManager) {
        this.mPreferenceManager = preferenceManager;
        this.setDefaultPackage("android.preference.");
    }

    @Override
    public GenericInflater<Preference, PreferenceGroup> cloneInContext(Context context) {
        return new PreferenceInflater(this, this.mPreferenceManager, context);
    }

    @Override
    protected boolean onCreateCustomFromTag(XmlPullParser xmlPullParser, Preference object, AttributeSet attributeSet) throws XmlPullParserException {
        String string2 = xmlPullParser.getName();
        if (string2.equals(INTENT_TAG_NAME)) {
            try {
                xmlPullParser = Intent.parseIntent((Resources)this.getContext().getResources(), (XmlPullParser)xmlPullParser, (AttributeSet)attributeSet);
                if (xmlPullParser != null) {
                    object.setIntent((Intent)xmlPullParser);
                }
                return true;
            }
            catch (IOException iOException) {
                object = new XmlPullParserException("Error parsing preference");
                object.initCause(iOException);
                throw object;
            }
        }
        if (string2.equals(EXTRA_TAG_NAME)) {
            this.getContext().getResources().parseBundleExtra(EXTRA_TAG_NAME, attributeSet, object.getExtras());
            try {
                XmlUtils.skipCurrentTag(xmlPullParser);
                return true;
            }
            catch (IOException iOException) {
                object = new XmlPullParserException("Error parsing preference");
                object.initCause(iOException);
                throw object;
            }
        }
        return false;
    }

    @Override
    protected PreferenceGroup onMergeRoots(PreferenceGroup preferenceGroup, boolean bl2, PreferenceGroup preferenceGroup2) {
        if (preferenceGroup == null) {
            preferenceGroup2.onAttachedToHierarchy(this.mPreferenceManager);
            return preferenceGroup2;
        }
        return preferenceGroup;
    }
}

