/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.internal.zzmg;
import com.google.android.gms.internal.zzmh;
import com.google.android.gms.internal.zzmj;

public final class zzmf {
    public static final Api<Api.ApiOptions.NoOptions> API;
    public static final Api.zzc<zzmj> zzUI;
    private static final Api.zza<zzmj, Api.ApiOptions.NoOptions> zzUJ;
    public static final zzmg zzamA;

    static {
        zzUI = new Api.zzc();
        zzUJ = new Api.zza<zzmj, Api.ApiOptions.NoOptions>(){

            @Override
            public /* synthetic */ Api.zzb zza(Context context, Looper looper, zzf zzf2, Object object, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
                return this.zzf(context, looper, zzf2, (Api.ApiOptions.NoOptions)object, connectionCallbacks, onConnectionFailedListener);
            }

            public zzmj zzf(Context context, Looper looper, zzf zzf2, Api.ApiOptions.NoOptions noOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
                return new zzmj(context, looper, zzf2, connectionCallbacks, onConnectionFailedListener);
            }
        };
        API = new Api<Api.ApiOptions.NoOptions>("Common.API", zzUJ, zzUI);
        zzamA = new zzmh();
    }
}

