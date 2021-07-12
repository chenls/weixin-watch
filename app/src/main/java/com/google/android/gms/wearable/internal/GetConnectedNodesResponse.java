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
import com.google.android.gms.wearable.internal.NodeParcelable;
import com.google.android.gms.wearable.internal.zzaq;
import java.util.List;

public class GetConnectedNodesResponse
implements SafeParcelable {
    public static final Parcelable.Creator<GetConnectedNodesResponse> CREATOR = new zzaq();
    public final int statusCode;
    public final int versionCode;
    public final List<NodeParcelable> zzbsI;

    GetConnectedNodesResponse(int n2, int n3, List<NodeParcelable> list) {
        this.versionCode = n2;
        this.statusCode = n3;
        this.zzbsI = list;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzaq.zza(this, parcel, n2);
    }
}

