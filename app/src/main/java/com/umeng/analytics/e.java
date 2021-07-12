/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 */
package com.umeng.analytics;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.h;

public class e {
    private static String[] a = new String[2];

    public static void a(Context context, String string2, String string3) {
        e.a[0] = string2;
        e.a[1] = string3;
        if (context != null) {
            h.a(context).a(string2, string3);
        }
    }

    public static String[] a(Context stringArray) {
        if (!TextUtils.isEmpty((CharSequence)a[0]) && !TextUtils.isEmpty((CharSequence)a[1])) {
            return a;
        }
        if (stringArray != null && (stringArray = h.a((Context)stringArray).a()) != null) {
            e.a[0] = stringArray[0];
            e.a[1] = stringArray[1];
            return a;
        }
        return null;
    }

    public static void b(Context context) {
        e.a[0] = null;
        e.a[1] = null;
        if (context != null) {
            h.a(context).b();
        }
    }
}

