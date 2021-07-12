/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.text.TextUtils
 */
package com.google.android.gms.common;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.internal.zzn;
import com.google.android.gms.common.zze;

public class zzc {
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = zze.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    private static final zzc zzafF = new zzc();

    zzc() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String zzj(@Nullable Context context, @Nullable String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("gcore_");
        stringBuilder.append(GOOGLE_PLAY_SERVICES_VERSION_CODE);
        stringBuilder.append("-");
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            stringBuilder.append(string2);
        }
        stringBuilder.append("-");
        if (context != null) {
            stringBuilder.append(context.getPackageName());
        }
        stringBuilder.append("-");
        if (context == null) return stringBuilder.toString();
        try {
            stringBuilder.append(context.getPackageManager().getPackageInfo((String)context.getPackageName(), (int)0).versionCode);
            return stringBuilder.toString();
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return stringBuilder.toString();
        }
    }

    public static zzc zzoK() {
        return zzafF;
    }

    @Nullable
    public PendingIntent getErrorResolutionPendingIntent(Context context, int n2, int n3) {
        return this.zza(context, n2, n3, null);
    }

    public String getErrorString(int n2) {
        return zze.getErrorString(n2);
    }

    @Nullable
    public String getOpenSourceSoftwareLicenseInfo(Context context) {
        return zze.getOpenSourceSoftwareLicenseInfo(context);
    }

    public int isGooglePlayServicesAvailable(Context context) {
        int n2;
        int n3 = n2 = zze.isGooglePlayServicesAvailable(context);
        if (zze.zzd(context, n2)) {
            n3 = 18;
        }
        return n3;
    }

    public boolean isUserResolvableError(int n2) {
        return zze.isUserRecoverableError(n2);
    }

    @Nullable
    public PendingIntent zza(Context context, int n2, int n3, @Nullable String string2) {
        if ((string2 = this.zza(context, n2, string2)) == null) {
            return null;
        }
        return PendingIntent.getActivity((Context)context, (int)n3, (Intent)string2, (int)0x10000000);
    }

    @Nullable
    public Intent zza(Context context, int n2, @Nullable String string2) {
        switch (n2) {
            default: {
                return null;
            }
            case 1: 
            case 2: {
                return zzn.zzx(GOOGLE_PLAY_SERVICES_PACKAGE, this.zzj(context, string2));
            }
            case 42: {
                return zzn.zzqU();
            }
            case 3: 
        }
        return zzn.zzcJ(GOOGLE_PLAY_SERVICES_PACKAGE);
    }

    public int zzaj(Context context) {
        return zze.zzaj(context);
    }

    public void zzak(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        zze.zzad(context);
    }

    public void zzal(Context context) {
        zze.zzal(context);
    }

    @Deprecated
    @Nullable
    public Intent zzbu(int n2) {
        return this.zza(null, n2, null);
    }

    public boolean zzd(Context context, int n2) {
        return zze.zzd(context, n2);
    }

    public boolean zzi(Context context, String string2) {
        return zze.zzi(context, string2);
    }
}

