/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.stats.zza;
import com.google.android.gms.common.stats.zzf;

public final class ConnectionEvent
extends zzf
implements SafeParcelable {
    public static final Parcelable.Creator<ConnectionEvent> CREATOR = new zza();
    final int mVersionCode;
    private final long zzane;
    private int zzanf;
    private final String zzang;
    private final String zzanh;
    private final String zzani;
    private final String zzanj;
    private final String zzank;
    private final String zzanl;
    private final long zzanm;
    private final long zzann;
    private long zzano;

    ConnectionEvent(int n2, long l2, int n3, String string2, String string3, String string4, String string5, String string6, String string7, long l3, long l4) {
        this.mVersionCode = n2;
        this.zzane = l2;
        this.zzanf = n3;
        this.zzang = string2;
        this.zzanh = string3;
        this.zzani = string4;
        this.zzanj = string5;
        this.zzano = -1L;
        this.zzank = string6;
        this.zzanl = string7;
        this.zzanm = l3;
        this.zzann = l4;
    }

    public ConnectionEvent(long l2, int n2, String string2, String string3, String string4, String string5, String string6, String string7, long l3, long l4) {
        this(1, l2, n2, string2, string3, string4, string5, string6, string7, l3, l4);
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
        zza.zza(this, parcel, n2);
    }

    public String zzrF() {
        return this.zzang;
    }

    public String zzrG() {
        return this.zzanh;
    }

    public String zzrH() {
        return this.zzani;
    }

    public String zzrI() {
        return this.zzanj;
    }

    public String zzrJ() {
        return this.zzank;
    }

    public String zzrK() {
        return this.zzanl;
    }

    @Override
    public long zzrL() {
        return this.zzano;
    }

    public long zzrM() {
        return this.zzann;
    }

    public long zzrN() {
        return this.zzanm;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public String zzrO() {
        String string2;
        StringBuilder stringBuilder = new StringBuilder().append("\t").append(this.zzrF()).append("/").append(this.zzrG()).append("\t").append(this.zzrH()).append("/").append(this.zzrI()).append("\t");
        if (this.zzank == null) {
            string2 = "";
            return stringBuilder.append(string2).append("\t").append(this.zzrM()).toString();
        }
        string2 = this.zzank;
        return stringBuilder.append(string2).append("\t").append(this.zzrM()).toString();
    }
}

