/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.IntentFilter
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.common.api.internal.zzq;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.internal.zzb;
import com.google.android.gms.wearable.internal.zzbn;
import com.google.android.gms.wearable.internal.zzbp;
import com.google.android.gms.wearable.internal.zzi;
import com.google.android.gms.wearable.internal.zzl;
import com.google.android.gms.wearable.internal.zzo;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ChannelImpl
implements SafeParcelable,
Channel {
    public static final Parcelable.Creator<ChannelImpl> CREATOR = new zzo();
    private final String mPath;
    final int mVersionCode;
    private final String zzVo;
    private final String zzbrb;

    ChannelImpl(int n2, String string2, String string3, String string4) {
        this.mVersionCode = n2;
        this.zzVo = zzx.zzz(string2);
        this.zzbrb = zzx.zzz(string3);
        this.mPath = zzx.zzz(string4);
    }

    private static zzb.zza<ChannelApi.ChannelListener> zza(final String string2, final IntentFilter[] intentFilterArray) {
        return new zzb.zza<ChannelApi.ChannelListener>(){

            @Override
            public void zza(zzbp zzbp2, zza.zzb<Status> zzb2, ChannelApi.ChannelListener channelListener, zzq<ChannelApi.ChannelListener> zzq2) throws RemoteException {
                zzbp2.zza(zzb2, channelListener, zzq2, string2, intentFilterArray);
            }
        };
    }

    @Override
    public PendingResult<Status> addListener(GoogleApiClient googleApiClient, ChannelApi.ChannelListener channelListener) {
        IntentFilter intentFilter = zzbn.zzgM("com.google.android.gms.wearable.CHANNEL_EVENT");
        return com.google.android.gms.wearable.internal.zzb.zza(googleApiClient, ChannelImpl.zza(this.zzVo, new IntentFilter[]{intentFilter}), channelListener);
    }

    @Override
    public PendingResult<Status> close(GoogleApiClient googleApiClient) {
        return googleApiClient.zza(new zzi<Status>(googleApiClient){

            @Override
            protected void zza(zzbp zzbp2) throws RemoteException {
                zzbp2.zzt(this, ChannelImpl.this.zzVo);
            }

            protected Status zzb(Status status) {
                return status;
            }

            @Override
            protected /* synthetic */ Result zzc(Status status) {
                return this.zzb(status);
            }
        });
    }

    @Override
    public PendingResult<Status> close(GoogleApiClient googleApiClient, final int n2) {
        return googleApiClient.zza(new zzi<Status>(googleApiClient){

            @Override
            protected void zza(zzbp zzbp2) throws RemoteException {
                zzbp2.zzh(this, ChannelImpl.this.zzVo, n2);
            }

            protected Status zzb(Status status) {
                return status;
            }

            @Override
            protected /* synthetic */ Result zzc(Status status) {
                return this.zzb(status);
            }
        });
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (object == this) break block4;
                if (!(object instanceof ChannelImpl)) {
                    return false;
                }
                object = (ChannelImpl)object;
                if (!this.zzVo.equals(((ChannelImpl)object).zzVo) || !zzw.equal(((ChannelImpl)object).zzbrb, this.zzbrb) || !zzw.equal(((ChannelImpl)object).mPath, this.mPath) || ((ChannelImpl)object).mVersionCode != this.mVersionCode) break block5;
            }
            return true;
        }
        return false;
    }

    @Override
    public PendingResult<Channel.GetInputStreamResult> getInputStream(GoogleApiClient googleApiClient) {
        return googleApiClient.zza(new zzi<Channel.GetInputStreamResult>(googleApiClient){

            @Override
            protected void zza(zzbp zzbp2) throws RemoteException {
                zzbp2.zzu(this, ChannelImpl.this.zzVo);
            }

            public Channel.GetInputStreamResult zzbt(Status status) {
                return new zza(status, null);
            }

            @Override
            public /* synthetic */ Result zzc(Status status) {
                return this.zzbt(status);
            }
        });
    }

    @Override
    public String getNodeId() {
        return this.zzbrb;
    }

    @Override
    public PendingResult<Channel.GetOutputStreamResult> getOutputStream(GoogleApiClient googleApiClient) {
        return googleApiClient.zza(new zzi<Channel.GetOutputStreamResult>(googleApiClient){

            @Override
            protected void zza(zzbp zzbp2) throws RemoteException {
                zzbp2.zzv(this, ChannelImpl.this.zzVo);
            }

            public Channel.GetOutputStreamResult zzbu(Status status) {
                return new zzb(status, null);
            }

            @Override
            public /* synthetic */ Result zzc(Status status) {
                return this.zzbu(status);
            }
        });
    }

    @Override
    public String getPath() {
        return this.mPath;
    }

    public String getToken() {
        return this.zzVo;
    }

    public int hashCode() {
        return this.zzVo.hashCode();
    }

    @Override
    public PendingResult<Status> receiveFile(GoogleApiClient googleApiClient, final Uri uri, final boolean bl2) {
        zzx.zzb(googleApiClient, (Object)"client is null");
        zzx.zzb(uri, (Object)"uri is null");
        return googleApiClient.zza(new zzi<Status>(googleApiClient){

            @Override
            protected void zza(zzbp zzbp2) throws RemoteException {
                zzbp2.zza((zza.zzb<Status>)this, ChannelImpl.this.zzVo, uri, bl2);
            }

            public Status zzb(Status status) {
                return status;
            }

            @Override
            public /* synthetic */ Result zzc(Status status) {
                return this.zzb(status);
            }
        });
    }

    @Override
    public PendingResult<Status> removeListener(GoogleApiClient googleApiClient, ChannelApi.ChannelListener channelListener) {
        zzx.zzb(googleApiClient, (Object)"client is null");
        zzx.zzb(channelListener, (Object)"listener is null");
        return googleApiClient.zza(new zzl.zzb(googleApiClient, channelListener, this.zzVo));
    }

    @Override
    public PendingResult<Status> sendFile(GoogleApiClient googleApiClient, Uri uri) {
        return this.sendFile(googleApiClient, uri, 0L, -1L);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public PendingResult<Status> sendFile(GoogleApiClient googleApiClient, final Uri uri, final long l2, final long l3) {
        zzx.zzb(googleApiClient, (Object)"client is null");
        zzx.zzb(this.zzVo, (Object)"token is null");
        zzx.zzb(uri, (Object)"uri is null");
        boolean bl2 = l2 >= 0L;
        zzx.zzb(bl2, "startOffset is negative: %s", l2);
        bl2 = l3 >= 0L || l3 == -1L;
        zzx.zzb(bl2, "invalid length: %s", l3);
        return googleApiClient.zza(new zzi<Status>(googleApiClient){

            @Override
            protected void zza(zzbp zzbp2) throws RemoteException {
                zzbp2.zza((zza.zzb<Status>)this, ChannelImpl.this.zzVo, uri, l2, l3);
            }

            public Status zzb(Status status) {
                return status;
            }

            @Override
            public /* synthetic */ Result zzc(Status status) {
                return this.zzb(status);
            }
        });
    }

    public String toString() {
        return "ChannelImpl{versionCode=" + this.mVersionCode + ", token='" + this.zzVo + '\'' + ", nodeId='" + this.zzbrb + '\'' + ", path='" + this.mPath + '\'' + "}";
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzo.zza(this, parcel, n2);
    }

    static final class zza
    implements Channel.GetInputStreamResult {
        private final Status zzUX;
        private final InputStream zzbsh;

        zza(Status status, InputStream inputStream) {
            this.zzUX = zzx.zzz(status);
            this.zzbsh = inputStream;
        }

        @Override
        public InputStream getInputStream() {
            return this.zzbsh;
        }

        @Override
        public Status getStatus() {
            return this.zzUX;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public void release() {
            if (this.zzbsh == null) return;
            try {
                this.zzbsh.close();
                return;
            }
            catch (IOException iOException) {
                return;
            }
        }
    }

    static final class zzb
    implements Channel.GetOutputStreamResult {
        private final Status zzUX;
        private final OutputStream zzbsi;

        zzb(Status status, OutputStream outputStream) {
            this.zzUX = zzx.zzz(status);
            this.zzbsi = outputStream;
        }

        @Override
        public OutputStream getOutputStream() {
            return this.zzbsi;
        }

        @Override
        public Status getStatus() {
            return this.zzUX;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public void release() {
            if (this.zzbsi == null) return;
            try {
                this.zzbsi.close();
                return;
            }
            catch (IOException iOException) {
                return;
            }
        }
    }
}

