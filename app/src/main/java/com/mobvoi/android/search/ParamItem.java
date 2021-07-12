/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.mobvoi.android.search;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

public class ParamItem
implements Parcelable,
Serializable {
    public static final Parcelable.Creator<ParamItem> CREATOR = new Parcelable.Creator<ParamItem>(){

        public ParamItem createFromParcel(Parcel parcel) {
            return new ParamItem(parcel.readString(), parcel.readString());
        }

        public ParamItem[] newArray(int n2) {
            return new ParamItem[n2];
        }
    };
    public String key;
    public String value;

    private ParamItem() {
    }

    public ParamItem(String string2, String string3) {
        this.key = string2;
        this.value = string3;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        parcel.writeString(this.key);
        parcel.writeString(this.value);
    }
}

