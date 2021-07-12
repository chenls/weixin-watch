/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources$NotFoundException
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.TypedValue
 */
package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

public class zzaf {
    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String zza(String string2, String string3, Context context, AttributeSet object, boolean bl2, boolean bl3, String string4) {
        void var3_10;
        void var5_13;
        void var6_14;
        string2 = object == null ? null : object.getAttributeValue(string2, string3);
        String string5 = string2;
        if (string2 != null) {
            String string6 = string2;
            if (string2.startsWith("@string/")) {
                void var4_12;
                String string7 = string2;
                if (var4_12 != false) {
                    String string8 = string2.substring("@string/".length());
                    String string9 = context.getPackageName();
                    TypedValue typedValue = new TypedValue();
                    try {
                        context.getResources().getValue(string9 + ":string/" + string8, typedValue, true);
                    }
                    catch (Resources.NotFoundException notFoundException) {
                        Log.w((String)var6_14, (String)("Could not find resource for " + string3 + ": " + string2));
                    }
                    if (typedValue.string != null) {
                        String string10 = typedValue.string.toString();
                    } else {
                        Log.w((String)var6_14, (String)("Resource " + string3 + " was not a string: " + typedValue));
                        String string11 = string2;
                    }
                }
            }
        }
        if (var5_13 != false && var3_10 == null) {
            Log.w((String)var6_14, (String)("Required XML attribute \"" + string3 + "\" missing"));
        }
        return var3_10;
    }
}

