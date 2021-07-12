/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.net.Uri
 *  android.text.TextUtils
 */
package com.google.android.gms.common.internal;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public class zzn {
    private static final Uri zzamj = Uri.parse((String)"http://plus.google.com/");
    private static final Uri zzamk = zzamj.buildUpon().appendPath("circles").appendPath("find").build();

    public static Intent zzcJ(String string2) {
        string2 = Uri.fromParts((String)"package", (String)string2, null);
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData((Uri)string2);
        return intent;
    }

    public static Intent zzqU() {
        Intent intent = new Intent("com.google.android.clockwork.home.UPDATE_ANDROID_WEAR_ACTION");
        intent.setPackage("com.google.android.wearable.app");
        return intent;
    }

    private static Uri zzw(String string2, @Nullable String string3) {
        string2 = Uri.parse((String)"market://details").buildUpon().appendQueryParameter("id", string2);
        if (!TextUtils.isEmpty((CharSequence)string3)) {
            string2.appendQueryParameter("pcampaignid", string3);
        }
        return string2.build();
    }

    public static Intent zzx(String string2, @Nullable String string3) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(zzn.zzw(string2, string3));
        intent.setPackage("com.android.vending");
        intent.addFlags(524288);
        return intent;
    }
}

