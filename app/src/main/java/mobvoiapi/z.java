/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcelable
 *  com.google.android.gms.location.LocationListener
 *  com.google.android.gms.location.LocationRequest
 *  com.google.android.gms.location.LocationServices
 */
package mobvoiapi;

import android.os.Bundle;
import android.os.Parcelable;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.mobvoi.android.common.ConnectionResult;
import com.mobvoi.android.common.UnsupportedException;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.common.api.Result;
import com.mobvoi.android.location.LocationListener;
import com.mobvoi.android.location.LocationRequest;
import com.mobvoi.android.location.LocationServices;
import com.mobvoi.android.wearable.Asset;
import com.mobvoi.android.wearable.DataApi;
import com.mobvoi.android.wearable.DataEventBuffer;
import com.mobvoi.android.wearable.DataItemAsset;
import com.mobvoi.android.wearable.MessageApi;
import com.mobvoi.android.wearable.NodeApi;
import com.mobvoi.android.wearable.internal.DataEventParcelable;
import com.mobvoi.android.wearable.internal.DataHolder;
import com.mobvoi.android.wearable.internal.DataItemAssetParcelable;
import com.mobvoi.android.wearable.internal.DataItemParcelable;
import com.mobvoi.android.wearable.internal.MessageEventHolder;
import com.mobvoi.android.wearable.internal.NodeHolder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import mobvoiapi.ad;
import mobvoiapi.bc;
import mobvoiapi.i;
import mobvoiapi.k;
import mobvoiapi.l;
import mobvoiapi.m;
import mobvoiapi.n;
import mobvoiapi.o;
import mobvoiapi.p;
import mobvoiapi.q;
import mobvoiapi.r;
import mobvoiapi.s;
import mobvoiapi.x;

public class z {
    public static Api<? extends Api.ApiOptions.NotRequiredOptions> a(com.mobvoi.android.common.api.Api api) {
        if (com.mobvoi.android.wearable.Wearable.API == api) {
            return Wearable.API;
        }
        if (LocationServices.API == api) {
            return com.google.android.gms.location.LocationServices.API;
        }
        return null;
    }

    public static GoogleApiClient.ConnectionCallbacks a(MobvoiApiClient.ConnectionCallbacks connectionCallbacks) {
        if (connectionCallbacks == null) {
            return null;
        }
        return new k(connectionCallbacks);
    }

    public static GoogleApiClient.OnConnectionFailedListener a(MobvoiApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        if (onConnectionFailedListener == null) {
            return null;
        }
        return new q(onConnectionFailedListener);
    }

    public static GoogleApiClient a(MobvoiApiClient mobvoiApiClient) {
        if (mobvoiApiClient == null) {
            throw new NullPointerException("Input parameter MobvoiApiClient is null, please check it.");
        }
        if (mobvoiApiClient instanceof ad) {
            if ((mobvoiApiClient = ((ad)mobvoiApiClient).a()) instanceof i) {
                throw new IllegalStateException("Can not use MMS to call GMS function. Please use loadService or adaptService before initialize a MobvoiApiClient.");
            }
            return ((s)mobvoiApiClient).a();
        }
        throw new UnsupportedException("Api google implements must use GoogleApiClient. But receive the " + mobvoiApiClient.getClass());
    }

    public static <R1 extends Result, R2 extends com.google.android.gms.common.api.Result> ResultCallback<R2> a(com.mobvoi.android.common.api.ResultCallback<R1> resultCallback) {
        if (resultCallback == null) {
            return null;
        }
        return new r(resultCallback);
    }

    public static com.google.android.gms.location.LocationListener a(LocationListener locationListener) {
        if (locationListener == null) {
            return null;
        }
        return new n(locationListener);
    }

    public static com.google.android.gms.location.LocationRequest a(LocationRequest locationRequest) {
        return com.google.android.gms.location.LocationRequest.create().setExpirationTime(locationRequest.getExpirationTime()).setFastestInterval(locationRequest.getFastestInterval()).setInterval(locationRequest.getInterval()).setNumUpdates(locationRequest.getNumUpdates()).setPriority(locationRequest.getPriority()).setSmallestDisplacement(locationRequest.getSmallestDisplacement());
    }

    public static com.google.android.gms.wearable.Asset a(Asset asset) {
        if (asset.getData() != null) {
            return com.google.android.gms.wearable.Asset.createFromBytes(asset.getData());
        }
        if (asset.getFd() != null) {
            return com.google.android.gms.wearable.Asset.createFromFd(asset.getFd());
        }
        if (asset.getUri() != null) {
            return com.google.android.gms.wearable.Asset.createFromUri(asset.getUri());
        }
        if (asset.getDigest() != null) {
            return com.google.android.gms.wearable.Asset.createFromRef(asset.getDigest());
        }
        return null;
    }

    public static DataApi.DataListener a(DataApi.DataListener dataListener) {
        if (dataListener == null) {
            return null;
        }
        return new m(dataListener);
    }

    public static com.google.android.gms.wearable.DataItemAsset a(DataItemAsset dataItemAsset) {
        return new l(dataItemAsset.getId(), dataItemAsset.getDataItemKey());
    }

    public static MessageApi.MessageListener a(MessageApi.MessageListener messageListener) {
        if (messageListener == null) {
            return null;
        }
        return new o(messageListener);
    }

    public static NodeApi.NodeListener a(NodeApi.NodeListener nodeListener) {
        if (nodeListener == null) {
            return null;
        }
        return new p(nodeListener);
    }

    public static PutDataRequest a(com.mobvoi.android.wearable.PutDataRequest object) {
        if (object == null || ((com.mobvoi.android.wearable.PutDataRequest)((Object)object)).getUri() == null) {
            return null;
        }
        PutDataRequest putDataRequest = PutDataRequest.create(((com.mobvoi.android.wearable.PutDataRequest)((Object)object)).getUri().getPath());
        putDataRequest.setData(((com.mobvoi.android.wearable.PutDataRequest)((Object)object)).getData());
        if (((com.mobvoi.android.wearable.PutDataRequest)((Object)object)).isUrgent()) {
            putDataRequest.setUrgent();
        }
        for (Map.Entry<String, Asset> entry : ((com.mobvoi.android.wearable.PutDataRequest)((Object)object)).getAssets().entrySet()) {
            com.google.android.gms.wearable.Asset asset = z.a(entry.getValue());
            if (asset == null) continue;
            putDataRequest.putAsset(entry.getKey(), asset);
        }
        return putDataRequest;
    }

    public static ConnectionResult a(com.google.android.gms.common.ConnectionResult connectionResult) {
        if (connectionResult == null) {
            return null;
        }
        return new ConnectionResult(connectionResult.getErrorCode(), connectionResult.getResolution());
    }

    public static <R1 extends Result, R2 extends com.google.android.gms.common.api.Result> com.mobvoi.android.common.api.PendingResult<R1> a(PendingResult<R2> pendingResult) {
        return new x(pendingResult);
    }

    public static <R1 extends Result, R2 extends com.google.android.gms.common.api.Result> R1 a(R2 R2) {
        if (R2 instanceof NodeApi.GetConnectedNodesResult) {
            return (R1)z.a((NodeApi.GetConnectedNodesResult)R2);
        }
        if (R2 instanceof NodeApi.GetLocalNodeResult) {
            return (R1)z.a((NodeApi.GetLocalNodeResult)R2);
        }
        if (R2 instanceof Status) {
            return (R1)z.a((Status)R2);
        }
        if (R2 instanceof MessageApi.SendMessageResult) {
            return (R1)z.a((MessageApi.SendMessageResult)R2);
        }
        if (R2 instanceof DataApi.DataItemResult) {
            return (R1)z.a((DataApi.DataItemResult)R2);
        }
        if (R2 instanceof DataApi.DeleteDataItemsResult) {
            return (R1)z.a((DataApi.DeleteDataItemsResult)R2);
        }
        if (R2 instanceof DataItemBuffer) {
            return (R1)z.a((DataItemBuffer)R2);
        }
        if (R2 instanceof DataApi.GetFdForAssetResult) {
            return (R1)z.a((DataApi.GetFdForAssetResult)R2);
        }
        throw new UnsupportedException("not implement the convert to mobvoi for class : " + R2.getClass().getName());
    }

    public static com.mobvoi.android.common.api.Status a(Status status) {
        if (status == null) {
            return null;
        }
        return new com.mobvoi.android.common.api.Status(status.getStatusCode(), status.getStatusMessage(), status.getResolution());
    }

    public static DataApi.DataItemResult a(DataApi.DataItemResult dataItemResult) {
        if (dataItemResult == null) {
            return null;
        }
        return new bc.a(z.a(dataItemResult.getStatus()), z.a(dataItemResult.getDataItem()));
    }

    public static DataApi.DeleteDataItemsResult a(DataApi.DeleteDataItemsResult deleteDataItemsResult) {
        if (deleteDataItemsResult == null) {
            return null;
        }
        return new bc.b(z.a(deleteDataItemsResult.getStatus()), deleteDataItemsResult.getNumDeleted());
    }

    public static DataApi.GetFdForAssetResult a(final DataApi.GetFdForAssetResult getFdForAssetResult) {
        if (getFdForAssetResult == null) {
            return null;
        }
        return new bc.c(z.a(getFdForAssetResult.getStatus()), getFdForAssetResult.getFd()){

            @Override
            public void release() {
                getFdForAssetResult.release();
            }
        };
    }

    public static DataEventBuffer a(com.google.android.gms.wearable.DataEventBuffer dataEventBuffer) {
        if (dataEventBuffer == null) {
            return null;
        }
        ArrayList<DataEventParcelable> arrayList = new ArrayList<DataEventParcelable>();
        Iterator iterator = dataEventBuffer.iterator();
        while (iterator.hasNext()) {
            DataEventParcelable dataEventParcelable = z.a((DataEvent)iterator.next());
            if (dataEventParcelable == null) continue;
            arrayList.add(dataEventParcelable);
        }
        return new DataEventBuffer(new DataHolder(dataEventBuffer.getStatus().getStatusCode(), null, arrayList));
    }

    public static com.mobvoi.android.wearable.DataItemBuffer a(DataItemBuffer dataItemBuffer) {
        if (dataItemBuffer == null) {
            return null;
        }
        ArrayList<DataItemParcelable> arrayList = new ArrayList<DataItemParcelable>();
        Iterator iterator = dataItemBuffer.iterator();
        while (iterator.hasNext()) {
            DataItemParcelable dataItemParcelable = z.a((DataItem)iterator.next());
            if (dataItemParcelable == null) continue;
            arrayList.add(dataItemParcelable);
        }
        return new com.mobvoi.android.wearable.DataItemBuffer(new DataHolder(dataItemBuffer.getStatus().getStatusCode(), arrayList, null));
    }

    public static MessageApi.SendMessageResult a(final MessageApi.SendMessageResult sendMessageResult) {
        if (sendMessageResult == null) {
            return null;
        }
        return new MessageApi.SendMessageResult(){

            @Override
            public int getRequestId() {
                return sendMessageResult.getRequestId();
            }

            @Override
            public com.mobvoi.android.common.api.Status getStatus() {
                return z.a(sendMessageResult.getStatus());
            }
        };
    }

    public static com.mobvoi.android.wearable.MessageEvent a(MessageEvent messageEvent) {
        if (messageEvent == null) {
            return null;
        }
        return new MessageEventHolder(messageEvent.getRequestId(), messageEvent.getSourceNodeId(), messageEvent.getPath(), messageEvent.getData());
    }

    public static com.mobvoi.android.wearable.Node a(Node node) {
        if (node == null) {
            return null;
        }
        return new NodeHolder(node.getId(), node.getDisplayName(), node.isNearby());
    }

    public static NodeApi.GetConnectedNodesResult a(final NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
        if (getConnectedNodesResult == null) {
            return null;
        }
        return new NodeApi.GetConnectedNodesResult(){

            @Override
            public List<com.mobvoi.android.wearable.Node> getNodes() {
                if (getConnectedNodesResult.getNodes() == null) {
                    return null;
                }
                ArrayList<com.mobvoi.android.wearable.Node> arrayList = new ArrayList<com.mobvoi.android.wearable.Node>();
                Iterator<Node> iterator = getConnectedNodesResult.getNodes().iterator();
                while (iterator.hasNext()) {
                    arrayList.add(z.a(iterator.next()));
                }
                return arrayList;
            }

            @Override
            public com.mobvoi.android.common.api.Status getStatus() {
                return z.a(getConnectedNodesResult.getStatus());
            }
        };
    }

    public static NodeApi.GetLocalNodeResult a(final NodeApi.GetLocalNodeResult getLocalNodeResult) {
        if (getLocalNodeResult == null) {
            return null;
        }
        return new NodeApi.GetLocalNodeResult(){

            @Override
            public com.mobvoi.android.wearable.Node getNode() {
                return z.a(getLocalNodeResult.getNode());
            }

            @Override
            public com.mobvoi.android.common.api.Status getStatus() {
                return z.a(getLocalNodeResult.getStatus());
            }
        };
    }

    public static DataEventParcelable a(DataEvent dataEvent) {
        if (dataEvent == null) {
            return null;
        }
        return new DataEventParcelable(dataEvent.getType(), z.a(dataEvent.getDataItem()));
    }

    public static DataItemAssetParcelable a(com.google.android.gms.wearable.DataItemAsset dataItemAsset) {
        if (dataItemAsset == null) {
            return null;
        }
        return new DataItemAssetParcelable(dataItemAsset.getId(), dataItemAsset.getDataItemKey());
    }

    public static DataItemParcelable a(DataItem dataItem) {
        if (dataItem == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        if (dataItem.getAssets() != null && dataItem.getAssets().size() > 0) {
            for (Map.Entry<String, com.google.android.gms.wearable.DataItemAsset> entry : dataItem.getAssets().entrySet()) {
                DataItemAssetParcelable dataItemAssetParcelable = z.a(entry.getValue());
                if (dataItemAssetParcelable == null) continue;
                bundle.putParcelable(entry.getKey(), (Parcelable)dataItemAssetParcelable);
            }
        }
        return new DataItemParcelable(dataItem.getUri(), bundle, dataItem.getData());
    }
}

