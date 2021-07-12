/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Process
 */
package com.google.android.gms.internal;

import android.os.Process;

class zznl
implements Runnable {
    private final int mPriority;
    private final Runnable zzx;

    public zznl(Runnable runnable, int n2) {
        this.zzx = runnable;
        this.mPriority = n2;
    }

    @Override
    public void run() {
        Process.setThreadPriority((int)this.mPriority);
        this.zzx.run();
    }
}

