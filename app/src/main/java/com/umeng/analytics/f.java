/*
 * Decompiled with CFR 0.151.
 */
package com.umeng.analytics;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class f {
    private static List<WeakReference<ScheduledFuture<?>>> a = new ArrayList();
    private static ExecutorService b = Executors.newSingleThreadExecutor();
    private static long c = 5L;
    private static ScheduledExecutorService d = Executors.newSingleThreadScheduledExecutor();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void a() {
        try {
            Iterator<WeakReference<ScheduledFuture<?>>> iterator = a.iterator();
            while (iterator.hasNext()) {
                ScheduledFuture scheduledFuture = (ScheduledFuture)iterator.next().get();
                if (scheduledFuture == null) continue;
                scheduledFuture.cancel(false);
            }
            a.clear();
            if (!b.isShutdown()) {
                b.shutdown();
            }
            if (!d.isShutdown()) {
                d.shutdown();
            }
            b.awaitTermination(c, TimeUnit.SECONDS);
            d.awaitTermination(c, TimeUnit.SECONDS);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public static void a(Runnable runnable) {
        if (b.isShutdown()) {
            b = Executors.newSingleThreadExecutor();
        }
        b.execute(runnable);
    }

    public static void a(Runnable runnable, long l2) {
        synchronized (f.class) {
            if (d.isShutdown()) {
                d = Executors.newSingleThreadScheduledExecutor();
            }
            a.add(new WeakReference(d.schedule(runnable, l2, TimeUnit.MILLISECONDS)));
            return;
        }
    }

    public static void b(Runnable runnable) {
        synchronized (f.class) {
            if (d.isShutdown()) {
                d = Executors.newSingleThreadScheduledExecutor();
            }
            d.execute(runnable);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void c(Runnable future) {
        synchronized (f.class) {
            if (d.isShutdown()) {
                d = Executors.newSingleThreadScheduledExecutor();
            }
            future = d.submit((Runnable)((Object)future));
            try {
                future.get(5L, TimeUnit.SECONDS);
            }
            catch (Exception exception) {}
            return;
        }
    }
}

