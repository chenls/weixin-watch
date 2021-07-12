/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.clearcut;

import android.os.Parcel;
import com.google.android.gms.clearcut.zzb;
import com.google.android.gms.clearcut.zzd;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.internal.zzsz;
import com.google.android.gms.playlog.internal.PlayLoggerContext;
import java.util.Arrays;

public class LogEventParcelable
implements SafeParcelable {
    public static final zzd CREATOR = new zzd();
    public final int versionCode;
    public PlayLoggerContext zzafh;
    public byte[] zzafi;
    public int[] zzafj;
    public final zzsz.zzd zzafk;
    public final zzb.zzb zzafl;
    public final zzb.zzb zzafm;

    LogEventParcelable(int n2, PlayLoggerContext playLoggerContext, byte[] byArray, int[] nArray) {
        this.versionCode = n2;
        this.zzafh = playLoggerContext;
        this.zzafi = byArray;
        this.zzafj = nArray;
        this.zzafk = null;
        this.zzafl = null;
        this.zzafm = null;
    }

    public LogEventParcelable(PlayLoggerContext playLoggerContext, zzsz.zzd zzd2, zzb.zzb zzb2, zzb.zzb zzb3, int[] nArray) {
        this.versionCode = 1;
        this.zzafh = playLoggerContext;
        this.zzafk = zzd2;
        this.zzafl = zzb2;
        this.zzafm = zzb3;
        this.zzafj = nArray;
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (!(object instanceof LogEventParcelable)) {
                    return false;
                }
                object = (LogEventParcelable)object;
                if (this.versionCode != ((LogEventParcelable)object).versionCode || !zzw.equal(this.zzafh, ((LogEventParcelable)object).zzafh) || !Arrays.equals(this.zzafi, ((LogEventParcelable)object).zzafi) || !Arrays.equals(this.zzafj, ((LogEventParcelable)object).zzafj) || !zzw.equal(this.zzafk, ((LogEventParcelable)object).zzafk) || !zzw.equal(this.zzafl, ((LogEventParcelable)object).zzafl) || !zzw.equal(this.zzafm, ((LogEventParcelable)object).zzafm)) break block5;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        return zzw.hashCode(this.versionCode, this.zzafh, this.zzafi, this.zzafj, this.zzafk, this.zzafl, this.zzafm);
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("LogEventParcelable[");
        stringBuilder.append(this.versionCode);
        stringBuilder.append(", ");
        stringBuilder.append(this.zzafh);
        stringBuilder.append(", ");
        String string2 = this.zzafi == null ? null : new String(this.zzafi);
        stringBuilder.append(string2);
        stringBuilder.append(", ");
        string2 = this.zzafj == null ? (String)null : zzv.zzcL(", ").zza(Arrays.asList(new int[][]{this.zzafj}));
        stringBuilder.append(string2);
        stringBuilder.append(", ");
        stringBuilder.append(this.zzafk);
        stringBuilder.append(", ");
        stringBuilder.append(this.zzafl);
        stringBuilder.append(", ");
        stringBuilder.append(this.zzafm);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzd.zza(this, parcel, n2);
    }
}

