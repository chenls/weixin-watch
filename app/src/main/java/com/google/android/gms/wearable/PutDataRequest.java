/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.wearable;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemAsset;
import com.google.android.gms.wearable.internal.DataItemAssetParcelable;
import com.google.android.gms.wearable.zzh;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class PutDataRequest
implements SafeParcelable {
    public static final Parcelable.Creator<PutDataRequest> CREATOR = new zzh();
    public static final String WEAR_URI_SCHEME = "wear";
    private static final long zzbrf = TimeUnit.MINUTES.toMillis(30L);
    private static final Random zzbrg = new SecureRandom();
    private final Uri mUri;
    final int mVersionCode;
    private byte[] zzaKm;
    private final Bundle zzbrh;
    private long zzbri;

    private PutDataRequest(int n2, Uri uri) {
        this(n2, uri, new Bundle(), null, zzbrf);
    }

    PutDataRequest(int n2, Uri uri, Bundle bundle, byte[] byArray, long l2) {
        this.mVersionCode = n2;
        this.mUri = uri;
        this.zzbrh = bundle;
        this.zzbrh.setClassLoader(DataItemAssetParcelable.class.getClassLoader());
        this.zzaKm = byArray;
        this.zzbri = l2;
    }

    public static PutDataRequest create(String string2) {
        return PutDataRequest.zzr(PutDataRequest.zzgL(string2));
    }

    public static PutDataRequest createFromDataItem(DataItem dataItem) {
        PutDataRequest putDataRequest = PutDataRequest.zzr(dataItem.getUri());
        for (Map.Entry<String, DataItemAsset> entry : dataItem.getAssets().entrySet()) {
            if (entry.getValue().getId() == null) {
                throw new IllegalStateException("Cannot create an asset for a put request without a digest: " + entry.getKey());
            }
            putDataRequest.putAsset(entry.getKey(), Asset.createFromRef(entry.getValue().getId()));
        }
        putDataRequest.setData(dataItem.getData());
        return putDataRequest;
    }

    public static PutDataRequest createWithAutoAppendedId(String string2) {
        StringBuilder stringBuilder = new StringBuilder(string2);
        if (!string2.endsWith("/")) {
            stringBuilder.append("/");
        }
        stringBuilder.append("PN").append(zzbrg.nextLong());
        return new PutDataRequest(2, PutDataRequest.zzgL(stringBuilder.toString()));
    }

    private static Uri zzgL(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            throw new IllegalArgumentException("An empty path was supplied.");
        }
        if (!string2.startsWith("/")) {
            throw new IllegalArgumentException("A path must start with a single / .");
        }
        if (string2.startsWith("//")) {
            throw new IllegalArgumentException("A path must start with a single / .");
        }
        return new Uri.Builder().scheme(WEAR_URI_SCHEME).path(string2).build();
    }

    public static PutDataRequest zzr(Uri uri) {
        return new PutDataRequest(2, uri);
    }

    public int describeContents() {
        return 0;
    }

    public Asset getAsset(String string2) {
        return (Asset)this.zzbrh.getParcelable(string2);
    }

    public Map<String, Asset> getAssets() {
        HashMap<String, Asset> hashMap = new HashMap<String, Asset>();
        for (String string2 : this.zzbrh.keySet()) {
            hashMap.put(string2, (Asset)this.zzbrh.getParcelable(string2));
        }
        return Collections.unmodifiableMap(hashMap);
    }

    public byte[] getData() {
        return this.zzaKm;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public boolean hasAsset(String string2) {
        return this.zzbrh.containsKey(string2);
    }

    public boolean isUrgent() {
        return this.zzbri == 0L;
    }

    public PutDataRequest putAsset(String string2, Asset asset) {
        zzx.zzz(string2);
        zzx.zzz(asset);
        this.zzbrh.putParcelable(string2, (Parcelable)asset);
        return this;
    }

    public PutDataRequest removeAsset(String string2) {
        this.zzbrh.remove(string2);
        return this;
    }

    public PutDataRequest setData(byte[] byArray) {
        this.zzaKm = byArray;
        return this;
    }

    public PutDataRequest setUrgent() {
        this.zzbri = 0L;
        return this;
    }

    public String toString() {
        return this.toString(Log.isLoggable((String)"DataMap", (int)3));
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString(boolean bl2) {
        StringBuilder stringBuilder = new StringBuilder("PutDataRequest[");
        StringBuilder stringBuilder2 = new StringBuilder().append("dataSz=");
        Object object = this.zzaKm == null ? "null" : Integer.valueOf(this.zzaKm.length);
        stringBuilder.append(stringBuilder2.append(object).toString());
        stringBuilder.append(", numAssets=" + this.zzbrh.size());
        stringBuilder.append(", uri=" + this.mUri);
        stringBuilder.append(", syncDeadline=" + this.zzbri);
        if (!bl2) {
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
        stringBuilder.append("]\n  assets: ");
        object = this.zzbrh.keySet().iterator();
        while (true) {
            if (!object.hasNext()) {
                stringBuilder.append("\n  ]");
                return stringBuilder.toString();
            }
            String string2 = (String)object.next();
            stringBuilder.append("\n    " + string2 + ": " + this.zzbrh.getParcelable(string2));
        }
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzh.zza(this, parcel, n2);
    }

    public Bundle zzIv() {
        return this.zzbrh;
    }

    public long zzIw() {
        return this.zzbri;
    }
}

