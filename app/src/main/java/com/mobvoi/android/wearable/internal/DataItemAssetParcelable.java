/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 */
package com.mobvoi.android.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;
import android.text.TextUtils;
import com.mobvoi.android.common.internal.safeparcel.SafeParcelable;
import com.mobvoi.android.wearable.DataItemAsset;
import mobvoiapi.bn;
import mobvoiapi.bo;

@Keep
public class DataItemAssetParcelable
implements SafeParcelable,
DataItemAsset {
    public static final Parcelable.Creator<DataItemAssetParcelable> CREATOR = new a();
    private final String id;
    private final String key;
    final int versionCode;

    public DataItemAssetParcelable(int n2, String string2, String string3) {
        this.versionCode = n2;
        this.id = string2;
        this.key = string3;
    }

    public DataItemAssetParcelable(DataItemAsset dataItemAsset) {
        this.versionCode = 1;
        this.id = dataItemAsset.getId();
        this.key = dataItemAsset.getDataItemKey();
    }

    public DataItemAssetParcelable(String string2, String string3) {
        this(1, string2, string3);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (object instanceof DataItemAsset) {
            object = (DataItemAsset)object;
            return TextUtils.equals((CharSequence)this.id, (CharSequence)object.getId());
        }
        return false;
    }

    @Override
    public DataItemAsset freeze() {
        return this;
    }

    @Override
    public String getDataItemKey() {
        return this.key;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public int hashCode() {
        return this.id.hashCode() * 37 + 23273;
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DataItemAssetParcelable[");
        stringBuilder.append("@");
        stringBuilder.append(Integer.toHexString(this.hashCode()));
        if (this.id == null) {
            stringBuilder.append(",noid");
        } else {
            stringBuilder.append(",");
            stringBuilder.append(this.id);
        }
        stringBuilder.append(", key=");
        stringBuilder.append(this.key);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        a.a(this, parcel, n2);
    }

    public static class a
    implements Parcelable.Creator<DataItemAssetParcelable> {
        static void a(DataItemAssetParcelable dataItemAssetParcelable, Parcel parcel, int n2) {
            n2 = bo.a(parcel);
            bo.a(parcel, 1, dataItemAssetParcelable.versionCode);
            bo.a(parcel, 2, dataItemAssetParcelable.getId(), false);
            bo.a(parcel, 3, dataItemAssetParcelable.getDataItemKey(), false);
            bo.a(parcel, n2);
        }

        public DataItemAssetParcelable a(Parcel parcel) {
            String string2 = null;
            int n2 = bn.b(parcel);
            int n3 = 0;
            String string3 = null;
            block5: while (parcel.dataPosition() < n2) {
                int n4 = bn.a(parcel);
                switch (bn.a(n4)) {
                    default: {
                        bn.b(parcel, n4);
                        continue block5;
                    }
                    case 1: {
                        n3 = bn.c(parcel, n4);
                        continue block5;
                    }
                    case 2: {
                        string3 = bn.e(parcel, n4);
                        continue block5;
                    }
                    case 3: 
                }
                string2 = bn.e(parcel, n4);
            }
            if (parcel.dataPosition() != n2) {
                throw new RuntimeException("parcel size exceeded. index = " + n2 + ", parcel = " + parcel);
            }
            return new DataItemAssetParcelable(n3, string3, string2);
        }

        public DataItemAssetParcelable[] a(int n2) {
            return new DataItemAssetParcelable[n2];
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.a(parcel);
        }

        public /* synthetic */ Object[] newArray(int n2) {
            return this.a(n2);
        }
    }
}

