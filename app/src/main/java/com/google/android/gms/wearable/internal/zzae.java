/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.wearable.internal.DataItemParcelable;

public class zzae
implements Parcelable.Creator<DataItemParcelable> {
    static void zza(DataItemParcelable dataItemParcelable, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, dataItemParcelable.mVersionCode);
        zzb.zza(parcel, 2, (Parcelable)dataItemParcelable.getUri(), n2, false);
        zzb.zza(parcel, 4, dataItemParcelable.zzIv(), false);
        zzb.zza(parcel, 5, dataItemParcelable.getData(), false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzil(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzlP(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public DataItemParcelable zzil(Parcel parcel) {
        byte[] byArray = null;
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        Bundle bundle = null;
        Bundle bundle2 = null;
        while (parcel.dataPosition() < n2) {
            Bundle bundle3;
            int n4 = zza.zzat(parcel);
            switch (zza.zzca(n4)) {
                default: {
                    zza.zzb(parcel, n4);
                    bundle3 = bundle;
                    bundle = bundle2;
                    bundle2 = bundle3;
                    break;
                }
                case 1: {
                    n3 = zza.zzg(parcel, n4);
                    bundle3 = bundle2;
                    bundle2 = bundle;
                    bundle = bundle3;
                    break;
                }
                case 2: {
                    bundle3 = (Uri)zza.zza(parcel, n4, Uri.CREATOR);
                    bundle2 = bundle;
                    bundle = bundle3;
                    break;
                }
                case 4: {
                    bundle3 = zza.zzr(parcel, n4);
                    bundle = bundle2;
                    bundle2 = bundle3;
                    break;
                }
                case 5: {
                    byArray = zza.zzs(parcel, n4);
                    bundle3 = bundle2;
                    bundle2 = bundle;
                    bundle = bundle3;
                }
            }
            bundle3 = bundle;
            bundle = bundle2;
            bundle2 = bundle3;
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new DataItemParcelable(n3, (Uri)bundle2, bundle, byArray);
    }

    public DataItemParcelable[] zzlP(int n2) {
        return new DataItemParcelable[n2];
    }
}

