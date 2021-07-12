/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 */
package com.google.android.gms.playlog.internal;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.playlog.internal.zzc;

public class LogEvent
implements SafeParcelable {
    public static final zzc CREATOR = new zzc();
    public final String tag;
    public final int versionCode;
    public final long zzbdA;
    public final long zzbdB;
    public final byte[] zzbdC;
    public final Bundle zzbdD;

    LogEvent(int n2, long l2, long l3, String string2, byte[] byArray, Bundle bundle) {
        this.versionCode = n2;
        this.zzbdA = l2;
        this.zzbdB = l3;
        this.tag = string2;
        this.zzbdC = byArray;
        this.zzbdD = bundle;
    }

    public LogEvent(long l2, long l3, String string2, byte[] byArray, String ... stringArray) {
        this.versionCode = 1;
        this.zzbdA = l2;
        this.zzbdB = l3;
        this.tag = string2;
        this.zzbdC = byArray;
        this.zzbdD = LogEvent.zzd(stringArray);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Bundle zzd(String ... stringArray) {
        Bundle bundle = null;
        if (stringArray != null) {
            if (stringArray.length % 2 != 0) {
                throw new IllegalArgumentException("extras must have an even number of elements");
            }
            int n2 = stringArray.length / 2;
            if (n2 != 0) {
                Bundle bundle2 = new Bundle(n2);
                int n3 = 0;
                while (true) {
                    bundle = bundle2;
                    if (n3 >= n2) break;
                    bundle2.putString(stringArray[n3 * 2], stringArray[n3 * 2 + 1]);
                    ++n3;
                }
            }
        }
        return bundle;
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("tag=").append(this.tag).append(",");
        stringBuilder.append("eventTime=").append(this.zzbdA).append(",");
        stringBuilder.append("eventUptime=").append(this.zzbdB).append(",");
        if (this.zzbdD != null && !this.zzbdD.isEmpty()) {
            stringBuilder.append("keyValues=");
            for (String string2 : this.zzbdD.keySet()) {
                stringBuilder.append("(").append(string2).append(",");
                stringBuilder.append(this.zzbdD.getString(string2)).append(")");
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzc.zza(this, parcel, n2);
    }
}

