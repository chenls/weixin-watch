/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.auth.api.signin.internal;

public class zze {
    static int zzXy = 31;
    private int zzXz = 1;

    /*
     * Enabled aggressive block sorting
     */
    public zze zzP(boolean bl2) {
        int n2 = zzXy;
        int n3 = this.zzXz;
        int n4 = bl2 ? 1 : 0;
        this.zzXz = n4 + n3 * n2;
        return this;
    }

    public int zzne() {
        return this.zzXz;
    }

    /*
     * Enabled aggressive block sorting
     */
    public zze zzp(Object object) {
        int n2 = zzXy;
        int n3 = this.zzXz;
        int n4 = object == null ? 0 : object.hashCode();
        this.zzXz = n4 + n3 * n2;
        return this;
    }
}

