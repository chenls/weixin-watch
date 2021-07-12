/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Looper
 */
package com.google.android.gms.wearable;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.internal.zzaz;
import com.google.android.gms.wearable.internal.zzbb;
import com.google.android.gms.wearable.internal.zzbm;
import com.google.android.gms.wearable.internal.zzbp;
import com.google.android.gms.wearable.internal.zzbr;
import com.google.android.gms.wearable.internal.zze;
import com.google.android.gms.wearable.internal.zzg;
import com.google.android.gms.wearable.internal.zzj;
import com.google.android.gms.wearable.internal.zzl;
import com.google.android.gms.wearable.internal.zzw;
import com.google.android.gms.wearable.internal.zzx;
import com.google.android.gms.wearable.zza;
import com.google.android.gms.wearable.zzc;
import com.google.android.gms.wearable.zzf;
import com.google.android.gms.wearable.zzi;
import com.google.android.gms.wearable.zzk;

public class Wearable {
    public static final Api<WearableOptions> API;
    public static final CapabilityApi CapabilityApi;
    public static final ChannelApi ChannelApi;
    public static final DataApi DataApi;
    public static final MessageApi MessageApi;
    public static final NodeApi NodeApi;
    public static final Api.zzc<zzbp> zzUI;
    private static final Api.zza<zzbp, WearableOptions> zzUJ;
    public static final zzc zzbrj;
    public static final zza zzbrk;
    public static final zzf zzbrl;
    public static final zzi zzbrm;
    public static final zzk zzbrn;

    static {
        DataApi = new zzx();
        CapabilityApi = new zzj();
        MessageApi = new zzaz();
        NodeApi = new zzbb();
        ChannelApi = new zzl();
        zzbrj = new zzg();
        zzbrk = new zze();
        zzbrl = new zzw();
        zzbrm = new zzbm();
        zzbrn = new zzbr();
        zzUI = new Api.zzc();
        zzUJ = new Api.zza<zzbp, WearableOptions>(){

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Override
            public zzbp zza(Context context, Looper looper, com.google.android.gms.common.internal.zzf zzf2, WearableOptions wearableOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
                if (wearableOptions != null) {
                    return new zzbp(context, looper, connectionCallbacks, onConnectionFailedListener, zzf2);
                }
                new WearableOptions(new WearableOptions.Builder());
                return new zzbp(context, looper, connectionCallbacks, onConnectionFailedListener, zzf2);
            }
        };
        API = new Api<WearableOptions>("Wearable.API", zzUJ, zzUI);
    }

    private Wearable() {
    }

    public static final class WearableOptions
    implements Api.ApiOptions.Optional {
        private WearableOptions(Builder builder) {
        }

        public static class Builder {
            public WearableOptions build() {
                return new WearableOptions(this);
            }
        }
    }
}

