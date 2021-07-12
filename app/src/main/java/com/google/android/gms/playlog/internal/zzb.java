/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.playlog.internal;

import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzsz;
import com.google.android.gms.playlog.internal.LogEvent;
import com.google.android.gms.playlog.internal.PlayLoggerContext;
import java.util.ArrayList;

public class zzb {
    private final ArrayList<zza> zzbdE = new ArrayList();
    private int zzbdF;

    public zzb() {
        this(100);
    }

    public zzb(int n2) {
        this.zzbdF = n2;
    }

    private void zzEV() {
        while (this.getSize() > this.getCapacity()) {
            this.zzbdE.remove(0);
        }
    }

    public void clear() {
        this.zzbdE.clear();
    }

    public int getCapacity() {
        return this.zzbdF;
    }

    public int getSize() {
        return this.zzbdE.size();
    }

    public boolean isEmpty() {
        return this.zzbdE.isEmpty();
    }

    public ArrayList<zza> zzEU() {
        return this.zzbdE;
    }

    public void zza(PlayLoggerContext playLoggerContext, LogEvent logEvent) {
        this.zzbdE.add(new zza(playLoggerContext, logEvent));
        this.zzEV();
    }

    public static class zza {
        public final PlayLoggerContext zzbdG;
        public final LogEvent zzbdH;
        public final zzsz.zzd zzbdI;

        private zza(PlayLoggerContext playLoggerContext, LogEvent logEvent) {
            this.zzbdG = zzx.zzz(playLoggerContext);
            this.zzbdH = zzx.zzz(logEvent);
            this.zzbdI = null;
        }
    }
}

