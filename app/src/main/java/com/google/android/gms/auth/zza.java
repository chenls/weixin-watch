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
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zza
implements Parcelable.Creator<AccountChangeEvent> {
    static void zza(AccountChangeEvent accountChangeEvent, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, accountChangeEvent.mVersion);
        zzb.zza(parcel, 2, accountChangeEvent.zzUZ);
        zzb.zza(parcel, 3, accountChangeEvent.zzVa, false);
        zzb.zzc(parcel, 4, accountChangeEvent.zzVb);
        zzb.zzc(parcel, 5, accountChangeEvent.zzVc);
        zzb.zza(parcel, 6, accountChangeEvent.zzVd, false);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzz(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzau(n2);
    }

    public AccountChangeEvent[] zzau(int n2) {
        return new AccountChangeEvent[n2];
    }

    public AccountChangeEvent zzz(Parcel parcel) {
        String string2 = null;
        int n2 = 0;
        int n3 = com.google.android.gms.common.internal.safeparcel.zza.zzau(parcel);
        long l2 = 0L;
        int n4 = 0;
        String string3 = null;
        int n5 = 0;
        block8: while (parcel.dataPosition() < n3) {
            int n6 = com.google.android.gms.common.internal.safeparcel.zza.zzat(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzca(n6)) {
                default: {
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, n6);
                    continue block8;
                }
                case 1: {
                    n5 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, n6);
                    continue block8;
                }
                case 2: {
                    l2 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, n6);
                    continue block8;
                }
                case 3: {
                    string3 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, n6);
                    continue block8;
                }
                case 4: {
                    n4 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, n6);
                    continue block8;
                }
                case 5: {
                    n2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, n6);
                    continue block8;
                }
                case 6: 
            }
            string2 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, n6);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new AccountChangeEvent(n5, l2, string3, n4, n2, string2);
    }
}

