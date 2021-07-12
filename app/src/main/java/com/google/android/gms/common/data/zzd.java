/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class zzd<T extends SafeParcelable>
extends AbstractDataBuffer<T> {
    private static final String[] zzajg = new String[]{"data"};
    private final Parcelable.Creator<T> zzajh;

    public zzd(DataHolder dataHolder, Parcelable.Creator<T> creator) {
        super(dataHolder);
        this.zzajh = creator;
    }

    @Override
    public /* synthetic */ Object get(int n2) {
        return this.zzbG(n2);
    }

    public T zzbG(int n2) {
        Object object = this.zzahi.zzg("data", n2, this.zzahi.zzbH(n2));
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(object, 0, ((byte[])object).length);
        parcel.setDataPosition(0);
        object = (SafeParcelable)this.zzajh.createFromParcel(parcel);
        parcel.recycle();
        return (T)object;
    }
}

