/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.Log
 */
package com.google.android.gms.auth.api.signin;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.R;

public enum zzd {
    zzXh("google.com", R.string.auth_google_play_services_client_google_display_name, "https://accounts.google.com"),
    zzXi("facebook.com", R.string.auth_google_play_services_client_facebook_display_name, "https://www.facebook.com");

    private final String zzVY;
    private final String zzXj;
    private final int zzXk;

    private zzd(String string3, int n3, String string4) {
        this.zzXj = string3;
        this.zzXk = n3;
        this.zzVY = string4;
    }

    public static zzd zzbL(String string2) {
        if (string2 != null) {
            for (zzd zzd2 : zzd.values()) {
                if (!zzd2.zzmT().equals(string2)) continue;
                return zzd2;
            }
            Log.w((String)"IdProvider", (String)("Unrecognized providerId: " + string2));
        }
        return null;
    }

    public String toString() {
        return this.zzXj;
    }

    public CharSequence zzae(Context context) {
        return context.getResources().getString(this.zzXk);
    }

    public String zzmT() {
        return this.zzXj;
    }
}

