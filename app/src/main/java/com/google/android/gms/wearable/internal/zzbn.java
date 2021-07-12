/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.IntentFilter
 *  android.net.Uri
 */
package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.net.Uri;

public final class zzbn {
    public static IntentFilter zza(String string2, Uri uri, int n2) {
        string2 = new IntentFilter(string2);
        if (uri.getScheme() != null) {
            string2.addDataScheme(uri.getScheme());
        }
        if (uri.getAuthority() != null) {
            string2.addDataAuthority(uri.getAuthority(), Integer.toString(uri.getPort()));
        }
        if (uri.getPath() != null) {
            string2.addDataPath(uri.getPath(), n2);
        }
        return string2;
    }

    public static IntentFilter zzgM(String string2) {
        string2 = new IntentFilter(string2);
        string2.addDataScheme("wear");
        string2.addDataAuthority("*", null);
        return string2;
    }
}

