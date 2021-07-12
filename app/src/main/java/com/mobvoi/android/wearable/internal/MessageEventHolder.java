/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.mobvoi.android.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.mobvoi.android.common.internal.safeparcel.SafeParcelable;
import com.mobvoi.android.wearable.MessageEvent;

public class MessageEventHolder
implements SafeParcelable,
MessageEvent {
    public static final Parcelable.Creator<MessageEventHolder> CREATOR = new Parcelable.Creator<MessageEventHolder>(){

        public MessageEventHolder a(Parcel parcel) {
            return new MessageEventHolder(parcel);
        }

        public MessageEventHolder[] a(int n2) {
            return new MessageEventHolder[n2];
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.a(parcel);
        }

        public /* synthetic */ Object[] newArray(int n2) {
            return this.a(n2);
        }
    };
    private final byte[] a;
    private final String b;
    private final String c;
    private final int d;
    private final int e;

    public MessageEventHolder(int n2, String string2, String string3, byte[] byArray) {
        this.e = n2;
        this.b = string2;
        this.c = string3;
        this.d = byArray.length;
        this.a = byArray;
    }

    private MessageEventHolder(Parcel parcel) {
        this.e = parcel.readInt();
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readInt();
        if (this.d > 0) {
            this.a = parcel.createByteArray();
            return;
        }
        this.a = new byte[0];
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public byte[] getData() {
        return this.a;
    }

    @Override
    public String getPath() {
        return this.c;
    }

    @Override
    public int getRequestId() {
        return this.e;
    }

    @Override
    public String getSourceNodeId() {
        return this.b;
    }

    public String toString() {
        return "source: " + this.b + ", length: " + this.d + ", path: " + this.c;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        parcel.writeInt(this.e);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeInt(this.d);
        parcel.writeByteArray(this.a);
    }
}

