/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.common.api.internal.zzu;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public interface zzp {
    public ConnectionResult blockingConnect();

    public ConnectionResult blockingConnect(long var1, TimeUnit var3);

    public void connect();

    public boolean disconnect();

    public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4);

    @Nullable
    public ConnectionResult getConnectionResult(@NonNull Api<?> var1);

    public boolean isConnected();

    public boolean isConnecting();

    public <A extends Api.zzb, R extends Result, T extends zza.zza<R, A>> T zza(@NonNull T var1);

    public boolean zza(zzu var1);

    public <A extends Api.zzb, T extends zza.zza<? extends Result, A>> T zzb(@NonNull T var1);

    public void zzoW();

    public void zzpj();

    public static interface zza {
        public void zzc(int var1, boolean var2);

        public void zzd(ConnectionResult var1);

        public void zzi(Bundle var1);
    }
}

