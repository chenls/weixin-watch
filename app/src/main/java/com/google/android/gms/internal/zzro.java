/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import com.google.android.gms.common.api.Api;

public final class zzro
implements Api.ApiOptions.Optional {
    public static final zzro zzbgV = new zza().zzFJ();
    private final boolean zzXa;
    private final boolean zzXc;
    private final String zzXd;
    private final String zzXe;
    private final boolean zzbgW;
    private final boolean zzbgX;

    private zzro(boolean bl2, boolean bl3, String string2, boolean bl4, String string3, boolean bl5) {
        this.zzbgW = bl2;
        this.zzXa = bl3;
        this.zzXd = string2;
        this.zzXc = bl4;
        this.zzbgX = bl5;
        this.zzXe = string3;
    }

    public boolean zzFH() {
        return this.zzbgW;
    }

    public boolean zzFI() {
        return this.zzbgX;
    }

    public boolean zzmO() {
        return this.zzXa;
    }

    public boolean zzmQ() {
        return this.zzXc;
    }

    public String zzmR() {
        return this.zzXd;
    }

    @Nullable
    public String zzmS() {
        return this.zzXe;
    }

    public static final class zza {
        private String zzbdY;
        private boolean zzbgY;
        private boolean zzbgZ;
        private boolean zzbha;
        private String zzbhb;
        private boolean zzbhc;

        public zzro zzFJ() {
            return new zzro(this.zzbgY, this.zzbgZ, this.zzbdY, this.zzbha, this.zzbhb, this.zzbhc);
        }
    }
}

