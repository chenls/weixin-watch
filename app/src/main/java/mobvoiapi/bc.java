/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.ParcelFileDescriptor
 *  android.os.ParcelFileDescriptor$AutoCloseInputStream
 *  android.os.RemoteException
 */
package mobvoiapi;

import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.common.api.PendingResult;
import com.mobvoi.android.common.api.Result;
import com.mobvoi.android.common.api.Status;
import com.mobvoi.android.wearable.Asset;
import com.mobvoi.android.wearable.DataApi;
import com.mobvoi.android.wearable.DataItem;
import com.mobvoi.android.wearable.DataItemAsset;
import com.mobvoi.android.wearable.DataItemBuffer;
import com.mobvoi.android.wearable.PutDataRequest;
import java.io.InputStream;
import mobvoiapi.bj;
import mobvoiapi.bm;

public class bc
implements DataApi {
    @Override
    public PendingResult<Status> addListener(MobvoiApiClient mobvoiApiClient, final DataApi.DataListener dataListener) {
        return mobvoiApiClient.setResult(new bm<Status>(){

            @Override
            protected /* synthetic */ Result a(Status status) {
                return this.c(status);
            }

            @Override
            protected void a(bj bj2) throws RemoteException {
                bj2.a((bm<Status>)this, dataListener);
            }

            protected Status c(Status status) {
                return status;
            }
        });
    }

    @Override
    public PendingResult<DataApi.DeleteDataItemsResult> deleteDataItems(MobvoiApiClient mobvoiApiClient, final Uri uri) {
        return mobvoiApiClient.setResult(new bm<DataApi.DeleteDataItemsResult>(){

            @Override
            protected /* synthetic */ Result a(Status status) {
                return this.c(status);
            }

            @Override
            protected void a(bj bj2) throws RemoteException {
                bj2.a(this, uri);
            }

            protected DataApi.DeleteDataItemsResult c(Status status) {
                return new b(status, -1);
            }
        });
    }

    @Override
    public PendingResult<DataApi.DataItemResult> getDataItem(MobvoiApiClient mobvoiApiClient, final Uri uri) {
        return mobvoiApiClient.setResult(new bm<DataApi.DataItemResult>(){

            @Override
            protected /* synthetic */ Result a(Status status) {
                return this.c(status);
            }

            @Override
            protected void a(bj bj2) throws RemoteException {
                bj2.b(this, uri);
            }

            protected DataApi.DataItemResult c(Status status) {
                return new a(status, null);
            }
        });
    }

    @Override
    public PendingResult<DataItemBuffer> getDataItems(MobvoiApiClient mobvoiApiClient) {
        return mobvoiApiClient.setResult(new bm<DataItemBuffer>(){

            @Override
            protected /* synthetic */ Result a(Status status) {
                return this.c(status);
            }

            @Override
            protected void a(bj bj2) throws RemoteException {
                bj2.a(this);
            }

            protected DataItemBuffer c(Status status) {
                return new DataItemBuffer(status);
            }
        });
    }

    @Override
    public PendingResult<DataItemBuffer> getDataItems(MobvoiApiClient mobvoiApiClient, final Uri uri) {
        return mobvoiApiClient.setResult(new bm<DataItemBuffer>(){

            @Override
            protected /* synthetic */ Result a(Status status) {
                return this.c(status);
            }

            @Override
            protected void a(bj bj2) throws RemoteException {
                bj2.c(this, uri);
            }

            protected DataItemBuffer c(Status status) {
                return new DataItemBuffer(status);
            }
        });
    }

    @Override
    public PendingResult<DataApi.GetFdForAssetResult> getFdForAsset(MobvoiApiClient mobvoiApiClient, final Asset asset) {
        if (asset == null) {
            throw new IllegalArgumentException("asset is null");
        }
        if (asset.getDigest() == null || asset.getData() != null) {
            throw new IllegalArgumentException("invalid asset, digest = " + asset.getDigest() + ", data = " + asset.getData());
        }
        return mobvoiApiClient.setResult(new bm<DataApi.GetFdForAssetResult>(){

            @Override
            protected /* synthetic */ Result a(Status status) {
                return this.c(status);
            }

            @Override
            protected void a(bj bj2) throws RemoteException {
                bj2.a(this, asset);
            }

            protected DataApi.GetFdForAssetResult c(Status status) {
                return new c(status, null);
            }
        });
    }

    @Override
    public PendingResult<DataApi.GetFdForAssetResult> getFdForAsset(MobvoiApiClient mobvoiApiClient, final DataItemAsset dataItemAsset) {
        return mobvoiApiClient.setResult(new bm<DataApi.GetFdForAssetResult>(){

            @Override
            protected /* synthetic */ Result a(Status status) {
                return this.c(status);
            }

            @Override
            protected void a(bj bj2) throws RemoteException {
                bj2.a(this, Asset.createFromRef(dataItemAsset.getId()));
            }

            protected DataApi.GetFdForAssetResult c(Status status) {
                return new c(status, null);
            }
        });
    }

    @Override
    public PendingResult<DataApi.DataItemResult> putDataItem(MobvoiApiClient mobvoiApiClient, final PutDataRequest putDataRequest) {
        return mobvoiApiClient.setResult(new bm<DataApi.DataItemResult>(){

            @Override
            protected /* synthetic */ Result a(Status status) {
                return this.c(status);
            }

            @Override
            protected void a(bj bj2) throws RemoteException {
                bj2.a(this, putDataRequest);
            }

            protected DataApi.DataItemResult c(Status status) {
                return new a(status, null);
            }
        });
    }

    @Override
    public PendingResult<Status> removeListener(MobvoiApiClient mobvoiApiClient, final DataApi.DataListener dataListener) {
        return mobvoiApiClient.setResult(new bm<Status>(){

            @Override
            protected /* synthetic */ Result a(Status status) {
                return this.c(status);
            }

            @Override
            protected void a(bj bj2) throws RemoteException {
                bj2.b((bm<Status>)this, dataListener);
            }

            protected Status c(Status status) {
                return status;
            }
        });
    }

    public static class a
    implements DataApi.DataItemResult {
        private DataItem a;
        private Status b;

        public a(Status status, DataItem dataItem) {
            this.b = status;
            this.a = dataItem;
        }

        @Override
        public DataItem getDataItem() {
            return this.a;
        }

        @Override
        public Status getStatus() {
            return this.b;
        }
    }

    public static class b
    implements DataApi.DeleteDataItemsResult {
        private Status a;
        private int b;

        public b(Status status, int n2) {
            this.a = status;
            this.b = n2;
        }

        @Override
        public int getNumDeleted() {
            return this.b;
        }

        @Override
        public Status getStatus() {
            return this.a;
        }
    }

    public static class c
    implements DataApi.GetFdForAssetResult {
        private Status a;
        private ParcelFileDescriptor b;
        private InputStream c;
        private volatile boolean d = false;

        public c(Status status, ParcelFileDescriptor parcelFileDescriptor) {
            this.a = status;
            this.b = parcelFileDescriptor;
        }

        @Override
        public ParcelFileDescriptor getFd() {
            if (this.d) {
                throw new IllegalStateException("Cannot access the ParcelFileDescriptor after release().");
            }
            return this.b;
        }

        @Override
        public InputStream getInputStream() {
            if (this.b == null) {
                return null;
            }
            if (this.d) {
                throw new IllegalStateException("Cannot access the input stream after release().");
            }
            this.c = new ParcelFileDescriptor.AutoCloseInputStream(this.b);
            return this.c;
        }

        @Override
        public Status getStatus() {
            return this.a;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void release() {
            block6: {
                if (this.b == null) {
                    return;
                }
                if (this.d) {
                    throw new IllegalStateException("releasing an already released result.");
                }
                try {
                    if (this.c != null) {
                        this.c.close();
                        break block6;
                    }
                    this.b.close();
                }
                catch (Exception exception) {}
            }
            this.d = true;
            this.b = null;
        }
    }
}

