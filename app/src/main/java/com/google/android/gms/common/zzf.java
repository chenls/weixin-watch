/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.util.Base64
 *  android.util.Log
 */
package com.google.android.gms.common;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.zzd;
import com.google.android.gms.common.zze;

public class zzf {
    private static final zzf zzafS = new zzf();

    private zzf() {
    }

    public static zzf zzoO() {
        return zzafS;
    }

    zzd.zza zza(PackageInfo object, zzd.zza ... zzaArray) {
        if (((PackageInfo)object).signatures.length != 1) {
            Log.w((String)"GoogleSignatureVerifier", (String)"Package has more than one signature.");
            return null;
        }
        object = new zzd.zzb(((PackageInfo)object).signatures[0].toByteArray());
        for (int i2 = 0; i2 < zzaArray.length; ++i2) {
            if (!zzaArray[i2].equals(object)) continue;
            return zzaArray[i2];
        }
        if (Log.isLoggable((String)"GoogleSignatureVerifier", (int)2)) {
            Log.v((String)"GoogleSignatureVerifier", (String)("Signature not valid.  Found: \n" + Base64.encodeToString((byte[])((zzd.zza)object).getBytes(), (int)0)));
        }
        return null;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public boolean zza(PackageInfo object, boolean bl2) {
        if (object != null && object.signatures != null) {
            void var1_3;
            void var2_5;
            if (var2_5 != false) {
                zzd.zza zza2 = this.zza((PackageInfo)object, zzd.zzd.zzafK);
            } else {
                zzd.zza zza3 = this.zza((PackageInfo)object, new zzd.zza[]{zzd.zzd.zzafK[0]});
            }
            if (var1_3 != null) {
                return true;
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean zza(PackageManager packageManager, PackageInfo packageInfo) {
        boolean bl2;
        block5: {
            boolean bl3;
            block4: {
                bl3 = false;
                if (packageInfo == null) break block4;
                if (zze.zzc(packageManager)) {
                    return this.zza(packageInfo, true);
                }
                bl3 = bl2 = this.zza(packageInfo, false);
                if (bl2) break block4;
                bl3 = bl2;
                if (this.zza(packageInfo, true)) break block5;
            }
            return bl3;
        }
        Log.w((String)"GoogleSignatureVerifier", (String)"Test-keys aren't accepted on this build.");
        return bl2;
    }
}

