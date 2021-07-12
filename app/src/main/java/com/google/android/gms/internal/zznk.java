/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zznl;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class zznk
implements ThreadFactory {
    private final int mPriority;
    private final String zzaoq;
    private final AtomicInteger zzaor = new AtomicInteger();
    private final ThreadFactory zzaos = Executors.defaultThreadFactory();

    public zznk(String string2) {
        this(string2, 0);
    }

    public zznk(String string2, int n2) {
        this.zzaoq = zzx.zzb(string2, (Object)"Name must not be null");
        this.mPriority = n2;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        runnable = this.zzaos.newThread(new zznl(runnable, this.mPriority));
        ((Thread)runnable).setName(this.zzaoq + "[" + this.zzaor.getAndIncrement() + "]");
        return runnable;
    }
}

