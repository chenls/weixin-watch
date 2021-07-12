/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.util.Log
 */
package com.mobvoi.android.semantic;

import android.content.Intent;
import android.util.Log;
import com.mobvoi.android.semantic.DatetimeTagValue;
import com.mobvoi.android.semantic.EntityTagValue;

public class SemanticIntentApi {
    private static String a(Intent object, String string2) {
        if (object == null || string2 == null || string2.isEmpty()) {
            return null;
        }
        try {
            object = object.getStringExtra(string2);
            return object;
        }
        catch (Exception exception) {
            Log.e((String)"SemanticIntentApi", (String)("Error when extract String value for tag: " + string2 + " , err: " + exception.getMessage()));
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static DatetimeTagValue extractAsDatetime(Intent object, String string2) {
        Object var2_3 = null;
        string2 = SemanticIntentApi.a((Intent)object, string2);
        object = var2_3;
        if (string2 == null) return object;
        try {
            return DatetimeTagValue.a(string2);
        }
        catch (Exception exception) {
            Log.e((String)"DatetimeTagValue", (String)("Error in parsing DatetimeTagValue, err: " + exception.getMessage()));
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static EntityTagValue extractAsEntity(Intent object, String string2) {
        Object var2_3 = null;
        string2 = SemanticIntentApi.a((Intent)object, string2);
        object = var2_3;
        if (string2 == null) return object;
        try {
            return EntityTagValue.b(string2);
        }
        catch (Exception exception) {
            Log.e((String)"EntityTagValue", (String)("Error in parsing EntityTagValue, err: " + exception.getMessage()));
            return null;
        }
    }

    public static String getQuery(Intent object) {
        try {
            object = object.getStringExtra("query");
            return object;
        }
        catch (Exception exception) {
            Log.e((String)"SemanticIntentApi", (String)("Error to get raw user query, err: " + exception.getMessage()));
            return null;
        }
    }
}

