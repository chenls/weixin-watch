/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.wearable.internal.zzau;
import com.google.android.gms.wearable.internal.zzm;
import com.google.android.gms.wearable.internal.zzu;

public final class zzt
extends zzau.zza {
    private zzm zzbsk;
    private zzu zzbso;
    private final Object zzpV = new Object();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void zza(zzu zzu2) {
        Object object = this.zzpV;
        // MONITORENTER : object
        this.zzbso = zzx.zzz(zzu2);
        zzm zzm2 = this.zzbsk;
        // MONITOREXIT : object
        if (zzm2 == null) return;
        zzu2.zzb(zzm2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void zzy(int n2, int n3) {
        zzm zzm2;
        Object object = this.zzpV;
        // MONITORENTER : object
        zzu zzu2 = this.zzbso;
        this.zzbsk = zzm2 = new zzm(n2, n3);
        // MONITOREXIT : object
        if (zzu2 == null) return;
        zzu2.zzb(zzm2);
    }
}

