/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.AuthAccountRequest;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzc
implements Parcelable.Creator<AuthAccountRequest> {
    static void zza(AuthAccountRequest authAccountRequest, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, authAccountRequest.mVersionCode);
        zzb.zza(parcel, 2, authAccountRequest.zzakA, false);
        zzb.zza((Parcel)parcel, (int)3, (Parcelable[])authAccountRequest.zzafT, (int)n2, (boolean)false);
        zzb.zza(parcel, 4, authAccountRequest.zzakB, false);
        zzb.zza(parcel, 5, authAccountRequest.zzakC, false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzam(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzbP(n2);
    }

    public AuthAccountRequest zzam(Parcel parcel) {
        Integer n2 = null;
        int n3 = zza.zzau(parcel);
        int n4 = 0;
        Integer n5 = null;
        Scope[] scopeArray = null;
        IBinder iBinder = null;
        block7: while (parcel.dataPosition() < n3) {
            int n6 = zza.zzat(parcel);
            switch (zza.zzca(n6)) {
                default: {
                    zza.zzb(parcel, n6);
                    continue block7;
                }
                case 1: {
                    n4 = zza.zzg(parcel, n6);
                    continue block7;
                }
                case 2: {
                    iBinder = zza.zzq(parcel, n6);
                    continue block7;
                }
                case 3: {
                    scopeArray = zza.zzb(parcel, n6, Scope.CREATOR);
                    continue block7;
                }
                case 4: {
                    n5 = zza.zzh(parcel, n6);
                    continue block7;
                }
                case 5: 
            }
            n2 = zza.zzh(parcel, n6);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new AuthAccountRequest(n4, iBinder, scopeArray, n5, n2);
    }

    public AuthAccountRequest[] zzbP(int n2) {
        return new AuthAccountRequest[n2];
    }
}

