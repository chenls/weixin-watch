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
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.signin.internal.SignInRequest;

public class zzi
implements Parcelable.Creator<SignInRequest> {
    static void zza(SignInRequest signInRequest, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, signInRequest.mVersionCode);
        zzb.zza(parcel, 2, signInRequest.zzFO(), n2, false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzgU(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzkc(n2);
    }

    public SignInRequest zzgU(Parcel parcel) {
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        ResolveAccountRequest resolveAccountRequest = null;
        block4: while (parcel.dataPosition() < n2) {
            int n4 = zza.zzat(parcel);
            switch (zza.zzca(n4)) {
                default: {
                    zza.zzb(parcel, n4);
                    continue block4;
                }
                case 1: {
                    n3 = zza.zzg(parcel, n4);
                    continue block4;
                }
                case 2: 
            }
            resolveAccountRequest = zza.zza(parcel, n4, ResolveAccountRequest.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new SignInRequest(n3, resolveAccountRequest);
    }

    public SignInRequest[] zzkc(int n2) {
        return new SignInRequest[n2];
    }
}

