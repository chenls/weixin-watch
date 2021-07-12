/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.api;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzc
implements Parcelable.Creator<Status> {
    static void zza(Status status, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, status.getStatusCode());
        zzb.zzc(parcel, 1000, status.getVersionCode());
        zzb.zza(parcel, 2, status.getStatusMessage(), false);
        zzb.zza(parcel, 3, (Parcelable)status.zzpc(), n2, false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzai(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzby(n2);
    }

    public Status zzai(Parcel parcel) {
        PendingIntent pendingIntent = null;
        int n2 = 0;
        int n3 = zza.zzau(parcel);
        String string2 = null;
        int n4 = 0;
        block6: while (parcel.dataPosition() < n3) {
            int n5 = zza.zzat(parcel);
            switch (zza.zzca(n5)) {
                default: {
                    zza.zzb(parcel, n5);
                    continue block6;
                }
                case 1: {
                    n2 = zza.zzg(parcel, n5);
                    continue block6;
                }
                case 1000: {
                    n4 = zza.zzg(parcel, n5);
                    continue block6;
                }
                case 2: {
                    string2 = zza.zzp(parcel, n5);
                    continue block6;
                }
                case 3: 
            }
            pendingIntent = (PendingIntent)zza.zza(parcel, n5, PendingIntent.CREATOR);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new Status(n4, n2, string2, pendingIntent);
    }

    public Status[] zzby(int n2) {
        return new Status[n2];
    }
}

