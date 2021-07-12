/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.os.Debug
 *  android.os.Parcelable
 *  android.os.Process
 *  android.os.SystemClock
 *  android.util.Log
 */
package com.google.android.gms.common.stats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Debug;
import android.os.Parcelable;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import com.google.android.gms.common.stats.ConnectionEvent;
import com.google.android.gms.common.stats.zzc;
import com.google.android.gms.common.stats.zzd;
import com.google.android.gms.common.stats.zze;
import com.google.android.gms.internal.zzmp;
import com.google.android.gms.internal.zznf;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class zzb {
    private static final Object zzalX = new Object();
    private static zzb zzanp;
    private static Integer zzanv;
    private final List<String> zzanq;
    private final List<String> zzanr;
    private final List<String> zzans;
    private final List<String> zzant;
    private zze zzanu;
    private zze zzanw;

    /*
     * Enabled aggressive block sorting
     */
    private zzb() {
        if (zzb.getLogLevel() == zzd.LOG_LEVEL_OFF) {
            this.zzanq = Collections.EMPTY_LIST;
            this.zzanr = Collections.EMPTY_LIST;
            this.zzans = Collections.EMPTY_LIST;
            this.zzant = Collections.EMPTY_LIST;
            return;
        }
        Object object = zzc.zza.zzanA.get();
        object = object == null ? Collections.EMPTY_LIST : Arrays.asList(((String)object).split(","));
        this.zzanq = object;
        object = zzc.zza.zzanB.get();
        object = object == null ? Collections.EMPTY_LIST : Arrays.asList(((String)object).split(","));
        this.zzanr = object;
        object = zzc.zza.zzanC.get();
        object = object == null ? Collections.EMPTY_LIST : Arrays.asList(((String)object).split(","));
        this.zzans = object;
        object = zzc.zza.zzanD.get();
        object = object == null ? Collections.EMPTY_LIST : Arrays.asList(((String)object).split(","));
        this.zzant = object;
        this.zzanu = new zze(1024, zzc.zza.zzanE.get());
        this.zzanw = new zze(1024, zzc.zza.zzanE.get());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static int getLogLevel() {
        if (zzanv != null) return zzanv;
        try {
            int n2 = zzmp.zzkr() ? zzc.zza.zzanz.get() : zzd.LOG_LEVEL_OFF;
            zzanv = n2;
        }
        catch (SecurityException securityException) {
            zzanv = zzd.LOG_LEVEL_OFF;
            return zzanv;
        }
        return zzanv;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void zza(Context context, String object, int n2, String string2, String string3, String string4, String string5) {
        String string6;
        long l2 = System.currentTimeMillis();
        String string7 = string6 = null;
        if ((zzb.getLogLevel() & zzd.zzanJ) != 0) {
            string7 = string6;
            if (n2 != 13) {
                string7 = zznf.zzn(3, 5);
            }
        }
        long l3 = 0L;
        if ((zzb.getLogLevel() & zzd.zzanL) != 0) {
            l3 = Debug.getNativeHeapAllocatedSize();
        }
        object = n2 == 1 || n2 == 4 || n2 == 14 ? new ConnectionEvent(l2, n2, null, null, null, null, string7, (String)object, SystemClock.elapsedRealtime(), l3) : new ConnectionEvent(l2, n2, string2, string3, string4, string5, string7, (String)object, SystemClock.elapsedRealtime(), l3);
        context.startService(new Intent().setComponent(zzd.zzanF).putExtra("com.google.android.gms.common.stats.EXTRA_LOG_EVENT", (Parcelable)object));
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private void zza(Context context, String string2, String string3, Intent object, int n2) {
        void var7_11;
        void var4_6;
        String string4;
        void var5_8;
        block6: {
            block7: {
                block4: {
                    block5: {
                        Object var8_9 = null;
                        if (!this.zzrQ()) return;
                        if (this.zzanu == null) break block4;
                        if (var5_8 != 4 && var5_8 != true) break block5;
                        if (!this.zzanu.zzcT(string2)) break block4;
                        Object var7_10 = null;
                        string4 = null;
                        Object var4_5 = var8_9;
                        break block6;
                    }
                    ServiceInfo serviceInfo = zzb.zzd(context, object);
                    if (serviceInfo == null) {
                        Log.w((String)"ConnectionTracker", (String)String.format("Client %s made an invalid request %s", string3, object.toUri(0)));
                        return;
                    }
                    string4 = serviceInfo.processName;
                    String string5 = serviceInfo.name;
                    String string6 = zznf.zzaz(context);
                    if (this.zzb(string6, string3, string4, string5)) break block7;
                }
                return;
            }
            this.zzanu.zzcS(string2);
        }
        this.zza(context, string2, (int)var5_8, (String)var4_6, string3, string4, (String)var7_11);
    }

    private String zzb(ServiceConnection serviceConnection) {
        return String.valueOf((long)Process.myPid() << 32 | (long)System.identityHashCode(serviceConnection));
    }

    private boolean zzb(String string2, String string3, String string4, String string5) {
        int n2 = zzb.getLogLevel();
        return !this.zzanq.contains(string2) && !this.zzanr.contains(string3) && !this.zzans.contains(string4) && !this.zzant.contains(string5) && (!string4.equals(string2) || (n2 & zzd.zzanK) == 0);
    }

    private boolean zzc(Context context, Intent intent) {
        if ((intent = intent.getComponent()) == null || com.google.android.gms.common.internal.zzd.zzakE && "com.google.android.gms".equals(intent.getPackageName())) {
            return false;
        }
        return zzmp.zzk(context, intent.getPackageName());
    }

    private static ServiceInfo zzd(Context object, Intent object2) {
        if ((object = object.getPackageManager().queryIntentServices((Intent)object2, 128)) == null || object.size() == 0) {
            Log.w((String)"ConnectionTracker", (String)String.format("There are no handler of this intent: %s\n Stack trace: %s", object2.toUri(0), zznf.zzn(3, 20)));
            return null;
        }
        if (object.size() > 1) {
            Log.w((String)"ConnectionTracker", (String)String.format("Multiple handlers found for this intent: %s\n Stack trace: %s", object2.toUri(0), zznf.zzn(3, 20)));
            object2 = object.iterator();
            if (object2.hasNext()) {
                Log.w((String)"ConnectionTracker", (String)((ResolveInfo)object2.next()).serviceInfo.name);
                return null;
            }
        }
        return ((ResolveInfo)object.get((int)0)).serviceInfo;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzb zzrP() {
        Object object = zzalX;
        synchronized (object) {
            if (zzanp == null) {
                zzanp = new zzb();
            }
            return zzanp;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean zzrQ() {
        return com.google.android.gms.common.internal.zzd.zzakE && zzb.getLogLevel() != zzd.LOG_LEVEL_OFF;
    }

    @SuppressLint(value={"UntrackedBindService"})
    public void zza(Context context, ServiceConnection serviceConnection) {
        context.unbindService(serviceConnection);
        this.zza(context, this.zzb(serviceConnection), (String)null, (Intent)null, 1);
    }

    public void zza(Context context, ServiceConnection serviceConnection, String string2, Intent intent) {
        this.zza(context, this.zzb(serviceConnection), string2, intent, 3);
    }

    public boolean zza(Context context, Intent intent, ServiceConnection serviceConnection, int n2) {
        return this.zza(context, context.getClass().getName(), intent, serviceConnection, n2);
    }

    @SuppressLint(value={"UntrackedBindService"})
    public boolean zza(Context context, String string2, Intent intent, ServiceConnection serviceConnection, int n2) {
        if (this.zzc(context, intent)) {
            Log.w((String)"ConnectionTracker", (String)"Attempted to bind to a service in a STOPPED package.");
            return false;
        }
        boolean bl2 = context.bindService(intent, serviceConnection, n2);
        if (bl2) {
            this.zza(context, this.zzb(serviceConnection), string2, intent, 2);
        }
        return bl2;
    }

    public void zzb(Context context, ServiceConnection serviceConnection) {
        this.zza(context, this.zzb(serviceConnection), (String)null, (Intent)null, 4);
    }
}

