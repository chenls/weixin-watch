/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzz
implements Parcelable.Creator<ResolveAccountResponse> {
    static void zza(ResolveAccountResponse resolveAccountResponse, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, resolveAccountResponse.mVersionCode);
        zzb.zza(parcel, 2, resolveAccountResponse.zzakA, false);
        zzb.zza(parcel, 3, resolveAccountResponse.zzqY(), n2, false);
        zzb.zza(parcel, 4, resolveAccountResponse.zzqZ());
        zzb.zza(parcel, 5, resolveAccountResponse.zzra());
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaq(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzbX(n2);
    }

    public ResolveAccountResponse zzaq(Parcel parcel) {
        ConnectionResult connectionResult = null;
        boolean bl2 = false;
        int n2 = zza.zzau(parcel);
        boolean bl3 = false;
        IBinder iBinder = null;
        int n3 = 0;
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
                    iBinder = zza.zzq(parcel, n4);
                    continue block7;
                }
                case 3: {
                    connectionResult = zza.zza(parcel, n4, ConnectionResult.CREATOR);
                    continue block7;
                }
                case 4: {
                    bl3 = zza.zzc(parcel, n4);
                    continue block7;
                }
                case 5: 
            }
            bl2 = zza.zzc(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new ResolveAccountResponse(n3, iBinder, connectionResult, bl3, bl2);
    }

    public ResolveAccountResponse[] zzbX(int n2) {
        return new ResolveAccountResponse[n2];
    }
}

