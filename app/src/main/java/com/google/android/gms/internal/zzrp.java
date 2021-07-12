/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.os.PowerManager
 *  android.os.PowerManager$WakeLock
 *  android.os.WorkSource
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.PowerManager;
import android.os.WorkSource;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.stats.zzg;
import com.google.android.gms.common.stats.zzi;
import com.google.android.gms.internal.zzlz;
import com.google.android.gms.internal.zzne;
import com.google.android.gms.internal.zzni;
import com.google.android.gms.internal.zznj;

public class zzrp {
    private static boolean DEBUG;
    private static String TAG;
    private static String zzbhl;
    private final Context mContext;
    private final String zzanQ;
    private final PowerManager.WakeLock zzbhm;
    private WorkSource zzbhn;
    private final int zzbho;
    private final String zzbhp;
    private boolean zzbhq;
    private int zzbhr;
    private int zzbhs;

    static {
        TAG = "WakeLock";
        zzbhl = "*gcore*:";
        DEBUG = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public zzrp(Context context, int n2, String string2) {
        String string3 = context == null ? null : context.getPackageName();
        this(context, n2, string2, null, string3);
    }

    /*
     * Enabled aggressive block sorting
     */
    @SuppressLint(value={"UnwrappedWakeLock"})
    public zzrp(Context context, int n2, String string2, String string3, String string4) {
        this.zzbhq = true;
        zzx.zzh(string2, "Wake lock name can NOT be empty");
        this.zzbho = n2;
        this.zzbhp = string3;
        this.mContext = context.getApplicationContext();
        this.zzanQ = !zzni.zzcV(string4) && "com.google.android.gms" != string4 ? zzbhl + string2 : string2;
        this.zzbhm = ((PowerManager)context.getSystemService("power")).newWakeLock(n2, string2);
        if (zznj.zzaA(this.mContext)) {
            string2 = string4;
            if (zzni.zzcV(string4)) {
                if (zzd.zzakE && zzlz.isInitialized()) {
                    Log.e((String)TAG, (String)("callingPackage is not supposed to be empty for wakelock " + this.zzanQ + "!"), (Throwable)new IllegalArgumentException());
                    string2 = "com.google.android.gms";
                } else {
                    string2 = context.getPackageName();
                }
            }
            this.zzbhn = zznj.zzl(context, string2);
            this.zzc(this.zzbhn);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzfJ(String string2) {
        boolean bl2 = this.zzfK(string2);
        String string3 = this.zzn(string2, bl2);
        if (DEBUG) {
            Log.d((String)TAG, (String)("Release:\n mWakeLockName: " + this.zzanQ + "\n mSecondaryName: " + this.zzbhp + "\nmReferenceCounted: " + this.zzbhq + "\nreason: " + string2 + "\n mOpenEventCount" + this.zzbhs + "\nuseWithReason: " + bl2 + "\ntrackingName: " + string3));
        }
        synchronized (this) {
            block8: {
                block7: {
                    block6: {
                        int n2;
                        if (!this.zzbhq) break block6;
                        this.zzbhr = n2 = this.zzbhr - 1;
                        if (n2 == 0 || bl2) break block7;
                    }
                    if (this.zzbhq || this.zzbhs != 1) break block8;
                }
                zzi.zzrZ().zza(this.mContext, zzg.zza(this.zzbhm, string3), 8, this.zzanQ, string3, this.zzbho, zznj.zzb(this.zzbhn));
                --this.zzbhs;
            }
            return;
        }
    }

    private boolean zzfK(String string2) {
        return !TextUtils.isEmpty((CharSequence)string2) && !string2.equals(this.zzbhp);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzj(String string2, long l2) {
        boolean bl2 = this.zzfK(string2);
        String string3 = this.zzn(string2, bl2);
        if (DEBUG) {
            Log.d((String)TAG, (String)("Acquire:\n mWakeLockName: " + this.zzanQ + "\n mSecondaryName: " + this.zzbhp + "\nmReferenceCounted: " + this.zzbhq + "\nreason: " + string2 + "\nmOpenEventCount" + this.zzbhs + "\nuseWithReason: " + bl2 + "\ntrackingName: " + string3 + "\ntimeout: " + l2));
        }
        synchronized (this) {
            block8: {
                block7: {
                    block6: {
                        if (!this.zzbhq) break block6;
                        int n2 = this.zzbhr;
                        this.zzbhr = n2 + 1;
                        if (n2 == 0 || bl2) break block7;
                    }
                    if (this.zzbhq || this.zzbhs != 0) break block8;
                }
                zzi.zzrZ().zza(this.mContext, zzg.zza(this.zzbhm, string3), 7, this.zzanQ, string3, this.zzbho, zznj.zzb(this.zzbhn), l2);
                ++this.zzbhs;
            }
            return;
        }
    }

    private String zzn(String string2, boolean bl2) {
        if (this.zzbhq) {
            if (bl2) {
                return string2;
            }
            return this.zzbhp;
        }
        return this.zzbhp;
    }

    public void acquire(long l2) {
        if (!zzne.zzsg() && this.zzbhq) {
            Log.wtf((String)TAG, (String)("Do not acquire with timeout on reference counted WakeLocks before ICS. wakelock: " + this.zzanQ));
        }
        this.zzj(null, l2);
        this.zzbhm.acquire(l2);
    }

    public boolean isHeld() {
        return this.zzbhm.isHeld();
    }

    public void release() {
        this.zzfJ(null);
        this.zzbhm.release();
    }

    public void setReferenceCounted(boolean bl2) {
        this.zzbhm.setReferenceCounted(bl2);
        this.zzbhq = bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void zzc(WorkSource workSource) {
        if (zznj.zzaA(this.mContext) && workSource != null) {
            if (this.zzbhn != null) {
                this.zzbhn.add(workSource);
            } else {
                this.zzbhn = workSource;
            }
            this.zzbhm.setWorkSource(this.zzbhn);
        }
    }
}

