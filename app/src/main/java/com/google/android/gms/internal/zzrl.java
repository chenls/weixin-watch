/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.internal.zzrm;
import com.google.android.gms.internal.zzro;
import com.google.android.gms.signin.internal.zzg;
import com.google.android.gms.signin.internal.zzh;

public final class zzrl {
    public static final Api<zzro> API;
    public static final Api.zzc<zzh> zzUI;
    public static final Api.zza<zzh, zzro> zzUJ;
    public static final Scope zzWW;
    public static final Scope zzWX;
    public static final Api<zza> zzaoG;
    public static final Api.zzc<zzh> zzavN;
    static final Api.zza<zzh, zza> zzbgS;
    public static final zzrm zzbgT;

    static {
        zzUI = new Api.zzc();
        zzavN = new Api.zzc();
        zzUJ = new Api.zza<zzh, zzro>(){

            @Override
            public zzh zza(Context context, Looper looper, zzf zzf2, zzro zzro2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
                if (zzro2 == null) {
                    zzro2 = zzro.zzbgV;
                }
                return new zzh(context, looper, true, zzf2, zzro2, connectionCallbacks, onConnectionFailedListener);
            }
        };
        zzbgS = new Api.zza<zzh, zza>(){

            @Override
            public zzh zza(Context context, Looper looper, zzf zzf2, zza zza2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
                return new zzh(context, looper, false, zzf2, zza2.zzFF(), connectionCallbacks, onConnectionFailedListener);
            }
        };
        zzWW = new Scope("profile");
        zzWX = new Scope("email");
        API = new Api<zzro>("SignIn.API", zzUJ, zzUI);
        zzaoG = new Api<zza>("SignIn.INTERNAL_API", zzbgS, zzavN);
        zzbgT = new zzg();
    }

    public static class zza
    implements Api.ApiOptions.HasOptions {
        private final Bundle zzbgU;

        public Bundle zzFF() {
            return this.zzbgU;
        }
    }
}

