/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.util.Log
 */
package com.google.android.gms.wearable;

import android.net.Uri;
import android.util.Log;
import com.google.android.gms.internal.zzsi;
import com.google.android.gms.internal.zzsu;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.PutDataRequest;

public class PutDataMapRequest {
    private final DataMap zzbrd;
    private final PutDataRequest zzbre;

    private PutDataMapRequest(PutDataRequest putDataRequest, DataMap dataMap) {
        this.zzbre = putDataRequest;
        this.zzbrd = new DataMap();
        if (dataMap != null) {
            this.zzbrd.putAll(dataMap);
        }
    }

    public static PutDataMapRequest create(String string2) {
        return new PutDataMapRequest(PutDataRequest.create(string2), null);
    }

    public static PutDataMapRequest createFromDataMapItem(DataMapItem dataMapItem) {
        return new PutDataMapRequest(PutDataRequest.zzr(dataMapItem.getUri()), dataMapItem.getDataMap());
    }

    public static PutDataMapRequest createWithAutoAppendedId(String string2) {
        return new PutDataMapRequest(PutDataRequest.createWithAutoAppendedId(string2), null);
    }

    public PutDataRequest asPutDataRequest() {
        zzsi.zza zza2 = zzsi.zza(this.zzbrd);
        this.zzbre.setData(zzsu.toByteArray(zza2.zzbty));
        int n2 = zza2.zzbtz.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            String string2 = Integer.toString(i2);
            Asset asset = zza2.zzbtz.get(i2);
            if (string2 == null) {
                throw new IllegalStateException("asset key cannot be null: " + asset);
            }
            if (asset == null) {
                throw new IllegalStateException("asset cannot be null: key=" + string2);
            }
            if (Log.isLoggable((String)"DataMap", (int)3)) {
                Log.d((String)"DataMap", (String)("asPutDataRequest: adding asset: " + string2 + " " + asset));
            }
            this.zzbre.putAsset(string2, asset);
        }
        return this.zzbre;
    }

    public DataMap getDataMap() {
        return this.zzbrd;
    }

    public Uri getUri() {
        return this.zzbre.getUri();
    }

    public boolean isUrgent() {
        return this.zzbre.isUrgent();
    }

    public PutDataMapRequest setUrgent() {
        this.zzbre.setUrgent();
        return this;
    }
}

