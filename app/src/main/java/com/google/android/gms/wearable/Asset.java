/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.ParcelFileDescriptor
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable;

import android.net.Uri;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.wearable.zze;

public class Asset
implements SafeParcelable {
    public static final Parcelable.Creator<Asset> CREATOR = new zze();
    final int mVersionCode;
    public Uri uri;
    private byte[] zzaKm;
    private String zzbqU;
    public ParcelFileDescriptor zzbqV;

    Asset(int n2, byte[] byArray, String string2, ParcelFileDescriptor parcelFileDescriptor, Uri uri) {
        this.mVersionCode = n2;
        this.zzaKm = byArray;
        this.zzbqU = string2;
        this.zzbqV = parcelFileDescriptor;
        this.uri = uri;
    }

    public static Asset createFromBytes(byte[] byArray) {
        if (byArray == null) {
            throw new IllegalArgumentException("Asset data cannot be null");
        }
        return new Asset(1, byArray, null, null, null);
    }

    public static Asset createFromFd(ParcelFileDescriptor parcelFileDescriptor) {
        if (parcelFileDescriptor == null) {
            throw new IllegalArgumentException("Asset fd cannot be null");
        }
        return new Asset(1, null, null, parcelFileDescriptor, null);
    }

    public static Asset createFromRef(String string2) {
        if (string2 == null) {
            throw new IllegalArgumentException("Asset digest cannot be null");
        }
        return new Asset(1, null, string2, null, null);
    }

    public static Asset createFromUri(Uri uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Asset uri cannot be null");
        }
        return new Asset(1, null, null, null, uri);
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (!(object instanceof Asset)) {
                    return false;
                }
                object = (Asset)object;
                if (!zzw.equal(this.zzaKm, ((Asset)object).zzaKm) || !zzw.equal(this.zzbqU, ((Asset)object).zzbqU) || !zzw.equal(this.zzbqV, ((Asset)object).zzbqV) || !zzw.equal(this.uri, ((Asset)object).uri)) break block5;
            }
            return true;
        }
        return false;
    }

    public byte[] getData() {
        return this.zzaKm;
    }

    public String getDigest() {
        return this.zzbqU;
    }

    public ParcelFileDescriptor getFd() {
        return this.zzbqV;
    }

    public Uri getUri() {
        return this.uri;
    }

    public int hashCode() {
        return zzw.hashCode(this.zzaKm, this.zzbqU, this.zzbqV, this.uri);
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Asset[@");
        stringBuilder.append(Integer.toHexString(this.hashCode()));
        if (this.zzbqU == null) {
            stringBuilder.append(", nodigest");
        } else {
            stringBuilder.append(", ");
            stringBuilder.append(this.zzbqU);
        }
        if (this.zzaKm != null) {
            stringBuilder.append(", size=");
            stringBuilder.append(this.zzaKm.length);
        }
        if (this.zzbqV != null) {
            stringBuilder.append(", fd=");
            stringBuilder.append(this.zzbqV);
        }
        if (this.uri != null) {
            stringBuilder.append(", uri=");
            stringBuilder.append(this.uri);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zze.zza(this, parcel, n2 | 1);
    }
}

