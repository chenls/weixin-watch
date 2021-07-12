/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.IntentFilter
 *  android.net.Uri
 *  android.os.RemoteException
 */
package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.net.Uri;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.common.api.internal.zzq;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.internal.zzbn;
import com.google.android.gms.wearable.internal.zzbp;
import com.google.android.gms.wearable.internal.zzi;

public final class zzaz
implements MessageApi {
    private PendingResult<Status> zza(GoogleApiClient googleApiClient, MessageApi.MessageListener messageListener, IntentFilter[] intentFilterArray) {
        return googleApiClient.zza(new zza(googleApiClient, messageListener, googleApiClient.zzr(messageListener), intentFilterArray));
    }

    @Override
    public PendingResult<Status> addListener(GoogleApiClient googleApiClient, MessageApi.MessageListener messageListener) {
        return this.zza(googleApiClient, messageListener, new IntentFilter[]{zzbn.zzgM("com.google.android.gms.wearable.MESSAGE_RECEIVED")});
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public PendingResult<Status> addListener(GoogleApiClient googleApiClient, MessageApi.MessageListener messageListener, Uri uri, int n2) {
        boolean bl2 = uri != null;
        zzx.zzb(bl2, (Object)"uri must not be null");
        bl2 = n2 == 0 || n2 == 1;
        zzx.zzb(bl2, (Object)"invalid filter type");
        return this.zza(googleApiClient, messageListener, new IntentFilter[]{zzbn.zza("com.google.android.gms.wearable.MESSAGE_RECEIVED", uri, n2)});
    }

    @Override
    public PendingResult<Status> removeListener(GoogleApiClient googleApiClient, final MessageApi.MessageListener messageListener) {
        return googleApiClient.zza(new zzi<Status>(googleApiClient){

            @Override
            protected void zza(zzbp zzbp2) throws RemoteException {
                zzbp2.zza((zza.zzb<Status>)this, messageListener);
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
    public PendingResult<MessageApi.SendMessageResult> sendMessage(GoogleApiClient googleApiClient, final String string2, final String string3, final byte[] byArray) {
        return googleApiClient.zza(new zzi<MessageApi.SendMessageResult>(googleApiClient){

            @Override
            protected void zza(zzbp zzbp2) throws RemoteException {
                zzbp2.zza(this, string2, string3, byArray);
            }

            protected MessageApi.SendMessageResult zzbz(Status status) {
                return new zzb(status, -1);
            }

            @Override
            protected /* synthetic */ Result zzc(Status status) {
                return this.zzbz(status);
            }
        });
    }

    private static final class zza
    extends zzi<Status> {
        private zzq<MessageApi.MessageListener> zzbbi;
        private MessageApi.MessageListener zzbsS;
        private IntentFilter[] zzbsT;

        private zza(GoogleApiClient googleApiClient, MessageApi.MessageListener messageListener, zzq<MessageApi.MessageListener> zzq2, IntentFilter[] intentFilterArray) {
            super(googleApiClient);
            this.zzbsS = zzx.zzz(messageListener);
            this.zzbbi = zzx.zzz(zzq2);
            this.zzbsT = zzx.zzz(intentFilterArray);
        }

        @Override
        protected void zza(zzbp zzbp2) throws RemoteException {
            zzbp2.zza((zza.zzb<Status>)this, this.zzbsS, this.zzbbi, this.zzbsT);
            this.zzbsS = null;
            this.zzbbi = null;
            this.zzbsT = null;
        }

        public Status zzb(Status status) {
            this.zzbsS = null;
            this.zzbbi = null;
            this.zzbsT = null;
            return status;
        }

        @Override
        public /* synthetic */ Result zzc(Status status) {
            return this.zzb(status);
        }
    }

    public static class zzb
    implements MessageApi.SendMessageResult {
        private final Status zzUX;
        private final int zzaox;

        public zzb(Status status, int n2) {
            this.zzUX = status;
            this.zzaox = n2;
        }

        @Override
        public int getRequestId() {
            return this.zzaox;
        }

        @Override
        public Status getStatus() {
            return this.zzUX;
        }
    }
}

