/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzsn;
import java.io.IOException;
import java.util.Arrays;

final class zzsw {
    final int tag;
    final byte[] zzbuv;

    zzsw(int n2, byte[] byArray) {
        this.tag = n2;
        this.zzbuv = byArray;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (object == this) break block4;
                if (!(object instanceof zzsw)) {
                    return false;
                }
                object = (zzsw)object;
                if (this.tag != ((zzsw)object).tag || !Arrays.equals(this.zzbuv, ((zzsw)object).zzbuv)) break block5;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.tag + 527) * 31 + Arrays.hashCode(this.zzbuv);
    }

    void writeTo(zzsn zzsn2) throws IOException {
        zzsn2.zzmB(this.tag);
        zzsn2.zzH(this.zzbuv);
    }

    int zzz() {
        return 0 + zzsn.zzmC(this.tag) + this.zzbuv.length;
    }
}

