/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.util.Log
 */
package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.util.Log;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemAsset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class zzad
implements DataItem {
    private Uri mUri;
    private byte[] zzaKm;
    private Map<String, DataItemAsset> zzbsy;

    public zzad(DataItem object) {
        this.mUri = object.getUri();
        this.zzaKm = object.getData();
        HashMap hashMap = new HashMap();
        for (Map.Entry<String, DataItemAsset> entry : object.getAssets().entrySet()) {
            if (entry.getKey() == null) continue;
            hashMap.put(entry.getKey(), entry.getValue().freeze());
        }
        this.zzbsy = Collections.unmodifiableMap(hashMap);
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.zzIM();
    }

    @Override
    public Map<String, DataItemAsset> getAssets() {
        return this.zzbsy;
    }

    @Override
    public byte[] getData() {
        return this.zzaKm;
    }

    @Override
    public Uri getUri() {
        return this.mUri;
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    @Override
    public DataItem setData(byte[] byArray) {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return this.toString(Log.isLoggable((String)"DataItem", (int)3));
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString(boolean bl2) {
        StringBuilder stringBuilder = new StringBuilder("DataItemEntity{ ");
        stringBuilder.append("uri=" + this.mUri);
        Object object = new StringBuilder().append(", dataSz=");
        Object object2 = this.zzaKm == null ? "null" : Integer.valueOf(this.zzaKm.length);
        stringBuilder.append(((StringBuilder)object).append(object2).toString());
        stringBuilder.append(", numAssets=" + this.zzbsy.size());
        if (bl2 && !this.zzbsy.isEmpty()) {
            stringBuilder.append(", assets=[");
            object = this.zzbsy.entrySet().iterator();
            object2 = "";
            while (object.hasNext()) {
                Map.Entry entry = (Map.Entry)object.next();
                stringBuilder.append((String)object2 + (String)entry.getKey() + ": " + ((DataItemAsset)entry.getValue()).getId());
                object2 = ", ";
            }
            stringBuilder.append("]");
        }
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    public DataItem zzIM() {
        return this;
    }
}

