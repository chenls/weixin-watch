/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.ParcelFileDescriptor
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.data;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zza
implements Parcelable.Creator<BitmapTeleporter> {
    static void zza(BitmapTeleporter bitmapTeleporter, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, bitmapTeleporter.mVersionCode);
        zzb.zza(parcel, 2, (Parcelable)bitmapTeleporter.zzIq, n2, false);
        zzb.zzc(parcel, 3, bitmapTeleporter.zzabB);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaj(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzbE(n2);
    }

    public BitmapTeleporter zzaj(Parcel parcel) {
        int n2 = 0;
        int n3 = com.google.android.gms.common.internal.safeparcel.zza.zzau(parcel);
        ParcelFileDescriptor parcelFileDescriptor = null;
        int n4 = 0;
        block5: while (parcel.dataPosition() < n3) {
            int n5 = com.google.android.gms.common.internal.safeparcel.zza.zzat(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzca(n5)) {
                default: {
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, n5);
                    continue block5;
                }
                case 1: {
                    n4 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, n5);
                    continue block5;
                }
                case 2: {
                    parcelFileDescriptor = (ParcelFileDescriptor)com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, n5, ParcelFileDescriptor.CREATOR);
                    continue block5;
                }
                case 3: 
            }
            n2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, n5);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new BitmapTeleporter(n4, parcelFileDescriptor, n2);
    }

    public BitmapTeleporter[] zzbE(int n2) {
        return new BitmapTeleporter[n2];
    }
}

