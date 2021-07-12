/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal.safeparcel;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;

public final class zzc {
    public static <T extends SafeParcelable> T zza(Intent object, String string2, Parcelable.Creator<T> creator) {
        if ((object = (Object)object.getByteArrayExtra(string2)) == null) {
            return null;
        }
        return zzc.zza((byte[])object, creator);
    }

    public static <T extends SafeParcelable> T zza(byte[] object, Parcelable.Creator<T> creator) {
        zzx.zzz(creator);
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(object, 0, ((byte[])object).length);
        parcel.setDataPosition(0);
        object = (SafeParcelable)creator.createFromParcel(parcel);
        parcel.recycle();
        return (T)object;
    }

    public static <T extends SafeParcelable> void zza(T t2, Intent intent, String string2) {
        intent.putExtra(string2, zzc.zza(t2));
    }

    public static <T extends SafeParcelable> byte[] zza(T object) {
        Parcel parcel = Parcel.obtain();
        object.writeToParcel(parcel, 0);
        object = parcel.marshall();
        parcel.recycle();
        return object;
    }
}

