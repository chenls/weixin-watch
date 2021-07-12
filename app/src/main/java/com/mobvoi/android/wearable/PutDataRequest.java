/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 */
package com.mobvoi.android.wearable;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.mobvoi.android.common.internal.safeparcel.SafeParcelable;
import com.mobvoi.android.wearable.Asset;
import com.mobvoi.android.wearable.DataItem;
import com.mobvoi.android.wearable.DataItemAsset;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import mobvoiapi.bn;
import mobvoiapi.bo;

public class PutDataRequest
implements SafeParcelable {
    public static final Parcelable.Creator<PutDataRequest> CREATOR = new PutDataRequestCreator();
    public static final long DEADLINE_OPPORTUNISTIC_MS = TimeUnit.MINUTES.toMillis(30L);
    public static final String WEAR_URI_SCHEME = "wear";
    @SuppressLint(value={"TrulyRandom"})
    private static final Random a = new SecureRandom();
    private final int b;
    private final Uri c;
    private final Bundle d;
    private byte[] e;
    private long f;

    private PutDataRequest(int n2, Uri uri, Bundle bundle, byte[] byArray) {
        this(n2, uri, bundle, byArray, DEADLINE_OPPORTUNISTIC_MS);
    }

    private PutDataRequest(int n2, Uri uri, Bundle bundle, byte[] byArray, long l2) {
        this.b = n2;
        this.c = uri;
        this.d = bundle;
        this.e = byArray;
        this.f = l2;
    }

    private static Uri a(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            throw new IllegalArgumentException("path is null or empty.");
        }
        if (!string2.startsWith("/") || string2.startsWith("//")) {
            throw new IllegalArgumentException("path must start with a single / .");
        }
        return new Uri.Builder().scheme(WEAR_URI_SCHEME).path(string2).build();
    }

    public static PutDataRequest create(String string2) {
        return PutDataRequest.createFromUri(PutDataRequest.a(string2));
    }

    public static PutDataRequest createFromDataItem(DataItem dataItem) {
        PutDataRequest putDataRequest = PutDataRequest.createFromUri(dataItem.getUri());
        for (Map.Entry<String, DataItemAsset> entry : dataItem.getAssets().entrySet()) {
            if (entry.getValue().getId() == null) {
                throw new IllegalStateException("asset id must exist, key = " + entry.getKey());
            }
            putDataRequest.putAsset(entry.getKey(), Asset.createFromRef(entry.getValue().getId()));
        }
        putDataRequest.setData(dataItem.getData());
        return putDataRequest;
    }

    public static PutDataRequest createFromUri(Uri uri) {
        return new PutDataRequest(1, uri, new Bundle(), null);
    }

    public static PutDataRequest createWithAutoAppendedId(String string2) {
        StringBuilder stringBuilder = new StringBuilder(string2);
        if (!string2.endsWith("/")) {
            stringBuilder.append("/");
        }
        stringBuilder.append("PN").append(a.nextLong());
        return PutDataRequest.createFromUri(PutDataRequest.a(stringBuilder.toString()));
    }

    public int describeContents() {
        return 0;
    }

    public Asset getAsset(String string2) {
        return (Asset)this.d.get(string2);
    }

    public Map<String, Asset> getAssets() {
        HashMap<String, Asset> hashMap = new HashMap<String, Asset>();
        for (String string2 : this.d.keySet()) {
            hashMap.put(string2, (Asset)this.d.getParcelable(string2));
        }
        return Collections.unmodifiableMap(hashMap);
    }

    public Bundle getBundle() {
        return this.d;
    }

    public byte[] getData() {
        return this.e;
    }

    public long getSyncDeadline() {
        return this.f;
    }

    public Uri getUri() {
        return this.c;
    }

    public int getVersionCode() {
        return this.b;
    }

    public boolean hasAsset(String string2) {
        return this.d.containsKey(string2);
    }

    public boolean isUrgent() {
        return this.f == 0L;
    }

    public PutDataRequest putAsset(String string2, Asset asset) {
        if (string2 == null || asset == null) {
            throw new NullPointerException("parameters is null, key = " + string2 + ", asset = " + asset);
        }
        if (asset.getData() == null && asset.getFd() == null && asset.getDigest() == null) {
            throw new IllegalArgumentException("asset is empty");
        }
        this.d.putParcelable(string2, (Parcelable)asset);
        return this;
    }

    public PutDataRequest removeAsset(String string2) {
        this.d.remove(string2);
        return this;
    }

    public PutDataRequest setData(byte[] byArray) {
        this.e = byArray;
        return this;
    }

    public PutDataRequest setUrgent() {
        this.f = 0L;
        return this;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String toString() {
        int n2;
        StringBuilder stringBuilder = new StringBuilder().append("PutDataRequest[@ Uri:").append(this.c).append(", DataSz: ");
        if (this.e == null) {
            n2 = 0;
            return stringBuilder.append(n2).append(", assetNum: ").append(this.d.size()).append(", syncDeadline: ").append(this.f).append("]").toString();
        }
        n2 = this.e.length;
        return stringBuilder.append(n2).append(", assetNum: ").append(this.d.size()).append(", syncDeadline: ").append(this.f).append("]").toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        PutDataRequestCreator.writeToParcel(this, parcel, n2);
    }

    public static class PutDataRequestCreator
    implements Parcelable.Creator<PutDataRequest> {
        public static void writeToParcel(PutDataRequest putDataRequest, Parcel parcel, int n2) {
            int n3 = bo.a(parcel);
            bo.a(parcel, 1, putDataRequest.getVersionCode());
            bo.a(parcel, 2, (Parcelable)putDataRequest.getUri(), n2, false);
            bo.a(parcel, 4, putDataRequest.getBundle(), false);
            bo.a(parcel, 5, putDataRequest.getData(), false);
            bo.a(parcel, 6, putDataRequest.getSyncDeadline());
            bo.a(parcel, n3);
        }

        public PutDataRequest createFromParcel(Parcel parcel) {
            int n2 = bn.b(parcel);
            int n3 = 0;
            long l2 = 0L;
            byte[] byArray = null;
            Bundle bundle = null;
            Uri uri = null;
            block7: while (parcel.dataPosition() < n2) {
                int n4 = bn.a(parcel);
                switch (bn.a(n4)) {
                    default: {
                        bn.b(parcel, n4);
                        continue block7;
                    }
                    case 1: {
                        n3 = bn.c(parcel, n4);
                        continue block7;
                    }
                    case 2: {
                        uri = (Uri)bn.a(parcel, n4, Uri.CREATOR);
                        continue block7;
                    }
                    case 4: {
                        bundle = bn.f(parcel, n4);
                        bundle.setClassLoader(PutDataRequest.class.getClassLoader());
                        continue block7;
                    }
                    case 5: {
                        byArray = bn.g(parcel, n4);
                        continue block7;
                    }
                    case 6: 
                }
                l2 = bn.d(parcel, n4);
            }
            if (parcel.dataPosition() != n2) {
                throw new RuntimeException("parcel size exceeded. index = " + n2 + ", parcel = " + parcel);
            }
            return new PutDataRequest(n3, uri, bundle, byArray, l2);
        }

        public PutDataRequest[] newArray(int n2) {
            return new PutDataRequest[n2];
        }
    }
}

