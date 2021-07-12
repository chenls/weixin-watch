/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.IBinder
 */
package com.google.android.gms.dynamic;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.zze;

public abstract class zzg<T> {
    private final String zzavI;
    private T zzavJ;

    protected zzg(String string2) {
        this.zzavI = string2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected final T zzaB(Context object) throws zza {
        if (this.zzavJ != null) return this.zzavJ;
        zzx.zzz(object);
        object = zze.getRemoteContext((Context)object);
        if (object == null) {
            throw new zza("Could not get remote context.");
        }
        object = object.getClassLoader();
        try {
            this.zzavJ = this.zzd((IBinder)((ClassLoader)object).loadClass(this.zzavI).newInstance());
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new zza("Could not load creator class.", classNotFoundException);
        }
        catch (InstantiationException instantiationException) {
            throw new zza("Could not instantiate creator.", instantiationException);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new zza("Could not access creator.", illegalAccessException);
        }
        return this.zzavJ;
    }

    protected abstract T zzd(IBinder var1);

    public static class zza
    extends Exception {
        public zza(String string2) {
            super(string2);
        }

        public zza(String string2, Throwable throwable) {
            super(string2, throwable);
        }
    }
}

