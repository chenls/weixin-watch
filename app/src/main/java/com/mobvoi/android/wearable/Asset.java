/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.ParcelFileDescriptor
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.mobvoi.android.wearable;

import android.net.Uri;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.mobvoi.android.common.UnsupportedException;
import com.mobvoi.android.common.internal.safeparcel.SafeParcelable;
import mobvoiapi.bn;
import mobvoiapi.bo;
import mobvoiapi.j;

public class Asset
implements SafeParcelable {
    public static final Parcelable.Creator<Asset> CREATOR = new AssetCreator();
    final int a;
    protected byte[] b;
    protected ParcelFileDescriptor c;
    protected String d;
    protected Uri e;

    Asset(int n2, byte[] byArray, String string2, ParcelFileDescriptor parcelFileDescriptor, Uri uri) {
        this.a = n2;
        this.b = byArray;
        this.c = parcelFileDescriptor;
        this.d = string2;
        this.e = uri;
    }

    public static Asset createFromBytes(byte[] byArray) {
        if (byArray == null) {
            throw new IllegalArgumentException("Asset data is null!");
        }
        return new Asset(1, byArray, null, null, null);
    }

    public static Asset createFromFd(ParcelFileDescriptor parcelFileDescriptor) {
        if (parcelFileDescriptor == null) {
            throw new IllegalArgumentException("Asset fd is null!");
        }
        return new Asset(1, null, null, parcelFileDescriptor, null);
    }

    public static Asset createFromRef(String string2) {
        if (string2 == null) {
            throw new IllegalArgumentException("Asset digest is null!");
        }
        return new Asset(1, null, string2, null, null);
    }

    public static Asset createFromUri(Uri uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Asset uri is null!");
        }
        throw new UnsupportedException();
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        boolean bl2 = false;
        boolean bl3 = bl2;
        if (bl2) return bl3;
        bl3 = bl2 = object instanceof Asset;
        if (!bl2) return bl3;
        object = (Asset)object;
        if (!j.a(this.b, ((Asset)object).b)) return false;
        if (!j.a(this.c, ((Asset)object).c)) return false;
        if (!j.a(this.d, ((Asset)object).d)) return false;
        if (!j.a(this.e, ((Asset)object).e)) return false;
        return true;
    }

    public byte[] getData() {
        return this.b;
    }

    public String getDigest() {
        return this.d;
    }

    public ParcelFileDescriptor getFd() {
        return this.c;
    }

    public Uri getUri() {
        return this.e;
    }

    public int getVersionCode() {
        return this.a;
    }

    public int hashCode() {
        return j.a(new Object[]{this.b, this.c, this.d, this.e});
    }

    public void setData(byte[] byArray) {
        this.b = byArray;
    }

    public void setFd(ParcelFileDescriptor parcelFileDescriptor) {
        this.c = parcelFileDescriptor;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Asset[@");
        stringBuilder.append(Integer.toHexString(this.hashCode()));
        if (this.b != null) {
            stringBuilder.append(", size=");
            stringBuilder.append(this.b.length);
        }
        if (this.c != null) {
            stringBuilder.append(", fd=");
            stringBuilder.append(this.c);
        }
        if (this.d != null) {
            stringBuilder.append(", digest=");
            stringBuilder.append(this.d);
        }
        if (this.e != null) {
            stringBuilder.append(", uri=");
            stringBuilder.append(this.e);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        AssetCreator.write(this, parcel, n2);
    }

    public static class AssetCreator
    implements Parcelable.Creator<Asset> {
        public static void write(Asset asset, Parcel parcel, int n2) {
            int n3 = bo.a(parcel);
            bo.a(parcel, 1, asset.getVersionCode());
            bo.a(parcel, 2, asset.getData(), false);
            bo.a(parcel, 3, asset.getDigest(), false);
            bo.a(parcel, 4, (Parcelable)asset.getFd(), n2, false);
            bo.a(parcel, 5, (Parcelable)asset.getUri(), n2, false);
            bo.a(parcel, n3);
        }

        public Asset createFromParcel(Parcel parcel) {
            Uri uri = null;
            int n2 = bn.b(parcel);
            int n3 = 0;
            ParcelFileDescriptor parcelFileDescriptor = null;
            String string2 = null;
            byte[] byArray = null;
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
                        byArray = bn.g(parcel, n4);
                        continue block7;
                    }
                    case 3: {
                        string2 = bn.e(parcel, n4);
                        continue block7;
                    }
                    case 4: {
                        parcelFileDescriptor = (ParcelFileDescriptor)bn.a(parcel, n4, ParcelFileDescriptor.CREATOR);
                        continue block7;
                    }
                    case 5: 
                }
                uri = (Uri)bn.a(parcel, n4, Uri.CREATOR);
            }
            if (parcel.dataPosition() != n2) {
                throw new RuntimeException("parcel size exceeded. index = " + n2 + ", parcel = " + parcel);
            }
            return new Asset(n3, byArray, string2, parcelFileDescriptor, uri);
        }

        public Asset[] newArray(int n2) {
            return new Asset[n2];
        }
    }
}

