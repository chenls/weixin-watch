/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.IntentFilter
 *  android.net.Uri
 *  android.os.ParcelFileDescriptor
 *  android.os.ParcelFileDescriptor$AutoCloseInputStream
 *  android.os.RemoteException
 */
package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.common.api.internal.zzq;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemAsset;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.internal.zzb;
import com.google.android.gms.wearable.internal.zzbn;
import com.google.android.gms.wearable.internal.zzbp;
import com.google.android.gms.wearable.internal.zzi;
import java.io.IOException;
import java.io.InputStream;

public final class zzx
implements DataApi {
    private PendingResult<Status> zza(GoogleApiClient googleApiClient, DataApi.DataListener dataListener, IntentFilter[] intentFilterArray) {
        return com.google.android.gms.wearable.internal.zzb.zza(googleApiClient, zzx.zza(intentFilterArray), dataListener);
    }

    private static zzb.zza<DataApi.DataListener> zza(final IntentFilter[] intentFilterArray) {
        return new zzb.zza<DataApi.DataListener>(){

            @Override
            public void zza(zzbp zzbp2, zza.zzb<Status> zzb2, DataApi.DataListener dataListener, zzq<DataApi.DataListener> zzq2) throws RemoteException {
                zzbp2.zza(zzb2, dataListener, zzq2, intentFilterArray);
            }
        };
    }

    private void zza(Asset asset) {
        if (asset == null) {
            throw new IllegalArgumentException("asset is null");
        }
        if (asset.getDigest() == null) {
            throw new IllegalArgumentException("invalid asset");
        }
        if (asset.getData() != null) {
            throw new IllegalArgumentException("invalid asset");
        }
    }

    @Override
    public PendingResult<Status> addListener(GoogleApiClient googleApiClient, DataApi.DataListener dataListener) {
        return this.zza(googleApiClient, dataListener, new IntentFilter[]{zzbn.zzgM("com.google.android.gms.wearable.DATA_CHANGED")});
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public PendingResult<Status> addListener(GoogleApiClient googleApiClient, DataApi.DataListener dataListener, Uri uri, int n2) {
        boolean bl2 = uri != null;
        com.google.android.gms.common.internal.zzx.zzb(bl2, (Object)"uri must not be null");
        bl2 = n2 == 0 || n2 == 1;
        com.google.android.gms.common.internal.zzx.zzb(bl2, (Object)"invalid filter type");
        return this.zza(googleApiClient, dataListener, new IntentFilter[]{zzbn.zza("com.google.android.gms.wearable.DATA_CHANGED", uri, n2)});
    }

    @Override
    public PendingResult<DataApi.DeleteDataItemsResult> deleteDataItems(GoogleApiClient googleApiClient, Uri uri) {
        return this.deleteDataItems(googleApiClient, uri, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public PendingResult<DataApi.DeleteDataItemsResult> deleteDataItems(GoogleApiClient googleApiClient, final Uri uri, final int n2) {
        boolean bl2;
        block3: {
            block2: {
                boolean bl3 = false;
                bl2 = uri != null;
                com.google.android.gms.common.internal.zzx.zzb(bl2, (Object)"uri must not be null");
                if (n2 == 0) break block2;
                bl2 = bl3;
                if (n2 != 1) break block3;
            }
            bl2 = true;
        }
        com.google.android.gms.common.internal.zzx.zzb(bl2, (Object)"invalid filter type");
        return googleApiClient.zza(new zzi<DataApi.DeleteDataItemsResult>(googleApiClient){

            @Override
            protected void zza(zzbp zzbp2) throws RemoteException {
                zzbp2.zzb(this, uri, n2);
            }

            protected DataApi.DeleteDataItemsResult zzbx(Status status) {
                return new zzb(status, 0);
            }

            @Override
            protected /* synthetic */ Result zzc(Status status) {
                return this.zzbx(status);
            }
        });
    }

    @Override
    public PendingResult<DataApi.DataItemResult> getDataItem(GoogleApiClient googleApiClient, final Uri uri) {
        return googleApiClient.zza(new zzi<DataApi.DataItemResult>(googleApiClient){

            @Override
            protected void zza(zzbp zzbp2) throws RemoteException {
                zzbp2.zza((zza.zzb<DataApi.DataItemResult>)this, uri);
            }

            protected DataApi.DataItemResult zzbv(Status status) {
                return new zza(status, null);
            }

            @Override
            protected /* synthetic */ Result zzc(Status status) {
                return this.zzbv(status);
            }
        });
    }

    @Override
    public PendingResult<DataItemBuffer> getDataItems(GoogleApiClient googleApiClient) {
        return googleApiClient.zza(new zzi<DataItemBuffer>(googleApiClient){

            @Override
            protected void zza(zzbp zzbp2) throws RemoteException {
                zzbp2.zzr(this);
            }

            protected DataItemBuffer zzbw(Status status) {
                return new DataItemBuffer(DataHolder.zzbI(status.getStatusCode()));
            }

            @Override
            protected /* synthetic */ Result zzc(Status status) {
                return this.zzbw(status);
            }
        });
    }

    @Override
    public PendingResult<DataItemBuffer> getDataItems(GoogleApiClient googleApiClient, Uri uri) {
        return this.getDataItems(googleApiClient, uri, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public PendingResult<DataItemBuffer> getDataItems(GoogleApiClient googleApiClient, final Uri uri, final int n2) {
        boolean bl2;
        block3: {
            block2: {
                boolean bl3 = false;
                bl2 = uri != null;
                com.google.android.gms.common.internal.zzx.zzb(bl2, (Object)"uri must not be null");
                if (n2 == 0) break block2;
                bl2 = bl3;
                if (n2 != 1) break block3;
            }
            bl2 = true;
        }
        com.google.android.gms.common.internal.zzx.zzb(bl2, (Object)"invalid filter type");
        return googleApiClient.zza(new zzi<DataItemBuffer>(googleApiClient){

            @Override
            protected void zza(zzbp zzbp2) throws RemoteException {
                zzbp2.zza(this, uri, n2);
            }

            protected DataItemBuffer zzbw(Status status) {
                return new DataItemBuffer(DataHolder.zzbI(status.getStatusCode()));
            }

            @Override
            protected /* synthetic */ Result zzc(Status status) {
                return this.zzbw(status);
            }
        });
    }

    @Override
    public PendingResult<DataApi.GetFdForAssetResult> getFdForAsset(GoogleApiClient googleApiClient, final Asset asset) {
        this.zza(asset);
        return googleApiClient.zza(new zzi<DataApi.GetFdForAssetResult>(googleApiClient){

            @Override
            protected void zza(zzbp zzbp2) throws RemoteException {
                zzbp2.zza((zza.zzb<DataApi.GetFdForAssetResult>)this, asset);
            }

            protected DataApi.GetFdForAssetResult zzby(Status status) {
                return new zzc(status, null);
            }

            @Override
            protected /* synthetic */ Result zzc(Status status) {
                return this.zzby(status);
            }
        });
    }

    @Override
    public PendingResult<DataApi.GetFdForAssetResult> getFdForAsset(GoogleApiClient googleApiClient, final DataItemAsset dataItemAsset) {
        return googleApiClient.zza(new zzi<DataApi.GetFdForAssetResult>(googleApiClient){

            @Override
            protected void zza(zzbp zzbp2) throws RemoteException {
                zzbp2.zza((zza.zzb<DataApi.GetFdForAssetResult>)this, dataItemAsset);
            }

            protected DataApi.GetFdForAssetResult zzby(Status status) {
                return new zzc(status, null);
            }

            @Override
            protected /* synthetic */ Result zzc(Status status) {
                return this.zzby(status);
            }
        });
    }

    @Override
    public PendingResult<DataApi.DataItemResult> putDataItem(GoogleApiClient googleApiClient, final PutDataRequest putDataRequest) {
        return googleApiClient.zza(new zzi<DataApi.DataItemResult>(googleApiClient){

            @Override
            protected void zza(zzbp zzbp2) throws RemoteException {
                zzbp2.zza((zza.zzb<DataApi.DataItemResult>)this, putDataRequest);
            }

            public DataApi.DataItemResult zzbv(Status status) {
                return new zza(status, null);
            }

            @Override
            public /* synthetic */ Result zzc(Status status) {
                return this.zzbv(status);
            }
        });
    }

    @Override
    public PendingResult<Status> removeListener(GoogleApiClient googleApiClient, final DataApi.DataListener dataListener) {
        return googleApiClient.zza(new zzi<Status>(googleApiClient){

            @Override
            protected void zza(zzbp zzbp2) throws RemoteException {
                zzbp2.zza((zza.zzb<Status>)this, dataListener);
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

    public static class zza
    implements DataApi.DataItemResult {
        private final Status zzUX;
        private final DataItem zzbsv;

        public zza(Status status, DataItem dataItem) {
            this.zzUX = status;
            this.zzbsv = dataItem;
        }

        @Override
        public DataItem getDataItem() {
            return this.zzbsv;
        }

        @Override
        public Status getStatus() {
            return this.zzUX;
        }
    }

    public static class zzb
    implements DataApi.DeleteDataItemsResult {
        private final Status zzUX;
        private final int zzbsw;

        public zzb(Status status, int n2) {
            this.zzUX = status;
            this.zzbsw = n2;
        }

        @Override
        public int getNumDeleted() {
            return this.zzbsw;
        }

        @Override
        public Status getStatus() {
            return this.zzUX;
        }
    }

    public static class zzc
    implements DataApi.GetFdForAssetResult {
        private volatile boolean mClosed = false;
        private final Status zzUX;
        private volatile InputStream zzbsh;
        private volatile ParcelFileDescriptor zzbsx;

        public zzc(Status status, ParcelFileDescriptor parcelFileDescriptor) {
            this.zzUX = status;
            this.zzbsx = parcelFileDescriptor;
        }

        @Override
        public ParcelFileDescriptor getFd() {
            if (this.mClosed) {
                throw new IllegalStateException("Cannot access the file descriptor after release().");
            }
            return this.zzbsx;
        }

        @Override
        public InputStream getInputStream() {
            if (this.mClosed) {
                throw new IllegalStateException("Cannot access the input stream after release().");
            }
            if (this.zzbsx == null) {
                return null;
            }
            if (this.zzbsh == null) {
                this.zzbsh = new ParcelFileDescriptor.AutoCloseInputStream(this.zzbsx);
            }
            return this.zzbsh;
        }

        @Override
        public Status getStatus() {
            return this.zzUX;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void release() {
            if (this.zzbsx == null) {
                return;
            }
            if (this.mClosed) {
                throw new IllegalStateException("releasing an already released result.");
            }
            try {
                if (this.zzbsh != null) {
                    this.zzbsh.close();
                } else {
                    this.zzbsx.close();
                }
                this.mClosed = true;
                this.zzbsx = null;
                this.zzbsh = null;
                return;
            }
            catch (IOException iOException) {
                return;
            }
        }
    }
}

