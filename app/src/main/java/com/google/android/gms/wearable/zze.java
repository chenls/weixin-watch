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
package com.google.android.gms.wearable;

import android.net.Uri;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.wearable.Asset;

public class zze
implements Parcelable.Creator<Asset> {
    static void zza(Asset asset, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, asset.mVersionCode);
        zzb.zza(parcel, 2, asset.getData(), false);
        zzb.zza(parcel, 3, asset.getDigest(), false);
        zzb.zza(parcel, 4, (Parcelable)asset.zzbqV, n2, false);
        zzb.zza(parcel, 5, (Parcelable)asset.uri, n2, false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzhX(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzly(n2);
    }

    public Asset zzhX(Parcel parcel) {
        Uri uri = null;
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        ParcelFileDescriptor parcelFileDescriptor = null;
        String string2 = null;
        byte[] byArray = null;
        block7: while (parcel.dataPosition() < n2) {
            int n4 = zza.zzat(parcel);
            switch (zza.zzca(n4)) {
                default: {
                    zza.zzb(parcel, n4);
                    continue block7;
                }
                case 1: {
                    n3 = zza.zzg(parcel, n4);
                    continue block7;
                }
                case 2: {
                    byArray = zza.zzs(parcel, n4);
                    continue block7;
                }
                case 3: {
                    string2 = zza.zzp(parcel, n4);
                    continue block7;
                }
                case 4: {
                    parcelFileDescriptor = (ParcelFileDescriptor)zza.zza(parcel, n4, ParcelFileDescriptor.CREATOR);
                    continue block7;
                }
                case 5: 
            }
            uri = (Uri)zza.zza(parcel, n4, Uri.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new Asset(n3, byArray, string2, parcelFileDescriptor, uri);
    }

    public Asset[] zzly(int n2) {
        return new Asset[n2];
    }
}

