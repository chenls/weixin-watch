/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 */
package com.google.android.gms.common.api;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.common.internal.zzx;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class Api<O extends ApiOptions> {
    private final String mName;
    private final zzc<?> zzaeE;
    private final zza<?, O> zzafW;
    private final zze<?, O> zzafX;
    private final zzf<?> zzafY;

    public <C extends zzb> Api(String string2, zza<C, O> zza2, zzc<C> zzc2) {
        zzx.zzb(zza2, (Object)"Cannot construct an Api with a null ClientBuilder");
        zzx.zzb(zzc2, (Object)"Cannot construct an Api with a null ClientKey");
        this.mName = string2;
        this.zzafW = zza2;
        this.zzafX = null;
        this.zzaeE = zzc2;
        this.zzafY = null;
    }

    public String getName() {
        return this.mName;
    }

    /*
     * Enabled aggressive block sorting
     */
    public zza<?, O> zzoP() {
        boolean bl2 = this.zzafW != null;
        zzx.zza(bl2, (Object)"This API was constructed with a SimpleClientBuilder. Use getSimpleClientBuilder");
        return this.zzafW;
    }

    /*
     * Enabled aggressive block sorting
     */
    public zze<?, O> zzoQ() {
        boolean bl2 = this.zzafX != null;
        zzx.zza(bl2, (Object)"This API was constructed with a ClientBuilder. Use getClientBuilder");
        return this.zzafX;
    }

    /*
     * Enabled aggressive block sorting
     */
    public zzc<?> zzoR() {
        boolean bl2 = this.zzaeE != null;
        zzx.zza(bl2, (Object)"This API was constructed with a SimpleClientKey. Use getSimpleClientKey");
        return this.zzaeE;
    }

    public boolean zzoS() {
        return this.zzafY != null;
    }

    public static interface ApiOptions {

        public static interface HasOptions
        extends ApiOptions {
        }

        public static final class NoOptions
        implements NotRequiredOptions {
            private NoOptions() {
            }
        }

        public static interface NotRequiredOptions
        extends ApiOptions {
        }

        public static interface Optional
        extends HasOptions,
        NotRequiredOptions {
        }
    }

    public static abstract class zza<T extends zzb, O> {
        public int getPriority() {
            return Integer.MAX_VALUE;
        }

        public abstract T zza(Context var1, Looper var2, com.google.android.gms.common.internal.zzf var3, O var4, GoogleApiClient.ConnectionCallbacks var5, GoogleApiClient.OnConnectionFailedListener var6);

        public List<Scope> zzo(O o2) {
            return Collections.emptyList();
        }
    }

    public static interface zzb {
        public void disconnect();

        public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4);

        public boolean isConnected();

        public void zza(GoogleApiClient.zza var1);

        public void zza(zzp var1, Set<Scope> var2);

        public boolean zzmE();

        public boolean zznb();

        public Intent zznc();

        public IBinder zzoT();
    }

    public static final class zzc<C extends zzb> {
    }

    public static interface zzd<T extends IInterface> {
        public T zzW(IBinder var1);

        public void zza(int var1, T var2);

        public String zzgu();

        public String zzgv();
    }

    public static interface zze<T extends zzd, O> {
        public int getPriority();

        public int zzoU();

        public T zzq(O var1);
    }

    public static final class zzf<C extends zzd> {
    }
}

