/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.auth.firstparty.shared;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.firstparty.shared.FACLData;
import com.google.android.gms.auth.firstparty.shared.ScopeDetail;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public class zzc
implements Parcelable.Creator<ScopeDetail> {
    static void zza(ScopeDetail scopeDetail, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, scopeDetail.version);
        zzb.zza(parcel, 2, scopeDetail.description, false);
        zzb.zza(parcel, 3, scopeDetail.zzYw, false);
        zzb.zza(parcel, 4, scopeDetail.zzYx, false);
        zzb.zza(parcel, 5, scopeDetail.zzYy, false);
        zzb.zza(parcel, 6, scopeDetail.zzYz, false);
        zzb.zzb(parcel, 7, scopeDetail.zzYA, false);
        zzb.zza(parcel, 8, scopeDetail.zzYB, n2, false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzY(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzaV(n2);
    }

    public ScopeDetail zzY(Parcel parcel) {
        FACLData fACLData = null;
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        ArrayList<String> arrayList = new ArrayList();
        String string2 = null;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        String string6 = null;
        block10: while (parcel.dataPosition() < n2) {
            int n4 = zza.zzat(parcel);
            switch (zza.zzca(n4)) {
                default: {
                    zza.zzb(parcel, n4);
                    continue block10;
                }
                case 1: {
                    n3 = zza.zzg(parcel, n4);
                    continue block10;
                }
                case 2: {
                    string6 = zza.zzp(parcel, n4);
                    continue block10;
                }
                case 3: {
                    string5 = zza.zzp(parcel, n4);
                    continue block10;
                }
                case 4: {
                    string4 = zza.zzp(parcel, n4);
                    continue block10;
                }
                case 5: {
                    string3 = zza.zzp(parcel, n4);
                    continue block10;
                }
                case 6: {
                    string2 = zza.zzp(parcel, n4);
                    continue block10;
                }
                case 7: {
                    arrayList = zza.zzD(parcel, n4);
                    continue block10;
                }
                case 8: 
            }
            fACLData = zza.zza(parcel, n4, FACLData.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new ScopeDetail(n3, string6, string5, string4, string3, string2, arrayList, fACLData);
    }

    public ScopeDetail[] zzaV(int n2) {
        return new ScopeDetail[n2];
    }
}

