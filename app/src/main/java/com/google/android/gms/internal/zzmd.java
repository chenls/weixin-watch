/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 */
package com.google.android.gms.internal;

import android.graphics.drawable.Drawable;
import android.support.v4.util.LruCache;
import com.google.android.gms.common.internal.zzw;

public final class zzmd
extends LruCache<zza, Drawable> {
    public zzmd() {
        super(10);
    }

    public static final class zza {
        public final int zzakx;
        public final int zzaky;

        public zza(int n2, int n3) {
            this.zzakx = n2;
            this.zzaky = n3;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean equals(Object object) {
            boolean bl2 = true;
            if (!(object instanceof zza)) {
                return false;
            }
            boolean bl3 = bl2;
            if (this == object) return bl3;
            object = (zza)object;
            if (((zza)object).zzakx != this.zzakx) return false;
            bl3 = bl2;
            if (((zza)object).zzaky == this.zzaky) return bl3;
            return false;
        }

        public int hashCode() {
            return zzw.hashCode(this.zzakx, this.zzaky);
        }
    }
}

