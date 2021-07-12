/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.ServiceConnection
 *  android.os.IBinder
 *  android.os.Looper
 */
package com.google.android.gms.common;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Looper;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class zza
implements ServiceConnection {
    private final BlockingQueue<IBinder> zzafA = new LinkedBlockingQueue<IBinder>();
    boolean zzafz = false;

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.zzafA.add(iBinder);
    }

    public void onServiceDisconnected(ComponentName componentName) {
    }

    public IBinder zzoJ() throws InterruptedException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("BlockingServiceConnection.getService() called on main thread");
        }
        if (this.zzafz) {
            throw new IllegalStateException();
        }
        this.zzafz = true;
        return this.zzafA.take();
    }
}

