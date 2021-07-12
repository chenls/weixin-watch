/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.mobvoi.android.wearable;

import android.net.Uri;
import com.mobvoi.android.wearable.Asset;
import com.mobvoi.android.wearable.DataMap;
import com.mobvoi.android.wearable.DataMapItem;
import com.mobvoi.android.wearable.PutDataRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PutDataMapRequest {
    private final PutDataRequest a;
    private final DataMap b;

    private PutDataMapRequest(PutDataRequest putDataRequest, DataMap dataMap) {
        this.a = putDataRequest;
        this.b = new DataMap();
        if (dataMap != null) {
            this.b.putAll(dataMap);
        }
    }

    private static void a(DataMap dataMap, Map<String, Asset> object, String string2) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String string3 : dataMap.keySet()) {
            Object object2 = dataMap.get(string3);
            if (object2 instanceof Asset) {
                object.put(string2 + string3, (Asset)object2);
                arrayList.add(string3);
                continue;
            }
            if (object2 instanceof DataMap) {
                PutDataMapRequest.a((DataMap)object2, (Map<String, Asset>)object, string2 + string3 + "_@_");
                continue;
            }
            if (!(object2 instanceof List)) continue;
            object2 = (List)object2;
            for (int i2 = 0; i2 < object2.size(); ++i2) {
                if (!(object2.get(i2) instanceof DataMap)) continue;
                PutDataMapRequest.a((DataMap)object2.get(i2), (Map<String, Asset>)object, string2 + string3 + "_@_" + i2 + "_#_");
            }
        }
        object = arrayList.iterator();
        while (object.hasNext()) {
            dataMap.remove((String)object.next());
        }
    }

    public static PutDataMapRequest create(String string2) {
        return new PutDataMapRequest(PutDataRequest.create(string2), null);
    }

    public static PutDataMapRequest createFromDataMapItem(DataMapItem dataMapItem) {
        return new PutDataMapRequest(PutDataRequest.createFromUri(dataMapItem.getUri()), dataMapItem.getDataMap());
    }

    public static PutDataMapRequest createFromUri(Uri uri) {
        return new PutDataMapRequest(PutDataRequest.createFromUri(uri), null);
    }

    public static PutDataMapRequest createWithAutoAppendedId(String string2) {
        return new PutDataMapRequest(PutDataRequest.createWithAutoAppendedId(string2), null);
    }

    public PutDataRequest asPutDataRequest() {
        HashMap<String, Asset> hashMap = new HashMap<String, Asset>();
        PutDataMapRequest.a(this.b, hashMap, "");
        Object object = this.b.toByteArray();
        this.a.setData((byte[])object);
        object = hashMap.keySet().iterator();
        while (object.hasNext()) {
            String string2 = (String)object.next();
            this.a.putAsset(string2, (Asset)hashMap.get(string2));
        }
        return this.a;
    }

    public DataMap getDataMap() {
        return this.b;
    }

    public Uri getUri() {
        return this.a.getUri();
    }

    public boolean isUrgent() {
        return this.a.isUrgent();
    }

    public PutDataMapRequest setUrgent() {
        this.a.setUrgent();
        return this;
    }
}

