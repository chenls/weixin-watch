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
package com.google.android.gms.wearable;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.wearable.PutDataRequest;

public class zzh
implements Parcelable.Creator<PutDataRequest> {
    static void zza(PutDataRequest putDataRequest, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, putDataRequest.mVersionCode);
        zzb.zza(parcel, 2, (Parcelable)putDataRequest.getUri(), n2, false);
        zzb.zza(parcel, 4, putDataRequest.zzIv(), false);
        zzb.zza(parcel, 5, putDataRequest.getData(), false);
        zzb.zza(parcel, 6, putDataRequest.zzIw());
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzhZ(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzlA(n2);
    }

    public PutDataRequest zzhZ(Parcel parcel) {
        byte[] byArray = null;
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        long l2 = 0L;
        Bundle bundle = null;
        Uri uri = null;
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
                    uri = (Uri)zza.zza(parcel, n4, Uri.CREATOR);
                    continue block7;
                }
                case 4: {
                    bundle = zza.zzr(parcel, n4);
                    continue block7;
                }
                case 5: {
                    byArray = zza.zzs(parcel, n4);
                    continue block7;
                }
                case 6: 
            }
            l2 = zza.zzi(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new PutDataRequest(n3, uri, bundle, byArray, l2);
    }

    public PutDataRequest[] zzlA(int n2) {
        return new PutDataRequest[n2];
    }
}

