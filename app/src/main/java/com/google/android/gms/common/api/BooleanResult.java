/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.common.api;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzx;

public class BooleanResult
implements Result {
    private final Status zzUX;
    private final boolean zzagf;

    public BooleanResult(Status status, boolean bl2) {
        this.zzUX = zzx.zzb(status, (Object)"Status must not be null");
        this.zzagf = bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block5: {
            block4: {
                if (object == this) break block4;
                if (!(object instanceof BooleanResult)) {
                    return false;
                }
                object = (BooleanResult)object;
                if (!this.zzUX.equals(((BooleanResult)object).zzUX) || this.zzagf != ((BooleanResult)object).zzagf) break block5;
            }
            return true;
        }
        return false;
    }

    @Override
    public Status getStatus() {
        return this.zzUX;
    }

    public boolean getValue() {
        return this.zzagf;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final int hashCode() {
        int n2;
        int n3 = this.zzUX.hashCode();
        if (this.zzagf) {
            n2 = 1;
            return n2 + (n3 + 527) * 31;
        }
        n2 = 0;
        return n2 + (n3 + 527) * 31;
    }
}

