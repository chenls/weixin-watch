/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.ParcelFileDescriptor
 *  android.os.ParcelFileDescriptor$AutoCloseOutputStream
 *  android.os.RemoteException
 */
package mobvoiapi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.common.api.Status;
import com.mobvoi.android.wearable.Asset;
import com.mobvoi.android.wearable.DataApi;
import com.mobvoi.android.wearable.DataItemBuffer;
import com.mobvoi.android.wearable.MessageApi;
import com.mobvoi.android.wearable.Node;
import com.mobvoi.android.wearable.NodeApi;
import com.mobvoi.android.wearable.PutDataRequest;
import com.mobvoi.android.wearable.internal.AddListenerRequest;
import com.mobvoi.android.wearable.internal.DataHolder;
import com.mobvoi.android.wearable.internal.DeleteDataItemsResponse;
import com.mobvoi.android.wearable.internal.GetConnectedNodesResponse;
import com.mobvoi.android.wearable.internal.GetDataItemResponse;
import com.mobvoi.android.wearable.internal.GetFdForAssetResponse;
import com.mobvoi.android.wearable.internal.GetLocalNodeResponse;
import com.mobvoi.android.wearable.internal.PutDataResponse;
import com.mobvoi.android.wearable.internal.RemoveListenerRequest;
import com.mobvoi.android.wearable.internal.SendMessageResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import mobvoiapi.bc;
import mobvoiapi.bd;
import mobvoiapi.bg;
import mobvoiapi.bi;
import mobvoiapi.bk;
import mobvoiapi.bl;
import mobvoiapi.bm;
import mobvoiapi.bp;
import mobvoiapi.d;
import mobvoiapi.e;

public class bj
extends e<bi> {
    private Map<MessageApi.MessageListener, bl> b = new HashMap<MessageApi.MessageListener, bl>();
    private Map<DataApi.DataListener, bl> c = new HashMap<DataApi.DataListener, bl>();
    private Map<NodeApi.NodeListener, bl> d = new HashMap<NodeApi.NodeListener, bl>();
    private ExecutorService e = Executors.newCachedThreadPool();

    public bj(Context context, Looper looper, MobvoiApiClient.ConnectionCallbacks connectionCallbacks, MobvoiApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, new String[0]);
    }

    private FutureTask<Boolean> a(final ParcelFileDescriptor parcelFileDescriptor, final byte[] byArray) {
        return new FutureTask<Boolean>(new Callable<Boolean>(){

            /*
             * Loose catch block
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public Boolean a() {
                bp.a("WearableAdapter", "process assets: write data to FD : " + parcelFileDescriptor);
                ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
                autoCloseOutputStream.write(byArray);
                autoCloseOutputStream.flush();
                bp.a("WearableAdapter", "process assets: wrote bytes length " + byArray.length);
                Boolean bl2 = true;
                try {
                    bp.a("WearableAdapter", "process assets: close " + parcelFileDescriptor);
                    autoCloseOutputStream.close();
                    return bl2;
                }
                catch (IOException iOException) {
                    return bl2;
                }
                catch (IOException iOException) {
                    try {
                        bp.d("WearableAdapter", "process assets: write data failed " + parcelFileDescriptor);
                    }
                    catch (Throwable throwable) {
                        try {
                            bp.a("WearableAdapter", "process assets: close " + parcelFileDescriptor);
                            autoCloseOutputStream.close();
                        }
                        catch (IOException iOException2) {
                            throw throwable;
                        }
                        throw throwable;
                    }
                    try {
                        bp.a("WearableAdapter", "process assets: close " + parcelFileDescriptor);
                        autoCloseOutputStream.close();
                    }
                    catch (IOException iOException3) {
                        return false;
                    }
                    return false;
                }
            }

            @Override
            public /* synthetic */ Object call() throws Exception {
                return this.a();
            }
        });
    }

    @Override
    private void a(final bm<Status> bm2, bl bl2) throws RemoteException {
        ((bi)this.d()).a((bg)new bk(){

            @Override
            public void a(Status status) throws RemoteException {
                bp.a("WearableAdapter", "set status rsp, status = " + status);
                bm2.a(status);
            }
        }, new AddListenerRequest(bl2));
    }

    private void b(final bm<Status> bm2, bl bl2) throws RemoteException {
        ((bi)this.d()).a((bg)new bk(){

            @Override
            public void a(Status status) throws RemoteException {
                bp.a("WearableAdapter", "set status rsp, status = " + status);
                bm2.a(status);
            }
        }, new RemoveListenerRequest(bl2));
    }

    @Override
    protected /* synthetic */ IInterface a(IBinder iBinder) {
        return this.c(iBinder);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void a(int var1_1, IBinder var2_2, Bundle var3_3) {
        bp.a("WearableAdapter", "on post init handler, statusCode = " + var1_1);
        if (var1_1 != 0) {
            return;
        }
        try {
            bp.a("WearableAdapter", "on post init handler, service = " + var2_2);
            var4_4 = new bk(){

                @Override
                public void a(Status status) throws RemoteException {
                }
            };
            var5_6 = bi.a.a(var2_2);
            var6_7 /* !! */  = this.b;
            synchronized (var6_7 /* !! */ ) {
                var7_8 = this.b.values().iterator();
            }
        }
        catch (RemoteException var4_5) {
            bp.a("WearableAdapter", "on post init handler, error while adding listener = ", var4_5);
lbl14:
            // 2 sources

            while (true) {
                bp.a("WearableAdapter", "on post init handler finished.");
                super.a(var1_1, var2_2, var3_3);
                return;
            }
        }
        {
            while (var7_8.hasNext()) {
                var8_9 = var7_8.next();
                bp.a("WearableAdapter", "on post init handler, adding Message listener = " + var8_9);
                var5_6.a((bg)var4_4, new AddListenerRequest(var8_9));
            }
        }
        {
            var6_7 /* !! */  = this.c;
            ** synchronized (var6_7 /* !! */ )
        }
lbl-1000:
        // 2 sources

        {
            for (bl var8_9 : this.c.values()) {
                bp.a("WearableAdapter", "on post init handler, adding Data listener = " + var8_9);
                var5_6.a((bg)var4_4, new AddListenerRequest(var8_9));
            }
        }
        var6_7 /* !! */  = this.d;
        synchronized (var6_7 /* !! */ ) {
            for (bl var8_9 : this.d.values()) {
                bp.a("WearableAdapter", "on post init handler, adding Node listener = " + var8_9);
                var5_6.a((bg)var4_4, new AddListenerRequest(var8_9));
            }
        }
        ** while (true)
    }

    public void a(final bm<DataItemBuffer> bm2) throws RemoteException {
        ((bi)this.d()).c(new bk(){

            @Override
            public void a(DataHolder dataHolder) throws RemoteException {
                bm2.a(new DataItemBuffer(dataHolder));
            }
        }, null);
    }

    @Override
    public void a(final bm<DataApi.DeleteDataItemsResult> bm2, Uri uri) throws RemoteException {
        bp.a("WearableAdapter", "delete dataItems, uri = " + uri);
        ((bi)this.d()).a((bg)new bk(){

            @Override
            public void a(DeleteDataItemsResponse deleteDataItemsResponse) throws RemoteException {
                bm2.a(new bc.b(new Status(deleteDataItemsResponse.b), deleteDataItemsResponse.c));
            }
        }, uri);
    }

    @Override
    public void a(final bm<DataApi.GetFdForAssetResult> bm2, Asset asset) throws RemoteException {
        ((bi)this.d()).a((bg)new bk(){

            @Override
            public void a(GetFdForAssetResponse getFdForAssetResponse) throws RemoteException {
                bm2.a(new bc.c(new Status(getFdForAssetResponse.b), getFdForAssetResponse.c));
            }
        }, asset);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void a(bm<Status> bm2, DataApi.DataListener dataListener) throws RemoteException {
        bp.a("WearableAdapter", "add data listener start.");
        Map<DataApi.DataListener, bl> map = this.c;
        synchronized (map) {
            if (this.c.get(dataListener) != null) {
                bm2.a(new Status(4002));
                bp.a("WearableAdapter", "add data listener 4002 error!");
            } else {
                bl bl2 = bl.a(dataListener);
                this.c.put(dataListener, bl2);
                this.a(bm2, bl2);
                bp.a("WearableAdapter", "add data listener " + bl2 + " added");
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void a(bm<Status> bm2, MessageApi.MessageListener messageListener) throws RemoteException {
        bp.a("WearableAdapter", "add message listener start. listener = " + messageListener + ".");
        Map<MessageApi.MessageListener, bl> map = this.b;
        synchronized (map) {
            if (this.b.get(messageListener) != null) {
                bm2.a(new Status(4002));
                bp.a("WearableAdapter", "add message listener 4002 error!");
            } else {
                bl bl2 = bl.a(messageListener);
                this.b.put(messageListener, bl2);
                this.a(bm2, bl2);
                bp.a("WearableAdapter", "add message listener " + bl2 + " added");
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void a(bm<Status> bm2, NodeApi.NodeListener nodeListener) throws RemoteException {
        bp.a("WearableAdapter", "add node listener start.");
        Map<NodeApi.NodeListener, bl> map = this.d;
        synchronized (map) {
            if (this.d.get(nodeListener) != null) {
                bm2.a(new Status(4002));
                bp.a("WearableAdapter", "add node listener 4002 error!");
            } else {
                bl bl2 = bl.a(nodeListener);
                this.d.put(nodeListener, bl2);
                this.a(bm2, bl2);
                bp.a("WearableAdapter", "add node listener " + bl2 + " added");
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void a(final bm<DataApi.DataItemResult> bm2, PutDataRequest putDataRequest) throws RemoteException {
        bp.a("WearableAdapter", "put data item, uri = " + putDataRequest.getUri());
        PutDataRequest putDataRequest2 = PutDataRequest.createFromUri(putDataRequest.getUri());
        final ArrayList<ParcelFileDescriptor[]> arrayList = new ArrayList<ParcelFileDescriptor[]>();
        putDataRequest2.setData(putDataRequest.getData());
        if (putDataRequest.isUrgent()) {
            putDataRequest2.setUrgent();
        }
        if (putDataRequest.getAssets() != null) {
            for (Map.Entry<String, Asset> entry : putDataRequest.getAssets().entrySet()) {
                Asset asset = entry.getValue();
                if (asset.getData() != null) {
                    Object object;
                    try {
                        object = ParcelFileDescriptor.createPipe();
                    }
                    catch (IOException iOException) {
                        throw new IllegalStateException("create Fd failed, request = " + putDataRequest, iOException);
                    }
                    bp.a("WearableAdapter", "process assets: replace data with FD in asset: " + asset + " read:" + object[0] + " write:" + object[1]);
                    asset.setFd(object[0]);
                    object = this.a(object[1], asset.getData());
                    this.e.execute((Runnable)object);
                    arrayList.add((ParcelFileDescriptor[])object);
                }
                if (asset.getDigest() != null) {
                    putDataRequest2.putAsset(entry.getKey(), asset);
                    continue;
                }
                if (asset.getFd() == null) continue;
                putDataRequest2.putAsset(entry.getKey(), Asset.createFromFd(asset.getFd()));
            }
        }
        bp.a("WearableAdapter", "call remote put data item: " + putDataRequest2);
        ((bi)this.d()).a((bg)new bk(){

            @Override
            public void a(PutDataResponse object) throws RemoteException {
                bp.a("WearableAdapter", "receive put data response, status = " + ((PutDataResponse)object).b + ", dataItem = " + ((PutDataResponse)object).c);
                bm2.a(new bc.a(new Status(((PutDataResponse)object).b), ((PutDataResponse)object).c));
                if (((PutDataResponse)object).b != 0) {
                    object = arrayList.iterator();
                    while (object.hasNext()) {
                        ((FutureTask)object.next()).cancel(true);
                    }
                }
            }
        }, putDataRequest2);
    }

    public void a(final bm<MessageApi.SendMessageResult> bm2, String string2, String string3, byte[] byArray) throws RemoteException {
        bp.a("WearableAdapter", "send message. path: + " + string3 + ", length = " + bp.a(byArray));
        ((bi)this.d()).a(new bk(){

            @Override
            public void a(SendMessageResponse sendMessageResponse) throws RemoteException {
                bm2.a(new bd.a(new Status(sendMessageResponse.b), sendMessageResponse.c));
            }
        }, string2, string3, byArray);
    }

    @Override
    protected void a(d d2, e.c c2) throws RemoteException {
        d2.a(c2, 0, this.e().getPackageName());
    }

    public void b(final bm<NodeApi.GetConnectedNodesResult> bm2) throws RemoteException {
        ((bi)this.d()).b(new bk(){

            @Override
            public void a(final GetConnectedNodesResponse getConnectedNodesResponse) throws RemoteException {
                bm2.a(new NodeApi.GetConnectedNodesResult(){

                    @Override
                    public List<Node> getNodes() {
                        return getConnectedNodesResponse.a();
                    }

                    @Override
                    public Status getStatus() {
                        return new Status(0);
                    }
                });
            }
        });
    }

    public void b(final bm<DataApi.DataItemResult> bm2, Uri uri) throws RemoteException {
        ((bi)this.d()).b(new bk(){

            @Override
            public void a(GetDataItemResponse getDataItemResponse) throws RemoteException {
                bm2.a(new bc.a(new Status(getDataItemResponse.b), getDataItemResponse.c));
            }
        }, uri);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void b(bm<Status> bm2, DataApi.DataListener object) throws RemoteException {
        Map<DataApi.DataListener, bl> map = this.c;
        synchronized (map) {
            object = this.c.remove(object);
            if (object == null) {
                bm2.a(new Status(4002));
            } else {
                this.b(bm2, (bl)object);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void b(bm<Status> bm2, MessageApi.MessageListener object) throws RemoteException {
        Map<MessageApi.MessageListener, bl> map = this.b;
        synchronized (map) {
            object = this.b.remove(object);
            if (object == null) {
                bm2.a(new Status(4002));
            } else {
                this.b(bm2, (bl)object);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void b(bm<Status> bm2, NodeApi.NodeListener object) throws RemoteException {
        Map<NodeApi.NodeListener, bl> map = this.d;
        synchronized (map) {
            object = this.d.remove(object);
            if (object == null) {
                bm2.a(new Status(4002));
            } else {
                this.b(bm2, (bl)object);
            }
            return;
        }
    }

    protected bi c(IBinder iBinder) {
        return bi.a.a(iBinder);
    }

    @Override
    public void c(final bm<NodeApi.GetLocalNodeResult> bm2) throws RemoteException {
        ((bi)this.d()).c(new bk(){

            @Override
            public void a(final GetLocalNodeResponse getLocalNodeResponse) throws RemoteException {
                bm2.a(new NodeApi.GetLocalNodeResult(){

                    @Override
                    public Node getNode() {
                        return getLocalNodeResponse.a();
                    }

                    @Override
                    public Status getStatus() {
                        if (getLocalNodeResponse.a() != null && getLocalNodeResponse.a().getId() == null) {
                            return new Status(8);
                        }
                        return new Status(0);
                    }
                });
            }
        });
    }

    public void c(final bm<DataItemBuffer> bm2, Uri uri) throws RemoteException {
        ((bi)this.d()).c(new bk(){

            @Override
            public void a(DataHolder dataHolder) throws RemoteException {
                bm2.a(new DataItemBuffer(dataHolder));
            }
        }, uri);
    }

    @Override
    public void disconnect() {
        super.disconnect();
    }

    @Override
    protected String f() {
        return "com.mobvoi.android.wearable.internal.IWearableService";
    }

    @Override
    protected String g() {
        return "com.mobvoi.android.wearable.BIND";
    }

    @Override
    protected void i() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void j() {
        Map<Object, bl> map = this.b;
        synchronized (map) {
            for (Object object : this.b.keySet()) {
                try {
                    ((bi)this.d()).a((bg)new bk(){

                        @Override
                        public void a(Status status) throws RemoteException {
                        }
                    }, new RemoveListenerRequest(this.b.get(object)));
                }
                catch (RemoteException remoteException) {
                    remoteException.printStackTrace();
                }
            }
            this.b.clear();
        }
        map = this.c;
        synchronized (map) {
            for (Object object : this.c.keySet()) {
                try {
                    ((bi)this.d()).a((bg)new bk(){

                        @Override
                        public void a(Status status) throws RemoteException {
                        }
                    }, new RemoveListenerRequest(this.c.get(object)));
                }
                catch (RemoteException remoteException) {
                    remoteException.printStackTrace();
                }
            }
            this.c.clear();
        }
        map = this.d;
        synchronized (map) {
            Iterator<Object> iterator = this.d.keySet().iterator();
            while (true) {
                Object object;
                if (!iterator.hasNext()) {
                    this.d.clear();
                    return;
                }
                object = (NodeApi.NodeListener)iterator.next();
                try {
                    ((bi)this.d()).a((bg)new bk(){

                        @Override
                        public void a(Status status) throws RemoteException {
                        }
                    }, new RemoveListenerRequest(this.d.get(object)));
                }
                catch (RemoteException remoteException) {
                    remoteException.printStackTrace();
                    continue;
                }
                break;
            }
        }
    }
}

