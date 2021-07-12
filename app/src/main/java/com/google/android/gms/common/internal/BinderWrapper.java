/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.annotation.KeepName;

@KeepName
public final class BinderWrapper
implements Parcelable {
    public static final Parcelable.Creator<BinderWrapper> CREATOR = new Parcelable.Creator<BinderWrapper>(){

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.zzan(parcel);
        }

        public /* synthetic */ Object[] newArray(int n2) {
            return this.zzbQ(n2);
        }

        public BinderWrapper zzan(Parcel parcel) {
            return new BinderWrapper(parcel);
        }

        public BinderWrapper[] zzbQ(int n2) {
            return new BinderWrapper[n2];
        }
    };
    private IBinder zzakD = null;

    public BinderWrapper() {
    }

    public BinderWrapper(IBinder iBinder) {
        this.zzakD = iBinder;
    }

    private BinderWrapper(Parcel parcel) {
        this.zzakD = parcel.readStrongBinder();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        parcel.writeStrongBinder(this.zzakD);
    }
}

