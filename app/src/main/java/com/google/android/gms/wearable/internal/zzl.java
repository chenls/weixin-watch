/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.IntentFilter
 *  android.os.RemoteException
 */
package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.common.api.internal.zzq;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.internal.zzb;
import com.google.android.gms.wearable.internal.zzbn;
import com.google.android.gms.wearable.internal.zzbp;
import com.google.android.gms.wearable.internal.zzi;

public final class zzl
implements ChannelApi {
    private static zzb.zza<ChannelApi.ChannelListener> zza(final IntentFilter[] intentFilterArray) {
        return new zzb.zza<ChannelApi.ChannelListener>(){

            @Override
            public void zza(zzbp zzbp2, zza.zzb<Status> zzb2, ChannelApi.ChannelListener channelListener, zzq<ChannelApi.ChannelListener> zzq2) throws RemoteException {
                zzbp2.zza(zzb2, channelListener, zzq2, null, intentFilterArray);
            }
        };
    }

    @Override
    public PendingResult<Status> addListener(GoogleApiClient googleApiClient, ChannelApi.ChannelListener channelListener) {
        zzx.zzb(googleApiClient, (Object)"client is null");
        zzx.zzb(channelListener, (Object)"listener is null");
        return com.google.android.gms.wearable.internal.zzb.zza(googleApiClient, zzl.zza(new IntentFilter[]{zzbn.zzgM("com.google.android.gms.wearable.CHANNEL_EVENT")}), channelListener);
    }

    @Override
    public PendingResult<ChannelApi.OpenChannelResult> openChannel(GoogleApiClient googleApiClient, final String string2, final String string3) {
        zzx.zzb(googleApiClient, (Object)"client is null");
        zzx.zzb(string2, (Object)"nodeId is null");
        zzx.zzb(string3, (Object)"path is null");
        return googleApiClient.zza(new zzi<ChannelApi.OpenChannelResult>(googleApiClient){

            @Override
            protected void zza(zzbp zzbp2) throws RemoteException {
                zzbp2.zze(this, string2, string3);
            }

            public ChannelApi.OpenChannelResult zzbs(Status status) {
                return new zza(status, null);
            }

            @Override
            public /* synthetic */ Result zzc(Status status) {
                return this.zzbs(status);
            }
        });
    }

    @Override
    public PendingResult<Status> removeListener(GoogleApiClient googleApiClient, ChannelApi.ChannelListener channelListener) {
        zzx.zzb(googleApiClient, (Object)"client is null");
        zzx.zzb(channelListener, (Object)"listener is null");
        return googleApiClient.zza(new zzb(googleApiClient, channelListener, null));
    }

    static final class zza
    implements ChannelApi.OpenChannelResult {
        private final Status zzUX;
        private final Channel zzbrY;

        zza(Status status, Channel channel) {
            this.zzUX = zzx.zzz(status);
            this.zzbrY = channel;
        }

        @Override
        public Channel getChannel() {
            return this.zzbrY;
        }

        @Override
        public Status getStatus() {
            return this.zzUX;
        }
    }

    static final class zzb
    extends zzi<Status> {
        private final String zzVo;
        private ChannelApi.ChannelListener zzbrZ;

        zzb(GoogleApiClient googleApiClient, ChannelApi.ChannelListener channelListener, String string2) {
            super(googleApiClient);
            this.zzbrZ = zzx.zzz(channelListener);
            this.zzVo = string2;
        }

        @Override
        protected void zza(zzbp zzbp2) throws RemoteException {
            zzbp2.zza(this, this.zzbrZ, this.zzVo);
            this.zzbrZ = null;
        }

        public Status zzb(Status status) {
            this.zzbrZ = null;
            return status;
        }

        @Override
        public /* synthetic */ Result zzc(Status status) {
            return this.zzb(status);
        }
    }
}

