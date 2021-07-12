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
import com.google.android.gms.auth.TokenData;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public class zze
implements Parcelable.Creator<TokenData> {
    static void zza(TokenData tokenData, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, tokenData.mVersionCode);
        zzb.zza(parcel, 2, tokenData.getToken(), false);
        zzb.zza(parcel, 3, tokenData.zzmn(), false);
        zzb.zza(parcel, 4, tokenData.zzmo());
        zzb.zza(parcel, 5, tokenData.zzmp());
        zzb.zzb(parcel, 6, tokenData.zzmq(), false);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzC(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzax(n2);
    }

    public TokenData zzC(Parcel parcel) {
        ArrayList<String> arrayList = null;
        boolean bl2 = false;
        int n2 = zza.zzau(parcel);
        boolean bl3 = false;
        Long l2 = null;
        String string2 = null;
        int n3 = 0;
        block8: while (parcel.dataPosition() < n2) {
            int n4 = zza.zzat(parcel);
            switch (zza.zzca(n4)) {
                default: {
                    zza.zzb(parcel, n4);
                    continue block8;
                }
                case 1: {
                    n3 = zza.zzg(parcel, n4);
                    continue block8;
                }
                case 2: {
                    string2 = zza.zzp(parcel, n4);
                    continue block8;
                }
                case 3: {
                    l2 = zza.zzj(parcel, n4);
                    continue block8;
                }
                case 4: {
                    bl3 = zza.zzc(parcel, n4);
                    continue block8;
                }
                case 5: {
                    bl2 = zza.zzc(parcel, n4);
                    continue block8;
                }
                case 6: 
            }
            arrayList = zza.zzD(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new TokenData(n3, string2, l2, bl3, bl2, arrayList);
    }

    public TokenData[] zzax(int n2) {
        return new TokenData[n2];
    }
}

