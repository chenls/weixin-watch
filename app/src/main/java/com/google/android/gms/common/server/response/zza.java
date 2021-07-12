/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.server.converter.ConverterWrapper;
import com.google.android.gms.common.server.response.FastJsonResponse;

public class zza
implements Parcelable.Creator<FastJsonResponse.Field> {
    static void zza(FastJsonResponse.Field field, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, field.getVersionCode());
        zzb.zzc(parcel, 2, field.zzrj());
        zzb.zza(parcel, 3, field.zzrp());
        zzb.zzc(parcel, 4, field.zzrk());
        zzb.zza(parcel, 5, field.zzrq());
        zzb.zza(parcel, 6, field.zzrr(), false);
        zzb.zzc(parcel, 7, field.zzrs());
        zzb.zza(parcel, 8, field.zzru(), false);
        zzb.zza(parcel, 9, field.zzrw(), n2, false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaA(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzcg(n2);
    }

    public FastJsonResponse.Field zzaA(Parcel parcel) {
        ConverterWrapper converterWrapper = null;
        int n2 = 0;
        int n3 = com.google.android.gms.common.internal.safeparcel.zza.zzau(parcel);
        String string2 = null;
        String string3 = null;
        boolean bl2 = false;
        int n4 = 0;
        boolean bl3 = false;
        int n5 = 0;
        int n6 = 0;
        block11: while (parcel.dataPosition() < n3) {
            int n7 = com.google.android.gms.common.internal.safeparcel.zza.zzat(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzca(n7)) {
                default: {
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, n7);
                    continue block11;
                }
                case 1: {
                    n6 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, n7);
                    continue block11;
                }
                case 2: {
                    n5 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, n7);
                    continue block11;
                }
                case 3: {
                    bl3 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, n7);
                    continue block11;
                }
                case 4: {
                    n4 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, n7);
                    continue block11;
                }
                case 5: {
                    bl2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, n7);
                    continue block11;
                }
                case 6: {
                    string3 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, n7);
                    continue block11;
                }
                case 7: {
                    n2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, n7);
                    continue block11;
                }
                case 8: {
                    string2 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, n7);
                    continue block11;
                }
                case 9: 
            }
            converterWrapper = com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, n7, ConverterWrapper.CREATOR);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new FastJsonResponse.Field(n6, n5, bl3, n4, bl2, string3, n2, string2, converterWrapper);
    }

    public FastJsonResponse.Field[] zzcg(int n2) {
        return new FastJsonResponse.Field[n2];
    }
}

