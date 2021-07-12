/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.playlog.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.playlog.internal.zze;

public class PlayLoggerContext
implements SafeParcelable {
    public static final zze CREATOR = new zze();
    public final String packageName;
    public final int versionCode;
    public final int zzbdL;
    public final int zzbdM;
    public final String zzbdN;
    public final String zzbdO;
    public final boolean zzbdP;
    public final String zzbdQ;
    public final boolean zzbdR;
    public final int zzbdS;

    public PlayLoggerContext(int n2, String string2, int n3, int n4, String string3, String string4, boolean bl2, String string5, boolean bl3, int n5) {
        this.versionCode = n2;
        this.packageName = string2;
        this.zzbdL = n3;
        this.zzbdM = n4;
        this.zzbdN = string3;
        this.zzbdO = string4;
        this.zzbdP = bl2;
        this.zzbdQ = string5;
        this.zzbdR = bl3;
        this.zzbdS = n5;
    }

    /*
     * Enabled aggressive block sorting
     */
    public PlayLoggerContext(String string2, int n2, int n3, String string3, String string4, String string5, boolean bl2, int n4) {
        this.versionCode = 1;
        this.packageName = zzx.zzz(string2);
        this.zzbdL = n2;
        this.zzbdM = n3;
        this.zzbdQ = string3;
        this.zzbdN = string4;
        this.zzbdO = string5;
        boolean bl3 = !bl2;
        this.zzbdP = bl3;
        this.zzbdR = bl2;
        this.zzbdS = n4;
    }

    @Deprecated
    public PlayLoggerContext(String string2, int n2, int n3, String string3, String string4, boolean bl2) {
        this.versionCode = 1;
        this.packageName = zzx.zzz(string2);
        this.zzbdL = n2;
        this.zzbdM = n3;
        this.zzbdQ = null;
        this.zzbdN = string3;
        this.zzbdO = string4;
        this.zzbdP = bl2;
        this.zzbdR = false;
        this.zzbdS = 0;
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
                if (!(object instanceof PlayLoggerContext)) {
                    return false;
                }
                object = (PlayLoggerContext)object;
                if (this.versionCode != ((PlayLoggerContext)object).versionCode || !this.packageName.equals(((PlayLoggerContext)object).packageName) || this.zzbdL != ((PlayLoggerContext)object).zzbdL || this.zzbdM != ((PlayLoggerContext)object).zzbdM || !zzw.equal(this.zzbdQ, ((PlayLoggerContext)object).zzbdQ) || !zzw.equal(this.zzbdN, ((PlayLoggerContext)object).zzbdN) || !zzw.equal(this.zzbdO, ((PlayLoggerContext)object).zzbdO) || this.zzbdP != ((PlayLoggerContext)object).zzbdP || this.zzbdR != ((PlayLoggerContext)object).zzbdR || this.zzbdS != ((PlayLoggerContext)object).zzbdS) break block5;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        return zzw.hashCode(this.versionCode, this.packageName, this.zzbdL, this.zzbdM, this.zzbdQ, this.zzbdN, this.zzbdO, this.zzbdP, this.zzbdR, this.zzbdS);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PlayLoggerContext[");
        stringBuilder.append("versionCode=").append(this.versionCode).append(',');
        stringBuilder.append("package=").append(this.packageName).append(',');
        stringBuilder.append("packageVersionCode=").append(this.zzbdL).append(',');
        stringBuilder.append("logSource=").append(this.zzbdM).append(',');
        stringBuilder.append("logSourceName=").append(this.zzbdQ).append(',');
        stringBuilder.append("uploadAccount=").append(this.zzbdN).append(',');
        stringBuilder.append("loggingId=").append(this.zzbdO).append(',');
        stringBuilder.append("logAndroidId=").append(this.zzbdP).append(',');
        stringBuilder.append("isAnonymous=").append(this.zzbdR).append(',');
        stringBuilder.append("qosTier=").append(this.zzbdS);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zze.zza(this, parcel, n2);
    }
}

