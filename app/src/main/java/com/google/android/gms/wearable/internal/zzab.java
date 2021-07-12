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
import com.google.android.gms.wearable.internal.DataItemAssetParcelable;

public class zzab
implements Parcelable.Creator<DataItemAssetParcelable> {
    static void zza(DataItemAssetParcelable dataItemAssetParcelable, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, dataItemAssetParcelable.mVersionCode);
        zzb.zza(parcel, 2, dataItemAssetParcelable.getId(), false);
        zzb.zza(parcel, 3, dataItemAssetParcelable.getDataItemKey(), false);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzik(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzlO(n2);
    }

    public DataItemAssetParcelable zzik(Parcel parcel) {
        String string2 = null;
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        String string3 = null;
        block5: while (parcel.dataPosition() < n2) {
            int n4 = zza.zzat(parcel);
            switch (zza.zzca(n4)) {
                default: {
                    zza.zzb(parcel, n4);
                    continue block5;
                }
                case 1: {
                    n3 = zza.zzg(parcel, n4);
                    continue block5;
                }
                case 2: {
                    string3 = zza.zzp(parcel, n4);
                    continue block5;
                }
                case 3: 
            }
            string2 = zza.zzp(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new DataItemAssetParcelable(n3, string3, string2);
    }

    public DataItemAssetParcelable[] zzlO(int n2) {
        return new DataItemAssetParcelable[n2];
    }
}

