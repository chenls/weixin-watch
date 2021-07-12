/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.Log
 */
package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemAsset;
import com.google.android.gms.wearable.internal.DataItemAssetParcelable;
import com.google.android.gms.wearable.internal.zzae;
import java.util.HashMap;
import java.util.Map;

public class DataItemParcelable
implements SafeParcelable,
DataItem {
    public static final Parcelable.Creator<DataItemParcelable> CREATOR = new zzae();
    private final Uri mUri;
    final int mVersionCode;
    private byte[] zzaKm;
    private final Map<String, DataItemAsset> zzbsy;

    DataItemParcelable(int n2, Uri object, Bundle bundle, byte[] byArray) {
        this.mVersionCode = n2;
        this.mUri = object;
        object = new HashMap();
        bundle.setClassLoader(DataItemAssetParcelable.class.getClassLoader());
        for (String string2 : bundle.keySet()) {
            object.put(string2, (DataItemAssetParcelable)bundle.getParcelable(string2));
        }
        this.zzbsy = object;
        this.zzaKm = byArray;
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.zzIN();
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
    public /* synthetic */ DataItem setData(byte[] byArray) {
        return this.zzz(byArray);
    }

    public String toString() {
        return this.toString(Log.isLoggable((String)"DataItem", (int)3));
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString(boolean bl2) {
        StringBuilder stringBuilder = new StringBuilder("DataItemParcelable[");
        stringBuilder.append("@");
        stringBuilder.append(Integer.toHexString(this.hashCode()));
        StringBuilder stringBuilder2 = new StringBuilder().append(",dataSz=");
        Object object = this.zzaKm == null ? "null" : Integer.valueOf(this.zzaKm.length);
        stringBuilder.append(stringBuilder2.append(object).toString());
        stringBuilder.append(", numAssets=" + this.zzbsy.size());
        stringBuilder.append(", uri=" + this.mUri);
        if (!bl2) {
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
        stringBuilder.append("]\n  assets: ");
        object = this.zzbsy.keySet().iterator();
        while (true) {
            if (!object.hasNext()) {
                stringBuilder.append("\n  ]");
                return stringBuilder.toString();
            }
            String string2 = object.next();
            stringBuilder.append("\n    " + string2 + ": " + this.zzbsy.get(string2));
        }
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzae.zza(this, parcel, n2);
    }

    public DataItemParcelable zzIN() {
        return this;
    }

    public Bundle zzIv() {
        Bundle bundle = new Bundle();
        bundle.setClassLoader(DataItemAssetParcelable.class.getClassLoader());
        for (Map.Entry<String, DataItemAsset> entry : this.zzbsy.entrySet()) {
            bundle.putParcelable(entry.getKey(), (Parcelable)new DataItemAssetParcelable(entry.getValue()));
        }
        return bundle;
    }

    public DataItemParcelable zzz(byte[] byArray) {
        this.zzaKm = byArray;
        return this;
    }
}

