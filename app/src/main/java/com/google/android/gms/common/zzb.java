/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zza;

public class zzb
implements Parcelable.Creator<ConnectionResult> {
    static void zza(ConnectionResult connectionResult, Parcel parcel, int n2) {
        int n3 = com.google.android.gms.common.internal.safeparcel.zzb.zzav(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, connectionResult.mVersionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, connectionResult.getErrorCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, (Parcelable)connectionResult.getResolution(), n2, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, connectionResult.getErrorMessage(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzag(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzbt(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public ConnectionResult zzag(Parcel parcel) {
        String string2 = null;
        int n2 = 0;
        int n3 = zza.zzau(parcel);
        PendingIntent pendingIntent = null;
        int n4 = 0;
        while (parcel.dataPosition() < n3) {
            int n5 = zza.zzat(parcel);
            switch (zza.zzca(n5)) {
                default: {
                    zza.zzb(parcel, n5);
                    n5 = n2;
                    n2 = n4;
                    n4 = n5;
                    break;
                }
                case 1: {
                    n5 = zza.zzg(parcel, n5);
                    n4 = n2;
                    n2 = n5;
                    break;
                }
                case 2: {
                    n5 = zza.zzg(parcel, n5);
                    n2 = n4;
                    n4 = n5;
                    break;
                }
                case 3: {
                    pendingIntent = (PendingIntent)zza.zza(parcel, n5, PendingIntent.CREATOR);
                    n5 = n4;
                    n4 = n2;
                    n2 = n5;
                    break;
                }
                case 4: {
                    string2 = zza.zzp(parcel, n5);
                    n5 = n4;
                    n4 = n2;
                    n2 = n5;
                }
            }
            n5 = n2;
            n2 = n4;
            n4 = n5;
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new ConnectionResult(n4, n2, pendingIntent, string2);
    }

    public ConnectionResult[] zzbt(int n2) {
        return new ConnectionResult[n2];
    }
}

