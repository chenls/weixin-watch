/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.signin.internal.SignInResponse;

public class zzj
implements Parcelable.Creator<SignInResponse> {
    static void zza(SignInResponse signInResponse, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, signInResponse.mVersionCode);
        zzb.zza(parcel, 2, signInResponse.zzqY(), n2, false);
        zzb.zza(parcel, 3, signInResponse.zzFP(), n2, false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzgV(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzkd(n2);
    }

    public SignInResponse zzgV(Parcel parcel) {
        ResolveAccountResponse resolveAccountResponse = null;
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        ConnectionResult connectionResult = null;
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
                    connectionResult = zza.zza(parcel, n4, ConnectionResult.CREATOR);
                    continue block5;
                }
                case 3: 
            }
            resolveAccountResponse = zza.zza(parcel, n4, ResolveAccountResponse.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new SignInResponse(n3, connectionResult, resolveAccountResponse);
    }

    public SignInResponse[] zzkd(int n2) {
        return new SignInResponse[n2];
    }
}

