/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.safeparcel.zza;

public class zzb
implements Parcelable.Creator<WebImage> {
    static void zza(WebImage webImage, Parcel parcel, int n2) {
        int n3 = com.google.android.gms.common.internal.safeparcel.zzb.zzav(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, webImage.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable)webImage.getUrl(), n2, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, webImage.getWidth());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, webImage.getHeight());
        com.google.android.gms.common.internal.safeparcel.zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzal(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzbN(n2);
    }

    public WebImage zzal(Parcel parcel) {
        int n2 = 0;
        int n3 = zza.zzau(parcel);
        Uri uri = null;
        int n4 = 0;
        int n5 = 0;
        block6: while (parcel.dataPosition() < n3) {
            int n6 = zza.zzat(parcel);
            switch (zza.zzca(n6)) {
                default: {
                    zza.zzb(parcel, n6);
                    continue block6;
                }
                case 1: {
                    n5 = zza.zzg(parcel, n6);
                    continue block6;
                }
                case 2: {
                    uri = (Uri)zza.zza(parcel, n6, Uri.CREATOR);
                    continue block6;
                }
                case 3: {
                    n4 = zza.zzg(parcel, n6);
                    continue block6;
                }
                case 4: 
            }
            n2 = zza.zzg(parcel, n6);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new WebImage(n5, uri, n4, n2);
    }

    public WebImage[] zzbN(int n2) {
        return new WebImage[n2];
    }
}

