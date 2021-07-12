/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 */
package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.stats.zzf;
import com.google.android.gms.common.stats.zzh;
import java.util.List;

public final class WakeLockEvent
extends zzf
implements SafeParcelable {
    public static final Parcelable.Creator<WakeLockEvent> CREATOR = new zzh();
    private final long mTimeout;
    final int mVersionCode;
    private final String zzanQ;
    private final int zzanR;
    private final List<String> zzanS;
    private final String zzanT;
    private int zzanU;
    private final String zzanV;
    private final String zzanW;
    private final float zzanX;
    private final long zzane;
    private int zzanf;
    private final long zzanm;
    private long zzano;

    WakeLockEvent(int n2, long l2, int n3, String string2, int n4, List<String> list, String string3, long l3, int n5, String string4, String string5, float f2, long l4) {
        this.mVersionCode = n2;
        this.zzane = l2;
        this.zzanf = n3;
        this.zzanQ = string2;
        this.zzanV = string4;
        this.zzanR = n4;
        this.zzano = -1L;
        this.zzanS = list;
        this.zzanT = string3;
        this.zzanm = l3;
        this.zzanU = n5;
        this.zzanW = string5;
        this.zzanX = f2;
        this.mTimeout = l4;
    }

    public WakeLockEvent(long l2, int n2, String string2, int n3, List<String> list, String string3, long l3, int n4, String string4, String string5, float f2, long l4) {
        this(1, l2, n2, string2, n3, list, string3, l3, n4, string4, string5, f2, l4);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public int getEventType() {
        return this.zzanf;
    }

    @Override
    public long getTimeMillis() {
        return this.zzane;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzh.zza(this, parcel, n2);
    }

    public String zzrK() {
        return this.zzanT;
    }

    @Override
    public long zzrL() {
        return this.zzano;
    }

    public long zzrN() {
        return this.zzanm;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public String zzrO() {
        StringBuilder stringBuilder = new StringBuilder().append("\t").append(this.zzrR()).append("\t").append(this.zzrT()).append("\t");
        String string2 = this.zzrU() == null ? "" : TextUtils.join((CharSequence)",", this.zzrU());
        stringBuilder = stringBuilder.append(string2).append("\t").append(this.zzrV()).append("\t");
        string2 = this.zzrS() == null ? "" : this.zzrS();
        stringBuilder = stringBuilder.append(string2).append("\t");
        if (this.zzrW() == null) {
            string2 = "";
            return stringBuilder.append(string2).append("\t").append(this.zzrX()).toString();
        }
        string2 = this.zzrW();
        return stringBuilder.append(string2).append("\t").append(this.zzrX()).toString();
    }

    public String zzrR() {
        return this.zzanQ;
    }

    public String zzrS() {
        return this.zzanV;
    }

    public int zzrT() {
        return this.zzanR;
    }

    public List<String> zzrU() {
        return this.zzanS;
    }

    public int zzrV() {
        return this.zzanU;
    }

    public String zzrW() {
        return this.zzanW;
    }

    public float zzrX() {
        return this.zzanX;
    }

    public long zzrY() {
        return this.mTimeout;
    }
}

