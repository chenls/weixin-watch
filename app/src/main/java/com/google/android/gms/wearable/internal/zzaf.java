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
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzc;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemAsset;
import com.google.android.gms.wearable.internal.zzac;
import com.google.android.gms.wearable.internal.zzad;
import java.util.HashMap;
import java.util.Map;

public final class zzaf
extends zzc
implements DataItem {
    private final int zzaDQ;

    public zzaf(DataHolder dataHolder, int n2, int n3) {
        super(dataHolder, n2);
        this.zzaDQ = n3;
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.zzIM();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Map<String, DataItemAsset> getAssets() {
        HashMap<String, DataItemAsset> hashMap = new HashMap<String, DataItemAsset>(this.zzaDQ);
        int n2 = 0;
        while (n2 < this.zzaDQ) {
            zzac zzac2 = new zzac(this.zzahi, this.zzaje + n2);
            if (zzac2.getDataItemKey() != null) {
                hashMap.put(zzac2.getDataItemKey(), zzac2);
            }
            ++n2;
        }
        return hashMap;
    }

    @Override
    public byte[] getData() {
        return this.getByteArray("data");
    }

    @Override
    public Uri getUri() {
        return Uri.parse((String)this.getString("path"));
    }

    @Override
    public DataItem setData(byte[] byArray) {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return this.toString(Log.isLoggable((String)"DataItem", (int)3));
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public String toString(boolean bl2) {
        void var2_4;
        byte[] byArray = this.getData();
        Object object = this.getAssets();
        StringBuilder stringBuilder = new StringBuilder("DataItemInternal{ ");
        stringBuilder.append("uri=" + this.getUri());
        Object object2 = new StringBuilder().append(", dataSz=");
        if (byArray == null) {
            String string2 = "null";
        } else {
            Integer n2 = byArray.length;
        }
        stringBuilder.append(((StringBuilder)object2).append(var2_4).toString());
        stringBuilder.append(", numAssets=" + object.size());
        if (bl2 && !object.isEmpty()) {
            stringBuilder.append(", assets=[");
            object = object.entrySet().iterator();
            String string3 = "";
            while (object.hasNext()) {
                void var2_6;
                object2 = (Map.Entry)object.next();
                stringBuilder.append((String)var2_6 + (String)object2.getKey() + ": " + ((DataItemAsset)object2.getValue()).getId());
                String string4 = ", ";
            }
            stringBuilder.append("]");
        }
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    public DataItem zzIM() {
        return new zzad(this);
    }
}

