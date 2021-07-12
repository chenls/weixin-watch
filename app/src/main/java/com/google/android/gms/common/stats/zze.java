/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 *  android.util.Log
 */
package com.google.android.gms.common.stats;

import android.os.SystemClock;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;

public class zze {
    private final long zzanN;
    private final int zzanO;
    private final SimpleArrayMap<String, Long> zzanP;

    public zze() {
        this.zzanN = 60000L;
        this.zzanO = 10;
        this.zzanP = new SimpleArrayMap(10);
    }

    public zze(int n2, long l2) {
        this.zzanN = l2;
        this.zzanO = n2;
        this.zzanP = new SimpleArrayMap();
    }

    private void zzb(long l2, long l3) {
        for (int i2 = this.zzanP.size() - 1; i2 >= 0; --i2) {
            if (l3 - this.zzanP.valueAt(i2) <= l2) continue;
            this.zzanP.removeAt(i2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Long zzcS(String object) {
        long l2 = SystemClock.elapsedRealtime();
        long l3 = this.zzanN;
        synchronized (this) {
            while (this.zzanP.size() >= this.zzanO) {
                this.zzb(l3, l2);
                Log.w((String)"ConnectionTracker", (String)("The max capacity " + this.zzanO + " is not enough. Current durationThreshold is: " + (l3 /= 2L)));
            }
            return this.zzanP.put((String)object, l2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean zzcT(String string2) {
        synchronized (this) {
            if (this.zzanP.remove(string2) == null) return false;
            return true;
        }
    }
}

