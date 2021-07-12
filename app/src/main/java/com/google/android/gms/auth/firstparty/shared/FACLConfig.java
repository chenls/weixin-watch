/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.text.TextUtils
 */
package com.google.android.gms.auth.firstparty.shared;

import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.auth.firstparty.shared.zza;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;

public class FACLConfig
implements SafeParcelable {
    public static final zza CREATOR = new zza();
    final int version;
    boolean zzYm;
    String zzYn;
    boolean zzYo;
    boolean zzYp;
    boolean zzYq;
    boolean zzYr;

    FACLConfig(int n2, boolean bl2, String string2, boolean bl3, boolean bl4, boolean bl5, boolean bl6) {
        this.version = n2;
        this.zzYm = bl2;
        this.zzYn = string2;
        this.zzYo = bl3;
        this.zzYp = bl4;
        this.zzYq = bl5;
        this.zzYr = bl6;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (object instanceof FACLConfig) {
            object = (FACLConfig)object;
            bl3 = bl2;
            if (this.zzYm == ((FACLConfig)object).zzYm) {
                bl3 = bl2;
                if (TextUtils.equals((CharSequence)this.zzYn, (CharSequence)((FACLConfig)object).zzYn)) {
                    bl3 = bl2;
                    if (this.zzYo == ((FACLConfig)object).zzYo) {
                        bl3 = bl2;
                        if (this.zzYp == ((FACLConfig)object).zzYp) {
                            bl3 = bl2;
                            if (this.zzYq == ((FACLConfig)object).zzYq) {
                                bl3 = bl2;
                                if (this.zzYr == ((FACLConfig)object).zzYr) {
                                    bl3 = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return bl3;
    }

    public int hashCode() {
        return zzw.hashCode(this.zzYm, this.zzYn, this.zzYo, this.zzYp, this.zzYq, this.zzYr);
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zza.zza(this, parcel, n2);
    }
}

