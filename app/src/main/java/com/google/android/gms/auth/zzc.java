/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.AccountChangeEvent;
import com.google.android.gms.auth.AccountChangeEventsResponse;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public class zzc
implements Parcelable.Creator<AccountChangeEventsResponse> {
    static void zza(AccountChangeEventsResponse accountChangeEventsResponse, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, accountChangeEventsResponse.mVersion);
        zzb.zzc(parcel, 2, accountChangeEventsResponse.zzpH, false);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzB(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzaw(n2);
    }

    public AccountChangeEventsResponse zzB(Parcel parcel) {
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        ArrayList<AccountChangeEvent> arrayList = null;
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
            arrayList = zza.zzc(parcel, n4, AccountChangeEvent.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new AccountChangeEventsResponse(n3, arrayList);
    }

    public AccountChangeEventsResponse[] zzaw(int n2) {
        return new AccountChangeEventsResponse[n2];
    }
}

