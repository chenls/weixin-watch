/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ValidateAccountRequest;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzae
implements Parcelable.Creator<ValidateAccountRequest> {
    static void zza(ValidateAccountRequest validateAccountRequest, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, validateAccountRequest.mVersionCode);
        zzb.zzc(parcel, 2, validateAccountRequest.zzre());
        zzb.zza(parcel, 3, validateAccountRequest.zzakA, false);
        zzb.zza((Parcel)parcel, (int)4, (Parcelable[])validateAccountRequest.zzrd(), (int)n2, (boolean)false);
        zzb.zza(parcel, 5, validateAccountRequest.zzrf(), false);
        zzb.zza(parcel, 6, validateAccountRequest.getCallingPackage(), false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzas(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzbZ(n2);
    }

    public ValidateAccountRequest zzas(Parcel parcel) {
        int n2 = 0;
        String string2 = null;
        int n3 = zza.zzau(parcel);
        Bundle bundle = null;
        Scope[] scopeArray = null;
        IBinder iBinder = null;
        int n4 = 0;
        block8: while (parcel.dataPosition() < n3) {
            int n5 = zza.zzat(parcel);
            switch (zza.zzca(n5)) {
                default: {
                    zza.zzb(parcel, n5);
                    continue block8;
                }
                case 1: {
                    n4 = zza.zzg(parcel, n5);
                    continue block8;
                }
                case 2: {
                    n2 = zza.zzg(parcel, n5);
                    continue block8;
                }
                case 3: {
                    iBinder = zza.zzq(parcel, n5);
                    continue block8;
                }
                case 4: {
                    scopeArray = zza.zzb(parcel, n5, Scope.CREATOR);
                    continue block8;
                }
                case 5: {
                    bundle = zza.zzr(parcel, n5);
                    continue block8;
                }
                case 6: 
            }
            string2 = zza.zzp(parcel, n5);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new ValidateAccountRequest(n4, n2, iBinder, scopeArray, bundle, string2);
    }

    public ValidateAccountRequest[] zzbZ(int n2) {
        return new ValidateAccountRequest[n2];
    }
}

