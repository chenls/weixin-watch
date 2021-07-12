/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.mobvoi.android.wearable.internal;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;
import com.mobvoi.android.common.internal.safeparcel.SafeParcelable;
import com.mobvoi.android.wearable.DataItem;
import com.mobvoi.android.wearable.DataItemAsset;
import com.mobvoi.android.wearable.internal.DataItemAssetParcelable;
import java.util.HashMap;
import java.util.Map;
import mobvoiapi.bn;
import mobvoiapi.bo;

@Keep
public class DataItemParcelable
implements SafeParcelable,
DataItem {
    public static final Parcelable.Creator<DataItemParcelable> CREATOR = new a();
    private Map<String, DataItemAsset> assets = new HashMap<String, DataItemAsset>();
    private byte[] data;
    private Uri uri;
    private int versionCode;

    public DataItemParcelable(int n2, Uri object, Bundle bundle, byte[] object2) {
        this.versionCode = n2;
        this.uri = object;
        this.data = object2;
        bundle.setClassLoader(DataItemAssetParcelable.class.getClassLoader());
        object = bundle.keySet().iterator();
        while (object.hasNext()) {
            object2 = (String)object.next();
            this.assets.put((String)object2, (DataItemAssetParcelable)bundle.getParcelable((String)object2));
        }
    }

    public DataItemParcelable(Uri uri, Bundle bundle, byte[] byArray) {
        this(1, uri, bundle, byArray);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public DataItem freeze() {
        return this;
    }

    @Override
    public Map<String, DataItemAsset> getAssets() {
        return this.assets;
    }

    public Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.setClassLoader(DataItemAssetParcelable.class.getClassLoader());
        for (String string2 : this.assets.keySet()) {
            bundle.putParcelable(string2, (Parcelable)new DataItemAssetParcelable(this.assets.get(string2)));
        }
        return bundle;
    }

    @Override
    public byte[] getData() {
        return this.data;
    }

    @Override
    public Uri getUri() {
        return this.uri;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    @Override
    public DataItem setData(byte[] byArray) {
        this.data = byArray;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("DataItemParcelable[");
        stringBuilder.append("@");
        stringBuilder.append(Integer.toHexString(this.hashCode()));
        StringBuilder stringBuilder2 = new StringBuilder().append(",dataSz=");
        Object object = this.data == null ? "null" : Integer.valueOf(this.data.length);
        stringBuilder.append(stringBuilder2.append(object).toString());
        stringBuilder.append(", numAssets=" + this.assets.size());
        stringBuilder.append(", uri=" + this.uri);
        stringBuilder.append("]\n  assets: [");
        object = this.assets.keySet().iterator();
        while (true) {
            if (!object.hasNext()) {
                stringBuilder.append("\n  ]");
                return stringBuilder.toString();
            }
            String string2 = object.next();
            stringBuilder.append("\n    " + string2 + ": " + this.assets.get(string2));
        }
    }

    public void writeToParcel(Parcel parcel, int n2) {
        a.a(this, parcel, n2);
    }

    public static class a
    implements Parcelable.Creator<DataItemParcelable> {
        static void a(DataItemParcelable dataItemParcelable, Parcel parcel, int n2) {
            int n3 = bo.a(parcel);
            bo.a(parcel, 1, dataItemParcelable.versionCode);
            bo.a(parcel, 2, (Parcelable)dataItemParcelable.getUri(), n2, false);
            bo.a(parcel, 4, dataItemParcelable.getBundle(), false);
            bo.a(parcel, 5, dataItemParcelable.getData(), false);
            bo.a(parcel, n3);
        }

        /*
         * Enabled aggressive block sorting
         */
        public DataItemParcelable a(Parcel parcel) {
            byte[] byArray = null;
            int n2 = bn.b(parcel);
            int n3 = 0;
            Bundle bundle = null;
            Bundle bundle2 = null;
            while (parcel.dataPosition() < n2) {
                Bundle bundle3;
                int n4 = bn.a(parcel);
                switch (bn.a(n4)) {
                    default: {
                        bn.b(parcel, n4);
                        bundle3 = bundle;
                        bundle = bundle2;
                        bundle2 = bundle3;
                        break;
                    }
                    case 1: {
                        n3 = bn.c(parcel, n4);
                        bundle3 = bundle2;
                        bundle2 = bundle;
                        bundle = bundle3;
                        break;
                    }
                    case 2: {
                        bundle3 = (Uri)bn.a(parcel, n4, Uri.CREATOR);
                        bundle2 = bundle;
                        bundle = bundle3;
                        break;
                    }
                    case 4: {
                        bundle3 = bn.f(parcel, n4);
                        bundle = bundle2;
                        bundle2 = bundle3;
                        break;
                    }
                    case 5: {
                        byArray = bn.g(parcel, n4);
                        bundle3 = bundle2;
                        bundle2 = bundle;
                        bundle = bundle3;
                    }
                }
                bundle3 = bundle;
                bundle = bundle2;
                bundle2 = bundle3;
            }
            if (parcel.dataPosition() != n2) {
                throw new RuntimeException("parcel size exceeded. index = " + n2 + ", parcel = " + parcel);
            }
            return new DataItemParcelable(n3, (Uri)bundle2, bundle, byArray);
        }

        public DataItemParcelable[] a(int n2) {
            return new DataItemParcelable[n2];
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.a(parcel);
        }

        public /* synthetic */ Object[] newArray(int n2) {
            return this.a(n2);
        }
    }
}

