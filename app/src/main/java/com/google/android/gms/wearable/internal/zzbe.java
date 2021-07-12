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

public class zzbe
implements Parcelable.Creator<PackageStorageInfo> {
    static void zza(PackageStorageInfo packageStorageInfo, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, packageStorageInfo.versionCode);
        zzb.zza(parcel, 2, packageStorageInfo.packageName, false);
        zzb.zza(parcel, 3, packageStorageInfo.label, false);
        zzb.zza(parcel, 4, packageStorageInfo.zzbta);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zziD(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzmh(n2);
    }

    public PackageStorageInfo zziD(Parcel parcel) {
        String string2 = null;
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        long l2 = 0L;
        String string3 = null;
        block6: while (parcel.dataPosition() < n2) {
            int n4 = zza.zzat(parcel);
            switch (zza.zzca(n4)) {
                default: {
                    zza.zzb(parcel, n4);
                    continue block6;
                }
                case 1: {
                    n3 = zza.zzg(parcel, n4);
                    continue block6;
                }
                case 2: {
                    string3 = zza.zzp(parcel, n4);
                    continue block6;
                }
                case 3: {
                    string2 = zza.zzp(parcel, n4);
                    continue block6;
                }
                case 4: 
            }
            l2 = zza.zzi(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new PackageStorageInfo(n3, string3, string2, l2);
    }

    public PackageStorageInfo[] zzmh(int n2) {
        return new PackageStorageInfo[n2];
    }
}

