/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.gms.common.server.converter.zza;
import com.google.android.gms.common.server.response.FastJsonResponse;

public class ConverterWrapper
implements SafeParcelable {
    public static final zza CREATOR = new zza();
    private final int mVersionCode;
    private final StringToIntConverter zzamF;

    ConverterWrapper(int n2, StringToIntConverter stringToIntConverter) {
        this.mVersionCode = n2;
        this.zzamF = stringToIntConverter;
    }

    private ConverterWrapper(StringToIntConverter stringToIntConverter) {
        this.mVersionCode = 1;
        this.zzamF = stringToIntConverter;
    }

    public static ConverterWrapper zza(FastJsonResponse.zza<?, ?> zza2) {
        if (zza2 instanceof StringToIntConverter) {
            return new ConverterWrapper((StringToIntConverter)zza2);
        }
        throw new IllegalArgumentException("Unsupported safe parcelable field converter class.");
    }

    public int describeContents() {
        zza zza2 = CREATOR;
        return 0;
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zza zza2 = CREATOR;
        zza.zza(this, parcel, n2);
    }

    StringToIntConverter zzrg() {
        return this.zzamF;
    }

    public FastJsonResponse.zza<?, ?> zzrh() {
        if (this.zzamF != null) {
            return this.zzamF;
        }
        throw new IllegalStateException("There was no converter wrapped in this ConverterWrapper.");
    }
}

