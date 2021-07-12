/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.text.TextUtils
 */
package com.google.android.gms.auth;

import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.auth.zze;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzx;
import java.util.List;

public class TokenData
implements SafeParcelable {
    public static final zze CREATOR = new zze();
    final int mVersionCode;
    private final String zzVo;
    private final Long zzVp;
    private final boolean zzVq;
    private final boolean zzVr;
    private final List<String> zzVs;

    TokenData(int n2, String string2, Long l2, boolean bl2, boolean bl3, List<String> list) {
        this.mVersionCode = n2;
        this.zzVo = zzx.zzcM(string2);
        this.zzVp = l2;
        this.zzVq = bl2;
        this.zzVr = bl3;
        this.zzVs = list;
    }

    @Nullable
    public static TokenData zzc(Bundle bundle, String string2) {
        bundle.setClassLoader(TokenData.class.getClassLoader());
        bundle = bundle.getBundle(string2);
        if (bundle == null) {
            return null;
        }
        bundle.setClassLoader(TokenData.class.getClassLoader());
        return (TokenData)bundle.getParcelable("TokenData");
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block3: {
            block2: {
                if (!(object instanceof TokenData)) break block2;
                object = (TokenData)object;
                if (TextUtils.equals((CharSequence)this.zzVo, (CharSequence)((TokenData)object).zzVo) && zzw.equal(this.zzVp, ((TokenData)object).zzVp) && this.zzVq == ((TokenData)object).zzVq && this.zzVr == ((TokenData)object).zzVr && zzw.equal(this.zzVs, ((TokenData)object).zzVs)) break block3;
            }
            return false;
        }
        return true;
    }

    public String getToken() {
        return this.zzVo;
    }

    public int hashCode() {
        return zzw.hashCode(this.zzVo, this.zzVp, this.zzVq, this.zzVr, this.zzVs);
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zze.zza(this, parcel, n2);
    }

    @Nullable
    public Long zzmn() {
        return this.zzVp;
    }

    public boolean zzmo() {
        return this.zzVq;
    }

    public boolean zzmp() {
        return this.zzVr;
    }

    @Nullable
    public List<String> zzmq() {
        return this.zzVs;
    }
}

