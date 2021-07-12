/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.playlog.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzsu;
import com.google.android.gms.playlog.internal.LogEvent;
import com.google.android.gms.playlog.internal.PlayLoggerContext;
import com.google.android.gms.playlog.internal.zza;
import com.google.android.gms.playlog.internal.zzb;
import com.google.android.gms.playlog.internal.zzd;
import java.util.ArrayList;

public class zzf
extends zzj<zza> {
    private final String zzTJ;
    private final zzd zzbdT;
    private final zzb zzbdU;
    private boolean zzbdV;
    private final Object zzpV;

    public zzf(Context context, Looper looper, zzd zzd2, com.google.android.gms.common.internal.zzf zzf2) {
        super(context, looper, 24, zzf2, zzd2, zzd2);
        this.zzTJ = context.getPackageName();
        this.zzbdT = zzx.zzz(zzd2);
        this.zzbdT.zza(this);
        this.zzbdU = new zzb();
        this.zzpV = new Object();
        this.zzbdV = true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzEW() {
        boolean bl2 = !this.zzbdV;
        com.google.android.gms.common.internal.zzb.zzab(bl2);
        if (!this.zzbdU.isEmpty()) {
            PlayLoggerContext playLoggerContext = null;
            try {
                ArrayList<LogEvent> arrayList = new ArrayList<LogEvent>();
                for (zzb.zza zza2 : this.zzbdU.zzEU()) {
                    if (zza2.zzbdI != null) {
                        ((zza)this.zzqJ()).zza(this.zzTJ, zza2.zzbdG, zzsu.toByteArray(zza2.zzbdI));
                        continue;
                    }
                    if (zza2.zzbdG.equals(playLoggerContext)) {
                        arrayList.add(zza2.zzbdH);
                        continue;
                    }
                    if (!arrayList.isEmpty()) {
                        ((zza)this.zzqJ()).zza(this.zzTJ, playLoggerContext, arrayList);
                        arrayList.clear();
                    }
                    playLoggerContext = zza2.zzbdG;
                    arrayList.add(zza2.zzbdH);
                }
                if (!arrayList.isEmpty()) {
                    ((zza)this.zzqJ()).zza(this.zzTJ, playLoggerContext, arrayList);
                }
                this.zzbdU.clear();
                return;
            }
            catch (RemoteException remoteException) {
                Log.e((String)"PlayLoggerImpl", (String)"Couldn't send cached log events to AndroidLog service.  Retaining in memory cache.");
            }
        }
    }

    private void zzc(PlayLoggerContext playLoggerContext, LogEvent logEvent) {
        this.zzbdU.zza(playLoggerContext, logEvent);
    }

    private void zzd(PlayLoggerContext playLoggerContext, LogEvent logEvent) {
        try {
            this.zzEW();
            ((zza)this.zzqJ()).zza(this.zzTJ, playLoggerContext, logEvent);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)"PlayLoggerImpl", (String)"Couldn't send log event.  Will try caching.");
            this.zzc(playLoggerContext, logEvent);
            return;
        }
        catch (IllegalStateException illegalStateException) {
            Log.e((String)"PlayLoggerImpl", (String)"Service was disconnected.  Will try caching.");
            this.zzc(playLoggerContext, logEvent);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void start() {
        Object object = this.zzpV;
        synchronized (object) {
            if (!this.isConnecting() && !this.isConnected()) {
                this.zzbdT.zzat(true);
                this.zzqG();
                return;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void stop() {
        Object object = this.zzpV;
        synchronized (object) {
            this.zzbdT.zzat(false);
            this.disconnect();
            return;
        }
    }

    @Override
    protected /* synthetic */ IInterface zzW(IBinder iBinder) {
        return this.zzdO(iBinder);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void zzau(boolean bl2) {
        Object object = this.zzpV;
        synchronized (object) {
            boolean bl3 = this.zzbdV;
            this.zzbdV = bl2;
            if (bl3 && !this.zzbdV) {
                this.zzEW();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void zzb(PlayLoggerContext playLoggerContext, LogEvent logEvent) {
        Object object = this.zzpV;
        synchronized (object) {
            if (this.zzbdV) {
                this.zzc(playLoggerContext, logEvent);
            } else {
                this.zzd(playLoggerContext, logEvent);
            }
            return;
        }
    }

    protected zza zzdO(IBinder iBinder) {
        return zza.zza.zzdN(iBinder);
    }

    @Override
    protected String zzgu() {
        return "com.google.android.gms.playlog.service.START";
    }

    @Override
    protected String zzgv() {
        return "com.google.android.gms.playlog.internal.IPlayLogService";
    }
}

