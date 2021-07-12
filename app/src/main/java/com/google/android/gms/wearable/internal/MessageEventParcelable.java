/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.internal.zzba;

public class MessageEventParcelable
implements SafeParcelable,
MessageEvent {
    public static final Parcelable.Creator<MessageEventParcelable> CREATOR = new zzba();
    private final String mPath;
    final int mVersionCode;
    private final byte[] zzaKm;
    private final String zzaPI;
    private final int zzaox;

    MessageEventParcelable(int n2, int n3, String string2, byte[] byArray, String string3) {
        this.mVersionCode = n2;
        this.zzaox = n3;
        this.mPath = string2;
        this.zzaKm = byArray;
        this.zzaPI = string3;
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public byte[] getData() {
        return this.zzaKm;
    }

    @Override
    public String getPath() {
        return this.mPath;
    }

    @Override
    public int getRequestId() {
        return this.zzaox;
    }

    @Override
    public String getSourceNodeId() {
        return this.zzaPI;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String toString() {
        Object object;
        StringBuilder stringBuilder = new StringBuilder().append("MessageEventParcelable[").append(this.zzaox).append(",").append(this.mPath).append(", size=");
        if (this.zzaKm == null) {
            object = "null";
            return stringBuilder.append(object).append("]").toString();
        }
        object = this.zzaKm.length;
        return stringBuilder.append(object).append("]").toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzba.zza(this, parcel, n2);
    }
}

