/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.StrictMode
 *  android.os.StrictMode$ThreadPolicy
 */
package com.google.android.gms.internal;

import android.os.StrictMode;
import java.util.concurrent.Callable;

public class zzpl {
    public static <T> T zzb(Callable<T> callable) {
        StrictMode.ThreadPolicy threadPolicy = StrictMode.getThreadPolicy();
        try {
            StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)StrictMode.ThreadPolicy.LAX);
            callable = callable.call();
            return (T)callable;
        }
        catch (Throwable throwable) {
            return null;
        }
        finally {
            StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)threadPolicy);
        }
    }
}

