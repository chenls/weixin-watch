/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.wearable.internal.PackageStorageInfo;
import com.google.android.gms.wearable.internal.StorageInfoResponse;
import java.util.ArrayList;

public class zzbl
implements Parcelable.Creator<StorageInfoResponse> {
    static void zza(StorageInfoResponse storageInfoResponse, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, storageInfoResponse.versionCode);
        zzb.zzc(parcel, 2, storageInfoResponse.statusCode);
        zzb.zza(parcel, 3, storageInfoResponse.zzbta);
        zzb.zzc(parcel, 4, storageInfoResponse.zzbtc, false);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zziI(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzmm(n2);
    }

    public StorageInfoResponse zziI(Parcel parcel) {
        int n2 = 0;
        int n3 = zza.zzau(parcel);
        long l2 = 0L;
        ArrayList<PackageStorageInfo> arrayList = null;
        int n4 = 0;
        block6: while (parcel.dataPosition() < n3) {
            int n5 = zza.zzat(parcel);
            switch (zza.zzca(n5)) {
                default: {
                    zza.zzb(parcel, n5);
                    continue block6;
                }
                case 1: {
                    n4 = zza.zzg(parcel, n5);
                    continue block6;
                }
                case 2: {
                    n2 = zza.zzg(parcel, n5);
                    continue block6;
                }
                case 3: {
                    l2 = zza.zzi(parcel, n5);
                    continue block6;
                }
                case 4: 
            }
            arrayList = zza.zzc(parcel, n5, PackageStorageInfo.CREATOR);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new StorageInfoResponse(n4, n2, l2, arrayList);
    }

    public StorageInfoResponse[] zzmm(int n2) {
        return new StorageInfoResponse[n2];
    }
}

