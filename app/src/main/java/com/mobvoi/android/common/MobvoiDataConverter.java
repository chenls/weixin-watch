/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.ParcelFileDescriptor
 */
package com.mobvoi.android.common;

import android.net.Uri;
import android.os.ParcelFileDescriptor;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.mobvoi.android.common.ConnectionResult;
import com.mobvoi.android.common.api.Status;
import com.mobvoi.android.wearable.Asset;
import com.mobvoi.android.wearable.DataApi;
import com.mobvoi.android.wearable.DataEvent;
import com.mobvoi.android.wearable.DataItem;
import com.mobvoi.android.wearable.DataItemAsset;
import com.mobvoi.android.wearable.DataMap;
import com.mobvoi.android.wearable.MessageApi;
import com.mobvoi.android.wearable.NodeApi;
import com.mobvoi.android.wearable.internal.DataEventParcelable;
import com.mobvoi.android.wearable.internal.DataItemParcelable;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import mobvoiapi.z;

public class MobvoiDataConverter {
    public static com.google.android.gms.common.ConnectionResult convertToGoogle(ConnectionResult connectionResult) {
        if (connectionResult == null) {
            return null;
        }
        return new com.google.android.gms.common.ConnectionResult(connectionResult.getErrorCode(), connectionResult.getResolution());
    }

    public static com.google.android.gms.common.api.Status convertToGoogle(Status status) {
        if (status == null) {
            return null;
        }
        return new com.google.android.gms.common.api.Status(status.getStatusCode(), status.getStatusMessage(), status.getResolution());
    }

    public static com.google.android.gms.wearable.Asset convertToGoogle(Asset asset) {
        return z.a(asset);
    }

    public static DataApi.DataItemResult convertToGoogle(final DataApi.DataItemResult dataItemResult) {
        if (dataItemResult == null) {
            return null;
        }
        return new DataApi.DataItemResult(){

            @Override
            public com.google.android.gms.wearable.DataItem getDataItem() {
                return MobvoiDataConverter.convertToGoogle(dataItemResult.getDataItem());
            }

            @Override
            public com.google.android.gms.common.api.Status getStatus() {
                return MobvoiDataConverter.convertToGoogle(dataItemResult.getStatus());
            }
        };
    }

    public static DataApi.DeleteDataItemsResult convertToGoogle(final DataApi.DeleteDataItemsResult deleteDataItemsResult) {
        if (deleteDataItemsResult == null) {
            return null;
        }
        return new DataApi.DeleteDataItemsResult(){

            @Override
            public int getNumDeleted() {
                return deleteDataItemsResult.getNumDeleted();
            }

            @Override
            public com.google.android.gms.common.api.Status getStatus() {
                return MobvoiDataConverter.convertToGoogle(deleteDataItemsResult.getStatus());
            }
        };
    }

    public static DataApi.GetFdForAssetResult convertToGoogle(final DataApi.GetFdForAssetResult getFdForAssetResult) {
        if (getFdForAssetResult == null) {
            return null;
        }
        return new DataApi.GetFdForAssetResult(){

            @Override
            public ParcelFileDescriptor getFd() {
                return getFdForAssetResult.getFd();
            }

            @Override
            public InputStream getInputStream() {
                return getFdForAssetResult.getInputStream();
            }

            @Override
            public com.google.android.gms.common.api.Status getStatus() {
                return MobvoiDataConverter.convertToGoogle(getFdForAssetResult.getStatus());
            }

            @Override
            public void release() {
                getFdForAssetResult.release();
            }
        };
    }

    public static com.google.android.gms.wearable.DataEvent convertToGoogle(final DataEvent dataEvent) {
        if (dataEvent == null) {
            return null;
        }
        return new com.google.android.gms.wearable.DataEvent(){

            @Override
            public com.google.android.gms.wearable.DataEvent freeze() {
                return this;
            }

            @Override
            public com.google.android.gms.wearable.DataItem getDataItem() {
                return MobvoiDataConverter.convertToGoogle(dataEvent.getDataItem());
            }

            @Override
            public int getType() {
                return dataEvent.getType();
            }

            @Override
            public boolean isDataValid() {
                return dataEvent.isDataValid();
            }
        };
    }

    public static com.google.android.gms.wearable.DataItem convertToGoogle(final DataItem dataItem) {
        if (dataItem == null) {
            return null;
        }
        return new com.google.android.gms.wearable.DataItem(){
            private byte[] b;
            {
                this.b = dataItem.getData();
            }

            @Override
            public com.google.android.gms.wearable.DataItem freeze() {
                return this;
            }

            @Override
            public Map<String, com.google.android.gms.wearable.DataItemAsset> getAssets() {
                HashMap<String, com.google.android.gms.wearable.DataItemAsset> hashMap = new HashMap<String, com.google.android.gms.wearable.DataItemAsset>();
                for (String string2 : dataItem.getAssets().keySet()) {
                    hashMap.put(string2, MobvoiDataConverter.convertToGoogle(dataItem.getAssets().get(string2)));
                }
                return hashMap;
            }

            @Override
            public byte[] getData() {
                return this.b;
            }

            @Override
            public Uri getUri() {
                return dataItem.getUri();
            }

            @Override
            public boolean isDataValid() {
                return dataItem.isDataValid();
            }

            @Override
            public com.google.android.gms.wearable.DataItem setData(byte[] byArray) {
                this.b = byArray;
                return this;
            }
        };
    }

    public static com.google.android.gms.wearable.DataItemAsset convertToGoogle(DataItemAsset dataItemAsset) {
        return z.a(dataItemAsset);
    }

    public static com.google.android.gms.wearable.DataMap convertToGoogle(DataMap dataMap) {
        return com.google.android.gms.wearable.DataMap.fromBundle(dataMap.toBundle());
    }

    public static MessageApi.SendMessageResult convertToGoogle(final MessageApi.SendMessageResult sendMessageResult) {
        if (sendMessageResult == null) {
            return null;
        }
        return new MessageApi.SendMessageResult(){

            @Override
            public int getRequestId() {
                return sendMessageResult.getRequestId();
            }

            @Override
            public com.google.android.gms.common.api.Status getStatus() {
                return MobvoiDataConverter.convertToGoogle(sendMessageResult.getStatus());
            }
        };
    }

    public static MessageEvent convertToGoogle(final com.mobvoi.android.wearable.MessageEvent messageEvent) {
        if (messageEvent == null) {
            return null;
        }
        return new MessageEvent(){

            @Override
            public byte[] getData() {
                return messageEvent.getData();
            }

            @Override
            public String getPath() {
                return messageEvent.getPath();
            }

            @Override
            public int getRequestId() {
                return messageEvent.getRequestId();
            }

            @Override
            public String getSourceNodeId() {
                return messageEvent.getSourceNodeId();
            }
        };
    }

    public static Node convertToGoogle(final com.mobvoi.android.wearable.Node node) {
        if (node == null) {
            return null;
        }
        return new Node(){

            @Override
            public String getDisplayName() {
                return node.getDisplayName();
            }

            @Override
            public String getId() {
                return node.getId();
            }

            @Override
            public boolean isNearby() {
                return node.isNearby();
            }
        };
    }

    public static NodeApi.GetConnectedNodesResult convertToGoogle(final NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
        if (getConnectedNodesResult == null) {
            return null;
        }
        return new NodeApi.GetConnectedNodesResult(){

            @Override
            public List<Node> getNodes() {
                if (getConnectedNodesResult.getNodes() == null) {
                    return null;
                }
                ArrayList<Node> arrayList = new ArrayList<Node>();
                Iterator<com.mobvoi.android.wearable.Node> iterator = getConnectedNodesResult.getNodes().iterator();
                while (iterator.hasNext()) {
                    arrayList.add(MobvoiDataConverter.convertToGoogle(iterator.next()));
                }
                return arrayList;
            }

            @Override
            public com.google.android.gms.common.api.Status getStatus() {
                return MobvoiDataConverter.convertToGoogle(getConnectedNodesResult.getStatus());
            }
        };
    }

    public static NodeApi.GetLocalNodeResult convertToGoogle(final NodeApi.GetLocalNodeResult getLocalNodeResult) {
        if (getLocalNodeResult == null) {
            return null;
        }
        return new NodeApi.GetLocalNodeResult(){

            @Override
            public Node getNode() {
                return MobvoiDataConverter.convertToGoogle(getLocalNodeResult.getNode());
            }

            @Override
            public com.google.android.gms.common.api.Status getStatus() {
                return MobvoiDataConverter.convertToGoogle(getLocalNodeResult.getStatus());
            }
        };
    }

    public static PutDataMapRequest convertToGoogle(com.mobvoi.android.wearable.PutDataMapRequest putDataMapRequest) {
        PutDataMapRequest putDataMapRequest2 = PutDataMapRequest.create(putDataMapRequest.getUri().getPath());
        putDataMapRequest2.getDataMap().putAll(MobvoiDataConverter.convertToGoogle(putDataMapRequest.getDataMap()));
        if (putDataMapRequest.isUrgent()) {
            putDataMapRequest2.setUrgent();
        }
        return putDataMapRequest2;
    }

    public static PutDataRequest convertToGoogle(com.mobvoi.android.wearable.PutDataRequest putDataRequest) {
        return z.a(putDataRequest);
    }

    public static ConnectionResult convertToMobvoi(com.google.android.gms.common.ConnectionResult connectionResult) {
        return z.a(connectionResult);
    }

    public static Status convertToMobvoi(com.google.android.gms.common.api.Status status) {
        return z.a(status);
    }

    public static Asset convertToMobvoi(com.google.android.gms.wearable.Asset asset) {
        if (asset.getData() != null) {
            return Asset.createFromBytes(asset.getData());
        }
        if (asset.getFd() != null) {
            return Asset.createFromFd(asset.getFd());
        }
        if (asset.getUri() != null) {
            return Asset.createFromUri(asset.getUri());
        }
        if (asset.getDigest() != null) {
            return Asset.createFromRef(asset.getDigest());
        }
        return null;
    }

    public static DataApi.DataItemResult convertToMobvoi(DataApi.DataItemResult dataItemResult) {
        return z.a(dataItemResult);
    }

    public static DataApi.DeleteDataItemsResult convertToMobvoi(DataApi.DeleteDataItemsResult deleteDataItemsResult) {
        return z.a(deleteDataItemsResult);
    }

    public static DataApi.GetFdForAssetResult convertToMobvoi(DataApi.GetFdForAssetResult getFdForAssetResult) {
        return z.a(getFdForAssetResult);
    }

    public static DataItemAsset convertToMobvoi(com.google.android.gms.wearable.DataItemAsset dataItemAsset) {
        return z.a(dataItemAsset);
    }

    public static DataMap convertToMobvoi(com.google.android.gms.wearable.DataMap dataMap) {
        return DataMap.fromBundle(dataMap.toBundle());
    }

    public static MessageApi.SendMessageResult convertToMobvoi(MessageApi.SendMessageResult sendMessageResult) {
        return z.a(sendMessageResult);
    }

    public static com.mobvoi.android.wearable.MessageEvent convertToMobvoi(MessageEvent messageEvent) {
        return z.a(messageEvent);
    }

    public static com.mobvoi.android.wearable.Node convertToMobvoi(Node node) {
        return z.a(node);
    }

    public static NodeApi.GetConnectedNodesResult convertToMobvoi(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
        return z.a(getConnectedNodesResult);
    }

    public static NodeApi.GetLocalNodeResult convertToMobvoi(NodeApi.GetLocalNodeResult getLocalNodeResult) {
        return z.a(getLocalNodeResult);
    }

    public static com.mobvoi.android.wearable.PutDataMapRequest convertToMobvoi(PutDataMapRequest putDataMapRequest) {
        com.mobvoi.android.wearable.PutDataMapRequest putDataMapRequest2 = com.mobvoi.android.wearable.PutDataMapRequest.create(putDataMapRequest.getUri().getPath());
        putDataMapRequest2.getDataMap().putAll(MobvoiDataConverter.convertToMobvoi(putDataMapRequest.getDataMap()));
        if (putDataMapRequest.isUrgent()) {
            putDataMapRequest2.setUrgent();
        }
        return putDataMapRequest2;
    }

    public static com.mobvoi.android.wearable.PutDataRequest convertToMobvoi(PutDataRequest object) {
        if (object == null || ((PutDataRequest)((Object)object)).getUri() == null) {
            return null;
        }
        com.mobvoi.android.wearable.PutDataRequest putDataRequest = com.mobvoi.android.wearable.PutDataRequest.create(((PutDataRequest)((Object)object)).getUri().getPath());
        putDataRequest.setData(((PutDataRequest)((Object)object)).getData());
        if (((PutDataRequest)((Object)object)).isUrgent()) {
            putDataRequest.setUrgent();
        }
        for (Map.Entry<String, com.google.android.gms.wearable.Asset> entry : ((PutDataRequest)((Object)object)).getAssets().entrySet()) {
            Asset asset = MobvoiDataConverter.convertToMobvoi(entry.getValue());
            if (asset == null) continue;
            putDataRequest.putAsset(entry.getKey(), asset);
        }
        return putDataRequest;
    }

    public static DataEventParcelable convertToMobvoi(com.google.android.gms.wearable.DataEvent dataEvent) {
        return z.a(dataEvent);
    }

    public static DataItemParcelable convertToMobvoi(com.google.android.gms.wearable.DataItem dataItem) {
        return z.a(dataItem);
    }
}

