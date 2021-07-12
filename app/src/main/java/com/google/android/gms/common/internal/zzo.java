/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzqt;

public final class zzo {
    public static final int zzaml = 23 - " PII_LOG".length();
    private static final String zzamm = null;
    private final String zzamn;
    private final String zzamo;

    public zzo(String string2) {
        this(string2, zzamm);
    }

    /*
     * Enabled aggressive block sorting
     */
    public zzo(String string2, String string3) {
        zzx.zzb(string2, (Object)"log tag cannot be null");
        boolean bl2 = string2.length() <= 23;
        zzx.zzb(bl2, "tag \"%s\" is longer than the %d character maximum", string2, 23);
        this.zzamn = string2;
        if (string3 != null && string3.length() > 0) {
            this.zzamo = string3;
            return;
        }
        this.zzamo = zzamm;
    }

    private String zzcK(String string2) {
        if (this.zzamo == null) {
            return string2;
        }
        return this.zzamo.concat(string2);
    }

    public void zzA(String string2, String string3) {
        if (this.zzbU(6)) {
            Log.e((String)string2, (String)this.zzcK(string3));
        }
    }

    public void zza(Context object, String string2, String string3, Throwable throwable) {
        StackTraceElement[] stackTraceElementArray = throwable.getStackTrace();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i2 = 0; i2 < stackTraceElementArray.length && i2 < 2; ++i2) {
            stringBuilder.append(stackTraceElementArray[i2].toString());
            stringBuilder.append("\n");
        }
        object = new zzqt((Context)object, 10);
        ((zzqt)object).zza("GMS_WTF", null, "GMS_WTF", stringBuilder.toString());
        ((zzqt)object).send();
        if (this.zzbU(7)) {
            Log.e((String)string2, (String)this.zzcK(string3), (Throwable)throwable);
            Log.wtf((String)string2, (String)this.zzcK(string3), (Throwable)throwable);
        }
    }

    public void zza(String string2, String string3, Throwable throwable) {
        if (this.zzbU(4)) {
            Log.i((String)string2, (String)this.zzcK(string3), (Throwable)throwable);
        }
    }

    public void zzb(String string2, String string3, Throwable throwable) {
        if (this.zzbU(5)) {
            Log.w((String)string2, (String)this.zzcK(string3), (Throwable)throwable);
        }
    }

    public boolean zzbU(int n2) {
        return Log.isLoggable((String)this.zzamn, (int)n2);
    }

    public void zzc(String string2, String string3, Throwable throwable) {
        if (this.zzbU(6)) {
            Log.e((String)string2, (String)this.zzcK(string3), (Throwable)throwable);
        }
    }

    public void zzy(String string2, String string3) {
        if (this.zzbU(3)) {
            Log.d((String)string2, (String)this.zzcK(string3));
        }
    }

    public void zzz(String string2, String string3) {
        if (this.zzbU(5)) {
            Log.w((String)string2, (String)this.zzcK(string3));
        }
    }
}

