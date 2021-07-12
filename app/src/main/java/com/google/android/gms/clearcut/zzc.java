/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.clearcut;

import com.google.android.gms.clearcut.LogEventParcelable;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import java.util.concurrent.TimeUnit;

public interface zzc {
    public PendingResult<Status> zza(GoogleApiClient var1, LogEventParcelable var2);

    public boolean zza(GoogleApiClient var1, long var2, TimeUnit var4);
}

