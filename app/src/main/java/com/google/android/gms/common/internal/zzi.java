/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.GetServiceRequest;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzi
implements Parcelable.Creator<GetServiceRequest> {
    static void zza(GetServiceRequest getServiceRequest, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, getServiceRequest.version);
        zzb.zzc(parcel, 2, getServiceRequest.zzall);
        zzb.zzc(parcel, 3, getServiceRequest.zzalm);
        zzb.zza(parcel, 4, getServiceRequest.zzaln, false);
        zzb.zza(parcel, 5, getServiceRequest.zzalo, false);
        zzb.zza((Parcel)parcel, (int)6, (Parcelable[])getServiceRequest.zzalp, (int)n2, (boolean)false);
        zzb.zza(parcel, 7, getServiceRequest.zzalq, false);
        zzb.zza(parcel, 8, (Parcelable)getServiceRequest.zzalr, n2, false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzao(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzbR(n2);
    }

    public GetServiceRequest zzao(Parcel parcel) {
        int n2 = 0;
        Account account = null;
        int n3 = zza.zzau(parcel);
        Bundle bundle = null;
        Scope[] scopeArray = null;
        IBinder iBinder = null;
        String string2 = null;
        int n4 = 0;
        int n5 = 0;
        block10: while (parcel.dataPosition() < n3) {
            int n6 = zza.zzat(parcel);
            switch (zza.zzca(n6)) {
                default: {
                    zza.zzb(parcel, n6);
                    continue block10;
                }
                case 1: {
                    n5 = zza.zzg(parcel, n6);
                    continue block10;
                }
                case 2: {
                    n4 = zza.zzg(parcel, n6);
                    continue block10;
                }
                case 3: {
                    n2 = zza.zzg(parcel, n6);
                    continue block10;
                }
                case 4: {
                    string2 = zza.zzp(parcel, n6);
                    continue block10;
                }
                case 5: {
                    iBinder = zza.zzq(parcel, n6);
                    continue block10;
                }
                case 6: {
                    scopeArray = zza.zzb(parcel, n6, Scope.CREATOR);
                    continue block10;
                }
                case 7: {
                    bundle = zza.zzr(parcel, n6);
                    continue block10;
                }
                case 8: 
            }
            account = (Account)zza.zza(parcel, n6, Account.CREATOR);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new GetServiceRequest(n5, n4, n2, string2, iBinder, scopeArray, bundle, account);
    }

    public GetServiceRequest[] zzbR(int n2) {
        return new GetServiceRequest[n2];
    }
}

